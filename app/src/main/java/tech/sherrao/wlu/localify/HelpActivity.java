package tech.sherrao.wlu.localify;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {

    private Button appInfoButton;
    private Button instructionsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_help);
        appInfoButton = super.findViewById(R.id.appInfoButton);

        appInfoButton.setOnClickListener(view -> displayAppInfo());

        instructionsButton = super.findViewById(R.id.instructionsButton);
        instructionsButton.setOnClickListener(v -> displayInstructions());


    }

    protected void displayAppInfo() {
        Dialog dialog = new Dialog(HelpActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.app_info_toolbar);

        Button dialogButton = dialog.findViewById(R.id.quitAppInfoButton);
        dialogButton.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    protected void displayInstructions() {
        Dialog dialog = new Dialog(HelpActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.instructions_toolbar);

        Button dialogButton = dialog.findViewById(R.id.quitInstructionsButton);

        dialogButton.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }
}