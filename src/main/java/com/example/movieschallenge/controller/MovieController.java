package com.example.movieschallenge.controller;

import com.example.movieschallenge.dto.MovieDto;
import com.example.movieschallenge.model.Movie;
import com.example.movieschallenge.service.MovieService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("api/movie")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }


    @GetMapping
    public ResponseEntity<Page<Movie>> getAllMovies(@PageableDefault() Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(movieService.getAllMovies(pageable));
    }


    @GetMapping("/id/{id}")
    public ResponseEntity<Object> getMovie(@PathVariable(value = "id") UUID id){
        Optional<Movie> movieOpt = movieService.getMovie(id);
        if(!movieOpt.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(movieOpt.get());
    }

    // returns a list of movies filtered by launch date
    @GetMapping("/date/{date}")
    public ResponseEntity<Object> getMovieByDate(@PathVariable(value = "date") String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);

        Optional<List<Movie>> moviesOpt = movieService.getMoviesByDate(localDate);
        if(!moviesOpt.isPresent() || moviesOpt.get().isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movies not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(moviesOpt.get());
    }

    @PostMapping
    public ResponseEntity<Object> addMovie(@RequestBody @Valid MovieDto movieDto){
        var movie = new Movie();
        BeanUtils.copyProperties(movieDto, movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(movieService.saveMovie(movie));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteMovie(@PathVariable(value = "id") UUID id){
        Optional<Movie> movieOpt = movieService.getMovie(id);
        if(!movieOpt.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie not found");
        }

        movieService.deleteMovie(movieOpt.get());
        return ResponseEntity.status(HttpStatus.OK).body("Movie deleted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateMovie(@PathVariable(value = "id") UUID id,
                                              @RequestBody @Valid MovieDto movieDto){
        Optional<Movie> movieOpt = movieService.getMovie(id);
        if(!movieOpt.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie not found");
        }

        var movie = movieOpt.get();
        movie.setTitle(movieDto.getTitle());
        movie.setDate(movieDto.getDate());
        movie.setRank(movieDto.getRank());
        movie.setRevenue(movieDto.getRevenue());

        /*Another way of updating the object*/
        /*var movie1 = new Movie();
        BeanUtils.copyProperties(movieDto, movie1);
        movie1.setId(movieOpt.get().getId());*/

        return ResponseEntity.status(HttpStatus.OK).body(movieService.saveMovie(movie));
    }

}
