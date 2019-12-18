package com.example.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity// 1.
public class Light {

    @Id// 2.
    @GeneratedValue
    private Long id;

    @NotNull // 3.
    private Integer level;

    @NotNull
    @Enumerated(EnumType.STRING) // 4.
    private Status status;


    @ManyToOne(optional =false)
    private Room room;
    public Light() {
    }
    public Light(Integer level, Status status, Room room) {
        this.level = level;
        this.status = status;
        this.room=room;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}