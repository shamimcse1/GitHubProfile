package com.example.githubprofile.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.githubprofile.helpers.DataResources;
import com.example.githubprofile.model.Profile;
import com.example.githubprofile.network.ApiInterface;
import com.example.githubprofile.network.RetrofitApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileRepository {

    public ApiInterface apiInterface;

    private MutableLiveData<DataResources<Profile>> mutableLiveData = new MutableLiveData<DataResources<Profile>>();
    public LiveData<DataResources<Profile>> profileLiveData = mutableLiveData;



    public  void setProfileLiveData(){

        apiInterface = RetrofitApiService.getRetrofit().create(ApiInterface.class);
        mutableLiveData.postValue(DataResources.loading());
        Call<Profile> profileCall =apiInterface.getProfile();

        profileCall.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if (response.code() == 200 && response.isSuccessful() && response.body()!= null){
                    mutableLiveData.postValue(DataResources.success(response.body()));
                }
                else {
                    mutableLiveData.postValue(DataResources.error(response.message()));
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                mutableLiveData.postValue(DataResources.error(t.getMessage()));
            }
        });
    }

}
