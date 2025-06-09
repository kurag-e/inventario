package com.perfulandia.inventario.services;


import com.perfulandia.inventario.dto.InventarioDTO;
import com.perfulandia.inventario.models.Inventario;
import com.perfulandia.inventario.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class Inventarioservices {

    @Autowired
    private InventarioRepository repository;

    public InventarioDTO guardar(InventarioDTO dto) {
        Inventario inventario = toEntity(dto);
        Inventario saved = repository.save(inventario);
        return toDTO(saved);
    }

    public List<InventarioDTO> listar() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<InventarioDTO> obtenerPorId(Long id) {
        return repository.findById(id)
                .map(this::toDTO);
    }

    public Optional<InventarioDTO> actualizar(Long id, InventarioDTO dto) {
        return repository.findById(id).map(inventario -> {
            inventario.setUbicacion(dto.getUbicacion());
            return toDTO(repository.save(inventario));
        });
    }

    public boolean eliminar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    // Métodos auxiliares
    private InventarioDTO toDTO(Inventario inventario) {
        InventarioDTO dto = new InventarioDTO();
        dto.setId(inventario.getId());
        dto.setUbicacion(inventario.getUbicacion());
        return dto;
    }

    private Inventario toEntity(InventarioDTO dto) {
        Inventario inventario = new Inventario();
        inventario.setId(dto.getId());
        inventario.setUbicacion(dto.getUbicacion());
        return inventario;
    }
}
