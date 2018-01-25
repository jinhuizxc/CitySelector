package com.example.jinhui.cityselector.effcet1;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by jinhui on 2018/1/25.
 * Email:1004260403@qq.com
 *
 * 绘制间距和快速选择ui
 */

public class ItemDecoration extends RecyclerView.ItemDecoration {

    private int dp;
    public ItemDecoration(int dp) {
        this.dp = dp;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        c.drawColor(Color.parseColor("#e2e2e2"));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = dp;
    }
}
