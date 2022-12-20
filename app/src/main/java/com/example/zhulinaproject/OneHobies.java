package com.example.zhulinaproject;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OneHobies extends AppCompatActivity {

    public static int id_hobies, id_selected;
    RatingBar rtngBar;
    TextView HobiesName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_hobies);
        rtngBar = findViewById(R.id.ratingBar);
        rtngBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                putData(id_hobies, CurrentUser.id_user, (int) rating);
            }
        });
        HobiesName = findViewById(R.id.HobiesName);
        setData();
    }

    private void putData(int id_hobby, int id_users, int personal_assessment) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ngknn.ru:5001/NGKNN/ЖулинаАА/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        SelectedsMask mask = new SelectedsMask(id_hobby, id_selected, id_users, personal_assessment);

        Call<SelectedsMask> call = retrofitAPI.createPutSelecteds(id_selected, mask);


        call.enqueue(new Callback<SelectedsMask>() {
            @Override
            public void onResponse(Call<SelectedsMask> call, Response<SelectedsMask> response) {
                Toast.makeText(OneHobies.this, "Оценка изменена", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<SelectedsMask> call, Throwable t) {
                Toast.makeText(OneHobies.this, "Ошибка: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setData() {
        Bundle arg = getIntent().getExtras();
        id_hobies = arg.getInt("id_hobby");
        id_selected = arg.getInt("id_selected");
        rtngBar.setRating(arg.getInt("personal_assessment"));
        HobiesName.setText(arg.getString("nameSlected"));
        //Image = arg.getString("photoSlected");

    }
}