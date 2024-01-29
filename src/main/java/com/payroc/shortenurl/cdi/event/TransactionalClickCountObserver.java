package com.payroc.shortenurl.cdi.event;

import com.payroc.shortenurl.jpa.UrlEntity;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.TransactionPhase;
import jakarta.transaction.Transactional;

/**
 * Transactional event observers can subscribe to be notified in certain stages of the current 
 * transaction, instead of being notified directly at the time the event is fired. 
 * Here we use this observer to get notified when an Original Url gets accessed by 
 * tinyUrl value so we can update the clickCount column in database. I understand in this case
 * it is not too much value to use an event for such update (as we can just make the update when we make a call to database)
 * but in situations where we want to decouple one task from the other, this is an important approach
 *  (i.e if we assume here clickCount event is not really the core functionality of this app instead it is there for metrics purposes) 
 *  
 */

@RequestScoped
public class TransactionalClickCountObserver {
	
	@Transactional
	public void observeClickCountEvent(@Observes(during = TransactionPhase.IN_PROGRESS) ClickCountEvent event){
		UrlEntity urlEntity = event.getUrlEntity();
		Long clickCount = urlEntity.getClickCount() + 1;
		urlEntity.setClickCount(clickCount);
	}

}
