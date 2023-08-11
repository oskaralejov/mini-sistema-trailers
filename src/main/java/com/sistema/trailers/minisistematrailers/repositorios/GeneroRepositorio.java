package com.sistema.trailers.minisistematrailers.repositorios;

import com.sistema.trailers.minisistematrailers.modelo.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneroRepositorio extends JpaRepository<Genero,Integer> {


}
