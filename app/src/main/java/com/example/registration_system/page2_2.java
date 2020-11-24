package com.example.registration_system;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class page2_2 extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    static final String db_name = "registration_data";
    static final String tb_name = "schedule";
    SQLiteDatabase db;
    String day, str;
    Button btn;
    TextView txv;
    Spinner sp;
    Calendar c = Calendar.getInstance();
    boolean yn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2_2);

        btn = (Button) findViewById(R.id.button15);
        txv = (TextView) findViewById(R.id.textView27);
        sp = (Spinner) findViewById(R.id.spinner);

        db = openOrCreateDatabase(db_name, Context.MODE_PRIVATE, null);
        Intent it = getIntent();
        str = it.getStringExtra("醫生");

        btn.setOnClickListener(this);
        txv.setOnClickListener(this);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        txv.setText(year + "/" + (month + 1) + "/" + dayOfMonth);
        int Week = 0;
        Week = (dayOfMonth + 2 * (month + 1) + 3 * ((month + 1) + 1) / 5 + year + year / 4 - year / 100 + year / 400) % 7;//計算星期
        String[] Day = {"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
        day = Day[Week];
        Cursor cu = db.rawQuery("SELECT day,ampm FROM " + tb_name + " WHERE name = '" + str + "'", null);
        if (cu.getCount() != 0) {
            if (cu.moveToFirst()) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                do {//逐筆讀出字串
                    if (cu.getString(0).compareTo(day) == 0) {
                        yn = true;
                        adapter.add(cu.getString(1));
                    }
                } while (cu.moveToNext());
                sp.setAdapter(adapter);
            }
        } else
            yn = false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(this)
                    .setTitle("確認視窗")
                    .setMessage("確定要返回嗎?")
                    .setIcon(R.drawable.ic_launcher_foreground)
                    .setPositiveButton("確定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    db.close();
                                    Intent intent = new Intent();
                                    intent.setClass(page2_2.this, page2.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }
                            })
                    .setNegativeButton("取消",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                }
                            }).show();
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v == btn) {
            if (!yn) {
                String msg = "";
                if (txv.getText().toString().compareTo("請選擇日期") == 0)
                    msg = "尚未選擇看診日期喔!";
                else
                    msg = "看診日期當天無看診喔!";
                new AlertDialog.Builder(this)
                        .setTitle("提醒")
                        .setMessage(msg)
                        .setIcon(R.drawable.ic_launcher_foreground)
                        .setPositiveButton("確定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).show();
            } else {
                Cursor cu = db.rawQuery("SELECT * FROM " + tb_name + " WHERE name = '" + str + "' AND day = '" + day + "' AND ampm = '" + sp.getSelectedItem() + "'", null);
                if (cu.moveToFirst()) {
                    Intent intent = new Intent();
                    intent.setClass(this, page3.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    str = cu.getString(0);
                    intent.putExtra("科別", str);
                    str = cu.getString(1);
                    intent.putExtra("醫生", str);
                    str = txv.getText().toString();
                    intent.putExtra("看診日期", str);
                    str = cu.getString(3);
                    intent.putExtra("診間", str);
                    str = cu.getString(4);
                    intent.putExtra("診別", str);
                    db.close();
                    startActivity(intent);
                }
            }
        } else if (v == txv) {
            new DatePickerDialog(this, this,
                    c.get(Calendar.YEAR),
                    c.get(Calendar.MONTH),
                    c.get(Calendar.DAY_OF_MONTH)).show();
        }
    }
}
