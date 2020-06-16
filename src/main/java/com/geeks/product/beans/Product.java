package com.geeks.product.beans;

import javax.persistence.*;
import java.util.List;

@Entity
public class Product{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private Double epuc;

    @Transient
    private List<Store> store;

    public List<Store> getStore() {
        return store;
    }

    public void setStore(List<Store> store) {
        this.store = store;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getEpuc() {
        return epuc;
    }

    public void setEpuc(Double epuc) {
        this.epuc = epuc;
    }
}
