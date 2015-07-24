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

import org.springframework.social.twitter.api.impl.TwitterObjectMixin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class TargetingCriteriaDiscoveryForPlatformVersionsMixin extends TwitterObjectMixin {

    @JsonCreator
    TargetingCriteriaDiscoveryForPlatformVersionsMixin(
            @JsonProperty("name") String name,
            @JsonProperty("number") String number,
            @JsonProperty("platform") String platform,
            @JsonProperty("targeting_type") String targetingType,
            @JsonProperty("targeting_value") String targetingValue) {}

}