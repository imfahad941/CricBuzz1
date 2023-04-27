package com.example.cricbuzz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity3 extends AppCompatActivity {
    ListView listView;
   String url="https://api.cricapi.com/v1/players_info?apikey=4d804354-c923-449b-88e5-10294b80995b&id=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        listView=findViewById(R.id.listView);
        ImageView imageView=findViewById(R.id.imageView);
        Intent intent=getIntent();
        String id=intent.getStringExtra("id");
        Log.d("MainActivity", "id received: " + id);
        url=url+id;
        ArrayList<String> namearraylist=new ArrayList<>();
        ArrayList<String> idarraylist = new ArrayList<>();
        StringRequest jsonObjectRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject dataJObject = jsonObject.getJSONObject("data");
                    String name=dataJObject.getString("name");
                    String dob=dataJObject.getString("dateOfBirth");
                    String dobsub=dob.substring(0,10);
                    String role=dataJObject.getString("role");
                    String batstyle=dataJObject.getString("battingStyle");
                    String bowlstyle=dataJObject.getString("bowlingStyle");
                    String pob=dataJObject.getString("placeOfBirth");
                    String country=dataJObject.getString("country");
                    String playerImg=dataJObject.getString("playerImg");
                    Picasso.get().load(playerImg).into(imageView);
                    String player="Name:"+name+"\n\nDate of Birth:"+dobsub+"\n\nRole:"+role+"\n\nBatting Style:"+batstyle+"\n\nBowling Style:"+bowlstyle+"\n\nPlace Of Birth:"+pob+"\n\nCountry:"+country;
                    namearraylist.add(player);
                    JSONArray stats=dataJObject.getJSONArray("stats");
                    HashMap<String, ArrayList<String>> statsMap = new HashMap<>();
                    for(int i=0;i<stats.length();i++){
                        JSONObject jsonObject1=stats.getJSONObject(i);
                        String matchtype=jsonObject1.getString("matchtype");
                        String matchtype1=matchtype.toUpperCase();
                        String stat=jsonObject1.getString("stat");
                        String stat1=stat.toUpperCase();
                        String value=jsonObject1.getString("value");
                        String info=stat1+":\t"+value;
                        if(!statsMap.containsKey(matchtype1)){
                            statsMap.put(matchtype1, new ArrayList<>());
                        }
                        statsMap.get(matchtype1).add(info);
                    }
                    ArrayList<String> namearraylist=new ArrayList<>();
                    namearraylist.add(player);
                    for(Map.Entry<String, ArrayList<String>> entry : statsMap.entrySet()){
                        String matchtype1 = entry.getKey();
                        ArrayList<String> matchStats = entry.getValue();
                        namearraylist.add(matchtype1);
                        namearraylist.addAll(matchStats);
                    }
                    ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, namearraylist);
                    listView.setAdapter(arrayAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
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
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);
    }
}