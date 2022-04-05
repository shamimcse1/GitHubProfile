package com.example.githubprofile.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.githubprofile.R;
import com.example.githubprofile.databinding.ActivityMainBinding;
import com.example.githubprofile.helpers.DataResources;
import com.example.githubprofile.helpers.UniversalPagerAdapter;
import com.example.githubprofile.model.Profile;
import com.example.githubprofile.view.fragment.OverviewFragment;
import com.example.githubprofile.view.fragment.ProjectsFragment;
import com.example.githubprofile.view.fragment.RepositoriesFragment;
import com.example.githubprofile.view.fragment.StarsFragment;
import com.example.githubprofile.viewModel.ProfileViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.LinkedHashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public ProfileViewModel profileViewModel;
    private ProgressDialog progressDialog;
    public ActivityMainBinding binding;
    public OverviewFragment overviewFragment;
    public RepositoriesFragment repositoriesFragment;
    public ProjectsFragment projectsFragment;
    public StarsFragment starsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading");

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        profileViewModel.getProfileData();

        profileViewModel.liveData.observe(this, new Observer<DataResources<Profile>>() {
            @Override
            public void onChanged(DataResources<Profile> profileDataResources) {
                switch (profileDataResources.getStatus()) {

                    case LOADING:
                        progressDialog.show();
                        break;
                    case SUCCESS:
                        Glide.with(MainActivity.this).load(profileDataResources.getActualData().getAvatarUrl()).into(binding.profileImage);
                        binding.userName.setText(profileDataResources.getActualData().getName());
                        binding.location.setText(profileDataResources.getActualData().getLocation());
                        binding.repository.setText(String.valueOf(profileDataResources.getActualData().getPublicRepos()));
                        binding.stars.setText(String.valueOf(profileDataResources.getActualData().getPublicGists()));
                        binding.followers.setText(String.valueOf(profileDataResources.getActualData().getFollowers()));
                        binding.following.setText(String.valueOf(profileDataResources.getActualData().getFollowing()));
                        progressDialog.dismiss();
                        break;
                    case ERROR:
                        progressDialog.dismiss();
                        break;
                }
            }
        });

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.pager);


        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(4);
        initFragment();

        Map<String, Fragment> stringFragmentMap = new LinkedHashMap<>();
        setupFragments(stringFragmentMap);

        UniversalPagerAdapter adapter = new UniversalPagerAdapter(getSupportFragmentManager(), stringFragmentMap);
        viewPager.setAdapter(adapter);

    }

    private void initFragment() {
        overviewFragment = new OverviewFragment();
        repositoriesFragment = new RepositoriesFragment();
        projectsFragment = new ProjectsFragment();
        starsFragment = new StarsFragment();
    }


    private void setupFragments(Map<String, Fragment> stringFragmentMap) {

        stringFragmentMap.put(getResources().getString(R.string.overview), overviewFragment);
        stringFragmentMap.put(getResources().getString(R.string.repositories), repositoriesFragment);
        stringFragmentMap.put(getResources().getString(R.string.projects), projectsFragment);
        stringFragmentMap.put(getResources().getString(R.string.stars), starsFragment);
    }
}