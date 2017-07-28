package br.com.dotcom.app2.activity;

import android.content.Intent;
import android.os.Bundle;
//import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import br.com.dotcom.app2.R;
import br.com.dotcom.app2.entity.Cliente;
import br.com.dotcom.app2.repositorio.ClienteRepositorio;
import br.com.dotcom.app2.database.DBCon;
import br.com.dotcom.app2.util.*;

public class ActEditCliente extends AppCompatActivity {

  private Cliente       cliente;

  private long          id;
  private EditText      edtNome;
  private EditText      edtSobrenome;
  private EditText      edtEndereco;
  private EditText      edtEmail;
  private EditText      edtTelefone;

  private ClienteRepositorio clienteRepositorio;

  private DBCon dbCon;

  @Override  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.act_edit_cliente);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    //layoutActEditCliente = (ConstraintLayout) findViewById(R.id.content_act_edit_cliente);

    edtNome      = (EditText)findViewById(R.id.edtNome);
    edtSobrenome = (EditText)findViewById(R.id.edtSobrenome);
    edtEndereco  = (EditText)findViewById(R.id.edtEndereco);
    edtEmail     = (EditText)findViewById(R.id.edtEmail);
    edtTelefone  = (EditText)findViewById(R.id.edtTelefone);
    edtTelefone.addTextChangedListener(new MaskWatcher("(##) #####-####"));

    Intent intent = getIntent();
    Bundle bundle = intent.getExtras();
    id = bundle.getLong("ID");

    dbCon = new DBCon(this);
    if (dbCon.connect()){
      clienteRepositorio = new ClienteRepositorio(dbCon.getConnection());
      cliente = clienteRepositorio.buscaCliente((long)bundle.getLong("ID"));
      edtNome.setText(cliente.nome);
      edtSobrenome.setText(cliente.sobrenome);
      edtEndereco.setText(cliente.endereco);
      edtEmail.setText(cliente.email);
      edtTelefone.setText(cliente.telefone);
    }else {
      alert("ERRO",dbCon.getMsgErro());
    }

    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        confirmar();
      }
    });
  }

  public void confirmar(){
    if (validaCampos()){
      try{
        System.out.println("Vou gravar...");
        cliente = new Cliente();
        cliente.id        = id;
        cliente.nome      = edtNome.getText().toString();
        cliente.sobrenome = edtSobrenome.getText().toString();
        cliente.endereco  = edtEndereco.getText().toString();
        cliente.email     = edtEmail.getText().toString();
        cliente.telefone  = edtTelefone.getText().toString();
        boolean ok = false;
        if (id==999999) {
          ok = clienteRepositorio.insert(cliente);
        }else{
          ok=clienteRepositorio.update(cliente);
        }
        if (ok){
          System.out.println("Gravei...");
          dbCon.close();
          finish();
        }else {
          alert("ERRO", clienteRepositorio.getMsgErro());
        }
      }catch(Exception ex){
        alert("ERRO",ex.getMessage());
      }
    }
  }

  private boolean validaCampos(){
    System.out.println("Vou validar...");
    boolean ok = true;
    Utilitarios util = new Utilitarios();
    if (util.isCampoVazio(edtNome.getText().toString())){
      ok = false;
      edtNome.requestFocus();
    }else if (util.isCampoVazio(edtSobrenome.getText().toString())){
      ok = false;
      edtSobrenome.requestFocus();
    }else if(util.isCampoVazio(edtEndereco.getText().toString())){
      ok = false;
      edtEndereco.requestFocus();
    }else if(!util.isEmailValido(edtEmail.getText().toString())){
      ok = false;
      edtEmail.requestFocus();
    }else if(!util.isTelefoneValido(edtTelefone.getText().toString())){
      ok = false;
      edtTelefone.requestFocus();
    }
    if (!ok){
      alert("ATENÇÃO","Campos Inválidos ou em Branco !");
    }
    System.out.println("Validei...");
    return ok;
  }

  private void alert(String titulo, String msgErro){
    AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
    alertDialog.setTitle(titulo);
    alertDialog.setMessage(msgErro);
    alertDialog.setNeutralButton("OK", null);
    alertDialog.show();
  }
}
