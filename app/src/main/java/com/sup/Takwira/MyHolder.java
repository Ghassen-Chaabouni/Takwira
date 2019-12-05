package com.sup.Takwira;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MyHolder {

    TextView titleTxt;
    TextView numberTxt;
    TextView descriptionTxt;
    ImageView img;
    public MyHolder(View itemView) {
        titleTxt= (TextView) itemView.findViewById(R.id.titleTxt);
        numberTxt= (TextView) itemView.findViewById(R.id.numberTxt);
        img=(ImageView) itemView.findViewById(R.id.img);
    }
}
