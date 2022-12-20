package com.example.zhulinaproject;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrationActivity extends AppCompatActivity {

    EditText etLogin, checkBox, etPatronumic, etSurname, etName;
    com.google.android.material.textfield.TextInputLayout edPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        edPassword = findViewById(R.id.edPassword);
        etLogin = findViewById(R.id.etLogin);
        checkBox = findViewById(R.id.edAges);
        etPatronumic = findViewById(R.id.etPatronumic);
        etSurname = findViewById(R.id.etSurname);
        etName = findViewById(R.id.etName);

    }

    public void onLogIn(View view) {
        Intent logIntent = new Intent(this, MainActivity.class);
        startActivity(logIntent);
    }

    public void onRegistration(View v) {
        postData(edPassword.getEditText().getText().toString(), etLogin.getText().toString(), Integer.parseInt(checkBox.getText().toString())
                , etPatronumic.getText().toString(), etName.getText().toString(), etSurname.getText().toString());
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    private void postData(String password, String login, int age, String patronymic, String name, String surname) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ngknn.ru:5001/NGKNN/ЖулинаАА/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        UsersMask mask = new UsersMask(password, login, age, patronymic, name, surname);

        Call<UsersMask> call = retrofitAPI.createPostUsers(password, mask);

        call.enqueue(new Callback<UsersMask>() {
            @Override
            public void onResponse(Call<UsersMask> call, Response<UsersMask> response) {
                Toast.makeText(RegistrationActivity.this, "Акаунт добавлен", Toast.LENGTH_LONG).show();
                etLogin.setText("");
                checkBox.setText("");
                etPatronumic.setText("");
                etSurname.setText("");
                etName.setText("");
            }

            @Override
            public void onFailure(Call<UsersMask> call, Throwable t) {
                Toast.makeText(RegistrationActivity.this, "Не возможно доббавить пользователя с таким логином", Toast.LENGTH_LONG).show();
            }
        });
    }
}