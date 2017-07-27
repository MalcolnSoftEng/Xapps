package xapps.teste;

import android.app.ProgressDialog;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.View;

import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.ListView;

import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    EditText mEdtLocal; //Caixa de Texto para digitar a procura
    ImageButton mBtnBuscar; // botão(imagem) da Procura

    private ProgressDialog pDialog;
    public ListView lv;
    public static String lat,lng,localName;
    public String writeConsult;
    // URL from API to get JSON data
    private static String url;
    boolean resultSearch = true;
    String avatar;

    ArrayList<HashMap<String, String>> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userList = new ArrayList<>();

        lv = (ListView) findViewById(R.id.list);


        mEdtLocal = (EditText) findViewById(R.id.edtLocal);
        mBtnBuscar = (ImageButton) findViewById(R.id.imgBtnBuscar);
        mBtnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                executeSearch();
            }
        });
    }


    public void executeSearch(){
        if (!resultSearch){
            Toast.makeText(getBaseContext(), "Nenhum Resultado",Toast.LENGTH_LONG).show();
        } else{

            writeConsult = mEdtLocal.getText().toString();
            writeConsult=writeConsult.replace(" ","+");
            new GetSearch().execute();
            url = "https://reqres.in/api/users?page="+writeConsult;

        }
    }

    private class GetSearch extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Carregando...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Resposta da URL: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("data");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String id = c.getString("id");
                        String name = c.getString("first_name");
                        String last_name = c.getString("last_name");
                        String avatar = c.getString("avatar");

                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("id", id);
                        contact.put("first_name", name);
                        contact.put("last_name", last_name);
                        contact.put("avatar", avatar);

                        // adding contact to contact list
                        userList.add(contact);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Erro : " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Erro: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Não foi possível estabelecer a conexão.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Não foi possível estabelecer a conexão. Check seu LogCAt para possíveis erros!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            UserListAdapter adapter = new UserListAdapter(
                    MainActivity.this, userList,
                    R.layout.list_item, new String[]{}, new int[]{});


            lv.setAdapter(adapter);
        }

    }
}