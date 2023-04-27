package com.example.cricbuzz;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class SettingsFragment extends Fragment {
    View parentholder;
    ListView listView;
    String url;
    String id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentholder=inflater.inflate(R.layout.fragment_settings,container,false);
        ListView listView=parentholder.findViewById(R.id.listView);
        url = "https://api.cricapi.com/v1/cricScore?apikey=4d804354-c923-449b-88e5-10294b80995b";
        ArrayList<String> namearraylist=new ArrayList<>();
        StringRequest jsonObjectRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Calendar currentCalendar = Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
                    JSONArray array = new JSONObject(response).getJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonObject = array.getJSONObject(i);
                        id = jsonObject.getString("id");
                        String team1 = jsonObject.getString("t1");
                        String team2 = jsonObject.getString("t2");
                        String t1s=jsonObject.getString("t1s");
                        String t2s=jsonObject.getString("t2s");
                        String datetime = jsonObject.getString("dateTimeGMT");
                        Date matchDate = dateFormat.parse(datetime); // convert the date string to a Date object
                        Calendar matchCalendar = Calendar.getInstance(TimeZone.getTimeZone("IST"));
                        matchCalendar.setTime(matchDate);
                        int result = matchCalendar.compareTo(currentCalendar);
                        String status = jsonObject.getString("status");
                        String matchstatus=jsonObject.getString("ms");
                        String team =team1+"vs"+team2+"\n\n"+status+"\n\n"+datetime;
                        if(result>0&&matchstatus.equals("fixture"))
                            namearraylist.add(team);
                    }
                    ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, namearraylist);
                    listView.setAdapter(arrayAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                            try {
                                        String selectedItem = namearraylist.get(pos);
                                        String[] parts = selectedItem.split("\n\n");
                                        String id1 = parts[3];
                                        Intent intent = new Intent(getContext(), MainActivity2.class);
                                        intent.putExtra("id", id1);
                                        Log.d("MainActivity2", "ID: " + id1);
                                        Log.d("MainActivity2", "Intent: " + intent.getExtras());
                                        startActivity(intent);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            //Intent intent = new Intent(getApplicationContext(), MatchDetail.class);
                            //intent.putExtra("id", id1);
                            //startActivity(intent);
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