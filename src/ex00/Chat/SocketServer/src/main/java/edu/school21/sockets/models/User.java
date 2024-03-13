package edu.school21.sockets.models;

import javax.persistence.Table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column
    private String name;

    @Column
    private String passwd;

    public User(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public User(long id, String name, String passwd) {
        this.id = id;
        this.name = name;
    }

    public void setId(long id){
        this.id = id;
    }
    public long getId(){
        return id;
    }

    public void setname(String name){
        this.name = name;
    }
    public String getname(){
        return name;
    }

    @Override
    public String toString() {
        return
                "User {id = " +
                id +
                ", name = " +
                name +
                "} ";
    }
}