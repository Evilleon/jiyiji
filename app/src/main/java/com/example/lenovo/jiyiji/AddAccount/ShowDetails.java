package com.example.lenovo.jiyiji.AddAccount;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.lenovo.jiyiji.MainActivity;
import com.example.lenovo.jiyiji.R;

import java.util.ArrayList;
import java.util.List;

public class ShowDetails extends AppCompatActivity{
    private DB db;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_show);

    }

}
