package com.example.movieschallenge.service;

import com.example.movieschallenge.repositories.MovieDao;
import com.example.movieschallenge.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;

@Service
public class MovieService {

    private final MovieDao movieDao;

    @Autowired
    public MovieService(@Qualifier("base") MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    public Page<Movie> getAllMovies(Pageable pageable){
        return movieDao.findAll(pageable);
    }

    public Optional<Movie> getMovie(UUID id) {
        return movieDao.findById(id);
    }

    public Optional<List<Movie>> getMoviesByDate(LocalDate date) {
        return movieDao.findAllByDate(date);
    }

    @Transactional
    public Movie saveMovie(Movie movie){
        return movieDao.save(movie);
    }

    @Transactional
    public void deleteMovie(Movie movie){
        movieDao.delete(movie);
    }
}
