package com.sup.Takwira;

import android.content.Context;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;


import java.util.ArrayList;


public class FirebaseClient2  {

    Context c;
    String DB_URL;
    ListView listView;
    Firebase firebase;
    ArrayList<Pub> pubs= new ArrayList<>();
    CustomAdapter customAdapter;
    DatabaseReference mDatabase;
    DataSnapshot x;

    public  FirebaseClient2(Context c, String DB_URL, ListView listView)
    {
        this.c= c;
        this.DB_URL= DB_URL;
        this.listView= listView;
        Firebase.setAndroidContext(c);
        firebase=new Firebase(DB_URL);
    }

    public  void refreshdata2()
    {
        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.getKey().equals("pub")){
                    x=dataSnapshot;
                }else if(dataSnapshot.getKey().equals("users")){
                    getupdates(x, dataSnapshot);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.getKey().equals("pub")){
                    x=dataSnapshot;
                }else if(dataSnapshot.getKey().equals("users")){
                    getupdates(x, dataSnapshot);
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

    public void getupdates(DataSnapshot dataSnapshot, DataSnapshot dataSnapshot2 ){

        pubs.clear();
        try {
            String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
            String[] L = dataSnapshot2.child(currentuser).getValue().toString().split(",");
            String[] L2;
            for(int j=0;j<L.length;j++){
                if(j==L.length-1){
                    L[j] = L[j].substring(0, L[j].length() - 1);
                    L2=L[j].split("=");
                }else{
                    L2=L[j].split("=");
                }
                for(DataSnapshot ds :dataSnapshot.getChildren()){
                    if(ds.getValue(Pub.class).getId().equals(L2[1])) {
                        Pub d = new Pub();
                        d.setTitle(ds.getValue(Pub.class).getTitle());
                        d.setDescription(ds.getValue(Pub.class).getDescription());
                        d.setNumber(ds.getValue(Pub.class).getNumber());
                        d.setId(ds.getValue(Pub.class).getId());
                        d.setUrl(ds.getValue(Pub.class).getUrl());
                        d.setUrl2(ds.getValue(Pub.class).getUrl2());
                        d.setUrl3(ds.getValue(Pub.class).getUrl3());
                        pubs.add(d);
                    }
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
        }catch (Exception e){
            Toast.makeText(c, "No data", Toast.LENGTH_SHORT).show();
        }
    }
}
