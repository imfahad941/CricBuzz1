package com.example.cricbuzz;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

public class CommentaryFragment extends Fragment{
    View parentholder;
    String url="https://api.cricapi.com/v1/match_bbb?apikey=47e7ec11-6e04-411c-811a-451c86522470&id=";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       parentholder=inflater.inflate(R.layout.fragment_commentary, container, false);
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
                    JSONArray CommArray = dataJObject.getJSONArray("bbb");
                    for(int i=0;i< CommArray.length();i++){
                        JSONObject jsonObject1=CommArray.getJSONObject(i);
                        String over = jsonObject1.getString("over");
                        String ball = jsonObject1.getString("ball");
                        JSONObject jsonObject2=jsonObject1.getJSONObject("batsman");

                        JSONObject jsonObject3=jsonObject1.getJSONObject("bowler");
                        String batsman=jsonObject2.getString("name");
                        String bowler=jsonObject3.getString("name");
                        String runs=jsonObject1.getString("runs");
                        String extras=jsonObject1.getString("extras");
                        String team=over+".\t"+ball+"\tBatting:"+batsman+"\tBowling:"+bowler+"\tRuns:"+runs+"\tExtras:"+extras;
                        namearraylist.add(team);
                    }
                ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1, namearraylist);
                listView.setAdapter(arrayAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                        String t1s="";
                        String t2s="";
                        String id="";
                        String team1="";
                        String team2="";
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