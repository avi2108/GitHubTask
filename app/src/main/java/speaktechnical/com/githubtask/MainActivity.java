package speaktechnical.com.githubtask;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import speaktechnical.com.githubtask.adapters.GitCommitsRecyclerAdapter;

public class MainActivity extends AppCompatActivity {

    private ArrayList<GitHubFeed> gitHubFeedArrayList;//datalist holding GitHub rails commit feed
    GitCommitsRecyclerAdapter adapter;

    RecyclerView recyclerView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.home_text));

        //---initializing views-----
        recyclerView = (RecyclerView) findViewById(R.id.recylerViewForGitCommitList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //--initializing class objects----
        gitHubFeedArrayList = new ArrayList<>();
        adapter = new GitCommitsRecyclerAdapter(this, gitHubFeedArrayList);
        recyclerView.setAdapter(adapter);
        getGitRepoCommitsData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This function will fetch git rails repo's commit data ,prepares data list and updates the recyclerview with data
     */
    void getGitRepoCommitsData() {
        progressBar.setVisibility(View.VISIBLE);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET,
                "https://api.github.com/search/commits?q=rails/rails", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Log.e("githubfeed", gitHubFeedArrayList.size() + "");

                                progressBar.setVisibility(View.GONE);
                                try {
                                    JSONArray jsonArray = response.getJSONArray("items");

                                    for (int index = 0; index < jsonArray.length(); index++) {

                                        JSONObject jsonObject = jsonArray.getJSONObject(index);
                                        //constructing ArrayList of GitHubFeed model objects
                                        gitHubFeedArrayList.add(new GitHubFeed(
                                                jsonObject.isNull("author")?jsonObject.getJSONObject("repository").getJSONObject("owner").getString("login"):jsonObject.getJSONObject("author").getString("login") ,
                                                jsonObject.isNull("author")?jsonObject.getJSONObject("repository").getJSONObject("owner").getString("avatar_url"):jsonObject.getJSONObject("author").getString("avatar_url") ,
                                                jsonObject.getJSONObject("commit").isNull("message") ? "No Message" : jsonObject.getJSONObject("commit").getString("message"),
                                                jsonObject.getJSONObject("commit").getJSONObject("tree").getString("sha"),
                                                jsonObject.getJSONObject("commit").getJSONObject("author").getString("date")));

                                    }
                                    adapter.notifyDataSetChanged();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Log.e("error", error.getMessage());
            }
        }) { //headers to be passed for this GIT commit data
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("User-Agent", "avi2108");
                params.put("Accept", "application/vnd.github.cloak-preview+json");
                return params;
            }
        };

        MyApplication.getInstance().addToRequestQueue(jsonRequest);
    }

}
