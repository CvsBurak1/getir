package org.mobile.getir.selector;

public class SelectorFactory {

  private SelectorFactory() {

  }

  public static Selector createElementHelper() {
    return new AndroidSelector();
  }
}
