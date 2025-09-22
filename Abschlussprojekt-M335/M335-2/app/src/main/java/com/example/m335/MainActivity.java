package com.example.m335;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.m335.adapter.ModuleAdapter;
import com.example.m335.model.Module;
import com.example.m335.viewmodel.ModuleViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * Main activity displaying the list of modules.
 * Serves as the entry point of the application.
 */
public class MainActivity extends AppCompatActivity {
    public static final int ADD_MODULE_REQUEST = 1;
    public static final int EDIT_MODULE_REQUEST = 2;

    private ModuleViewModel moduleViewModel;

    /**
     * Initializes the activity, sets up the RecyclerView and ViewModel
     *
     * @param savedInstanceState Saved state bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        // Set up adapter
        final ModuleAdapter adapter = new ModuleAdapter();
        recyclerView.setAdapter(adapter);

        // Set up ViewModel
        moduleViewModel = new ViewModelProvider(this).get(ModuleViewModel.class);
        moduleViewModel.getAllModules().observe(this, new Observer<List<Module>>() {
            @Override
            public void onChanged(List<Module> modules) {
                // Update RecyclerView
                adapter.setModules(modules);
            }
        });

        // Set up FAB to add new modules
        FloatingActionButton fabAddModule = findViewById(R.id.fab_add_module);
        fabAddModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ModuleEditActivity.class);
                startActivityForResult(intent, ADD_MODULE_REQUEST);
            }
        });

        // Set up click listener for editing modules
        adapter.setOnItemClickListener(new ModuleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Module module) {
                Intent intent = new Intent(MainActivity.this, ModuleEditActivity.class);
                intent.putExtra(ModuleEditActivity.EXTRA_MODULE_NUMBER, module.getModuleNumber());
                intent.putExtra(ModuleEditActivity.EXTRA_MODULE_TITLE, module.getModuleTitle());

                if (module.getZpNote() != null) {
                    intent.putExtra(ModuleEditActivity.EXTRA_ZP_NOTE, module.getZpNote().toString());
                }

                if (module.getLbNote() != null) {
                    intent.putExtra(ModuleEditActivity.EXTRA_LB_NOTE, module.getLbNote().toString());
                }

                startActivityForResult(intent, EDIT_MODULE_REQUEST);
            }
        });
    }

    /**
     * Handles the result from the ModuleEditActivity
     *
     * @param requestCode The request code
     * @param resultCode The result code
     * @param data The intent containing the result data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            String moduleNumber = data.getStringExtra(ModuleEditActivity.EXTRA_MODULE_NUMBER);
            String moduleTitle = data.getStringExtra(ModuleEditActivity.EXTRA_MODULE_TITLE);
            String zpNoteStr = data.getStringExtra(ModuleEditActivity.EXTRA_ZP_NOTE);
            String lbNoteStr = data.getStringExtra(ModuleEditActivity.EXTRA_LB_NOTE);

            // Create module object
            Module module = new Module(moduleNumber, moduleTitle);

            // Set optional grades if provided
            if (zpNoteStr != null && !zpNoteStr.isEmpty()) {
                module.setZpNote(Float.parseFloat(zpNoteStr));
            }

            if (lbNoteStr != null && !lbNoteStr.isEmpty()) {
                module.setLbNote(Float.parseFloat(lbNoteStr));
            }

            if (requestCode == ADD_MODULE_REQUEST) {
                moduleViewModel.insert(module);
            } else if (requestCode == EDIT_MODULE_REQUEST) {
                moduleViewModel.update(module);
            }
        }
    }
}