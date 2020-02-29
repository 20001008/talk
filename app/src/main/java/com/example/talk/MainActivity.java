package com.example.talk;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    private static final int MSN_type_dm=1;
    private static final int MSN_type_wf=0;
    private LinkedList<zylist>mainlist;
    private zyadapter zyadapter;
    private FileOutputStream out;
    private SQLiteOpenHelper sqLiteOpenHelper;
    private String TAG;
    private  String[] PERMISSSIONS={"android.permission.READ_EXTERNAL_STORAGE","android.permission.WRITE_EXTERNAL_STORAGE"};
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       if(Build.VERSION.SDK_INT>=21)
       {
           getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
           getWindow().setStatusBarColor(0xffffffff);
       }
        //申请权限
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);
        zcPromissions(MainActivity.this);
        //注册
        final SQLiteOpenHelper sqLiteOpenHelper = new MyDataSQL(this, "book.db", null, 1);
        final SQLiteDatabase sqLiteDatabase=sqLiteOpenHelper.getWritableDatabase();
        final RecyclerView recyclerView=findViewById(R.id.recycerview);
        Button sendButton=findViewById(R.id.button);
        final EditText editText=findViewById(R.id.editText2);
        mainlist=new LinkedList<zylist>();
        //数据库创建
        Cursor cursor =sqLiteDatabase.query("Book",null,null,null,null,null,null);
        if (cursor.moveToFirst())
        {
            do{
                String string=cursor.getString(cursor.getColumnIndex("message"));
                int integer=cursor.getInt(cursor.getColumnIndex("type"));
                Log.d(TAG, "message:"+string+"\n type:"+Integer.toString(integer));
                mainlist.add(new zylist(integer,string,false));
            }while (cursor.moveToNext());
        }
        cursor.close();

        //加入对方消息
        mainlist.add(new zylist(MSN_type_dm,"在吗？",false));
        mainlist.add(new zylist(MSN_type_wf, "什么事 ？", false));
        mainlist.add(new zylist(MSN_type_dm,"来我家吃蝙蝠！！！",false));
        zyadapter=new zyadapter(this,mainlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(zyadapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent intent=new Intent("com.example.talk.MyReceiver.test");
               intent.setComponent(new ComponentName("com.example.talk","com.example.talk.MyReceiver"));
               sendBroadcast(intent);
                mainlist.add(new zylist(MSN_type_wf,editText.getText().toString(),true));
                zyadapter.notifyItemInserted(mainlist.size()-1);
                //sqLiteDatabase.delete("Book","message=?",new String[]{"什么事？"});
                recyclerView.scrollToPosition(mainlist.size() - 1);

                //数据库创建
                //zyadapter.notifyDataSetChanged();

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EditText edit = findViewById(R.id.editText2);
        save(edit.getText().toString());
    }

    public void save(String data) {
        BufferedWriter bwf = null;
        try {
            out = openFileOutput("data", Context.MODE_PRIVATE);
            bwf = new BufferedWriter(new OutputStreamWriter(out));
            bwf.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bwf != null) {
                try {
                    bwf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ;
            }
        }
    }

    public String read(String s) {
        FileInputStream fileInputStream;
        BufferedReader bfrd = null;
        StringBuilder sber = new StringBuilder();
        try {
            fileInputStream = openFileInput(s);
            bfrd = new BufferedReader(new InputStreamReader(fileInputStream));
            String ss = "";
            if ((ss = bfrd.readLine()) != null) {
                sber.append(ss);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bfrd != null) {
                try {
                    bfrd.close();
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        }
        return sber.toString();
    }
    public void zcPromissions(Activity activity)
    {
        try {
            for(int n=0;n<=PERMISSSIONS.length;n++) {
                Log.d(TAG, "zcPromissions: " + Integer.toString(n));
                Log.d(TAG, "zcPromissions: "+Integer.toString(n));
                int Permission = ActivityCompat.checkSelfPermission(MainActivity.this, PERMISSSIONS[n]);
                if (Permission != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(activity,PERMISSSIONS,1);
                }

            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
       switch (requestCode)
       {
           case 1:
               if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
                   Log.d(TAG, "onRequestPermissionsResult: 权限授权成功");
               break;
               default:
       }
    }
}

