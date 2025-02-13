package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import java.util.Objects;

public record Vehiculo(String marca, String modelo, String matricula) {

    private static final String ER_MARCA = "^[a-zA-Z\\-]+$";
    private static final String ER_MATRICULA = "^\\d{4}[A-Z]{3}$";

    public Vehiculo{
        validarMarca(marca);
        validarModelo(modelo);
        validarMatricula(matricula);
    }

    private void validarMarca(String marca){
        Objects.requireNonNull(marca, "Marca no puede ser nula");
        if(!ER_MARCA.matches(marca)){
            throw new IllegalArgumentException();
        }
    }

    private void validarModelo(String modelo){
        Objects.requireNonNull(modelo, "Modelo no puede ser nulo");
    }

    private void validarMatricula(String matricula){
        Objects.requireNonNull(matricula, "Matricula no puede ser nula");
    }

}
