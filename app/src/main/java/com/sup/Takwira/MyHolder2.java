package com.sup.Takwira;

import android.view.View;
import android.widget.TextView;

public class MyHolder2 {

    TextView text_message_name;
    TextView text_message_body;
    public MyHolder2(View itemView) {
        text_message_name= (TextView) itemView.findViewById(R.id.text_message_name);
        text_message_body= (TextView) itemView.findViewById(R.id.text_message_body);
    }
}
