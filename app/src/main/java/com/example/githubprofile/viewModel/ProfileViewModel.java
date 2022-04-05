package com.example.githubprofile.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.githubprofile.helpers.DataResources;
import com.example.githubprofile.model.Profile;
import com.example.githubprofile.repository.ProfileRepository;

public class ProfileViewModel extends ViewModel {

    ProfileRepository profileRepository = new ProfileRepository();
    public LiveData<DataResources<Profile>> liveData = profileRepository.profileLiveData;

    public  void  getProfileData(){
        profileRepository.setProfileLiveData();
    }
}
