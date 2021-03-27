package com.example.lenovo.jiyiji.AddAccount;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.jiyiji.MainActivity;
import com.example.lenovo.jiyiji.R;
import com.example.lenovo.jiyiji.Utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class BillFragment extends Fragment {
    private ListView show;
    private List<BillBean> billlist;
    private BillAdapter adapter;
    private DB db;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int arg1=msg.arg1;
            String info= (String) msg.obj;
            if (msg.what==1){
            }
            if (arg1==0){
                Show();
            }
        }
    };
    private Message message;

    private TextView show_all_income;
    private TextView show_all_pay;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.layout_show,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        show = view.findViewById(R.id.show_bill);


        show_all_income = view.findViewById(R.id.show_all_income);
        show_all_pay = view.findViewById(R.id.show_all_pay);
        billlist = new ArrayList<>();
        onResume();
        Show();

       show.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showDeleteDialog(position);
                return false;
            }
        });
    }

    public void onResume()
    {
        Log.d("create_connect","建立连接");
        super.onResume();
        db = db.getInstance(getActivity(),1);
        db.openReadLink();
        db.openWriteLink();
        //db.temp();
        Log.d("create_connect","建立连接完成");
    }

    @Override
    public void onPause()
    {
        Log.d("close_connect","关闭连接");
        super.onPause();
        db.closeLink();
        Log.d("close_connect","关闭连接完成");
    }

    //流水显示
    private void Show()
    {
        onResume();
        billlist = new ArrayList<>();
        billlist = db.showBill(billlist);
        //initBillList();
        adapter = new BillAdapter(getContext(),R.layout.billlist_item,billlist);
        show.setAdapter(adapter);
        showAll();
    }

    private void showDeleteDialog(final int position)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("提示");
        builder.setMessage("您确定要删除该条记录吗？");
        builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.delete(billlist.get(position).getId());
                /*
                int isOK = db.delete(billlist.get(position).getId());
                if (isOK>0) {
                    Toast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "删除失败", Toast.LENGTH_LONG).show();
                }*/
                Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_LONG).show();
                dialog.dismiss();
                message = mHandler.obtainMessage();
                message.what=1;
                message.arg1=0;
                message.obj="倒计时：";
                mHandler.sendMessage(message);

            }
        });
        builder.setNegativeButton("取消",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void showAll()
    {
        int all_income = 0;
        int all_pay = 0;
        for(int i=0;i < billlist.size(); i++)
        {
            String c = billlist.get(i).getChoose();
            if(c.equals("收入"))
            {
                all_income += billlist.get(i).getMoney();
            }else if(c.equals("支出"))
            {
                all_pay += billlist.get(i).getMoney();
            }
        }
        show_all_income.setText(all_income+"");
        show_all_pay.setText(all_pay+"");
    }

}
