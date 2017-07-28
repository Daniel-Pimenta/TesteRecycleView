package br.com.dotcom.app2.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import br.com.dotcom.app2.R;
import br.com.dotcom.app2.adapter.ClienteListAdapter;

import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Daniel on 25/07/2017.
 */

public class ClienteViewHolder extends RecyclerView.ViewHolder {

  public TextView    title;
  public ImageButton moreButton;
  public ImageButton deleteButton;


  public ClienteViewHolder(View view) {
    super(view);
    view.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        seleciona(v,getAdapterPosition());
      }
    });
    title        = (TextView)view.findViewById(R.id.main_line_title);
    deleteButton = (ImageButton)view.findViewById(R.id.main_line_delete);
    //moreButton   = (ImageButton)view.findViewById(R.id.main_line_more);
  }

  private void seleciona(View view, int position) {

  }


}
