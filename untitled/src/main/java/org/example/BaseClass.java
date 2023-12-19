package org.example;

import groovyjarjarpicocli.CommandLine;
import io.restassured.specification.RequestSpecification;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.io.File;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;

import static org.example.ObjectHelper.getSampleTestDataFile;

@Listeners(ExtentReportListener.class)
public class BaseClass extends ExtentReportListener{
    public LinkedHashMap<String,String> testData;
    public JsonHandler jhandler;
    public static int classCounter =0;
    public RequestSpecification ReqSpecs;

    public RestAssuredUtils RaUtils;
    @Parameters({"env"})
    @BeforeSuite
    public void setUp(@Optional String env){
        if (env !=null){
            System.setProperty("env",env.toLowerCase());
        } else {
            System.setProperty("env","");
        }
    }

    @BeforeClass
    public void beforeClass(ITestContext iTestContext){
        RaUtils = new RestAssuredUtils(org.example.ObjectHelper.BASEURI);
        //instance of other required classes
        if(iTestContext.getCurrentXmlTest().getClasses().get(classCounter).toString().contains("testSuite_Sample")) {
            jhandler = new JsonHandler(new File(org.example.ObjectHelper.getSampleTestDataFile));
       // }//else if(iTestContext.getCurrentXmlTest().getClasses().get(classCounter).toString().contains("testSuite_TF_622")){
            //jhandler = new JsonHandler(new File(ObjectHelper.getTMF622TestDataFile));

        } else {
          //  jhandler = new JsonHandler(new File(ObjectHelper.getTMF622TestDataFile));

        }
        classCounter++;
    }

    @BeforeMethod
    public void beforeMethod(Method m) {
        // Start test
        test = reports.createTest(m.getName());
    }
    @AfterSuite
    public void tearDown() {
        // push results to report after each test
        reports.flush();
    }
}

