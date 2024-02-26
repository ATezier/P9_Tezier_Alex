package com.openclassrooms.medilabo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long pid;
    @NotBlank
    @Column(name = "firstname")
    String firstName;
    @NotBlank
    @Column(name = "lastname")
    String lastName;
    @NotBlank
    @Column(name = "birthdate")
    String birthdate;
    @NotBlank
    @Column(name = "genre")
    Gender gender;
    @Column(name = "address")
    String address;
    @Column(name = "phone")
    String phone;
    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name="pid", referencedColumnName = "pid")
    List<Report> reports;

    public Patient(String firstName, String lastName, String birthdate, Gender gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.gender = gender;
        this.address = null;
        this.phone = null;
        this.reports = null;
    }
    public Long getPid() {
        return pid;
    }
    public void setPid(Long pid) {
        this.pid = pid;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getBirthdate() {
        return birthdate;
    }
    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }
    public Gender getGender() {
        return gender;
    }
    public void setGender(Gender gender) {
        this.gender = gender;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
