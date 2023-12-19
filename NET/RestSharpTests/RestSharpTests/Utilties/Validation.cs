using AventStack.ExtentReports;

namespace RestSharpTests.Utilties
{
    public static class Validation
    {
        public static string StringReportFormatting(string jsonString)
        {
            jsonString = jsonString.Replace(",", ",<br/>").Replace("{", "<br /><br />{<br /><br />").Replace("}", "<br /><br />}<br /><br />");
            return jsonString;
        }

        public static bool ResponseCode(ExtentTest test, int actualCode, int expectedCode)
        {
            bool output;
            if (actualCode == expectedCode)
            {
                test.Log(Status.Pass, "The response code is as expected. <br / > Expected response code is : " + expectedCode + "<br />Actual response code is " + actualCode);
                output = true;
            }
            else
            {
                test.Log(Status.Fail, "The response code is NOT as expected. >br /> Expected response code is: " + expectedCode + "<br />Actual response code is " + actualCode);
                output = false;
            }
            return output;
        }

        public static bool ResponseBody(ExtentTest test, string actualBody, string expectedBody)
        {
            bool output;
            if (actualBody.Equals(expectedBody, StringComparison.OrdinalIgnoreCase))
            {
                test.Log(Status.Pass, "The response Body is as expected. <br / > Expected expectedBody  is : " + expectedBody + "<br />Actual response code is " + actualBody);
                output = true;
            }
            else
            {
                test.Log(Status.Fail, "The response Body is NOT as expected. >br /> Expected response Body is: " + expectedBody + "<br />Actual response code is " + actualBody);
                output = false;
            }
            return output;
        }
    }
}
