package com.example.pro1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView list;
    private EditText edt1;
    private  EditText edt2;
    private Button btn;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    public static final String DATA="DATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list=findViewById(R.id.taskListView);
        edt1=findViewById(R.id.titleEditText);
        edt2=findViewById(R.id.dueDateEditText);
        btn=findViewById(R.id.addButton);
        setupSharedPrefs();
        updateData();
    }
    public void setupSharedPrefs(){
        prefs= PreferenceManager.getDefaultSharedPreferences(this);
        editor=prefs.edit();
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
            list.setAdapter(adapter);
        }
    }
    public void clkbtn(View view) {
        String title=edt1.getText().toString();
        String date=edt2.getText().toString();
        String data=prefs.getString(DATA,"");
        edt1.setText("");
        edt2.setText("");
        Gson gson=new Gson();
        Task [] datatask=gson.fromJson(data,Task[].class);
        int p=0;
        if(data!="")
            p=datatask.length;
        Task [] newtask=new Task[p+1];
        if(datatask!=null)
            System.arraycopy(datatask,0,newtask,0,p);
        newtask[p]=new Task(title,date);
        String retdata=gson.toJson(newtask);
        editor.putString(DATA,retdata);
        editor.commit();
        ArrayList<String> arr=new ArrayList<String>();
        for(int i=0;i<newtask.length;i++){
            arr.add("Name: "+newtask[i].getTitle()+"\nTill Date: "+newtask[i].getDate());
        }
        ArrayAdapter adapter;
        adapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,arr);
        list.setAdapter(adapter);

    }

    public void clkbtn2(View view) {
        Intent intent =new Intent(this,ListAct.class);
        startActivity(intent);
    }
}