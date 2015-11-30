package csci4100.uoit.ca.mobilenoteproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
/**
 * Created by mkcy on 11/29/2015.
 */
public class LoginScreen extends AppCompatActivity {


    EditText studentID;
    EditText passwordEnter;


    private ProgressDialog loginDialog;

    // JSON class parser
    JSONParser jsonParser = new JSONParser();

    // url to login authentication
    private static final String url_login = "http://lifenote.ca/mobile/database/login.php";

    private static final String TAG_SUCCESS = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        studentID = (EditText) findViewById(R.id.editText_student_id);
        passwordEnter = (EditText) findViewById(R.id.editText_password);


    }

    public void loginAuthenticate(View v) {

        if (studentID != null && passwordEnter != null) {
            new LoginAuthenticate().execute();
        }
    }

    /* Background Async Task

     */
    class LoginAuthenticate extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loginDialog = new ProgressDialog(LoginScreen.this);
            loginDialog.setMessage("Authenticating Login, please wait...");
            loginDialog.setIndeterminate(false);
            loginDialog.setCancelable(true);
            loginDialog.show();
        }

        protected String doInBackground(String... args) {

            String student = studentID.getText().toString();
            String password = passwordEnter.getText().toString();

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("studentID", student));
            params.add(new BasicNameValuePair("password", password));
            // updating UI from Background Thread

            // Check for success tag
            int success;
            try {
                // Building Parameters
                // getting product details by making HTTP request
                // Note that product details url will use GET request
                JSONObject json = jsonParser.makeHttpRequest(
                        url_login, "GET", params);

                // check your log for json response
                Log.d("Student Authentication", json.toString());

                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {


                } else {
                    // product with pid not found
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }



            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once got all details
            loginDialog.dismiss();
        }
    }
}
