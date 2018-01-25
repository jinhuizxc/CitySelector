package com.example.jinhui.cityselector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.jinhui.cityselector.effcet1.SelectorActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 看到一个不错的城市选择器，学习下！
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bt_selector1)
    Button btSelector1;
    @BindView(R.id.bt_selector2)
    Button btSelector2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_selector1, R.id.bt_selector2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_selector1:
                // 参考自：https://github.com/LiuXi0314/LetterTipSelector
                startActivity(new Intent(this, SelectorActivity.class));
                break;
            case R.id.bt_selector2:
                // https://github.com/yuruizhe/CityPicker 代码拿过来。自定义数据库有些问题，以后有时间在处理
//                startActivity(new Intent(this, Selector2Activity.class));
                Toast.makeText(this, "待处理！", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
