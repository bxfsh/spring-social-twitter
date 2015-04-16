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
package org.springframework.social.twitter.api.impl.advertising;

import static org.junit.Assert.assertEquals;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON;
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
import java.util.TimeZone;

import org.junit.Test;
import org.springframework.social.twitter.api.advertising.AdvertisingAccount;
import org.springframework.social.twitter.api.advertising.FundingInstrument;
import org.springframework.social.twitter.api.advertising.FundingInstrumentType;
import org.springframework.social.twitter.api.basic.AdvertisingAccountSorting;
import org.springframework.social.twitter.api.basic.ApprovalStatus;
import org.springframework.social.twitter.api.impl.AbstractTwitterApiTest;

/**
 * @author Hudson mendes
 */
public class AdvertisingTemplateTest extends AbstractTwitterApiTest {

	@Test
	public void getAccounts() {
		mockServer
			.expect(requestTo("https://ads-api.twitter.com/0/accounts?with_deleted=true&sort=updated_at"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("ad-accounts"), APPLICATION_JSON));


		List<AdvertisingAccount> accounts = twitter.advertisingOperations().getAccounts(
				new AdvertisingAccountQueryBuilder()
					.includeDeleted(true)
					.sortBy(AdvertisingAccountSorting.updated_at));
		
		assertAdvertisingAccountContents(accounts);
	}

	@Test
	public void getFundingInstruments() throws UnsupportedEncodingException {
		String mockedAccountId = "hkk5";
		String mockedFundingInstrumentId1 = "h2459";
		String mockedFundingInstrumentId2 = "95jll";
		mockServer
			.expect(requestTo(
					"https://ads-api.twitter.com/0/accounts/" + mockedAccountId + "/funding_instruments" +
					"?funding_instrument_ids=" + URLEncoder.encode(mockedFundingInstrumentId1 + "," + mockedFundingInstrumentId2, UTF8) +
					"&with_deleted=false"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("ad-funding-instruments"), APPLICATION_JSON));
	
		List<FundingInstrument> fundingInstruments = twitter.advertisingOperations().getFundingInstruments(
				mockedAccountId,
				new FundingInstrumentQueryBuilder()
					.withFundingInstruments(mockedFundingInstrumentId1, mockedFundingInstrumentId2)
					.includeDeleted(false));
		
		assertFundingInstrumentContents(fundingInstruments);
	}
	
	private void assertAdvertisingAccountContents(List<AdvertisingAccount> accounts) {
		assertEquals(2, accounts.size());
		
		assertEquals("l0l0l0", accounts.get(0).getId());
		assertEquals("h1234jasd", accounts.get(0).getName());
		assertEquals("699169a7693e571000000fef0ef0ef09", accounts.get(0).getSalt());
		assertEquals(ApprovalStatus.ACCEPTED, accounts.get(0).getApprovalStatus());
		assertEquals(TimeZone.getTimeZone("America/Los_Angeles"), accounts.get(0).getTimeZone());
		assertEquals(LocalDateTime.of(2013,Month.MAY,22,07,00,00), accounts.get(0).getTimeZoneSwitchAt());
		assertEquals(LocalDateTime.of(2013,Month.MARCH,05,21,57,11), accounts.get(0).getCreatedAt());
		assertEquals(LocalDateTime.of(2015,Month.FEBRUARY,21,03,15,30), accounts.get(0).getUpdatedAt());
		assertEquals(false, accounts.get(0).isDeleted());
		
		assertEquals("l1l1l1", accounts.get(1).getId());
		assertEquals("test02483", accounts.get(1).getName());
		assertEquals("abababababababababababababababab", accounts.get(1).getSalt());
		assertEquals(ApprovalStatus.ACCEPTED, accounts.get(1).getApprovalStatus());
		assertEquals(TimeZone.getTimeZone("America/Los_Angeles"), accounts.get(1).getTimeZone());
		assertEquals(LocalDateTime.of(2013,Month.JANUARY,01,01,01,01), accounts.get(1).getTimeZoneSwitchAt());
		assertEquals(LocalDateTime.of(2011,Month.JANUARY,01,01,01,01), accounts.get(1).getCreatedAt());
		assertEquals(LocalDateTime.of(2012,Month.JANUARY,01,01,01,01), accounts.get(1).getUpdatedAt());
		assertEquals(false, accounts.get(0).isDeleted());
	}
	
	private void assertFundingInstrumentContents(List<FundingInstrument> fundingInstruments) {
		assertEquals(2, fundingInstruments.size());
		
		assertEquals("hw6ie", fundingInstruments.get(0).getId());
		assertEquals(FundingInstrumentType.CREDIT_CARD, fundingInstruments.get(0).getType());
		assertEquals("hkk5", fundingInstruments.get(0).getAccountId());
		assertEquals("MasterCard ending in 1234", fundingInstruments.get(0).getDescription());
		assertEquals("USD", fundingInstruments.get(0).getCurrency());
		assertEquals(new BigDecimal(1000.00), fundingInstruments.get(0).getCreditLimit());
		assertEquals(null, fundingInstruments.get(0).getCreditRemaining());
		assertEquals(new BigDecimal(100.00), fundingInstruments.get(0).getFundedAmount());
		assertEquals(LocalDateTime.of(2012, Month.NOVEMBER, 8, 02, 31, 46), fundingInstruments.get(0).getStartTime());
		assertEquals(null, fundingInstruments.get(0).getEndTime());
		assertEquals(LocalDateTime.of(2012, Month.NOVEMBER, 8, 02, 31, 46), fundingInstruments.get(0).getCreatedAt());
		assertEquals(LocalDateTime.of(2012, Month.NOVEMBER, 20, 23, 20, 35), fundingInstruments.get(0).getUpdatedAt());
		assertEquals(false, fundingInstruments.get(0).isCancelled());
		assertEquals(true, fundingInstruments.get(0).isDeleted());
		
		assertEquals("i1234", fundingInstruments.get(1).getId());
		assertEquals(FundingInstrumentType.CREDIT_LINE, fundingInstruments.get(1).getType());
		assertEquals("hkk5", fundingInstruments.get(1).getAccountId());
		assertEquals("FakeNike - Credit Line", fundingInstruments.get(1).getDescription());
		assertEquals("GBP", fundingInstruments.get(1).getCurrency());
		assertEquals(new BigDecimal(150000.00).round(MathContext.DECIMAL32), fundingInstruments.get(1).getCreditLimit());
		assertEquals(new BigDecimal(123661.919751).round(MathContext.DECIMAL32), fundingInstruments.get(1).getCreditRemaining());
		assertEquals(new BigDecimal(0.00).round(MathContext.DECIMAL32), fundingInstruments.get(1).getFundedAmount());
		assertEquals(LocalDateTime.of(2013, Month.MAY, 30, 04, 00, 00), fundingInstruments.get(1).getStartTime());
		assertEquals(null, fundingInstruments.get(1).getEndTime());
		assertEquals(LocalDateTime.of(2013, Month.MAY, 30, 18, 16, 38), fundingInstruments.get(1).getCreatedAt());
		assertEquals(LocalDateTime.of(2013, Month.MAY, 30, 18, 16, 38), fundingInstruments.get(1).getUpdatedAt());
		assertEquals(false, fundingInstruments.get(1).isCancelled());
		assertEquals(false, fundingInstruments.get(1).isDeleted());
	}
	
}
