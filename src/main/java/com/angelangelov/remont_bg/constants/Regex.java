package com.angelangelov.remont_bg.constants;

public  class Regex {

    public static final String SIMPLE_TEXT_REGEX = "^[a-zA-Z0-9\\s+\\,\\.\\!\\\"-_]+$";
    public static final String EMAIL_REGEX = "^\\S+@\\S+\\.\\S+$";
    public static final String FIRST_NAME_REGEX = "^[a-zA-Z]+$";
    public static final String LAST_NAME_REGEX = "^[a-zA-Z0-9]+$";
    public static final String PHONE_REGEX = "^[0-9+]+{1,20}$";

}
