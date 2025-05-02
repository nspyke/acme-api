package com.nspyke.acmeapi.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entity class representing a Doodad in the system.
 */
@Entity
@Table(name = "doodads")
public class Doodad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column
    private String image;

    @Column(nullable = false)
    private Float price;

    // Default constructor required by JPA
    public Doodad() {}

    // Constructor with all fields except id
    public Doodad(String name, String description, String image, Float price) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Doodad{" + "id="
                + id + ", name='"
                + name + '\'' + ", description='"
                + description + '\'' + ", image='"
                + image + '\'' + ", price="
                + price + '}';
    }
}
