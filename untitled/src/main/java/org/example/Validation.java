package org.example;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class Validation {
    public static String stringReportFormatting(String jsonString){
        jsonString = jsonString.replace(",",",<br/>").replace("{","<br />{<br />").replace("}","<br />}<br />");
        return jsonString;
    }

    public static boolean responseCode(ExtentTest test, int actualCode, int expectedCode){

        boolean output;
        if(actualCode == expectedCode){
            test.log(Status.PASS,"The response code is as expected. <br / > Expected response code is : " + expectedCode + "<br />Actual response code is " + actualCode);
            output = true;
        } else {
            test.log(Status.FAIL,"The response code is NOT as expected. >br /> Expected response code is: " + expectedCode + "<br />Actual response code is " + actualCode);
            output= false;
        }
        return output;
    }
    public static boolean responseBody(ExtentTest test, String actualBody, String expectedBody){

        boolean output;
        if(actualBody.equalsIgnoreCase(expectedBody)){
            test.log(Status.PASS,"The response Body is as expected. <br / > Expected expectedBody  is : " + expectedBody + "<br />Actual response code is " + actualBody);
            output = true;
        } else {
            test.log(Status.FAIL,"The response Body is NOT as expected. >br /> Expected response Body is: " + expectedBody + "<br />Actual response code is " + actualBody);
            output= false;
        }
        return output;
    }
}
