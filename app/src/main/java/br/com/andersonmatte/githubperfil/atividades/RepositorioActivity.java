package br.com.andersonmatte.githubperfil.atividades;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.andersonmatte.githubperfil.R;
import br.com.andersonmatte.githubperfil.base.ActivityBase;
import br.com.andersonmatte.githubperfil.entidades.Usuario;
import br.com.andersonmatte.githubperfil.recycler.RecyclerViewRepositorio;
import br.com.andersonmatte.githubperfil.entidades.Repositorio;

public class RepositorioActivity extends ActivityBase {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "RepositorioActivity";
    private ArrayList<Repositorio> listaRepositorios = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositorio);

        //Recebe a lista de repositorios passados na Intent da
        // Classe PerfilActivity por mecanismo de Bundle e por Parcelable.
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            listaRepositorios = extras.getParcelableArrayList("resultado");
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerViewRepositorio(listaRepositorios, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((RecyclerViewRepositorio) mAdapter).setOnItemClickListener(new RecyclerViewRepositorio
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i(LOG_TAG, " Clicked on Item " + position);
            }
        });
    }

    //Botao voltar para activity com suporte bread crumb.
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, PerfilActivity.class);
        //prepara o objeto para passar para a PerfilActivity.
        Bundle bundle = new Bundle();
        bundle.putSerializable("resultado", (Serializable) this.usuarioRecebido);
        intent.putExtra("usuario", bundle);
        startActivity(intent);
        finish();
    }

}
