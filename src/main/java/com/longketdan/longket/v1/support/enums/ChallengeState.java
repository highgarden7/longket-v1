package com.longketdan.longket.v1.support.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ChallengeState {
    @JsonProperty("OPEN") OPEN("OPEN", "진행중"),
    @JsonProperty("ENDED") ENDED("ENDED", "마감");


    private final String state;
    private final String literal;

    ChallengeState(String state, String literal) {
        this.state = state;
        this.literal = literal;
    }

    public String getState() {
        return this.state;
    }

    public String getLiteral() {
        return this.literal;
    }
}
