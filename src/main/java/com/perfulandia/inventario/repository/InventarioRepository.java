package com.perfulandia.inventario.repository;

import com.perfulandia.inventario.models.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {
}
