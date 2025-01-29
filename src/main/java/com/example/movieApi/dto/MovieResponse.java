package com.example.movieApi.dto;

import java.util.List;

public record MovieResponse(List<MovieDto> movieDtos, Integer pageNumber, Integer pageSize, int totalElements, int totalPages, boolean isLast)  {
}
