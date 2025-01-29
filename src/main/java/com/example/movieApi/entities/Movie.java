package com.example.movieApi.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movieId;

    @Column(nullable = false, length = 200)
    @NotBlank(message = "It should not be empty")
    private String title;

    @Column(nullable = false, length = 200)
    @NotBlank(message = "It should not be empty")
    private String director;

    @Column(nullable = false, length = 200)
    @NotBlank(message = "It should not be empty")
    private String studio;

    @ElementCollection
    @CollectionTable(name = "movie_cast")
    private Set<String> movieCast;

    @Column(nullable = false, length = 200)
    private Integer releaseYear;

    @Column(nullable = false, length = 200)
    @NotBlank(message = "It should not be empty")
    private String poster;

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public Set<String> getMovieCast() {
        return movieCast;
    }

    public void setMovieCast(Set<String> movieCast) {
        this.movieCast = movieCast;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Movie(Integer movieId, String title, String director, String studio, Set<String> movieCast, Integer releaseYear, String poster) {
        this.movieId = movieId;
        this.title = title;
        this.director = director;
        this.studio = studio;
        this.movieCast = movieCast;
        this.releaseYear = releaseYear;
        this.poster = poster;
    }

    public Movie() {

    }
}
