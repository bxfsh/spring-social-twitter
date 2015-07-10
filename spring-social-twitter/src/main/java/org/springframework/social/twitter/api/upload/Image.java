/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.twitter.api.upload;

import org.springframework.social.twitter.api.TwitterObject;

/**
 * <p>A representation of uploaded media.</p>
 * @author clatko
 */
public class Image extends TwitterObject {
	private static final long serialVersionUID = 1L;
	private long w;
	private long h;
	private String imageType;
	
	public Image(long w, long h, String imageType) {
		this.w = w;
		this.h = h;
		this.imageType = imageType;
	}

	public long getW() {
		return w;
	}

	public long getH() {
		return h;
	}

	public String getImageType() {
		return imageType;
	}


}
