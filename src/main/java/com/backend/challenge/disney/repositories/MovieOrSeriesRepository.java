/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backend.challenge.disney.repositories;

import com.backend.challenge.disney.entities.MovieOrSeries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieOrSeriesRepository extends JpaRepository<MovieOrSeries, String>{
    
}
