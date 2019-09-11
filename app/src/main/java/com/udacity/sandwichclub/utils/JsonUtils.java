package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

// JSONUTils.java gets the json data from strings.xml
public class JsonUtils {

    public static final String NAME = "name";
    public static final String MAIN_NAME = "mainName";
    public static final String ALSO_KNOWN_AS = "alsoKnownAs";
    public static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    public static final String DESCRIPTION = "description";
    public static final String IMAGE_URL = "image";
    public static final String INGREDIENTS = "ingredients";

    // parse JSON data to a Sandwich object
    public static Sandwich parseSandwichJson(String json) {

        // create sandwich object
        Sandwich sandwich = new Sandwich();
        try {
            JSONObject root = new JSONObject(json);

            //get root object
            JSONObject name = root.getJSONObject(NAME);

            //set object values
            sandwich.setMainName(name.getString(MAIN_NAME));
            sandwich.setAlsoKnownAs(convertJsonArrayToList(name.getJSONArray(ALSO_KNOWN_AS)));

            sandwich.setPlaceOfOrigin(root.getString(PLACE_OF_ORIGIN));
            sandwich.setDescription(root.getString(DESCRIPTION));
            sandwich.setImage(root.getString(IMAGE_URL));
            sandwich.setIngredients(convertJsonArrayToList(root.getJSONArray(INGREDIENTS)));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sandwich;
    }

    // Convert a JSONArray into a List<String> object
    private static List<String> convertJsonArrayToList(JSONArray jsonArray) {
        List<String> returnedList = new ArrayList<>();
        try {
            for(int index = 0; index < jsonArray.length(); index++) {
                returnedList.add(jsonArray.getString(index));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnedList;
    }
}