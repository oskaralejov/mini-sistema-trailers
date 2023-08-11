package com.sistema.trailers.minisistematrailers.servicio;

import com.sistema.trailers.minisistematrailers.excepciones.AlmacenException;

import com.sistema.trailers.minisistematrailers.excepciones.FileNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class AlmacenServicioImpl implements AlmacenServicio{

    @Value("ASSETS")
    private String storageLocation;

    @PostConstruct //sirve para indicar que este metodo se va a ejecutar cada vez que haya una nueva instancia de esta clase
    @Override
    public void iniciarAlmacenDeArchivo() {
        try {
            Files.createDirectories(Paths.get(storageLocation)); //indicar donde vamos a almcenar nuestras fotos
        }catch (IOException exception){

            throw new AlmacenException("Error al iniciar el almacen de archivos");
        }

    }

    @Override
    public String almacenarArchivo(MultipartFile archivo) {
        String nombreArchivo = archivo.getOriginalFilename();
        if(archivo.isEmpty()) {
            throw new AlmacenException("no se puede almacenar un archivo vacio");
        }
        try {
            InputStream inputStream = archivo.getInputStream();
            Files.copy(inputStream,Paths.get(storageLocation).resolve(nombreArchivo), StandardCopyOption.REPLACE_EXISTING); //sirve para declarar que si hay un archivo con el mismo nombre lo reemplace
        }catch (IOException exception){
            throw new AlmacenException("Error al almacenar el archivo "+ nombreArchivo,exception);
        }
        return nombreArchivo;
    }

    @Override
    public Path cargarArchivo(String nombreArchivo) {
        return Paths.get(storageLocation).resolve(nombreArchivo);
    }

    @Override
    public Resource cargarComoRecurso(String nombreArchivo) {

        try {
            Path archivo = cargarArchivo(nombreArchivo);
            Resource recurso = new UrlResource(archivo.toUri());
            if(recurso.exists() || recurso.isReadable()){
                return recurso;

            }else{
                throw new FileNotFoundException("No se pudo encontrar el archivo "+nombreArchivo);
            }
        }catch (MalformedURLException exception){
            throw new FileNotFoundException("No se pudo encontrar el archivo "+nombreArchivo, exception);
        }
    }

    @Override
    public void eliminarArchivo(String nombreArchivo) {
        Path archivo = cargarArchivo(nombreArchivo);
        try{
            FileSystemUtils.deleteRecursively(archivo);
        }catch (Exception exception){

            System.out.println(exception);

        }
    }
}
