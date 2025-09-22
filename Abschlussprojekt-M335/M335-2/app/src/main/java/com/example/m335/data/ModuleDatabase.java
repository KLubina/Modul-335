package com.example.m335.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.m335.model.Module;

/**
 * Room database for storing modules.
 * Implements Singleton pattern to provide a single database instance.
 */
@Database(entities = {Module.class}, version = 1, exportSchema = false)
public abstract class ModuleDatabase extends RoomDatabase {

    /**
     * Provides access to the Module DAO
     *
     * @return The ModuleDao instance
     */
    public abstract ModuleDao moduleDao();

    private static ModuleDatabase INSTANCE;

    /**
     * Gets the database instance, creating it if it doesn't exist
     *
     * @param context The application context
     * @return The ModuleDatabase instance
     */
    public static ModuleDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ModuleDatabase.class) {
                if (INSTANCE == null) {
                    // Create database
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    ModuleDatabase.class,
                                    "module_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}