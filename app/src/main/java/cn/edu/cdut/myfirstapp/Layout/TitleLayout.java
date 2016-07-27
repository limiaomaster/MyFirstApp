package cn.edu.cdut.myfirstapp.Layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import cn.edu.cdut.myfirstapp.R;


/**
 * Created by Administrator on 2016/5/26 0026.
 */

public class TitleLayout extends LinearLayout {



    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode()) { return; }

        //LayoutInflater.from(context).inflate(R.layout.foot_main,this);
        LayoutInflater.from(context).inflate(R.layout.title_main,this);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick (View v){
                Toast.makeText(getContext(), "您点击了记录按钮，但是并未开始记录！", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
