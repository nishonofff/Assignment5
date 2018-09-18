package com.example.garik.assignment5;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.garik.assignment5.Models.User;
import com.example.garik.assignment5.Retrofit.GithubService;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.garik.assignment5.Constants.Constants.USER_NAME_KEY;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.go_button)
    Button goButton;
    @BindView(R.id.search_user_edit_text)
    EditText searchUserEdittext;

    GithubService service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        searchUserEdittext.setSelection(searchUserEdittext.getText().length());

        Gson gson= new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        GsonConverterFactory converterFactory=GsonConverterFactory.create(gson);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/users/")
                .addConverterFactory(converterFactory)
                .build();

        service = retrofit.create(GithubService.class);



    }


    @OnClick(R.id.go_button)
    void onGoButtonClicked() {


        service.userInfo(searchUserEdittext.getText().toString()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.body()!=null){
                    Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
                    intent.putExtra(USER_NAME_KEY, searchUserEdittext.getText().toString());
                    startActivity(intent);
                }
                else {
                   Toast toast = Toasty.error(MainActivity.this,"No such a user !", Toast.LENGTH_SHORT);
                   toast.setGravity(Gravity.CENTER,0,200);

                   toast.show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Snackbar.make(findViewById(android.R.id.content),"Connection failed !",Snackbar.LENGTH_SHORT).show();
            }
        });




    }


}
