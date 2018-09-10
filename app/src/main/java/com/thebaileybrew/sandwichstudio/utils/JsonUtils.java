package com.thebaileybrew.sandwichstudio.utils;

import android.util.Log;


import com.thebaileybrew.sandwichstudio.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private static final String TAG = JsonUtils.class.getSimpleName();

    private static final String SANDWICH_NAME = "name";
    private static final String SANDWICH_MAIN = "mainName";
    private static final String AKA = "alsoKnownAs";
    private static final String ORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE_LOC = "image";
    private static final String INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {

        Sandwich thisSandwich = new Sandwich();

        List<String> ingredientsList = new ArrayList<>();
        List<String> akaList = new ArrayList<>();

        try {
            JSONObject currentSandwichJson = new JSONObject(json);

            //Extract the current sandwich name
            JSONObject sandwichNameObject = currentSandwichJson.getJSONObject("name");
            String currentSandwich = sandwichNameObject.getString("mainName");

            //Extract the AKA list
            JSONArray sandwichAKAList = sandwichNameObject.getJSONArray("alsoKnownAs");
            for (int i = 0; i < sandwichAKAList.length(); i++){
                if (sandwichAKAList.length() != 0) {
                    String value = sandwichAKAList.getString(i);
                    String alsoKnownAs = String.valueOf(value);
                    akaList.add(alsoKnownAs);
                }
            }

            //Extract the place of origin
            String sandwichOrigin = "";
            if (sandwichNameObject.has("placeOfOrigin")) {
                sandwichOrigin = sandwichNameObject.getString("placeOfOrigin");
            }

            //Extract the sandwich description
            String sandwichDescription = currentSandwichJson.getString("description");

            //Extract the image path
            String sandwichDrawable = currentSandwichJson.getString("image");

            JSONArray sandwichIngredientsList = currentSandwichJson.getJSONArray("ingredients");
            for (int t = 0; t <sandwichIngredientsList.length(); t++){
                if (sandwichIngredientsList.length() != 0) {
                    String sandwichIngredients = String.valueOf(sandwichIngredientsList.getString(t));
                    ingredientsList.add(sandwichIngredients);
                }
            }

            Log.v(TAG, "Sandwich name: " + currentSandwich +
                    " \nAlso known as: " + akaList + " \nOrigin: " + sandwichOrigin +
                    " \nDescription: " + sandwichDescription + " \nImage location: " + sandwichDrawable +
                    " \nIngredients: " + ingredientsList);

            thisSandwich = new Sandwich(currentSandwich, akaList, sandwichOrigin, sandwichDescription,
                    sandwichDrawable, ingredientsList);

        } catch (JSONException je) {
            Log.e(TAG, "parseSandwichJson: problem parsing", je);
        }

        return thisSandwich;
    }
}
