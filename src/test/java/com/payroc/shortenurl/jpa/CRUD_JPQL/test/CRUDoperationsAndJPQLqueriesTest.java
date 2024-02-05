package com.payroc.shortenurl.jpa.CRUD_JPQL.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


import static org.junit.jupiter.api.Assertions.assertEquals;

import com.payroc.shortenurl.cdi.UrlShortenerUtil_Base62;
import com.payroc.shortenurl.jpa.UrlEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;

@ExtendWith(MockitoExtension.class)
public class CRUDoperationsAndJPQLqueriesTest {

	EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
	EntityManager em;
	EntityTransaction txn;
	UrlEntity entity;

	@InjectMocks
	UrlShortenerUtil_Base62 util;

	private String TINY_URL;
	private String origUrl = "https://projects.eclipse.org/projects/ee4j.jakartaee-platform/releases/web-profile-11";

	@BeforeEach 
	public void initEntityManager() {
		em = emf.createEntityManager();
		txn = em.getTransaction();

		entity = new UrlEntity();
		entity.setTinyUrl(null);
		entity.setOrigUrl(origUrl);
		entity.setClickCount(1L);
	}
	
	@AfterEach
	public void closeEntityManager() {
		if(em != null) 
			em.close();
	}


	@Test
	public void shouldCreateUrlEntity() {

		txn.begin();
		em.persist(entity);
		txn.commit();

		assertNotNull(entity.getId(), "ID should not be null");
		TINY_URL = util.generateTinyUrl( entity.getId());
		entity.setTinyUrl(TINY_URL);

		txn.begin();
		UrlEntity ent = em.find(UrlEntity.class, entity.getId());
		txn.commit();

		assertEquals(TINY_URL, ent.getTinyUrl());

	}


	@Test
	public void shouldFindTinyUrlByOriginalUrl() {

		txn.begin();
		em.persist(entity);
		txn.commit();

		assertNotNull(entity.getId(), "ID should not be null");
		TINY_URL = util.generateTinyUrl( entity.getId());
		entity.setTinyUrl(TINY_URL);

		UrlEntity en = em.createNamedQuery("UrlEntity.findByOriginalUrl", UrlEntity.class).setParameter("origUrl", origUrl).getSingleResult();

		assertEquals(TINY_URL, en.getTinyUrl());
	}

	
	@Test
	public void shouldRaisePersistenceException_NullOriginalUrl() {
		assertThrows(PersistenceException.class,
				()->{
					UrlEntity entity = new UrlEntity();
					entity.setTinyUrl(null);
					entity.setOrigUrl(null);
					entity.setClickCount(1L);

					em.persist(entity);
				});
	}


}
