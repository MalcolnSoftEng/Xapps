package xapps.teste;

import java.io.Serializable;

/**
 * Created by Malcoln on 26/07/2017.
 */

public class Usuario implements Serializable{
    String id;
    String name;
    String last_name;
    String avatar;

    public Usuario() {
    }
    public Usuario(String id, String name, String last_name, String avatar) {
        this.id = id;
        this.name = name;
        this.last_name = last_name;
        this.avatar = avatar;
    }
    public String toString(){
        return id;
    }
}
