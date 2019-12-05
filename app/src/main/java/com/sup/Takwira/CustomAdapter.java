package com.sup.Takwira;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;


public class CustomAdapter extends BaseAdapter {
    Context c;
    ArrayList<Pub> pubs;
    LayoutInflater inflater;


    public CustomAdapter(Context c, ArrayList<Pub> pubs) {
        this.c = c;
        this.pubs = pubs;
    }


    @Override
    public int getCount() {
        return pubs.size();
    }

    @Override
    public Object getItem(int i) {
        return pubs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertview, ViewGroup viewGroup) {

        if (inflater== null)
        {
            inflater=(LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } if(convertview==null)
        {
            convertview= inflater.inflate(R.layout.adapter_item,viewGroup,false);

        }

        MyHolder holder= new MyHolder(convertview);
        holder.titleTxt.setText(pubs.get(i).getTitle());
        try {
            holder.descriptionTxt.setText(pubs.get(i).getDescription());
        }catch (Exception e){
            ;
        }
        holder.numberTxt.setText(pubs.get(i).getNumber());

        String [] list = holder.numberTxt.getText().toString().split("/");

        if(Integer.parseInt(list[0])>=Integer.parseInt(list[1])){
            holder.numberTxt.setTextColor(Color.parseColor("#E52C2C"));
        }else{
            holder.numberTxt.setTextColor(Color.parseColor("#0BD14D"));
        }
        PicassoClient.downloadimg(c,pubs.get(i).getUrl(),holder.img);
        PicassoClient.downloadimg(c,pubs.get(i).getUrl2(),holder.img);
        PicassoClient.downloadimg(c,pubs.get(i).getUrl3(),holder.img);

        return convertview;
    }
}
