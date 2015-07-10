package org.springframework.social.twitter.api.impl;

public enum TwitterApiUriResourceForUpload {
	UPLOAD("media/upload.json");
	
	private final String path;
	
	TwitterApiUriResourceForUpload(String path) {
		this.path = path;
	}
	
	public String getPath() {
		return this.path;
	}
}
