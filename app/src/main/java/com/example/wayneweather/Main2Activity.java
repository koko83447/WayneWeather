package com.example.wayneweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    TextView mWeather;
    Table table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Bundle data = getIntent().getExtras();
        table = (Table) data.getSerializable("weather");

        mWeather = findViewById(R.id.textView);
        mWeather.setText(table.getStartTime()+"\n"+table.getEndTime()+"\n"+table.getParameterName()+table.getParameterUnit());

    }


    @Override
    protected void onRestart() {
        Toast.makeText(Main2Activity.this,"歡迎回來",Toast.LENGTH_LONG).show();
        super.onRestart();
    }
}
