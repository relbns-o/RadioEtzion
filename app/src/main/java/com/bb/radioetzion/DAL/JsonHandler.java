package com.bb.radioetzion.DAL;

import com.bb.radioetzion.BL.Show;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class JsonHandler {
    public List<Show> parseJson(String jsonString) {
        List<Show> showsList = new ArrayList<>();
        //declaring the json object and passing the string to it.
        JSONObject jsonObject = null;
        try {
            //get the entire string and treat it as a JSON object
            jsonObject = new JSONObject(jsonString);
            //get the data array
            JSONArray shows = jsonObject.getJSONArray("data");
            // cycles through the array and adds new show instance of the required fields to a show list
            for(int i=0; i<shows.length(); i++) {
                JSONObject show = shows.getJSONObject(i);
                showsList.add(new Show(
                        show.getString("name"),
                        show.getInt("audio_length"),
                        show.getString("url"),
                        show.getString("created_time")
                ));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // return the show list
        return showsList;
    }
}
