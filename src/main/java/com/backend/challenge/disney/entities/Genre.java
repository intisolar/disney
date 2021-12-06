/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backend.challenge.disney.entities;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Genre {
    
    @Id
    @GeneratedValue (generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String ID;
    private String name;
    @OneToOne
    private Picture picture;
    @ManyToMany
    private List<MovieOrSeries> moviesOrSeries;

    /**
     * @return the ID
     */
    public String getID() {
        return ID;
    }

    /**
     * @param ID the ID to set
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the picture
     */
    public Picture getPicture() {
        return picture;
    }

    /**
     * @param picture the picture to set
     */
    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    /**
     * @return the moviesOrSeries
     */
    public List<MovieOrSeries> getMoviesOrSeries() {
        return moviesOrSeries;
    }

    /**
     * @param moviesOrSeries the moviesOrSeries to set
     */
    public void setMoviesOrSeries(List<MovieOrSeries> moviesOrSeries) {
        this.moviesOrSeries = moviesOrSeries;
    }

    @Override
    public String toString() {
        return "Genre{" + "ID=" + ID + ", name=" + name + ", picture=" + picture + ", moviesOrSeries=" + moviesOrSeries + '}';
    }
    
}
