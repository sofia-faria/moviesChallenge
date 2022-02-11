package com.example.movieschallenge.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "MOVIES")
public class Movie implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;
    @Column(nullable = false, name = "title")
    private String title;
    @Column(nullable = false, name = "date")
    private LocalDate date;
    @Column(nullable = false, name = "rank")
    private Integer rank;
    @Column(nullable = false, name = "revenue")
    private Double revenue;

    public Movie() {
    }

    public Movie(@JsonProperty("title") String title,
                 @JsonProperty("date") LocalDate date,
                 @JsonProperty("rank") Integer rank,
                 @JsonProperty("revenue") Double revenue) {
        this.title = title;
        this.date = date;
        this.rank = rank;
        this.revenue = revenue;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getDate() {
        return date;
    }

    public Integer getRank() {
        return rank;
    }

    public Double getRevenue() {
        return revenue;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public void setRevenue(Double revenue) {
        this.revenue = revenue;
    }
}
