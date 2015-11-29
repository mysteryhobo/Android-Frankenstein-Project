package csci4100.uoit.ca.mobilenoteproject;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AttachPopup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attach_popup);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_attach_popup, menu);
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

        return super.onOptionsItemSelected(item);
    }

    public void downloadPdf(View v) {
        downloadPdf();
    }

    private void downloadPdf() {

        String extStorageDirectory = Environment.getExternalStorageDirectory()
                .toString();
        File folder = new File(extStorageDirectory, "pdf");
        folder.mkdir();
        File file = new File(folder, "Read.pdf");
        try {
            file.createNewFile();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Downloader.DownloadFile("https://www.durhamregiontransit.com/Schedules/Documents/0314_Oshawa/0314_401_Simcoe_North_WKDY_S3_M4.pdf", file);

        showPdf();
    }
    public void showPdf()
    {
        File file = new File(Environment.getExternalStorageDirectory()+"/pdf/Read.pdf");
        PackageManager packageManager = getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/pdf");
        startActivity(intent);
    }

}
