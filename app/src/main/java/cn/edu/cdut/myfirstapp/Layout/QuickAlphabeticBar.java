package cn.edu.cdut.myfirstapp.Layout;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;

import cn.edu.cdut.myfirstapp.R;


/**
 * 字母索引条
 * 
 * @author Administrator
 * 
 */
public class QuickAlphabeticBar extends ImageButton {
	private TextView mDialogText; // 中间显示字母的文本框
	private Handler mHandler; // 处理UI的句柄
	private ListView mListView; // 列表
	private float mHight; // 高度
	// 字母列表索引
	private String[] string_A2Z = new String[] { "#", "A", "B", "C", "D", "E",
			"F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
			"S", "T", "U", "V", "W", "X", "Y", "Z" };
	// 字母索引哈希表
	private HashMap<String, Integer> hashMap;
	private Paint paint = new Paint();
	private int choose = -1;

	public QuickAlphabeticBar(Context context) {
		super(context);
	}

	public QuickAlphabeticBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public QuickAlphabeticBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}


    //先执行onMeasure()方法，然后就立刻执行onDraw()方法。
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //Log.v("onMeasure()","我被调用了！");
        // this.mHight = this.getHeight();

    }



    // 初始化
	public void init(Activity ctx) {
		mDialogText = (TextView) ctx.findViewById(R.id.fast_position);
		mDialogText.setVisibility(View.INVISIBLE);
		mHandler = new Handler();
	}

	// 设置需要索引的列表
	public void setListView(ListView mList) {
		this.mListView = mList;
        //Log.v("setListView()","this.mList = mList;");
	}

	// 设置字母索引哈希表
	public void setAlphaIndexer(HashMap<String, Integer> map) {
		this.hashMap = map;

	}

	// 设置字母索引条的高度
	public void setHight(float mHight) {
		this.mHight = mHight;
        //Log.v("setHight()","this.mHight = mHight;");
		//Log.v("setHight()","传进来的参数mHight = "+mHight);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//Log.v("onTouchEvent()","onTouchEvent方法开始执行------------------------------------>");
		int act = event.getAction();
		float y = event.getY();
       // Log.v("onTouchEvent()","手指点击位置的Y坐标为："+y);
		final int oldChoose = choose;
		// 计算手指位置，找到对应的段，让mList移动段开头的位置上
		int selectIndex = (int) (y / (mHight / string_A2Z.length));//第几号字母
      //  Log.v("onTouchEvent()","所以点击的是第 "+selectIndex+" 号字母");
		//Log.v("onTouchEvent","快速滚动条的实际高度为："+mHight);

		if (selectIndex > -1 && selectIndex < string_A2Z.length) { // 防止越界
			String key = string_A2Z[selectIndex]; //根据几号字母，得到字母，2就是C。
         //   Log.v("onTouchEvent()","该字母是 "+key);
			if (hashMap.containsKey(key)) {	//如果哈希表中包含所点击的字母，就让中间显示出该字母，否则不显示。
            //    Log.v("onTouchEvent()","您的联系人中有该字母的人！");
				int pos = hashMap.get(key);	//根据Key得到Value，根据点击的字母得到第几位联系人。

				if (mListView.getHeaderViewsCount() > 0) { // 防止ListView有标题栏,本例中没有
					this.mListView.setSelectionFromTop(
							pos + mListView.getHeaderViewsCount(), 0);
				} else {
					this.mListView.setSelectionFromTop(pos, 0);
				}
				mDialogText.setText(string_A2Z[selectIndex]);
			}
            else
               Log.v("onTouchEvent()","您的联系人中没有该字母的人！！");
		}
        //完成此if后，只会显示有该拼音的联系人首字母，不会显示没有该联系人的字母。
        //比如没有R开头的联系人，就不会显示R。

        //下面的switch语句负责把字母显示出来。
		switch (act) {

		case MotionEvent.ACTION_DOWN:
            Log.v("MotionEvent判断您的触摸类型。。。","触摸类型为。。。ACTION_DOWN..........");
			boolean showBkg = true;
			if (oldChoose != selectIndex) {
				if (selectIndex > 0 && selectIndex < string_A2Z.length) {
					choose = selectIndex;
					invalidate();
				}
			}
			if (mHandler != null) {
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						if (mDialogText != null && mDialogText.getVisibility() == View.INVISIBLE) {
							mDialogText.setVisibility(VISIBLE);
						}
					}
				});
			}
			break;


		case MotionEvent.ACTION_MOVE:
            Log.v("MotionEvent判断您的触摸类型。。。","触摸类型为。。。ACTION_MOVE..........");
			if (oldChoose != selectIndex) {
				if (selectIndex > 0 && selectIndex < string_A2Z.length) {
					choose = selectIndex;
					invalidate();
				}
			}
			break;

		case MotionEvent.ACTION_UP:
            Log.v("MotionEvent判断您的触摸类型。。。","触摸类型为。。。ACTION_UP.............");
			showBkg = false;
			choose = -1;
			if (mHandler != null) {
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						if (mDialogText != null && mDialogText.getVisibility() == View.VISIBLE) {
							mDialogText.setVisibility(INVISIBLE);
						}
					}
				});
			}
			break;


		default:
			break;
		}
		return super.onTouchEvent(event);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
     //   Log.v("onDrew()","onDraw()方法得到执行----------->");
        //在onDraw()方法中才能调用getHeight()方法来获取滚动条的高度，因为只有fragment3显示出来的时候，才会调用onDraw()方法
        //来执行绘制滚动条，只有滚动条被绘制出来了，才能获取高度，否则高度为0！
		int height = getHeight();
        //Log.v("onDrew()","高度为： "+height);
        this.mHight = height;

		int width = getWidth();
     //   Log.v("onDrew()-->getWidth()","得到的宽度为："+width);

        int sigleHeight = height / string_A2Z.length; // 单个字母占的高度

		for (int i = 0; i < string_A2Z.length; i++) {
			paint.setColor(Color.WHITE);
			paint.setTextSize(40);//设置大小单位是px
			//paint.setTypeface(Typeface.DEFAULT_BOLD);//设置字体
			paint.setAntiAlias(true);//抗锯齿

			if (i == choose) { 
				paint.setColor(Color.parseColor("#00BFFF")); // 滑动时按下字母颜色
                paint.setTextSize(100);
				paint.setFakeBoldText(true);
			}
			// 绘画的位置
			float xPos = width / 2 - paint.measureText(string_A2Z[i]) / 2;
       //     Log.v("onDrew()","绘画的位置为：X坐标    "+xPos);
			float yPos = sigleHeight * i + sigleHeight;
        //    Log.v("onDrew()","绘画的位置为：Y坐标    "+yPos);
			canvas.drawText(string_A2Z[i], xPos, yPos, paint);
			paint.reset();
		}
	}

}
