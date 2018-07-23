package br.com.andersonmatte.githubperfil.base;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;

import br.com.andersonmatte.githubperfil.entidades.Usuario;

public class ActivityBase extends AppCompatActivity {

    protected Usuario usuarioRecebido;

    //Suporte para Download de Imagem.
    protected class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;
        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView = imageView;
            Toast.makeText(getApplicationContext(), "Por favor, aguarde, pode demorar alguns segundos ..", Toast.LENGTH_SHORT).show();
        }
        protected Bitmap doInBackground(String... urls) {
            String imageURL = urls[0];
            Bitmap bimage = null;
            try {
                InputStream in = new java.net.URL(imageURL).openStream();
                bimage = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }

    public Usuario getUsuarioRecebido() {
        return usuarioRecebido;
    }

    public void setUsuarioRecebido(Usuario usuarioRecebido) {
        this.usuarioRecebido = usuarioRecebido;
    }

}
