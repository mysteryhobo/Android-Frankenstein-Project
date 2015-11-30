package csci4100.uoit.ca.mobilenoteproject;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainPage extends ListActivity {

    private static final int CREATE_NEW_TEXT_NOTE_REQUEST_CODE = 1;
    private static final int NOTE_PROMPT_REQUEST_CODE = 2;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> noteList;
    ListView list;

    // url to get all notes list
    private static String url_all_notes = "http://lifenote.ca/mobile/database/get_all_notes.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_NOTE = "note";
    private static final String TAG_NOTE_PK = "note_pk";
    private static final String TAG_TITLE = "title";

    // products JSONArray
    JSONArray notes = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_all_notes);

        noteList = new ArrayList<HashMap<String, String>>();

        new LoadAllNotes().execute();
        list = getListView();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String note_pk = ((TextView) view.findViewById(R.id.note_pk)).getText()
                        .toString();

                // Starting new intent
                Intent in = new Intent(getApplicationContext(),
                        NotePrompt.class);
                // sending note_pk to next activity
                in.putExtra(TAG_NOTE_PK, note_pk);

                // starting new activity and expecting some response back
                startActivityForResult(in, 100);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.pop) {
            Intent intent = new Intent(this, AttachPopup.class);
            startActivity(intent);
        }
        if (id == R.id.start) {
            Intent intent = new Intent(this, LoginScreen.class);
            startActivity(intent);
        }
        if (id == R.id.audioLectures) {
            Intent intent = new Intent(this, ShowAudioLectures.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CREATE_NEW_TEXT_NOTE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {


                Intent intent = getIntent();
                finish();
                startActivity(intent);


//                TextView newNoteText = (TextView) findViewById(R.id.textView_mainPage);

//                Uri imageUri = data.getParcelableExtra("imageUri");
//                ImageView noteImg = (ImageView) findViewById(R.id.img_testNoteImage_mainPage);
//                noteImg.setImageURI(imageUri);
//                noteImg.setImageBitmap((Bitmap) data.getParcelableExtra("BitmapImage"));
            }
        }
        if (requestCode == NOTE_PROMPT_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK){

            }
        }
    }

    public void createNewTextNote(View btn) {
        Intent createNewTextNoteIntent = new Intent(this, CreateNewTextNote.class);

        String student = getIntent().getStringExtra("StudentID");
        createNewTextNoteIntent.putExtra("StudentID", student);
        startActivityForResult(createNewTextNoteIntent, CREATE_NEW_TEXT_NOTE_REQUEST_CODE);
    }


    public void showCourses(View view) {
        Intent showNotesIntent = new Intent(this, ShowClasses.class);
        startActivity(showNotesIntent);
    }

    public void showAudioLectures(View view) {
        Intent showNotesIntent = new Intent(this, ShowAudioLectures.class);
        startActivity(showNotesIntent);
    }


    class LoadAllNotes extends AsyncTask<String, String, String> {


        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            String student = getIntent().getStringExtra("StudentID");
            params.add(new BasicNameValuePair("studentID", student));
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_notes, "GET", params);

            // Check your log cat for JSON reponse
            Log.d("All Products: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Notes
                    notes = json.getJSONArray(TAG_NOTE);
                    for (int i = 0; i < notes.length(); i++) {
                        JSONObject c = notes.getJSONObject(i);

                        // Storing each json item in variable
                        String id = c.getString(TAG_NOTE_PK);
                        String name = c.getString(TAG_TITLE);

                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put(TAG_NOTE_PK, id);
                        map.put(TAG_TITLE, name);

                        // adding HashList to ArrayList
                        noteList.add(map);
                    }
                } else {


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;


        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products

            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                            MainPage.this, noteList,
                            R.layout.list_note, new String[]{TAG_NOTE_PK,
                            TAG_TITLE},
                            new int[]{R.id.note_pk, R.id.title});
                    // updating listview
                   setListAdapter(adapter);
                }
            });
        }
    }
}
