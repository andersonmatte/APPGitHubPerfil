package br.com.andersonmatte.githubperfil.entidades;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*Classe repositóio @implements Parcelable
*
* Fonte de referência: https://developer.android.com/reference/android/os/Parcelable
*
* */

public class Repositorio implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("language")
    @Expose
    private String language;

    public Repositorio(int id, String name, String description, String language){
        this.id = id;
        this.name = name;
        this.description = description;
        this.language = language;
    }

    //Recebe um Parcel que permite ler dados dele e passando para os atributos.
    private Repositorio(Parcel p){
        id = p.readInt();
        name = p.readString();
        description = p.readString();
        language = p.readString();
    }

    //Todas as classes que implementarem o Parcelable, devem ter esse atributo,
    // ele é quem junta as funcionalidades de um DataInputStream e DataOutputStream
    // para serializar e deserializar objetos.
    public static final Parcelable.Creator<Repositorio>
            CREATOR = new Parcelable.Creator<Repositorio>() {

        public Repositorio createFromParcel(Parcel in) {
            return new Repositorio(in);
        }

        public Repositorio[] newArray(int size) {
            return new Repositorio[size];
        }
    };

    //inteiro que identifica a classe.
    @Override
    public int describeContents() {
        return 0;
    }

    //Responsável por serializar as informações da classe.
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(language);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

}
