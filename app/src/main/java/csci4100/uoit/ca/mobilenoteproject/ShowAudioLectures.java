package csci4100.uoit.ca.mobilenoteproject;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ShowAudioLectures extends AppCompatActivity {
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_lectures);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayList<AudioLecture> lectures = new ArrayList<>();
        AudioLecture lecture1 = new AudioLecture("French lecture 1");
        lectures.add(lecture1);

        AudioLecture lecture2 = new AudioLecture("French Lecture 2");
        lectures.add(lecture2);

        AudioLecture lecture3 = new AudioLecture("French Lecture 3");
        lectures.add(lecture3);

        AudioLectureAdaptor adaptor = new AudioLectureAdaptor(this, lectures);
        ListView lecturesList = (ListView) findViewById(R.id.lecturesListView);
        lecturesList.setAdapter(adaptor);

//        lecturesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                switch (position) {
//                    case 1: mediaPlayer = MediaPlayer.create(context, R.raw.sound_file_1);
//                }
//            }
//        });



    }
}
