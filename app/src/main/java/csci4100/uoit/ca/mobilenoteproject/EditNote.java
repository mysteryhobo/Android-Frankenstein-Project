package csci4100.uoit.ca.mobilenoteproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EditNote extends AppCompatActivity {

    EditText txtTitle;
    EditText txtDesc;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    private static final String url_view_note_desc = "http://lifenote.ca/mobile/database/view_note.php";
    private static final String url_update_note = "http://lifenote.ca/mobile/database/delete_note.php";
    private static final String url_delete_note = "http://lifenote.ca/mobile/database/update_note.php";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_NOTE = "note";
    private static final String TAG_NOTE_PK = "note_pk";
    private static final String TAG_TITLE = "title";
    private static final String TAG_TEXT = "text";
    private static Intent returnEditNoteIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        new GetNote().execute();

    }




    class GetNote extends AsyncTask<String, String, String> {

        String title;
        String text;

        protected String doInBackground(String... args) {

            // updating UI from Background Thread

            // Check for success tag
            int success;
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                String note_pk = getIntent().getStringExtra("note_pk");
                params.add(new BasicNameValuePair("note_pk", note_pk));

                // getting product details by making HTTP request
                // Note that product details url will use GET request
                JSONObject json = jsonParser.makeHttpRequest(
                        url_view_note_desc, "GET", params);

                // check your log for json response
                Log.d("Single Note Details", json.toString());

                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    // successfully received note details
                    JSONArray noteObj = json
                            .getJSONArray(TAG_NOTE); // JSON Array

                    JSONObject note = noteObj.getJSONObject(0);
                    // get first note object from JSON Array


                    // product with this pid found
                    // Edit Text
                    txtTitle = (EditText) findViewById(R.id.EditText_enterTitle);
                    txtDesc = (EditText) findViewById(R.id.EditText_description);

                    title = note.getString(TAG_TITLE);
                    text = note.getString(TAG_TEXT);

                    // display product data in EditText


                } else {
                    // product with pid not found
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String file_url) {

            txtTitle.setText(title);
            txtDesc.setText(text);

        }
    }

    public void deleteNote(View v) {
        deleteNote();
    }

    private void deleteNote() {
        returnEditNoteIntent = new Intent();
        new DeleteNote().execute();
    }

    public void updateNote(View v){
        updateNote();
    }

    private void updateNote() {
        returnEditNoteIntent = new Intent();
        new UpdateNote().execute();
    }
    class DeleteNote extends AsyncTask<String, String, String> {
        int flag = 0;

        protected String doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            String note_pk = getIntent().getStringExtra("note_pk");
            params.add(new BasicNameValuePair("note_pk", note_pk));


            int success;
            try {
            // Building Parameters
            // getting product details by making HTTP request
            // Note that product details url will use GET request
            JSONObject json = jsonParser.makeHttpRequest(
                    url_delete_note, "POST", params);

            // check your log for json response
            Log.d("Note Delete", json.toString());

            // json success tag
            success = json.getInt(TAG_SUCCESS);
            if (success == 1) {

                setResult(RESULT_OK, returnEditNoteIntent);
                finish();


            } else {
                flag = 1;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }



        return null;
    }
    }

    class UpdateNote extends AsyncTask<String, String, String> {
        int flag = 0;

        protected String doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            String note_pk = getIntent().getStringExtra("note_pk");
            params.add(new BasicNameValuePair("note_pk", note_pk));


            int success;
            try {
                // Building Parameters
                // getting product details by making HTTP request
                // Note that product details url will use GET request
                JSONObject json = jsonParser.makeHttpRequest(
                        url_update_note, "POST", params);

                // check your log for json response
                Log.d("Note Update", json.toString());
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {

                    setResult(RESULT_OK, returnEditNoteIntent);
                    finish();


                } else {
                    flag = 1;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }



            return null;
        }

    }
}
