/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

/**
 *
 * @author Eleazar Lopez <Universidad de San Carlos de Guatemala>
 */
public class Text extends Componente {

    private String contenido;

    public Text() {
        setId("");
        setPosX(0);
        setPosY(0);
        setHeight(100);
        setWidth(100);
        setColor("white");
        setBorder(0);
        setClassName("");
        contenido = "";
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

}
