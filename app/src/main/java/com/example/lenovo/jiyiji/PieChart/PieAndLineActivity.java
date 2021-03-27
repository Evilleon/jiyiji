package com.example.lenovo.jiyiji.PieChart;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.lenovo.jiyiji.Chart.PageFragment;
import com.example.lenovo.jiyiji.R;
import com.example.lenovo.jiyiji.Utils.ToastUtil;

public class PieAndLineActivity extends AppCompatActivity {
    private RadioButton piechart,linechart;//饼状图和折线图
    private Spinner select;//选择饼状图显示种类
    private RadioGroup chart;
    private PieChartFragment pieChartFragment;
    private PageFragment pageFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_and_line);
        /*piechart=findViewById(R.id.RB_PieChart);//得到饼状图
        linechart=findViewById(R.id.RB_LineChart);
        chart=findViewById(R.id.RG_chart);
        if( pageFragment == null){
            pageFragment=new PageFragment();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.FL_chart,pageFragment).commitAllowingStateLoss();



        chart.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.RB_PieChart:
                        /*if( pieChartFragment == null){
                            pieChartFragment=new PieChartFragment();
                        }
                        if( pageFragment == null){
                            pageFragment=new PageFragment();
                        }
                        getSupportFragmentManager().beginTransaction().replace(R.id.FL_chart,pageFragment).commitAllowingStateLoss();
                        break;
                    case R.id.RB_LineChart:
                        ToastUtil.showMsg(PieAndLineActivity.this,"制作中，尽请期待！");
                        break;

                }
            }
        });*/

    }
}
