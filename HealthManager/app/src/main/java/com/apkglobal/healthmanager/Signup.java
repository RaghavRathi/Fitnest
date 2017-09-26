package com.apkglobal.healthmanager;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {
    EditText et_name, et_gender, et_age, et_height, et_weight, et_email, et_pass, et_confirmpass;
    Button btn_submit;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        et_name = (EditText) findViewById(R.id.et_name);
        et_age = (EditText) findViewById(R.id.et_age);
        et_height = (EditText) findViewById(R.id.et_height);
        et_weight = (EditText) findViewById(R.id.et_weight);
        et_email = (EditText) findViewById(R.id.et_email);
        et_gender = (EditText) findViewById(R.id.et_gender);
        et_pass = (EditText) findViewById(R.id.et_pass);
        et_confirmpass = (EditText) findViewById(R.id.et_confirmpass);
        final String pass, confirmpass;
        pass = et_pass.getText().toString();
        confirmpass = et_confirmpass.getText().toString();
        progressDialog = new ProgressDialog(Signup.this);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pass.equals(confirmpass)) {
                    registerUser();

                } else {
                    et_confirmpass.setError("Passwords do not match");
                }

            }

        });
    }


    private void registerUser() {
        final String name = et_name.getText().toString();
        final String age = et_age.getText().toString();
        final String height = et_height.getText().toString();
        final String weight = et_weight.getText().toString();
        final String email = et_email.getText().toString();
        final String password = et_pass.getText().toString();
        final String gender = et_gender.getText().toString();

        progressDialog.setMessage("Registering User .....");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://www.aptronnoida.com/Raghav/insert.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Intent i = new Intent(Signup.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                        Toast.makeText(Signup.this, "Registration Successfull", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Toast.makeText(Signup.this, "nhi hua submit" , Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("height", height);
                params.put("age", age);
                params.put("weight", weight);
                params.put("gender", gender);
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }

    public void onBackPressed() {
        AlertDialog.Builder ab = new AlertDialog.Builder(Signup.this);
        ab.setIcon(R.drawable.ic);
        ab.setTitle("Exit Application");
        ab.setMessage("Do you want to exit the application yes or no");
        ab.setCancelable(true);
        ab.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        ab.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        ab.create();
        ab.show();
    }

}
