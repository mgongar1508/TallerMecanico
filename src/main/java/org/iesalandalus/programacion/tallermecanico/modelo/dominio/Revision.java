package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Objects;

public class Revision {

    private static float PRECIO_HORA = 30f;
    private static float PRECIO_DIA = 10f;
    private static float PRECIO_MATERIAL = 1.5f;
    static DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private Cliente cliente;
    private Vehiculo vehiculo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int horas;
    private float precioMaterial;

    public Revision(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio){
        setCliente(cliente);
        setVehiculo(vehiculo);
        setFechaInicio(fechaInicio);
    }

    public Revision(Revision revision){
        Objects.requireNonNull(revision, "La revisión no puede ser nula.");
        this.cliente = new Cliente(revision.getCliente());
        setVehiculo(revision.getVehiculo());
        setFechaInicio(revision.getFechaInicio());
        if (revision.getFechaFin() != null) {
            setFechaFin(revision.getFechaFin());
        }
        try{
            anadirHoras(revision.getHoras());
            anadirPrecioMaterial(revision.getPrecioMaterial());
        }catch (TallerMecanicoExcepcion e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public Cliente getCliente() {
        return cliente;
    }
    public Vehiculo getVehiculo() {
        return vehiculo;
    }
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }
    public LocalDate getFechaFin() {
        return fechaFin;
    }
    public float getPrecioMaterial() {
        return precioMaterial;
    }
    public int getHoras() {
        return horas;
    }

    public void setCliente(Cliente cliente) {
        Objects.requireNonNull(cliente, "El cliente no puede ser nulo.");
        this.cliente = cliente;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        Objects.requireNonNull(vehiculo, "El vehículo no puede ser nulo.");
        this.vehiculo = vehiculo;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        Objects.requireNonNull(fechaInicio, "La fecha de inicio no puede ser nula.");
        if(fechaInicio.isAfter(LocalDate.now())){
            throw new IllegalArgumentException("La fecha de inicio no puede ser futura.");
        }
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFin(LocalDate fechaFin) {
        Objects.requireNonNull(fechaFin, "La fecha de fin no puede ser nula.");
        if(fechaFin.isEqual(fechaInicio) || fechaFin.isBefore(fechaInicio)){
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio.");
        }
        if(fechaFin.isAfter(LocalDate.now())){
            throw new IllegalArgumentException("La fecha de fin no puede ser futura.");
        }
        this.fechaFin = fechaFin;
    }

    public void anadirHoras(int horas) throws TallerMecanicoExcepcion{
        if(estaCerrada()){
            throw new TallerMecanicoExcepcion("No se puede añadir horas, ya que la revisión está cerrada.");
        }
        if(horas < 1){
            throw new IllegalArgumentException("Las horas a añadir deben ser mayores que cero.");
        }
        this.horas += horas;
    }

    public void anadirPrecioMaterial(float precio) throws TallerMecanicoExcepcion{
        if(estaCerrada()){
            throw new TallerMecanicoExcepcion("No se puede añadir precio del material, ya que la revisión está cerrada.");
        }
        if(precio < 1){
            throw new IllegalArgumentException("El precio del material a añadir debe ser mayor que cero.");
        }
        precioMaterial += precio;
    }

    public boolean estaCerrada(){
        return fechaFin != null;
    }

    public void cerrar(LocalDate fechaFin)throws TallerMecanicoExcepcion{
        if(estaCerrada()){
            throw new TallerMecanicoExcepcion("La revisión ya está cerrada.");
        }
        setFechaFin(fechaFin);
    }

    public float getPrecio(){
        return (getHoras()*PRECIO_HORA+getDias()*PRECIO_DIA+precioMaterial*PRECIO_MATERIAL);
    }

    private float getDias(){
        return ChronoUnit.DAYS.between(fechaInicio, LocalDate.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Revision revision = (Revision) o;
        return Objects.equals(cliente, revision.cliente) && Objects.equals(vehiculo, revision.vehiculo) && Objects.equals(fechaInicio, revision.fechaInicio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cliente, vehiculo, fechaInicio);
    }

    @Override
    public String toString() {
        if(estaCerrada()){
            return String.format(Locale.FRANCE, "%s - %s: (%s - %s), %d horas, %.2f € en material, %.2f € total", cliente, vehiculo, fechaInicio.format(FORMATO_FECHA), fechaFin.format(FORMATO_FECHA), horas, precioMaterial, getPrecio());
        }else{
            return String.format(Locale.FRANCE, "%s - %s: (%s - ), %d horas, %.2f € en material", cliente, vehiculo, fechaInicio.format(FORMATO_FECHA), horas, precioMaterial);
        }
    }
}
