package com.example.movieApi.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
public class MovieDto {

    private Integer movieId;

    @NotBlank(message = "It should not be empty")
    private String title;

    @NotBlank(message = "It should not be empty")
    private String director;

    @NotBlank(message = "It should not be empty")
    private String studio;

    private Set<String> movieCast;

    private Integer releaseYear;

    @NotBlank(message = "It should not be empty")
    private String poster;

    @NotBlank(message = "It should not be empty")
    private String posterUrl;

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

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public MovieDto(Integer movieId, String title, String director, String studio, Set<String> movieCast, Integer releaseYear, String poster, String posterUrl) {
        this.movieId = movieId;
        this.title = title;
        this.director = director;
        this.studio = studio;
        this.movieCast = movieCast;
        this.releaseYear = releaseYear;
        this.poster = poster;
        this.posterUrl = posterUrl;
    }

    public MovieDto() {
        // Default constructor for DTO
    }
}
