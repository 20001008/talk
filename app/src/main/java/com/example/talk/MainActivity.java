package com.example.talk;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
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

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //注册
        final SQLiteOpenHelper sqLiteOpenHelper = new MyDataSQL(this, "book.db", null, 1);
        final RecyclerView recyclerView=findViewById(R.id.recycerview);
        Button sendButton=findViewById(R.id.button);
        final EditText editText=findViewById(R.id.editText2);
        //数据库创建

        mainlist=new LinkedList<zylist>();
        //加入对方消息
        mainlist.add(new zylist(MSN_type_dm,"在吗？"));
        mainlist.add(new zylist(MSN_type_wf,"什么事？"));
        mainlist.add(new zylist(MSN_type_dm,"来我家吃蝙蝠！！！"));
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
                mainlist.add(new zylist(MSN_type_wf,editText.getText().toString()));
                zyadapter.notifyItemInserted(mainlist.size()-1);
                sqLiteOpenHelper.getWritableDatabase();
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

}

