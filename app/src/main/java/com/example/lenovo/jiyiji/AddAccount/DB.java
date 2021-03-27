package com.example.lenovo.jiyiji.AddAccount;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;

public class DB extends SQLiteOpenHelper {

    private static final String DB_NAME="accountbook.db";
    private static final int DB_VERSION=1;
    private static DB mHelper=null;
    private SQLiteDatabase mDB=null;
    private static String TABLE_NAME="默认账本";
    private static final String TABLE_NAME_BOOK = "book";
    public void setTableName(String TABLE_NAME)
    {
        this.TABLE_NAME = TABLE_NAME;
    }

    public DB(Context context)
    {
        super(context,DB_NAME,null,DB_VERSION);
    }
    public DB(Context context ,int version)
    {
        super(context,DB_NAME,null,version);
    }

    public SQLiteDatabase getMDB()
    {
        return mDB;
    }

    //利用单例模式获取数据库帮助器的唯一实例
    public static DB getInstance(Context context,int version)
    {

        if (version>0&&mHelper==null)
        {
            mHelper=new DB(context, version);
        }else if (mHelper==null)
        {
            mHelper=new DB(context);
        }
        return mHelper;
    }

    //获取账本名
    public String getTableName()
    {
        return TABLE_NAME;
    }

    //打开数据库的读连接
    public SQLiteDatabase openReadLink()
    {
        if (mDB==null||!mDB.isOpen())
        {
            mDB=mHelper.getReadableDatabase();
        }
        return mDB;
    }
    //打开数据库的写连接
    public SQLiteDatabase openWriteLink()
    {
        if (mDB==null||!mDB.isOpen())
        {
            mDB=mHelper.getWritableDatabase();
        }
        return mDB;
    }
    //关闭数据库连接
    public void closeLink()
    {
        if (mDB!=null&&mDB.isOpen())
        {
            mDB.close();
            mDB=null;
        }
    }
    //创建数据库，执行建表语句
    public void onCreate(SQLiteDatabase db)
    {
        String drop_sql="DROP TABLE IF EXISTS "+TABLE_NAME+";";
        db.execSQL(drop_sql);
        String create_sql="CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" ("
                +"id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                +"money double NOT NULL,"
                +"choose VARCHAR NOT NULL,"
                +"sort varchar NOT NULL,"
                +"time varchar NOT NULL,"
                +"remark varchar"
                +")";
        db.execSQL(create_sql);
        Log.d("create","建表成功"+TABLE_NAME_BOOK);
        onCreateBookInfo(db);
    }

    //创建账本记录表
    public void onCreateBookInfo(SQLiteDatabase db)
    {
        String drop_sql2="DROP TABLE IF EXISTS "+TABLE_NAME_BOOK+";";
        db.execSQL(drop_sql2);
        String create_sql2="CREATE TABLE IF NOT EXISTS "+TABLE_NAME_BOOK+" ("
                +"id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                +"bookname varchar NOT NULL,"
                +"privacy varchar NOT NULL"
                +")";
        db.execSQL(create_sql2);
        Log.d("create","建表成功"+TABLE_NAME_BOOK);
        BookBean bookBean = new BookBean("默认账本","正常");
        long result = -1;
        ContentValues cv = new ContentValues();
        cv.put("bookname",bookBean.getBookname());
        cv.put("privacy",bookBean.getPrivacy());
        result = db.insert(TABLE_NAME_BOOK,"",cv);
        if(result >= 0)
        {
            Log.i("add", "添加账本成功");
        }
    }

    //新建账本
    public boolean onCreateBook(SQLiteDatabase db,String TABLE_NAME) {
        this.TABLE_NAME = TABLE_NAME;
        //String drop_sql="DROP TABLE IF EXISTS "+TABLE_NAME+";";
        //db.execSQL(drop_sql);
        Log.d("create", "建表开始" + TABLE_NAME);
        boolean result = tabbleIsExist();
        if (result)
        {
            Log.d("create", "表" + TABLE_NAME+"已存在");
        }else {
            String create_sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                    + "money double NOT NULL,"
                    + "choose VARCHAR NOT NULL,"
                    + "sort varchar NOT NULL,"
                    + "time varchar NOT NULL,"
                    + "remark varchar"
                    + ")";
            db.execSQL(create_sql);
            Log.d("create", "建表成功" + TABLE_NAME);
        }
        return result;
    }

    //添加账本
    public boolean addBook(SQLiteDatabase db,String TABLE_NAME)
    {
        boolean result = onCreateBook(db,TABLE_NAME);
        BookBean bookBean = new BookBean(TABLE_NAME,"正常");
        insertBook(bookBean);
        return result;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //添加账本
    public void insertBook(BookBean bookBean)
    {
        long result = -1;
        ContentValues cv = new ContentValues();
        cv.put("bookname",bookBean.getBookname());
        cv.put("privacy",bookBean.getPrivacy());
        result = mDB.insert(TABLE_NAME_BOOK,"",cv);
        if(result >= 0)
        {
            Log.i("add", "添加账本成功");
        }
    }

    //添加账单
    public long insert(BillBean billbean)
    {
        long result = -1;
        ContentValues cv = new ContentValues();
        cv.put("money",billbean.getMoney());
        cv.put("choose",billbean.getChoose());
        cv.put("sort",billbean.getSort());
        cv.put("time",billbean.getTime());
        cv.put("remark",billbean.getRemark());
        result = mDB.insert(TABLE_NAME,"",cv);
        if(result >= 0)
        {
            Log.i("add", "添加账单成功");
        }
        return result;
    }

    //显示账本
    public List<BookBean> showBook(List<BookBean> booklist)
    {
        Cursor cursor = mDB.query(TABLE_NAME_BOOK,null,null,null,null,null,null);
        cursor.moveToLast();
        do
        {
            BookBean bookBeanshow = new BookBean(cursor.getInt(0),cursor.getString(1),cursor.getString(2));
            booklist.add(bookBeanshow);
        }while (cursor.moveToPrevious());
        cursor.close();
        Log.d("show_book","显示账本完成");
        return booklist;
    }

    //显示账单
    public List<BillBean> showBill(List<BillBean> billlist)
    {
        Cursor cursor = mDB.query(TABLE_NAME,null,null,null,null,null,null);
        cursor.moveToLast();
        if(cursor != null && cursor.getCount() > 0) {
            do {
                BillBean billBeanshow = new BillBean(cursor.getInt(0), cursor.getDouble(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
                billlist.add(billBeanshow);
            } while (cursor.moveToPrevious());
        }
        cursor.close();
        return billlist;
    }

    //删除账单
    public void delete(int id)
    {
        Log.i("delete","删除开始 id= "+id);
        String whereClause = "id=?";
        String[] args = {String.valueOf(id)};
        mDB.delete(TABLE_NAME, whereClause, args);
        Log.i("delete","删除成功");

    }

    public void temp()
    {
        String sql = "alter table bill rename to 默认账本";
        mDB.execSQL(sql);
        Log.i("temp","修改成功");
    }

    //检测某个表是否存在
    public boolean tabbleIsExist(){
        boolean result = false;
        if(TABLE_NAME == null){
            return false;
        }
        Cursor cursor = null;
        try {
            String sql = "select count(*) from "+TABLE_NAME_BOOK+" where type ='table' and name ='"+TABLE_NAME+"' ";
            cursor = mDB.rawQuery(sql, null);
            if(cursor.moveToNext()){
                int count = cursor.getInt(0);
                if(count>0){
                    result = true;
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
        return result;
    }

}
