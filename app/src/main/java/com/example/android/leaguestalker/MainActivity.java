package com.example.android.leaguestalker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_search = findViewById(R.id.btn_search);
        EditText et_summoner_name = findViewById(R.id.et_summoner_name);
        ListView lv_summoners = findViewById(R.id.lv_summoners);

        LeagueAPIHelper leagueAPIHelper = new LeagueAPIHelper(MainActivity.this);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                leagueAPIHelper.getSummonerIdBySummonerName(et_summoner_name.getText().toString(), new LeagueAPIHelper.SearchBySummonerNameListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String id) {
                        Toast.makeText(MainActivity.this, id, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}