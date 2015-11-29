package csci4100.uoit.ca.mobilenoteproject;

/**
 * Created by mkcy on 11/29/2015.
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
public class Downloader {

    public static void DownloadFile(String fileURL, File directory) {

        final String file = fileURL;
        final File dir = directory;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    FileOutputStream f = new FileOutputStream(dir);
                    URL u = new URL(file);
                    HttpURLConnection c = (HttpURLConnection) u.openConnection();
                    c.setRequestMethod("GET");
                    c.setDoOutput(true);
                    c.connect();

                    InputStream in = c.getInputStream();

                    byte[] buffer = new byte[1024];
                    int len1 = 0;
                    while ((len1 = in.read(buffer)) > 0) {
                        f.write(buffer, 0, len1);
                    }
                    f.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
}