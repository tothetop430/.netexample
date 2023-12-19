package org.example;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class JsonHandler {

    //constructor requiring jsonFile to be read
    private static File jsonFile;

    public JsonHandler(File jsonFile) {
        this.jsonFile = jsonFile;
    }
    //returns true if string is JSON

    public boolean isJsonValid(String text) {
        try {
            new JSONObject(text);

        } catch (JSONException e) {
            return false;
        }
        return true;
    }

    public JSONArray getArrayFromJsonFile() {
        JSONArray jsonArray = null;
        try {
            InputStream is = new FileInputStream(jsonFile.getAbsolutePath());
            String jsonTxt = IOUtils.toString(is, "UTF-8");
            jsonTxt = jsonTxt.substring(jsonTxt.indexOf("["));
            jsonArray = new JSONArray(jsonTxt);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    public static JSONObject getObjFromJsonFile() {
        JSONObject jsonObj = null;
        try {
            InputStream is = new FileInputStream(jsonFile.getAbsolutePath());
            String jsonTxt = IOUtils.toString(is, "UTF-8");
            jsonTxt = jsonTxt.substring(jsonTxt.indexOf("{"));
            jsonObj = new JSONObject(jsonTxt);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObj;
    }

    //get testData from jsonFile NOte: main object in jsonfile is a JSON Array
    //return ArrayList

    public ArrayList<LinkedHashMap<String, String>> getJsonData() {
        JSONArray jsonArray = getArrayFromJsonFile();
        ArrayList<LinkedHashMap<String, String>> jsonData = new ArrayList<LinkedHashMap<String, String>>();

        //iterate over JsonArray
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jObj = jsonArray.getJSONObject(i);
            Map<String, String> map = new LinkedHashMap();
            jObj.keySet().forEach(keyStr -> {
                String keyValue = jObj.get(keyStr).toString();
                map.put(keyStr, keyValue);
            });
            jsonData.add(i, (LinkedHashMap<String, String>) map);
        }
        return jsonData;
    }

    //GET TEST DATA FROM JSON
    //returns JSONOBject

    public static LinkedHashMap<String, String> getTestDataFromJson() {
        JSONObject JsonObj = getObjFromJsonFile();
        LinkedHashMap<String, String> jsonData = new LinkedHashMap<>();

        //iterate over JsonObj
        JSONArray keys = JsonObj.names();
        for (int i = 0; i < keys.length(); i++) {
            JSONObject valueObj = (JSONObject) JsonObj.get(keys.get(i).toString());
            jsonData.put(keys.get(i).toString(), valueObj.toString());
        }
        return jsonData;
    }

    //return map of the string, string from the jsonObject
    //return linkedHashmap
    public LinkedHashMap<String, String> jsonObjectToStringMap(JSONObject jsonObject) {
        LinkedHashMap<String, String> objMap = new LinkedHashMap<>();
        try {
            JSONArray objKeys = jsonObject.names();
            for (int i = 0; i < objKeys.length(); i++) {
                String key = objKeys.getString(i);
                String value = jsonObject.get(key).toString();
                objMap.put(key, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objMap;
    }
}
