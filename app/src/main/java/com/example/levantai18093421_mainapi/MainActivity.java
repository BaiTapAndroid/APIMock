package com.example.levantai18093421_mainapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.PriorityGoalRow;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.channels.spi.SelectorProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button btnPut, btnDelete,btnPost;
    RecyclerView recyclerView;
    List<Product> list;
    EditText editName, editPrice;
    RadioButton radioTrue, radioFalse;
    MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        btnPut = (Button) findViewById(R.id.btnPut);
        btnDelete = findViewById(R.id.btnDelete);
        editName = findViewById(R.id.editName);
        editPrice = findViewById(R.id.editPrice);
        radioFalse = findViewById(R.id.radioFalse);
        radioTrue = findViewById(R.id.radioTrue);
        btnPost = findViewById(R.id.btnPost);
        GetArrayJson();
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String name = intent.getStringExtra("name");
        String price = intent.getStringExtra("price");
       // String status =intent.getStringExtra("status");
        editName.setText(name);
        editPrice.setText(price);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "https://60b4f2bbfe923b0017c833fa.mockapi.io/api/products/"+id;
                DeleteApi(url);
                GetArrayJson();
            }
        });
        btnPut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://60b4f2bbfe923b0017c833fa.mockapi.io/api/products/"+id;
                Boolean status;
                String name = String.valueOf(editName.getText());
                int price = Integer.parseInt(String.valueOf(editPrice.getText()));
                if (radioTrue.isChecked())
                {
                    status = true;
                } else status = false;

                Product product = new Product();
                product.setId(0);
                product.setName(name);
                product.setPrice(price);
                product.setStatus(status);
                PutApi(url, product);
                GetArrayJson();
            }
        });
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://60b4f2bbfe923b0017c833fa.mockapi.io/api/products";
                Boolean status;
                String name = String.valueOf(editName.getText());
                int price = Integer.parseInt(String.valueOf(editPrice.getText()));
                if (radioTrue.isChecked())
                {
                    status = true;
                } else status = false;

                Product product = new Product();
                product.setId(0);
                product.setName(name);
                product.setPrice(price);
                product.setStatus(status);
                PostApi(url, product);
                GetArrayJson();
                editName.setText("");
                editPrice.setText("");
            }
        });
    }

    private void GetData(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

               // tvDisplay.setText(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,
                        "Error make by API server!", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void GetJson(String url){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        try {
//                            tvDisplay.setText(response.getString("name").toString());
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error by get JsonObject...", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);

    }

    private List<Product> GetArrayJson(){
        String url = "https://60b4f2bbfe923b0017c833fa.mockapi.io/api/products";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest =
                new StringRequest(Request.Method.GET
                        ,url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                               try {
                                   JSONArray json = new JSONArray(response);
                                    list =new ArrayList<>();
                                   //Toast.makeText(MainActivity.this, ""+response.toString(), Toast.LENGTH_SHORT).show();
                                   for (int i=0; i<json.length(); i++){
                                       JSONObject object = json.getJSONObject(i);
                                       Product product = new Product();
//                                       int id = object.getInt("id");
//                                       String name  = object.getString("name");
//                                       int price = object.getInt("price");
//                                       Boolean status = object.getBoolean("status");

                                       product.setId(object.getInt("id"));
                                       product.setName(object.getString("name"));
                                       product.setPrice(object.getInt("price"));
                                       product.setStatus(object.getBoolean("status"));
                                       list.add(product);

                                   }
                                   myAdapter = new MyAdapter(list,MainActivity.this);
                                   recyclerView.setAdapter(myAdapter);
                                   recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                               }
                               catch (JSONException e)
                                    {
                                        e.printStackTrace();
                                    }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error by get Json Array!", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(stringRequest);
        return null;
    }
    private void ListArrayJson(String url){
        JsonArrayRequest jsonArrayRequest =
                new JsonArrayRequest(url,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {

                                    try {
                                        for(int i=0; i<response.length(); i++){
                                            JSONObject object = (JSONObject) response.get(i);
                                            int id = object.getInt("id");
                                            String name  = object.getString("name");
                                            int price = object.getInt("price");
                                            Boolean status = object.getBoolean("status");
                                            Product product = new Product();
                                            product.setId(id);
                                            product.setName(name);
                                            product.setPrice(price);
                                            product.setStatus(status);
                                            list.add(product);
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error by get Json Array!", Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);

    }

    private void PostApi(String url, Product product){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error by Post data!", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String> params = new HashMap<>();
                params.put("name",product.getName());
                params.put("price",String.valueOf(product.getPrice()));
                params.put("status",String.valueOf(product.getStatus()));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void PutApi(String url, Product product){
        StringRequest stringRequest = new StringRequest(
                Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error by Post data!", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("name",product.getName());
                params.put("price",String.valueOf(product.getPrice()));
                params.put("status",String.valueOf(product.getStatus()));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void DeleteApi(String url){
        StringRequest stringRequest = new StringRequest(
                Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error by Post data!", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}