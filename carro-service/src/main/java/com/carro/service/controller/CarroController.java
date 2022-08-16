package com.carro.service.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carro.service.entidades.Carro;
import com.carro.service.service.CarroService;


@RestController
@RequestMapping("/carro")
public class CarroController{
    @Autowired
    private CarroService carroservice;
    @GetMapping
    public ResponseEntity<List<Carro>> listarCarro(){
        List<Carro> carros = carroservice.getAll();
        if(carros.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(carros);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Carro> obtenerCarro(@PathVariable("id") int id){
        Carro carro = carroservice.getCarroById(id);
        if(carro == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(carro);
    }

    @PostMapping
    public ResponseEntity<Carro> guardarCarro(@RequestBody Carro carro){
        Carro nuevocarro = carroservice.save(carro);
        return ResponseEntity.ok(nuevocarro);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Carro>> listarCarrosPorUsuarioId(@PathVariable("usuarioId") int id){
        List<Carro> carros = carroservice.byUsuarioId(id);
        if(carros.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(carros);
    }
}