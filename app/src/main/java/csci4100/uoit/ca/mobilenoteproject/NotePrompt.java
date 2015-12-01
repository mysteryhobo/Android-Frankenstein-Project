package csci4100.uoit.ca.mobilenoteproject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mkcy on 11/29/2015.
 */
public class NotePrompt extends AppCompatActivity {

    TextView txtTitle;
    TextView txtDesc;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    // single product url
    private static final String url_view_note_desc = "http://lifenote.ca/mobile/database/view_note.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_NOTE = "note";
    private static final String TAG_NOTE_PK = "note_pk";
    private static final String TAG_TITLE = "title";
    private static final String TAG_TEXT = "text";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_note);

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
                    txtTitle = (TextView) findViewById(R.id.note_title);
                    txtDesc = (TextView) findViewById(R.id.note_desc);

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
}

