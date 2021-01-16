package com.estoeban.blizzardwowdatatest.repositories;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class CharacterInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String covenant;
    private String twosRating;
    private String threesRating;
    private String rbgRating;

    @Override
    public String toString() {
        return "CharacterInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", covenant='" + covenant + '\'' +
                ", twosRating='" + twosRating + '\'' +
                ", threesRating='" + threesRating + '\'' +
                ", rbgRating='" + rbgRating + '\'' +
                '}';
    }
}
