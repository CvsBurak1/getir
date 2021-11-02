package org.mobile.getir;

import org.mobile.getir.helper.*;
import org.mobile.getir.model.SelectorInfo;
import com.thoughtworks.gauge.Step;
import io.appium.java_client.MobileElement;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.util.List;


public class StepImpl extends HookImpl {

    public ScenarioContext context;
    protected Logger logger = LoggerFactory.getLogger(getClass());

    public List<MobileElement> findElements(By by) throws Exception {
        List<MobileElement> webElementList = null;
        try {
            webElementList = appiumFluentWait.until(new ExpectedCondition<List<MobileElement>>() {
                @Nullable
                @Override
                public List<MobileElement> apply(@Nullable WebDriver driver) {
                    List<MobileElement> elements = driver.findElements(by);
                    return elements.size() > 0 ? elements : null;
                }
            });
            if (webElementList == null) {
                throw new NullPointerException(String.format("by = %s Web element list not found", by.toString()));
            }
        } catch (Exception e) {
            throw e;
        }
        return webElementList;
    }

    public MobileElement findElement(By by) throws Exception {
        MobileElement mobileElement;
        try {
            mobileElement = findElements(by).get(0);
        } catch (Exception e) {
            throw e;
        }
        return mobileElement;
    }

    public MobileElement findElementByKeyWithoutAssert(String key) {
        SelectorInfo selectorInfo = selector.getSelectorInfo(key);

        MobileElement mobileElement = null;
        try {
            mobileElement = selectorInfo.getIndex() > 0 ? findElements(selectorInfo.getBy())
                    .get(selectorInfo.getIndex()) : findElement(selectorInfo.getBy());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mobileElement;
    }

    public MobileElement findElementByKey(String key) {
        SelectorInfo selectorInfo = selector.getSelectorInfo(key);

        MobileElement mobileElement = null;
        try {
            mobileElement = selectorInfo.getIndex() > 0 ? findElements(selectorInfo.getBy())
                    .get(selectorInfo.getIndex()) : findElement(selectorInfo.getBy());
        } catch (Exception e) {
            Assertions.fail("key = %s by = %s Element not found ", key, selectorInfo.getBy().toString());
            e.printStackTrace();
        }
        return mobileElement;
    }

    public List<MobileElement> findElemenstByKey(String key) {
        SelectorInfo selectorInfo = selector.getSelectorInfo(key);
        List<MobileElement> mobileElements = null;
        try {
            mobileElements = findElements(selectorInfo.getBy());
        } catch (Exception e) {
            Assertions.fail("key = %s by = %s Elements not found ", key, selectorInfo.getBy().toString());
            e.printStackTrace();
        }
        return mobileElements;
    }

    @Step("Click element by <key>")
    public void clickByKey(String key) {
        findElementByKey(key).click();
        logger.info(key + " elementine tıklandı");
    }

    @Step("Get element <type> by key <key> and set context to compare")
    public void setTextToContextWithKey(String type, String key) {
        context = new ScenarioContext();
        String text = findElementByKey(key).getText();
        logger.info("Getting text to compare, the text is: " + text);

        switch (type) {
            case "PRICE":
                context.setContext(Context.PRODUCT_PRICE, text);
                break;
            case "TITLE":
                context.setContext(Context.PRODUCT_TITLE, text);
                break;
            case "DESC":
                context.setContext(Context.PRODUCT_DESC, text);
                break;
        }
    }

    @Step("Get element <type> by key <key> and compare with context, if <equal>")
    public void getTextAndCompareContext(String type, String key, String equal) {
        String lastText = findElementByKey(key).getText();
        logger.info(lastText);

        String text = null;
        switch (type) {
            case "PRICE":
                text = (String) context.getContext(Context.PRODUCT_PRICE);
                logger.info("The saved context is: " +text);
                break;
            case "TITLE":
                text = (String) context.getContext(Context.PRODUCT_TITLE);
                logger.info("The saved context is: " +text);
                break;
            case "DESC":
                text = (String) context.getContext(Context.PRODUCT_DESC);
                logger.info("The saved context is: " +text);
                break;
        }

        if (equal.equals("equal")){
            Assert.assertTrue(lastText.contains(text));
        } else Assert.assertFalse(lastText.contains(text));
    }

    @Step("Click element by <key> with index <index>")
    public void clickElemWithIndex(String key, int index){
        List<MobileElement> elems = findElemenstByKey(key);
        logger.info("Clicking element at index: " + index);
        elems.get(index+1).click();
    }

    @Step("Check if element <key> exist")
    public void checkifElemExist(String key){
        findElementByKey(key).isDisplayed();
        logger.info("Element is exist at the current location.");
    }

    @Step("Check if element's <key> text contains <text>")
    public void checkIfElementTextContains(String key, String text){
        String txt = findElementByKey(key).getText();
        logger.info("Checking for text: " + text );
        logger.info("The element's text is: " + txt );
        Assert.assertTrue(txt.contains(text));
    }

    @Step("Go to <text>")
    public void goTo(String text){
        List<MobileElement> elems = findElemenstByKey("Menu_categories");
        logger.info("Going to " + text);
        switch (text) {
            case "Snacks":
                elems.get(0).click();
                break;
            case "Electronics":
                elems.get(1).click();
                break;
            case "Water":
                elems.get(2).click();
                break;
            case "Home Care":
                elems.get(3).click();
                break;
            case "Baby Care":
                elems.get(4).click();
                break;
            case "Fruits & Veg":
                elems.get(5).click();
                break;
        }
    }

    @Step("Click Android back button")
    public void androidBackBtn(){
        logger.info("Clicked back button.");
        appiumDriver.navigate().back();
    }

    @Step("Check if onboarding screen is on then close")
    public void closeOnBoarding(){
        if(findElementByKeyWithoutAssert("WelcomePage_skipButton") != null){
            clickByKey("WelcomePage_skipButton");
        } else logger.info("Onboarding page is not appeared");
    }
}


