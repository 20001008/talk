package com.example.talk;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class zyadapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int MSN_type_dm=1;
    private static final int MSN_type_wf=0;
   private LinkedList<zylist>list;
   private Context context;
   private View vw;
    private LinearLayout linearLayout1;
    private LinearLayout linearLayout2;
    private TextView texeView1;
    private TextView texeView2;
    private CardView cardView1;
    private CardView cardView2;
    private SQLiteOpenHelper sqLiteOpenHelper;
    private SQLiteDatabase sqLiteDatabase;
    private ContentValues contentValues;
    private Boolean ifsavesql;
    public  zyadapter(Context context, LinkedList<zylist>list)
    {
        this.list=list;
        this.context=context;
    }
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        vw= LinearLayout.inflate(context,R.layout.layout,null);
        myholder myholder=new myholder(vw);
        myholder.setIsRecyclable(false);
       linearLayout1=vw.findViewById(R.id.linearlayout1);
        linearLayout2=vw.findViewById(R.id.linearlayout2);
        texeView1=vw.findViewById(R.id.textView);
        texeView2=vw.findViewById(R.id.textView2);
        cardView1=vw.findViewById(R.id.cardview1);
        cardView2=vw.findViewById(R.id.cardview2);
        contentValues=new ContentValues();
        sqLiteOpenHelper=new MyDataSQL(context,"book.db",null,1);
        sqLiteDatabase=sqLiteOpenHelper.getWritableDatabase();
        return myholder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(list.get(position).getIfsavesql()==true)
        {
            contentValues.put("message",list.get(position).getmessage());
            contentValues.put("num",position+1);
            contentValues.put("type",list.get(position).gettype());
            sqLiteDatabase.insert("Book",null,contentValues);
            Log.d(TAG, "message:"+list.get(position).getmessage()+"\n"+"num:"+Integer.toString(position+1));
        }
        if(list.get(position).gettype()==MSN_type_wf)
        {
            linearLayout1.setVisibility(View.GONE);
            linearLayout2.setVisibility(View.VISIBLE);
            texeView2.setText(list.get(position).getmessage());
            cardView2.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            texeView2.setTextColor(0xffffffff); } else
        {
           linearLayout2.setVisibility(View.GONE);
            linearLayout1.setVisibility(View.VISIBLE);
            texeView1.setText(list.get(position).getmessage());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class myholder extends RecyclerView.ViewHolder
{

    public myholder(@NonNull View itemView) {
        super(itemView);
    }
}



