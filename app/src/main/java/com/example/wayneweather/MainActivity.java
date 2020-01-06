package com.example.wayneweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private String response;
    private Boolean firstStart = true;
    String path="https://opendata.cwb.gov.tw/api/v1/rest/datastore/F-C0032-001?Authorization=CWB-D9B8EACB-6706-4717-BC49-1436EC8B08CB&locationName=%E8%87%BA%E5%8C%97%E5%B8%82&elementName=MinT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstStart = getSharedPreferences("Weather",MODE_MULTI_PROCESS).getBoolean("Start",firstStart);
        if (!firstStart)Toast.makeText(MainActivity.this,"歡迎回來",Toast.LENGTH_LONG).show();

        receive();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MyAdapter myAdapter = new MyAdapter(MainActivity.this);
                myAdapter.weatherList(Mint.getTable(response));
                RecyclerView recyclerView = findViewById(R.id.recycle_view);
                recyclerView.setAdapter(myAdapter);

                LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                recyclerView.setLayoutManager(layoutManager);

                myAdapter.setListener(new MyAdapter.Listener() {
                    public void onClick(int position) {
                        Table table = Mint.getTable(response).get(position);
                        Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("weather",table);
                        intent.putExtras(bundle);
                        startActivity(intent);

                    }
                });
            }
        },2000);
    }

    private void receive() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        response=executeHttpGet();
                    }
                }
        ).start();
    }

    private String executeHttpGet() {

        HttpURLConnection con=null;
        InputStream in=null;
        try {
            con= (HttpURLConnection) new URL(path).openConnection();
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            con.setDoInput(true);
            con.setRequestMethod("GET");
            if(con.getResponseCode()==200){
                in=con.getInputStream();
                return parseInfo(in);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String parseInfo(InputStream in) throws IOException {
        BufferedReader br=new BufferedReader(new InputStreamReader(in));
        StringBuilder sb=new StringBuilder();
        String line=null;
        while ((line=br.readLine())!=null){
            sb.append(line+"\n");
        }
        return sb.toString();
    }
    @Override
    protected void onDestroy() {
        firstStart = false;
        SharedPreferences preferences = getSharedPreferences("Weather",MODE_MULTI_PROCESS);
        preferences.edit().putBoolean("Start", firstStart);
        super.onDestroy();
    }
}
