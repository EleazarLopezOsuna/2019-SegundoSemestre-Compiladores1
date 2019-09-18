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
public class Image extends Componente{

    private String fuente;
    
    public Image() {
        setId(null);
        setPosX(0);
        setPosY(0);
        setHeight(100);
        setWidth(100);
        setColor("white");
        setBorder(0);
        setClassName(null);
        fuente = "";
    }

    public String getFuente() {
        return fuente;
    }

    public void setFuente(String fuente) {
        this.fuente = fuente;
    }
    
}
