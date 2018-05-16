package com.example.farrellmochammad.jarkomlan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ConnectActivity extends AppCompatActivity {

    public TextView IP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        Button btnConnect = (Button) findViewById(R.id.buttonConnect);
        TextView IP = (TextView)findViewById(R.id.IP);
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ConnectActivity.this,MonitoringActivity.class));
            }
        });
    }
}
