package com.example.garik.assignment5.Retrofit;

import com.example.garik.assignment5.Models.Repo;
import com.example.garik.assignment5.Models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by GaRiK on 17.09.2018.
 */

public interface GithubService {

    @GET("{user_name}")
    Call<User> userInfo(@Path("user_name") String name);


    @GET("{user_name}/repos")
    Call<List<Repo>> repoList(@Path("user_name") String name);


}
