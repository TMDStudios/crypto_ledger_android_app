package com.tmdstudios.cryptoledger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private Button createProfileBtn;
    private Button loginBtn;
    private TextView textView;
    private EditText editText;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String tempAPI;
    private Boolean apiAccepted = false;
    private Boolean apiChecked = false;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.scrollingText);
        textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        textView.setSelected(true);

        editText = findViewById(R.id.APIKey);

        createProfileBtn = findViewById(R.id.createProfile);
        createProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guest();
            }
        });

        loginBtn = findViewById(R.id.login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logIn();
            }
        });

        getAPI();
    }

    private void getAPI(){
        intent = new Intent(this, HomeActivity.class);
        sharedPreferences = MainActivity.this.getPreferences(MODE_PRIVATE);
        tempAPI = sharedPreferences.getString("APIKey", "");
        editText.setText(tempAPI);
    }

    public void logIn(){
        if(editText.getText().length()>0){tempAPI=""+editText.getText();}
        Toast.makeText(MainActivity.this, "Checking API Key", Toast.LENGTH_SHORT).show();
        try{
            String url = "https://crypto-ledger.herokuapp.com/api/get-user-ledger/"+tempAPI;
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            if(response.length()>0){
                                apiAccepted = true;
                                tempAPI = ""+editText.getText();
                                editor = sharedPreferences.edit();
                                editor.putString("APIKey", tempAPI);
                                editor.apply();
                                intent.putExtra("api_key", tempAPI);
                                startActivity(intent);
                            }else{
//                                This might need work...
                                editText.setText("");
                                Toast.makeText(MainActivity.this, "Invalid API Key", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });

            RequestQueue mQueue = Volley.newRequestQueue(this);
            mQueue.add(request);

        }catch(Exception e){
            Toast.makeText(MainActivity.this, "Unable to process API Key", Toast.LENGTH_SHORT).show();
        }
    }

    public void guest(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}