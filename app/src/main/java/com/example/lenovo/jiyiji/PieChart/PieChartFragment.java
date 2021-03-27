package com.example.lenovo.jiyiji.PieChart;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.jiyiji.AddAccount.BillBean;
import com.example.lenovo.jiyiji.AddAccount.DB;
import com.example.lenovo.jiyiji.Date.CustomDatePicker;
import com.example.lenovo.jiyiji.Date.DateFormatUtils;
import com.example.lenovo.jiyiji.Date.Datebean;
import com.example.lenovo.jiyiji.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.support.v4.widget.SwipeRefreshLayout.LARGE;

public class PieChartFragment extends Fragment {
    private DB db;
    private PieChart pieChartView;
    private List<BillBean> billlist;
    private BillBean billBeanshow;
    private Double money;//金额
    private String choose;//收入或支出
    private String sort;//种类
    private String time;//记账时间
    private String remark;//备注
    //总金额
    private List<Integer> sum;//存放数据
    /*
    获取日期
     */
    private TextView mTvSelectedDate1, mTvSelectedDate2, mTvrefresh;
    private CustomDatePicker mDatePicker1, mDatePicker2;
    private List<Datebean> datebeans;
    private Datebean datebeanshow;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_pei_chart, container, false);
        return view;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        pieChartView = view.findViewById(R.id.MyPieChart);
        mTvrefresh = view.findViewById(R.id.tv_refresh);

        OnClick onClick = new OnClick();
        mTvSelectedDate1 = view.findViewById(R.id.tv_selected_startdate);
        initDatePicker();
        view.findViewById(R.id.ll_startdate).setOnClickListener(onClick);

        mTvSelectedDate2 = view.findViewById(R.id.tv_selected_enddate);
        initDatePicker2();
        view.findViewById(R.id.ll_enddate).setOnClickListener(onClick);


        sum = CountNum(sum);
        onPause();
        InEx();
        mTvrefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pieChartView.clear();
                sum = CountNum(sum);
                onPause();
                InEx();
            }
        });


    }

    public void InEx() {
        List<PieEntry> yVals = new ArrayList<>();
        Description description = new Description();
        yVals.add(new PieEntry(sum.get(0), "收入"));
        yVals.add(new PieEntry(sum.get(1), "支出"));

        List<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#4A92FC"));
        colors.add(Color.parseColor("#ee6e55"));

        PieDataSet pieDataSet = new PieDataSet(yVals, "");
        pieDataSet.setColors(colors);
        pieDataSet.setValueTextSize(30);
        PieData pieData = new PieData(pieDataSet);

        description.setText("");
        pieChartView.setData(pieData);
        pieChartView.setCenterText("收入/支出");
        pieChartView.setCenterTextSize(30);
        pieChartView.setEntryLabelTextSize(30);
        pieChartView.setDescription(description);
        pieChartView.setExtraOffsets(20, 20, 20, 20);
        pieChartView.setRotationEnabled(false);
    }

    public List<Integer> CountNum(List<Integer> sum) {
        onResume();
        int sum1 = 0, j = 0, sum2 = 0, k = 0;
        sum = new ArrayList<>();
        billlist = new ArrayList<>();
        billlist = db.showBill(billlist);
        for (int i = 0; i < billlist.size(); i++) {
            billBeanshow = billlist.get(i);

            if (center(billBeanshow.getTime())) {
                if ((billBeanshow.getChoose()).equals("收入")) {
                    j = (int) Math.ceil(billBeanshow.getMoney());
                    sum1 += j;
                    Log.d("收入：", "" + j);
                } else if ((billBeanshow.getChoose()).equals("支出")) {
                    k = (int) Math.ceil(billBeanshow.getMoney());
                    sum2 += k;

                    Log.d("支出：", "" + k);
                }
            }

        }
        Log.d("起始时间", "" + mTvSelectedDate1.getText().toString());
        sum.add(sum1);
        sum.add(sum2);
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

    private Boolean center(String str) {
        String starttime;
        String endtime;

        starttime = mTvSelectedDate1.getText().toString();
        endtime = mTvSelectedDate2.getText().toString();


        String[] start = starttime.split("-");
        String[] end = endtime.split("-");
        String[] com = str.split("-");
        int s = Integer.valueOf(start[0] + start[1] + start[2]);
        int e = Integer.valueOf(end[0] + end[1] + end[2]);
        int c = Integer.valueOf(com[0] + com[1] + com[2]);
        Log.d("起始日期对吗？",""+s);
        Log.d("终止日期对吗？",""+e);
        Log.d("传入日期对吗？",""+c);
        if ((c >= s) && (c <= e)) {
            Log.d("13509783402：","我看看进啦几个");
            return true;
        } else {
            return false;
        }


    }

}
