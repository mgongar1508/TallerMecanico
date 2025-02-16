package org.iesalandalus.programacion.tallermecanico.modelo.negocio;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Clientes {
    private List<Cliente> coleccionClientes;

    public Clientes(){
        coleccionClientes = new ArrayList<>();
    }

    public List<Cliente> get(){
        return coleccionClientes;
    }

    public void insertar(Cliente cliente) throws TallerMecanicoExcepcion{
        Objects.requireNonNull(cliente, "No se puede insertar un cliente nulo.");
        if(coleccionClientes.contains(cliente)){
            throw new TallerMecanicoExcepcion("Ya existe un cliente con ese DNI.");
        }
        coleccionClientes.add(cliente);
    }

    public boolean modificar(Cliente cliente, String nombre, String telefono) throws TallerMecanicoExcepcion {
        boolean flag = false;
        Objects.requireNonNull(cliente, "No se puede modificar un cliente nulo.");
        Cliente clienteEncontrado = buscar(cliente);
        if (clienteEncontrado == null){
            throw new TallerMecanicoExcepcion("No existe ningún cliente con ese DNI.");
        }
        if (nombre != null && !nombre.isBlank()){
            clienteEncontrado.setNombre(nombre);
            flag = true;
        }
        if(telefono != null && !telefono.isBlank()){
            clienteEncontrado.setTelefono(telefono);
            flag = true;
        }
        return flag;
    }

    public Cliente buscar(Cliente cliente){
        Objects.requireNonNull(cliente, "No se puede buscar un cliente nulo.");
        if(coleccionClientes.contains(cliente)){
            return cliente;
        }else{
            return null;
        }
    }

    public void borrar(Cliente cliente) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(cliente, "No se puede borrar un cliente nulo.");
        if (coleccionClientes.contains(cliente)){
            coleccionClientes.remove(cliente);
        } else {
            throw new TallerMecanicoExcepcion("No existe ningún cliente con ese DNI.");
        }
    }
}
