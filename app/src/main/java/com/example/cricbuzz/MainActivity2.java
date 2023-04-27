package com.example.cricbuzz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity2 extends AppCompatActivity {
    ListView listView;
    String url = "https://api.cricapi.com/v1/match_squad?apikey=061428d7-7ba1-4d29-9d25-1ca6211ed5ae&id=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        listView=findViewById(R.id.listView);
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
                    JSONArray array = new JSONObject(response).getJSONArray("data");
                    JSONObject jsonObject = array.getJSONObject(0);
                    String teamName=jsonObject.getString("teamName");
                    namearraylist.add(teamName);
                    JSONArray players = jsonObject.getJSONArray("players");
                    for (int i = 0; i < players.length(); i++) {
                        JSONObject jsonObject1 = players.getJSONObject(i);
                        String id = jsonObject1.getString("id");
                        String name = jsonObject1.getString("name");
                        String role = jsonObject1.getString("role");
                        String squad = name + ":\t" + role;
                        if (!role.equals("--")) {
                            namearraylist.add(squad);
                            idarraylist.add(id);
                        }}
                    JSONObject jsonObject2=array.getJSONObject(1);
                    teamName=jsonObject2.getString("teamName");
                    namearraylist.add(teamName);
                    players = jsonObject2.getJSONArray("players");
                    for (int i = 0; i < players.length(); i++) {
                        JSONObject jsonObject1 = players.getJSONObject(i);
                        String id = jsonObject1.getString("id");
                        String name = jsonObject1.getString("name");
                        String role = jsonObject1.getString("role");
                        String squad = name + ":\t" + role;
                        if (!role.equals("--")) {
                            namearraylist.add(squad);
                            idarraylist.add(id);
                        }
                    }

                    ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, namearraylist);
                                listView.setAdapter(arrayAdapter);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                                        try {
                                            String selectedItem = namearraylist.get(pos);
                                            String id1 = idarraylist.get(pos);
                                            String[] parts = selectedItem.split("\n\n");
                                            Intent intent = new Intent(getApplicationContext(), MainActivity3.class);
                                            intent.putExtra("id", id1);
                                            Log.d("MainActivity2", "ID: " + id1);
                                            Log.d("MainActivity2", "Intent: " + intent.getExtras());
                                            startActivity(intent);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    });
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(jsonObjectRequest);
                }
                }
