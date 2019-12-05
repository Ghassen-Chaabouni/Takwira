package com.sup.Takwira;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class FirebaseChatClient  {

    Context c;
    String DB_URL;
    ListView listView;
    Firebase firebase;
    ArrayList<ChatFirebase> chatFirebase= new ArrayList<>();
    CustomAdapter2 customAdapter;
    private String currentuser;
    private TextView text;

    public  FirebaseChatClient(Context c, String DB_URL, ListView listView)
    {
        this.c= c;
        this.DB_URL= DB_URL;
        this.listView= listView;
        Firebase.setAndroidContext(c);
        firebase=new Firebase(DB_URL);
    }

    public  void savedata(String message, String id1, String id2)
    {
        ChatFirebase d= new ChatFirebase();
        d.setMessage(message);
        currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        d.setId(currentuser);
        firebase.child("chat").child(id1).child(id2).child(currentuser).child("message").setValue(message);
    }

    public  void refreshdata(String id)
    {
        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.getKey().equals("chat")){
                    getupdates(dataSnapshot.child(id), id);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.getKey().equals("chat")) {
                    getupdates(dataSnapshot.child(id), id);
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

    public void getupdates(DataSnapshot dataSnapshot, String id){

        chatFirebase.clear();
        int i=1;
        for(DataSnapshot ds :dataSnapshot.getChildren()){
            for(DataSnapshot ds2 :dataSnapshot.child(Integer.toString(i)).getChildren()) {
                for (DataSnapshot ds3 : dataSnapshot.child(Integer.toString(i)).child(ds2.getKey()).getChildren()) {
                    Log.i("ok456",ds3.getValue().toString());
                    ChatFirebase d = new ChatFirebase();
                    d.setMessage(ds3.getValue().toString());
                    d.setId(ds2.getKey());
                    chatFirebase.add(d);
                }
            }
            i=i+1;
        }
        if(chatFirebase.size()>0)
        {
            customAdapter=new CustomAdapter2(c, chatFirebase);
            listView.setAdapter((ListAdapter) customAdapter);
        }else
        {
            ChatFirebase d = new ChatFirebase();
            d.setMessage("Say Hi!");
            d.setId("welcome");
            chatFirebase.add(d);
            firebase.child("chat").child(id).child("1").child("welcome").child("message").setValue("Say Hi!");
        }
    }
    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

}
