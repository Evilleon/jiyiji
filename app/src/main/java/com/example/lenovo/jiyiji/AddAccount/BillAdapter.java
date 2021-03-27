package com.example.lenovo.jiyiji.AddAccount;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lenovo.jiyiji.R;

import java.util.List;

public class BillAdapter extends ArrayAdapter<BillBean> {
    private int resourceId;

    public BillAdapter(Context context, int textViewResourceId, List<BillBean> objucts)
    {
        super(context,textViewResourceId,objucts);
        resourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        BillBean billBean = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView tv_money = view.findViewById(R.id.tv_money);
        TextView tv_sort = view.findViewById(R.id.tv_sort);
        TextView tv_time = view.findViewById(R.id.tv_time);

        //整型
        double mo = billBean.getMoney();
        int mon = (int) mo;

        tv_money.setText(String.valueOf(mon));

        tv_sort.setText(billBean.getSort());
        tv_time.setText(billBean.getTime());

        //money颜色
        String c = billBean.getChoose();
        if(c.equals("支出"))
        {
            tv_money.setTextColor(Color.parseColor("#da0101"));
        }else if(c.equals("收入"))
        {
            tv_money.setTextColor(Color.parseColor("#18cb00"));
        }

        return view;
    }
}
