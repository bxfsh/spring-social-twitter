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

import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.impl.DataListHolder;

/**
 * Interface defining the operations for promoted tweets (Ads API).
 * 
 * @author Hudson Mendes
 */
public interface PromotionOperations {

    /**
     * Retrieves a of {@link PromotableUser}.
     * 
     * @param accountId identifies the account for which we are trying to promote users.
     * @param query are the parameters used to query the user
     * @return
     */
    DataListHolder<PromotableUser> getPromotableUsers(String accountId, PromotableUserQuery query);


    /**
     * Retrieves a sequence of of promoted-only {@link Tweet}
     * 
     * @param accountId identifies the account for which we want to retrieve promoted-only tweets.
     * @param query are the parameters for which we want to retrieve tweets.
     * @return a cursored list of {@link Tweet}.
     */
    DataListHolder<Tweet> getPromotedOnlyTweets(String accountId, PromotedOnlyTweetQuery query);

    /**
     * Creates a {@link Tweet} that is a promoted-only tweet.
     * 
     * @param accountId identifies the account for which the promoted-only tweet will be created.
     * @param input is the data of the tweet.
     * @return the {@link Tweet} that has been created.
     */
    Tweet createPromotedOnlyTweet(String accountId, PromotedOnlyTweetForm input);
}