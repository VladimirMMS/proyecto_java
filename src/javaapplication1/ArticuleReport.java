/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication1;

/**
 *
 * @author vladi
 */
public class ArticuleReport {
    String nombre;
    String precio;
    String cantidad;
    String tipo;
    String itebis;
    
    public ArticuleReport(String nombre, String precio, String cantidad, String tipo, String itebis) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.tipo = tipo;
        this.itebis = itebis;
    }
    public String getnombre() {
        return nombre;
    }
    public String getprecio() {
        return precio;
    }
    public String getcantidad() {
        return cantidad;
    }
    public String gettypeart() {
        return tipo;
    }
    public String getitebis() {
        return itebis;
    }
    
}
