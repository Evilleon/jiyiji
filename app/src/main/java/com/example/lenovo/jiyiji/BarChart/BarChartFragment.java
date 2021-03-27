package com.example.lenovo.jiyiji.BarChart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.jiyiji.AddAccount.BillBean;
import com.example.lenovo.jiyiji.AddAccount.DB;
import com.example.lenovo.jiyiji.R;
import com.example.lenovo.jiyiji.Utils.Colors;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class BarChartFragment extends Fragment {
    private BarChart barChart;
    private XAxis xAxis;
    private DB db;
    private List<BillBean> billlist;
    private BillBean billBeanshow;
    private List<Integer> sum = new ArrayList<Integer>();//存放数据
    private String[] arr = {"饮食", "户外", "水电", "服饰", "运动", "美容", "网购", "送礼", "生活", "其他"};
    int j=-1;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_bar_chart, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        barChart=view.findViewById(R.id.bar_chart);

        sum = CountNum(sum);
        InitBarChart();
    }

    private void InitBarChart(){
        Description description = new Description();
        xAxis=barChart.getXAxis();
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        barChart.setDrawGridBackground(false); // 是否显示表格颜色
        barChart.getAxisLeft().setDrawAxisLine(false);
        barChart.setTouchEnabled(false); // 设置是否可以触摸
        barChart.setDragEnabled(true);// 是否可以拖拽
        barChart.setScaleEnabled(true);// 是否可以缩放

        description.setText("");
        //barChart.setDescription("zwingli");// 数据描述
        barChart.getAxisLeft().setEnabled(false);
        barChart.getAxisRight().setEnabled(false);
        Legend legend = barChart.getLegend();//隐藏比例尺
        legend.setEnabled(false);

        ArrayList<BarEntry> yValues = new ArrayList<>();
        for(int i=0;i<sum.size();i++){
            if(sum.get(i)!=0){
                yValues.add(new BarEntry(i+1, sum.get(i)));
            }
        }







        ArrayList<IBarDataSet> xValues = new ArrayList<>();
        for(int i=0;i<arr.length;i++){
            xValues.add(new BarDataSet(yValues,arr[i]));
        }




        BarDataSet barDataSet=new BarDataSet(yValues,"");



        barDataSet.setColors(Colors.RandColors());
        //7、显示，柱状图的宽度和动画效果
        BarData barData = new BarData(barDataSet);
        barData.setValueFormatter(new CallCountValueFormatter());
        //barDataSet.setBarSpacePercent(40f);//值越大，柱状图就越宽度越小；
        barChart.animateY(1000);
        barChart.setData(barData); //

    }

    private List<Integer> CountNum(List<Integer> sum){
        onResume();
        int di=0,go=0,wa=0,dr=0,sp=0,lo=0,sh=0,gi=0,li=0,ot=0;//总金额
        sum=new ArrayList<>();//存放数据
        int diet=0,gooutside=0,waterandelectricity=0,dress=0,sports=0,looks=0,shoppingonline=0,gift=0,life=0,others=0;//饮食，户外,水电，服饰等
        billlist = new ArrayList<>();
        billlist = db.showBill(billlist);
        for(int i=0;i<billlist.size();i++){
            billBeanshow=billlist.get(i);
            //if(center(billBeanshow.getTime())) {
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
            //}
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

    public class CallCountValueFormatter extends ValueFormatter implements IValueFormatter {
        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {

            int i  = (int) value;
            j++;
            return i+arr[j];

        }
    }

}
