package com.payroc.shortenurl.cdi;
import java.util.List;

import com.payroc.shortenurl.cdi.event.ClickCountEvent;
import com.payroc.shortenurl.jpa.UrlEntity;
import com.payroc.shortenurl.jpa.UrlResponse;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UrlShortnerDAOService {

	@PersistenceContext(unitName = "primary")
	private EntityManager entityManager;

	@Inject
	private UrlShortenerUtil util;

	@Inject
	private Event<ClickCountEvent> clickCountEvent;


	/**
	 * Since an in memory database is used for this project, this serves by list all the entries we have in
	 * our database. I have a plan to make this accessible only by API KEY if time permits. 
	 * @param tinyUrl short Url String
	 * @return UrlEntity existing long URL if it exists otherwise null 
	 */
	public List<UrlEntity> findAll() {

		List<UrlEntity> shortUrl = entityManager
				.createNamedQuery("UrlEntity.findAllUrlEntries", UrlEntity.class)
				.getResultList();

		return shortUrl;
	}

	/**
	 * It searches the database by primary key to return original URL for a given tiny URL
	 * @param tinyUrl short Url String
	 * @return UrlEntity existing long URL if it exists otherwise null 
	 * 
	 * @Transactional Normally there is no need to annotate read operations Transactional but in this case we need
	 * the context so we can fire an event for the even Observer to update clickCount in database
	 */

	@Transactional
	public UrlEntity findOrigUrlByTinyUrl(String tinyUrl) {


		UrlEntity urlEntity = null;

		int primaryKey = util.parsePrimaryKey(tinyUrl);
		urlEntity =  entityManager.find(UrlEntity.class, Long.valueOf(primaryKey));

		/*
		 * Fire a clickCount event so the clickCountObserver can update increment clickCount and update database
		*/
		if(urlEntity != null) 
			clickCountEvent.fire(new ClickCountEvent(urlEntity));

		return urlEntity;
	}


	/**
	 * It searches the database to check if there already is a tiny url for a given long URL
	 * @param origUrl long Url String
	 * @return UrlEntity existing tiny URL if it exists otherwise null
	 * Note: Searching by long url will be a performance hit when the entries are in millions, indexing it will be
	 * necessary to for a fast retrieval.
	 */

	public UrlEntity findByOriginalUrl(String origUrl) {

		UrlEntity urlEntity = null;
		List<UrlEntity> urlObjList = entityManager
				.createNamedQuery("UrlEntity.findByOriginalUrl", UrlEntity.class)
				.setParameter("origUrl", origUrl)
				.getResultList();

		if(urlObjList.size() == 0 ) {
			return urlEntity;
		}

		if(urlObjList.size() > 1 ) {
			throw new NonUniqueResultException("More than one record returned.");
		}

		return urlObjList.get(0);
	}


	/**
	 * It returns a tiny URL from a long URL passed as argument
	 * @param origUrl long Url String
	 * @return UrlResponse tiny URL wrapper
	 * @Transactional annotation is used because we have the persist call to database and we want this method call
	 *  to be executed in a transaction.
	 */

	@Transactional
	public UrlResponse createTinyUrl(String origUrl) {

		UrlEntity existingTinyUrl = findByOriginalUrl(origUrl);

		if (!isExistingTinyUrl(existingTinyUrl)) {

			/* Here we first persist our UrlEntity object to database with tinyUrl value set NULL so we can 
			 * get our database generated primary key. Then we use the primary key to generate base62 encoded value 
			 * and finally we call the UrlEntity setter method to update tinyUrl value. Notice that we didn't need to call
			 * entity manager again to persist with the newly generated tinyUrl value, that is because 
			 * the persist call we already made takes the entity instance, adds it to the context and makes that instance managed
			 *  (i.e. future updates to the entity will be tracked).
			 * 
			 * */
			UrlEntity urlEntity = createNewUrlEntityRecord(origUrl, null);

			entityManager.persist(urlEntity);

			String newTinyUrl = util.generateTinyUrl(urlEntity.getId());

			//updating this entity object will also updates our database record for tinyUrl;
			urlEntity.setTinyUrl(newTinyUrl);

			return  createTinyUrlResponse(newTinyUrl);
		}

		return  createTinyUrlResponse(existingTinyUrl.getTinyUrl());
	}


	
	private boolean isExistingTinyUrl(UrlEntity urlEntity) {
		return urlEntity != null;
	}
	
	

	/**
	 * It creates a UrlResponse by wrapping the string passed as an argument
	 * @param tinyUrl Short Url String
	 * @return UrlResponse tiny URL wrapper for URL string
	 */
	private UrlResponse createTinyUrlResponse(String tinyUrl) {
		UrlResponse urlResponse = new UrlResponse();
		urlResponse.setUrl(tinyUrl);
		return urlResponse;

	}

	/**
	 * It creates a new entry to persist to the database
	 * returns the instance of Config.
	 * @param origUrl original Url
	 * @param tinyUrl Short Url
	 * @return UrlEntity
	 */
	private UrlEntity createNewUrlEntityRecord(String origUrl, String tinyUrl) {

		UrlEntity newUrlEntity = new UrlEntity();
		newUrlEntity.setOrigUrl(origUrl);
		newUrlEntity.setTinyUrl(tinyUrl);
		newUrlEntity.setClickCount(1L);

		return newUrlEntity;
	}

}