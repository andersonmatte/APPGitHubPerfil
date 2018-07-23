package br.com.andersonmatte.githubperfil.atividades;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.andersonmatte.githubperfil.Interfaces.IGitHubAPI;
import br.com.andersonmatte.githubperfil.R;
import br.com.andersonmatte.githubperfil.base.ActivityBase;
import br.com.andersonmatte.githubperfil.entidades.Repositorio;
import br.com.andersonmatte.githubperfil.entidades.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilActivity extends ActivityBase {

    private List<Repositorio> listaRepositorio = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        //Recebe os dados passados na Intent da Classe HomeActivity por mecanismo de Bundle.
        Bundle bundle = getIntent().getBundleExtra("usuario");
        if (bundle != null) {
            this.usuarioRecebido = (Usuario) bundle.getSerializable("resultado");
            this.montaPerfil(usuarioRecebido);
        }
        //Clique do botão buscar os repositórios do usuário.
        Button buttonBuscarRepositorios = (Button) findViewById(R.id.btn_buscarRepositorios);
        buttonBuscarRepositorios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usuarioRecebido != null && usuarioRecebido.getLogin() != null){
                    //Busca os dados na API GitHub com auxilio do Retrofit e Gson
                    // para fazer o parse do Json para classe Java.
                    IGitHubAPI iGitHubAPI  = IGitHubAPI.retrofit.create(IGitHubAPI.class);
                    final Call<List<Repositorio>> callrepositorio = iGitHubAPI.getRepositorios(usuarioRecebido.getLogin());
                    callrepositorio.enqueue(new Callback<List<Repositorio>>() {
                        @Override
                        public void onResponse(Call<List<Repositorio>> call, Response<List<Repositorio>> response) {
                            listaRepositorio = response.body();
                            //prepara os objetos para passar para a RepositorioActivity.
                            Bundle bundle = new Bundle();
                            bundle.putParcelableArrayList("resultado", (ArrayList<? extends Parcelable>) listaRepositorio);
                            //Chama a RepositorioActivity já com a lista de objetos populados.
                            Intent intentRepositorio = new Intent(PerfilActivity.this, RepositorioActivity.class);
                            intentRepositorio.putExtras(bundle);
                            startActivity(intentRepositorio);
                        }
                        @Override
                        public void onFailure(Call<List<Repositorio>> call, Throwable t) {
                            Log.i("Erro busca repositório", t.getMessage());
                        }
                    });
                }
            }
        });
    }

    //Montagem da UI com os dados recebidos do serviço da API.
    // Define imagem do Avatar se não tiver seta default.
    public void montaPerfil(Usuario usuario){
        if (usuario.getAvatarUrl() != null){
            new DownloadImageFromInternet((ImageView) findViewById(R.id.imgUsuario))
                    .execute(usuario.getAvatarUrl());
        } else {
            ImageView imageViewPadrao = (ImageView) findViewById(R.id.imgUsuario);
            imageViewPadrao.setImageResource(R.mipmap.github);
        }
        //Popula os atributos do usuário com o obejeto recebido.
        TextView nomeUsuario = (TextView) findViewById(R.id.nomeUsuario);
        nomeUsuario.setText(usuario.getName() != null ? getResources().getText(R.string.label_usuario) + " " + usuario.getName() : null);
        TextView loginUsuario = (TextView) findViewById(R.id.loginUsuario);
        loginUsuario.setText(usuario.getLogin() != null ? getResources().getText(R.string.label_login) + " " + usuario.getLogin() : null);
        TextView followers = (TextView) findViewById(R.id.followers);
        followers.setText(usuario.getFollowers() != 0 ? getResources().getText(R.string.label_followers) + " " + String.valueOf(usuario.getFollowers()) : null);
        TextView following = (TextView) findViewById(R.id.following);
        following.setText(usuario.getFollowing() != 0 ? getResources().getText(R.string.label_following) + " " + String.valueOf(usuario.getFollowing()) : null);
    }

    //Botao voltar para activity com suporte bread crumb.
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    public List<Repositorio> getListaRepositorio() {
        return listaRepositorio;
    }

    public void setListaRepositorio(List<Repositorio> listaRepositorio) {
        this.listaRepositorio = listaRepositorio;
    }

}
