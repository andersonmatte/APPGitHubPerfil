package br.com.andersonmatte.githubperfil.recycler;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import br.com.andersonmatte.githubperfil.R;
import br.com.andersonmatte.githubperfil.entidades.Repositorio;

public class RecyclerViewRepositorio extends RecyclerView.Adapter<RecyclerViewRepositorio.DataObjectHolder> {

    private Context context;
    private static String LOG_TAG = "RecyclerViewRepositorio";
    private ArrayList<Repositorio> mDataset;
    private static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        AppCompatTextView textViewName;
        AppCompatTextView textViewDescription;
        AppCompatTextView textViewLanguage;

        public DataObjectHolder(View itemView) {
            super(itemView);

            textViewName = (AppCompatTextView) itemView.findViewById(R.id.textName);
            textViewDescription = (AppCompatTextView) itemView.findViewById(R.id.textViewDescription);
            textViewLanguage = (AppCompatTextView) itemView.findViewById(R.id.textViewLanguage);

            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public RecyclerViewRepositorio(ArrayList<Repositorio> myDataset, Context context) {
       this.mDataset = myDataset;
        this.context = context;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_repositorio, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        if (mDataset.get(position).getName() != null){
            holder.textViewName.setText(context.getApplicationContext().getResources().getString(R.string.label_repositorio).toString() + " " + mDataset.get(position).getName());
        } else {
            holder.textViewName.setText(context.getApplicationContext().getResources().getString(R.string.label_repositorio).toString());
        }
        if (mDataset.get(position).getDescription() != null){
            holder.textViewDescription.setText(context.getApplicationContext().getResources().getString(R.string.label_descricao).toString() + " " + mDataset.get(position).getDescription());
        } else {
            holder.textViewDescription.setText(context.getApplicationContext().getResources().getString(R.string.label_descricao).toString());
        }
        if (mDataset.get(position).getLanguage() != null){
            holder.textViewLanguage.setText(context.getApplicationContext().getResources().getString(R.string.label_linguagem).toString() + " " + mDataset.get(position).getLanguage());
        } else {
            holder.textViewLanguage.setText(context.getApplicationContext().getResources().getString(R.string.label_linguagem).toString());
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

}