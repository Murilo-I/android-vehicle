package com.example.estacionamento;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.estacionamento.model.Owner;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.ContentType;
import cz.msebera.android.httpclient.entity.StringEntity;

public class MainActivity extends AppCompatActivity {
    private static final String URL = "http://localhost:8081/proprietario";

    EditText edtNome, edtCpf;
    Button btnCad, btnList;
    AsyncHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtNome = findViewById(R.id.edtNome);
        edtCpf = findViewById(R.id.edtCpf);
        client = new AsyncHttpClient();
        btnCad = findViewById(R.id.button1);

        btnCad.setOnClickListener(view -> {

            if (edtNome.getText().toString().isEmpty() || edtCpf.getText().toString()
                    .isEmpty()) {
                Toast.makeText(MainActivity.this, "Existes campos em brancos !",
                        Toast.LENGTH_SHORT).show();
            } else {
                Owner owner = new Owner();
                owner.setNome(edtNome.getText().toString());
                owner.setCpf(edtCpf.getText().toString());
                register(owner);
            }
        });

        btnList = findViewById(R.id.button2);
        btnList.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, OwnerList.class);
            startActivity(i);
        });
    }

    public void register(Owner obj) {
        JSONObject params = new JSONObject();
        try {
            params.put("nome", obj.getNome());
            params.put("cpf", obj.getCpf());
        } catch (JSONException e) {
            Log.e("error", "message: ", e);
        }
        StringEntity entity = new StringEntity(params.toString(), ContentType.APPLICATION_JSON);
        client.post(MainActivity.this, URL, entity,
                "application/json", new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers,
                                          byte[] responseBody) {
                        if (statusCode == 200) {
                            Toast.makeText(MainActivity.this,
                                            "Proprietário cadastrado com sucesso!",
                                            Toast.LENGTH_SHORT)
                                    .show();
                            edtNome.setText(null);
                            edtCpf.setText(null);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody,
                                          Throwable error) {
                        Toast.makeText(MainActivity.this,
                                "Erro ao cadstrar proprietário: " +
                                        error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}