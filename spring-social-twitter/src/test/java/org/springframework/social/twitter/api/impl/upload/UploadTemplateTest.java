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

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.social.twitter.api.impl.AbstractTwitterApiTest;
import org.springframework.social.twitter.api.upload.ChunkCommandType;
import org.springframework.social.twitter.api.upload.UploadedEntity;

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

        final Resource resource = dataResource("profilepic.gif");
        final InputStream is = resource.getInputStream();
        final byte[] data = bufferObj(is);

        final UploadedEntity uploadedEntity = twitter.uploadOperations().uploadSimple(data);
        assertEquals(12302, uploadedEntity.getSize());
        assertEquals("image/png", uploadedEntity.getImage().getImageType());
    }

    @Test
    public void uploadChunk_test() throws IOException {
        final String mediaId = init_test();
    }


    private String init_test() throws UnsupportedEncodingException {
        final String command = ChunkCommandType.INIT.name();
        final String contentType = "video/mp4";
        final int totalSize = 383631;

        final MockMultipartHttpServletRequest request = new MockMultipartHttpServletRequest();
        request.addParameter("command", command);
        request.addParameter("media_type", contentType);
        request.addParameter("total_bytes", String.valueOf(totalSize));

        /*
         * final StringBuilder sb = new StringBuilder();
         * sb.append("command=" + command);
         * sb.append("&");
         * sb.append("total_bytes=" + String.valueOf(totalSize));
         * sb.append("&");
         * sb.append("media_type=" + URLEncoder.encode(contentType, UTF8));
         */

        mockServer
                .expect(requestTo("https://upload.twitter.com/1.1/media/upload.json"))
                .andExpect(method(POST))
                .andExpect(content().string(containsString("Content-Disposition: form-data; name=\"command\"")))
                .andExpect(content().string(containsString("Content-Disposition: form-data; name=\"media_type\"")))
                .andExpect(content().string(containsString("Content-Disposition: form-data; name=\"total_bytes\"")))
                .andExpect(content().string(containsString(command)))
                .andExpect(content().string(containsString(contentType)))
                .andExpect(content().string(containsString(String.valueOf(totalSize))))
                .andRespond(withSuccess(jsonResource("init"), APPLICATION_JSON));

        final UploadedEntity uploadedEntity = twitter.uploadOperations().uploadChunkedInit(totalSize, contentType);
        assertEquals("623694170260049925", uploadedEntity.getMediaIdString());
        assertEquals(3599, uploadedEntity.getExpiresAfterSecs());

        return uploadedEntity.getMediaIdString();
    }

    public void append_test(final String mediaId) throws IOException {
        final Resource resource = dataResource("small.mp4");
        final InputStream is = resource.getInputStream();
        final byte[] data = bufferObj(is);


        final String command = ChunkCommandType.APPEND.name();
        final int segmentId = 0;

        final StringBuilder sb = new StringBuilder();
        sb.append("command=" + command);
        sb.append("&");
        sb.append("media_id=" + mediaId);
        sb.append("&");
        sb.append("segment_index=" + String.valueOf(segmentId));
        sb.append("&");
        sb.append("media=" + String.valueOf(segmentId));

        final MockMultipartFile mockMultipartFile = new MockMultipartFile("media", data);
        final MockMultipartHttpServletRequest request = new MockMultipartHttpServletRequest();
        request.addFile(mockMultipartFile);

        mockServer
                .expect(requestTo("https://upload.twitter.com/1.1/media/upload.json"))
                .andExpect(method(POST))
                .andExpect(content().string(sb.toString()))
                .andRespond(withSuccess(jsonResource("upload"), APPLICATION_JSON));

        twitter.uploadOperations().uploadChunkedAppend(mediaId, data, 0);
        //        assertEquals(12302,uploadedEntity.getSize());
        //        assertEquals("image/png", uploadedEntity.getImage().getImageType());
    }



    private byte[] bufferObj(final InputStream is) throws IOException {
        final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int count;
        final byte[] data = new byte[16384];
        while ((count = is.read(data, 0, data.length)) != -1)
            buffer.write(data, 0, count);
        buffer.flush();
        return buffer.toByteArray();
    }

}
