package com.example.android.leaguestalker;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class LeagueAPIHelper {
    public static final String API_KEY = "?api_key=" + "RGAPI-d52d9140-8776-452f-882d-0795db3294d9";
    public static final String QUERY_FOR_SUMMONER_NAME = "https://na1.api.riotgames.com/lol/summoner/v4/summoners/by-name/"; //{Summoner name}?api_key=[API_KEY]
    public static final String QUERY_FOR_CURRENT_GAME = "https://na1.api.riotgames.com/lol/spectator/v4/active-games/by-summoner/"; // {Summoner ID}

    Context context;

    public LeagueAPIHelper(Context context){this.context = context;}

    /*
        Request Summoner's info using Summoner name
        JSONObject includes: id, accountId, puuid, name, profileIconId, revisionDate, summonerLevel
        IN:     String Summoner_name
        OUT:    String id
     */
    public interface SearchBySummonerNameListener{
        void onError(String message);
        void onResponse(String id);
    }

    public void getSummonerIdBySummonerName(String summonerName, SearchBySummonerNameListener searchBySummonerNameListener){
        String url = QUERY_FOR_SUMMONER_NAME + summonerName + API_KEY;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        String id = "";
                        JSONObject status;
                        try {
                            if(response.getString("id") != null){
                                // If the Summoner name exists
                                id = response.getString("id");
                            }
                            else{
                                // If the Summoner name does not exist or search goes wrong, print out the status message
                                status = response.getJSONObject("status");
                                String message = status.getString("message");
                                int status_code = status.getInt("status_code");
                                Toast.makeText(context, Integer.toString(status_code) + ": " + message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        searchBySummonerNameListener.onResponse(id);
                    }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                searchBySummonerNameListener.onError(error.toString());
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(request);
    }
}
