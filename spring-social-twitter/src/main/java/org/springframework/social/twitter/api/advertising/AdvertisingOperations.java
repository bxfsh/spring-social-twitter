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
package org.springframework.social.twitter.api.advertising;

import java.util.List;

import org.springframework.social.ApiException;
import org.springframework.social.MissingAuthorizationException;

/**
 * Interface defining the operations for advertising operations.
 * @author Hudson Mendes
 */
public interface AdvertisingOperations {
	
	/**
	 * Retrieves a list of all {@link AdvertisingAccount} to which the authenticating user has access.  
	 * @return a list of {@link AdvertisingAccount}
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	List<AdvertisingAccount> getAccounts(AdvertisingAccountQuery query);
	
	/**
	 * Retrieves a list of all {@link FundingInstrument} linked to a particular {@link AdvertisingAccount}.
	 * @param accountId identifies the account for which we want to get the funding instruments.  
	 * @return a list of {@link FundingInstrument}
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	List<FundingInstrument> getFundingInstruments(String accountId);
	
}
