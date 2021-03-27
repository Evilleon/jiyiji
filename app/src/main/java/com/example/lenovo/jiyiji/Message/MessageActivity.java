package com.example.lenovo.jiyiji.Message;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.lenovo.jiyiji.MainActivity;
import com.example.lenovo.jiyiji.R;

public class MessageActivity extends AppCompatActivity {
    private TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.CustomTitleBarTheme);  //声明标题栏，注意这一句的位置
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_message);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title_bar);
        TextView title_show = findViewById(R.id.tv_title_show);
        title_show.setText("信息");
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
