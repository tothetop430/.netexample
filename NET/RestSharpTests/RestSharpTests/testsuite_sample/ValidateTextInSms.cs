using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json.Nodes;
using System.Threading.Tasks;
using AventStack.ExtentReports;
using RestSharp;
using RestSharpTests;
using RestSharpTests.Base;
using RestSharpTests.Utilties;
using Newtonsoft.Json;
using AventStack.ExtentReports.Model;
using NUnit.Framework.Interfaces;
using Newtonsoft.Json.Linq;
using NUnit.Framework.Internal;

namespace RestSharpTests.testsuite_sample
{
    [TestFixture]
    public class ValidateTextInSms : BaseClass
    {
        [Test]
        [TestCaseSource(typeof(TestDataProvider), nameof(TestDataProvider.GetTestData), new object[] { "Test1" })]
        public void SendSmsInEnglish(string dataSetAsString)
        {
            try
            {
               Console.WriteLine(dataSetAsString);
                JObject dataMap = JObject.Parse(dataSetAsString);
                restRequest = restSharpUtility.GetRestRequest(ObjectHelper.SendSampleApiEndpoint);
                restRequest = restSharpUtility.addBody(restRequest, dataMap["requestBody"].ToString());
                restRequest = restSharpUtility.addQueryParam(restRequest, "phoneNumber", dataMap["queryParams"]["phoneNumber"].ToString());
                //get response

                restResponse = restSharpUtility.GetRestResponse(restRequest, Method.Post);
                Assert.IsTrue(Validation.ResponseCode(extentTest, (int)restResponse.StatusCode, 200));


                Assert.IsTrue(Validation.ResponseBody(extentTest, restResponse.Content, dataMap["expectedResponse"].ToString()));

              
            }
            catch (Exception e)
            {
                LogMessage(Status.Fail, "Test failed :" + e.ToString());
                Assert.IsTrue(false, e.ToString());

            }
        }
    }
}
