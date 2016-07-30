package cn.edu.cdut.myfirstapp.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.edu.cdut.myfirstapp.Fragment.Fragment3;
import cn.edu.cdut.myfirstapp.Fragment.Fragment4;
import cn.edu.cdut.myfirstapp.R;


public class MainActivity extends  FragmentActivity implements OnClickListener {

    private int index;
    private int currentIndex;

    private Fragment[] fragments;

    //public static Fragment1 frag1;
    public static Fragment4 frag4;

    private ImageView[] imageViews;
    private TextView[] textViews;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        //frag1 = new Fragment1();
        frag4 = new Fragment4();
        //Fragment2 frag2 = new Fragment2();
        Fragment3 frag3 = new Fragment3();

        fragments = new Fragment[]{frag4, frag3};   // 要把Fragment*.java中import android.support.v4.app.Fragment;才行。否则不能添加进来。


        imageViews = new ImageView[2];
        imageViews[0] = (ImageView) findViewById(R.id.iv_zhuye);
        imageViews[1] = (ImageView) findViewById(R.id.iv_wode);
        imageViews[0].setSelected(true);

        textViews = new TextView[2];
        textViews[0] = (TextView) findViewById(R.id.tv_zhuye);
        textViews[1] = (TextView) findViewById(R.id.tv_wode);
        textViews[0].setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.selected));

        getSupportFragmentManager().beginTransaction()
                .add(R.id.LL_content_main, frag4)
                .add(R.id.LL_content_main, frag3)
                .hide(frag3)
                .show(frag4)
                .commit();

        LinearLayout ll_zhuye = (LinearLayout) findViewById(R.id.LL_zhuye);
        LinearLayout ll_wode = (LinearLayout) findViewById(R.id.LL_wode);

        ll_zhuye.setOnClickListener(this);
        ll_wode.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.LL_zhuye:
                index = 0;
                //Fragment4.init();

                break;

            case R.id.LL_wode:
                index = 1;
                break;
        }
        if (currentIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentIndex]);

            if (!fragments[index].isAdded()) {
                trx.add(R.id.LL_content_main, fragments[index]);
            }
            trx.show(fragments[index]).commit();
        }
        imageViews[currentIndex].setSelected(false);
        imageViews[index].setSelected(true);

        textViews[currentIndex].setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.unselected));
        textViews[index].setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.selected));

        currentIndex = index;
    }
}