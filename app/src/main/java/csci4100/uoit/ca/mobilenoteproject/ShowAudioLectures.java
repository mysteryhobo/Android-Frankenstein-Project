package csci4100.uoit.ca.mobilenoteproject;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ShowAudioLectures extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_lectures);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayList<AudioLecture> lectures = new ArrayList<>();
        AudioLecture lecture1 = new AudioLecture("frenchlecture1");
        lectures.add(lecture1);

        AudioLecture lecture2 = new AudioLecture("frenchlecture2");
        lectures.add(lecture2);

        AudioLecture lecture3 = new AudioLecture("frenchlecture3");
        lectures.add(lecture3);

        AudioLectureAdaptor adaptor = new AudioLectureAdaptor(this, lectures);
        ListView lecturesList = (ListView) findViewById(R.id.lecturesListView);
        lecturesList.setAdapter(adaptor);
    }
}
