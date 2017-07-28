package br.com.dotcom.app2.repositorio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.util.ArrayList;
import java.util.List;

import br.com.dotcom.app2.entity.Cliente;

/**
 * Created by Daniel on 26/07/2017.
 */

public class ClienteRepositorio {

  private SQLiteDatabase con;
  private long id;
  private ContentValues cv;
  private String[] parametros;
  private String msgErro;

  public ClienteRepositorio(SQLiteDatabase con){
    this.con = con;
  }

  private boolean execute(String opt){
    try{
      switch (opt){
        case "I":
          System.out.println("INSERT");
          id = con.insertOrThrow("CLIENTE",null,cv);
          break;
        case "A":
          System.out.println("UPDATE");
          con.update("CLIENTE",cv,"CODIGO=?", parametros);
          break;
        case "E":
          System.out.println("DELETE");
          con.delete("CLIENTE","CODIGO=?", parametros);
          break;
      }
      cv = null;
      parametros = null;
      return true;
    }catch(SQLiteException e){
      msgErro = e.getMessage();
    }
    return false;
  }

  public boolean insert(Cliente cliente){
    cv = new ContentValues();
    cv.put("NOME",cliente.nome);
    cv.put("SOBRENOME",cliente.sobrenome);
    cv.put("ENDERECO",cliente.endereco);
    cv.put("EMAIL",cliente.email);
    cv.put("TELEFONE",cliente.telefone);
    return execute("I");
  }

  public boolean update(Cliente cliente){
    cv = new ContentValues();
    cv.put("NOME",cliente.nome);
    cv.put("SOBRENOME",cliente.sobrenome);
    cv.put("ENDERECO",cliente.endereco);
    cv.put("EMAIL",cliente.email);
    cv.put("TELEFONE",cliente.telefone);
    parametros = new String[1];
    parametros[0] = (String.valueOf(cliente.id));
    return execute("A");
  }

  public boolean delete(long id){
    parametros = new String[1];
    parametros[0] = (String.valueOf(id));
    return execute("E");
  }

  public List<Cliente> buscaTodos(){
    List<Cliente> clientes = new ArrayList<Cliente>();
    StringBuilder sql = new StringBuilder();
    sql.append("SELECT CODIGO, NOME, SOBRENOME, ENDERECO, EMAIL, TELEFONE FROM CLIENTE");

    Cursor rs = con.rawQuery(sql.toString(),null);
    int qtd = rs.getCount();
    if (qtd > 0){
      rs.moveToFirst();
      clientes.clear();
      do{
        Cliente cliente = new Cliente();
        cliente.id        = rs.getInt(0);
        cliente.nome      = rs.getString(1);
        cliente.sobrenome = rs.getString(2);
        cliente.endereco  = rs.getString(3);
        cliente.email     = rs.getString(4);
        cliente.telefone  = rs.getString(5);
        clientes.add(cliente);
      }while(rs.moveToNext());
    }
    return clientes;
  }

  public Cliente buscaCliente(long id) {
    StringBuilder sql = new StringBuilder();
    sql.append("SELECT DISTINCT CODIGO, NOME, SOBRENOME, ENDERECO, EMAIL, TELEFONE FROM CLIENTE WHERE CODIGO = ?");
    String[] par = new String[1];
    par[0] = (String.valueOf(id));

    Cursor rs = con.rawQuery(sql.toString(),par);
    int qtd = rs.getCount();
    Cliente cliente = new Cliente();
    if (qtd > 0){
      rs.moveToFirst();
      cliente.id        = rs.getInt(0);
      cliente.nome      = rs.getString(1);
      cliente.sobrenome = rs.getString(2);
      cliente.endereco  = rs.getString(3);
      cliente.email     = rs.getString(4);
      cliente.telefone  = rs.getString(5);
    }
    return cliente;
  }

  public long getId(){
    return id;
  }

  public String getMsgErro(){
    return msgErro;
  }
}
