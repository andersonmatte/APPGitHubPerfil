package br.com.andersonmatte.githubperfil.atividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;

import br.com.andersonmatte.githubperfil.Interfaces.IGitHubAPI;
import br.com.andersonmatte.githubperfil.R;
import br.com.andersonmatte.githubperfil.base.ActivityBase;
import br.com.andersonmatte.githubperfil.entidades.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends ActivityBase {

    private AppCompatEditText editTextUsuario;
    private TextInputLayout textLayoutUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //Clique do botão buscar usuário.
        AppCompatButton buttonBuscarUsuario = (AppCompatButton) findViewById(R.id.btn_buscarUsuario);
        buttonBuscarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextUsuario = (AppCompatEditText) findViewById(R.id.et_usuario);
                textLayoutUsuario = (TextInputLayout) findViewById(R.id.txtlayout_usuario);
                if (validaForm()){
                    //Busca os dados na API GitHub com auxilio do Retrofit e Gson
                    // para fazer o parse do Json para classe Java.
                    IGitHubAPI iGitHubAPI  = IGitHubAPI.retrofit.create(IGitHubAPI.class);
                    final Call<Usuario> callUsuario = iGitHubAPI.getUsuario(editTextUsuario.getText().toString());
                    callUsuario.enqueue(new Callback<Usuario>() {
                        @Override
                        public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                            Usuario usuario = response.body();
                            //prepara o objeto para passar para a PerfilActivity.
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("resultado", usuario);
                            //Chama a PerfilActivity já com o objeto populado.
                            Intent intentPerfil = new Intent(HomeActivity.this, PerfilActivity.class);
                            intentPerfil.putExtra("usuario", bundle);
                            startActivity(intentPerfil);
                        }
                        @Override
                        public void onFailure(Call<Usuario> call, Throwable t) {
                            Log.i("Erro na busca usuário", t.getMessage());
                        }
                    });
                }
            }
        });
    }

    //Valida se o usuário foi preenchido.
    private Boolean validaForm() {
        if (editTextUsuario.getText().toString().isEmpty()) {
            textLayoutUsuario.setErrorEnabled(true);
            textLayoutUsuario.setError("Preencha o nome de usuário");
            return false;
        } else {
            textLayoutUsuario.setErrorEnabled(false);
            return true;
        }
    }

}
