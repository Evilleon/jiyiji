package com.example.lenovo.jiyiji.AddAccount;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lenovo.jiyiji.R;

import java.util.List;

public class BookAdapter extends ArrayAdapter<BookBean> {

    private int resourceId;

    public BookAdapter(Context context, int textViewResourceId, List<BookBean> objucts)
    {
        super(context,textViewResourceId,objucts);
        resourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        BookBean bookBean = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView tv_bookname = view.findViewById(R.id.tv_bookname);

        tv_bookname.setText(bookBean.getBookname());

        return view;
    }

}
