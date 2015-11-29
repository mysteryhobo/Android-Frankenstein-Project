package csci4100.uoit.ca.mobilenoteproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Peter Little on 08/11/15.
 */
public class NoteDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_FILENAME = "notes.db";
    public static final String TABLE_NAME = "Notes";

    // don't forget to use the column name '_id' for your primary key
    public static final String CREATE_STATEMENT = "CREATE TABLE " + TABLE_NAME + "(" +
            "  _id integer primary key autoincrement, " +
            "  name text not null," +
            "  description text not null," +
            "  date text not null" +
            ")";
    public static final String DROP_STATEMENT = "DROP TABLE " + TABLE_NAME;

    public NoteDBHelper(Context context) {
        super(context, DATABASE_FILENAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        // the implementation below is adequate for the first version
        // however, if we change our table at all, we'd need to execute code to move the data
        // to the new table structure, then delete the old tables (renaming the new ones)

        // the current version destroys all existing data
        database.execSQL(DROP_STATEMENT);
        database.execSQL(CREATE_STATEMENT);
    }

    public Note createNote(String name, String description, String date/*, Uri media*/) {
        // create the object
        Note note = new Note(name, description, date/*, media*/);

        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        // insert the data into the database
        ContentValues values = new ContentValues();
        values.put("name", note.getName());
        values.put("description", note.getDescription());
        values.put("date", note.getDate().toString());
//        values.put("media", note.getMedia());
        long id = database.insert(TABLE_NAME, null, values);

        // assign the Id of the new database row as the Id of the object
        note.setId(id);

        return note;
    }

    public Note getNote(long id) {
        Note note = null;

        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        // retrieve the note from the database
        String[] columns = new String[] { "name", "description", "date"/*, "media"*/};
        Cursor cursor = database.query(TABLE_NAME, columns, "_id = ?", new String[] { "" + id }, "", "", "");
        if (cursor.getCount() >= 1) {
            cursor.moveToFirst();
            String name = cursor.getString(0);
            String description = cursor.getString(1);
            String date = cursor.getString(2);
            /*Uri media = cursor.getMedia(3);*/
            note = new Note(name, description, date/*, media*/);
            note.setId(id);
        }

        Log.i("DatabaseAccess", "getNote(" + id + "):  note: " + note);

        return note;
    }

    public ArrayList<Note> getAllNotes() {
        ArrayList<Note> notes = new ArrayList<Note>();

        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        // retrieve the note from the database
        String[] columns = new String[] { "_id", "firstName", "lastName", "date"/*, "media"*/};
        Cursor cursor = database.query(TABLE_NAME, columns, "", new String[]{}, "", "", "");
        cursor.moveToFirst();
        do {
            // collect the note data, and place it into a note object
            long id = Long.parseLong(cursor.getString(0));
            String name = cursor.getString(1);
            String description = cursor.getString(2);
            String date = cursor.getString(3);
            Note note = new Note(name, description, date);
            note.setId(id);

            // add the current note to the list
            notes.add(note);

            // advance to the next row in the results
            cursor.moveToNext();
        } while (!cursor.isAfterLast());

        Log.i("DatabaseAccess", "getAllNotes():  num: " + notes.size());

        return notes;
    }
    public boolean updateNote(Note note) {
        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        // update the data in the database
        ContentValues values = new ContentValues();
        values.put("name", note.getName());
        values.put("description", note.getDescription());
        values.put("date", note.getDate());
        int numRowsAffected = database.update(TABLE_NAME, values, "_id = ?", new String[] { "" + note.getId() });

        Log.i("DatabaseAccess", "updateNote(" + note + "):  numRowsAffected: " + numRowsAffected);

        // verify that the note was updated successfully
        return (numRowsAffected == 1);
    }

    public boolean deleteNote(long id) {
        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        // delete the note
        int numRowsAffected = database.delete(TABLE_NAME, "_id = ?", new String[] { "" + id });

        Log.i("DatabaseAccess", "deleteNote(" + id + "):  numRowsAffected: " + numRowsAffected);

        // verify that the note was deleted successfully
        return (numRowsAffected == 1);
    }

    public void deleteAllNotes() {
        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        // delete the note
        int numRowsAffected = database.delete(TABLE_NAME, "", new String[] {});

        Log.i("DatabaseAccess", "deleteAllNotes():  numRowsAffected: " + numRowsAffected);
    }
}