package com.example.movieschallenge.repositories;

import com.example.movieschallenge.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

@Repository("base")
public interface MovieDao extends JpaRepository<Movie, UUID> {

    Optional<List<Movie>> findAllByDate(LocalDate date);
}
