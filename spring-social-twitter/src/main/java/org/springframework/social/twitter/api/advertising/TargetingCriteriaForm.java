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
package org.springframework.social.twitter.api.advertising;

/**
 * Allow setting then entire set of {@link TargetingCriterion} for
 * a particular {@link LineItem} all at once
 *
 * @author Hudson Mendes
 */
public interface TargetingCriteriaForm {

    public TargetingCriteriaForm forLineItem(String lineItem);

    public TargetingCriteriaForm withBroadKeywords(String... keywords);

    public TargetingCriteriaForm withExactKeywords(String... keywords);

    public TargetingCriteriaForm withUnorderedKeywords(String... keywords);

    public TargetingCriteriaForm withPhraseKeywords(String... keywords);

    public TargetingCriteriaForm withNegativeExactKeywords(String... keywords);

    public TargetingCriteriaForm withNegativeUnorderedKeywords(String... keywords);

    public TargetingCriteriaForm withNegativePhraseKeywords(String... keywords);

    public TargetingCriteriaForm withLocations(String... locations);

    public TargetingCriteriaForm withInterests(String... interests);

    public TargetingCriteriaForm withGender(TargetingCriteriaGender gender);

    public TargetingCriteriaForm withAgeBuckets(TargetingCriteriaAgeBucket... ageBuckets);

    public TargetingCriteriaForm followersOfUsers(String... followedUserIds);

    public TargetingCriteriaForm similarToUsers(String... similiarUserIds);

    public TargetingCriteriaForm usingPlatforms(String... platforms);

    public TargetingCriteriaForm usingPlatformVersions(String... versions);

    public TargetingCriteriaForm usingDevices(String... devices);

    public TargetingCriteriaForm onWifiOnly(Boolean wifiOnly);
}
