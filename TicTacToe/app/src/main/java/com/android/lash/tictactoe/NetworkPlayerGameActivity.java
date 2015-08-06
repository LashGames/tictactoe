package com.android.lash.tictactoe;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class NetworkPlayerGameActivity extends ActionBarActivity {
    private ProgressDialog progressDialog;
    private JSONParser jsonParser=new JSONParser();
    EditText player1IdTxt;
    EditText player2IdTxt;
    EditText startTimeTxt;
    String isSuccess="Success";

    private static String url_start_game = "http://tictactoelash.hostoi.com/android_connect/start_new_game.php";
    private static final String TAG_SUCCESS = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_player_game);
        Button newGameBtn= (Button) findViewById(R.id.newGameBtn);
        player1IdTxt= (EditText) findViewById(R.id.player1IdTxt);
        player2IdTxt= (EditText) findViewById(R.id.player2IdTxt);
        startTimeTxt= (EditText) findViewById(R.id.startTimeTxt);

        newGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new startNewGame(getBaseContext()).execute();
            }
        });
    }
    class startNewGame extends AsyncTask<String, String, String> {
        private Context baseContext;
        public startNewGame(Context context){
            this.baseContext=context;
        }
        @Override

        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(NetworkPlayerGameActivity.this);
            progressDialog.setMessage("Starting Game...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String player1 = player1IdTxt.getText().toString();
            String player2 = player2IdTxt.getText().toString();
            String startTime = startTimeTxt.getText().toString();

            // Building Parameters
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("player1", player1));
            params.add(new BasicNameValuePair("player2", player2));
            params.add(new BasicNameValuePair("start_time", startTime));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_start_game,
                    "POST", params);

            // check log cat fro response
            //Log.d("Create Response", json.toString());

            // check for success tag
            if(json!=null) {
                try {
                    int success = json.getInt(TAG_SUCCESS);

                    if (success == 1) {
                        // successfully created product
                        isSuccess="Success";
                        //Toast.makeText(baseContext, "Game Started Successfully", Toast.LENGTH_SHORT).show();

                        // closing this screen
                        finish();
                    } else {
                        // failed to create product
                        isSuccess="Fail";
                        //Toast.makeText(baseContext, "Game Start Unsucessfully", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                isSuccess="Null";
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            if (isSuccess.equals("Null")) {
                Toast.makeText(baseContext, "Null JSON", Toast.LENGTH_SHORT).show();
            }else if(isSuccess.equals("Success")) {
                Toast.makeText(baseContext, "Success", Toast.LENGTH_SHORT).show();
            }else if(isSuccess.equals("Fail")) {
                Toast.makeText(baseContext, "Fail", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_single_player_game, menu);
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
}
