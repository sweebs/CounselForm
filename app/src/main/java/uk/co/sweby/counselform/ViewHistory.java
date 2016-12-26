package uk.co.sweby.counselform;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class ViewHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);
        List<CounselHistoryItem>  list = readHistoryFile();
        Collections.sort(list);

        TableLayout tab = (TableLayout) findViewById(R.id.history_table);

        TableRow tr;
        TextView textView;

        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);


        int i = 0;
        for (CounselHistoryItem item : list) {
            tr =  new TableRow(this);
            tr.setPadding(5,5,5,5);
            tr.setId(i++);
            tr.setLayoutParams(lp);
            textView = new TextView(this);
            textView.setTextColor(Color.BLACK);
            textView.setText(R.string.counsel);

            tr.addView(textView);
            tab.addView(tr);

            tr =  new TableRow(this);
            tr.setPadding(5,5,5,5);
            tr.setId(i++);
            tr.setLayoutParams(lp);
            textView = new TextView(this);
            textView.setText(item.getPointOfCounsel());
            textView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT));
            textView.setWidth(300);
            textView.setMaxLines(4);
            textView.setSingleLine(false);
            tr.addView(textView);
            tab.addView(tr);

            tr =  new TableRow(this);
            tr.setPadding(5,5,5,5);
            tr.setId(i++);
            tr.setLayoutParams(lp);

            textView = new TextView(this);
            textView.setTextColor(Color.BLACK);
            textView.setText(R.string.assigned);
            tr.addView(textView);
            textView = new TextView(this);
            textView.setTextColor(Color.BLACK);
            textView.setText(R.string.dateCompleted);
            tr.addView(textView);
            tab.addView(tr);

            tr =  new TableRow(this);
            tr.setPadding(5,5,5,5);
            tr.setId(i++);
            tr.setLayoutParams(lp);
            textView = new TextView(this);
            textView.setText(item.getDateSet());
            tr.addView(textView);
            textView = new TextView(this);
            textView.setText(item.getDateCompleted());
            tr.addView(textView);
            tab.addView(tr);
        }


    }

    protected List<CounselHistoryItem> readHistoryFile() {
        CounselHistoryItem item = null;
        List<CounselHistoryItem> list = new ArrayList<>();
        try {
            FileInputStream fis = openFileInput(MainActivity.FILE_HISTORY);
            InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);
            String line;
            StringTokenizer stringTokenizer;
            while ((line = br.readLine()) != null) {
                stringTokenizer = new StringTokenizer(line, "|");
                item = new CounselHistoryItem();
                item.setPointOfCounsel(stringTokenizer.nextToken());
                item.setDateSet(stringTokenizer.nextToken());
                item.setDateCompleted(stringTokenizer.nextToken());
                list.add(item);
            }
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
            return list;
        }
        return list;
    }
}
