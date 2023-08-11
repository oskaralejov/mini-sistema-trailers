package com.sistema.trailers.minisistematrailers.controladores;

import com.sistema.trailers.minisistematrailers.modelo.Pelicula;
import com.sistema.trailers.minisistematrailers.repositorios.PeliculaRespositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("")
public class HomeControlador {

    @Autowired
    private PeliculaRespositorio peliculaRespositorio;

    @GetMapping("")
    public ModelAndView verPaginaDeInicio() {
        List<Pelicula> ultimasPeliculas = peliculaRespositorio.findAll(PageRequest.of(0,4, Sort.by("fechaEstreno").descending())).toList();
        return new ModelAndView("index")
                .addObject("ultimasPeliculas", ultimasPeliculas);
    }

    @GetMapping("/peliculas")
    public ModelAndView listarPeliculas(@PageableDefault(sort="fechaEstreno",direction = Sort.Direction.DESC)Pageable pageable) {

        Page<Pelicula> peliculas = peliculaRespositorio.findAll(pageable);
        return new ModelAndView("peliculas")
                .addObject("peliculas",peliculas);
    }

    @GetMapping("/peliculas/{id}")
    public ModelAndView mostrarDetallesDePelicula(@PathVariable Integer id){
        Pelicula pelicula = peliculaRespositorio.getById(id);
        return new ModelAndView("pelicula").addObject("pelicula",pelicula);
    }

}

