package com.example.talk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    private static final int MSN_type_dm=1;
    private static final int MSN_type_wf=0;
private LinkedList<zylist>mainlist;
private zyadapter zyadapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //注册

        final RecyclerView recyclerView=findViewById(R.id.recycerview);
        Button sendButton=findViewById(R.id.button);
        final EditText editText=findViewById(R.id.editText2);
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
                editText.setText("");
                recyclerView.scrollToPosition(mainlist.size()-1);
                //zyadapter.notifyDataSetChanged();
            }
        });

    }


}

