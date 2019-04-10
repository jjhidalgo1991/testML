package com.jangapp.myappmla.repository.network.api;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.jangapp.myappmla.Constants;
import com.jangapp.myappmla.repository.storge.model.ProdOverView;

import java.lang.reflect.Type;
import java.util.ArrayList;

class ProductsJsonDeserializer implements JsonDeserializer {

    private static String TAG = ProductsJsonDeserializer.class.getSimpleName();

    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ArrayList<ProdOverView> prod = null;
        try {
            JsonObject jsonObject = json.getAsJsonObject();
            JsonArray prodJsonArray = jsonObject.getAsJsonArray(Constants.RESULT_ARRAY_DATA_TAG);
            prod = new ArrayList<>(prodJsonArray.size());
            for (int i = 0; i < prodJsonArray.size(); i++) {
                // adding the converted wrapper to our container
                ProdOverView dematerialized = context.deserialize(prodJsonArray.get(i), ProdOverView.class);
                prod.add(dematerialized);
            }
        } catch (JsonParseException e) {
            Log.e(TAG, String.format("Could not deserialize Product element: %s", json.toString()));
        }
        return prod;
    }
}
