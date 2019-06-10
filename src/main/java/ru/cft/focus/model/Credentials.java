package ru.cft.focus.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Credentials {

    public String user;
    public String password;

    public Credentials(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public Credentials() {
    }
}
