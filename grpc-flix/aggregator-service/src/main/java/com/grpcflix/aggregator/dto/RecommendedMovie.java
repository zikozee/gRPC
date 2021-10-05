package com.grpcflix.aggregator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Ezekiel Eromosei
 * @created : 05 Oct, 2021
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendedMovie {

    private String title;
    private int year;
    private double rating;
}
