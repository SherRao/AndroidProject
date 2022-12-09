package tech.sherrao.wlu.localify;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import tech.sherrao.wlu.localify.databinding.ActivityToolbarBinding;

public class ToolbarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityToolbarBinding binding = ActivityToolbarBinding.inflate(super.getLayoutInflater());
        super.setContentView(binding.getRoot());
        super.setSupportActionBar(binding.toolbar);

        binding.fab.setOnClickListener(view ->
                startActivity(new Intent(ToolbarActivity.this, FavouritesActivity.class))
        );
    }

    @Override
    public boolean onSupportNavigateUp() {
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    @SuppressLint("NonConstantResourceId")
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case R.id.toolbar_menu_action_one:
                Log.d("Toolbar", "Option 1 selected");
                super.startActivity(new Intent(ToolbarActivity.this, HelpActivity.class));
                break;

            case R.id.toolbar_menu_action_two:
                Log.d("Toolbar", "Option 2 selected");
                super.startActivity(new Intent(ToolbarActivity.this, SettingsActivity.class));
                break;

            default:
                Log.d("Toolbar", "Default selected");
                break;
        }

        return super.onOptionsItemSelected(menuItem);
    }
}