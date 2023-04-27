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

import java.util.ArrayList;


public class SquadFragment extends Fragment {
    View parentholder;
    ListView listView;
String url="https://api.cricapi.com/v1/match_squad?apikey=4d804354-c923-449b-88e5-10294b80995b&id=";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    parentholder=inflater.inflate(R.layout.fragment_squad, container, false);
        Bundle bundle=getArguments();
        if (bundle != null&& bundle.containsKey("id")) {
            String id = bundle.getString("id");
            url = url + id;
        }
        listView=parentholder.findViewById(R.id.listView);
        ArrayList<String> namearraylist=new ArrayList<>();
        ArrayList<String> idarraylist = new ArrayList<>();
        ArrayList<String> idarraylist1 = new ArrayList<>();
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
                            idarraylist1.add(id);
                        }
                    }

                    ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, namearraylist);
                    listView.setAdapter(arrayAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                            try {
                                String id1 = null;
                                if (pos <= idarraylist.size()) {
                                    id1 = idarraylist.get(pos - 1);
                                } else {
                                    id1 = idarraylist1.get(pos - idarraylist.size() - 2);
                                }
                                Intent intent = new Intent(getContext(), MainActivity3.class);
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