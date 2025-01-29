package com.example.movieApi.service;

import com.example.movieApi.dto.MovieDto;
import com.example.movieApi.dto.MovieResponse;
import com.example.movieApi.entities.Movie;
import com.example.movieApi.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MovieServiceImpl implements  MovieService{

    private final MovieRepository movieRepository;
    private final FileService fileService;

    public MovieServiceImpl(MovieRepository movieRepository, FileService fileService){
        this.movieRepository = movieRepository;
        this.fileService = fileService;
    }

    @Value("${project.poster}")
    private String path;

    @Value(("${base.url}"))
    private String baseUrl;

    @Override
    public MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException {
        Movie movie = new Movie();

        //upload file
        String uploadedFile = fileService.uploadFile(path,file);

        //set the field name as poster
        movieDto.setPoster(uploadedFile);

        //to save we need to map DTO to movie object
        // Update movie fields
        movie.setTitle(movieDto.getTitle());
        movie.setDirector(movieDto.getDirector());
        movie.setStudio(movieDto.getStudio());
        movie.setMovieCast(movieDto.getMovieCast());
        movie.setReleaseYear(movieDto.getReleaseYear());
        movie.setPoster(movieDto.getPoster());

        //save the movie object
        Movie savedMovie = movieRepository.save(movie);

        //generate poster url
        String posterUrl = baseUrl + "/file/" + uploadedFile;

        //map movie object to DTO object and return
        MovieDto response = new MovieDto(savedMovie.getMovieId(),savedMovie.getTitle(),savedMovie.getDirector(),savedMovie.getStudio(),savedMovie.getMovieCast(),savedMovie.getReleaseYear(),savedMovie.getPoster(),posterUrl);

        return response;
    }

    @Override
    public MovieDto getMovie(Integer movieId) {
        //to check the data in DB
        //if exists fetch the data in movie format
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new RuntimeException("Movie not found"));


        //generate posterURL
        String posterUrl = baseUrl + "/file/" + movie.getPoster();

        //map movie to movieDTO
        MovieDto response = new MovieDto(movie.getMovieId(),movie.getTitle(),movie.getDirector(),movie.getStudio(),movie.getMovieCast(),movie.getReleaseYear(),movie.getPoster(),posterUrl);

        return response;
    }

    @Override
    public List<MovieDto> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();

        List<MovieDto> movieDtos = new ArrayList<>();

        for(Movie movie : movies) {
            String posterUrl = baseUrl + "/file/" + movie.getPoster();
            MovieDto movieDto = new MovieDto(movie.getMovieId(),movie.getTitle(),movie.getDirector(),movie.getStudio(),movie.getMovieCast(),movie.getReleaseYear(),movie.getPoster(),posterUrl);
            movieDtos.add(movieDto);
        }
        return movieDtos;
    }

    @Override
    public MovieDto updateMovie(Integer movieId, MovieDto movieDto, MultipartFile file) throws IOException {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new RuntimeException("Movie not found"));

        //upload file
        String uploadedFile = fileService.uploadFile(path,file);

        //set the field name as poster
        movieDto.setPoster(uploadedFile);

        //to save we need to map DTO to movie object
        // Update movie fields
        movie.setMovieId(movie.getMovieId());
        movie.setTitle(movieDto.getTitle());
        movie.setDirector(movieDto.getDirector());
        movie.setStudio(movieDto.getStudio());
        movie.setMovieCast(movieDto.getMovieCast());
        movie.setReleaseYear(movieDto.getReleaseYear());
        movie.setPoster(movieDto.getPoster());

        //save the movie object
        Movie savedMovie = movieRepository.save(movie);

        //generate poster url
        String posterUrl = baseUrl + "/file/" + uploadedFile;

        //map movie object to DTO object and return
        MovieDto response = new MovieDto(savedMovie.getMovieId(),savedMovie.getTitle(),savedMovie.getDirector(),savedMovie.getStudio(),savedMovie.getMovieCast(),savedMovie.getReleaseYear(),savedMovie.getPoster(),posterUrl);

        return response;
    }

    @Override
    public String deleteMovie(Integer movieId) throws IOException {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new RuntimeException("Movie not found"));
        Files.deleteIfExists(Paths.get(path + File.separator + movie.getPoster()));
        movieRepository.deleteById(movieId);
        return "Movie is deleted with ID : " + movieId;
    }

    @Override
    public MovieResponse getAllMoviesWithPagination(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Page<Movie> moviePages = movieRepository.findAll(pageable);
        List<Movie> movies = moviePages.getContent();

        List<MovieDto> movieDtos = new ArrayList<>();

        for(Movie movie : movies) {
            String posterUrl = baseUrl + "/file/" + movie.getPoster();
            MovieDto movieDto = new MovieDto(movie.getMovieId(),movie.getTitle(),movie.getDirector(),movie.getStudio(),movie.getMovieCast(),movie.getReleaseYear(),movie.getPoster(),posterUrl);
            movieDtos.add(movieDto);
        }

        return new MovieResponse(movieDtos,pageNumber,pageSize, (int) moviePages.getTotalElements(),moviePages.getTotalPages(),moviePages.isLast());
    }

    @Override
    public MovieResponse getAllMoviesWithPaginationAndSorting(Integer pageNumber, Integer pageSize, String sortBy, String dir) {

        Sort sort = dir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Movie> moviePages = movieRepository.findAll(pageable);
        List<Movie> movies = moviePages.getContent();

        List<MovieDto> movieDtos = new ArrayList<>();

        for(Movie movie : movies) {
            String posterUrl = baseUrl + "/file/" + movie.getPoster();
            MovieDto movieDto = new MovieDto(movie.getMovieId(),movie.getTitle(),movie.getDirector(),movie.getStudio(),movie.getMovieCast(),movie.getReleaseYear(),movie.getPoster(),posterUrl);
            movieDtos.add(movieDto);
        }

        return new MovieResponse(movieDtos,pageNumber,pageSize, (int) moviePages.getTotalElements(),moviePages.getTotalPages(),moviePages.isLast());
    }


}
