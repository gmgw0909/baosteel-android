package com.meetutech.baosteel.http.api;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.http.api
// Author: culm at 2017-04-26
//*********************************************************

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.meetutech.baosteel.common.AppConstant;
import com.meetutech.baosteel.common.HTTPConstant;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class RestClient {

  private ApiService apiService;

  public RestClient() {
    //Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
    GsonBuilder gson = new GsonBuilder();
    gson.registerTypeAdapter(Date.class, new DateTypeDeserializer());

    RestAdapter adapter = new RestAdapter.Builder().setLogLevel(
        AppConstant.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
        .setEndpoint(HTTPConstant.API_HOST).setConverter(new GsonConverter(gson.create()))
        .build();
    apiService = adapter.create(ApiService.class);
  }

  public ApiService getApiService() {
    return apiService;
  }

  public static class DateTypeDeserializer implements JsonDeserializer<Date> {
    private static final String[] DATE_FORMATS = new String[]{
        "yyyy-MM-dd'T'HH:mm:ssZ",
        "yyyy-MM-dd'T'HH:mm:ss",
        "yyyy-MM-dd",
        "EEE MMM dd HH:mm:ss z yyyy",
        "HH:mm:ss",
        "MM/dd/yyyy HH:mm:ss aaa",
        "yyyy-MM-dd'T'HH:mm:ss.SSSSSS",
        "yyyy-MM-dd'T'HH:mm:ss.SSSSSSS",
        "yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'",
        "MMM d',' yyyy H:mm:ss a"
    };

    @Override
    public Date deserialize(JsonElement jsonElement, Type typeOF, JsonDeserializationContext context) throws
        JsonParseException {
      for (String format : DATE_FORMATS) {
        try {
          return new Date(new SimpleDateFormat(format, Locale.CHINA).parse(jsonElement.getAsString()).getTime()+8*60*60*1000);
        } catch (ParseException e) {
        }
      }
      throw new JsonParseException("Unparseable date: \"" + jsonElement.getAsString()
          + "\". Supported formats: \n" + Arrays.toString(DATE_FORMATS));
    }
  }

}

