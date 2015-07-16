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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.social.twitter.api.TwitterObject;


/**
 * Data discovery for targeting criterias based on events.
 * 
 * @author Hudson Mendes
 */
public class TargetingCriteriaDiscoveryForEvents extends TwitterObject {
    private final Map<String, Long> reach = new HashMap<String, Long>();
    private final String name;
    private final LocalDateTime startTime;
    private final List<String> topUsers = new ArrayList<String>();
    private final List<String> topHashTags = new ArrayList<String>();
    private final Map<String, Float> genderBreakdownPercentage = new HashMap<String, Float>();

    private final List<String> countryCodes;
    private final LocalDateTime endTime;
    
    public TargetingCriteriaDiscoveryForEvents(
    		final Map<String, Long> reach,
    	    final String name,
    	    final LocalDateTime startTime,
    	    final List<String> topUsers,
    	    final List<String> topHashTags,
    	    final Map<String, Float> genderBreakdownPercentage,
    	    
    	    final List<String> countryCodes,
    	    final LocalDateTime endTime) {

        this.reach.putAll(reach);
        this.name = name;
        this.startTime = startTime;
        this.topUsers.addAll(topUsers);
        this.topHashTags.addAll(topHashTags);
        this.genderBreakdownPercentage.putAll(genderBreakdownPercentage);
        
        this.countryCodes = countryCodes;
        this.endTime = endTime;
    }


    
}
