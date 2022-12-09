package tech.sherrao.wlu.localify;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import tech.sherrao.wlu.localify.databinding.ActivitySettingsBinding;

@SuppressLint("UseSwitchCompatOrMaterialCode")
public class SettingsActivity extends AppCompatActivity {

    private static final String[] ORIGINS = new String[] {"Russian", "Middle-Eastern", "Indian", "Chinese"};
    private final String SHARED_PREFS_NAME = "tech.sherrao.wlu.localify";
    private final String SHARED_PREFS_FIRSTNAME_KEY = SHARED_PREFS_NAME + ".firstName";
    private final String SHARED_PREFS_LASTNAME_KEY = SHARED_PREFS_NAME + ".lastName";
    private final String SHARED_PREFS_EMAIL_KEY = SHARED_PREFS_NAME + ".email";
    private final String SHARED_PREFS_PHONE_NUMBER_KEY = SHARED_PREFS_NAME + ".phoneNumber";
    private final String SHARED_PREFS_ORIGINS_KEY = SHARED_PREFS_NAME + ".origin";

    private SharedPreferences prefs;
    private ArrayAdapter<String> adapter;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText phoneNumber;
    private Switch makeChanges;
    private Spinner country;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySettingsBinding binding = ActivitySettingsBinding.inflate(super.getLayoutInflater());
        super.setContentView(binding.getRoot());

        adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, ORIGINS);
        firstName = super.findViewById(R.id.settingsFirstNameEditText);
        lastName = super.findViewById(R.id.settingsLastNameEditText);
        email = super.findViewById(R.id.settingsEmailEditText);
        phoneNumber = super.findViewById(R.id.settingsPhoneNumberEditText);
        makeChanges = super.findViewById(R.id.settingsChangesSwitch);
        country = super.findViewById(R.id.settingsCountryDropDown);
        country.setAdapter(adapter);
        save = super.findViewById(R.id.settingsSaveButton);
        prefs = super.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);

        setFields();
        updateComponents();
    }

    private void setFields() {
        firstName.setText(prefs.getString(SHARED_PREFS_FIRSTNAME_KEY, ""));
        lastName.setText(prefs.getString(SHARED_PREFS_LASTNAME_KEY, ""));
        email.setText(prefs.getString(SHARED_PREFS_EMAIL_KEY, ""));

        int number = prefs.getInt(SHARED_PREFS_PHONE_NUMBER_KEY, 0);
        phoneNumber.setText(number == 0 ? "" : Integer.toString(number));
        String origin = prefs.getString(SHARED_PREFS_ORIGINS_KEY, null);
        if(origin != null)
            country.setSelection(adapter.getPosition((origin)));
    }

    private void updateComponents() {
        //Disable until switch is activated
        firstName.setEnabled(false);
        lastName.setEnabled(false);
        email.setEnabled(false);
        phoneNumber.setEnabled(false);
        country.setEnabled(false);

        //Safety switch
        makeChanges.setOnCheckedChangeListener((compoundButton, b) -> {
            if(b) {
                firstName.setEnabled(true);
                lastName.setEnabled(true);
                email.setEnabled(true);
                phoneNumber.setEnabled(true);
                country.setEnabled(true);
            } else {
                firstName.setEnabled(false);
                lastName.setEnabled(false);
                email.setEnabled(false);
                phoneNumber.setEnabled(false);
                country.setEnabled(false);
            }
        });

        //Save button
        save.setOnClickListener(v-> saveInfo());
    }

    private void saveInfo() {
        SharedPreferences.Editor editPref = prefs.edit();
        editPref.putString(SHARED_PREFS_FIRSTNAME_KEY, String.valueOf(firstName.getText()));
        editPref.putString(SHARED_PREFS_LASTNAME_KEY, String.valueOf(lastName.getText()));
        editPref.putString(SHARED_PREFS_EMAIL_KEY, String.valueOf(email.getText()));
        if(!String.valueOf(phoneNumber.getText()).equals(""))
            editPref.putInt(SHARED_PREFS_PHONE_NUMBER_KEY, Integer.parseInt(String.valueOf(phoneNumber.getText())));

        editPref.putString(SHARED_PREFS_ORIGINS_KEY, country.getSelectedItem().toString());
        editPref.apply();

        Toast.makeText(this, "Settings have been saved!", Toast.LENGTH_LONG).show();
    }
}