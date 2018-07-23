package br.com.andersonmatte.githubperfil.Interfaces;

import java.util.List;

import br.com.andersonmatte.githubperfil.entidades.Follower;
import br.com.andersonmatte.githubperfil.entidades.Following;
import br.com.andersonmatte.githubperfil.entidades.Repositorio;
import br.com.andersonmatte.githubperfil.entidades.Usuario;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IGitHubAPI {

    public static final Retrofit retrofit =  new Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET("/users/{usuario}")
    Call<Usuario> getUsuario(@Path("usuario") String usuario);

    @GET("/users/{usuario}/repos")
    Call<List<Repositorio>> getRepositorios(@Path("usuario") String usuario);

    @GET("/users/{usuario}/repos")
    Call<List<Follower>> getFollowers(@Path("usuario") String usuario);

    @GET("/users/{usuario}/following")
    Call<List<Following>> getFollowing(@Path("usuario") String usuario);

}
