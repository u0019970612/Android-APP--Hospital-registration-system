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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    static final String db_name = "registration_data";//資料庫
    static final String tb_name = "schedule";
    SQLiteDatabase db;
    Button btn, btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.button);
        btn2 = (Button) findViewById(R.id.button2);
        btn.setOnClickListener(this);
        btn2.setOnClickListener(this);

        //醫生科別資料表
        db = openOrCreateDatabase(db_name, Context.MODE_PRIVATE, null);
        String createTable = "CREATE TABLE IF NOT EXISTS " + tb_name + "(branch varchar(32)," + "name varchar(32)," + "day varchar(16)," + "room varchar(16)," + "ampm varchar(16))";
        db.execSQL(createTable);
        Cursor cu = db.rawQuery("SELECT * FROM " + tb_name, null);
        if (cu.getCount() == 0) {//無資料
            addData("中醫科", "陳怡真", "星期一", "第106診", "上午診");
            addData("中醫科", "陳怡真", "星期三", "第106診", "上午診");
            addData("中醫科", "陳怡真", "星期三", "第106診", "下午診");
            addData("中醫科", "陳怡真", "星期四", "第106診", "上午診");
            addData("中醫科", "林經偉", "星期一", "第102診", "下午診");
            addData("中醫科", "林經偉", "星期二", "第102診", "上午診");
            addData("中醫科", "林經偉", "星期四", "第102診", "下午診");
            addData("中醫科", "陳中奎", "星期二", "第105診", "下午診");
            addData("中醫科", "陳中奎", "星期五", "第105診", "上午診");
            addData("中醫科", "陳中奎", "星期五", "第105診", "下午診");

            addData("牙科", "蘇明仁", "星期一", "第185診", "上午診");
            addData("牙科", "蘇明仁", "星期一", "第185診", "下午診");
            addData("牙科", "蘇明仁", "星期三", "第185診", "下午診");
            addData("牙科", "蘇明仁", "星期二", "第185診", "上午診");
            addData("牙科", "蔣依婷", "星期五", "第183診", "下午診");
            addData("牙科", "蔣依婷", "星期五", "第183診", "上午診");
            addData("牙科", "蔣依婷", "星期四", "第183診", "下午診");
            addData("牙科", "黃銘傑", "星期二", "第186診", "上午診");
            addData("牙科", "黃銘傑", "星期三", "第186診", "下午診");
            addData("牙科", "黃銘傑", "星期四", "第186診", "上午診");

            addData("婦產科", "朱堂元", "星期一", "第302診", "上午診");
            addData("婦產科", "朱堂元", "星期一", "第302診", "下午診");
            addData("婦產科", "朱堂元", "星期三", "第302診", "下午診");
            addData("婦產科", "朱堂元", "星期五", "第302診", "下午診");
            addData("婦產科", "丁大清", "星期二", "第301診", "上午診");
            addData("婦產科", "丁大清", "星期四", "第301診", "上午診");
            addData("婦產科", "丁大清", "星期四", "第301診", "下午診");
            addData("婦產科", "魏佑吉", "星期二", "第301診", "下午診");
            addData("婦產科", "魏佑吉", "星期三", "第301診", "上午診");
            addData("婦產科", "魏佑吉", "星期五", "第301診", "上午診");

            addData("內科", "謝仁哲", "星期一", "第136診", "上午診");
            addData("內科", "謝仁哲", "星期一", "第136診", "下午診");
            addData("內科", "謝仁哲", "星期四", "第136診", "下午診");
            addData("內科", "謝仁哲", "星期五", "第136診", "下午診");
            addData("內科", "鄭景仁", "星期二", "第136診", "上午診");
            addData("內科", "鄭景仁", "星期四", "第136診", "上午診");
            addData("內科", "鄭景仁", "星期五", "第136診", "下午診");
            addData("內科", "劉維新", "星期二", "第137診", "下午診");
            addData("內科", "劉維新", "星期三", "第137診", "上午診");
            addData("內科", "劉維新", "星期三", "第137診", "上午診");

            addData("外科", "李明哲", "星期二", "第136診", "下午診");
            addData("外科", "李明哲", "星期五", "第136診", "上午診");
            addData("外科", "李明哲", "星期四", "第136診", "下午診");
            addData("外科", "李明哲", "星期五", "第136診", "下午診");
            addData("外科", "陳言丞", "星期一", "第136診", "上午診");
            addData("外科", "陳言丞", "星期一", "第136診", "下午診");
            addData("外科", "陳言丞", "星期三", "第136診", "上午診");
            addData("外科", "吳柏鋼", "星期二", "第137診", "上午診");
            addData("外科", "吳柏鋼", "星期三", "第137診", "下午診");
            addData("外科", "吳柏鋼", "星期四", "第137診", "上午診");
        }
        db.close();
    }

    private void addData(String branch, String name, String day, String room, String ampm) {
        ContentValues cv = new ContentValues();
        cv.put("branch", branch);
        cv.put("name", name);
        cv.put("day", day);
        cv.put("room", room);
        cv.put("ampm", ampm);
        db.insert(tb_name, null, cv);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(this)
                    .setTitle("確認視窗")
                    .setMessage("確定要結束應用程式嗎?")
                    .setIcon(R.drawable.ic_launcher_foreground)
                    .setPositiveButton("確定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    android.os.Process.killProcess(android.os.Process.myPid());
                                    System.exit(0);
                                }
                            })
                    .setNegativeButton("取消",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v == btn) {
            Intent intent = new Intent();
            intent.setClass(this, page2.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if (v == btn2) {
            Intent intent = new Intent();
            intent.setClass(this, page4.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
}
