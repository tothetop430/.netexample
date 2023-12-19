using NUnit.Framework;
using RestSharp;
using System;
using System.Collections.Generic;
using System.IO;
using System.Reflection;
using AventStack.ExtentReports;
using AventStack.ExtentReports.Gherkin.Model;
using RestSharpTests.Utilties;

namespace RestSharpTests.Base
{
    public class BaseClass : ExtentReportListener
    {


        public JsonHandler JHandler;
        public static int ClassCounter = 0;

        public RestSharpUtility restSharpUtility;

        public RestRequest restRequest;
        public RestResponse restResponse;
      
        public BaseClass()
        {

        }

        public void SetUp()
        {
            var env = TestContext.Parameters["env"];
            if (env != null)
            {
                Environment.SetEnvironmentVariable("env", env.ToLower());
            }
            else
            {
                Environment.SetEnvironmentVariable("env", string.Empty);
            }
        }

        [OneTimeSetUp]
        public void BeforeClass()
        {
            SetUp();
            SetUpReports(TestContext.CurrentContext);
            restSharpUtility = new RestSharpUtility(ObjectHelper.BASEURI);

            // Instance of other required classes
            if (TestContext.CurrentContext.Test.ClassName.ToLower().Contains("testsuite_sample"))
            {
                if (!JsonHandler.IsInitialized())
                    JHandler = new JsonHandler(new FileInfo(ObjectHelper.GetSampleTestDataFile));
                else
                    JHandler = new JsonHandler();
            }
            ClassCounter++;
        }

        [SetUp]
        public void BeforeMethod()
        {
            // Start test
            TestStarted();

        }

        [TearDown]
        public void AfterMethod()
        { 
            TestFinished();
        }

        [OneTimeTearDown]
        public void TearDown()
        {
            // Push results to report after each test
            Reports.Flush();
        }


    }
}
