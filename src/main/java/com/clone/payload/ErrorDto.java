package com.clone.payload;

import java.util.Date;
import lombok.Getter;

@Getter
public class ErrorDto {
    private String message;
    private Date date;
    private String uri;

    public ErrorDto(String message, Date date, String uri) {
        this.message = message;
        this.date = date;
        this.uri = uri;
    }
}
