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
import android.widget.EditText;

public class page4 extends AppCompatActivity implements View.OnClickListener{
    static final String db_name="registration_data";
    static final String tb_name="patient";
    SQLiteDatabase db;
    Button btn;
    EditText edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page4);

        btn=(Button)findViewById(R.id.button8);//確認
        edt=(EditText)findViewById(R.id.editText2);

        db=openOrCreateDatabase(db_name, Context.MODE_PRIVATE,null);
        String createTable = "CREATE TABLE IF NOT EXISTS " + tb_name + "(pid varchar(32)," + "describe varchar(60))";
        db.execSQL(createTable);
        btn.setOnClickListener(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            db.close();
            Intent intent=new Intent();
            intent.setClass(page4.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if(edt.getText().toString().compareTo("")!=0) {
            String str;
            Cursor cu = db.rawQuery("SELECT * FROM " + tb_name + " WHERE pid = '" + edt.getText().toString() + "'", null);
            if (cu.moveToFirst()) {
                Intent intent = new Intent();
                intent.setClass(this, page5.class);
                str = cu.getString(0);
                intent.putExtra("身分證號", str);
                db.close();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
            else{
                new AlertDialog.Builder(this)
                        .setTitle("提醒")
                        .setMessage("無掛號資料!")
                        .setIcon(R.drawable.ic_launcher_foreground)
                        .setPositiveButton("確定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // TODO Auto-generated method stub
                                    }
                                }).show();
            }
        }
        else {
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
    }
}
