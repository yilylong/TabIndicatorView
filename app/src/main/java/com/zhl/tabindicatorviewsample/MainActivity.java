package com.zhl.tabindicatorviewsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zhl.tabindicatorview.bean.TabItem;
import com.zhl.tabindicatorview.view.PswdInputView;
import com.zhl.tabindicatorview.view.TabIndicatorView;
import com.zhl.tabindicatorview.view.TabItemView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabIndicatorView indicatorView = (TabIndicatorView) findViewById(R.id.tab_indicator_view);
        TabIndicatorView indicatorView2 = (TabIndicatorView) findViewById(R.id.tab_indicator_view2);
        final PswdInputView inputView = (PswdInputView) findViewById(R.id.tab_indicator_view3);
        Button remove = (Button) findViewById(R.id.removebtn);
        Button addbtn = (Button) findViewById(R.id.addBtn);
        ArrayList<TabItem> items = new ArrayList<TabItem>();
        for (int i = 0; i < 4; i++) {
            TabItem item = new TabItem();
            item.title = "近" + (i + 1) + "月";
            item.position = i;
            items.add(item);
        }
        indicatorView.initTabs(items, new TabIndicatorView.OnTabItemCheckListener() {
            @Override
            public void onTabItemCheck(TabItemView itemTabView, int position) {
                Toast.makeText(MainActivity.this, "当前选中==" + position, Toast.LENGTH_SHORT).show();
            }
        });
        indicatorView.setDefaultCheckedPos(1);

        ArrayList<TabItem> items2 = new ArrayList<TabItem>();
        for (int i = 0; i < 4; i++) {
            TabItem item = new TabItem();
            item.title = "第" + (i + 1) + "季度";
            item.position = i;
            items2.add(item);
        }
        indicatorView2.initTabs(items2, new TabIndicatorView.OnTabItemCheckListener() {
            @Override
            public void onTabItemCheck(TabItemView itemTabView, int position) {
                Toast.makeText(MainActivity.this, "当前选中==" + position, Toast.LENGTH_SHORT).show();
            }
        });
        indicatorView2.setDefaultCheckedPos(0);
        inputView.initTabs(6);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position>0){
                    --position;
                }
                inputView.removePsw(position);
            }
        });
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputView.insertPsw(position);
                if(position<6){
                    ++position;
                }
            }
        });
    }
}
