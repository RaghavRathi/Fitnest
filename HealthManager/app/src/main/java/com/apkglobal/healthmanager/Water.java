package com.apkglobal.healthmanager;

import android.content.Intent;
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
import org.w3c.dom.Text;

/**
 * Created by dell on 7/31/2017.
 */

public class Water extends Fragment {
    public static final String DATA_URL = "http://www.aptronnoida.com/Raghav/search_email.php?email=";

    public static final String KEY_AGE = "age";
    public static final String KEY_HEIGHT = "height";
    public static final String KEY_WEIGHT = "weight";
    public static final String JSON_ARRAY = "server_result";

    Methords methords;
    EditText et_age, et_height, et_weight;
    String age, height, weight;
    Button btn_determine;
    TextView tv_determine,tv_click;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.water, container, false);

        methords = new Methords(getActivity());
        et_age = (EditText) view.findViewById(R.id.et_age);
        et_height = (EditText) view.findViewById(R.id.et_height);
        et_weight = (EditText) view.findViewById(R.id.et_weight);
        btn_determine = (Button) view.findViewById(R.id.btn_determine);
        tv_determine = (TextView) view.findViewById(R.id.tv_determine);
        getData();
        btn_determine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                age = et_age.getText().toString();
                weight = et_weight.getText().toString();
                height = et_height.getText().toString();
                int userage, userheight, userweight;
                userage = Integer.parseInt(age);
                userheight = Integer.parseInt(height);
                userweight = Integer.parseInt(weight);
                if ((userage > 0 && userage <= 5) && (userweight > 1 && userweight <= 20)) {
                    tv_determine.setText("2-4 GLASSES PER DAY");
                } else if ((userage > 5 && userage <= 15) && (userweight > 20 && userweight <= 40)) {
                    tv_determine.setText("6-10 GLASSES PER DAY");
                } else if ((userage > 15 && userage <= 25) && (userweight > 40 && userweight <= 60)) {
                    tv_determine.setText("8-12 GLASSES PER DAY");
                } else if ((userage > 25 && userage <= 64) && (userweight > 60 && userweight <= 80)) {
                    tv_determine.setText("10-14 GLASSES PER DAY");
                }
                else {
                    tv_determine.setText("8-12 GLASSES PER DAY");
                }
            }
        });
        tv_click=(TextView)view.findViewById(R.id.tv_click);
        tv_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent image = new Intent(getActivity(),WaterTwo.class);
                startActivity(image);
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

