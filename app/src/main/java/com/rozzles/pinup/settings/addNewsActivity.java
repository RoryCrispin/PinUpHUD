package com.rozzles.pinup.settings;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.rozzles.pinup.R;

import java.util.ArrayList;
import java.util.Collections;

public class addNewsActivity extends ActionBarActivity {

    ArrayList<String> newsSourceArray;
    private ListView mainListView;
    private ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);
        mainListView = (ListView) findViewById(R.id.newsSourceList);
        newsSourceArray = new ArrayList<String>();
        listAdapter = new ArrayAdapter<String>(this, R.layout.news_source_list_item, newsSourceArray);

        mainListView.setAdapter(listAdapter);

        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                newsSourceArray.remove(position);
                listAdapter.notifyDataSetChanged();
            }
        });

        loadSources(this);
        listAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onStop() {

        saveSources(this);
        super.onStop();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_news, menu);
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

    public void addClicked(View v) {
        System.out.println("add!");
        showDialog();
    }

    private void showDialog() {

        final EditText txtUrl = new EditText(this);

// Set the default text to a link of the Queen
        txtUrl.setHint("http://feeds.bbci.co.uk/news/world/rss.xml");

        new AlertDialog.Builder(this)
                .setTitle("RSS Feed")
                .setMessage("Paste in the link to an RSS newsfeed. \n Almost all news sites offer these feeds.")
                .setView(txtUrl)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String url = txtUrl.getText().toString();
                        addFeed(url);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                })
                .show();
    }

    private void addFeed(String url) {
        if (verifyRSS(url)) {
            newsSourceArray.add(url);
            listAdapter.notifyDataSetChanged();
        } else {
            Context context = getApplicationContext();
            CharSequence text = "Not a valid feed!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

    }

    private boolean verifyRSS(String url) {

        return true;
    }

    public boolean saveArray(String[] array, String arrayName, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("com.rozzles.pinup.newsSources", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(arrayName + "_size", array.length);
        for (int i = 0; i < array.length; i++)
            editor.putString(arrayName + "_" + i, array[i]);
        return editor.commit();
    }

    public String[] loadArray(String arrayName, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("com.rozzles.pinup.newsSources", 0);
        int size = prefs.getInt(arrayName + "_size", 0);
        String array[] = new String[size];
        for (int i = 0; i < size; i++)
            array[i] = prefs.getString(arrayName + "_" + i, null);
        return array;
    }

    private void loadSources(Context c) {
        String[] list = loadArray("newsSourceList", c);
        newsSourceArray.clear();
        Collections.addAll(newsSourceArray, list);
    }

    private void saveSources(Context c) {
        String[] list = newsSourceArray.toArray(new String[newsSourceArray.size()]);
        saveArray(list, "newsSourceList", c);
    }


}
