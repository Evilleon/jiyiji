package com.example.lenovo.jiyiji.LineChart;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.jiyiji.AddAccount.BillBean;
import com.example.lenovo.jiyiji.AddAccount.DB;
import com.example.lenovo.jiyiji.Date.CustomDatePicker;
import com.example.lenovo.jiyiji.Date.DateFormatUtils;
import com.example.lenovo.jiyiji.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class LineChartFragment extends Fragment {
    private LineChart lineChart;//折线图实例化
    private DB db;
    private List<BillBean> billlist;
    private BillBean billBeanshow;
    /*
    获取日期
     */
    private TextView mTvSelectedDate1, mTvSelectedDate2, mTvrefresh;
    private CustomDatePicker mDatePicker1, mDatePicker2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_line_chart, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lineChart = view.findViewById(R.id.day_line);

        OnClick onClick = new OnClick();
        mTvSelectedDate1 = view.findViewById(R.id.tv_line_startdate);
        initDatePicker();
        view.findViewById(R.id.ll_startdate).setOnClickListener(onClick);

        mTvSelectedDate2 = view.findViewById(R.id.tv_line_enddate);
        initDatePicker2();
        view.findViewById(R.id.ll_enddate).setOnClickListener(onClick);


        drawlinechart();

    }

    private void drawlinechart() {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < 12; i++)
            entries.add(new Entry(i, new Random().nextInt(300)));
        LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to dataset
        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        lineChart.invalidate(); // refresh
    }

    private List<Integer> CountNum(List<Integer> sum) {
        onResume();
        billlist = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        list = center();
        billlist = db.showBill(billlist);
        for (int i = 0; i < billlist.size(); i++) {
            billBeanshow = billlist.get(i);
            if(get(billBeanshow.getTime())){
                String[] str=billBeanshow.getTime().split("-");

                if((Integer.valueOf(str[2])>=list.get(0))&&(Integer.valueOf(str[2])<=list.get(list.size()-1))){

                }
            }


        }
        return sum;
    }

    public void onResume() {
        super.onResume();
        db = db.getInstance(getActivity(), 1);
        db.openReadLink();
        db.openWriteLink();
        Log.d("create_connect", "建立连接完成");
    }

    @Override
    public void onPause() {
        super.onPause();
        db.closeLink();
        Log.d("close_connect", "关闭连接完成");
    }

    class OnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_startdate:
                    // 日期格式为yyyy-MM-dd
                    mDatePicker1.show(mTvSelectedDate1.getText().toString());
                    break;

                case R.id.ll_enddate:
                    // 日期格式为yyyy-MM-dd
                    mDatePicker2.show(mTvSelectedDate2.getText().toString());
                    break;

            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDatePicker1.onDestroy();
        mDatePicker2.onDestroy();
    }

    private void initDatePicker() {
        long beginTimestamp = DateFormatUtils.str2Long("2009-05-01", false);
        long endTimestamp = System.currentTimeMillis();

        mTvSelectedDate1.setText(DateFormatUtils.long2Str(endTimestamp, false));

        // 通过时间戳初始化日期，毫秒级别
        mDatePicker1 = new CustomDatePicker(getActivity(), new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                mTvSelectedDate1.setText(DateFormatUtils.long2Str(timestamp, false));
            }
        }, beginTimestamp, endTimestamp);
        // 不允许点击屏幕或物理返回键关闭
        mDatePicker1.setCancelable(false);
        // 不显示时和分
        mDatePicker1.setCanShowPreciseTime(false);
        // 不允许循环滚动
        mDatePicker1.setScrollLoop(false);
        // 不允许滚动动画
        mDatePicker1.setCanShowAnim(false);
    }

    private void initDatePicker2() {
        long beginTimestamp = DateFormatUtils.str2Long("2009-05-01", false);
        long endTimestamp = System.currentTimeMillis();

        mTvSelectedDate2.setText(DateFormatUtils.long2Str(endTimestamp, false));

        // 通过时间戳初始化日期，毫秒级别
        mDatePicker2 = new CustomDatePicker(getActivity(), new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                mTvSelectedDate2.setText(DateFormatUtils.long2Str(timestamp, false));
            }
        }, beginTimestamp, endTimestamp);
        // 不允许点击屏幕或物理返回键关闭
        mDatePicker2.setCancelable(false);
        // 不显示时和分
        mDatePicker2.setCanShowPreciseTime(false);
        // 不允许循环滚动
        mDatePicker2.setScrollLoop(false);
        // 不允许滚动动画
        mDatePicker2.setCanShowAnim(false);
    }

    /*
    横坐标7天
     */
    private List<Integer> center() {
        List<Integer> list = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);

        int day = calendar.get(Calendar.DAY_OF_MONTH);

        for (int lastday = day - 6; lastday <= day; lastday++) {
            list.add(lastday);
        }
        return list;
    }

    public static List<Integer> getLastDayOfMonth(int year, int month, int moreday) {
        List<Integer> list = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month - 1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        String lastDayOfMonth = sdf.format(cal.getTime());
        int lastday = Integer.valueOf(lastDayOfMonth);
        int day = lastday - moreday + 1;
        for (; day < lastday; day++) {
            list.add(day);
        }
        return list;
    }

    public Boolean get(String str) {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        List<Integer> list = new ArrayList<>();
        list = center();
        String[] com = str.split("-");
        int d = Integer.valueOf(com[2]);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == d&&month==Integer.valueOf(com[1])) {
                return true;
            }
        }
        return false;
    }
}

