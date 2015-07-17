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

import static org.junit.Assert.assertEquals;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.core.io.Resource;
import org.springframework.social.twitter.api.impl.AbstractTwitterApiTest;
import org.springframework.social.twitter.api.upload.UploadedEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * @author Chris Latko
 */
public class UploadTemplateTest extends AbstractTwitterApiTest {
	@SuppressWarnings("unused")
	private final static Log logger = LogFactory.getLog(UploadTemplateTest.class);
	
    @Test
    public void uploadSimple_test() throws IOException {
        mockServer
                .expect(requestTo("https://upload.twitter.com/1.1/media/upload.json"))
                .andExpect(method(POST))
                .andRespond(withSuccess(jsonResource("upload"), APPLICATION_JSON));

        Resource resource = dataResource("profilepic.gif");
        InputStream is = resource.getInputStream();
        byte[] data = bufferObj(is);
        
        MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();
        form.add("media", data);

        UploadedEntity uploadedEntity = twitter.uploadOperations().uploadSimple(data);
        assertEquals(12302,uploadedEntity.getSize());
        assertEquals("image/png", uploadedEntity.getImage().getImageType());
    }

	private byte[] bufferObj(final InputStream is) throws IOException {
    	ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    	int count;
    	byte[] data = new byte[16384];
    	while((count = is.read(data, 0, data.length)) != -1) {
    		buffer.write(data, 0, count);
    	}
    	buffer.flush();
        return buffer.toByteArray();
    }

}
