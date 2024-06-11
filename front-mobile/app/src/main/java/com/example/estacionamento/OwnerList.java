package com.example.estacionamento;

import static com.example.estacionamento.MainActivity.URL;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.estacionamento.model.Owner;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class OwnerList extends AppCompatActivity {

    Activity context;
    ListView owners;
    AsyncHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_proprietario);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        context = OwnerList.this;
        owners = findViewById(R.id.ownersList);
        client = new AsyncHttpClient();
        loadOwner();

    }

    public void loadOwner() {
        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    listAll(new String(responseBody));
                } else {
                    Toast.makeText(OwnerList.this,
                            "Falha ao carregar proprietários. Status:" + statusCode,
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode,
                                  cz.msebera.android.httpclient.Header[] headers,
                                  byte[] responseBody,
                                  Throwable error) {
                Toast.makeText(OwnerList.this,
                        "Erro ao carregar proprietários: " + error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void listAll(String response) {
        final ArrayList<Owner> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                Owner p = new Owner();

                p.setId(jsonArray.getJSONObject(i).getInt("owner_id"));

                p.setNome(jsonArray.getJSONObject(i).getString("nome"));

                p.setCpf(jsonArray.getJSONObject(i).getString("cpf"));
                list.add(p);
            }
            OwnerAdapter adapter = new OwnerAdapter(context,
                    R.layout.adapter,
                    R.id.ownerId,
                    list);
            owners.setAdapter(adapter);
        } catch (JSONException e) {
            Log.e("error", "message: " + e);
        }

        owners.setOnItemLongClickListener((adapterView, view, i, l) -> {
            Owner p = list.get(i);
            String url = URL + p.getId();
            client.delete(url, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode,
                                      cz.msebera.android.httpclient.Header[] headers,
                                      byte[] responseBody) {
                    if (statusCode == 200) {
                        Toast.makeText(OwnerList.this,
                                "Proprietário excluído com sucesso",
                                Toast.LENGTH_SHORT).show();
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            Log.e("error", "message: " + e);
                        }
                        loadOwner();
                    }
                }

                @Override
                public void onFailure(int statusCode,
                                      cz.msebera.android.httpclient.Header[] headers,
                                      byte[] responseBody,
                                      Throwable error) {
                    Toast.makeText(OwnerList.this,
                            "Erro ao excluir proprietário: " + error.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
            return false;
        });

        owners.setOnItemClickListener((adapterView, view, i, l) -> {
            final Owner owner = list.get(i);
            String b = "owner_id: " + owner.getId() + "\n" +
                    "nome: " + owner.getNome() + "\n" +
                    "cpf: " + owner.getCpf() + "\n";
            AlertDialog.Builder a = new AlertDialog.Builder(OwnerList.this);
            a.setCancelable(true);
            a.setTitle("Detalhes do proprietário");
            a.setMessage(b);
            a.setIcon(R.drawable.ic_launcher_background);
            a.setNegativeButton("Editar", (dialogInterface, i1) -> {
                Intent i2 = new Intent(OwnerList.this, Alternate.class);
                i2.putExtra("owner", owner);
                startActivity(i2);
            });
            a.show();
        });
    }
}