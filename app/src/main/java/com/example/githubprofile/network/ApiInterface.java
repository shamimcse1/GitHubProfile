package com.example.githubprofile.network;

import com.example.githubprofile.model.Profile;
import com.example.githubprofile.model.Repository;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("shamimcse1")
    Call<Profile> getProfile();

    @GET("shamimcse1/repos")
    Call<Repository> getRepositories();
}
