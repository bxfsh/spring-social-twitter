package org.springframework.social.twitter.api.impl;

import org.springframework.util.StringUtils;

/**
 * Decouples host definition so that test diversions become possible.
 * 
 * @author Hudson Mendes
 *
 */
public abstract class TwitterApiHosts {

    private static final String DEFAULT_STANDARD_API_URL_BASE = "https://api.twitter.com/1.1/";
    private static final String DEFAULT_ADS_API_URL_BASE = "https://ads-api.twitter.com/0/";

    private static String hostForAdsApi = DEFAULT_ADS_API_URL_BASE;
    private static String hostForStandardApi = DEFAULT_STANDARD_API_URL_BASE;

    public static String getAdsApi() {
        return hostForAdsApi;
    }

    public static void setAdsApiHost(String host) {
        if (StringUtils.hasText(host))
            hostForAdsApi = host;
    }

    public static String getStandardApi() {
        return hostForStandardApi;
    }

    public static void setStandardApiHost(String host) {
        if (StringUtils.hasText(host))
            hostForStandardApi = host;
    }

}
