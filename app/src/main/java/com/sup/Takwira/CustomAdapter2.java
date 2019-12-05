package com.sup.Takwira;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class CustomAdapter2 extends BaseAdapter {
    Context c;
    ArrayList<ChatFirebase> chatFirebase;
    LayoutInflater inflater;
    private DatabaseReference mDatabase;

    public CustomAdapter2(Context c, ArrayList<ChatFirebase> chatFirebase) {
        this.c = c;
        this.chatFirebase = chatFirebase;
    }


    @Override
    public int getCount() {
        return chatFirebase.size();
    }

    @Override
    public Object getItem(int i) {
        return chatFirebase.get(i);
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
            convertview= inflater.inflate(R.layout.adapter_item_chat,viewGroup,false);

        }

        MyHolder2 holder= new MyHolder2 (convertview);
        holder.text_message_body.setText(chatFirebase.get(i).getMessage());

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("usersacc").child(chatFirebase.get(i).getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                holder.text_message_name.setText(dataSnapshot.child("name").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return convertview;
    }
}
