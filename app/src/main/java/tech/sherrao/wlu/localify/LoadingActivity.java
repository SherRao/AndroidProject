package tech.sherrao.wlu.localify;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class LoadingActivity extends Activity {

    private static final int LOADING_TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_loading);
        new Handler().postDelayed(() -> startActivity(new Intent(LoadingActivity.this, FirstTimeActivity.class)), LOADING_TIME);
    }
}