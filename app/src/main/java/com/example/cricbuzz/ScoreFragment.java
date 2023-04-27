package com.example.cricbuzz;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class ScoreFragment extends Fragment {
    View parentholder;
    String url="https://api.cricapi.com/v1/match_scorecard?apikey=061428d7-7ba1-4d29-9d25-1ca6211ed5ae&id=";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentholder=inflater.inflate(R.layout.fragment_score, container, false);
        Bundle bundle=getArguments();
        if (bundle != null&& bundle.containsKey("id")) {
            String id = bundle.getString("id");
            url = url + id;
        }
        ListView listView=parentholder.findViewById(R.id.listView);
        ArrayList<String> namearraylist=new ArrayList<>();
        StringRequest jsonObjectRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject dataJObject = jsonObject.getJSONObject("data");
                    String status = dataJObject.getString("status");
                    String venue = dataJObject.getString("venue");
                    JSONArray teamsArray = dataJObject.getJSONArray("teams");
                    String team1 = teamsArray.getString(Integer.parseInt("0"));
                    String team2 = teamsArray.getString(Integer.parseInt("1"));
                    String teams="TEAM1:"+team1+"\t\tTEAM2:"+team2;
                    namearraylist.add(teams);
                    namearraylist.add(status);
                    namearraylist.add(venue);
                    JSONArray scoreArray=dataJObject.getJSONArray("score");
                    for(int i=0;i<scoreArray.length();i++) {
                        JSONObject jsonObject1=scoreArray.getJSONObject(i);
                        String runs=jsonObject1.getString("r");
                        String overs=jsonObject1.getString("o");
                        String wickets=jsonObject1.getString("w");
                        String score="runs:\t\t"+runs+"overs\t\t"+overs+"wickets\t\t"+wickets;
                        namearraylist.add(score);
                    }
                    JSONArray scorecardArray=dataJObject.getJSONArray("scorecard");
                    namearraylist.add("BATTING SCORECARD-(R-runs,B-balls,SR-StrikeRate)");
                    JSONObject jsonObject2 = scorecardArray.getJSONObject(0);
                    JSONArray battingArray = jsonObject2.getJSONArray("batting");
                    JSONArray bowlingArray = jsonObject2.getJSONArray("bowling");
                    for (int i = 0; i < battingArray.length(); i++) {
                        JSONObject jsonObject3 = battingArray.getJSONObject(i);
                        JSONObject jsonObject4 = jsonObject3.getJSONObject("batsman");
                        String name = jsonObject4.getString("name");
                        String dismissal = jsonObject3.getString("dismissal-text");
                        String runs = jsonObject3.getString("r");
                        String balls = jsonObject3.getString("b");
                        String fours = jsonObject3.getString("4s");
                        String sixes = jsonObject3.getString("6s");
                        String strikerate = jsonObject3.getString("sr");
                        String matchStarted=dataJObject.getString("matchStarted");
                        if (dismissal.endsWith("?")) {
                            dismissal = dismissal.substring(0, dismissal.length() - 1);
                        }
                        String scorecard = name+"\t"+dismissal+"\n\nR:" + runs + "\tB:" + balls + "\t4's:" + fours + "\t6's:" + sixes + "\tSR:" + strikerate;
                        if(matchStarted.equals("true"))
                         namearraylist.add(scorecard);
                    }
                    namearraylist.add("BOWLING SCORECARD-(O-Overs,M-Maiden,R-Runs,W-Wickets,ECO-Economy");
                    for (int i = 0; i < bowlingArray.length(); i++) {
                        JSONObject jsonObject3 = bowlingArray.getJSONObject(i);
                        JSONObject jsonObject4 = jsonObject3.getJSONObject("bowler");
                        String name = jsonObject4.getString("name");
                        String overs = jsonObject3.getString("o");
                        String maiden = jsonObject3.getString("m");
                        String runs = jsonObject3.getString("r");
                        String wickets = jsonObject3.getString("w");
                        String economy = jsonObject3.getString("eco");
                        String matchStarted= dataJObject.getString("matchStarted");
                        String scorecard = name + "\tO:" + overs + "\tM:" + maiden + "\tR:" + runs + "\tW:" + wickets + "\tECO:" + economy;
                        if(matchStarted.equals("true"))
                         namearraylist.add(scorecard);
                    }
                    if(!scorecardArray.isNull(1)){
                    namearraylist.add("BATTING SCORECARD");
                    jsonObject2 = scorecardArray.getJSONObject(1);
                    battingArray = jsonObject2.getJSONArray("batting");
                    bowlingArray = jsonObject2.getJSONArray("bowling");
                    for (int i = 0; i < battingArray.length(); i++) {
                        JSONObject jsonObject3 = battingArray.getJSONObject(i);
                        JSONObject jsonObject4 = jsonObject3.getJSONObject("batsman");
                        String name = jsonObject4.getString("name");
                        String dismissal = jsonObject3.getString("dismissal-text");
                        String runs = jsonObject3.getString("r");
                        String balls = jsonObject3.getString("b");
                        String fours = jsonObject3.getString("4s");
                        String sixes = jsonObject3.getString("6s");
                        String strikerate = jsonObject3.getString("sr");
                        String matchStarted= dataJObject.getString("matchStarted");
                        if (dismissal.endsWith("?")) {
                            dismissal = dismissal.substring(0, dismissal.length() - 1);
                        }
                        String scorecard = name + "\t" + dismissal + "\t" + runs+ "\t" + balls + "\t" + fours + "\t" + sixes + "\t" + strikerate;
                        if(matchStarted.equals("true"))
                         namearraylist.add(scorecard);
                    }
                    namearraylist.add("BOWLING SCORECARD");
                    for (int i = 0; i < bowlingArray.length(); i++) {
                        JSONObject jsonObject3 = bowlingArray.getJSONObject(i);
                        JSONObject jsonObject4 = jsonObject3.getJSONObject("bowler");
                        String name = jsonObject4.getString("name");
                        String overs = jsonObject3.getString("o");
                        String maiden = jsonObject3.getString("m");
                        String runs = jsonObject3.getString("r");
                        String wickets = jsonObject3.getString("w");
                        String economy = jsonObject3.getString("eco");
                        String matchStarted= dataJObject.getString("matchStarted");
                        String scorecard = name + "\t" + overs + "\t" + maiden + "\t" + runs + "\t" + wickets + "\t" + economy;
                       if(matchStarted.equals("true"))
                        namearraylist.add(scorecard);
                    }}
                    ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(),
                            android.R.layout.simple_list_item_1, namearraylist);
                    listView.setAdapter(arrayAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                            String name = "";
                            try {
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);
        return parentholder;
    }
}
