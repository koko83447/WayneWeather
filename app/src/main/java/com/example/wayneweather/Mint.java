package com.example.wayneweather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Mint {
    public static List<Table> getTable(String response){
        List<Table> tableList = new ArrayList<>();
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONObject records = jsonObject.getJSONObject("records");
            JSONArray location = records.optJSONArray("location");
            for (int i = 0; i < location.length(); i++) {
                JSONObject object = location.getJSONObject(i);
                JSONArray weatherElement = object.getJSONArray("weatherElement");
                for (int j = 0; j <weatherElement.length(); j++) {
                    JSONObject object1 = weatherElement.getJSONObject(j);
                    JSONArray time = object1.getJSONArray("time");
                    for (int k = 0; k < time.length(); k++) {
                        JSONObject object2 = time.getJSONObject(k);
                        String startTime= object2.optString("startTime");
                        String endTime= object2.optString("endTime");
                        JSONObject parameter = object2.getJSONObject("parameter");
                        String parameterName = parameter.optString("parameterName");
                        String parameterUnit = parameter.optString("parameterUnit");

                        tableList.add(new Table(startTime,endTime,parameterName,parameterUnit));
                    }

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tableList;
    }
}
