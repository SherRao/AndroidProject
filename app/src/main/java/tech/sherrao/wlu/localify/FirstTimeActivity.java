package tech.sherrao.wlu.localify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class FirstTimeActivity extends AppCompatActivity {

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_first_time);

        prefs = super.getSharedPreferences("tech.sherrao.wlu.localify", Context.MODE_PRIVATE);
        String originLang = prefs.getString("tech.sherrao.wlu.localify.origin", null);
        if(originLang != null)
            super.startActivity(new Intent(FirstTimeActivity.this, MapsActivity.class));

        Spinner spinner = super.findViewById(R.id.firstTimeDropdown);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, new String[] {"EN", "US"});
        spinner.setAdapter(adapter);

    }
}