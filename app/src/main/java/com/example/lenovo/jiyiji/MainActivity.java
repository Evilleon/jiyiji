package com.example.lenovo.jiyiji;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.jiyiji.AddAccount.AddBill;
import com.example.lenovo.jiyiji.AddAccount.BillFragment;
import com.example.lenovo.jiyiji.AddAccount.BookManager;
import com.example.lenovo.jiyiji.AddAccount.DB;
import com.example.lenovo.jiyiji.Camera.CameraActivity;
import com.example.lenovo.jiyiji.Explain.ExplainActivity;
import com.example.lenovo.jiyiji.Feedback.FeedBackActivity;
import com.example.lenovo.jiyiji.Login.LoginActivity;
import com.example.lenovo.jiyiji.Message.MessageActivity;
import com.example.lenovo.jiyiji.News.NewsFragment;
import com.example.lenovo.jiyiji.PieChart.ChartFragment;
import com.example.lenovo.jiyiji.Setting.SettingActivity;
import com.example.lenovo.jiyiji.Share.ShareActivity;
import com.example.lenovo.jiyiji.Utils.ToastUtil;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ChartFragment pieChartFragment;
    private BillFragment billFragment;
    private NewsFragment newsFragment;
    private TextView login,mTvadd,mTvstatistics,mTvwallet,mtvnews;
    private ImageView statistics,add,wallet,news;//图表,添加，显示
    private ImageView headdraw;//记一记的标志
    private Intent intent=new Intent();
    private Button bookname;
    private DB db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        statistics=findViewById(R.id.statistics);
        add=findViewById(R.id.add);
        wallet=findViewById(R.id.wallet);
        news = findViewById(R.id.news);
        mTvadd=findViewById(R.id.tv_add);
        mTvstatistics=findViewById(R.id.tv_statistics);
        mTvwallet=findViewById(R.id.tv_wallet);
        mtvnews=findViewById(R.id.tv_news);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (billFragment != null) {
            fragmentTransaction.hide(billFragment);
        }
        if (billFragment == null) {
            billFragment = new BillFragment();
            fragmentTransaction.add(R.id.mainlyaout, billFragment);
        } else {
            fragmentTransaction.show(billFragment);
        }
        fragmentTransaction.commit();

        bookname = findViewById(R.id.book_name);
        onResume();
        bookname.setText(db.getTableName());
        bookname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (v.getId())
                {
                    //添加账单
                    case R.id.add:
                        intent=new Intent(MainActivity.this,AddBill.class);
                        startActivity(intent);
                        break;
                    case R.id.book_name:
                        Log.i("addbook","add book");
                        intent=new Intent(MainActivity.this, BookManager.class);
                        startActivity(intent);
                        break;
                }
            }
        });
        setListenter();
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_main);

        login=headerLayout.findViewById(R.id.tvlogin);
        headdraw=headerLayout.findViewById(R.id.iv_headdraw);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showMsg(MainActivity.this,"登录正在施工，敬请期待！");
            }
        });
        headdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showMsg(MainActivity.this,"登录正在施工，敬请期待！");
            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            ToastUtil.showMsg(this,"Alpha版有些简陋，敬请期待！");
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            ToastUtil.showMsg(this,"亲，Alpha版请您使用手机自带相机哦！");

        } else if (id == R.id.nav_message) {
            intent=new Intent(MainActivity.this,MessageActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_explain) {
            intent=new Intent(MainActivity.this,ExplainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {
            ToastUtil.showMsg(this,"Alpha版正在与马化腾和马云商讨授权事宜，请期待哦！");

        } else if (id == R.id.nav_feedback) {
            ToastUtil.showMsg(this,"Alpha版认为您的反馈可以写在博客园里哦！");

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void setListenter(){
        OnClick onclick=new OnClick();
        statistics.setOnClickListener(onclick);
        add.setOnClickListener(onclick);
        wallet.setOnClickListener(onclick);
        news.setOnClickListener(onclick);
        mTvwallet.setOnClickListener(onclick);
        mTvstatistics.setOnClickListener(onclick);
        mTvadd.setOnClickListener(onclick);
        mtvnews.setOnClickListener(onclick);


    }
    class OnClick implements View.OnClickListener{


        @Override
        public void onClick(View v) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            if(billFragment != null){
                fragmentTransaction.hide(billFragment);
            }
            if(pieChartFragment != null){
                fragmentTransaction.hide(pieChartFragment);
            }
            switch (v.getId()){
                case R.id.tv_statistics:

                case R.id.statistics :
                    if( pieChartFragment == null){
                        pieChartFragment=new ChartFragment();
                        fragmentTransaction.add(R.id.mainlyaout, pieChartFragment);
                    }else{
                        fragmentTransaction.show(pieChartFragment);
                    }
                    break;
                case R.id.tv_add:
                case R.id.add:
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(),AddBill.class);
                    startActivity(intent);
                    break;
                case R.id.tv_wallet:
                case R.id.wallet:
                    if( billFragment == null){
                        billFragment=new BillFragment();
                        fragmentTransaction.add(R.id.mainlyaout,billFragment);
                    }else{
                        fragmentTransaction.show(billFragment);
                    }
                    break;
                case R.id.news:
                case R.id.tv_news:
                    if(newsFragment==null){
                        newsFragment=new NewsFragment();
                        fragmentTransaction.add(R.id.mainlyaout,newsFragment);
                    }else{
                        fragmentTransaction.show(newsFragment);
                    }
                    break;


            }
            fragmentTransaction.commitAllowingStateLoss();

        }
    }

    public void onResume()
    {
        Log.d("create_connect","建立连接");
        super.onResume();
        db = db.getInstance(MainActivity.this,1);
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
}
