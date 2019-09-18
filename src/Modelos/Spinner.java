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
public class Spinner extends Componente {

    private int min;
    private int max;

    public Spinner() {
        setId(null);
        setPosX(0);
        setPosY(0);
        setHeight(100);
        setWidth(100);
        setColor("white");
        setBorder(0);
        setClassName(null);
        min = -100;
        max = 100;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

}
