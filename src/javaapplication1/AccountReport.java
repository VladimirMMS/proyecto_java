/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication1;

public class AccountReport {
    private String company;
    private String direccion;
    private String fecha;
    private String total;
    private String empleado;
    private String deuda;
    private String tipo;
    public AccountReport(String company, String direccion, String fecha, String total, String empleado, String deuda, String tipo) {
       this.company = company;
       this.direccion = direccion;
       this.fecha=fecha;
       this.total=total;
       this.empleado=empleado;
       this.deuda=deuda;
       this.tipo=tipo;
    }
    public String getcompany() {
        return company;
    }
    public String getdireccion() {
        return direccion;
    }
    public String getfecha() {
        return fecha;
    }
    public String gettotal() {
        return total;
    }
    public String getempleado() {
        return empleado;
    }
    public String getdeuda() {
        return deuda;
    }
    public String gettipo() {
        return tipo;
    }
    
}
