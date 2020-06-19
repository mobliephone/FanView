package com.sh.fanview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 简单扇形图
 * @Author: cgw
 * @CreateDate: 2019/8/13 10:12
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FanChartView fcView = findViewById(R.id.fcView);
        HollowCircleView hcView = findViewById(R.id.hcView);
        HollowCircleLineView hclView = findViewById(R.id.hclView);

        List<FanBean> fanBeans = new ArrayList<>();
        fanBeans.add(new FanBean("生活日用",0.15f,150f));
        fanBeans.add(new FanBean("交通出行",0.10f,100f));
        fanBeans.add(new FanBean("服饰美容",0.15f,150f));
        fanBeans.add(new FanBean("医疗保健",0.025f,25f));
        fanBeans.add(new FanBean("通讯物流",0.025f,25f));
        fanBeans.add(new FanBean("餐饮美食",0.50f,500f));
        fanBeans.add(new FanBean("其他",0.05f,50f));

        fcView.setData(fanBeans);
        hcView.setData(fanBeans);
        hclView.setData(fanBeans);
    }
}
