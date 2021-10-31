package com.example.foodapp.retrofit;


import com.example.foodapp.model.AllFood;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("v3/499b44cd-778f-44b6-95de-f10fc77a73da")
    Call<AllFood> getAllData();


    // lets make our model class of json data.

}
