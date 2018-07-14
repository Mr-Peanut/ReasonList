package com.guans.reasonlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import util.MultiSpinner;
import util.ReasonItem;

public class MainActivity extends AppCompatActivity {
    private TextView reasonText;
    private List<ReasonItem> list;
    private MultiSpinner multiSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reasonText=findViewById(R.id.reasonText);
        if(list==null){
            list=new ArrayList<>();
            for(int i=0;i<=4;i++){
                ReasonItem item=new ReasonItem("10"+String.valueOf(i),"原因"+String.valueOf(i+1),(i == 2 || i == 4));
                list.add(item);
            }
            list.add(new ReasonItem("105","其他，请填写",false));
        }
        multiSpinner=new MultiSpinner(reasonText,list);
        //multiSpinner.setReasonsList(list);
        multiSpinner.setOkButtonClickedListener(new MultiSpinner.OnOkButtonClickedListener() {
            StringBuilder builder=new StringBuilder();
            @Override
            public void onOkClicked(List<ReasonItem> list, View view) {
                for(ReasonItem item:list){
                   if(item.getOriginalChecked()) {
                       builder.append(item.getReasonContent()).append("；");
                   }
                }
                reasonText.setText(builder);
            }
        });
        multiSpinner.setCancelButtonClickedListener(new MultiSpinner.OnCancelButtonClickedListener() {
            @Override
            public void onCancelClicked(List<ReasonItem> list, View view) {
                StringBuilder builder=new StringBuilder();
                for(ReasonItem item:list){
                    if(item.getOriginalChecked()) {
                        builder.append(item.getReasonContent()).append("；");
                    }
                }
                reasonText.setText(builder);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(multiSpinner.isPopuWindowOnShow()){
            multiSpinner.cancelPopWindow();
            return;
        }
        super.onBackPressed();
    }
}
