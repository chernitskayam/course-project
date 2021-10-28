package com.example.arduino_plug;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    final Random random = new Random();
    TextView tempValue;
    TextView vlazhValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "Мы в дерьме", Toast.LENGTH_SHORT).show();
        setRandom();
    }

    void setRandom () {
        int minV = 40;
        int maxV = 100;
        int minT = -40;
        int maxT = 40;
        int diff = maxT - minT;
        int temp = random.nextInt(diff + 1);
        temp += minT;
        tempValue = findViewById(R.id.tempValue);
        tempValue.setText(String.valueOf(temp));
        diff = maxV - minV;
        int vlazh = random.nextInt(diff + 1);
        vlazh += minV;
        vlazhValue = findViewById(R.id.vlazhValue);
        vlazhValue.setText(String.valueOf(vlazh));
    }

    public void update(View view)
    {
        setRandom();
        Toast.makeText(this, "Данные обновлены", Toast.LENGTH_SHORT).show();
    }

    public void send(View view)
    {
        String url = "http://api.blueberry.gq/send.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, response.trim(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getParams() throws AuthFailureError{
                HashMap<String, String> value = new HashMap<>();
                tempValue = findViewById(R.id.tempValue);
                value.put("temp", tempValue.getText().toString());
                Toast.makeText(MainActivity.this, "Данные отправлены", Toast.LENGTH_SHORT).show();
                return value;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
    }
}