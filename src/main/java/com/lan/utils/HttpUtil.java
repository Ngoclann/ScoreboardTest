package com.lan.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpUtil {
  private String value;

  public HttpUtil(String value) {
    this.value = value;
  }

  public <T> T toModel(Class<T> tClass) {
    try {
      return new ObjectMapper().readValue(value, tClass);
    } catch (Exception e) {
      Logger.getLogger(HttpUtil.class.getName()).log(Level.SEVERE, null, e);
    }
    return null;
  }

  public static HttpUtil of(BufferedReader reader) {
    StringBuilder sb = new StringBuilder();

    try {
      String line;
      while ((line = reader.readLine()) != null) {
        sb.append(line);
      }
    } catch (IOException e) {
      Logger.getLogger(HttpUtil.class.getName()).log(Level.SEVERE, null, e);
    }
    return new HttpUtil(sb.toString());
  }
}
