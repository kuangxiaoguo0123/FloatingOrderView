package com.sprite.floatingorderview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sprite.floatingorderview.view.ATFloatingOrderView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    private void init() {
        ATFloatingOrderView sellOrderView = (ATFloatingOrderView) findViewById(R.id.sell_order_view);
        ATFloatingOrderView riderOrderView = (ATFloatingOrderView) findViewById(R.id.rider_order_view);

        sellOrderView.setCenterText(getString(R.string.sell_title),
                getString(R.string.sell_description), getString(R.string.sell_detail));

        riderOrderView.setCenterText(getString(R.string.rider_title),
                getString(R.string.rider_description), getString(R.string.rider_detail));
    }
}
