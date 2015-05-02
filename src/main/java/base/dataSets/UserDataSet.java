package base.dataSets;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Виталий on 02.05.2015.
 */
@Entity
@Table(name = "users")
public class UserDataSet implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    public UserDataSet() {
    }

    public UserDataSet(int id, String name, String email, String password) {
        this.setId(id);
        this.setName(name);
        this.setEmail(email);
        this.setPassword(password);
    }

    public UserDataSet(String name, String email, String password) {
        this.setId(-1);
        this.setName(name);
        this.setEmail(email);
        this.setPassword(password);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
