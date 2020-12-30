package com.bandari.util;

import java.util.Map;

public class ThreadLocalCache {

  private static ThreadLocal<Map<String, ? extends CacheObject>> configOrderDTOThread = new ThreadLocal<>();

  public static void set(Map<String, ? extends CacheObject> objectMap) {
    configOrderDTOThread.set(objectMap);
  }

  public static Map<String, ? extends CacheObject> get() {
    return configOrderDTOThread.get();
  }

  //防止内存泄漏
  public static void remove() {
    configOrderDTOThread.remove();
  }
}
