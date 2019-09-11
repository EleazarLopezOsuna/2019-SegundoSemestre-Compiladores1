/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Nodos;

import java.util.ArrayList;

/**
 *
 * @author Eleazar Lopez <Universidad de San Carlos de Guatemala>
 */
public class NodoSintactico {
    private String nombre;
    private ArrayList<NodoSintactico> hijos;
    private Object valor;
    private int numNodo;

    public NodoSintactico(String nombre) {
        this.nombre = nombre;
        hijos = new ArrayList<>();
        setNumNodo(0);
    }

    public void addHijo(NodoSintactico hijo){
        hijos.add(hijo);
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<NodoSintactico> getHijos() {
        return hijos;
    }

    public void setHijos(ArrayList<NodoSintactico> hijos) {
        this.hijos = hijos;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

    public int getNumNodo() {
        return numNodo;
    }

    public void setNumNodo(int numNodo) {
        this.numNodo = numNodo;
    }
    
    
}
