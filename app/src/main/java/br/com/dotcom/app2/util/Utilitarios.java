package br.com.dotcom.app2.util;

import android.text.TextUtils;
import android.util.Patterns;

/**
 * Created by Daniel on 26/07/2017.
 */

public class Utilitarios {

  public Utilitarios(){

  }

  public boolean isCampoVazio(String valor){
    boolean ok = (valor==null || TextUtils.isEmpty(valor) || valor.trim().isEmpty());
    return ok;
  }

  public boolean isEmailValido(String valor){
    boolean ok = (!isCampoVazio(valor) && Patterns.EMAIL_ADDRESS.matcher(valor).matches());
    return ok;
  }

  public boolean isTelefoneValido(String valor){
    boolean ok = (!isCampoVazio(valor) && Patterns.PHONE.matcher(valor).matches());
    return ok;
  }
}
