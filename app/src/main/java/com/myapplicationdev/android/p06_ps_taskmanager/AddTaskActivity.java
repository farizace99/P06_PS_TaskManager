package com.myapplicationdev.android.p06_ps_taskmanager;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity {

    EditText etName, etDesc, etDuration;
    Button btnInsert, btnCancel;
    int reqCode = 12345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        etName = findViewById(R.id.etName);
        etDesc = findViewById(R.id.etDesc);
        etDuration = findViewById(R.id.etDuration);
        btnInsert = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancel);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                DBHelper db = new DBHelper(AddTaskActivity.this);
                String desc = etDesc.getText().toString();
                int time = Integer.parseInt(etDuration.getText().toString());
                // Insert a task
                long inserted_id = db.insertTask(name, desc);
                db.close();

                if (inserted_id != -1) {
                    Toast.makeText(AddTaskActivity.this, "Insert successfully",
                            Toast.LENGTH_SHORT).show();
                }

                Intent i = new Intent();
                setResult(RESULT_OK, i);
                finish();

                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.SECOND, time);

                Intent intent = new Intent(AddTaskActivity.this,
                        Notification.class);
                intent.putExtra("task", name);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        AddTaskActivity.this, reqCode,
                        intent, PendingIntent.FLAG_CANCEL_CURRENT);

                AlarmManager am = (AlarmManager)
                        getSystemService(Activity.ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                        pendingIntent);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
