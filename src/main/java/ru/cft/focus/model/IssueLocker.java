package ru.cft.focus.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IssueLocker {

    public Boolean locked;
    public String active_lock_reason;

    public IssueLocker(String reason, Boolean locked) {
        this.locked = locked;
        this.active_lock_reason = reason;
    }

    public IssueLocker() {
    }
}
