package com.ask.thejava8.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 중복 사용할 수 있는 애노테이션을 만들기
 * 중복 사용할 애노테이션 만들기
 * 중복 애노테이션 컨테이너 만들기
 * 컨테이너 애노테이션은 중복 애노테이션과 @Retention 및 @Target 이 같거나 더 넓어야 한다.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE_USE)
public @interface ChickenContainer {
  Chicken[] value();
}

