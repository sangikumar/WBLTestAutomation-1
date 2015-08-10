package com.wbl.utils;

import org.codehaus.jackson.*;
import org.openqa.selenium.By;

import java.io.*;
import java.util.*;

/**
 * Created by svelupula on 8/8/2015.
 */
public class WBy {

    private static HashMap<String, String> _locators;

    public static void loadJsonMap(String jsonPath) {
        if (_locators != null)
            return;
        _locators = new HashMap<String, String>();
        try {
            JsonFactory f = new JsonFactory();
            JsonParser jp = f.createJsonParser(new File("locators.json"));
        } catch (Exception ex) {

        }

        /*for (var item in o1)
        {
            if (!_locators.ContainsKey(item.Key))
            {
                _locators.Add(item.Key, item.Value.ToString());
            }

        }*/
    }

    public static String getValue(String key) {
        try {
            return _locators.get(key);
        } catch (Exception ex) {

        }
        return "";
    }

    public static By get(String locator) throws Exception {
        try {
            if (locator.contains("=")) {
                String[] values = locator.split("=");
                if (values[0].toLowerCase().equals("css")) {
                    return By.cssSelector(getValue(values[1]));
                }
                if (values[0].toLowerCase().equals("xpath")) {
                    return By.xpath(getValue(values[1]));
                }
                if (values[0].toLowerCase().equals("id")) {
                    return By.id(getValue(values[1]));
                }
                if (values[0].toLowerCase().equals("name")) {
                    return By.name(getValue(values[1]));
                }
                if (values[0].toLowerCase().equals("class")) {
                    return By.className(getValue(values[1]));
                }
                if (values[0].toLowerCase().equals("link")) {
                    return By.linkText(getValue(values[1]));
                }
                if (values[0].toLowerCase().equals("plink")) {
                    return By.partialLinkText(getValue(values[1]));
                }
                if (values[0].toLowerCase().equals("tag")) {
                    return By.tagName(getValue(values[1]));
                }
                return By.cssSelector(getValue(values[1]));
            }

            return By.cssSelector(getValue(locator));
        } catch (Exception ex) {
            throw ex;
        }
    }


}
