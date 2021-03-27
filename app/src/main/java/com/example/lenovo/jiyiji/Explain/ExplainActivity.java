package com.example.lenovo.jiyiji.Explain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.lenovo.jiyiji.MainActivity;
import com.example.lenovo.jiyiji.R;

import org.w3c.dom.Text;

public class ExplainActivity extends AppCompatActivity {
    private TextView explain,back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.CustomTitleBarTheme);  //声明标题栏，注意这一句的位置
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_explain);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title_bar);
        TextView title_show = findViewById(R.id.tv_title_show);
        back=findViewById(R.id.back);
        title_show.setText("使用说明");
        explain=findViewById(R.id.explain);
        explain.setText("这是一款以记账为基础功能，延伸出少量衍生功能的记账软件。\n" +
                "安装环境：Android系统4.0版本及以上，apk安装使用。\n" +
                "使用向导，打开页面后：导航栏在下方：分别是记账选项，账单，统计以及新闻。\n" +
                "记账选项是最基础的功能，点开后可以选择是收入还是支出，在下面是选择分类，选择时间以及备注。\n" +
                "账单选项是显示账单内容，包括金额，区别收支，以及分类等\n" +
                "统计选项：用图表展示账本内容，包括饼状图以及树状图。\n" +
                "\n" +
                "APP的最上方是账本选项，可以更换账本");
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
