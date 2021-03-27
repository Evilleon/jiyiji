package com.example.lenovo.jiyiji.AddAccount;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.jiyiji.MainActivity;
import com.example.lenovo.jiyiji.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookManager extends AppCompatActivity implements View.OnClickListener {

    private ListView show_book;
    private DB db;
    private List<BookBean> booklist;
    private BookAdapter adapter;
    private Button addbook;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.CustomTitleBarTheme);  //声明标题栏，注意这一句的位置
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.add_book);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title_bar);
        init();
        Show();

    }

    public void init()
    {
        TextView title_show = findViewById(R.id.tv_title_show);
        addbook = findViewById(R.id.add_book);
        addbook.setOnClickListener(this);
        title_show.setText("账本管理");
        findViewById(R.id.back).setOnClickListener(this);
        show_book = findViewById(R.id.show_book);
        show_book.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showSelectBook(position);
                return;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.back:
            {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), MainActivity.class);
                this.startActivity(intent);
            }break;
            case R.id.add_book:
            {
                addBook();
            }break;
        }
    }

    protected void onResume()
    {
        Log.d("create_connect","建立连接");
        super.onResume();
        db = db.getInstance(this,1);
        db.openReadLink();
        db.openWriteLink();
        Log.d("create_connect","建立连接完成");
    }

    @Override
    protected void onPause()
    {
        Log.d("close_connect","关闭连接");
        super.onPause();
        db.closeLink();
        Log.d("close_connect","关闭连接完成");
    }

    public void Show()
    {
        onResume();
        booklist = new ArrayList<>();
        booklist = db.showBook(booklist);
        //initBillList();
        adapter = new BookAdapter(BookManager.this,R.layout.booklist_item,booklist);
        show_book.setAdapter(adapter);
    }

    private void addBook()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText bookname = new EditText(this);
        builder.setTitle("请输入账本名：").setView(bookname);
        //长度限制16字符
        InputFilter[] filters = {new InputFilter.LengthFilter(16)};
        bookname.setFilters(filters);
        InputFilter typeFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                Pattern p = Pattern.compile("[a-zA-Z|\u4e00-\u9fa5]+");
                Matcher m = p.matcher(source.toString());
                if (!m.matches()) return "";
                return null;
            }

        };
        bookname.setFilters(new InputFilter[]{typeFilter});
        builder.setPositiveButton("添加", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean result = db.addBook(db.getMDB(),bookname.getText().toString());
                if(result)
                {
                    Toast.makeText(BookManager.this,"账本 "+ bookname.getText().toString() +" 已存在",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(BookManager.this, "添加账本 " + bookname.getText().toString() + " 成功", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    message = mHandler.obtainMessage();
                    message.what = 1;
                    message.arg1 = 0;
                    message.obj = "倒计时：";
                    mHandler.sendMessage(message);
                }
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
    private void showSelectBook(final int position)
    {
        db.setTableName(booklist.get(position).getBookname());
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(),MainActivity.class);
        this.startActivity(intent);
    }

}
