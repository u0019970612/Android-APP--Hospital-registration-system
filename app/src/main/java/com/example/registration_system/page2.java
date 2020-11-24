package com.example.registration_system;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class page2 extends AppCompatActivity implements View.OnClickListener {
    Button btn, btn2, btn3, btn4, btn5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);

        btn = (Button) findViewById(R.id.button3);
        btn2 = (Button) findViewById(R.id.button4);
        btn3 = (Button) findViewById(R.id.button5);
        btn4 = (Button) findViewById(R.id.button6);
        btn5 = (Button) findViewById(R.id.button7);

        btn.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.setClass(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setClass(page2.this, page2_1.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        String str = "";
        if (v == btn)
            str = "內科";
        else if (v == btn2)
            str = "外科";
        else if (v == btn3)
            str = "牙科";
        else if (v == btn4)
            str = "婦產科";
        else if (v == btn5)
            str = "中醫科";
        intent.putExtra("科別", str);
        startActivity(intent);
    }
}
