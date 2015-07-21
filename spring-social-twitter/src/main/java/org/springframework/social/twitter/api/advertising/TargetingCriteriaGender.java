package org.springframework.social.twitter.api.advertising;

public enum TargetingCriteriaGender {
    BOTH(null),
    MALE(1),
    FEMALE(2);

    private final Integer value;

    TargetingCriteriaGender(Integer number) {
        this.value = number;
    }

    public Integer value() {
        return value;
    }
}
