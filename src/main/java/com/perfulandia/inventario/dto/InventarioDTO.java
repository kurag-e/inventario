package com.perfulandia.inventario.dto;

import org.springframework.hateoas.RepresentationModel;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventarioDTO extends RepresentationModel<InventarioDTO> {
    private Integer id;
    private String ubicacion;
}
