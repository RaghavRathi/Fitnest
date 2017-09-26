package com.apkglobal.healthmanager;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    public static final String DATA_URL = "http://www.aptronnoida.com/Raghav/fetch_details.php";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String JSON_ARRAY = "server_result";
    Button btn_signin, btn_register, btn_facebook, btn_gmail;
    EditText et_email, et_password;
    Methords methords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_pass);
        methords = new Methords(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        btn_facebook = (Button) findViewById(R.id.btn_facebook);
        btn_gmail = (Button) findViewById(R.id.btn_gmail);
        btn_signin = (Button) findViewById(R.id.btn_signin);
        btn_register = (Button) findViewById(R.id.btn_register);


        btn_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/"));
                startActivity(intent);
            }
        });


        btn_gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.gmail.com"));
                startActivity(intent);

            }
        });


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, Signup.class);
                startActivity(intent);
            }
        });

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });


    }

    public void userLogin() {
        String semail = et_email.getText().toString().trim();
        String spassword = et_password.getText().toString();
        if (semail.equals("")) {
            et_email.setError("Email Cannot be blank");
        } else if (spassword.length() == 0) {
            et_password.setError("Password must be entered");
        } else {
            String url = DATA_URL;


            final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
            pd.setMessage("Loading...");
            pd.show();


            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //loading.dismiss();
                    if (response.equals("null")) {
                        Toast.makeText(LoginActivity.this, "user not found", Toast.LENGTH_LONG).show();
                    } else {
                        pd.dismiss();
                        showJSON(response);
                    }

                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    });

            RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
            requestQueue.add(stringRequest);
        }
    }


    private void showJSON(String response) {
        String email = "";
        String password = "";
        String s_email = et_email.getText().toString();
        String s_pass = et_password.getText().toString();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(JSON_ARRAY);
            int j = result.length();
            for (int i = 0; i < j; i++) {
                JSONObject collegeData = result.getJSONObject(i);
                email = collegeData.getString(KEY_EMAIL);
                password = collegeData.getString(KEY_PASSWORD);
                if (email.equals(s_email)) {
                    methords.saveLoginDetails(email);
                    if (password.equals(s_pass)) {
                        Toast.makeText(LoginActivity.this, "LOGIN SUCCESSFUL...WELCOME", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    } else {
                        et_password.setError("PASSWORDS DO NOT MATCH");
                    }
                } else {

                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

