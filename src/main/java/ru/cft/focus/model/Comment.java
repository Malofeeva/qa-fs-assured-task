package ru.cft.focus.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Comment {

    public String body;

    public Comment(String body) {
        this.body = body;
    }

    public Comment() {
    }
}
