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
package org.springframework.social.twitter.api.impl.advertising;

import static org.junit.Assert.assertEquals;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import org.junit.Test;
import org.springframework.social.twitter.api.advertising.TargetingCriterion;
import org.springframework.social.twitter.api.impl.AbstractTwitterApiTest;
import org.springframework.social.twitter.api.impl.DataListHolder;

/**
 * @author Hudson mendes
 */
public class TargetingCriteriaTemplateTest extends AbstractTwitterApiTest {

    @Test
    public void getTargetingCriterias() {
        String mockedAccountId = "hkk5";
        String mockedLineItemId = "oi4h5";
        mockServer
                .expect(requestTo(
                        "https://ads-api.twitter.com/0/accounts/" + mockedAccountId + "/targeting_criteria" +
                                "?line_item_id=" + mockedLineItemId +
                                "&with_deleted=false"))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("ad-targeting-criteria"), APPLICATION_JSON));

        DataListHolder<TargetingCriterion> targetingCriterias = twitter.targetingCriteriaOperations().getTargetingCriterions(
                mockedAccountId,
                new TargetingCriterionQueryBuilder()
                        .withLineItem(mockedLineItemId)
                        .includeDeleted(false));

        assertTargetCriteriaContents(targetingCriterias.getList());
    }

    @Test
    public void getTargetingCriteria() {
        String mockedAccountId = "hkk5";
        String mockedTargetingCriteriaId = "2rqqn";
        mockServer
                .expect(requestTo("https://ads-api.twitter.com/0/accounts/" + mockedAccountId + "/targeting_criteria/" + mockedTargetingCriteriaId))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("ad-targeting-criteria-single"), APPLICATION_JSON));

        TargetingCriterion criteria = twitter.targetingCriteriaOperations().getTargetingCriterion(mockedAccountId, mockedTargetingCriteriaId);
        assertSingleTargetingCriteriaContents(criteria);
    }

    @Test
    public void createTargetingCriteria() {
        String mockedAccountId = "hkk5";
        String doesntMatterString = "doesnt-matter";
        Boolean doesntMatterBool = false;
        String chainedPostContent =
                "line_item_id=" + doesntMatterString + "&" +
                        "name=" + doesntMatterString + "&" +
                        "targeting_type=" + "APP_STORE_CATEGORY" + "&" +
                        "targeting_value=" + doesntMatterString + "&" +
                        "deleted=" + doesntMatterBool;

        mockServer
                .expect(requestTo("https://ads-api.twitter.com/0/accounts/" + mockedAccountId + "/targeting_criteria"))
                .andExpect(method(POST))
                .andExpect(content().string(chainedPostContent))
                .andRespond(withSuccess(jsonResource("ad-targeting-criteria-single"), APPLICATION_JSON));

        TargetingCriterion criteria = twitter.targetingCriteriaOperations().createTargetingCriterion(
                mockedAccountId,
                new TargetingCriterionFormBuilder()
                        .withLineItem(doesntMatterString)
                        .withName(doesntMatterString)
                        .targeting("APP_STORE_CATEGORY", doesntMatterString)
                        .active());

        assertSingleTargetingCriteriaContents(criteria);
    }

    private void assertTargetCriteriaContents(List<TargetingCriterion> criterias) {
        assertEquals(4, criterias.size());

        assertEquals("2kzxf", criterias.get(0).getId());
        assertEquals("hkk5", criterias.get(0).getAccountId());
        assertEquals("69ob", criterias.get(0).getLineItemId());
        assertEquals("episod", criterias.get(0).getName());
        assertEquals("SIMILAR_TO_FOLLOWERS_OF_USER", criterias.get(0).getTargetingType());
        assertEquals("819797", criterias.get(0).getTargetingValue());
        assertEquals(false, criterias.get(0).isDeleted());
        assertEquals(LocalDateTime.of(2012, Month.NOVEMBER, 30, 22, 58, 11), criterias.get(0).getCreatedAt());
        assertEquals(LocalDateTime.of(2012, Month.NOVEMBER, 30, 22, 58, 11), criterias.get(0).getUpdatedAt());

        assertEquals("2kzxi", criterias.get(1).getId());
        assertEquals("hkk5", criterias.get(1).getAccountId());
        assertEquals("69ob", criterias.get(1).getLineItemId());
        assertEquals("matrixsynth", criterias.get(1).getName());
        assertEquals("SIMILAR_TO_FOLLOWERS_OF_USER", criterias.get(1).getTargetingType());
        assertEquals("22231561", criterias.get(1).getTargetingValue());
        assertEquals(false, criterias.get(1).isDeleted());
        assertEquals(LocalDateTime.of(2012, Month.NOVEMBER, 30, 22, 58, 12), criterias.get(1).getCreatedAt());
        assertEquals(LocalDateTime.of(2012, Month.NOVEMBER, 30, 22, 58, 12), criterias.get(1).getUpdatedAt());

        assertEquals("2kzxj", criterias.get(2).getId());
        assertEquals("hkk5", criterias.get(2).getAccountId());
        assertEquals("69ob", criterias.get(2).getLineItemId());
        assertEquals("Horse_ebooks", criterias.get(2).getName());
        assertEquals("SIMILAR_TO_FOLLOWERS_OF_USER", criterias.get(2).getTargetingType());
        assertEquals("174958347", criterias.get(2).getTargetingValue());
        assertEquals(false, criterias.get(2).isDeleted());
        assertEquals(LocalDateTime.of(2012, Month.NOVEMBER, 30, 22, 58, 12), criterias.get(2).getCreatedAt());
        assertEquals(LocalDateTime.of(2012, Month.NOVEMBER, 30, 22, 58, 12), criterias.get(2).getUpdatedAt());

        assertEquals("2mq7j", criterias.get(3).getId());
        assertEquals("hkk5", criterias.get(3).getAccountId());
        assertEquals("69ob", criterias.get(3).getLineItemId());
        assertEquals("righteous dude", criterias.get(3).getName());
        assertEquals("PHRASE_KEYWORD", criterias.get(3).getTargetingType());
        assertEquals("righteous dude", criterias.get(3).getTargetingValue());
        assertEquals(true, criterias.get(3).isDeleted());
        assertEquals(LocalDateTime.of(2012, Month.DECEMBER, 05, 05, 11, 15), criterias.get(3).getCreatedAt());
        assertEquals(LocalDateTime.of(2012, Month.DECEMBER, 06, 05, 11, 15), criterias.get(3).getUpdatedAt());
    }

    private void assertSingleTargetingCriteriaContents(TargetingCriterion criteria) {
        assertEquals("2rqqn", criteria.getId());
        assertEquals("hkk5", criteria.getAccountId());
        assertEquals("6zva", criteria.getLineItemId());
        assertEquals("Portland OR, US", criteria.getName());
        assertEquals("LOCATION", criteria.getTargetingType());
        assertEquals("b6b8d75a320f81d9", criteria.getTargetingValue());
        assertEquals(false, criteria.isDeleted());
        assertEquals(LocalDateTime.of(2012, Month.DECEMBER, 13, 22, 51, 32), criteria.getCreatedAt());
        assertEquals(LocalDateTime.of(2012, Month.DECEMBER, 13, 22, 51, 32), criteria.getUpdatedAt());
    }
}
