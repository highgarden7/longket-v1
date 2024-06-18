package com.longketdan.longket.v1.support.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ApproveStatus {
    @JsonProperty("WAIT") WAIT("WAIT", "대기"),
    @JsonProperty("APPROVED") APPROVED("APPROVED", "승인"),
    @JsonProperty("REJECTED") REJECTED("REJECTED", "거절");


    private final String status;
    private final String literal;

    ApproveStatus(String status, String literal) {
        this.status = status;
        this.literal = literal;
    }

    public String status() {
        return this.status;
    }

    public String getLiteral() {
        return this.literal;
    }

}
