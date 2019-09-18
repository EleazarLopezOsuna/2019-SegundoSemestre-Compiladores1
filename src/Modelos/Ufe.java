/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import java.util.ArrayList;

/**
 *
 * @author Eleazar Lopez <Universidad de San Carlos de Guatemala>
 */
public class Ufe {
    private ArrayList<Componente> componentes;
    private String nombre;

    public Ufe(String nombre){
        componentes = new ArrayList<>();
        this.nombre = nombre;
    }

    public ArrayList<Componente> getComponentes() {
        return componentes;
    }

    public void setComponentes(ArrayList<Componente> componentes) {
        this.componentes = componentes;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
}
