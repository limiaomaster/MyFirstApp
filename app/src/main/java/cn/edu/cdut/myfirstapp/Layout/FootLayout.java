package cn.edu.cdut.myfirstapp.Layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.edu.cdut.myfirstapp.R;


/**
 * Created by Administrator on 2016/5/26 0026.
 */

public class FootLayout extends RelativeLayout implements  OnClickListener {

    private int index;
    private int currentIndex;

    private ImageView[] imageViews;
    private TextView[] textViews;




    public FootLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode()) { return; }
        LayoutInflater.from(context).inflate(R.layout.foot_main,this);

        initView();

    }

    private void initView() {


/*
        imageViews = new ImageView[2];
        imageViews[0] = (ImageView) findViewById(R.id.iv_zhuye);
        imageViews[1] = (ImageView) findViewById(R.id.iv_wode);
        imageViews[0].setSelected(true);

        textViews = new TextView[2];
        textViews[0] = (TextView) findViewById(R.id.tv_zhuye);
        textViews[1] = (TextView) findViewById(R.id.tv_wode);
        textViews[0].setTextColor(getResources().getColor(R.color.selected));

        LinearLayout ll_zhuye = (LinearLayout) findViewById(R.id.LL_zhuye);
        LinearLayout ll_wode = (LinearLayout) findViewById(R.id.LL_wode);

        ll_zhuye.setOnClickListener(this);
        ll_wode.setOnClickListener(this);*/


    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.LL_zhuye:
                index = 0;
                break;

            case R.id.LL_wode:
                index = 1;
                break;
        }

        imageViews[currentIndex].setSelected(false);
        imageViews[index].setSelected(true);

        textViews[currentIndex].setTextColor(getResources().getColor(R.color.unselected));
        textViews[index].setTextColor(getResources().getColor(R.color.selected));

        currentIndex = index;
    }
}
