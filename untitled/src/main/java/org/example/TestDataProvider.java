package org.example;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TestDataProvider extends BaseClass{
    @DataProvider(name="testData")
    public Object[] dataProvider(Method m){
        //get testData from json File
        testData= JsonHandler.getTestDataFromJson();

        //get test cases list as groups for which data is required
        List<String> testCases= Arrays.asList(m.getAnnotation(Test.class).groups());

        //build test data as per groups and return as object
        List<String>tcData = new ArrayList<>();
        for (Map.Entry<String,String> tMap : testData.entrySet()) {
            if (testCases.contains(tMap.getKey())== true) {
                tcData.add(tMap.getValue().toString());
            }
        }
        Object[] testCaseData = tcData.toArray(new Object[tcData.size()]);
        return testCaseData;

    }

}
