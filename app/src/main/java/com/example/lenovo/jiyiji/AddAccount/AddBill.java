package com.example.lenovo.jiyiji.AddAccount;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.jiyiji.MainActivity;
import com.example.lenovo.jiyiji.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddBill extends AppCompatActivity implements View.OnClickListener {
    private static final int ANSWER_TEXT_MAX_LENGTH = 6;
    private LinearLayout layout0,layout1,layout2,layout3,layout4,layout5, layout6,layout7,layout8,layout9,delete;
    private EditText money;
    private TextView remark;
    private View save;
    private Spinner sort;
    private ArrayAdapter<String> sortAdapter;
    private String checked_text;
    private RadioGroup choose;
    private RadioButton checked;
    private String[] sortArray;
    private String sort_check;
    private DB db;
    private Button btn_date;
    private Calendar cal = Calendar.getInstance();
    private String date_text = null;

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
        {
            cal.set(Calendar.YEAR,year);
            cal.set(Calendar.MONTH,monthOfYear);
            cal.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            updateDate();
        }

    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.CustomTitleBarTheme);  //声明标题栏，注意这一句的位置
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_add_bill);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title_bar);
        init();
        initRadioGroup();
        initSpinner();
        btn_date.setOnClickListener(this);
    }

    //保存
    public void Save() {

        //SQLiteDatabase db2 = db.openWriteLink();
        //db.onCreateBookInfo(db.openWriteLink());
        String money_text = money.getText().toString();
        if (money_text.length() == 0) {
            Toast.makeText(getApplicationContext(), "金额输入不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if ((date_text == null ) || (date_text.length() == 0)) {
            Toast.makeText(getApplicationContext(), "请选择时间", Toast.LENGTH_SHORT).show();
            return;
        }
        String remark_text = remark.getText().toString();
        if (remark_text.length() == 0) {
            remark_text = null;
        }
        Double money_text_d = Double.parseDouble(money_text);

        BillBean billBean = new BillBean(money_text_d, checked_text, sort_check, date_text,remark_text);
        Toast.makeText(getApplicationContext(),"保存成功",Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(), "保存成功:" + billBean.getMoney() + ", " + billBean.getChoose() + ", " + billBean.getSort() + ", " + billBean.getTime() + ", " + billBean.getRemark(), Toast.LENGTH_SHORT).show();
        long result = db.insert(billBean);
        if(result>=0)
        {
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(),MainActivity.class);
            this.startActivity(intent);
        }
    }


    //初始化
    private void init() {
        checked_text = "支出";
        money = findViewById(R.id.money);
        remark = findViewById(R.id.remark);
        sort = findViewById(R.id.sort);
        btn_date = findViewById(R.id.btn_date);
        TextView title_show = findViewById(R.id.tv_title_show);
        title_show.setText("添加账单");
        findViewById(R.id.back).setOnClickListener(this);
        getCurrentDate();
        layout0 = findViewById(R.id.screen_0);
        layout0.setOnClickListener(this);
        layout1 = findViewById(R.id.screen_1);
        layout1.setOnClickListener(this);
        layout2 = findViewById(R.id.screen_2);
        layout2.setOnClickListener(this);
        layout3 = findViewById(R.id.screen_3);
        layout3.setOnClickListener(this);
        layout4 = findViewById(R.id.screen_4);
        layout4.setOnClickListener(this);
        layout5 = findViewById(R.id.screen_5);
        layout5.setOnClickListener(this);
        layout6 = findViewById(R.id.screen_6);
        layout6.setOnClickListener(this);
        layout7 = findViewById(R.id.screen_7);
        layout7.setOnClickListener(this);
        layout8 = findViewById(R.id.screen_8);
        layout8.setOnClickListener(this);
        layout9 = findViewById(R.id.screen_9);
        layout9.setOnClickListener(this);
        save = findViewById(R.id.save);
        save.setOnClickListener(this);
        delete = findViewById(R.id.screen_delete);
        delete.setOnClickListener(this);
        money = findViewById(R.id.money);
    }


    //初始化下拉框
    private void initSpinner() {


        sort = findViewById(R.id.sort);
        String[] sortArray = getResources().getStringArray(R.array.pay_sort);
        //声明一个下拉列表的数组适配器
        sortAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, sortArray);
        //设置下拉框风格
        sortAdapter.setDropDownViewResource(R.layout.dropdown_style);

        //设置下拉框的数组适配器
        sort.setAdapter(sortAdapter);

        //为下拉框设置选择监听器，一旦用户选中某一项，就出发监听器的onItemSelected方法
        sort.setOnItemSelectedListener(new AddSortSelectedListener());
    }

    //初始化单选框
    private void initRadioGroup() {

        //获取RadioGroup控件
        choose = findViewById(R.id.choose);

        //设置默认选中
        //choose.check(checked.getId());
        //为RadioGroup添加监听事件
        choose.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //获取RadioButton控件
                checked = findViewById(choose.getCheckedRadioButtonId());
                //获取选中的radio的值
                checked_text = checked.getText().toString();
                //在这个函数里面用来改变选择的radioButton的数值，以及与其值相关的任何操作
                selectRadioBtn();
            }
        });

    }

    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_date:
            {
                new DatePickerDialog(AddBill.this,R.style.MyDatePickerDialogTheme,listener,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)
                ).show();
            }break;
            case R.id.back:
            {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(),MainActivity.class);
                this.startActivity(intent);
            }break;
            case R.id.screen_0:
                addPwdText(0);
                break;
            case R.id.screen_1:
                addPwdText(1);
                break;
            case R.id.screen_2:
                addPwdText(2);
                break;
            case R.id.screen_3:
                addPwdText(3);
                break;
            case R.id.screen_4:
                addPwdText(4);
                break;
            case R.id.screen_5:
                addPwdText(5);
                break;
            case R.id.screen_6:
                addPwdText(6);
                break;
            case R.id.screen_7:
                addPwdText(7);
                break;
            case R.id.screen_8:
                addPwdText(8);
                break;
            case R.id.screen_9:
                addPwdText(9);
                break;
            case R.id.save:
                Save();
                break;
            case R.id.screen_delete:
                addPwdText(-1);
                break;
        }
    }

    private void addPwdText(int num) {
        Log.i("key","addPwdText num=" + num);
        String answer = money.getText().toString();
        if(num >= 0 && answer.length() > ANSWER_TEXT_MAX_LENGTH){
            return;
        }
        if(num >= 0)
        {
            money.setText(answer + num);
        }else
        {
            if(TextUtils.isEmpty(answer)){
                return;
            }
            if(num == -1){
                money.setText(answer.substring(0,answer.length() - 1));
            }
        }
    }

    //定义一个选择监听器，它实现了接口OnAddSortSelectedListener
    class AddSortSelectedListener implements AdapterView.OnItemSelectedListener {
        //选择时的处理方法
        /*
        表示当Spinner控件中item被选中时回调的方法
        四个参数
        AdapterView<?> parent ：当前出发事件的适配器控件对象 此处Spinner
        View view ：当前被选中的item的对象
        int position ：当前被选中的item的下标
        long id ：当前被选中item的id
        */
        public void onItemSelected(AdapterView<?> arg0, View arg1, int num, long arg3) {
            //sortAdapter.notifyDataSetChanged();
            //根据下标在适配器中获取
            sort_check = sortAdapter.getItem(num);
        }

        //未选择时的处理方法
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    private void selectRadioBtn() {
        //Log.i("radio",checked_text);
        //Toast.makeText(AddBill.this,"checked_text = "+checked_text,Toast.LENGTH_SHORT).show();
    }

    protected void onResume()
    {
        super.onResume();
        db = db.getInstance(this,1);
        db.openReadLink();
        db.openWriteLink();
        Log.d("create_connect","建立连接完成");
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        db.closeLink();
        Log.d("close_connect","关闭连接完成");
    }

    //选择日期
    private void updateDate(){

        SimpleDateFormat
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        btn_date.setText(simpleDateFormat.format(cal.getTime()));
        date_text = btn_date.getText().toString();
    }

    //获取当前日期
    private void getCurrentDate()
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        date_text = df.format(new Date());
        btn_date.setText(date_text);
    }

}
