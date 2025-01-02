package com.project.ecommerce.model;

import lombok.Getter;
import lombok.Setter;

import java.text.MessageFormat;

@Getter
@Setter
public class Message {

    public static String ERROR_MSG = "'{' \"Status\" : \"{0}\",\"Message\" : \"{1}\"'}'";
    public static String SUCCESS_MSG = "'{' \"Status\" : \"{0}\",\"Message\" : \"{1}\"'}'";
    public static String TOKEN_MSG = "'{' \"Status\" : \"{0}\",\"Token\" : \"{1}\"'}'";

    public Message() {
    }

    public static String getTokenMsg(String token) {
        return MessageFormat.format(TOKEN_MSG, "Success", token);
    }

    public static String getErrorMsg(String error) {
        return MessageFormat.format(ERROR_MSG, "Fail", error);
    }

    public static String getSuccessMsg(String msg){
        return MessageFormat.format(SUCCESS_MSG,"Success", msg);
    }
}
