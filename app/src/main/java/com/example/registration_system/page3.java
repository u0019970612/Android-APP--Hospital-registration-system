package com.example.registration_system;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class page3 extends AppCompatActivity implements View.OnClickListener {
    static final String db_name = "registration_data";
    static final String tb_name = "patient";
    SQLiteDatabase db;
    Button btn, btn2;
    TextView txv;
    EditText edt;
    String str = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page3);

        btn = (Button) findViewById(R.id.button10);
        btn2 = (Button) findViewById(R.id.button13);
        txv = (TextView) findViewById(R.id.textView7);
        edt = (EditText) findViewById(R.id.editText);

        Intent it = getIntent();
        db = openOrCreateDatabase(db_name, Context.MODE_PRIVATE, null);
        str += it.getStringExtra("科別") + " ";
        str += it.getStringExtra("醫生") + " ";
        str += it.getStringExtra("看診日期") + " ";
        str += it.getStringExtra("診間") + " ";
        str += it.getStringExtra("診別") + "\n";
        txv.setText(str);

        btn.setOnClickListener(this);
        btn2.setOnClickListener(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //攔截
        }
        return true;
    }

    private void addData(String pid, String describe) {
        ContentValues cv = new ContentValues();
        cv.put("pid", pid);
        cv.put("describe", describe);
        db.insert(tb_name, null, cv);
    }

    @Override
    public void onClick(View v) {
        if (v == btn) {
            if (edt.getText().toString().compareTo("") != 0) {
                String createTable = "CREATE TABLE IF NOT EXISTS " + tb_name + "(pid varchar(32)," + "describe varchar(60))";
                db.execSQL(createTable);
                Cursor cu = db.rawQuery("SELECT * FROM " + tb_name + " WHERE pid = '" + edt.getText().toString() + "' AND describe = '" + txv.getText().toString() + "'", null);
                if (cu.getCount() == 0) {//無資料
                    new AlertDialog.Builder(this)
                            .setTitle("提醒")
                            .setMessage("是否確定要掛號")
                            .setIcon(R.drawable.ic_launcher_foreground)
                            .setPositiveButton("確定",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            addData(edt.getText().toString(), txv.getText().toString());
                                            db.close();
                                            Intent intent = new Intent();
                                            intent.setClass(page3.this, MainActivity.class);
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
                } else {
                    new AlertDialog.Builder(this)
                            .setTitle("提醒")
                            .setMessage("已掛過號!")
                            .setIcon(R.drawable.ic_launcher_foreground)
                            .setPositiveButton("確定",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // TODO Auto-generated method stub
                                        }
                                    }).show();
                }
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("提醒")
                        .setMessage("尚未填寫身分證號!")
                        .setIcon(R.drawable.ic_launcher_foreground)
                        .setPositiveButton("確定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // TODO Auto-generated method stub
                                    }
                                }).show();
            }
        } else if (v == btn2) {
            new AlertDialog.Builder(this)
                    .setTitle("確認視窗")
                    .setMessage("要返回哪一頁面?")
                    .setIcon(R.drawable.ic_launcher_foreground)
                    .setPositiveButton("功能選單",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    db.close();
                                    Intent intent = new Intent();
                                    intent.setClass(page3.this, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }
                            })
                    .setNegativeButton("科別",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    db.close();
                                    Intent intent = new Intent();
                                    intent.setClass(page3.this, page2.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }
                            }).show();
        }
    }
}