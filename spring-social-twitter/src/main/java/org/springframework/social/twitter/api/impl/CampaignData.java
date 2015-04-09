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
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.social.twitter.api.Campaign;
import org.springframework.social.twitter.api.ReasonNotServable;
import org.springframework.social.twitter.api.TransferingData;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Builder related to {@link Campaign} data that generates a map (key, value)
 * that can be posted into the twitter api endpoint.
 * 
 * @author Hudson Mendes
 */
public class CampaignData extends PostDataBuilder {
	private String name;
	private String currency;
	private String fundingInstrumentId;
	private BigDecimal totalBudget;
	private BigDecimal dailyBudget;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private final List<ReasonNotServable> reasonsNotServable;
	private Boolean standardDelivery = true;
	private Boolean paused = false;
	private Boolean deleted = false;

	public CampaignData() {
		this.reasonsNotServable = new ArrayList<ReasonNotServable>();
	}

	public CampaignData withName(String name) {
		this.name = name;
		return this;
	}

	public CampaignData withCurrency(String currency) {
		this.currency = currency;
		return this;
	}

	public CampaignData withFundingInstrument(String fundingInstrumentId) {
		this.fundingInstrumentId = fundingInstrumentId;
		return this;
	}

	public CampaignData withBudget(BigDecimal totalBudget, BigDecimal dailyBudget) {
		this.totalBudget = totalBudget;
		this.dailyBudget = dailyBudget;
		return this;
	}
	
	public CampaignData activeUntil(LocalDateTime endTime) {
		return activeBetween(null, endTime);
	}
	
	public CampaignData activeFrom(LocalDateTime startTime) {
		return activeBetween(startTime, null);
	}

	public CampaignData activeBetween(LocalDateTime startTime, LocalDateTime endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
		return this;
	}

	public CampaignData thatCantBeServedDueTo(ReasonNotServable... reasons) {
		if (reasons != null)
			Arrays.stream(reasons).forEach(reason -> {
				this.reasonsNotServable.add(reason);
			});
		return this;
	}
	
	public CampaignData withStandardDelivery(Boolean standardDelivery) {
		this.standardDelivery = standardDelivery;
		return this;
	}
	
	public CampaignData paused() {
		this.paused = true;
		return this;
	}
	
	public CampaignData unpaused() {
		this.paused = false;
		return this;
	}
	
	public CampaignData deleted() {
		this.deleted = true;
		return this;
	}
	
	public CampaignData active() {
		this.deleted = false;
		return this;
	}
	
	@Override
	public MultiValueMap<String, Object> toRequestParameters() {
		MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
		
		appendParameter(params, "name", this.name);
		appendParameter(params, "currency", this.currency);
		appendParameter(params, "funding_instrument_id", this.fundingInstrumentId);
		
		appendParameter(params, "total_budget_amount_local_micro", translateBigDecimalIntoMicro(this.totalBudget));
		appendParameter(params, "daily_budget_amount_local_micro", translateBigDecimalIntoMicro(this.dailyBudget));
		
		if (this.startTime != null) appendParameter(params, "start_time", this.startTime.toInstant(ZoneOffset.UTC));
		if (this.endTime != null) appendParameter(params, "end_time", this.endTime.toInstant(ZoneOffset.UTC));
		
		appendParameter(params, "reasons_not_servable", this.reasonsNotServable);
		appendParameter(params, "standard_delivery", this.standardDelivery);
		appendParameter(params, "paused", this.paused);
		appendParameter(params, "deleted", this.deleted);
				
		return params;
	}
	
}
