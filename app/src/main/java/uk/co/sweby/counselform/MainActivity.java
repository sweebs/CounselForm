package uk.co.sweby.counselform;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    public static final String FILE_CURRENT = "current.csv";
    public static final String FILE_HISTORY = "history.csv";

    public static String currentPointOfCounsel;

    public static String dateAssigned;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CounselHistoryItem item = readCurrentPointOfCounsel();
        if (item != null) {
            currentPointOfCounsel = item.getPointOfCounsel();
            dateAssigned = item.getDateSet();
            setFields();
        } /* else if (savedInstanceState != null) {
            dateAssigned = savedInstanceState.getString("dateAssigned");
            currentPointOfCounsel = savedInstanceState.getString("currentPointOfCounsel");
        } */
    }

    @Override
    protected void onResume() {
        super.onResume();
        setFields();
    }



    private void setFields() {
        TextView displayPoc = (TextView) findViewById(R.id.textViewCurrentPointOfCounsel);
        displayPoc.setText(currentPointOfCounsel);
        TextView displayDate = (TextView) findViewById(R.id.dateAssigned);
        displayDate.setText(dateAssigned);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
            case R.id.selectPointOfCounsel:
                launchSelectPointOfCounsel();
                return true;
            case R.id.viewHistory:
                launchViewHistory();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void launchSelectPointOfCounsel() {
        Intent intent = new Intent(this, SelectPointOfCounsel.class);
        startActivity(intent);
    }

    private void launchViewHistory() {
        Intent intent = new Intent(this, ViewHistory.class);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);
        return true;
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("currentPointOfCounsel", currentPointOfCounsel);
        outState.putString("dateAssigned", dateAssigned);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        dateAssigned = savedInstanceState.getString("dateAssigned");
        currentPointOfCounsel = savedInstanceState.getString("currentPointOfCounsel");
    }



    protected CounselHistoryItem readCurrentPointOfCounsel() {
        CounselHistoryItem item = null;
        try {
            FileInputStream fis = openFileInput(FILE_CURRENT);
            InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);
            String contentLine = null;
            String line;
            while ((line = br.readLine()) != null) {
                contentLine = line;
            }
            if (contentLine != null) {
                StringTokenizer stringTokenizer = new StringTokenizer(contentLine, "|");
                item = new CounselHistoryItem();
                item.setPointOfCounsel(stringTokenizer.nextToken());
                item.setDateSet(stringTokenizer.nextToken());

            }
            fis.close();
        } catch (IOException e) {
            return null;
        }
        return item;
    }
}
