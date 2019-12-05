package com.sup.Takwira;

import android.content.Context;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

public class FirebaseClient  {

    Context c;
    String DB_URL;
    ListView listView;
    Firebase firebase;
    ArrayList<Pub> pubs= new ArrayList<>();
    CustomAdapter customAdapter;

    public  FirebaseClient(Context c, String DB_URL, ListView listView)
    {
        this.c= c;
        this.DB_URL= DB_URL;
        this.listView= listView;
        Firebase.setAndroidContext(c);
        firebase=new Firebase(DB_URL);
    }

    public  void savedata(String number)
    {
        Pub d= new Pub();
        d.setNumber(number);
        firebase.child("pub").push().setValue(d);
    }

    public  void refreshdata()
    {
        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.getKey().equals("pub")){
                    getupdates(dataSnapshot);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.getKey().equals("pub")) {
                    getupdates(dataSnapshot);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

    public void getupdates(DataSnapshot dataSnapshot){

        pubs.clear();
        for(DataSnapshot ds :dataSnapshot.getChildren()){
                Pub d= new Pub();
                d.setTitle(ds.getValue(Pub.class).getTitle());
                d.setDescription(ds.getValue(Pub.class).getDescription());
                d.setNumber(ds.getValue(Pub.class).getNumber());
                d.setId(ds.getValue(Pub.class).getId());
                d.setUrl(ds.getValue(Pub.class).getUrl());
                d.setUrl2(ds.getValue(Pub.class).getUrl2());
                d.setUrl3(ds.getValue(Pub.class).getUrl3());
                d.setTime(ds.getValue(Pub.class).getTime());
                d.setDate(ds.getValue(Pub.class).getDate());
                pubs.add(d);

        }
        if(pubs.size()>0)
        {
            customAdapter=new CustomAdapter(c, pubs);
            listView.setAdapter((ListAdapter) customAdapter);
        }else
        {
            Toast.makeText(c, "No data", Toast.LENGTH_SHORT).show();
        }
    }

}
