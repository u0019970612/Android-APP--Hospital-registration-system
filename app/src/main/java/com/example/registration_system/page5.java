package com.example.registration_system;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.Spinner;
import android.widget.TextView;

public class page5 extends AppCompatActivity implements View.OnClickListener {
    static final String db_name = "registration_data";
    static final String tb_name = "patient";
    SQLiteDatabase db;
    Button btn, btn2;
    Spinner sp;
    TextView txv;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page5);

        btn = (Button) findViewById(R.id.button14);//確認
        btn2 = (Button) findViewById(R.id.button18);//取消掛號
        txv = (TextView) findViewById(R.id.textView13);
        sp = (Spinner) findViewById(R.id.spinner2);

        Intent it = getIntent();
        str = it.getStringExtra("身分證號");
        db = openOrCreateDatabase(db_name, Context.MODE_PRIVATE, null);
        Cursor cu = db.rawQuery("SELECT describe FROM " + tb_name + " WHERE pid = '" + str + "'", null);
        if (cu.getCount() != 0) {
            if (cu.moveToFirst()) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                do {//逐筆讀出字串
                    adapter.add(cu.getString(0));
                    txv.setText(txv.getText().toString() + cu.getString(0) + "\n");
                } while (cu.moveToNext());
                sp.setAdapter(adapter);
            }
        } else
            txv.setText("已無掛號紀錄!\n按下確定按鈕即可返回查詢/取消!");


        btn.setOnClickListener(this);
        btn2.setOnClickListener(this);
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
                                    Intent intent = new Intent();
                                    intent.setClass(page5.this, page4.class);
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
            db.close();
            Intent intent = new Intent();
            intent.setClass(this, page4.class);
            startActivity(intent);
        } else if (v == btn2) {
            new AlertDialog.Builder(this)
                    .setTitle("確認視窗")
                    .setMessage("確定要取消此筆掛號嗎?")
                    .setIcon(R.drawable.ic_launcher_foreground)
                    .setPositiveButton("確定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String del = "DELETE FROM " + tb_name + " WHERE pid = '" + str + "' AND describe = '" + sp.getSelectedItem()  + "'";
                                    db.execSQL(del);
                                    db.close();
                                    Intent intent = new Intent();
                                    intent.setClass(page5.this, page5.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.putExtra("身分證號", str);
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
    }
}