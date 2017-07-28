package br.com.dotcom.app2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import br.com.dotcom.app2.repositorio.ClienteRepositorio;

/**
 * Created by Daniel on 26/07/2017.
 */

public class DBCon {

  private SQLiteDatabase con;
  private SQLLite        sql;
  private String         msgErro;
  private Context        context;

  public DBCon() {
  }

  public DBCon(Context context) {
    this.context = context;
  }

  public boolean connect(){
    try{
      sql = new SQLLite(context);
      con = sql.getWritableDatabase();
      return true;
    }catch(android.database.SQLException ex){
      setMsgErro(ex.getMessage());
    }
    return false;
  }

  public void close(){
    con.close();
    sql.close();
  }

  public String getMsgErro() {
    return msgErro;
  }

  public void setMsgErro(String msgErro) {
    this.msgErro = msgErro;
  }

  public SQLiteDatabase getConnection() {
    return con;
  }
}
