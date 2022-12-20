package com.example.zhulinaproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RandomHobbies extends AppCompatActivity implements View.OnClickListener {

    int IdHobies;
    private Button butRandom;
    private ImageButton btnSelectHobies, btnAccount, btnSelected, btnRandom;
    private TextView textDescription, textInvestment, textHobbyName, textLink;
    private ImageView Image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_hobbies);

        butRandom = findViewById(R.id.buttonRandom);
        btnSelectHobies = findViewById(R.id.btnSelectHobies);
        btnAccount = findViewById(R.id.btnAccount);
        btnSelected = findViewById(R.id.btnSelected);
        btnRandom = findViewById(R.id.btnRandom);
        textDescription = findViewById(R.id.textDescription);
        textInvestment = findViewById(R.id.textInvestment);
        textHobbyName = findViewById(R.id.textHobbyName);
        textLink = findViewById(R.id.textLink);
        Image = findViewById(R.id.imageView);
        new GetProducts().execute();
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        Intent newIntent;
        switch (v.getId()) {
            case R.id.buttonRandom:
                new GetProducts().execute();
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnAccount:
                newIntent = new Intent(this, Account.class);
                startActivity(newIntent);
                break;
            case R.id.btnSelected:
                newIntent = new Intent(this, SelectedHobbies.class);
                startActivity(newIntent);
                break;
            case R.id.btnSelectHobies:
                postData(IdHobies, CurrentUser.id_user, 0);
                break;
        }
    }

    private void postData(int id_hobby, int id_users, int personal_assessment) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ngknn.ru:5001/NGKNN/ЖулинаАА/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        SelectedsMask mask = new SelectedsMask(id_hobby, id_users, personal_assessment);

        Call<SelectedsMask> call = retrofitAPI.createPostSelecteds(mask);

        call.enqueue(new Callback<SelectedsMask>() {
            @Override
            public void onResponse(Call<SelectedsMask> call, Response<SelectedsMask> response) {
                Toast.makeText(RandomHobbies.this, "Хоби добавлено", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<SelectedsMask> call, Throwable t) {
                Toast.makeText(RandomHobbies.this, "Хоби не добавлено, возможно оно уже находится в вашем избраном", Toast.LENGTH_LONG).show();
            }
        });
    }

    private class GetProducts extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("https://ngknn.ru:5001/NGKNN/ЖулинаАА/api/Hobbies");
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
                JSONObject productJson = new JSONObject(s);

                IdHobies = productJson.getInt("hobby_id");
                textHobbyName.setText(productJson.getString("hobby1"));
                textDescription.setText(productJson.getString("short_description"));
                productJson.getString("photo");
                textLink.setText(productJson.getString("information_link"));
                textInvestment.setText(productJson.getInt("age_limit"));


            } catch (Exception ignored) {

            }
        }
    }
}