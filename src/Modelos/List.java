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
public class List extends Componente {

    private ArrayList<String> items;
    int defecto;

    public List() {
        setId(null);
        setPosX(0);
        setPosY(0);
        setHeight(100);
        setWidth(100);
        setColor("white");
        setBorder(0);
        setClassName(null);
        items = new ArrayList<>();
        defecto = -1;
    }

    public ArrayList<String> getItems() {
        return items;
    }

    public void setItems(ArrayList<String> items) {
        this.items = items;
    }

    public int getDefecto() {
        return defecto;
    }

    public void setDefecto(int defecto) {
        this.defecto = defecto;
    }

}
