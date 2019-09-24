package com.kojotdev.blog.webfluxrsocketexample.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {

    public final String message;

    @JsonCreator
    public Message(@JsonProperty("message") String message) {
        this.message = message;
    }
}
