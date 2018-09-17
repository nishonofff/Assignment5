package com.example.garik.assignment5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.garik.assignment5.Retrofit.GithubService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.garik.assignment5.Constants.Constants.USER_NAME_KEY;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.go_button)
    Button goButton;
    @BindView(R.id.search_user_edit_text)
    EditText searchUserEdittext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        searchUserEdittext.setSelection(searchUserEdittext.getText().length());

    }


    @OnClick(R.id.go_button)
    void onGoButtonClicked() {

        Intent intent = new Intent(this, UserInfoActivity.class);
        intent.putExtra(USER_NAME_KEY, searchUserEdittext.getText().toString());
        startActivity(intent);


    }


}
