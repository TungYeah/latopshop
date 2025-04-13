package vn.minhtung.laptopshop.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String descrpition;
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescrpition() {
        return descrpition;
    }
    public void setDescrpition(String descrpition) {
        this.descrpition = descrpition;
    }
    @Override
    public String toString() {
        return "Role [id=" + id + ", name=" + name + ", descrpition=" + descrpition + "]";
    }
}