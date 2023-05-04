/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication1;


public class Articule_List {
    private String nombre;
    private String precio;
    private String cantidad;
    private String itebis;
    private String totalt;
    public Articule_List(String nombre, String precio, String cantidad, String itebis, String totalt) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.itebis = itebis;
        this.totalt = totalt;
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
    public String getitebis() {
        return itebis;
    }
    public String gettotalt() {
        return totalt;
    }
}
