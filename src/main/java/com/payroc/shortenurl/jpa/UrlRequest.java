package com.payroc.shortenurl.jpa;


import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * A Wrapper class for long and short Url string request 
 * It needs to be annotated with @XmlRootElement annotation for marshalling and unmarshalling operations.
 * 
 */

@XmlRootElement
public class UrlRequest {

	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}