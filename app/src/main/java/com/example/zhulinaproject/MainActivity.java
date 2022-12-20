package com.example.zhulinaproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean f = false;
    private Button btnLogIn;
    private TextView textReg;
    private EditText textLogin;
    private com.google.android.material.textfield.TextInputLayout textPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogIn = findViewById(R.id.btnLogIn);
        textReg = findViewById(R.id.txtRegistration);
        textLogin = findViewById(R.id.textLogin);
        textPassword = findViewById(R.id.textPassword);
    }

    public void onReg(View view) {
        Intent regIntent = new Intent(this, RegistrationActivity.class);
        startActivity(regIntent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogIn:
                try {
                    f = new IsUser().execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (f) {
                    new User().execute();
                    try {
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Intent newIntent = new Intent(this, RandomHobbies.class);
                    startActivity(newIntent);
                } else {
                    Toast.makeText(MainActivity.this, "Вы ввели неверный логин или пароль", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.txtRegistration:
                Intent newIntent = new Intent(this, RegistrationActivity.class);
                startActivity(newIntent);
                break;
        }

    }

    private class IsUser extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                String Login = textLogin.getText().toString(), Password = textPassword.getEditText().getText().toString();
                URL url = new URL("https://ngknn.ru:5001/NGKNN/ЖулинаАА/api/Users?loginUser={" + Login + "}&pass={" + Password + "}");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                url.openStream()));
                String inputLine;

                inputLine = in.readLine();


                in.close();
                if (Boolean.parseBoolean(inputLine)) {
                    return true;
                } else {
                    return false;
                }

            } catch (Exception exception) {
                Toast.makeText(MainActivity.this, "Произошла ошибка", Toast.LENGTH_LONG).show();
                return false;
            }
        }


    }

    private class User extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                String Login = textLogin.getText().toString(), Password = textPassword.getEditText().getText().toString();
                URL url = new URL("https://ngknn.ru:5001/NGKNN/ЖулинаАА/api/Users?password={" + Password + "}&loginUser={" + Login + "}");
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

                CurrentUser.id_user = productJson.getInt("id_user");
                CurrentUser.login = productJson.getString("login");
                CurrentUser.name = productJson.getString("name");
                CurrentUser.surname = productJson.getString("surname");
                CurrentUser.patronymic = productJson.getString("patronymic");
                CurrentUser.age = productJson.getInt("age");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
