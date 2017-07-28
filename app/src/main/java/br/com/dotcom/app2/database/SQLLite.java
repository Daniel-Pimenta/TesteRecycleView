package br.com.dotcom.app2.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Daniel on 26/07/2017.
 */

public class SQLLite extends SQLiteOpenHelper {

  private DatabaseErrorHandler e;
  private String msgErro;

  public SQLLite(Context context) {
    super(context, "DB2", null, 1);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    System.out.println("SQLiteDatabase.onCreate");
    db.execSQL(ScriptDDL.getCreateTableCliente());
    System.out.println("SQLiteDatabase.onCreate 1");
    db.execSQL(ScriptDDL.cargaInicial());
    System.out.println("SQLiteDatabase.onCreate 2");
    db.execSQL(ScriptDDL.cargaInicial());
    System.out.println("SQLiteDatabase.onCreate 3");
    db.execSQL(ScriptDDL.cargaInicial());
    System.out.println("SQLiteDatabase.onCreate 4");
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {  }

  public SQLiteDatabase getConnection(){
    return getWritableDatabase();
  }

  public String getMsgErro(){
    return msgErro;
  }
}
