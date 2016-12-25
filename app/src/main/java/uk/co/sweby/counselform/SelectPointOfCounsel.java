package uk.co.sweby.counselform;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Paul on 24/12/2016.
 */

public class SelectPointOfCounsel  extends AppCompatActivity {

    private SelectPointOfCounsel self;

    private String[] pointsOfCounsel = {"Study  1: Accurate Reading", "Study 2: Words Clearly Spoken",
            "Study 3: Correct Pronunciation", "Study 4: Fluent Delivery", "Study 5 Appropriate Pausing",
            "Study 6 Proper Sense Stress", "Study 7 Principal Ideas Emphasized" ,"Study 8 Suitable Volume",
            "Study 9 Modulation", "Study 10 Enthusiasm", "Study 11 Warmth and Feeling",
            "Study 12 Gestures and Facial Expressions", "Study 13 Visual Contact", "Study 14 Naturalness",
            "Study 15 Good Personal Appearance", "Study 16 Poise", "Study 17 Use of Microphone",
            "Study 18 Use of Bible in Replying", "Study 19 Use of Bible Encouraged",
            "Study 20 Scriptures Effectively Introduced", "Study 21 Scriptures Read With Proper Emphasis",
            "Study 22 Scriptures Correctly Applied",  "Study 23 Practical Value Made Clear",
            "Study 24 Choice of Words", "Study 25 Use of an Outline", "Study 26 Logical Development of Material",
            "Study 27 Extemporaneous Delivery", "Study 28 Conversational Manner", "Study 29 Voice Quality",
            "Study 30 Interest Shown in the Other Person", "Study 31 Respect Shown to Others",
            "Study 32 Expressed With Conviction", "Study 33 Tactful yet Firm", "Study 34 Upbuilding and Positive",
            "Study 35 Repetition for Emphasis", "Study 36 Theme Developed", "Study 37 Main Points Made to Stand Out",
            "Study 38 Interest-Arousing Introduction", "Study 39 Effective Conclusion","Study 40 Accuracy of Statement",
            "Study 41 Understandable to Others", "Study 42 Informative to Your Audience", "Study 43 Use of Assigned Material",
            "Study 44 Effective Use of Questions",  "Study 45 Illustrations/Examples That Teach",
            "Study 46 Illustrations From Familiar Situations", "Study 47 Effective Use of Visual Aids",
            "Study 48 Reasoning Manner", "Study 49 Sound Arguments Given", "Study 50 Effort to Reach the Heart",
            "Study 51 Accurately Timed, Properly Proportioned", "Study 52 Effective Exhortation",
            "Study 53 Audience Encouraged and Strengthened"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_point_of_counsel);
        self = this;
        createRadioButtons();
    }
    private void createRadioButtons() {
        final RadioButton[] rb = new RadioButton[pointsOfCounsel.length];
        RadioGroup rg = new RadioGroup(this);
        rg.setOrientation(RadioGroup.VERTICAL);
        for(int i=0; i < pointsOfCounsel.length; i++){
            rb[i]  = new RadioButton(this);
            rb[i].setText(pointsOfCounsel[i]);
            rb[i].setId(i);
            if (pointsOfCounsel[i].equals(MainActivity.currentPointOfCounsel)) {
                rb[i].setChecked(true);
            }
           rg.addView(rb[i]);
        }

        LinearLayout layout = (LinearLayout) findViewById(R.id.select_poc);

        layout.addView(rg);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {

            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String formattedDate = sdf.format(date);
                if (MainActivity.currentPointOfCounsel != null &&
                         !MainActivity.currentPointOfCounsel.equals("")) {
                    CounselHistoryItem item = new CounselHistoryItem();
                    item.setPointOfCounsel(MainActivity.currentPointOfCounsel);
                    item.setDateSet(MainActivity.dateAssigned);
                    item.setDateCompleted(formattedDate);
                    saveHistory(item);
                }

                MainActivity.dateAssigned = formattedDate;
                MainActivity.currentPointOfCounsel = pointsOfCounsel[checkedId];
                CounselHistoryItem item = new CounselHistoryItem();
                item.setDateSet(MainActivity.dateAssigned);
                item.setPointOfCounsel(MainActivity.currentPointOfCounsel);
                saveCurrentPointOfCounsel(item);
                self.finish();
            }
        });
    }

    protected void saveCurrentPointOfCounsel(CounselHistoryItem item)  {
        try {
            FileOutputStream outputStream = openFileOutput(MainActivity.FILE_CURRENT, Context.MODE_PRIVATE);
            outputStream.write(item.toString().getBytes());
            outputStream.close();
        } catch (Exception e) {
            Context context = getApplicationContext();
            CharSequence text = "Exception on Save : " + e.toString();
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    protected void saveHistory(CounselHistoryItem item) {
        try {
            FileOutputStream outputStream = openFileOutput(MainActivity.FILE_HISTORY, Context.MODE_APPEND);
            String lineToWrite = item.toString() + "\n";
            outputStream.write(lineToWrite.getBytes());
            outputStream.close();
        } catch (IOException e) {
            Context context = getApplicationContext();
            CharSequence text = "Exception on Save : " + e.toString();
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

}