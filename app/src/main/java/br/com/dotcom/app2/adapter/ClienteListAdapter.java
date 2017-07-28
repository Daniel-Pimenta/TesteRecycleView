package br.com.dotcom.app2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.dotcom.app2.database.DBCon;
import br.com.dotcom.app2.entity.Cliente;
import br.com.dotcom.app2.R;
import br.com.dotcom.app2.repositorio.ClienteRepositorio;

import android.widget.ImageButton;
import android.widget.TextView;


public class ClienteListAdapter extends RecyclerView.Adapter<ClienteListAdapter.ClienteViewHolder>  {

  private List<Cliente> clientes;
  private Cliente cliente;
  private Context context;
  private OnDataSelected  onDataSelected;
  private ClienteRepositorio clienteRepositorio;
  private DBCon dbCon;

  public ClienteListAdapter(Context context, OnDataSelected onDataSelected, List<Cliente> clientes) {
    this.context = context;
    this.onDataSelected = onDataSelected;
    this.clientes = clientes;
  }

  public ClienteListAdapter.ClienteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_act_main_view_linha, parent, false);
    ClienteViewHolder viewHolder = new ClienteViewHolder(view);
    return viewHolder;
  }

  public void onBindViewHolder(ClienteViewHolder holder, int position) {
    final int pos = position;
    cliente = clientes.get(position);
    /*
    holder.moreButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View view) {updateItem(pos);}
    });
    */
    holder.deleteButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View view) {removeItem(pos);}
    });
    holder.title.setText(cliente.id +" - " + cliente.nome + " " + cliente.sobrenome);
  }
  /*
  public void updateItem(int position) {
    Cliente cliente = clientes.get(position);
    //context.startActivity(new Intent(this, LinearLayoutActivity.class));

    notifyItemChanged(position);
  }
  */
  public void removeItem(int position){
    Cliente cliente = clientes.get(position);
    dbCon = new DBCon(context);
    if (dbCon.connect()) {
      clienteRepositorio = new ClienteRepositorio(dbCon.getConnection());
      if (clienteRepositorio.delete(cliente.id)) {
        System.out.println("Deletei...");
        clientes.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, clientes.size());
      }
    }
  }

  public int getItemCount() {
    return clientes.size();
  }

  public static interface OnDataSelected {
    public void onDataSelected(View view, int position);
  }

  public class ClienteViewHolder extends RecyclerView.ViewHolder {

    public TextView title;
    //public ImageButton moreButton;
    public ImageButton deleteButton;

    public ClienteViewHolder(View view) {
      super(view);
      view.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          seleciona(v, getAdapterPosition());
        }
      });
      title = (TextView) view.findViewById(R.id.main_line_title);
      deleteButton = (ImageButton) view.findViewById(R.id.main_line_delete);
      //moreButton = (ImageButton) view.findViewById(R.id.main_line_more);
    }

    private void seleciona(View view, int position) {
      if (onDataSelected != null) {
        onDataSelected.onDataSelected(view, position);
      }
    }

  }



}