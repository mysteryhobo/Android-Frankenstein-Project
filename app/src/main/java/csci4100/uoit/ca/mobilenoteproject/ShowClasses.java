package csci4100.uoit.ca.mobilenoteproject;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class ShowClasses extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_classes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ArrayList<Course> courses = new ArrayList<>();
        Course course1 = new Course("HardCoded: Mobile Devices");
        courses.add(course1);

        Course course2 = new Course("HardCoded: Capstone");
        courses.add(course2);

        Course course3 = new Course("HardCoded: Distributed Systems");
        courses.add(course3);

        Course course4 = new Course("HardCoded: Computer Networks");
        courses.add(course3);

        Course course5 = new Course("HardCoded: User Interfaces");
        courses.add(course3);

        CourseAdaptor adaptor = new CourseAdaptor(this, courses);
        ListView courseList = (ListView) findViewById(R.id.list_courses);
        courseList.setAdapter(adaptor);
    }

}
