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
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class page2_1 extends AppCompatActivity implements View.OnClickListener{
    static final String db_name="registration_data";
    static final String tb_name="schedule";
    SQLiteDatabase db;
    RadioGroup radioGroup;
    Button btn;
    String branch,str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2_1);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        btn = (Button)findViewById(R.id.button16);

        Intent it = getIntent();
        branch = it.getStringExtra("科別");
        db = openOrCreateDatabase(db_name, Context.MODE_PRIVATE,null);
        Cursor c = db.rawQuery("SELECT name FROM "+tb_name+" WHERE branch = '"+branch+"'",null);
        String old = "";
        if(c.moveToFirst()){
                do{//逐筆讀出字串
                    if(c.getString(0).compareTo(old)!=0)
                    {
                        RadioButton radioButton = new RadioButton(this);
                        RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
                        //设置RadioButton边距 (int left, int top, int right, int bottom)
                        lp.setMargins(32,15,32,15);
                        //设置文字距离四周的距离
                        radioButton.setPadding(10, 0, 0, 0);
                        radioButton.setTextSize(24);
                        //设置文字
                        old=c.getString(0);
                        radioButton.setText(""+c.getString(0));
                        radioGroup.addView(radioButton);
                    }
                }while(c.moveToNext());
        }

        btn.setOnClickListener(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            Intent intent = new Intent();
            intent.setClass(this,page2.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if(v == btn)
        {
            if(radioGroup.getCheckedRadioButtonId() == -1) {
                new AlertDialog.Builder(this)
                        .setTitle("提醒")
                        .setMessage("尚未選取醫生喔!")
                        .setIcon(R.drawable.ic_launcher_foreground)
                        .setPositiveButton("確定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // TODO Auto-generated method stub
                                    }
                                }).show();
            }
            else {
                db.close();
                Intent intent = new Intent();
                intent.setClass(this, page2_2.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                RadioButton rb = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                str = rb.getText().toString();
                intent.putExtra("醫生", str);
                startActivity(intent);
            }
        }
    }
}
