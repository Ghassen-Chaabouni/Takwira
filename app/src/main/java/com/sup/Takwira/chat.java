package com.sup.Takwira;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class chat extends AppCompatActivity {

    final static  String DB_URL= "https://countme-3ea02.firebaseio.com/";
    ListView listView;
    FirebaseChatClient firebaseChatClient;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private String id, message, id2, id3, currentuser;
    private EditText messageView;
    private DatabaseReference refdb;
    private FirebaseDatabase database;
    private String[] ch;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.primary, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.primary));
        }
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primary)));

        listView=(ListView)findViewById(R.id.message_list);
        firebaseChatClient= new FirebaseChatClient(this, DB_URL,listView);

        id = getIntent().getStringExtra("id");
        firebaseChatClient.refreshdata(id);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference refdb = database.getReference();
        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() == null)
        {
            finish();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }

    }


    public void onClickSend(View view) {
        messageView = (EditText) findViewById(R.id.edittext_chatbox);
        message = messageView.getText().toString();
        if(!message.equals("")){
            id2 = getIntent().getStringExtra("id");

            database = FirebaseDatabase.getInstance();
            refdb = database.getReference();
            id3="";
            refdb.child("chat").child(id2).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    id3=(dataSnapshot.getChildrenCount()+1)+"";
                    firebaseChatClient.savedata(message, id2, id3);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            messageView.setText("");
        }
    }

        @Override
    public void onBackPressed() {
        super.onBackPressed();
        chat.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }
}
