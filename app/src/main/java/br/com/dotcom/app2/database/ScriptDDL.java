package br.com.dotcom.app2.database;

/**
 * Created by Daniel on 26/07/2017.
 */

public class ScriptDDL {

  public static String getCreateTableCliente(){
    StringBuilder sql = new StringBuilder();
    sql.append("CREATE TABLE IF NOT EXISTS CLIENTE (");
    sql.append("  CODIGO    INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,");
    sql.append("  NOME      VARCHAR(250) NOT NULL,");
    sql.append("  SOBRENOME VARCHAR(250) NOT NULL,");
    sql.append("  ENDERECO  VARCHAR(250) NOT NULL,");
    sql.append("  EMAIL     VARCHAR(250) NOT NULL,");
    sql.append("  TELEFONE  VARCHAR(20)  NOT NULL ");
    sql.append(")");
    return sql.toString();
  }

  public static String cargaInicial(){
    StringBuilder sql = new StringBuilder();
    sql.append("INSERT INTO CLIENTE (NOME,SOBRENOME,ENDERECO,EMAIL,TELEFONE) VALUES ");
    sql.append("('DANIEL','PIMENTA','RUA SINUOSA 198 CASA 8','daniel.s.pimenta@gmail.com','+5521991598700')");
    return sql.toString();
  }

}
