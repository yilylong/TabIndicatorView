package com.zhl.tabindicatorview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.zhl.tabindicatorview.R;
import com.zhl.tabindicatorview.util.MeasureUtil;

/**
 * 描述：一个导航tab的item
 * Created by zhaohl on 2016-7-25.
 */
public class TabItemView extends View {
    public static final int STATE_UNSELECTED = 0;
    public static final int STATE_SELECTED = 1;
    private int tabStrokeUncheckColor = 0xFFB5B5B5;
    private int tabStrokeCheckedColor = 0xFFB5B5B5;
    private int titleUnselectedColor = 0xFFB5B5B5;
    private int titleSelectedColor = 0xFF000000;
    private int tabStrokeWidth = 3;
    private int position;
    private String title;
    private int state = STATE_UNSELECTED;
    private OnTapListener onTapListener;
    private Paint paint;
    private int tabsCount = 4;
    private int textSize = 30;
    private int roundRadiu = 5;

    public TabItemView(Context context, String title, int position, int tabsCount) {
        this(context);
        this.title = title;
        this.position = position;
        this.tabsCount = tabsCount;
    }

    public TabItemView(Context context) {
        this(context, null);
    }

    public TabItemView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public TabItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TabItemView);
        tabStrokeUncheckColor = array.getColor(R.styleable.TabItemView_tab_strokeuncheck_color, tabStrokeUncheckColor);
        tabStrokeCheckedColor = array.getColor(R.styleable.TabItemView_tab_strokecheck_color, tabStrokeCheckedColor);
        tabStrokeWidth = MeasureUtil.dp2px(context, array.getInteger(R.styleable.TabItemView_tab_stroke_width, 0));
        if (tabStrokeWidth == 0) {
            tabStrokeWidth = 3;
        }
        titleUnselectedColor = array.getColor(R.styleable.TabItemView_title_unselected_color, titleUnselectedColor);
        titleSelectedColor = array.getColor(R.styleable.TabItemView_title_selected_color, titleSelectedColor);
        state = array.getInteger(R.styleable.TabItemView_tab_state, 0);
        textSize = MeasureUtil.dp2px(context, array.getInteger(R.styleable.TabItemView_tab_textsize, 0));
        if (textSize == 0) {
            textSize = 50;
        }
        roundRadiu = MeasureUtil.dp2px(context, array.getInteger(R.styleable.TabItemView_tab_roundradiu, 10));
        title = array.getString(R.styleable.TabItemView_tab_title);
        position = array.getInteger(R.styleable.TabItemView_tab_position, 1);
        array.recycle();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }


    public void setRoundRadiu(int roundRadiu) {
        this.roundRadiu = roundRadiu;
        invalidate();
    }

    public int getTitleSelectedColor() {
        return titleSelectedColor;
    }

    public void setTitleSelectedColor(int titleSelectedColor) {
        this.titleSelectedColor = titleSelectedColor;
        invalidate();
    }

    public int getTitleUnselectedColor() {
        return titleUnselectedColor;
    }

    public void setTitleUnselectedColor(int titleUnselectedColor) {
        this.titleUnselectedColor = titleUnselectedColor;
        invalidate();
    }

    public int getTabStrokeWidth() {
        return tabStrokeWidth;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                setState(STATE_SELECTED);
                break;
        }
        return super.onTouchEvent(event);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        switch (state) {
            case STATE_UNSELECTED:
                drawUnchecked(canvas);
                break;
            case STATE_SELECTED:
                drawChecked(canvas);
                break;
        }
    }

    private void drawChecked(Canvas canvas) {
        paint.setColor(tabStrokeCheckedColor);
        paint.setStyle(Paint.Style.FILL);
        float border = tabStrokeWidth/2;
        if (position == 0) {
//            Path path = new Path();
//            path.moveTo(roundRadiu, 0);
//            path.lineTo(getMeasuredWidth(), 0);
//            path.lineTo(getMeasuredWidth(), getMeasuredHeight());
//            path.lineTo(roundRadiu, getMeasuredHeight());
//            RectF arcLeftBottom = new RectF(0, getMeasuredHeight() - 2 * roundRadiu, 2 * roundRadiu, getMeasuredHeight());
//            path.arcTo(arcLeftBottom, 90, 90);
//            path.lineTo(0, roundRadiu);
//            RectF arcLeftTop = new RectF(0, 0, 2 * roundRadiu, 2 * roundRadiu);
//            path.arcTo(arcLeftTop, 180, 90);
//            canvas.drawPath(path, paint);
            Path path = new Path();
            PointF pointTopLeft = new PointF(border+roundRadiu, border);
            PointF pointTopRight = new PointF(getMeasuredWidth()-border, border);
            PointF pointBottomRight = new PointF(getMeasuredWidth()-border, getMeasuredHeight()-border);
            PointF pointBottomLeft = new PointF(border+roundRadiu, getMeasuredHeight()-border);
            RectF arcLeftBottom = new RectF(border, getMeasuredHeight()-border - 2 * roundRadiu, 2 * roundRadiu+border, getMeasuredHeight()-border);
            RectF arcLeftTop = new RectF(border, border, 2 * roundRadiu+border, 2 * roundRadiu+border);
            path.moveTo(pointTopLeft.x,pointTopLeft.y);
            path.lineTo(pointTopRight.x,pointTopRight.y);
            path.lineTo(pointBottomRight.x,pointBottomRight.y);
            path.lineTo(pointBottomLeft.x,pointBottomLeft.y);
            path.arcTo(arcLeftBottom, 90, 90);
            path.lineTo(border, roundRadiu+border);
            path.arcTo(arcLeftTop, 180, 90);
            canvas.drawPath(path, paint);

        } else if (position == tabsCount - 1) {
//            Path path = new Path();
//            path.moveTo(0, 0);
//            path.lineTo(getMeasuredWidth() - roundRadiu, 0);
//            RectF arcRightTop = new RectF(getMeasuredWidth() - 2 * roundRadiu, 0, getMeasuredWidth(), 2 * roundRadiu);
//            path.arcTo(arcRightTop, 270, 90);
//            path.lineTo(getMeasuredWidth(), getMeasuredHeight() - roundRadiu);
//            RectF arcRightBottom = new RectF(getMeasuredWidth() - 2 * roundRadiu, getMeasuredHeight() - 2 * roundRadiu, getMeasuredWidth(), getMeasuredHeight());
//            path.arcTo(arcRightBottom, 0, 90);
//            path.lineTo(0, getMeasuredHeight());
//            path.close();
//            canvas.drawPath(path, paint);

            Path path = new Path();
            // 左上
            path.moveTo(border, border);
            // 右上
            path.lineTo(getMeasuredWidth()-border - roundRadiu, border);
            // 右上圆弧
            RectF arcRightTop = new RectF(getMeasuredWidth()-border - 2 * roundRadiu, border, getMeasuredWidth()-border, 2 * roundRadiu+border);
            path.arcTo(arcRightTop, 270, 90);
            // 右下
            path.lineTo(getMeasuredWidth()-border, getMeasuredHeight()-border - roundRadiu);
            // 右下圆弧
            RectF arcRightBottom = new RectF(getMeasuredWidth() -border- 2 * roundRadiu, getMeasuredHeight() -border- 2 * roundRadiu, getMeasuredWidth()-border, getMeasuredHeight()-border);
            path.arcTo(arcRightBottom, 0, 90);
            // 左下
            path.lineTo(border, getMeasuredHeight()-border);
            path.close();
            canvas.drawPath(path, paint);

        } else {
            canvas.drawRect(new RectF(border, border, getMeasuredWidth()-border, getMeasuredHeight()-border), paint);
        }
        paint.setTextSize(textSize);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(titleSelectedColor);
        Rect rect = new Rect();
        paint.getTextBounds(title, 0, title.length(), rect);
        float textWidth = rect.width();
        Paint.FontMetrics metrics = paint.getFontMetrics();
        float baseline = (getMeasuredHeight() - metrics.bottom + metrics.top) / 2 - metrics.top;
        canvas.drawText(title, getWidth() / 2 - textWidth / 2, baseline, paint);
        if (onTapListener != null) {
            onTapListener.onTaped(this, position);
        }
    }

    private void drawUnchecked(Canvas canvas) {
        paint.setColor(tabStrokeUncheckColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(tabStrokeWidth);
        if (position == 0) {
            Path path = new Path();
            PointF pointTopLeft = new PointF(tabStrokeWidth+roundRadiu, tabStrokeWidth);
            PointF pointTopRight = new PointF(getMeasuredWidth()-tabStrokeWidth, tabStrokeWidth);
            PointF pointBottomRight = new PointF(getMeasuredWidth()-tabStrokeWidth, getMeasuredHeight()-tabStrokeWidth);
            PointF pointBottomLeft = new PointF(tabStrokeWidth+roundRadiu, getMeasuredHeight()-tabStrokeWidth);
            RectF arcLeftBottom = new RectF(tabStrokeWidth, getMeasuredHeight()-tabStrokeWidth - 2 * roundRadiu, 2 * roundRadiu+tabStrokeWidth, getMeasuredHeight()-tabStrokeWidth);
            RectF arcLeftTop = new RectF(tabStrokeWidth, tabStrokeWidth, 2 * roundRadiu+tabStrokeWidth, 2 * roundRadiu+tabStrokeWidth);
            path.moveTo(pointTopLeft.x,pointTopLeft.y);
            path.lineTo(pointTopRight.x,pointTopRight.y);
            path.lineTo(pointBottomRight.x,pointBottomRight.y);
            path.lineTo(pointBottomLeft.x,pointBottomLeft.y);
            path.arcTo(arcLeftBottom, 90, 90);
            path.lineTo(tabStrokeWidth, roundRadiu+tabStrokeWidth);
            path.arcTo(arcLeftTop, 180, 90);
            canvas.drawPath(path, paint);
        } else if (position == tabsCount - 1) {
            Path path = new Path();
            // 左上
            path.moveTo(tabStrokeWidth, tabStrokeWidth);
            // 右上
            path.lineTo(getMeasuredWidth()-tabStrokeWidth - roundRadiu, tabStrokeWidth);
            // 右上圆弧
            RectF arcRightTop = new RectF(getMeasuredWidth()-tabStrokeWidth - 2 * roundRadiu, tabStrokeWidth, getMeasuredWidth()-tabStrokeWidth, 2 * roundRadiu+tabStrokeWidth);
            path.arcTo(arcRightTop, 270, 90);
            // 右下
            path.lineTo(getMeasuredWidth()-tabStrokeWidth, getMeasuredHeight()-tabStrokeWidth - roundRadiu);
            // 右下圆弧
            RectF arcRightBottom = new RectF(getMeasuredWidth() -tabStrokeWidth- 2 * roundRadiu, getMeasuredHeight() -tabStrokeWidth- 2 * roundRadiu, getMeasuredWidth()-tabStrokeWidth, getMeasuredHeight()-tabStrokeWidth);
            path.arcTo(arcRightBottom, 0, 90);
            // 左下
            path.lineTo(tabStrokeWidth, getMeasuredHeight()-tabStrokeWidth);
            path.close();
            canvas.drawPath(path, paint);
        } else {
            canvas.drawRect(new RectF(tabStrokeWidth, tabStrokeWidth, getMeasuredWidth()-tabStrokeWidth, getMeasuredHeight()-tabStrokeWidth),paint);
        }
        paint.setTextSize(textSize);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(titleUnselectedColor);
//        float textHeight = getTextHeight(paint);
        Rect rect = new Rect();
        paint.getTextBounds(title, 0, title.length(), rect);
        float textWidth = rect.width();
        Paint.FontMetrics metrics = paint.getFontMetrics();
        float baseline = (getMeasuredHeight() - metrics.bottom + metrics.top) / 2 - metrics.top;
        canvas.drawText(title, getWidth() / 2 - textWidth / 2, baseline, paint);
    }

    public int getTabStrokeCheckedColor() {
        return tabStrokeCheckedColor;
    }

    public void setTabStrokeCheckedColor(int tabStrokeCheckedColor) {
        this.tabStrokeCheckedColor = tabStrokeCheckedColor;
    }

    public int getTabStrokeUncheckColor() {
        return tabStrokeUncheckColor;
    }

    public void setTabStrokeUncheckColor(int tabStrokeUncheckColor) {
        this.tabStrokeUncheckColor = tabStrokeUncheckColor;
    }

    public void setTabStrokeWidth(int tabStrokeWidth) {
        this.tabStrokeWidth = tabStrokeWidth;
        postInvalidate();
    }

    public void setTabsCount(int tabsCount) {
        this.tabsCount = tabsCount;
        postInvalidate();
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
        postInvalidate();
    }

    public void setState(int state) {
        this.state = state;
        postInvalidate();
    }

    public int getState() {
        return this.state;
    }

    public boolean isSelected() {
        return this.state == STATE_SELECTED;
    }


    public void setOnTapListener(OnTapListener tapListener) {
        this.onTapListener = tapListener;
    }

    private float getTextHeight(Paint p) {
        Paint.FontMetrics fontMetrics = p.getFontMetrics();
        return fontMetrics.descent - fontMetrics.ascent;
    }

    public interface OnTapListener {
        public void onTaped(TabItemView seatView, int position);
    }
}
