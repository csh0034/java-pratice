package com.ask.thejava8.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TYPE_PARAMETER: 타입 변수에만 사용할 수 있다.
 * TYPE_USE: 타입 변수를 포함해서 모든 타입 선언부에 사용할 수 있다.
 * 타입 선언 부
 * - 제네릭 타입
 * - 변수 타입
 * - 매개변수 타입
 * - 예외 타입
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE_USE)
// @Target(ElementType.TYPE_PARAMETER)
@Repeatable(ChickenContainer.class)
public @interface Chicken {
  String value() default "";
}
