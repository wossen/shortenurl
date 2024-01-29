package com.payroc.shortenurl.cdi.event;

import com.payroc.shortenurl.jpa.UrlEntity;

public class ClickCountEvent {
	
	private UrlEntity urlEntity;
	
	public ClickCountEvent(UrlEntity urlEntity) {
		this.urlEntity = urlEntity;
	}
	
	
	public UrlEntity getUrlEntity() {
		return this.urlEntity;
	}
	
}
