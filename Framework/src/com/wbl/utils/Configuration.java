package com.wbl.utils;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

/**
 * Created by svelupula on 8/8/2015.
 */
public class Configuration {

    public Browsers Browser;
    public String BaseUrl;
    public String TestResultPath;
    public String TestDataPath;
    public int WaitTimeout;


    public Configuration() {
        try {
            Properties props = new Properties();
            props.load(new FileReader(new File("config.properties")));
            Browser = Browsers.valueOf(props.getProperty("browser"));
            BaseUrl = props.getProperty("url");
            TestResultPath = props.getProperty("test-result-path");
            TestDataPath = props.getProperty("test-data-path");
            WaitTimeout = Integer.parseInt(props.getProperty("wait-timeout"));
        } catch (Exception ex) {

        }

    }

}
