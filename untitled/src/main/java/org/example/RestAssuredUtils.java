package org.example;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;


public class RestAssuredUtils {
    private String BaseURI;
    private RequestSpecBuilder Request_Builder;
    private RequestSpecification Request_Spec;

    //constructor requiring base uri
    public RestAssuredUtils(String baseUri) {
        this.BaseURI= baseUri;
    }

    /*return basic request specifications


     */
    public RequestSpecification getReqSpecs(){
        Request_Builder = new RequestSpecBuilder();
        Request_Builder.setBaseUri(this.BaseURI);
Request_Spec = Request_Builder.build();
return Request_Spec;
    }


    public RequestSpecification addBasePath(RequestSpecification reqSpecs, String basePath){
        return reqSpecs.basePath(basePath);
    }

    public RequestSpecification addHeader(RequestSpecification reqSpecs,String headerKey, String headerValue){
        return reqSpecs.header(headerKey,headerValue);
    }
    public RequestSpecification addHeaders(RequestSpecification reqSpecs, Map<String,String> headerMap, String headerValue){
        return reqSpecs.headers(headerMap);
    }

    public RequestSpecification addQueryParam(RequestSpecification reqSpecs, String queryParam, String queryValue){
        return reqSpecs.queryParam(queryParam,queryValue);
    }
    public RequestSpecification addPathParam(RequestSpecification reqSpecs, String pathParam, String pathParamValue){
        return reqSpecs.pathParam(pathParam,pathParamValue);
    }
    public RequestSpecification addQueryParams(RequestSpecification reqSpecs, Map<String,String> queryMap){
        return reqSpecs.queryParams(queryMap);
    }
    public RequestSpecification addStringBodyToReq(RequestSpecification reqSpecs, String reqBody){
        return reqSpecs.body(reqBody);
    }

    public Response getResponse(RequestSpecification reqSpecs, String type) {
        Response response = null;
        if (type.equalsIgnoreCase("get")) {
            response = given().spec(reqSpecs).when().log().all().get();
        } else if (type.equalsIgnoreCase("post")) {
            response = given().spec(reqSpecs).when().log().all().post();

        } else if (type.equalsIgnoreCase("delete")) {
            response = given().spec(reqSpecs).when().log().all().delete();
        } else if (type.equalsIgnoreCase("put")) {
            response = given().spec(reqSpecs).when().log().all().put();
        } else if (type.equalsIgnoreCase("patch")) {
            response = given().spec(reqSpecs).when().log().all().patch();
        }
        response.then().log().all();
        return response;
    }

    public JSONObject getResponseAsJsonObj(Response resp){
    return new JSONObject(resp.asString());

}
public JSONArray getResponseAsJsonArray(Response resp){
    return new JSONArray(resp.asString());
    }

/*    public boolean validateJsonSchema(RequestSpecification reqSpecs, String type, String jsonSchemaFile){
        boolean jsonSchemaMatch= false;
        try {
            if (type.equalsIgnoreCase("get")){
                given().spec(reqSpecs).when().get().then().log().ifValidationFails(LogDetail.ALL).assertThat().body(matchesJsonSchemaInClasspath(jsonSchemaFile));
            }else if (type.equalsIgnoreCase("post")){
                given().spec(reqSpecs).when().post().then().log().ifValidationFails(LogDetail.ALL).assertThat().body(matchesJsonSchemaInClasspath(jsonSchemaFile));
            }
            jsonSchemaMatch =true;
        }catch (AssertionError ae){
            jsonSchemaMatch= false;
        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonSchemaMatch;
    }*/

    public RequestSpecification addAuthentication(RequestSpecification reqSpecs, String Username, String Password){
        return reqSpecs.auth().basic(Username,Password);
    }
}
