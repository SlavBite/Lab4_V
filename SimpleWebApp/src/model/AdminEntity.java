package model;

import javax.persistence.*;

@Entity
@Table(name = "admins", schema = "webapproles")
public class AdminEntity {
    private int idAdmin;
    private String name;
    private String password;

    @Id
    @Column(name = "id_admin")
    public int getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
