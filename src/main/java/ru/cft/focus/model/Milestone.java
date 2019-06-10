package ru.cft.focus.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Milestone {

    public String title;
    public String state;
    public String description;
    public String due_on;

    public Milestone(String title, String state, String description, String due_on) {
        this.title = title;
        this.state = state;
        this.description = description;
        this.due_on = due_on;
    }

    public Milestone() {
    }
}
