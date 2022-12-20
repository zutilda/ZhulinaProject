package com.example.zhulinaproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Account extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btnLiave, btnAccount, btnSelected, btnRandom;
    private TextView textCountSelectedHobies, textAges, textMail, textClientName;
    private ImageView Image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        btnAccount = findViewById(R.id.btnAccount);
        btnSelected = findViewById(R.id.btnSelected);
        btnRandom = findViewById(R.id.btnRandom);
        btnLiave = findViewById(R.id.btnLiave);
        textClientName = findViewById(R.id.textClientName);
        textMail = findViewById(R.id.textMail);
        textAges = findViewById(R.id.textAges);
        textCountSelectedHobies = findViewById(R.id.textCountSelectedHobies);
        Image = findViewById(R.id.MainLogo);
        textClientName.setText(CurrentUser.surname + " " + CurrentUser.name + " " + CurrentUser.patronymic);
        textMail.setText(CurrentUser.login);
        textAges.setText(CurrentUser.age + " ");
        new GetProducts().execute();
    }


    @Override
    public void onClick(View v) {
        Intent newIntent;
        switch (v.getId()) {
            case R.id.btnLiave:
                newIntent = new Intent(this, MainActivity.class);
                startActivity(newIntent);
                break;
            case R.id.btnAccount:
                newIntent = new Intent(this, Account.class);
                startActivity(newIntent);
                break;
            case R.id.btnRandom:
                newIntent = new Intent(this, RandomHobbies.class);
                startActivity(newIntent);
                break;
            case R.id.btnSelected:
                newIntent = new Intent(this, SelectedHobbies.class);
                startActivity(newIntent);
                break;

        }
    }

    private class GetProducts extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("https://ngknn.ru:5001/NGKNN/ЖулинаАА/api/Selecteds/" + CurrentUser.id_user);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                return result.toString();

            } catch (Exception exception) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                textCountSelectedHobies.setText(s.replace("\\n","\n").replace("\"",""));

            } catch (Exception ignored) {

            }
        }
    }

}