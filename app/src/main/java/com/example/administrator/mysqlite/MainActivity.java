package com.example.administrator.mysqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private MyDataBadeHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //第四个参数为建立数据库的版本号
        dbHelper = new MyDataBadeHelper(this,"BookStore.db", null, 4);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.create_database:
            dbHelper.getWritableDatabase();
                break;
            case R.id.add_data:
                Toast.makeText(this, "add data successfully", Toast.LENGTH_SHORT).show();
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                //开始组装第一条数据
                values.put("name", "The Da Vinci Code");
                values.put("author", "Dan Brown");
                values.put("pages", 454);
                values.put("price", 16.96);
                db.insert("book", null, values);//插入第一条数据
                //开始组装第二条数据
                values.put("name", "The Lost Symbol");
                values.put("author", "Dan Brown");
                values.put("pages", 510);
                values.put("price", 19.95);
                db.insert("book", null, values);//插入第二条数据;
                break;
            case R.id.update_data:
                Toast.makeText(this, "update data successfully", Toast.LENGTH_SHORT).show();
                SQLiteDatabase udb = dbHelper.getWritableDatabase();
                ContentValues uvalues = new ContentValues();
                uvalues.put("price", 10.99);
                udb.update("book", uvalues, "name = ?", new String[]{ "The Da Vinci Code"});
                break;
            case R.id.delete_data:
                SQLiteDatabase ddb = dbHelper.getWritableDatabase();
                ddb.delete("book", "pages > ?", new String [] { " 500 "});
                break;
            case R.id.query_data:
                SQLiteDatabase qdb = dbHelper.getWritableDatabase();
                //cursor对象为光标定位
                Cursor cursor = qdb.query("book", null, null, null, null, null, null);
                //从数据表Book第一项开始遍历
                if(cursor.moveToFirst()){
                    do {
                        //遍历Cursor对象
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));

                        Log.d("MainActivity", " book name is " + name);
                        Log.d("MainActivity", " book author is " + author);
                        Log.d("MainActivity", " book pages is " + pages);
                        Log.d("MainActivity", " book price is " + price);
                    }while (cursor.moveToNext());//到最后一项值为空时结束遍历

                }
                cursor.close();
                break;
            case R.id.replace_data:
                SQLiteDatabase rdb = dbHelper.getWritableDatabase();
                //开启事务
                rdb.beginTransaction();
                try{
                rdb.delete("book",null, null);
                    ContentValues rvalues = new ContentValues();
                    rvalues.put("name", "Game of Throne");
                    rvalues.put("author","George Martin");
                    rvalues.put("pages" , 720);
                    rvalues.put("price", 20.85);
                    rdb.insert("book", null, rvalues);
                    //开启事务成功
                    rdb.setTransactionSuccessful();

                } catch (Exception e ){
                    e.printStackTrace();
                }finally {
                    rdb.endTransaction(); //结束事务
                }
                break;
        }
    }
}
