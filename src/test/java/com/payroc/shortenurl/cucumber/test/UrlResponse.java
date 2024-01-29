package com.payroc.shortenurl.cucumber.test;

import io.cucumber.core.internal.com.fasterxml.jackson.annotation.JsonProperty;

public class UrlResponse {
	
	@JsonProperty("url")
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}