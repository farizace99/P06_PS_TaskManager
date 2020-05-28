package com.myapplicationdev.android.p06_ps_taskmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAdd;
    ListView lv;

    ArrayAdapter aa;
    ArrayList<Task> al;

    int requestCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAddTask);
        lv = findViewById(R.id.lvTask);

        DBHelper db = new DBHelper(MainActivity.this);
        al = db.getAllTasks();
        aa = new ArrayAdapter<Task>(this, android.R.layout.simple_list_item_1, al);
        lv.setAdapter(aa);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivityForResult(i, requestCode);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 9){
            if (requestCode == requestCode) {
                DBHelper db = new DBHelper(MainActivity.this);
                al.clear();
                al.addAll(db.getAllTasks());
                db.close();
                aa.notifyDataSetChanged();
            }
        }
    }

}
