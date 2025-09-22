package com.example.m335;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.m335.viewmodel.ModuleViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/**
 * Activity for adding or editing a module.
 * Handles user input validation and saving to the database.
 */
public class ModuleEditActivity extends AppCompatActivity {
    public static final String EXTRA_MODULE_NUMBER = "com.example.m335.EXTRA_MODULE_NUMBER";
    public static final String EXTRA_MODULE_TITLE = "com.example.m335.EXTRA_MODULE_TITLE";
    public static final String EXTRA_ZP_NOTE = "com.example.m335.EXTRA_ZP_NOTE";
    public static final String EXTRA_LB_NOTE = "com.example.m335.EXTRA_LB_NOTE";

    private TextInputLayout textInputLayoutModuleNumber;
    private TextInputLayout textInputLayoutModuleTitle;
    private TextInputLayout textInputLayoutZpNote;
    private TextInputLayout textInputLayoutLbNote;

    private TextInputEditText editTextModuleNumber;
    private TextInputEditText editTextModuleTitle;
    private TextInputEditText editTextZpNote;
    private TextInputEditText editTextLbNote;

    private ModuleViewModel moduleViewModel;
    private boolean editMode = false;

    /**
     * Initializes the activity, sets up the form fields and handles editing mode
     *
     * @param savedInstanceState Saved state bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_module);

        moduleViewModel = new ViewModelProvider(this).get(ModuleViewModel.class);

        // Initialize views
        textInputLayoutModuleNumber = findViewById(R.id.text_input_layout_module_number);
        textInputLayoutModuleTitle = findViewById(R.id.text_input_layout_module_title);
        textInputLayoutZpNote = findViewById(R.id.text_input_layout_zp_note);
        textInputLayoutLbNote = findViewById(R.id.text_input_layout_lb_note);

        editTextModuleNumber = findViewById(R.id.edit_text_module_number);
        editTextModuleTitle = findViewById(R.id.edit_text_module_title);
        editTextZpNote = findViewById(R.id.edit_text_zp_note);
        editTextLbNote = findViewById(R.id.edit_text_lb_note);

        Button buttonSave = findViewById(R.id.button_save);

        // Set up action bar
        getSupportActionBar().setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);

        // Check if we're in edit mode
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_MODULE_NUMBER)) {
            setTitle("Modul bearbeiten");
            editMode = true;

            // Fill form with existing module data
            editTextModuleNumber.setText(intent.getStringExtra(EXTRA_MODULE_NUMBER));
            editTextModuleTitle.setText(intent.getStringExtra(EXTRA_MODULE_TITLE));

            if (intent.hasExtra(EXTRA_ZP_NOTE)) {
                editTextZpNote.setText(intent.getStringExtra(EXTRA_ZP_NOTE));
            }

            if (intent.hasExtra(EXTRA_LB_NOTE)) {
                editTextLbNote.setText(intent.getStringExtra(EXTRA_LB_NOTE));
            }

            // In edit mode, module number shouldn't be changed
            editTextModuleNumber.setEnabled(false);
        } else {
            setTitle("Neues Modul");
        }

        // Set up save button
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveModule();
            }
        });
    }

    /**
     * Saves the module after validating input
     */
    private void saveModule() {
        // Get input values
        String moduleNumber = editTextModuleNumber.getText().toString().trim();
        String moduleTitle = editTextModuleTitle.getText().toString().trim();
        String zpNoteStr = editTextZpNote.getText().toString().trim();
        String lbNoteStr = editTextLbNote.getText().toString().trim();

        // Validate input
        String validationError = moduleViewModel.validateModule(
                moduleNumber, moduleTitle, zpNoteStr, lbNoteStr);

        if (validationError != null) {
            Toast.makeText(this, validationError, Toast.LENGTH_SHORT).show();

            // Set error on appropriate field
            if (validationError.contains("Modulnummer")) {
                textInputLayoutModuleNumber.setError(validationError);
            } else if (validationError.contains("Modultitel")) {
                textInputLayoutModuleTitle.setError(validationError);
            } else if (validationError.contains("ZP-Note")) {
                textInputLayoutZpNote.setError(validationError);
            } else if (validationError.contains("LB-Note")) {
                textInputLayoutLbNote.setError(validationError);
            }

            return;
        }

        // Clear any previous errors
        textInputLayoutModuleNumber.setError(null);
        textInputLayoutModuleTitle.setError(null);
        textInputLayoutZpNote.setError(null);
        textInputLayoutLbNote.setError(null);

        // Set result
        Intent data = new Intent();
        data.putExtra(EXTRA_MODULE_NUMBER, moduleNumber);
        data.putExtra(EXTRA_MODULE_TITLE, moduleTitle);

        if (!zpNoteStr.isEmpty()) {
            data.putExtra(EXTRA_ZP_NOTE, zpNoteStr);
        }

        if (!lbNoteStr.isEmpty()) {
            data.putExtra(EXTRA_LB_NOTE, lbNoteStr);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    /**
     * Handles action bar item clicks (back button)
     *
     * @param item The menu item that was clicked
     * @return true if the event was handled, false otherwise
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}