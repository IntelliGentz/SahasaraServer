package com.intelligentz.sahasara.model;

/**
 * Created by Lakshan on 2017-03-24.
 */
public class Route {
    private String id;
    private String name;
    private String cluster;
    private City startCity;
    private City endCity;

    public Route() {
    }

    public Route(String id, String name, String cluster, City startCity, City endCity) {
        this.id = id;
        this.name = name;
        this.cluster = cluster;
        this.startCity = startCity;
        this.endCity = endCity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
