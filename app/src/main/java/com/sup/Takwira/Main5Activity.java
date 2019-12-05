package com.sup.Takwira;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Main5Activity extends AppCompatActivity {

    final static  String DB_URL= "https://countme-3ea02.firebaseio.com/";
    ListView listView;
    FirebaseClient2 firebaseClient2;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.primary, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.primary));
        }
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primary)));

        listView=(ListView)findViewById(R.id.listview);
        firebaseClient2= new FirebaseClient2(this, DB_URL,listView);
        firebaseClient2.refreshdata2();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference refdb = database.getReference();
        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() == null)
        {
            finish();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                refdb.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String[] L = dataSnapshot.child("users").child(currentuser).getValue().toString().split(",");
                        String[] L2;
                        int b=0;
                        for(int j=0;j<L.length;j++) {
                            if (j == L.length - 1) {
                                L[j] = L[j].substring(0, L[j].length() - 1);
                                L2 = L[j].split("=");
                            } else {
                                L2 = L[j].split("=");
                            }
                            for (DataSnapshot uniqueKey : dataSnapshot.child("pub").getChildren()) {
                                if(L2[1].equals(uniqueKey.child("id").getValue().toString()) && j==position) {
                                    b=1;
                                    String booksValue = uniqueKey.getValue().toString();

                                    String test = booksValue;
                                    Log.i("test13021302",booksValue);
                                    Intent intent = new Intent(Main5Activity.this, Main6Activity.class);

                                    intent.putExtra("ListViewClickedValue", test);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                                    break;
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

        });

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,Main7Activity.class);
        startActivity(intent);
        Main5Activity.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }
}
