package com.example.githubprofile.network;

import com.example.githubprofile.model.Profile;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("shamimcse1")
    Call<Profile> getProfile();
}
