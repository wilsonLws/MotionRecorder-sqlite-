package com.jluzh.wilsonleung.motionrecorder;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/7/21.
 */
public class RecordActivity extends Activity{
    //继承SQLiteOpenHelper类
    private MySQLiteOpenHelper sqlHelper;
    private ListView listview;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_record);
        ExitApplication.getInstance().addActivity(this);

    sqlHelper = new MySQLiteOpenHelper(this, "Database_Record.db", null, 2);
        sqlHelper.getWritableDatabase();
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        listview = (ListView) findViewById(R.id.listview1);

        try {
            SQLiteDatabase db1 = sqlHelper.getWritableDatabase();
            //游标查询每条数据
            Cursor cursor = db1.query("record", null, null, null, null, null, null);
            //定义list存储数据
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            //适配器SimpleAdapter数据绑定
            //错误:构造函数SimpleAdapter未定义 需把this修改为MainActivity.this
            SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.layout_item,
                    new String[]{"distance", "step", "time", "recordtime"},
                    new int[]{R.id.record_id, R.id.record_distance, R.id.record_step, R.id.record_recordtime});
            while(cursor.moveToNext()) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put( "distance", cursor.getString(cursor.getColumnIndex("distance")) );
                map.put( "step", cursor.getString(cursor.getColumnIndex("step")) );
                map.put( "time", cursor.getString(cursor.getColumnIndex("time")) );
                map.put( "recordtime", cursor.getString(cursor.getColumnIndex("recordtime")) );
                list.add(map);
            }
            listview.setAdapter(adapter);
        }
        catch (Exception e){
            Log.i("exception", e.toString());
        }


    }
}
