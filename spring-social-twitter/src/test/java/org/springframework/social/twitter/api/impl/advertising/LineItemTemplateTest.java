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
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import org.junit.Test;
import org.springframework.social.twitter.api.advertising.AdvertisingObjective;
import org.springframework.social.twitter.api.advertising.AdvertisingPlacementType;
import org.springframework.social.twitter.api.advertising.AdvertisingSentiment;
import org.springframework.social.twitter.api.advertising.BidUnit;
import org.springframework.social.twitter.api.advertising.LineItem;
import org.springframework.social.twitter.api.advertising.LineItemOptimization;
import org.springframework.social.twitter.api.advertising.ProductType;
import org.springframework.social.twitter.api.impl.AbstractTwitterApiTest;

/**
 * @author Hudson mendes
 */
public class LineItemTemplateTest extends AbstractTwitterApiTest {

    @Test
    public void getLineItem() {
        String mockedAccountId = "hkk5";
        String mockedLineItem = "5woz";
        mockServer
                .expect(requestTo(
                        "https://ads-api.twitter.com/0/accounts/" + mockedAccountId + "/line_items" +
                                "/" + mockedLineItem +
                                "?with_deleted=true"))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("line-items-single"), APPLICATION_JSON));

        LineItem lineItem = twitter.lineItemOperations().getLineItem(mockedAccountId, mockedLineItem);
        assertSingleLineItemContents(lineItem);
    }

    @Test
    public void getLineItems() throws UnsupportedEncodingException {
        String mockedAccountId = "hkk5";
        String mockedCampaignId1 = "x945j";
        String mockedCampaignId2 = "1jvrj";
        String mockedFundingInstrumentId1 = "jrtjh4";
        String mockedFundingInstrumentId2 = "ekffd";
        String mockedLineItemId1 = "z9j";
        String mockedLineItemId2 = "045k";
        mockServer
                .expect(requestTo(
                        "https://ads-api.twitter.com/0/accounts/" + mockedAccountId + "/line_items" +
                                "?campaign_ids=" + URLEncoder.encode(mockedCampaignId1 + "," + mockedCampaignId2, UTF8) +
                                "&funding_instrument_ids=" + URLEncoder.encode(mockedFundingInstrumentId1 + "," + mockedFundingInstrumentId2, UTF8) +
                                "&line_item_ids=" + URLEncoder.encode(mockedLineItemId1 + "," + mockedLineItemId2, UTF8) +
                                "&with_deleted=false"))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("line-items"), APPLICATION_JSON));

        List<LineItem> lineItems = twitter.lineItemOperations().getLineItems(
                mockedAccountId,
                new LineItemQueryBuilder()
                        .withCampaigns(mockedCampaignId1, mockedCampaignId2)
                        .withFundingInstruments(mockedFundingInstrumentId1, mockedFundingInstrumentId2)
                        .withLineItems(mockedLineItemId1, mockedLineItemId2)
                        .includeDeleted(false));

        assertLineItemContents(lineItems);
    }

    @Test
    public void createLineItem() {
        String mockedAccountId = "hkk5";
        String doesntMatterString = "doesn-matter-altered";
        BigDecimal doesntMatterDecimal = new BigDecimal(1.00);
        Boolean doesntMatterBool = false;

        String chainedPostContent =
                "campaign_id=" + doesntMatterString + "&" +
                        "currency=" + doesntMatterString + "&" +
                        "placement_type=" + AdvertisingPlacementType.PROMOTED_TWEETS_FOR_SEARCH + "&" +
                        "objective=" + AdvertisingObjective.APP_INSTALLS + "&" +
                        "include_sentiment=" + AdvertisingSentiment.POSITIVE_ONLY + "&" +
                        "optimization=" + LineItemOptimization.WEBSITE_CONVERSIONS + "&" +
                        "total_budget_amount_local_micro=" + doesntMatterDecimal.multiply(new BigDecimal(1000000L)) + "&" +
                        "bid_amount_local_micro=" + doesntMatterDecimal.multiply(new BigDecimal(1000000L)) + "&" +
                        "suggested_high_cpe_bid_local_micro=" + doesntMatterDecimal.add(new BigDecimal(10)).multiply(new BigDecimal(1000000L)) + "&" +
                        "suggested_low_cpe_bid_local_micro=" + doesntMatterDecimal.multiply(new BigDecimal(1000000L)) + "&" +
                        "paused=" + !doesntMatterBool + "&" +
                        "deleted=" + doesntMatterBool;

        mockServer
                .expect(requestTo("https://ads-api.twitter.com/0/accounts/" + mockedAccountId + "/line_items"))
                .andExpect(method(POST))
                .andExpect(content().string(chainedPostContent))
                .andRespond(withSuccess(jsonResource("line-items-single"), APPLICATION_JSON));

        LineItem lineItem = twitter.lineItemOperations().createLineItem(
                mockedAccountId,
                new LineItemFormBuilder()
                        .withCampaign(doesntMatterString)
                        .withCurrency(doesntMatterString)
                        .withTotalBudget(doesntMatterDecimal.toString())
                        .withBidAmount(doesntMatterDecimal.toString())
                        .withSuggestedCpeBid(doesntMatterDecimal.toString(), doesntMatterDecimal.add(new BigDecimal(10)).toString())
                        .withPlacementType(AdvertisingPlacementType.PROMOTED_TWEETS_FOR_SEARCH)
                        .withObjective(AdvertisingObjective.APP_INSTALLS)
                        .optimizingFor(LineItemOptimization.WEBSITE_CONVERSIONS)
                        .includingSentiment(AdvertisingSentiment.POSITIVE_ONLY)
                        .paused()
                        .active());

        assertSingleLineItemContents(lineItem);
    }

    @Test
    public void updateLineItem() {
        String mockedAccountId = "hkk5";
        String mockedLineItemId = "l13r";
        String doesntMatterString = "doesn-matter-altered";
        BigDecimal doesntMatterDecimal = new BigDecimal(2.00);
        Boolean doesntMatterBool = true;

        String chainedPostContent =
                "campaign_id=" + doesntMatterString + "&" +
                        "currency=" + doesntMatterString + "&" +
                        "placement_type=" + AdvertisingPlacementType.PROMOTED_TWEETS_FOR_TIMELINES + "&" +
                        "objective=" + AdvertisingObjective.FOLLOWERS + "&" +
                        "include_sentiment=" + AdvertisingSentiment.ALL + "&" +
                        "optimization=" + LineItemOptimization.DEFAULT + "&" +
                        "total_budget_amount_local_micro=" + doesntMatterDecimal.multiply(new BigDecimal(1000000L)) + "&" +
                        "bid_amount_local_micro=" + doesntMatterDecimal.multiply(new BigDecimal(1000000L)) + "&" +
                        "suggested_high_cpe_bid_local_micro=" + doesntMatterDecimal.add(new BigDecimal(10)).multiply(new BigDecimal(1000000L)) + "&" +
                        "suggested_low_cpe_bid_local_micro=" + doesntMatterDecimal.multiply(new BigDecimal(1000000L)) + "&" +
                        "paused=" + !doesntMatterBool + "&" +
                        "deleted=" + doesntMatterBool;

        mockServer
                .expect(requestTo("https://ads-api.twitter.com/0/accounts/" + mockedAccountId + "/line_items/" + mockedLineItemId))
                .andExpect(method(PUT))
                .andExpect(content().string(chainedPostContent))
                .andRespond(withSuccess(jsonResource("line-items-single"), APPLICATION_JSON));

        twitter.lineItemOperations().updateLineItem(
                mockedAccountId,
                mockedLineItemId,
                new LineItemFormBuilder()
                        .withCampaign(doesntMatterString)
                        .withCurrency(doesntMatterString)
                        .withTotalBudget(doesntMatterDecimal.toString())
                        .withBidAmount(doesntMatterDecimal.toString())
                        .withSuggestedCpeBid(doesntMatterDecimal.toString(), doesntMatterDecimal.add(new BigDecimal(10)).toString())
                        .withPlacementType(AdvertisingPlacementType.PROMOTED_TWEETS_FOR_TIMELINES)
                        .withObjective(AdvertisingObjective.FOLLOWERS)
                        .optimizingFor(LineItemOptimization.DEFAULT)
                        .includingSentiment(AdvertisingSentiment.ALL)
                        .unpaused()
                        .deleted());
    }

    @Test
    public void deleteLineItem() {
        String mockedAccountId = "0ga0yn";
        String mockedLineItemId = "92ph";
        mockServer
                .expect(requestTo("https://ads-api.twitter.com/0/accounts/" + mockedAccountId + "/line_items/" + mockedLineItemId))
                .andExpect(method(DELETE))
                .andRespond(withSuccess());

        twitter.lineItemOperations().deleteLineItem(mockedAccountId, mockedLineItemId);
    }

    private void assertLineItemContents(List<LineItem> lineItems) {
        assertEquals(22, lineItems.size());

        assertEquals("awvv", lineItems.get(0).getId());
        assertEquals("gq0vqj", lineItems.get(0).getAccountId());
        assertEquals("e094", lineItems.get(0).getCampaignId());
        assertEquals(ProductType.PROMOTED_TWEETS, lineItems.get(0).getProductType());
        assertEquals(BidUnit.APP_CLICK, lineItems.get(0).getBidUnit());
        assertEquals(LineItemOptimization.DEFAULT, lineItems.get(0).getOptimization());
        assertEquals(AdvertisingObjective.APP_ENGAGEMENTS, lineItems.get(0).getObjective());
        assertEquals(AdvertisingSentiment.POSITIVE_ONLY, lineItems.get(0).getIncludeSentiment());
        assertEquals(AdvertisingPlacementType.PROMOTED_TWEETS, lineItems.get(0).getPlacementType());
        assertEquals("USD", lineItems.get(0).getCurrency());
        assertEquals(null, lineItems.get(0).getTotalBudgetAmount());
        assertEquals(new BigDecimal("10"), lineItems.get(0).getBidAmount());
        assertEquals(null, lineItems.get(0).getSuggestedHighCpeBid());
        assertEquals(null, lineItems.get(0).getSuggestedLowCpeBid());
        assertEquals(false, lineItems.get(0).isAutomaticallySelectBid());
        assertEquals(true, lineItems.get(0).isPaused());
        assertEquals(false, lineItems.get(0).isDeleted());
        assertEquals(LocalDateTime.of(2015, Month.MAY, 29, 20, 32, 20), lineItems.get(0).getCreatedAt());
        assertEquals(LocalDateTime.of(2015, Month.MAY, 29, 20, 38, 43), lineItems.get(0).getUpdatedAt());
    }

    private void assertSingleLineItemContents(LineItem lineItem) {
        assertEquals("69ob", lineItem.getId());
        assertEquals("hkk5", lineItem.getAccountId());
        assertEquals("7wdy", lineItem.getCampaignId());
        assertEquals("GBP", lineItem.getCurrency());
        assertEquals(AdvertisingPlacementType.PROMOTED_ACCOUNT, lineItem.getPlacementType());
        assertEquals(AdvertisingObjective.FOLLOWERS, lineItem.getObjective());
        assertEquals(null, lineItem.getIncludeSentiment());
        assertEquals(LineItemOptimization.DEFAULT, lineItem.getOptimization());
        assertEquals(null, lineItem.getTotalBudgetAmount());
        assertEquals(new BigDecimal(1.75).round(MathContext.DECIMAL32), lineItem.getBidAmount());
        assertEquals(null, lineItem.getSuggestedHighCpeBid());
        assertEquals(null, lineItem.getSuggestedLowCpeBid());
        assertEquals(false, lineItem.isPaused());
        assertEquals(false, lineItem.isDeleted());
        assertEquals(LocalDateTime.of(2012, Month.NOVEMBER, 30, 22, 58, 11), lineItem.getCreatedAt());
        assertEquals(LocalDateTime.of(2012, Month.NOVEMBER, 30, 22, 58, 11), lineItem.getUpdatedAt());
    }
}
