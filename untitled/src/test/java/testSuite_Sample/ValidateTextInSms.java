package testSuite_Sample;

import com.aventstack.extentreports.Status;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.example.BaseClass;
import org.example.ObjectHelper;
import org.example.TestDataProvider;
import org.example.Validation;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class ValidateTextInSms extends BaseClass {

    @Test(dataProvider = "testData", dataProviderClass = TestDataProvider.class,groups = {"Test1"})
    public void SendSmsInEnglish(String dataSetAsString){
        try {
            JSONObject dataMap = new JSONObject(dataSetAsString);
            //test.log(Status.INFO, dataMap.getString("testDescripion");
            System.out.println(dataMap.getString("testDescription"));

            //Request builder and response
            ReqSpecs= RaUtils.getReqSpecs();
            ReqSpecs = RaUtils.addBasePath(ReqSpecs, ObjectHelper.sendSampleApiEndpoint);
            ReqSpecs = RaUtils.addStringBodyToReq(ReqSpecs,dataMap.get("requestBody").toString());
            ReqSpecs= RaUtils.addPathParam(ReqSpecs,"phoneNumber",dataMap.getJSONObject("queryParams").getString("phoneNumber"));
            //get response

            Response resp = RaUtils.getResponse(ReqSpecs,"post");
            Assert.assertTrue(Validation.responseCode(test, resp.getStatusCode(), 200));

            //getting response headers
        //    Headers responseHeaders = resp.getHeaders();
         //   Assert.assertTrue(Validation.headers(test,responseHeaders,ObjectHelper.allHeaders));

            Assert.assertTrue(Validation.responseBody(test,resp.getBody().asString(), String.valueOf(dataMap.get("expectedResponse"))));

        }catch (Exception e){
            Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
            test.log(Status.FAIL,"Test failed :" + e.toString());
            Assert.assertTrue(false,e.toString());
        }
    }
}
//https://api.apis.guru/v2/providers.json