package csci4100.uoit.ca.mobilenoteproject;

import android.content.Context;
import android.media.AudioDeviceInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Peter on 29/11/15.
 */
public class AudioLectureAdaptor extends BaseAdapter {
    private Context context;
    private ArrayList<AudioLecture> data;

    public AudioLectureAdaptor(Context context, ArrayList<AudioLecture> data) {
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
        AudioLecture lecToDisplay = data.get(position);
        Log.d("DoctorAdapter", "Doctor:");
        Log.d("DoctorAdapter", "  Name:   "+lecToDisplay.getName());
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_view_audio_item, parent, false);
        }
        TextView audioTextName = (TextView) convertView.findViewById(R.id.lectureName);
        audioTextName.setText(lecToDisplay.getName());
        return convertView;
    }
}

