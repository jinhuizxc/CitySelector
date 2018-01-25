package com.example.jinhui.cityselector.effcet1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by jinhui on 2018/1/25.
 * Email:1004260403@qq.com
 * <p>
 * dispatchTouchEvent,onTouchEvent与onInterceptTouchEvent
 * 1.首先明白一个常识：View 没有onInterceptTouchEvent事件，而ViewGroup这三个事件都有，是viewgroup继承View之后才加了一个方法叫onIntercepTouchEvent。
 * 从字面意思可以看出，onInterceptTouchEvent是拦截器，用来拦截事件用的,dispatchTouchEvent是用来分发事件的，onTouchEvent是用来处理事件的。
 * 大家不难看出，应该是先走dispatchTouchEvent然后走onTouchEvent。那OnInterceptTouchEven的调用时机是什么时候呢？为了更好的理解这三个事件，我们从简单到复杂，先从一个子view，一个viewgroup,然后viewgroup里有子view。
 * <p>
 * 2.针对一个View来讲，事件是先走该View的dispatchTouchEvent，然后再走onTouchEvent(也有可能不走)。
 * <p>
 * 什么时候不会走onTouchEvent呢？当重写dispatchTouchEvent,不走super.dispatchTouchEvent直接返回false，它就不会走onTouchEvent。
 * 当然这样做是违反android架构常理的，一般的dispatchTouchEvent是不建议重写的。不过通过这个案例我们可以总结出这么一个结论.
 * <p>
 * 在事件到达view的时候，先走dispatchTouchEvent，在系统的dispatchTouchEvent中它会调用该view的Ontouch方法如果此onTouch方法的down事件里返回true，则
 * <p>
 * dispatchTouchEvent方法也返回true，且把以后的move事件,up事件都传给onTouch。之后的move事件及up事件的返回值，onTouch返回什么dispatchTouchEvent也返回什么。
 * <p>
 * 相反如果传第一个down事件给ontouch的时候，ontouch返回的是false,从此事件不再会传过来，也就是不会走dispatchTouchEvent。更不会走ontouchevent
 * 3.针对一个ViewGroup来讲(没有子view的时候)：
 * <p>
 * 事件的走向是dispatchTouchEvent->onInterceptTouchEvent->onTouchEvent
 * <p>
 * 我们会发现它们的逻辑跟view 的没什么两样，只是在走down事件的时候onInterceptTouchEvent会在中间，而这里不管onInterceptTouchEvent返回什么都不会干扰它像2.形容的那
 * <p>
 * 样运行，难道onInterceptTouchEvent这个方法没用？
 * <p>
 * 4.当Viewgroup里有子view的时候
 * <p>
 * <p>
 * down事件走向:viewgroup.dispatchTouchEvent->viewgroup.onInterceptTouchEvent ->如果返回true->viewgroup.onTouch-------------------------------分支1
 * |->如果返回false->view.dispatchTouchEvent分支2
 * <p>
 * <p>
 * <p>
 * 分支1：之后的move或up事件的走向是:viewgroup.dispatchTouchEvent->viewgroup.ontouch  这里不管ontouch返回的是什么都是这个走向
 * <p>
 * 分支2：down事件到了view.dispatchTouchEvent->view.onTouch->返回true->分支3
 * |->返回false->viewgroup.ontouch->返回true->move,up等事件viewgroup.dispatchTouchEvent-
 * |->返回false,则该viewgroup不会再收到后续事件了
 * >viewgroup.ontouch
 * <p>
 * <p>
 * 分支3：子view的onTOuch返回true了，表示子view能接受该事件，今后的事件走向是
 * <p>
 * Move:viewgroup.dispatchTouchEvent->viewgroup.onInterceptTouchEvent返回？
 * <p>
 * 如果返回的是false，以后的move,up都这么走viewgroup.dispatchTouchEvent->viewgroup.onInterceptTouchEvent->view.dispatchTouchEvent->view.ontouch
 * <p>
 * 如果返回的是true，抢夺子view的move事件接下来的走向是:强制传Cancel事件和UP事件给view,view.dispatchTouchEvent->view.ontouch(无视它返回什么)->然后把Move事件留给viewgroup:viewgroup.dispatchTouchEvent->viewgroup.ontouch
 * <p>
 * <p>
 * <p>
 * 这个现象大家应该在listview或是scrollview里见过，就是当用户在scrollview里按住一个按钮，发现按钮做了相应反应（按钮高亮了），但当按住不放拖它时，发现界面在滚动，这就是因为onInterceptTouchEvent抢事件了！
 * <p>
 * 如果有时间我会做一个app来验证以上的。谢谢大家
 * <p>
 * <p>
 * <p>
 * 转至：http://www.eoeandroid.com/thread-262615-1-1.html
 */

public class SelectorView extends View {


    private static final String TAG = "SelectorView";

    String[] b = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};

    private Paint paint = new Paint();

    private float mScale = 1;

    boolean showBg = false;

    int choose = -1;


    public SelectorView(Context context) {
        super(context);
    }

    public SelectorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mScale = (float) getContext().getResources().getDisplayMetrics().widthPixels / 800;
    }

    public SelectorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (showBg) {
            canvas.drawColor(Color.parseColor("#40000000"));
        }

        int height = getHeight();
        int width = getWidth();
        int singleHeight = height / b.length;
        for (int i = 0; i < b.length; i++) {
            paint.setColor(Color.GRAY);
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            int fontSize = (int) (18 * mScale);
            paint.setTextSize(fontSize);
            paint.setAntiAlias(true);
            if (i == choose) {
                paint.setColor(Color.parseColor("#3399ff"));
                paint.setFakeBoldText(true);
            }
            /**
             *     String str = "Hello";
             canvas.drawText( str , x , y , paint);

             //1. 粗略计算文字宽度
             Log.d(TAG, "measureText=" + paint.measureText(str));

             //2. 计算文字所在矩形，可以得到宽高
             Rect rect = new Rect();
             paint.getTextBounds(str, 0, str.length(), rect);
             int w = rect.width();
             int h = rect.height();
             Log.d(TAG, "w=" +w+"  h="+h);

             //3. 精确计算文字宽度
             int textWidth = getTextWidth(paint, str);
             Log.d(TAG, "textWidth=" + textWidth);

             public static int getTextWidth(Paint paint, String str) {
             int iRet = 0;
             if (str != null && str.length() > 0) {
             int len = str.length();
             float[] widths = new float[len];
             paint.getTextWidths(str, widths);
             for (int j = 0; j < len; j++) {
             iRet += (int) Math.ceil(widths[j]);
             }
             }
             return iRet;
             }
             */
            float xPos = width / 2 - paint.measureText(b[i]) / 2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(b[i], xPos, yPos, paint);
            paint.reset();
        }

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float y = event.getY();
        int oldChoose = choose;
        OnChangedListener listener = onChangedListener;
        // 计算选择的item对应的下标,
        int chooseIndex = (int) (y / getHeight() * b.length);
        Log.e(TAG, "y =" + y);
//        Log.e(TAG, "getHeight =" + getHeight()); // 1744
        Log.e(TAG, "chooseIndex =" + chooseIndex);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                showBg = true;
                if (oldChoose != chooseIndex && listener != null){
                    if (chooseIndex >= 0 && chooseIndex < b.length){
                        listener.onChanged(b[chooseIndex]);
                        choose = chooseIndex;
                        Log.e(TAG, "choose =" + chooseIndex);
                        invalidate();  // 刷新视图，不写不起作用
                    }
                }

                break;
            case MotionEvent.ACTION_MOVE:
                // 和down的写法完全一样，但是效果不一样，可以的
                if (oldChoose != chooseIndex && listener != null){
                    if (chooseIndex >= 0 && chooseIndex < b.length){
                        listener.onChanged(b[chooseIndex]);
                        choose = chooseIndex;
                        invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                showBg = false;
                choose = -1; // 置为-1，还原状态，不然再次点击相同字母没显示的
                invalidate();
                break;
        }

//        return super.dispatchTouchEvent(event);
        return true;  // 置为true
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    // 接口回调
    public interface OnChangedListener {
        void onChanged(String s);
    }

    private OnChangedListener onChangedListener;

    public void setOnChangedListener(OnChangedListener onChangedListener) {
        this.onChangedListener = onChangedListener;
    }
}
