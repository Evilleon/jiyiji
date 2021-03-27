package com.example.lenovo.jiyiji.PieChart;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.lenovo.jiyiji.AddAccount.BillBean;
import com.example.lenovo.jiyiji.AddAccount.DB;
import com.example.lenovo.jiyiji.BarChart.BarChartFragment;
import com.example.lenovo.jiyiji.Chart.PageFragment;
import com.example.lenovo.jiyiji.LineChart.LineChartFragment;
import com.example.lenovo.jiyiji.R;
import com.example.lenovo.jiyiji.Utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class ChartFragment extends Fragment {
    private ViewPager viewPager;
    private RadioButton piechart,linechart;//饼状图和折线图
    private RadioGroup chart;
    private PageFragment pageFragment;
    private LineChartFragment lineChartFragment;
    private BarChartFragment barChartFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_pie_and_line,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        piechart=view.findViewById(R.id.RB_PieChart);//得到饼状图
        linechart=view.findViewById(R.id.RB_LineChart);
        chart=view.findViewById(R.id.RG_chart);

            pageFragment=new PageFragment();

        getChildFragmentManager().beginTransaction().add(R.id.FL_chart,pageFragment).commitAllowingStateLoss();
        chart.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.RB_PieChart:
                        if( pageFragment == null){
                            pageFragment=new PageFragment();
                        }
                        getChildFragmentManager().beginTransaction().replace(R.id.FL_chart,pageFragment).commitAllowingStateLoss();
                        break;
                    case R.id.RB_LineChart:
                        if( barChartFragment == null){
                            barChartFragment=new BarChartFragment();
                        }
                        getChildFragmentManager().beginTransaction().replace(R.id.FL_chart,barChartFragment).commitAllowingStateLoss();
                        break;

                }
            }
        });





    }


}
