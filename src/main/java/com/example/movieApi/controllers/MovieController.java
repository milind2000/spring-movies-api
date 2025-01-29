package com.example.movieApi.controllers;

import com.example.movieApi.dto.MovieDto;
import com.example.movieApi.dto.MovieResponse;
import com.example.movieApi.entities.Movie;
import com.example.movieApi.service.MovieService;
import com.example.movieApi.utils.AppConstant;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/movie")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService){
        this.movieService = movieService;
    }

    @GetMapping("/")
    public ResponseEntity<List<MovieDto>> getAllMoviesHandler()  {
        return new ResponseEntity<>(movieService.getAllMovies(), HttpStatus.FOUND);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<MovieDto> addMovieHandler(@RequestPart MultipartFile file, @RequestPart String movieDtoString) throws IOException {
        MovieDto dto = convertToMovieDTO(movieDtoString);
        return new ResponseEntity<>(movieService.addMovie(dto,file), HttpStatus.CREATED);
    }

    @PutMapping("/update/{movieId}")
    public ResponseEntity<MovieDto> updateMovieHandler(@PathVariable Integer movieId, @RequestPart MultipartFile file, @RequestPart String movieDtoString) throws IOException {
        MovieDto dto = convertToMovieDTO(movieDtoString);
        return new ResponseEntity<>(movieService.updateMovie(movieId,dto,file), HttpStatus.OK);
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<MovieDto> getMovieHandler(@PathVariable Integer movieId)  {
        return new ResponseEntity<>(movieService.getMovie(movieId), HttpStatus.FOUND);
    }

    @DeleteMapping("/delete/{movieId}")
    public ResponseEntity<String> deleteMovieHandler(@PathVariable Integer movieId) throws IOException {
        return new ResponseEntity<>(movieService.deleteMovie(movieId), HttpStatus.GONE);
    }
    
    @GetMapping ("/allMoviesPage")
    public ResponseEntity<MovieResponse> getMoviesWithPagination(
            @RequestParam(defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(defaultValue = AppConstant.PAGE_SIZE, required = false ) Integer pageSize) {
        return ResponseEntity.ok(movieService.getAllMoviesWithPagination(pageNumber,pageSize));
    }

    @GetMapping ("/allMoviesPageSort")
    public ResponseEntity<MovieResponse> getMoviesWithPaginationSorting(
            @RequestParam(defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(defaultValue = AppConstant.PAGE_SIZE, required = false ) Integer pageSize,
            @RequestParam(defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
            @RequestParam(defaultValue = AppConstant.SORT_DIR, required = false) String dir
            ) {
        return ResponseEntity.ok(movieService.getAllMoviesWithPaginationAndSorting(pageNumber,pageSize,sortBy,dir));
    }

    private MovieDto convertToMovieDTO(String movieDToObj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(movieDToObj,MovieDto.class);
    }

}
