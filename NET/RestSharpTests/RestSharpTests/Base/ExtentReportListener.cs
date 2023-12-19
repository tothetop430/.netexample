using System;
using System.Reflection;
using AventStack.ExtentReports;
using AventStack.ExtentReports.Reporter;
using NUnit.Framework;
using NUnit.Framework.Interfaces;
using RestSharp;

namespace RestSharpTests.Base
{

    public class ExtentReportListener
    {
        public static AventStack.ExtentReports.ExtentReports Reports;
        public static ExtentTest extentTest;
        private static readonly string ResultPath = GetResultPath();

        private static string GetResultPath()
        {
            string path = Assembly.GetCallingAssembly().CodeBase;
            string actualPath = path.Substring(0, path.LastIndexOf("bin"));
            string projectPath = new Uri(actualPath).LocalPath;


            string resultPath = projectPath + "test-output\\";
            if (!Directory.Exists(resultPath))
            {
                Directory.CreateDirectory(resultPath);
            }
            return resultPath;
        }

        private string ReportLocation = $"{ResultPath}Report\\";



        public void SetUpReports(TestContext context)
        {

            var currentTime = DateTime.Now.ToString("yyyy-MM-dd hh-mm.ss");
            var sparkReporter = new ExtentSparkReporter($"{ReportLocation}ExtentReport_{currentTime}.html");
            Reports = new AventStack.ExtentReports.ExtentReports();
          
            Reports.AttachReporter(sparkReporter);
            Reports.AddSystemInfo("Application Name", "Testing");
            Reports.AddSystemInfo("Environment", Environment.GetEnvironmentVariable("env"));
            Console.WriteLine($"{ReportLocation}Reportlocation");
        }

        public void TestStarted()
        {
            extentTest = Reports.CreateTest(TestContext.CurrentContext.Test.MethodName);
            Console.WriteLine(TestContext.CurrentContext.Test.ClassName);
            Console.WriteLine(TestContext.CurrentContext.Test.MethodName);

        }

        public void TestFinished()
        {
            TestOutput();
            Reports.Flush();
        }

        public void TestOutput()
        {
            try
            {
                var status = TestContext.CurrentContext.Result.Outcome.Status;
                var stacktrace = string.IsNullOrEmpty(TestContext.CurrentContext.Result.StackTrace)
                  ? ""
                  : string.Format("{0}", TestContext.CurrentContext.Result.StackTrace);

                var errorMessage = TestContext.CurrentContext.Result.Message;
                Status logstatus;
                switch (status)
                {
                    case TestStatus.Failed:
                        logstatus = Status.Fail;
                        extentTest.Log(Status.Fail, "Test ended with " + Status.Fail + " – " + errorMessage);
                        extentTest.Log(Status.Fail, stacktrace);
                        break;
                    case TestStatus.Skipped:
                        logstatus = Status.Skip;
                        extentTest.Log(Status.Skip, "Test ended with " + Status.Skip);
                        break;
                    default:
                        logstatus = Status.Pass;
                        extentTest.Log(Status.Pass, "Test ended with " + Status.Pass);
                        extentTest.Log(Status.Pass, "Test steps finished for test case " + TestContext.CurrentContext.Test.Name);
                        
                        break;
                }
            }
            catch (Exception ex)
            {
                throw ex;
            }

        }

        public void LogMessage(Status st, string msg)
        {
            extentTest.Log(st, msg);
        }
    }
}
