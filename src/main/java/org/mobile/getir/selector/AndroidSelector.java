package org.mobile.getir.selector;

import org.mobile.getir.model.ElementInfo;
import io.appium.java_client.MobileBy;
import org.openqa.selenium.By;

public class AndroidSelector implements Selector {

  @Override
  public By getElementInfoToBy(ElementInfo elementInfo) {
    By by = null;
    switch (elementInfo.getAndroidType()) {
      case "css":
        by = By.cssSelector(elementInfo.getAndroidValue());
        break;
      case "id":
        by = By.id(elementInfo.getAndroidValue());
        break;
      case "xpath":
        by = By.xpath(elementInfo.getAndroidValue());
        break;
      case "class":
        by = By.className(elementInfo.getAndroidValue());
        break;
      case "text":
        by = By.linkText(elementInfo.getAndroidValue());
        break;
      case "accessibility id":
        by = MobileBy.AccessibilityId(elementInfo.getAndroidValue());
        break;
    }
    return by;
  }

  @Override
  public int getElementInfoToIndex(ElementInfo elementInfo) {
    return elementInfo.getAndroidIndex();
  }
}
