package org.poetry;

import java.util.List;
import java.util.Collections;

public class Poetry {
  public static void main(String[] args) {
    caeser().forEach(System.out::println);
  }

  public static List<String> caeser() {
    return List.<String>of(
      "O, pardon me, thou bleeding piece of earth,",
      "That i am meek and gentle with these butchers!"
    );
  }
}
