package com.bb.radioetzion.PL;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.bb.radioetzion.BL.ListAdapter;
import com.bb.radioetzion.BL.Show;
import com.bb.radioetzion.DAL.AsyncResponse;
import com.bb.radioetzion.DAL.ConnectionHandler;
import com.bb.radioetzion.DAL.JsonHandler;
import com.bb.radioetzion.R;

import java.util.List;
// implements AsyncResponse th handle http connection result
public class MainActivity extends AppCompatActivity implements AsyncResponse {
    // radio etzion mixcloud api url
    private final String URL = "https://api.mixcloud.com/Radio_Etzion2/cloudcasts/";
    // list button state (true shows list icon, false shows back icon
    private boolean state = true;
    // declaring local data members
    private Context context;
    private ImageButton imageView;
    private WebView webView;
    private ListView listView;
    private ListAdapter listAdapter;
    private List<Show> showList;
    private ConnectionHandler connectionHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initialize pointers
        setPointer();
    }

    private void setPointer() {
        // assigning reference to the local data members
        this.context = this;
        imageView = findViewById(R.id.imgList);
        listView = findViewById(R.id.placeHolder);
        webView = findViewById(R.id.placeHolder2);

        // instantiate the connectionHandler (AsyncTask) class which gets the json from the given url
        connectionHandler = new ConnectionHandler();
        // connect the AsyncTask class to the main activity with Interface(AsyncResponse)
        connectionHandler.delegate = this;
        // get the json and pass it back to the main activity in the processFinish function below
        connectionHandler.execute(URL);
    }

    // gets the returned json string and complete logic
    @Override
    public void processFinish(String output) {
        // using JsonHandler class to parse json string to list of Shows
        showList = new JsonHandler().parseJson(output);
        // building the listView
        //buildListView();
        // base adapter for hte listView
        listAdapter = new ListAdapter(context,showList);
        listView.setAdapter(listAdapter);
        // listView onItemClickListener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // hides the listView
                listView.setVisibility(View.GONE);
                // reveals the webView
                webView.setVisibility(View.VISIBLE);
                // change the list icon image to a back image
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_return_48dp));
                // loads the current url to the webView
                webView.loadUrl(showList.get(position).getUrl());
                // change the state variable to false (used as an indication of the icon status)
                state = false;
            }
        });

        // building the webView
        buildWebView();
        // list button onClickListener (if app state is web view execute the following)
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!state){
                    // hides the webView
                    webView.setVisibility(View.GONE);
                    // reveals the listView
                    listView.setVisibility(View.VISIBLE);
                    // change the back icon image to the list image
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_list_48dp));
                    // change the state variable to true (used as an indication of the icon status)
                    state = true;
                }
            }
        });
    }


    private void buildWebView() {
        // get the WebView Settings instance
        WebSettings settings = webView.getSettings();
        // Enable JavaScript - to allow sites that depend on JS to load content
        settings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient());
    }
}
