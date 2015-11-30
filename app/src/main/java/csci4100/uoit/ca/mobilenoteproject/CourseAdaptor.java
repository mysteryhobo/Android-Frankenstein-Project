package csci4100.uoit.ca.mobilenoteproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Peter on 30/11/15.
 */
public class CourseAdaptor extends BaseAdapter{
    private Context context;
    private ArrayList<Course> data;

    public CourseAdaptor(Context context, ArrayList<Course> data) {
        this.data = data;
        this.context = context;
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return data.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Course courseToDisplay = data.get(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_class, parent, false);
        }
        //TextView courseTextName = (TextView) convertView.findViewById(R.id.lbl_courseName);
        //courseTextName.setText(courseToDisplay.getName());
        return convertView;
    }

}
