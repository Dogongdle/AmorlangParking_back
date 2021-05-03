package com.parkinglot.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Response {
    private String response;
    private String message;

    public Response(String response, String message) {
        this.response = response;
        this.message = message;
    }

}