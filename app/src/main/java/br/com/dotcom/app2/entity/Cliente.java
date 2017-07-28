package br.com.dotcom.app2.entity;

/**
 * Created by Daniel on 25/07/2017.
 */

public class Cliente {

  public long id;
  public String nome;
  public String sobrenome;
  public String endereco;
  public String email;
  public String telefone;

  public Cliente(){}

  public Cliente(long id, String nome, String sobrenome, String endereco, String email, String telefone){
    this.id        = id;
    this.nome      = nome;
    this.sobrenome = sobrenome;
    this.endereco  = endereco;
    this.email     = email;
    this.telefone  = telefone;
  }


}
