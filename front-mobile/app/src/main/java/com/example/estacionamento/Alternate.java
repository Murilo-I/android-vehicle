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

public class Alternate extends AppCompatActivity {

    EditText edtCodigo, edtNome, edtCpf;
    Button btnAlternate;
    AsyncHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_alterar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Owner owner;
        Intent intent = getIntent();
        owner = (Owner) intent.getSerializableExtra("proprietario");
        edtCodigo = findViewById(R.id.edtId);
        edtNome = findViewById(R.id.edtNome2);
        edtCpf = findViewById(R.id.edtCpf2);
        btnAlternate = findViewById(R.id.button);
        client = new AsyncHttpClient();
        assert owner != null;
        edtCodigo.setText(String.valueOf(owner.getId()));
        edtNome.setText(String.valueOf(owner.getNome()));
        edtCpf.setText(String.valueOf(owner.getCpf()));
        btnAlternate.setOnClickListener(view -> {
            if (edtNome.getText().toString().isEmpty() ||
                    edtCpf.getText().toString().isEmpty()) {
                Toast.makeText(Alternate.this, "Existes campos em brancos!",
                        Toast.LENGTH_SHORT).show();
            } else {
                Owner obj = new Owner();

                obj.setId(Integer.parseInt(edtCodigo.getText().toString()));
                obj.setNome(edtNome.getText().toString());
                obj.setCpf(edtCpf.getText().toString());
                alterarProprietario(obj);
            }
        });
    }

    public void alterarProprietario(Owner obj) {
        String url;
        url = "http://192.168.1.94:8081/proprietario/" + obj.getId();
        JSONObject parametros = new JSONObject();
        try {
            parametros.put("nome", obj.getNome());
            parametros.put("cpf", obj.getCpf());
        } catch (JSONException e) {
            Log.e("error", "message: " + e);
        }
        StringEntity entity = new StringEntity(parametros.toString(), ContentType.APPLICATION_JSON);
        client.put(Alternate.this, url, entity, "application/json",
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode,
                                          Header[] headers,
                                          byte[] responseBody) {
                        if (statusCode == 200) {
                            Toast.makeText(Alternate.this, "Proprietário alterado com sucesso!",
                                    Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(Alternate.this, OwnerList.class);
                            startActivity(i);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode,
                                          Header[] headers,
                                          byte[] responseBody,
                                          Throwable error) {
                        Toast.makeText(Alternate.this,
                                "Erro ao alterar proprietário: " + error.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

}