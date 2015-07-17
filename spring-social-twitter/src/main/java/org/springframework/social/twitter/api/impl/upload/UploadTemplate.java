/*
 * Copyright 2014 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.twitter.api.impl.upload;

import java.net.URI;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.social.twitter.api.ton.TonOperations;
import org.springframework.social.twitter.api.upload.UploadedEntity;
import org.springframework.social.twitter.api.upload.UploadOperations;
import org.springframework.social.twitter.api.impl.AbstractTwitterOperations;
import org.springframework.social.twitter.api.impl.TwitterApiBuilderForHttpEntity;
import org.springframework.social.twitter.api.impl.TwitterApiBuilderForUri;
import org.springframework.social.twitter.api.impl.TwitterApiUriResourceForUpload;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Implementation of {@link TonOperations}, providing a binding to the Twitter Object Nest.
 * 
 * @author Chris Latko
 */
public class UploadTemplate extends AbstractTwitterOperations implements UploadOperations {
	
	private final RestTemplate restTemplate;

    public UploadTemplate(RestTemplate restTemplate, boolean isAuthorizedForUser, boolean isAuthorizedForApp) {
        super(isAuthorizedForUser, isAuthorizedForApp);
        this.restTemplate = restTemplate;
    }

	@Override
	public UploadedEntity uploadSimple(final byte[] data) {
        requireUserAuthorization();

        MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();
        form.add("media", new ByteArrayResource(data));
        
        URI uri = new TwitterApiBuilderForUri()
        		.withResource(TwitterApiUriResourceForUpload.UPLOAD)
        		.build();

        HttpEntity<?> entity = new TwitterApiBuilderForHttpEntity<>(form)
        		.multipart(true)
        		.build();

        return restTemplate.exchange(uri, HttpMethod.POST, entity, UploadedEntity.class).getBody();
	}

	@Override
	public void uploadChunked(final String uploadCommand, final String resumeId, final byte[] data, final String contentType) {

	}

	


}
