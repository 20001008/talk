package com.example.talk;

public class zylist {
    private int type;
    private String message;
    private Boolean ifsavesql;
public zylist(int type,String message,Boolean ifsavesql2)
{
    this.type=type;
    this.message=message;
    this.ifsavesql=ifsavesql2;
}
public String getmessage()
{
    return this.message;
}
public  int gettype()
{
    return  this.type;
}
public Boolean getIfsavesql(){return  this.ifsavesql;}
}
