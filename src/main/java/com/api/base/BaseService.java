package com.api.base;

import static io.restassured.RestAssured.*;

import com.api.filters.LoggingFilter;
import com.api.models.request.LoginRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseService {

    // Responsible to handle Base URI
    // Responsible for creating request specification
    // Responsible for Handling response

    private static final String BASE_URL = "http://64.227.160.186:8080";
    private RequestSpecification requestSpecification;
    static
    {
        RestAssured.filters(new LoggingFilter());
    }
    public BaseService()
    {
        requestSpecification = given().baseUri(BASE_URL);
    }
    protected void setAuthToken(String authToken)
    {
        requestSpecification.header("authorization", "Bearer "+authToken);
    }
    protected Response postRequest(Object payload, String endpoint)
    {
           return requestSpecification.contentType(ContentType.JSON).body(payload).post(endpoint);
    }

    protected Response postRequest(String payload, String endpoint)
    {
        return requestSpecification.contentType(ContentType.JSON).body(payload).post(endpoint);
    }

    protected String getPostResponseAsString(String payload,String endpoint,String... optional)
    {
        return requestSpecification.contentType(ContentType.JSON).body(payload).post(endpoint).asPrettyString();
    }

    protected Response getRequest(String endpoint)
    {
        return requestSpecification.get(endpoint);
    }

    protected Response putRequest(Object payload,String endpoint)
    {
        return requestSpecification.contentType(ContentType.JSON).body(payload).put(endpoint);
    }

    protected Response putRequest(String payload,String endpoint)
    {
        return requestSpecification.patch(endpoint);
    }
}
