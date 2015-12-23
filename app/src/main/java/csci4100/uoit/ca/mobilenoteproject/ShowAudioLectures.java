package csci4100.uoit.ca.mobilenoteproject;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ShowAudioLectures extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    private boolean playingStatus = false;
    ArrayList<MediaPlayer> listOfPlayers = new ArrayList<MediaPlayer>();

    public void listRaw(ArrayList<AudioLecture> lectureList){
        Field[] fields=R.raw.class.getFields();
        for(int count=0; count < fields.length; count++){
            Log.i("Raw Asset: ", fields[count].getName());
            try{
                int resourceID=fields[count].getInt(fields[count]);
                AudioLecture lect = new AudioLecture(fields[count].getName());
                lectureList.add(lect);
                MediaPlayer newPlayer = MediaPlayer.create(getBaseContext(), fields[count].getInt(fields[count]));
                listOfPlayers.add(newPlayer);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_lectures);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        ArrayList<AudioLecture> lectures = new ArrayList<>();
        listRaw(lectures);

        AudioLectureAdaptor adaptor = new AudioLectureAdaptor(this, lectures);
        ListView lecturesList = (ListView) findViewById(R.id.lecturesListView);
        lecturesList.setAdapter(adaptor);

        mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.frenchlesson1);


        lecturesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                    mediaPlayer = listOfPlayers.get(position);
                    mediaPlayer.start();

            }
        });

    }
    public void stopPlayer (View view) {
        mediaPlayer.stop();
    }
}
