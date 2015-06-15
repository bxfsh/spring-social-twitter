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

import org.springframework.social.twitter.api.advertising.TargetingCriteria;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaForm;
import org.springframework.social.twitter.api.advertising.TargetingType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Facilitate the creation of the request body for post and put
 * requests made to the management of {@link TargetingCriteria} data.
 * 
 * @author Hudson Mendes
 */
public class TargetingCriteriaFormBuilder extends AbstractTwitterFormBuilder implements TargetingCriteriaForm {
    private String lineItemId;
    private String name;
    private String targetingType;
    private String targetingValue;
    private Boolean deleted;

    /* (non-Javadoc)
     * @see org.springframework.social.twitter.api.impl.advertising.TargetingCriteriaForm#withLineItem(java.lang.String)
     */
    @Override
    public TargetingCriteriaForm withLineItem(String lineItemId) {
        this.lineItemId = lineItemId;
        return this;
    }

    /* (non-Javadoc)
     * @see org.springframework.social.twitter.api.impl.advertising.TargetingCriteriaForm#withName(java.lang.String)
     */
    @Override
    public TargetingCriteriaForm withName(String name) {
        this.name = name;
        return this;
    }

    /* (non-Javadoc)
     * @see org.springframework.social.twitter.api.impl.advertising.TargetingCriteriaForm#targeting(java.lang.String, java.lang.String)
     */
    @Override
    public TargetingCriteriaForm targeting(String targetingType, String targetingValue) {
        this.targetingType = targetingType;
        this.targetingValue = targetingValue;
        return this;
    }

    /* (non-Javadoc)
     * @see org.springframework.social.twitter.api.impl.advertising.TargetingCriteriaForm#targeting(org.springframework.social.twitter.api.advertising.TargetingType, java.lang.String)
     */
    @Override
    public TargetingCriteriaForm targeting(TargetingType targetingType, String targetingValue) {
        return targeting(targetingType.toString(), targetingValue);
    }

    /* (non-Javadoc)
     * @see org.springframework.social.twitter.api.impl.advertising.TargetingCriteriaForm#active()
     */
    @Override
    public TargetingCriteriaFormBuilder active() {
        this.deleted = false;
        return this;
    }

    /* (non-Javadoc)
     * @see org.springframework.social.twitter.api.impl.advertising.TargetingCriteriaForm#deleted()
     */
    @Override
    public TargetingCriteriaForm deleted() {
        this.deleted = true;
        return this;
    }

    @Override
    public MultiValueMap<String, String> toRequestBody() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();

        appendParameter(params, "line_item_id", this.lineItemId);
        appendParameter(params, "name", this.name);
        appendParameter(params, "targeting_type", this.targetingType);
        appendParameter(params, "targeting_value", this.targetingValue);
        appendParameter(params, "deleted", this.deleted);

        return params;
    }

}
