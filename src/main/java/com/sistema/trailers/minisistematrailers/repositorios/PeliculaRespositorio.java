package com.sistema.trailers.minisistematrailers.repositorios;
import com.sistema.trailers.minisistematrailers.modelo.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeliculaRespositorio extends JpaRepository<Pelicula,Integer> {



}
