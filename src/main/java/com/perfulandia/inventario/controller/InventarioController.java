package com.perfulandia.inventario.controller;

import com.perfulandia.inventario.dto.InventarioDTO;
import com.perfulandia.inventario.services.Inventarioservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/inventario")
@CrossOrigin(origins = "*")
public class InventarioController {

    @Autowired
    private Inventarioservices service;


    @PostMapping
    public InventarioDTO crear(@RequestBody InventarioDTO dto) {
        InventarioDTO creado = service.guardar(dto);
        creado.add(linkTo(methodOn(InventarioController.class).obtenerHATEOAS(creado.getId())).withSelfRel());
        creado.add(linkTo(methodOn(InventarioController.class).obtenerTodosHATEOAS()).withRel("todos"));
        return creado;
    }

    
    @GetMapping("/hateoas/{id}")
    public InventarioDTO obtenerHATEOAS(@PathVariable Integer id) {
        Long idLong = id.longValue();
        InventarioDTO dto = service.obtenerPorId(idLong).orElse(null);
        if (dto != null) {
            dto.add(linkTo(methodOn(InventarioController.class).obtenerHATEOAS(id)).withSelfRel());
            dto.add(linkTo(methodOn(InventarioController.class).obtenerTodosHATEOAS()).withRel("todos"));
            dto.add(linkTo(methodOn(InventarioController.class).eliminar(id)).withRel("eliminar"));
        }
        return dto;
    }

    @GetMapping("/hateoas")
    public List<InventarioDTO> obtenerTodosHATEOAS() {
        List<InventarioDTO> lista = service.listar();
        for (InventarioDTO dto : lista) {
            dto.add(linkTo(methodOn(InventarioController.class).obtenerHATEOAS(dto.getId().intValue())).withSelfRel());
        }
        return lista;
    }

    @GetMapping("/{id}")
    public InventarioDTO obtener(@PathVariable Integer id) {
        Long idLong = id.longValue();
        return service.obtenerPorId(idLong).orElse(null);
    }


    @PutMapping("/{id}")
    public InventarioDTO actualizar(@PathVariable Integer id, @RequestBody InventarioDTO dto) {
        Long idLong = id.longValue();
        InventarioDTO actualizado = service.actualizar(idLong, dto).orElse(null);
        if (actualizado != null) {
            actualizado.add(linkTo(methodOn(InventarioController.class).obtenerHATEOAS(id)).withSelfRel());
            actualizado.add(linkTo(methodOn(InventarioController.class).obtenerTodosHATEOAS()).withRel("todos"));
        }
        return actualizado;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        Long idLong = id.longValue();
        boolean eliminado = service.eliminar(idLong);
        return eliminado
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
