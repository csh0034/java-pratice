package com.ask.thejavamanipulate.di;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class ContainerService {

  public static <T> T getObject(Class<T> classType) {
    T instance = createInstance(classType);

    Arrays.stream(classType.getDeclaredFields()).forEach(f -> {
      if (f.getAnnotation(Inject.class) != null) {
        Object filedInstance = createInstance(f.getType());
        f.setAccessible(true);

        try {
          f.set(instance, filedInstance);
        } catch (IllegalAccessException e) {
          throw new RuntimeException(e);
        }
      }
    });

    return instance;
  }

  private static <T> T createInstance(Class<T> classType) {
    try {
      return classType.getConstructor().newInstance();
    } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

}
