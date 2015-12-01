package csci4100.uoit.ca.mobilenoteproject;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.app.ListActivity;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Milan
 */
public class ShowClasses extends ListActivity {

    private static final int SHOW_NOTES_REQUEST_CODE = 1;
    private static int RESULT_LOAD_IMAGE = 2;

    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> classList;
    ListView list;

    // url to get all notes list
    private static String url_all_classes = "http://lifenote.ca/mobile/database/get_all_classes.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_CLASS = "class";
    private static final String TAG_CLASS_PK = "class_pk";
    private static final String TAG_TITLE = "class_name";

    // products JSONArray
    JSONArray classes = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_all_classes);



       classList = new ArrayList<HashMap<String, String>>();
        new LoadAllClasses().execute();
        list = getListView();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String class_pk = ((TextView) view.findViewById(R.id.class_pk)).getText()
                        .toString();

                // Starting new intent
                Intent in = new Intent(getApplicationContext(),
                        ShowNotes.class);
                String student = getIntent().getStringExtra("StudentID");
                // sending note_pk to next activity
                in.putExtra(TAG_CLASS_PK, class_pk);
                in.putExtra("StudentID", student);

                // starting new activity and expecting some response back
                startActivity(in);
            }
        }); 
    }

//    public void accessGallery(View view){
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"),2);
//    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SHOW_NOTES_REQUEST_CODE) {
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
//            if (requestCode == RESULT_LOAD_IMAGE) {
//                if (resultCode == Activity.RESULT_OK){
//                    Uri uri = Uri.parse(data.getDataString());
//                    File f = new File(getRealPathFromURI(uri));
//                    Drawable d = Drawable.createFromPath(f.getAbsolutePath());
//                    RelativeLayout layout = (RelativeLayout) findViewById(R.id.layout_classes);
//                    layout.setBackground(d);
//                }
//            }
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    public void viewContent(View view) {
        Intent viewContentIntent = new Intent(this, AttachPopup.class);
        startActivity(viewContentIntent);
    }



    class LoadAllClasses extends AsyncTask<String, String, String> {


        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            String student = getIntent().getStringExtra("StudentID");
            params.add(new BasicNameValuePair("studentID", student));
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_classes, "GET", params);

            // Check your log cat for JSON reponse
            Log.d("All Classes: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Notes
                    classes = json.getJSONArray(TAG_CLASS);
                    for (int i = 0; i < classes.length(); i++) {
                        JSONObject c = classes.getJSONObject(i);

                        // Storing each json item in variable
                        String id = c.getString(TAG_CLASS_PK);
                        String name = c.getString(TAG_TITLE);

                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put(TAG_CLASS_PK, id);
                        map.put(TAG_TITLE, name);

                        // adding HashList to ArrayList
                        classList.add(map);
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
                            ShowClasses.this, classList,
                            R.layout.list_class, new String[]{TAG_CLASS_PK,
                            TAG_TITLE},
                            new int[]{R.id.class_pk, R.id.title});
                    // updating listview
                    setListAdapter(adapter);
                }
            });
        }
    }



}
