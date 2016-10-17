package com.zhl.tabindicatorview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zhl.tabindicatorview.R;
import com.zhl.tabindicatorview.bean.TabItem;

import java.util.ArrayList;

/**
 * 描述：
 * Created by zhaohl on 2016-9-30.
 */
public class TabIndicatorView extends LinearLayout {
    private ArrayList<TabItemView> tabItemViews = new ArrayList<TabItemView>();
    private ArrayList<TabItem> tabItems = new ArrayList<TabItem>();
    private OnTabItemCheckListener onTabItemCheckListener;
    private int tabTextSize = -1;
    private int cornerRadiu = -1;
    private int borderWidth = -1;
    private int tabCheckedTextColor = -1;
    private int tabUnCheckedTextColor = -1;
    private int tabCheckBGcolor = -1;
    private int tabUnCheckBGcolor = -1;
    private int defaultCheckedPos = -1;

    public TabIndicatorView(Context context) {
        this(context, null);
    }

    public TabIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public TabIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(LinearLayout.HORIZONTAL);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TabIndicatorView);
        tabCheckedTextColor = array.getColor(R.styleable.TabIndicatorView_tabCheckedTextColor,-1);
        tabUnCheckedTextColor = array.getColor(R.styleable.TabIndicatorView_tabUnCheckedTextColor,-1);
        tabCheckBGcolor = array.getColor(R.styleable.TabIndicatorView_tabCheckBGcolor,-1);
        tabUnCheckBGcolor = array.getColor(R.styleable.TabIndicatorView_tabUnCheckBGcolor,-1);
        tabTextSize = array.getInteger(R.styleable.TabIndicatorView_tabTextSize,-1);
        cornerRadiu = array.getInteger(R.styleable.TabIndicatorView_tabCornerRadiu,-1);
        borderWidth = array.getInteger(R.styleable.TabIndicatorView_tabBorderWidth,-1);
        array.recycle();
    }

    public void initTabs(ArrayList<TabItem> tabItems,OnTabItemCheckListener onTabCheckListener) {
        this.tabItems = tabItems;
        this.onTabItemCheckListener = onTabCheckListener;
        if(null!=tabItems){
            for(TabItem item:tabItems){
                TabItemView itemView = new TabItemView(getContext(),item.title,item.position,tabItems.size());
                if(tabCheckedTextColor!=-1){
                    itemView.setTitleSelectedColor(tabCheckedTextColor);
                }
                if(tabUnCheckedTextColor!=-1){
                    itemView.setTitleUnselectedColor(tabUnCheckedTextColor);
                }
                if(tabCheckBGcolor!=-1){
                    itemView.setTabStrokeCheckedColor(tabCheckBGcolor);
                }
                if(tabUnCheckBGcolor!=-1){
                    itemView.setTabStrokeUncheckColor(tabUnCheckBGcolor);
                }
                if(tabTextSize!=-1){
                    itemView.setTextSize(tabTextSize);
                }
                if(cornerRadiu!=-1){
                    itemView.setRoundRadiu(cornerRadiu);
                }
                if(borderWidth!=-1){
                    itemView.setTabStrokeWidth(borderWidth);
                }
                LayoutParams params = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
                params.weight = 1;
                if(item.position==0){
                    params.rightMargin = -itemView.getTabStrokeWidth();
                }else if(item.position==tabItems.size()-1){
                    params.leftMargin = -itemView.getTabStrokeWidth();
                }else{
                    params.leftMargin = -itemView.getTabStrokeWidth();
                    params.rightMargin = -itemView.getTabStrokeWidth();
                }
                addView(itemView,params);
                itemView.setOnTapListener(new TabItemView.OnTapListener() {
                    @Override
                    public void onTaped(TabItemView seatView, int position) {
                        if(onTabItemCheckListener!=null){
                            onTabItemCheckListener.onTabItemCheck(seatView,position);
                        }
                        for(int i=0;i<tabItemViews.size();i++){
                            if(position==i){
                                continue;
                            }
                            tabItemViews.get(i).setState(TabItemView.STATE_UNSELECTED);
                        }
                    }
                });
                tabItemViews.add(itemView);
            }
        }
    }

    public interface OnTabItemCheckListener{
        public void onTabItemCheck(TabItemView itemTabView, int position);
    }


    public int getTabTextSize() {
        return tabTextSize;
    }

    public void setTabTextSize(int tabTextSize) {
        this.tabTextSize = tabTextSize;
        invidateData();
    }

    public int getCornerRadiu() {
        return cornerRadiu;
    }

    public void setCornerRadiu(int cornerRadiu) {
        this.cornerRadiu = cornerRadiu;
        invidateData();
    }

    public int getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
        invidateData();
    }

    public int getTabCheckedTextColor() {
        return tabCheckedTextColor;
    }

    public void setTabCheckedTextColor(int tabCheckedTextColor) {
        this.tabCheckedTextColor = tabCheckedTextColor;
        invidateData();
    }

    public int getTabUnCheckedTextColor() {
        return tabUnCheckedTextColor;
    }

    public void setTabUnCheckedTextColor(int tabUnCheckedTextColor) {
        this.tabUnCheckedTextColor = tabUnCheckedTextColor;
        invidateData();
    }

    public int getTabCheckBGcolor() {
        return tabCheckBGcolor;
    }

    public void setTabCheckBGcolor(int tabCheckBGcolor) {
        this.tabCheckBGcolor = tabCheckBGcolor;
        invidateData();
    }

    public int getTabUnCheckBGcolor() {
        return tabUnCheckBGcolor;
    }

    public void setTabUnCheckBGcolor(int tabUnCheckBGcolor) {
        this.tabUnCheckBGcolor = tabUnCheckBGcolor;
        invidateData();
    }

    public ArrayList<TabItem> getTabItems() {
        return tabItems;
    }

    public int getDefaultCheckedPos() {
        return defaultCheckedPos;
    }

    public void setDefaultCheckedPos(int defaultCheckedPos) {
        if(defaultCheckedPos<0){
            return;
        }
        this.defaultCheckedPos = defaultCheckedPos;
        if(tabItemViews!=null&&tabItemViews.size()>0){
            tabItemViews.get(defaultCheckedPos).setState(TabItemView.STATE_SELECTED);
        }
    }

    public void invidateData(){
        if(tabItemViews!=null&&tabItemViews.size()>0){
            removeAllViews();
            tabItemViews.clear();
            initTabs(tabItems,onTabItemCheckListener);
            requestLayout();
        }
    }
}
