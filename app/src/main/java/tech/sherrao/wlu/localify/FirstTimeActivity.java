package tech.sherrao.wlu.localify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class FirstTimeActivity extends AppCompatActivity {

    private final String SHARED_PREFS_NAME = "tech.sherrao.wlu.localify";
    private final String SHARED_PREFS_ORIGINS_KEY = SHARED_PREFS_NAME + ".origin";
    private static final String[] ORIGINS = new String[] {"Middle-Eastern", "Indian", "Chinese"};
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_first_time);

        prefs = super.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        checkIfFirstTime();
        updateComponents();
    }

    private void checkIfFirstTime() {
        String originLang = prefs.getString(SHARED_PREFS_ORIGINS_KEY, null);
        if(originLang != null)
            super.startActivity(new Intent(FirstTimeActivity.this, MainActivity.class));
    }

    private void updateComponents() {
        Spinner spinner = super.findViewById(R.id.firstTimeDropdown);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, ORIGINS);
        spinner.setAdapter(adapter);

        Button button = super.findViewById(R.id.firstTimeButton);
        button.setOnClickListener((view) -> {
            String origin = adapter.getItem(spinner.getSelectedItemPosition());
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("tech.sherrao.wlu.localify.origin", origin);
            editor.apply();

            startActivity(new Intent(FirstTimeActivity.this, MainActivity.class));
        });
    }
}