package org.example;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
public class ExtentReportListener implements ITestListener {
    public static ExtentReports reports;
    public static ExtentTest test;
    private static String resultPath = getResultPath();
    private static String getResultPath() {
        resultPath = "test";
        if (!new File(resultPath).isDirectory()) {
            new File(resultPath).mkdirs();
        }
        return resultPath;
    }
    String ReportLocation = "test-ouput/Report/" + resultPath + "/";
    public void onTestStart(ITestResult result) {
        test = reports.createTest(result.getMethod().getMethodName());
        System.out.println(result.getTestClass().getTestName());
        System.out.println(result.getMethod().getMethodName());
    }
    public void onTestSuccess(ITestResult result) {
        test.log(Status.PASS, "Test is passed");
    }
    public void onTestFailure(ITestResult result) {
        test.log(Status.FAIL, "Test is failed");
    }
    public void onTestSkipped(ITestResult result) {
        test.log(Status.SKIP, "Test is skipped");
    }
    public void onStart(ITestContext context) {
        String currentTime = new SimpleDateFormat("yyyy-MM-dd hh-mm.ss").format(Calendar.getInstance().getTime());
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(ReportLocation + "ExtentReport" + "_" + currentTime + ".html");
        reports = new ExtentReports();
        reports.attachReporter(sparkReporter);
        reports.setSystemInfo("Application Name", "Testing");
        reports.setSystemInfo("Environment", System.getProperty("env"));
        System.out.println(ReportLocation + "Reportlocation");
    }
    public void onFinish(ITestContext context) {
        reports.flush();
    }
}