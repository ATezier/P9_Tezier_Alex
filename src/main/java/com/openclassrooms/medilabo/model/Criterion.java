package com.openclassrooms.medilabo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "criterion")
@NoArgsConstructor
public class Criterion {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long cid;
    @NotBlank
    @Column(name = "name")
    String name;

    public Criterion(String name) {
        this.name = name;
    }
    public Long getCid() {
        return cid;
    }
    public void setCid(Long cid) {
        this.cid = cid;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
