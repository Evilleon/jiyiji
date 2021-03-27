package com.example.lenovo.jiyiji.PieChart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lenovo.jiyiji.AddAccount.BillBean;
import com.example.lenovo.jiyiji.AddAccount.DB;
import com.example.lenovo.jiyiji.Date.CustomDatePicker;
import com.example.lenovo.jiyiji.Date.DateFormatUtils;
import com.example.lenovo.jiyiji.R;
import com.example.lenovo.jiyiji.Utils.Colors;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TypePieChartFragment extends Fragment {
    private PieChart pieChart;
    private DB db;
    private List<BillBean> billlist;
    private BillBean billBeanshow;

    private List<Integer> sum = new ArrayList<Integer>();//存放数据
    private String[] arr = {"饮食", "户外", "水电", "服饰", "运动", "美容", "网购", "送礼", "生活", "其他"};
    /*
    获取日期
     */
    private TextView mTvSelectedDate1, mTvSelectedDate2, mTvrefresh;
    private CustomDatePicker mDatePicker1, mDatePicker2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_type_pie_chart, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pieChart = view.findViewById(R.id.typepiechart);
        mTvrefresh = view.findViewById(R.id.tv_type_refresh);

        OnClick onClick = new OnClick();

        mTvSelectedDate1 = view.findViewById(R.id.tv_type_selected_startdate);
        initDatePicker();
        view.findViewById(R.id.ll_startdate).setOnClickListener(onClick);
        mTvSelectedDate2 = view.findViewById(R.id.tv_type_selected_enddate);
        initDatePicker2();
        view.findViewById(R.id.ll_enddate).setOnClickListener(onClick);
        sum = CountNum(sum);
        InEx();

        mTvrefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pieChart.clear();
                sum = CountNum(sum);
                onPause();
                InEx();
            }
        });

    }

    private void InEx() {
        List<PieEntry> yVals = new ArrayList<>();
        List<Integer> colors = new ArrayList<>();
        Description description = new Description();

        for (int i = 0; i < sum.size(); i++) {
            if (sum.get(i) != 0) {
                yVals.add(new PieEntry(sum.get(i), arr[i]));
                int color = Colors.RandColors();
                colors.add(color);
            }
        }


        PieDataSet pieDataSet = new PieDataSet(yVals, "");
        pieDataSet.setColors(colors);
        pieDataSet.setValueTextSize(10);
        PieData pieData = new PieData(pieDataSet);

        description.setText("");
        pieChart.setData(pieData);
        pieChart.setCenterText("种类");
        pieChart.setCenterTextSize(30);
        pieChart.setEntryLabelTextSize(15);
        pieChart.setDescription(description);
        pieChart.setRotationEnabled(false);
        pieChart.setExtraOffsets(20, 20, 20, 20);
    }

    private List<Integer> CountNum(List<Integer> sum) {
        onResume();
        int di = 0, go = 0, wa = 0, dr = 0, sp = 0, lo = 0, sh = 0, gi = 0, li = 0, ot = 0;//总金额
        sum = new ArrayList<>();//存放数据
        int diet = 0, gooutside = 0, waterandelectricity = 0, dress = 0, sports = 0, looks = 0, shoppingonline = 0, gift = 0, life = 0, others = 0;//饮食，户外,水电，服饰等
        billlist = new ArrayList<>();
        billlist = db.showBill(billlist);
        for (int i = 0; i < billlist.size(); i++) {
            billBeanshow = billlist.get(i);
            if (center(billBeanshow.getTime())) {
                if ((billBeanshow.getSort()).equals("饮食")) {
                    di = (int) Math.ceil(billBeanshow.getMoney());
                    diet += di;
                    Log.d("饮食：", "" + di);
                } else if ((billBeanshow.getSort()).equals("户外")) {
                    go = (int) Math.ceil(billBeanshow.getMoney());
                    gooutside += go;

                    Log.d("户外：", "" + go);
                } else if ((billBeanshow.getSort()).equals("水电")) {
                    wa = (int) Math.ceil(billBeanshow.getMoney());
                    waterandelectricity += wa;

                    Log.d("水电：", "" + wa);
                } else if ((billBeanshow.getSort()).equals("服饰")) {
                    dr = (int) Math.ceil(billBeanshow.getMoney());
                    dress += dr;

                    Log.d("服饰：", "" + dr);
                } else if ((billBeanshow.getSort()).equals("运动")) {
                    sp = (int) Math.ceil(billBeanshow.getMoney());
                    sports += sp;

                    Log.d("运动：", "" + sp);
                } else if ((billBeanshow.getSort()).equals("美容")) {
                    lo = (int) Math.ceil(billBeanshow.getMoney());
                    looks += lo;

                    Log.d("美容：", "" + lo);
                } else if ((billBeanshow.getSort()).equals("网购")) {
                    sh = (int) Math.ceil(billBeanshow.getMoney());
                    shoppingonline += sh;

                    Log.d("网购：", "" + sh);
                } else if ((billBeanshow.getSort()).equals("送礼")) {
                    gi = (int) Math.ceil(billBeanshow.getMoney());
                    gift += gi;

                    Log.d("送礼：", "" + gi);
                } else if ((billBeanshow.getSort()).equals("生活")) {
                    li = (int) Math.ceil(billBeanshow.getMoney());
                    life += li;

                    Log.d("生活：", "" + li);
                } else if ((billBeanshow.getSort()).equals("其他")) {
                    ot = (int) Math.ceil(billBeanshow.getMoney());
                    others += ot;

                    Log.d("其他：", "" + ot);
                }
            }
        }
        sum.add(diet);
        sum.add(gooutside);
        sum.add(waterandelectricity);
        sum.add(dress);
        sum.add(sports);
        sum.add(looks);
        sum.add(shoppingonline);
        sum.add(gift);
        sum.add(life);
        sum.add(others);
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
                    // 日期格式为yyyy-MM-dd HH:mm
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
        Log.d("起始日期对吗？", "" + s);
        Log.d("终止日期对吗？", "" + e);
        Log.d("传入日期对吗？", "" + c);
        if ((c >= s) && (c <= e)) {
            Log.d("13509783402：", "我看看进啦几个");
            return true;
        } else {
            return false;
        }


    }


}
