package com.apkglobal.healthmanager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

/**
 * Created by dell on 7/31/2017.
 */

public class Sleep extends Fragment {
    public static final String DATA_URL = "http://www.aptronnoida.com/Raghav/search_email.php?email=";

    public static final String KEY_AGE = "age";
    public static final String KEY_HEIGHT = "height";
    public static final String KEY_WEIGHT = "weight";
    public static final String JSON_ARRAY = "server_result";

    Methords methords;
    EditText et_age, et_height, et_weight;
    Button btn_determine;
    TextView tv_determine;
    String age, weight, height;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sleep, container, false);

        methords = new Methords(getActivity());
        tv_determine = (TextView) view.findViewById(R.id.tv_determine);
        et_age = (EditText) view.findViewById(R.id.et_age);
        et_height = (EditText) view.findViewById(R.id.et_height);
        et_weight = (EditText) view.findViewById(R.id.et_weight);

        btn_determine = (Button) view.findViewById(R.id.btn_determine);

        getData();
        btn_determine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                age = et_age.getText().toString();
                weight = et_weight.getText().toString();
                height = et_height.getText().toString();
                int userage = Integer.parseInt(age);
                int userheight = Integer.parseInt(height);
                int userweight = Integer.parseInt(weight);

                if (userage > 0 && userage <= 3) {
                    tv_determine.setText("12-14 HOURS");
                } else if (userage > 3 && userage <= 6) {
                    tv_determine.setText("10-13 HOURS");
                } else if (userage > 6 && userage <= 13) {
                    tv_determine.setText("9-11 HOURS");
                } else if (userage > 14 && userage <= 17) {
                    tv_determine.setText("8-10 HOURS");
                } else if (userage > 18 && userage <= 25) {
                    tv_determine.setText("7-9 HOURS");
                } else if (userage > 26 && userage <= 64) {
                    tv_determine.setText("7-9 HOURS");
                } else {
                    tv_determine.setText("7-8 HOURS");
                }
            }
        });
        return view;
    }


    private void getData() {

        String email = methords.getEmail().toString();
        String url = DATA_URL + email;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //loading.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response) {
        String age = "";
        String height = "";
        String weight = "";
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
            age = collegeData.getString(KEY_AGE);
            height = collegeData.getString(KEY_HEIGHT);
            weight = collegeData.getString(KEY_WEIGHT);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        et_age.setText(age);
        et_height.setText(height);
        et_weight.setText(weight);


    }
}
