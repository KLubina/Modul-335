package com.example.m335.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.m335.model.Module;

import java.util.List;

/**
 * Repository class that abstracts access to the module data sources
 */
public class ModuleRepository {
    private ModuleDao moduleDao;
    private LiveData<List<Module>> allModules;

    /**
     * Constructor initializes the database and data access components
     *
     * @param application The application context
     */
    public ModuleRepository(Application application) {
        ModuleDatabase database = ModuleDatabase.getDatabase(application);
        moduleDao = database.moduleDao();
        allModules = moduleDao.getAllModules();
    }

    /**
     * Gets all modules from the database
     *
     * @return LiveData containing a list of all modules
     */
    public LiveData<List<Module>> getAllModules() {
        return allModules;
    }

    /**
     * Gets a module by its module number
     *
     * @param moduleNumber The module number to search for
     * @return LiveData containing the module with the specified number
     */
    public LiveData<Module> getModuleByNumber(String moduleNumber) {
        return moduleDao.getModuleByNumber(moduleNumber);
    }

    /**
     * Inserts a module into the database asynchronously
     *
     * @param module The module to insert
     */
    public void insert(Module module) {
        new InsertModuleAsyncTask(moduleDao).execute(module);
    }

    /**
     * Updates a module in the database asynchronously
     *
     * @param module The module to update
     */
    public void update(Module module) {
        new UpdateModuleAsyncTask(moduleDao).execute(module);
    }

    /**
     * Deletes a module from the database asynchronously
     *
     * @param module The module to delete
     */
    public void delete(Module module) {
        new DeleteModuleAsyncTask(moduleDao).execute(module);
    }

    /**
     * AsyncTask to insert a module on a background thread
     */
    private static class InsertModuleAsyncTask extends AsyncTask<Module, Void, Void> {
        private ModuleDao asyncTaskDao;

        InsertModuleAsyncTask(ModuleDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Module... modules) {
            asyncTaskDao.insert(modules[0]);
            return null;
        }
    }

    /**
     * AsyncTask to update a module on a background thread
     */
    private static class UpdateModuleAsyncTask extends AsyncTask<Module, Void, Void> {
        private ModuleDao asyncTaskDao;

        UpdateModuleAsyncTask(ModuleDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Module... modules) {
            asyncTaskDao.update(modules[0]);
            return null;
        }
    }

    /**
     * AsyncTask to delete a module on a background thread
     */
    private static class DeleteModuleAsyncTask extends AsyncTask<Module, Void, Void> {
        private ModuleDao asyncTaskDao;

        DeleteModuleAsyncTask(ModuleDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Module... modules) {
            asyncTaskDao.delete(modules[0]);
            return null;
        }
    }
}