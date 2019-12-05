package com.sup.Takwira;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sup.Takwira.R;

public class ProfileActivity extends AppCompatActivity {

    private DatabaseReference refdb;
    private FirebaseDatabase database;
    private String currentuser;
    private String name, address, email, mobile;
    private TextView nameView, addressView, emailView, mobileView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.primary, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.primary));
        }
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primary)));

        nameView = (TextView) findViewById(R.id.name);
        addressView = (TextView) findViewById(R.id.address);
        emailView = (TextView) findViewById(R.id.email);
        mobileView = (TextView) findViewById(R.id.mobile);

        database = FirebaseDatabase.getInstance();
        refdb = database.getReference();
        currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        refdb.child("usersacc").child(currentuser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name = dataSnapshot.child("name").getValue().toString();
                address = dataSnapshot.child("address").getValue().toString();
                email = dataSnapshot.child("email").getValue().toString();
                mobile = dataSnapshot.child("mobile").getValue().toString();

                nameView.setText(name);
                addressView.setText(address);
                emailView.setText(email);
                mobileView.setText(mobile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ProfileActivity.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

}
