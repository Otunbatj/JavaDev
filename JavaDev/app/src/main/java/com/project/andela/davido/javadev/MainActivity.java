package com.project.andela.davido.javadev;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.project.andela.davido.javadev.devadapter.DevAdapter;
import com.project.andela.davido.javadev.devmodel.DevModel;
import com.project.andela.davido.javadev.util.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private DevAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<DevModel> userList = new ArrayList<>();
    private static String LOG_TAG = "MainActivity";

    private String userName, userImageURL, userHTMLURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdapter = new DevAdapter(userList, this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

        ((DevAdapter) mAdapter).setOnItemClickListener(new DevAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                DevModel userModel = userList.get(position);
                Intent intent = new Intent(MainActivity.this, DevActivity.class);
                intent.putExtra("userName", userModel.getName());
                intent.putExtra("userImageURL", userModel.getImageURL());
                intent.putExtra("userhtmlURL", userModel.getHtmlURL());
                startActivity(intent);
                mAdapter.notifyDataSetChanged();
            }
        });

        sendRequest("https://api.github.com/search/users?q=+language:java+location:lagos");
    }

    private void sendRequest(String URL) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(LOG_TAG, response.toString());

                try {
                    JSONArray jsonArray = response.getJSONArray("items");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        userName = jsonObject.getString("login");
                        userImageURL = jsonObject.getString("avatar_url");
                        userHTMLURL = jsonObject.getString("html_url");

                        userList.add(new DevModel(userName, userImageURL, userHTMLURL));
                    }
                    mAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this,
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(LOG_TAG, "Error: " + error.getMessage());
                Toast.makeText(MainActivity.this,
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }
}