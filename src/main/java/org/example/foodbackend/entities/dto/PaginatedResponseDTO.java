package org.example.foodbackend.entities.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedResponseDTO<T> {
    private List<T> data;
    private int currentPage;
    private int totalPages;
    private long totalItems;
}
