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

import java.util.ArrayList;
import java.util.List;

import org.springframework.social.twitter.api.advertising.Campaign;
import org.springframework.social.twitter.api.advertising.CampaignQuery;
import org.springframework.social.twitter.api.advertising.CampaignSorting;
import org.springframework.util.MultiValueMap;

/**
 * Facilitates the creation of the query that will be
 * used to filter results from the {@link Campaign} endpoint.
 * 
 * @author Hudson Mendes
 */
public class CampaignQueryBuilder
        extends AbstractTwitterQueryForSortableEntityBuilder<CampaignQuery, CampaignSorting>
        implements CampaignQuery {

    private List<String> fundingInstrumentIds;
    private List<String> campaignIds;
    private Integer count;
    private Integer cursor;

    @Override
    public CampaignQuery withCampaigns(String... campaignIds) {
        this.campaignIds = new ArrayList<String>();
        for (int i = 0; i < campaignIds.length; i++)
            this.campaignIds.add(campaignIds[i]);
        return this;
    }

    @Override
    public CampaignQuery withFundingInstruments(String... fundingInstrumentIds) {
        this.fundingInstrumentIds = new ArrayList<String>();
        for (int i = 0; i < fundingInstrumentIds.length; i++)
            this.fundingInstrumentIds.add(fundingInstrumentIds[i]);
        return this;
    }

    @Override
    public CampaignQuery withCount(Integer count) {
        this.count = count;
        return this;
    }

    @Override
    public CampaignQuery withCursor(Integer cursor) {
        this.cursor = cursor;
        return this;
    }

    @Override
    protected void makeParameters(MultiValueMap<String, String> map) {
        appendParameter(map, "campaign_ids", this.campaignIds);
        appendParameter(map, "funding_instrument_ids", this.fundingInstrumentIds);
        appendParameter(map, "count", this.count);
        appendParameter(map, "cursor", this.cursor);
    }
}
