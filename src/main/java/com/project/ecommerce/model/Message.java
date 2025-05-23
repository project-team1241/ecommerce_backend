package com.project.ecommerce.model;

import lombok.Getter;
import lombok.Setter;

import java.text.MessageFormat;

@Getter
@Setter
public class Message {

    public static final String ERROR_MSG = "'{' \"Status\" : \"{0}\",\"Message\" : \"{1}\"'}'";
    public static final String SUCCESS_MSG = "'{' \"Status\" : \"{0}\",\"Message\" : \"{1}\"'}'";
    public static final String TOKEN_MSG = "'{' \"Status\" : \"{0}\",\"Token\" : \"{1}\"'}'";
    public static final String TOKEN_MSG_USER = "'{' \"Status\" : \"{0}\",\"Token\" : \"{1}\",\"UserRole\" : \"{2}\"'}'";

    private Message() { // Ignored
    }

    public static String getTokenMsg(String token) {
        return MessageFormat.format(TOKEN_MSG, "Success", token);
    }

    public static String getTokenMsg(String token,String userRole) {
        return MessageFormat.format(TOKEN_MSG_USER, "Success", token, userRole);
    }

    public static String getErrorMsg(String error) {
        return MessageFormat.format(ERROR_MSG, "Fail", error);
    }

    public static String getSuccessMsg(String msg){
        return MessageFormat.format(SUCCESS_MSG,"Success", msg);
    }
}
