package com.sup.Takwira;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.viewpagerindicator.CirclePageIndicator;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Main6Activity extends AppCompatActivity {

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<com.sup.Takwira.ImageModel> imageModelArrayList;

    private Bitmap[] myImageList;

    private TextView titleView, cancel, descriptionView, numberView, timeView, dateView, joinView;
    private ImageView imageView;
    private ImageButton btnjoin;
    private FirebaseClient firebaseClient;
    private DatabaseReference mDatabase;
    private String[] list2;
    private FirebaseAuth mAuth;
    private String currentuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.primary, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.primary));
        }
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primary)));

        titleView = (TextView) findViewById(R.id.title);
        descriptionView = (TextView) findViewById(R.id.description);
        numberView = (TextView) findViewById(R.id.number);
        timeView = (TextView) findViewById(R.id.time);
        dateView = (TextView) findViewById(R.id.date);
        joinView = (TextView) findViewById(R.id.join);
        imageView = (ImageView) findViewById(R.id.image);
        cancel = (TextView) findViewById(R.id.cancel);

        String TempHolder = getIntent().getStringExtra("ListViewClickedValue");
        String[] list = TempHolder.split(",");
        String date = list[0].replace("{date=", "");
        String number = list[1].replace("number=", "");
        String description = list[4].replace(" description=", "");
        String id = list[5].replace(" id=", "");
        String time = list[6].replace(" time=", "");
        String title = list[7].replace(" title=", "");
        String url = list[8].replace(" url=", "");
        String url2 = list[3].replace(" url2=", "");
        String url3 = list[2].replace(" url3=", "");
        url = url.replace("}","");

        imageModelArrayList = new ArrayList<>();
        imageModelArrayList = populateList(url, url2, url3);

        init();

        titleView.setText(title);
        descriptionView.setText(description);
        numberView.setText(number);
        dateView.setText(date);
        timeView.setText(time);
        getSupportActionBar().setTitle(title);

        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                mDatabase = FirebaseDatabase.getInstance().getReference();
                String TempHolder = getIntent().getStringExtra("ListViewClickedValue");
                String[] list = TempHolder.split(",");
                String id = list[5].replace(" id=", "");

                mDatabase.child("users").child(currentuser).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot uniqueKey : dataSnapshot.getChildren()) {
                            if(uniqueKey.getValue().toString().equals(id)){
                                mDatabase.child("users").child(currentuser).child(uniqueKey.getKey()).removeValue();
                                Intent intent = new Intent(Main6Activity.this, Main5Activity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                                break;
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

    public void onClick_chat(View view){
        String TempHolder = getIntent().getStringExtra("ListViewClickedValue");
        String[] list = TempHolder.split(",");
        String id = list[5].replace(" id=", "");

        Intent intent = new Intent(Main6Activity.this, chat.class);
        intent.putExtra("id", id);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    private ArrayList<com.sup.Takwira.ImageModel> populateList(String url, String url2, String url3){

        ArrayList<com.sup.Takwira.ImageModel> list = new ArrayList<>();
        URL url4 = null;
        try {
            url4 = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Bitmap bmp1 = null;
        try {
            bmp1 = BitmapFactory.decodeStream(url4.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        URL url5 = null;
        try {
            url5 = new URL(url2);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Bitmap bmp2 = null;
        try {
            bmp2 = BitmapFactory.decodeStream(url5.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        URL url6 = null;
        try {
            url6 = new URL(url3);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Bitmap bmp3 = null;
        try {
            bmp3 = BitmapFactory.decodeStream(url6.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        myImageList = new Bitmap[]{bmp1, bmp2, bmp3};
        for(int i = 0; i < 3; i++){
            com.sup.Takwira.ImageModel imageModel = new com.sup.Takwira.ImageModel();
            imageModel.setImage_drawable(myImageList[i]);
            list.add(imageModel);
        }

        return list;
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,Main5Activity.class);
        startActivity(intent);
        Main6Activity.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }
    private void init() {

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new com.sup.Takwira.SlidingImage_Adapter(Main6Activity.this,imageModelArrayList));

        CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

        indicator.setRadius(5 * density);

        NUM_PAGES =imageModelArrayList.size();

        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }

}
