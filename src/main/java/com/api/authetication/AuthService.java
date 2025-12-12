package com.api.authetication;

import com.api.base.BaseService;
import com.api.models.request.LoginRequest;
import io.restassured.response.Response;

import java.util.HashMap;

public class AuthService extends BaseService {

    private static final String BASE_PATH = "/api/auth/";

   /* public AuthService()
    {
        super();
    }*/

    public Response login(LoginRequest payload)
    {
        return postRequest(payload,BASE_PATH+"login");
    }
    public Response login(String payload)
    {
        return postRequest(payload,BASE_PATH+"login");
    }
    public Response forgotPassword(String emailAddress)
    {
        HashMap<String, String> payload = new HashMap<String, String>();
        payload.put("email", emailAddress);
        return postRequest(payload, BASE_PATH+"forgot-password");
    }
}
