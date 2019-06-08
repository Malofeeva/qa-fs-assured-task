package ru.cft.focus.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Issue {

    public String title;
    public String body;
    public List<String> assignees;
    public Integer milestone;
    public List<String> labels;

    public Issue(String title, String body, List<String> assignees, Integer milestone, List<String> labels) {
        this.title = title;
        this.body = body;
        this.assignees = assignees;
        this.milestone = milestone;
        this.labels = labels;
    }

    public Issue() {
    }
}
