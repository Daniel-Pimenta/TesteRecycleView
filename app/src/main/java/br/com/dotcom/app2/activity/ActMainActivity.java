package br.com.dotcom.app2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.dotcom.app2.R;
import br.com.dotcom.app2.database.DBCon;
import br.com.dotcom.app2.entity.Cliente;
import br.com.dotcom.app2.adapter.ClienteListAdapter;
import br.com.dotcom.app2.adapter.holder.ClienteViewHolder;
import br.com.dotcom.app2.repositorio.ClienteRepositorio;
import br.com.dotcom.app2.util.Utilitarios;

public class ActMainActivity extends AppCompatActivity implements ClienteListAdapter.OnDataSelected{

  private RecyclerView recyclerView;
  private LinearLayoutManager linearLayoutManager;
  private GridLayoutManager gridLayoutManager;
  private RecyclerView.Adapter adapter;
  private RecyclerView.ViewHolder viewHolder;
  private List<Cliente> clientes = new ArrayList<Cliente>();
  private ClienteRepositorio clienteRepositorio;
  private DBCon dbCon;
  private Utilitarios util;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.act_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putLong("ID",999999);
        Intent intent = new Intent(ActMainActivity.this, ActEditCliente.class);
        intent.putExtras(bundle);
        startActivity(intent);
      }
    });

    recyclerView = (RecyclerView) findViewById(R.id.content_act_main_recycle);
    recyclerView.setHasFixedSize(true);

    linearLayoutManager = new LinearLayoutManager(this);
    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

    gridLayoutManager = new GridLayoutManager(this,2);
    gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

    recyclerView.setLayoutManager(linearLayoutManager);
    // Configurando um divisor entre linhas, para uma melhor visualização.

    recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
  }

  @Override
  protected void onStart(){
    super.onStart();
    leClientes();
  }
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_act_main, menu);
    return true;
  }
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    //noinspection SimplifiableIfStatement
    switch (id) {
      case R.id.menu_item_00 :
        if(recyclerView.getLayoutManager() instanceof  GridLayoutManager) {
          recyclerView.setLayoutManager(linearLayoutManager);
        } else {
          recyclerView.setLayoutManager(gridLayoutManager);
        }
        return true;
      case R.id.menu_item_01 :
        return true;
      case R.id.menu_item_02 :
        return true;
    };
    return super.onOptionsItemSelected(item);
  }
  @Override
  public void onDataSelected(View view, int position) {
    Cliente cliente  = clientes.get(position);
    //Toast.makeText(this, "Selected Item: " + selectedItem.toString(), Toast.LENGTH_SHORT).show();

    Intent intent = new Intent(ActMainActivity.this, ActEditCliente.class);
    String txt = position +"";
    Bundle bundle = new Bundle();
    bundle.putLong("ID",cliente.id);
    intent.putExtras(bundle);
    dbCon.close();
    startActivity(intent);
  }

  private void leClientes(){
    System.out.println("Conectou");
    dbCon = new DBCon(this);
    if (dbCon.connect()){
      clienteRepositorio = new ClienteRepositorio(dbCon.getConnection());
      clientes = clienteRepositorio.buscaTodos();
      adapter = new ClienteListAdapter(this, this, clientes);
      recyclerView.setAdapter(adapter);
      System.out.println("Bucatodos: "+clientes.size());
    }else {
      alert("ERRO",dbCon.getMsgErro());
    }
  }

  private void alert(String titulo, String msgErro){
    AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
    alertDialog.setTitle(titulo);
    alertDialog.setMessage(msgErro);
    alertDialog.setNeutralButton("OK", null);
    alertDialog.show();
  }

}
