package com.openclassrooms.medilabo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Table(name = "reports")
@Entity
@NoArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long rid;
    @Column(name = "pid")
    Long pid;
    @Column(name = "date")
    @NotBlank
    Timestamp date;
    @Column(name = "description")
    @NotBlank
    String description;

    public Report(Long pid, Timestamp date, String description) {
        this.pid = pid;
        this.date = date;
        this.description = description;
    }
    public Long getRid() {
        return rid;
    }
    public void setRid(Long rid) {
        this.rid = rid;
    }
    public Long getPid() {
        return pid;
    }
    public void setPid(Long pid) {
        this.pid = pid;
    }
    public Timestamp getDate() {
        return date;
    }
    public void setDate(Timestamp date) {
        this.date = date;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
