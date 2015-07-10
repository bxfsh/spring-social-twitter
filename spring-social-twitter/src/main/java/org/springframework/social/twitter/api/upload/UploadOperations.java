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
package org.springframework.social.twitter.api.upload;

import org.springframework.social.ApiException;
import org.springframework.social.MissingAuthorizationException;

/**
 * Interface defining media uploads.
 * 
 * @author Chris Latko
 */
public interface UploadOperations {

    /**
     * Media to be used with tweets need to be uploaded here. Only media (not mediat_data) is supported.
     * 
     * @param media the binary data
     * @throws ApiException if there is an error while communicating with Twitter.
     * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
     * @return MediaEntity a JSON representation of metadata of the uploaded media.
     */
	UploadedEntity uploadSimple(final byte[] media);

    /**
     * Uploads larger than 64MB. They are chunked and uploaded via a resumeId.
     * 
     * @param mediaId identifier used to resume an upload
     * @param data the binary data
     * @param contentType mime type of data, must conform to http://www.iana.org/assignments/media-types/media-types.xhtml
     * @param expiry TON expiry date, up to 7 days, but ruby script has 10 days.
     * @throws ApiException if there is an error while communicating with Twitter.
     * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
     */
    void uploadChunked(final String uploadCommand, final String resumeId, final byte[] data, final String contentType);

}
