package org.springframework.social.twitter.api.impl;

public enum TwitterApiUriResourceForAdvertising {
    ACCOUNTS("accounts"),
    ACCOUNT("accounts/:account_id"),

    CAMPAIGNS("accounts/:account_id/campaigns"),
    CAMPAIGN("accounts/:account_id/campaigns/:campaign_id"),

    PROMOTABLE_USERS("accounts/:account_id/promotable_users"),
    SPONSORED_TWEETS("accounts/:account_id/scoped_timeline"),
    SPONSORED_TWEETS_CREATE("accounts/:account_id/tweet"),

    PROMOTED_TWEET_REFERENCES("accounts/:account_id/promoted_tweets"),
    PROMOTED_TWEET_REFERENCE("accounts/:account_id/promoted_tweets/:promoted_tweet_id"),

    PROMOTED_USER_REFERENCES("accounts/:account_id/promoted_accounts"),
    PROMOTED_USER_REFERENCE("accounts/:account_id/promoted_accounts/:promoted_account_id"),

    FUNDING_INSTRUMENTS("accounts/:account_id/funding_instruments"),

    LINE_ITEMS("accounts/:account_id/line_items"),
    LINE_ITEM("accounts/:account_id/line_items/:line_item_id"),

    TARGETING_CRITERIAS("accounts/:account_id/targeting_criteria"),
    TARGETING_CRITERIA("accounts/:account_id/targeting_criteria/:targeting_criteria_id"),

    TARGETINGS_DISCOVERY_APP_STORE_CATEGORIES("targeting_criteria/app_store_categories"),
    TARGETINGS_DISCOVERY_BEHAVIOR_TAXONOMIES("targeting_criteria/behavior_taxonomies"),
    TARGETINGS_DISCOVERY_BEHAVIORS("targeting_criteria/behaviors"),
    TARGETINGS_DISCOVERY_DEVICES("targeting_criteria/devices"),
    TARGETINGS_DISCOVERY_INTERESTS("targeting_criteria/interests"),
    TARGETINGS_DISCOVERY_LANGUAGES("targeting_criteria/languages"),
    TARGETINGS_DISCOVERY_LOCATIONS("targeting_criteria/locations"),
    TARGETINGS_DISCOVERY_NETWORK_OPERATORS("targeting_criteria/network_operators"),
    TARGETINGS_DISCOVERY_PLATFORM_VERSIONS("targeting_criteria/platform_versions"),
    TARGETINGS_DISCOVERY_PLATFORMS("targeting_criteria/platforms"),
    TARGETINGS_DISCOVERY_TV_CHANNELS("targeting_criteria/tv_channels"),
    TARGETINGS_DISCOVERY_TV_GENRES("targeting_criteria/tv_genres"),
    TARGETINGS_DISCOVERY_TV_MARKETS("targeting_criteria/tv_markets"),
    TARGETINGS_DISCOVERY_TV_SHOWS("targeting_criteria/tv_shows"),

    TAILORED_AUDIENCES("accounts/:account_id/tailored_audiences"),
    TAILORED_AUDIENCE("accounts/:account_id/tailored_audiences/:tailored_audience_id"),
    TAILORED_AUDIENCE_CHANGES("accounts/:account_id/tailored_audience_changes"),
    TAILORED_AUDIENCE_CHANGE("accounts/:account_id/tailored_audience_changes/:tailored_audience_change_id"),
    GLOBAL_OPT_OUT("accounts/:account_id/tailored_audiences/global_opt_out"),

    STATS_ACCOUNT("stats/accounts/:account_id"),
    STATS_CAMPAIGNS("stats/accounts/:account_id/campaigns"),
    STATS_CAMPAIGN("stats/accounts/:account_id/campaigns/:campaign_id"),
    STATS_FUNDING_INSTRUMENTS("stats/accounts/:account_id/funding_instruments"),
    STATS_FUNDING_INSTRUMENT("stats/accounts/:account_id/funding_instruments/:funding_instrument_id"),
    STATS_LINE_ITEMS("stats/accounts/:account_id/line_items"),
    STATS_LINE_ITEM("stats/accounts/:account_id/line_items/:line_item_id"),
    STATS_PROMOTED_ACCOUNTS("stats/accounts/:account_id/promoted_accounts"),
    STATS_PROMOTED_ACCOUNT("stats/accounts/:account_id/promoted_accounts/:promoted_account_id"),
    STATS_PROMOTED_TWEETS("stats/accounts/:account_id/promoted_tweets"),
    STATS_PROMOTED_TWEET("stats/accounts/:account_id/promoted_tweets/:promoted_tweet_id");

    private final String name;

    TwitterApiUriResourceForAdvertising(String path) {
        name = path;
    }

    public String getPath() {
        return name;
    }
}
