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
public class Panel extends Componente {

    private ArrayList<Componente> componentes;

    public Panel() {
        setId("");
        setPosX(0);
        setPosY(0);
        setHeight(100);
        setWidth(100);
        setColor("white");
        setBorder(0);
        setClassName("");
        componentes = new ArrayList<>();
    }

    public ArrayList<Componente> getComponentes() {
        return componentes;
    }

    public void setComponentes(ArrayList<Componente> componentes) {
        this.componentes = componentes;
    }

}
