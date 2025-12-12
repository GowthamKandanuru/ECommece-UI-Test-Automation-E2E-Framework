package com.api.authetication;

import com.api.base.BaseService;
import io.restassured.response.Response;

import java.util.HashMap;

public class UserprofileManagementService extends BaseService {

    private static final String BASE_PATH = "/api/users/";

    public Response getProfile(String authToken)
    {
        setAuthToken(authToken);
        return getRequest(BASE_PATH+"profile");
    }
    public Response updateProfile(Object payload,String authToken)
    {
        setAuthToken(authToken);
        return  putRequest(payload,BASE_PATH+"profile");
    }
    public Response updateProfile(HashMap<String,String > payload, String authToken)
    {
        setAuthToken(authToken);
        return putRequest(payload, BASE_PATH+"profile");
    }
}
