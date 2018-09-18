package com.example.garik.assignment5;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.garik.assignment5.Adapters.GithubAdapter;
import com.example.garik.assignment5.Models.Repo;
import com.example.garik.assignment5.Models.User;
import com.example.garik.assignment5.Retrofit.GithubService;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.garik.assignment5.Constants.Constants.USER_NAME_KEY;

public class UserInfoActivity extends AppCompatActivity {


    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.user_info)
    TextView userInfo;
    @BindView(R.id.user_image)
    ImageView userImage;
    @BindView(R.id.repo_container_recyclerview)
    RecyclerView reposRecyclerView;


    private GithubService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        ButterKnife.bind(this);



        String searchedUser=getIntent().getStringExtra(USER_NAME_KEY);


        reposRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Gson gson= new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        GsonConverterFactory converterFactory=GsonConverterFactory.create(gson);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/users/")
                .addConverterFactory(converterFactory)
                .build();

        service = retrofit.create(GithubService.class);
        service.userInfo(searchedUser).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user=response.body();
                if(user!=null){
                    userName.setText(user.getName());
                    userInfo.setText(user.getLogin()+"/"+ user.getCompany() +"/"+user.getLocation());
                    GlideApp.with(UserInfoActivity.this)
                            .load(user.getAvatarUrl())
                            .placeholder(R.drawable.avatar)
                            .circleCrop()
                            .into(userImage);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Snackbar.make(findViewById(android.R.id.content),"Connection failed !",Snackbar.LENGTH_SHORT).show();
            }
        });

        service.repoList(searchedUser).enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {

                List<Repo> repoList=response.body();
                if(response.body()!=null){
                    GithubAdapter adapter=new GithubAdapter(UserInfoActivity.this,repoList);
                    reposRecyclerView.setAdapter(adapter);
                }


            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                Snackbar.make(findViewById(android.R.id.content),"Connection failed !",Snackbar.LENGTH_SHORT).show();
            }
        });



    }


}
