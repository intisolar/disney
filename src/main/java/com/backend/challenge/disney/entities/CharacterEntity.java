
package com.backend.challenge.disney.entities;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class CharacterEntity {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String name;
    private Integer age;
    private float weight;
    private String story;
    @OneToOne
    private Picture picture;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
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
     * @return the age
     */
    public Integer getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * @return the weight
     */
    public float getWeight() {
        return weight;
    }

    /**
     * @param weight the weight to set
     */
    public void setWeight(float weight) {
        this.weight = weight;
    }

    /**
     * @return the story
     */
    public String getStory() {
        return story;
    }

    /**
     * @param story the story to set
     */
    public void setStory(String story) {
        this.story = story;
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

    @Override
    public String toString() {
        return "Character{" + "id=" + id + ", name=" + name + ", age=" + age + ", weight=" + weight + ", story=" + story + ", picture=" + picture + '}';
    }
    
}
