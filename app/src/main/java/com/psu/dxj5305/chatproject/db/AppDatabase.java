package com.psu.dxj5305.chatproject.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;

@Database(entities = {Thought.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "thoughts")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract ThoughtDao thoughtDao();

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    Executors.newSingleThreadExecutor().execute(() -> {
                        populateDefaultThoughts(INSTANCE);
                    });
                }
            };

    private static void populateDefaultThoughts(AppDatabase db) {
        ThoughtDao dao = db.thoughtDao();

        // Get the default thoughts from the DefaultContent class
        String[] defaultThoughts = DefaultContent.thoughtlist;

        // Insert each default thought into the database
        for (String thought : defaultThoughts) {
            dao.insert(new Thought(thought));
        }
    }
}



