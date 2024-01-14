package com.example.pro1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.ArrayList;

public class ListAct extends AppCompatActivity {
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    public static final String DATA="DATA";
    private ListView list2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        list2=findViewById(R.id.list2);
        setupSharedPrefs();
        updateData();

    }
    public void setupSharedPrefs(){
        prefs= PreferenceManager.getDefaultSharedPreferences(this);
        editor=prefs.edit();
    }
    public void click(View view) {
        Intent intent =new Intent(this,MainActivity.class);
        startActivity(intent);
    }


    public void updateData(){
        String data=prefs.getString(DATA,"");
        Gson gson=new Gson();
        if(data!="") {
            Task[] datatask = gson.fromJson(data, Task[].class);
            int p = 0;
            ArrayList<String> arr = new ArrayList<String>();
            for (int i = 0; i < datatask.length; i++) {
                arr.add("Name: " + datatask[i].getTitle() + "\nTill Date: " + datatask[i].getDate());
            }
            ArrayAdapter adapter;
            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arr);
            list2.setAdapter(adapter);
        }
    }
}