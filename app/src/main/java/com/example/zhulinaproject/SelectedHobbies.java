package com.example.zhulinaproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SelectedHobbies extends AppCompatActivity implements View.OnClickListener {

    ListView listView;
    List<SelectedsMask> lvSelecteds;
    ImageButton btnAccount, btnSelected,btnRandom;
    AdapterHobies pAdapter;
    RatingBar rtngBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_hobbies);

        rtngBar = findViewById(R.id.ratingBar);
        btnRandom = findViewById(R.id.btnRandom);
        btnAccount = findViewById(R.id.btnAccount);
        btnSelected = findViewById(R.id.btnSelected);

        listView = findViewById(R.id.listV);
        lvSelecteds = new ArrayList<>();
        pAdapter = new AdapterHobies(SelectedHobbies.this, lvSelecteds);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                OneHobies.id_hobies = Integer.parseInt(String.valueOf(id));
                Intent intent = new Intent(SelectedHobbies.this, OneHobies.class);
                intent.putExtra("id_selected", lvSelecteds.get(position).getId_selected());
                intent.putExtra("id_hobby", lvSelecteds.get(position).getId_hobby());
                intent.putExtra("personal_assessment", lvSelecteds.get(position).getPersonal_assessment());
                intent.putExtra("nameSlected", lvSelecteds.get(position).getNameSlected());
                intent.putExtra("photoSlected", lvSelecteds.get(position).getPhotoSlected());
                startActivity(intent);
            }
        });

        listView.setAdapter(pAdapter);
        new GetHobies().execute();
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

            case R.id.btnAccount:
                newIntent = new Intent(this, Account.class);
                startActivity(newIntent);
                break;
            case R.id.btnSelected:
                newIntent = new Intent(this, SelectedHobbies.class);
                startActivity(newIntent);
                break;
            case R.id.btnRandom:
                newIntent = new Intent(this, RandomHobbies.class);
                startActivity(newIntent);
                break;
        }
    }

    private class GetHobies extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("https://ngknn.ru:5001/NGKNN/ЖулинаАА/api/Selecteds?idUser=" + CurrentUser.id_user);
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
                JSONArray tempArray = new JSONArray(s);
                for (int i = 0; i < tempArray.length(); i++) {

                    JSONObject productJson = tempArray.getJSONObject(i);
                    SelectedsMask tempProduct = new SelectedsMask(
                            productJson.getInt("id_selected"),
                            productJson.getInt("id_hobby"),
                            productJson.getInt("id_users"),
                            productJson.getString("nameSlected"),
                            productJson.getInt("personal_assessment"),
                            productJson.getString("PhotoSlected")
                    );
                    lvSelecteds.add(tempProduct);
                    pAdapter.notifyDataSetInvalidated();
                }
            } catch (Exception ignored) {

            }
        }
    }
}