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
package org.springframework.social.twitter.api.impl;

import java.math.BigDecimal;
import java.time.ZoneOffset;

import org.springframework.social.twitter.api.AdvertisingObjective;
import org.springframework.social.twitter.api.AdvertisingPlacementType;
import org.springframework.social.twitter.api.AdvertisingSentiment;
import org.springframework.social.twitter.api.LineItem;
import org.springframework.social.twitter.api.LineItemOptimization;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Builder related to {@link LineItem} data that generates a map (key, value)
 * that can be posted into the twitter api endpoint.
 * 
 * @author Hudson Mendes
 */
public class LineItemData extends PostDataBuilder {
	private String campaignId;
	private String currency;
	private AdvertisingPlacementType placementType;
	private AdvertisingObjective objective;
	private AdvertisingSentiment includeSentiment;
	private LineItemOptimization optimization;
	private BigDecimal totalBudgetAmount;
	private BigDecimal bidAmount;
	private BigDecimal suggestedHighCpeBid;
	private BigDecimal suggestedLowCpeBid;
	private Boolean paused;
	private Boolean deleted;
	
	public LineItemData withCampaign(String campaignId) {
		this.campaignId = campaignId;
		return this;
	}
	
	public LineItemData withCurrency(String currency) {
		this.currency = currency;
		return this;
	}
	
	public LineItemData withPlacementType(AdvertisingPlacementType placementType) {
		this.placementType = placementType;
		return this;
	}
	
	public LineItemData withObjective(AdvertisingObjective objective) {
		this.objective = objective;
		return this;
	}
	
	public LineItemData includingSentiment(AdvertisingSentiment sentiment) {
		this.includeSentiment = sentiment;
		return this;
	}
	
	public LineItemData optimizingFor(LineItemOptimization optimization) {
		this.optimization = optimization;
		return this;
	}
	
	public LineItemData withTotalBudget(BigDecimal totalBudgetAmount) {
		this.totalBudgetAmount = totalBudgetAmount;
		return this;
	}
	
	public LineItemData withBidAmount(BigDecimal bidAmount) {
		this.bidAmount = bidAmount;
		return this;
	}
	
	public LineItemData withSuggestedCpeBid(BigDecimal low, BigDecimal high) {
		this.suggestedHighCpeBid = high;
		this.suggestedLowCpeBid = low;
		return this;
	}
	
	public LineItemData paused() { 
		this.paused = true;
		return this;
	}
	
	public LineItemData unpaused() {
		this.paused = false;
		return this;
	}
	
	public LineItemData deleted() { 
		this.deleted = true;
		return this;
	}
	
	public LineItemData active() {
		this.deleted = false;
		return this;
	}

	@Override
	public MultiValueMap<String, Object> toRequestParameters() {
		MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
		
		appendParameter(params, "campaign_id", this.campaignId);
		appendParameter(params, "currency", this.currency);
		
		appendParameter(params, "placement_type", this.placementType);
		appendParameter(params, "objective", this.objective);
		appendParameter(params, "include_sentiment", this.includeSentiment);
		appendParameter(params, "optimization", this.optimization);
		
		appendParameter(params, "total_budget_amount", translateBigDecimalIntoMicro(this.totalBudgetAmount));
		appendParameter(params, "bid_amount", translateBigDecimalIntoMicro(this.bidAmount));
		appendParameter(params, "suggested_high_cpe_bid", translateBigDecimalIntoMicro(this.suggestedHighCpeBid));
		appendParameter(params, "suggested_low_cpe_bid", translateBigDecimalIntoMicro(this.suggestedLowCpeBid));
		
		appendParameter(params, "paused", this.paused);
		appendParameter(params, "deleted", this.deleted);
				
		return params;
	} 
}

