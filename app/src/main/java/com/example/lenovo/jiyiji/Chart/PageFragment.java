package com.example.lenovo.jiyiji.Chart;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.jiyiji.LineChart.LineChartFragment;
import com.example.lenovo.jiyiji.PieChart.PieChartFragment;
import com.example.lenovo.jiyiji.PieChart.TypePieChartFragment;
import com.example.lenovo.jiyiji.R;

import java.util.ArrayList;
import java.util.List;

public class PageFragment extends Fragment {
    private ViewPager viewPager;
    private View view1;//第一种饼状图
    private View view2;//第二种饼状图
    private List<Fragment> pageview;
    private ChartPagerAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_view_pager,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = view.findViewById(R.id.viewpager);
        //view1=getLayoutInflater().inflate(R.layout.activity_pei_chart,null);
        //view2=getLayoutInflater().inflate(R.layout.layout_type_pie_chart,null);
        pageview = new ArrayList<>();
        pageview.add(new PieChartFragment());
        pageview.add(new TypePieChartFragment());
        adapter=new ChartPagerAdapter(getChildFragmentManager(),pageview);

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
    }
}
