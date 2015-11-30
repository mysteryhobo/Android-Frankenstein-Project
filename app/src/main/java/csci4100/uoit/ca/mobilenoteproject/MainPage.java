package csci4100.uoit.ca.mobilenoteproject;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class MainPage extends AppCompatActivity {

    private static final int CREATE_NEW_TEXT_NOTE_REQUEST_CODE = 1;
    ListView noteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        noteList = (ListView) findViewById(R.id.noteListView);

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
            Intent intent = new Intent(this,AttachPopup.class);
            startActivity(intent);
        }
        if (id == R.id.start) {
            Intent intent = new Intent(this,LoginScreen.class);
            startActivity(intent);
        }
        if (id == R.id.audioLectures) {
            Intent intent = new Intent(this,ShowAudioLectures.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CREATE_NEW_TEXT_NOTE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
//                TextView newNoteText = (TextView) findViewById(R.id.textView_mainPage);

//                Uri imageUri = data.getParcelableExtra("imageUri");
//                ImageView noteImg = (ImageView) findViewById(R.id.img_testNoteImage_mainPage);
//                noteImg.setImageURI(imageUri);
//                noteImg.setImageBitmap((Bitmap) data.getParcelableExtra("BitmapImage"));
            }
        }
    }

    public void createNewTextNote(View btn) {
        Intent createNewTextNoteIntent = new Intent(this, CreateNewTextNote.class);
        startActivityForResult(createNewTextNoteIntent, CREATE_NEW_TEXT_NOTE_REQUEST_CODE);
    }
}
