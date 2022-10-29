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

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_first_time);

        prefs = super.getSharedPreferences("tech.sherrao.wlu.localify", Context.MODE_PRIVATE);
        checkIfFirstTime();
        updateComponents();
    }

    private void checkIfFirstTime() {
        String originLang = prefs.getString("tech.sherrao.wlu.localify.origin", null);
        if(originLang != null)
            super.startActivity(new Intent(FirstTimeActivity.this, MapsActivity.class));
    }

    private void updateComponents() {
        Spinner spinner = super.findViewById(R.id.firstTimeDropdown);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, new String[] {"EN", "US"});
        spinner.setAdapter(adapter);

        Button button = super.findViewById(R.id.firstTimeButton);
        button.setOnClickListener((view) -> {
            String origin = adapter.getItem(spinner.getSelectedItemPosition());
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("tech.sherrao.wlu.localify.origin", origin);
            editor.apply();

            startActivity(new Intent(FirstTimeActivity.this, MapsActivity.class));
        });
    }
}