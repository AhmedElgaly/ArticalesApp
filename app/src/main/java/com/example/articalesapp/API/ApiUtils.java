package com.example.articalesapp.API;

public class ApiUtils {
    private ApiUtils() {}

    public static final String BASE_URL = "https://newsapi.org/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
