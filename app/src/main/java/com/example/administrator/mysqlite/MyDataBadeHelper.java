package com.example.administrator.mysqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/10/31.
 */

public class MyDataBadeHelper extends SQLiteOpenHelper {

    //创建书本表
    public static final String CREATE_BOOK = "create table book ("+ "id integer primary key autoincrement,"
                                                                    +  "author text,"
                                                                    + "price real,"
                                                                    + "pages integer,"
                                                                    + "name text )";

    //创建目录表
    public static final String CREATE_CATEGORY = "create table Category("
            + "id integer primary key autoincrement, "
            + "category_name text,"
            + "category_code integer)";
    private Context mContext;

    public  MyDataBadeHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
         super(context, name, factory, version);
         mContext = context;

     }





    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
        db.execSQL(CREATE_CATEGORY);
        Toast.makeText(mContext, "Created succeeded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists Book");
        db.execSQL("drop table if exists Category");
        onCreate(db);
    }
}
