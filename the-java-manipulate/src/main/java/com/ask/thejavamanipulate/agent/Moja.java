package com.ask.thejavamanipulate.agent;

import static net.bytebuddy.matcher.ElementMatchers.named;

import java.io.File;
import java.io.IOException;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;

public class Moja {
  public String pullOut() throws IOException {
    new ByteBuddy().redefine(Moja.class)
        .method(named("pullOut")).intercept(FixedValue.value("Rabbit!"))
        .make().saveIn(new File("/Users/ask/GIT/java-practice/the-java-manipulate/target/classes/"));
    return "";
  }

  public String pullOutAgent() {
    return "";
  }
}
