package com.meetutech.baosteel.common;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.common
// Author: culm at 2017-04-26
//*********************************************************

public class HTTPConstant {


//    public static final String API_HOST = "https://lab.baosteelf.com";
//    public static final String STATIC_FILES_HOST = "https://staticlab.baosteelf.com";

//    public static final String API_HOST = "http://192.168.75.131:3000";
//    public static final String STATIC_FILES_HOST = "http://192.168.75.131:3000";

    public static final String API_HOST = "http://192.168.0.201:8889";
    public static final String STATIC_FILES_HOST = "http://192.168.0.201:8889";

    public static final String SUB_INFO_URL = "/info/html";
    public static final String FMT_STATIC_HLS_STREAM_URL = API_HOST + "/hls/%s.m3u8";
    //HTTP Params
    public static final String HEADER_E_TAG = "ETag";
    public static final String HEADER_IF_NONE_MATCH = "If-None-Match";

    public static String getStaticFileFullPath(String url) {
        return STATIC_FILES_HOST + url;
    }
}
