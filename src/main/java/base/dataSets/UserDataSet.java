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

    @Column(name = "score")
    private int score;

    public UserDataSet() {
    }

    public UserDataSet(int id, String name, String email, String password, int score) {
        this.setId(id);
        this.setName(name);
        this.setEmail(email);
        this.setPassword(password);
        this.setScore(score);
    }

    public UserDataSet(String name, String email, String password) {
        this.setId(-1);
        this.setName(name);
        this.setEmail(email);
        this.setPassword(password);
        this.setScore(0);
    }

    public UserDataSet(String name, String email, String password, int score) {
        this.setId(-1);
        this.setName(name);
        this.setEmail(email);
        this.setPassword(password);
        this.setScore(score);
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

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", score=" + score +
                '}';
    }
}
