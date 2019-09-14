/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1_compiladores_segundosemestre_2019;

import Modelos.Entorno;
import Modelos.Expresion;
import Modelos.Simbolo;
import Nodos.NodoSintactico;

/**
 *
 * @author Eleazar Lopez <Universidad de San Carlos de Guatemala>
 */
public class Ejecutor {

    public void Ejecutar(NodoSintactico raiz) {
        recorrer(raiz, null);
    }

    private void recorrer(NodoSintactico raiz, Entorno ent) {
        Expresion resultado;
        Entorno nuevo;
        boolean boleano;
        switch (raiz.getNombre()) {
            case "INICIO":
                nuevo = new Entorno(ent);
                recorrer(raiz.getHijos().get(0), nuevo);
                break;
            case "OPCION":
            case "FUNCION":
            case "SINO":
            case "SINO-SI":
                recorrer(raiz.getHijos().get(0), ent);
                break;
            case "OPCIONES":
            case "ASIGNACIONES":
            case "FUNCIONES":
                recorrer(raiz.getHijos().get(0), ent);
                recorrer(raiz.getHijos().get(1), ent);
                break;
            case "E":
                Expresion hola = resolverExpresion(raiz.getHijos().get(0), ent);
                break;
            case "SDA":
            case "ADA":
            case "AD":
            case "SD":
                //SDA - Simple Declaracion Asignacion
                //ADA - Arreglo Declaracion Asignacion
                //AD - Arreglo Declaracion
                //SD - Simple Declaracion
                EjecutarDeclaracion(raiz, ent);
                break;
            case "SI":
                resultado = resolverExpresion(raiz.getHijos().get(0), ent);
                switch (resultado.tipo) {
                    case booleano:
                        boleano = (boolean) resultado.valor;
                        if (boleano) {
                            nuevo = new Entorno(ent);
                            recorrer(raiz.getHijos().get(1), nuevo);
                        }
                        break;
                    default:
                        //AGREGAR EL ERROR
                        System.out.println(resultado.valor);
                        break;
                }
                break;
            case "SI-SINO":
                resultado = resolverExpresion(raiz.getHijos().get(0), ent);
                switch (resultado.tipo) {
                    case booleano:
                        boleano = (boolean) resultado.valor;
                        if (boleano) {
                            nuevo = new Entorno(ent);
                            recorrer(raiz.getHijos().get(1), nuevo);
                        } else {
                            nuevo = new Entorno(ent);
                            recorrer(raiz.getHijos().get(2), nuevo);
                        }
                        break;
                    default:
                        //AGREGAR EL ERROR
                        System.out.println(resultado.valor);
                        break;
                }
                break;
            case "IMPRIMIR":
                resultado = resolverExpresion(raiz.getHijos().get(0), ent);
                if (resultado.tipo != Simbolo.EnumTipo.error) {
                    System.out.println(resultado.valor);
                } else {
                    //AGREGAR EL ERROR
                    System.out.println(resultado.valor);
                }
                break;
            case "REPETIR":
                if (raiz.getHijos().size() == 2) {
                    nuevo = new Entorno(ent);
                    EjecutarRepetir(raiz, nuevo);
                }
                break;
            case "MIENTRAS":
                if (raiz.getHijos().size() == 2) {
                    nuevo = new Entorno(ent);
                    EjecutarMientras(raiz, nuevo);
                }
                break;
            case "REASIGNACION":
                EjecutarReasignacion(raiz, ent);
                break;
        }
    }

    private void EjecutarReasignacion(NodoSintactico raiz, Entorno ent) {
        String nombre = (String) raiz.getHijos().get(0).getValor();
        Simbolo sim = ent.buscar(nombre, raiz.getLinea(), raiz.getColumna());
        if (sim != null) {
            Expresion resultado = resolverExpresion(raiz.getHijos().get(1), ent);
            if (resultado.tipo != Simbolo.EnumTipo.error) {
                sim = new Simbolo(resultado.tipo, resultado.valor);
                if(!ent.modificar(nombre, sim)){
                    //AGREGAR EL ERROR
                    System.out.println("Error al reasignar la variable");
                }
            } else {
                //AGREGAR EL ERROR
            }
        } else {
            //AGREGAR EL ERROR
            System.out.println("La variable no existe");
        }
    }

    private void EjecutarMientras(NodoSintactico raiz, Entorno ent) {
        Expresion resultado = resolverExpresion(raiz.getHijos().get(0), ent);
        switch (resultado.tipo) {
            case entero:
                int contador = (int) resultado.valor;
                while (contador > 0) {
                    recorrer(raiz.getHijos().get(1), ent);
                    contador--;
                }
                break;
            case error:
                //AGREGAR EL ERROR
                System.out.println(resultado.valor);
                break;
            default:
                //AGREGAR EL ERROR
                System.out.println("Se esperaba tipo entero, tipo " + resultado.tipo + " encontrado");
                break;
        }
    }

    private void EjecutarRepetir(NodoSintactico raiz, Entorno ent) {
        Expresion resultado = resolverExpresion(raiz.getHijos().get(0), ent);
        switch (resultado.tipo) {
            case entero:
                int contador = (int) resultado.valor;
                while (contador > 0) {
                    recorrer(raiz.getHijos().get(1), ent);
                    contador--;
                }
                break;
            case error:
                //AGREGAR EL ERROR
                System.out.println(resultado.valor);
                break;
            default:
                //AGREGAR EL ERROR
                System.out.println("Se esperaba tipo entero, tipo " + resultado.tipo + " encontrado");
                break;
        }
    }

    private void EjecutarDeclaracion(NodoSintactico raiz, Entorno ent) {
        Expresion resultado;
        Expresion index;
        Simbolo nuevo;
        switch (raiz.getNombre()) {
            case "SDA":
                resultado = resolverExpresion(raiz.getHijos().get(1), ent);
                if (resultado.tipo != Simbolo.EnumTipo.error) {
                    nuevo = new Simbolo(resultado.tipo, resultado.valor);
                    if (!ent.insertar(String.valueOf(raiz.getHijos().get(0).getValor()), nuevo, raiz.getLinea(), raiz.getColumna())) {
                        //AGREGAR EL ERROR
                        System.out.println("Error la variable ya existe");
                    }
                } else {
                    //AGREGAR EL ERROR
                    System.out.println(resultado.valor);
                }
                break;
            case "SD":
                nuevo = new Simbolo(Simbolo.EnumTipo.vacio, null);
                if (!ent.insertar(String.valueOf(raiz.getHijos().get(0).getValor()), nuevo, raiz.getLinea(), raiz.getColumna())) {
                    //AGREGAR EL ERROR
                    System.out.println("Error la variable ya existe");
                }
                break;
            case "ADA":
                if (!raiz.getHijos().get(1).getNombre().equals("DATARR")) {
                    index = resolverExpresion(raiz.getHijos().get(1), ent);
                    switch (index.tipo) {
                        case entero:
                            resultado = resolverExpresion(raiz.getHijos().get(2), ent);
                            int repeticiones = (int) index.valor - 1;
                            nuevo = new Simbolo(index.tipo, index.valor);
                            if (!ent.insertar(String.valueOf(raiz.getHijos().get(0).getValor()), nuevo, raiz.getLinea(), raiz.getColumna())) {
                                //AGREGAR EL ERROR
                                System.out.println("Error la variable ya existe");
                                break;
                            } else {
                                while (0 <= repeticiones) {
                                    String nombreVariable = repeticiones + "_" + String.valueOf(raiz.getHijos().get(0).getValor());
                                    nuevo = new Simbolo(resultado.tipo, resultado.valor);
                                    ent.insertar(nombreVariable, nuevo, raiz.getLinea(), raiz.getColumna());
                                    repeticiones--;
                                }
                            }
                            break;
                        case error:
                            //AGREGAR ERROR
                            System.out.println(index.valor);
                            break;
                        default:
                            //AGREGAR ERROR
                            System.out.println("Se esperaba indice entero");
                            break;
                    }
                } else {
                    NodoSintactico elementos = raiz.getHijos().get(1);
                    int hijos = elementos.getHijos().size();
                    int error = 0;
                    nuevo = new Simbolo(Simbolo.EnumTipo.entero, elementos.getHijos().size());
                    if (!ent.insertar(String.valueOf(raiz.getHijos().get(0).getValor()), nuevo, raiz.getLinea(), raiz.getColumna())) {
                        //AGREGAR EL ERROR
                        System.out.println("Error la variable ya existe");
                        break;
                    } else {
                        for (int i = 0; i < hijos; i++) {
                            resultado = resolverExpresion(elementos.getHijos().get(i), ent);
                            if (resultado == null) {
                                error++;
                                break;
                            } else if (resultado.tipo == Simbolo.EnumTipo.error) {
                                error++;
                                break;
                            }
                        }
                        if (error == 0) {
                            for (int i = 0; i < hijos; i++) {
                                resultado = resolverExpresion(elementos.getHijos().get(i), ent);
                                String nombreVariable = i + "_" + String.valueOf(raiz.getHijos().get(0).getValor());
                                nuevo = new Simbolo(resultado.tipo, resultado.valor);
                                ent.insertar(nombreVariable, nuevo, raiz.getLinea(), raiz.getColumna());
                            }
                        } else {
                            //AGREGAR EL ERROR
                            System.out.println("La variable no ha sido inicializada");
                        }
                    }

                }
                break;
        }
    }

    private Expresion resolverExpresion(NodoSintactico raiz, Entorno ent) {
        switch (raiz.getNombre()) {
            case "ENTERO":
                return new Expresion(Simbolo.EnumTipo.entero, raiz.getValor());
            case "DOBLE":
                return new Expresion(Simbolo.EnumTipo.doble, raiz.getValor());
            case "CADENA":
                return new Expresion(Simbolo.EnumTipo.cadena, raiz.getValor());
            case "ID":
                if (raiz.getHijos().isEmpty()) {
                    Simbolo sim = ent.buscar(String.valueOf(raiz.getValor()), raiz.getLinea(), raiz.getColumna());
                    if (sim != null) {
                        if (sim.tipo != Simbolo.EnumTipo.vacio) {
                            return new Expresion(sim.tipo, sim.valor);
                        } else {
                            return new Expresion(Simbolo.EnumTipo.error, "Variable Vacia");
                        }
                    }
                    return new Expresion(Simbolo.EnumTipo.error, "La Variable no existe");
                } else {
                    Expresion index = resolverExpresion(raiz.getHijos().get(0), ent);
                    if (index.tipo == Simbolo.EnumTipo.entero) {
                        String nombreVariable = String.valueOf(index.valor) + "_" + String.valueOf(raiz.getValor());
                        Simbolo var = ent.buscar(String.valueOf(raiz.getValor()), raiz.getLinea(), raiz.getColumna());
                        if (var != null) {
                            int maximo = (int) var.valor;
                            int indice = (int) index.valor;
                            if (indice < maximo) {
                                if (indice >= 0) {
                                    Simbolo sim = ent.buscar(nombreVariable, raiz.getLinea(), raiz.getColumna());
                                    if (sim != null) {
                                        return new Expresion(sim.tipo, sim.valor);
                                    } else {
                                        return new Expresion(Simbolo.EnumTipo.error, "Variable Vacia");
                                    }
                                } else {
                                    return new Expresion(Simbolo.EnumTipo.error, "Indice debe ser un Entero Positivo");
                                }
                            } else {
                                return new Expresion(Simbolo.EnumTipo.error, "Indice fuera del limite");
                            }
                        } else {
                            return new Expresion(Simbolo.EnumTipo.error, "La Variable no Existe");
                        }
                    } else {
                        return new Expresion(Simbolo.EnumTipo.error, "Se esperaba un indice tipo entero, tipo " + index.tipo + " encontrado");
                    }
                }
            case "CARACTER":
                return new Expresion(Simbolo.EnumTipo.caracter, raiz.getValor());
            case "BOOLEANO":
                return new Expresion(Simbolo.EnumTipo.booleano, raiz.getValor());
            case "+":
                return OperarSuma(resolverExpresion(raiz.getHijos().get(0), ent), resolverExpresion(raiz.getHijos().get(1), ent));
            case "-":
                return OperarResta(resolverExpresion(raiz.getHijos().get(0), ent), resolverExpresion(raiz.getHijos().get(1), ent));
            case "*":
                return OperarMultiplicacion(resolverExpresion(raiz.getHijos().get(0), ent), resolverExpresion(raiz.getHijos().get(1), ent));
            case "/":
                return OperarDivision(resolverExpresion(raiz.getHijos().get(0), ent), resolverExpresion(raiz.getHijos().get(1), ent));
            case "NEGATIVO":
                return OperarNegativo(resolverExpresion(raiz.getHijos().get(0), ent));
            case "pow":
                return OperarPotencia(resolverExpresion(raiz.getHijos().get(0), ent), resolverExpresion(raiz.getHijos().get(1), ent));
            case "||":
                return OperarOrLogico(resolverExpresion(raiz.getHijos().get(0), ent), resolverExpresion(raiz.getHijos().get(1), ent));
            case "&&":
                return OperarAndLogico(resolverExpresion(raiz.getHijos().get(0), ent), resolverExpresion(raiz.getHijos().get(1), ent));
            case "!":
                return OperarNotLogico(resolverExpresion(raiz.getHijos().get(0), ent));
            case "^":
                return OperarXorLogico(resolverExpresion(raiz.getHijos().get(0), ent), resolverExpresion(raiz.getHijos().get(1), ent));
            case ">":
                return OperarMayorQue(resolverExpresion(raiz.getHijos().get(0), ent), resolverExpresion(raiz.getHijos().get(1), ent));
            case "<":
                return OperarMenorQue(resolverExpresion(raiz.getHijos().get(0), ent), resolverExpresion(raiz.getHijos().get(1), ent));
            case ">=":
                return OperarMayorIgualQue(resolverExpresion(raiz.getHijos().get(0), ent), resolverExpresion(raiz.getHijos().get(1), ent));
            case "<=":
                return OperarMenorIgualQue(resolverExpresion(raiz.getHijos().get(0), ent), resolverExpresion(raiz.getHijos().get(1), ent));
            case "==":
                return OperarIgual(resolverExpresion(raiz.getHijos().get(0), ent), resolverExpresion(raiz.getHijos().get(1), ent));
            case "!=":
                return OperarDiferente(resolverExpresion(raiz.getHijos().get(0), ent), resolverExpresion(raiz.getHijos().get(1), ent));
        }
        return new Expresion(Simbolo.EnumTipo.error, "ERROR");
    }

    private Expresion OperarSuma(Expresion expresion1, Expresion expresion2) {
        switch (expresion1.tipo) {
            case cadena:
                //Cadena (Entero || Doble || Caracter || Booleano || Cadena)
                return new Expresion(Simbolo.EnumTipo.cadena, String.valueOf(expresion1.valor) + String.valueOf(expresion2.valor));
            case doble:
                switch (expresion2.tipo) {
                    case cadena:
                        //Doble Cadena
                        return new Expresion(Simbolo.EnumTipo.cadena, String.valueOf(expresion1.valor) + String.valueOf(expresion2.valor));
                    case entero:
                    case doble:
                        //Doble Entero
                        return new Expresion(Simbolo.EnumTipo.doble, Double.valueOf(String.valueOf(expresion1.valor)) + Double.valueOf(String.valueOf(expresion2.valor)));
                    case caracter:
                        //Doble Caracter
                        return new Expresion(Simbolo.EnumTipo.doble, Double.valueOf(String.valueOf(expresion1.valor)) + Double.valueOf(String.valueOf(expresion2.valor).charAt(0)));
                    case error:
                        return expresion2;
                    default:
                        String cadena = "Suma no definida entre los tipos " + expresion1.tipo + " y " + expresion2.tipo;
                        return new Expresion(Simbolo.EnumTipo.error, cadena);
                }
            case entero:
                switch (expresion2.tipo) {
                    case entero:
                        //Entero Entero
                        return new Expresion(Simbolo.EnumTipo.entero, Integer.valueOf(String.valueOf(expresion1.valor)) + Integer.valueOf(String.valueOf(expresion2.valor)));
                    case cadena:
                        //Entero Cadena
                        return new Expresion(Simbolo.EnumTipo.cadena, String.valueOf(expresion1.valor) + String.valueOf(expresion2.valor));
                    case caracter:
                        //Entero Caracter
                        return new Expresion(Simbolo.EnumTipo.doble, Integer.valueOf(String.valueOf(expresion1.valor)) + Integer.valueOf(String.valueOf(expresion2.valor).charAt(0)));
                    case doble:
                        //Entero Doble
                        return new Expresion(Simbolo.EnumTipo.doble, Double.valueOf(String.valueOf(expresion1.valor)) + Double.valueOf(String.valueOf(expresion2.valor)));
                    case error:
                        return expresion2;
                    default:
                        String cadena = "Suma no definida entre los tipos " + expresion1.tipo + " y " + expresion2.tipo;
                        return new Expresion(Simbolo.EnumTipo.error, cadena);
                }
            case caracter:
                switch (expresion2.tipo) {
                    case cadena:
                        //Caracter Cadena
                        return new Expresion(Simbolo.EnumTipo.cadena, String.valueOf(expresion1.valor) + String.valueOf(expresion2.valor));
                    case doble:
                        //Caracter Doble
                        return new Expresion(Simbolo.EnumTipo.cadena, Double.valueOf(String.valueOf(expresion1.valor)) + Double.valueOf(String.valueOf(expresion2.valor)));
                    case entero:
                        //Caracter Entero
                        return new Expresion(Simbolo.EnumTipo.cadena, Integer.valueOf(String.valueOf(expresion1.valor)) + Integer.valueOf(String.valueOf(expresion2.valor)));
                    case caracter:
                        //Caracter Caracter
                        return new Expresion(Simbolo.EnumTipo.cadena, Integer.valueOf(String.valueOf(expresion1.valor)) + Integer.valueOf(String.valueOf(expresion2.valor)));
                    case error:
                        return expresion2;
                    default:
                        String cadena = "Suma no definida entre los tipos " + expresion1.tipo + " y " + expresion2.tipo;
                        return new Expresion(Simbolo.EnumTipo.error, cadena);
                }
            case error:
                return expresion1;
            default:
                String cadena = "Suma no definida entre los tipos " + expresion1.tipo + " y " + expresion2.tipo;
                return new Expresion(Simbolo.EnumTipo.error, cadena);
        }
    }

    private Expresion OperarResta(Expresion expresion1, Expresion expresion2) {
        switch (expresion1.tipo) {
            case doble:
                switch (expresion2.tipo) {
                    case entero:
                    case doble:
                        //Doble Entero
                        return new Expresion(Simbolo.EnumTipo.doble, Double.valueOf(String.valueOf(expresion1.valor)) - Double.valueOf(String.valueOf(expresion2.valor)));
                    case caracter:
                        //Doble Caracter
                        return new Expresion(Simbolo.EnumTipo.doble, Double.valueOf(String.valueOf(expresion1.valor)) - Double.valueOf(String.valueOf(expresion2.valor).charAt(0)));
                    default:
                        String cadena = "Resta no definida entre los tipos " + expresion1.tipo + " y " + expresion2.tipo;
                        return new Expresion(Simbolo.EnumTipo.error, cadena);
                }
            case entero:
                switch (expresion2.tipo) {
                    case entero:
                        //Entero Entero
                        return new Expresion(Simbolo.EnumTipo.entero, Integer.valueOf(String.valueOf(expresion1.valor)) - Integer.valueOf(String.valueOf(expresion2.valor)));
                    case caracter:
                        //Entero Caracter
                        return new Expresion(Simbolo.EnumTipo.entero, Integer.valueOf(String.valueOf(expresion1.valor)) - Integer.valueOf(String.valueOf(expresion2.valor).charAt(0)));
                    case doble:
                        //Entero Doble
                        return new Expresion(Simbolo.EnumTipo.doble, Double.valueOf(String.valueOf(expresion1.valor)) - Double.valueOf(String.valueOf(expresion2.valor)));
                    default:
                        String cadena = "Resta no definida entre los tipos " + expresion1.tipo + " y " + expresion2.tipo;
                        return new Expresion(Simbolo.EnumTipo.error, cadena);
                }
            case caracter:
                switch (expresion2.tipo) {
                    case doble:
                        //Caracter Doble
                        return new Expresion(Simbolo.EnumTipo.doble, Double.valueOf(String.valueOf(expresion1.valor)) - Double.valueOf(String.valueOf(expresion2.valor)));
                    case entero:
                        //Caracter Entero
                        return new Expresion(Simbolo.EnumTipo.entero, Integer.valueOf(String.valueOf(expresion1.valor)) - Integer.valueOf(String.valueOf(expresion2.valor)));
                    case caracter:
                        //Caracter Caracter
                        return new Expresion(Simbolo.EnumTipo.entero, Integer.valueOf(String.valueOf(expresion1.valor)) - Integer.valueOf(String.valueOf(expresion2.valor)));
                    default:
                        String cadena = "Resta no definida entre los tipos " + expresion1.tipo + " y " + expresion2.tipo;
                        return new Expresion(Simbolo.EnumTipo.error, cadena);
                }
            case error:
                return expresion1;
            default:
                String cadena = "Resta no definida entre los tipos " + expresion1.tipo + " y " + expresion2.tipo;
                return new Expresion(Simbolo.EnumTipo.error, cadena);
        }
    }

    private Expresion OperarMultiplicacion(Expresion expresion1, Expresion expresion2) {
        switch (expresion1.tipo) {
            case doble:
                switch (expresion2.tipo) {
                    case entero:
                    case doble:
                        //Doble Entero
                        return new Expresion(Simbolo.EnumTipo.doble, Double.valueOf(String.valueOf(expresion1.valor)) * Double.valueOf(String.valueOf(expresion2.valor)));
                    case caracter:
                        //Doble Caracter
                        return new Expresion(Simbolo.EnumTipo.doble, Double.valueOf(String.valueOf(expresion1.valor)) * Double.valueOf(String.valueOf(expresion2.valor).charAt(0)));
                    default:
                        String cadena = "Multiplicacion no definida entre los tipos " + expresion1.tipo + " y " + expresion2.tipo;
                        return new Expresion(Simbolo.EnumTipo.error, cadena);
                }
            case entero:
                switch (expresion2.tipo) {
                    case entero:
                        //Entero Entero
                        return new Expresion(Simbolo.EnumTipo.entero, Integer.valueOf(String.valueOf(expresion1.valor)) * Integer.valueOf(String.valueOf(expresion2.valor)));
                    case caracter:
                        //Entero Caracter
                        return new Expresion(Simbolo.EnumTipo.entero, Integer.valueOf(String.valueOf(expresion1.valor)) * Integer.valueOf(String.valueOf(expresion2.valor).charAt(0)));
                    case doble:
                        //Entero Doble
                        return new Expresion(Simbolo.EnumTipo.doble, Double.valueOf(String.valueOf(expresion1.valor)) * Double.valueOf(String.valueOf(expresion2.valor)));
                    default:
                        String cadena = "Multiplicacion no definida entre los tipos " + expresion1.tipo + " y " + expresion2.tipo;
                        return new Expresion(Simbolo.EnumTipo.error, cadena);
                }
            case caracter:
                switch (expresion2.tipo) {
                    case doble:
                        //Caracter Doble
                        return new Expresion(Simbolo.EnumTipo.doble, Double.valueOf(String.valueOf(expresion1.valor)) * Double.valueOf(String.valueOf(expresion2.valor)));
                    case entero:
                        //Caracter Entero
                        return new Expresion(Simbolo.EnumTipo.entero, Integer.valueOf(String.valueOf(expresion1.valor)) * Integer.valueOf(String.valueOf(expresion2.valor)));
                    case caracter:
                        //Caracter Caracter
                        return new Expresion(Simbolo.EnumTipo.entero, Integer.valueOf(String.valueOf(expresion1.valor)) * Integer.valueOf(String.valueOf(expresion2.valor)));
                    default:
                        String cadena = "Multiplicacion no definida entre los tipos " + expresion1.tipo + " y " + expresion2.tipo;
                        return new Expresion(Simbolo.EnumTipo.error, cadena);
                }
            case error:
                return expresion1;
            default:
                String cadena = "Multiplicacion no definida entre los tipos " + expresion1.tipo + " y " + expresion2.tipo;
                return new Expresion(Simbolo.EnumTipo.error, cadena);
        }
    }

    private Expresion OperarDivision(Expresion expresion1, Expresion expresion2) {
        switch (expresion1.tipo) {
            case doble:
                switch (expresion2.tipo) {
                    case entero:
                    case doble:
                        //Doble Entero
                        if (String.valueOf(expresion2).equals("0") || String.valueOf(expresion2).equals("0.0")) {
                            return new Expresion(Simbolo.EnumTipo.error, "Division por 0");
                        } else {
                            return new Expresion(Simbolo.EnumTipo.doble, Double.valueOf(String.valueOf(expresion1.valor)) / Double.valueOf(String.valueOf(expresion2.valor)));
                        }
                    case caracter:
                        //Doble Caracter
                        if (String.valueOf(expresion2).equals("0") || String.valueOf(expresion2).equals("0.0")) {
                            return new Expresion(Simbolo.EnumTipo.error, "Division por 0");
                        } else {
                            return new Expresion(Simbolo.EnumTipo.doble, Double.valueOf(String.valueOf(expresion1.valor)) / Double.valueOf(String.valueOf(expresion2.valor).charAt(0)));
                        }
                    default:
                        String cadena = "Division no definida entre los tipos " + expresion1.tipo + " y " + expresion2.tipo;
                        return new Expresion(Simbolo.EnumTipo.error, cadena);
                }
            case entero:
                switch (expresion2.tipo) {
                    case entero:
                        //Entero Entero
                        if (String.valueOf(expresion2).equals("0") || String.valueOf(expresion2).equals("0.0")) {
                            return new Expresion(Simbolo.EnumTipo.error, "Division por 0");
                        } else {
                            return new Expresion(Simbolo.EnumTipo.doble, Integer.valueOf(String.valueOf(expresion1.valor)) / Integer.valueOf(String.valueOf(expresion2.valor)));
                        }
                    case caracter:
                        //Entero Caracter
                        if (String.valueOf(expresion2).equals("0") || String.valueOf(expresion2).equals("0.0")) {
                            return new Expresion(Simbolo.EnumTipo.error, "Division por 0");
                        } else {
                            return new Expresion(Simbolo.EnumTipo.doble, Integer.valueOf(String.valueOf(expresion1.valor)) / Integer.valueOf(String.valueOf(expresion2.valor).charAt(0)));
                        }
                    case doble:
                        //Entero Doble
                        if (String.valueOf(expresion2).equals("0") || String.valueOf(expresion2).equals("0.0")) {
                            return new Expresion(Simbolo.EnumTipo.error, "Division por 0");
                        } else {
                            return new Expresion(Simbolo.EnumTipo.doble, Double.valueOf(String.valueOf(expresion1.valor)) - Double.valueOf(String.valueOf(expresion2.valor)));
                        }
                    default:
                        String cadena = "Division no definida entre los tipos " + expresion1.tipo + " y " + expresion2.tipo;
                        return new Expresion(Simbolo.EnumTipo.error, cadena);
                }
            case caracter:
                switch (expresion2.tipo) {
                    case doble:
                        //Caracter Doble
                        return new Expresion(Simbolo.EnumTipo.cadena, Double.valueOf(String.valueOf(expresion1.valor)) / Double.valueOf(String.valueOf(expresion2.valor)));
                    case entero:
                        //Caracter Entero
                        return new Expresion(Simbolo.EnumTipo.cadena, Integer.valueOf(String.valueOf(expresion1.valor)) / Integer.valueOf(String.valueOf(expresion2.valor)));
                    case caracter:
                        //Caracter Caracter
                        return new Expresion(Simbolo.EnumTipo.cadena, Integer.valueOf(String.valueOf(expresion1.valor)) / Integer.valueOf(String.valueOf(expresion2.valor)));
                    default:
                        String cadena = "Division no definida entre los tipos " + expresion1.tipo + " y " + expresion2.tipo;
                        return new Expresion(Simbolo.EnumTipo.error, cadena);
                }
            case error:
                return expresion1;
            default:
                String cadena = "Division no definida entre los tipos " + expresion1.tipo + " y " + expresion2.tipo;
                return new Expresion(Simbolo.EnumTipo.error, cadena);
        }
    }

    private Expresion OperarPotencia(Expresion expresion1, Expresion expresion2) {
        switch (expresion1.tipo) {
            case doble:
            case entero:
                switch (expresion2.tipo) {
                    case entero:
                    case doble:
                        //Doble Entero
                        return new Expresion(Simbolo.EnumTipo.doble, Math.pow(Double.valueOf(String.valueOf(expresion1.valor)), Double.valueOf(String.valueOf(expresion2.valor))));
                    case caracter:
                        //Doble Caracter
                        return new Expresion(Simbolo.EnumTipo.doble, Math.pow(Double.valueOf(String.valueOf(expresion1.valor)), Double.valueOf(String.valueOf(expresion2.valor).charAt(0))));
                    default:
                        String cadena = "Potencia no definida entre los tipos " + expresion1.tipo + " y " + expresion2.tipo;
                        return new Expresion(Simbolo.EnumTipo.error, cadena);
                }
            case caracter:
                switch (expresion2.tipo) {
                    case doble:
                    case entero:
                        //Caracter Doble
                        return new Expresion(Simbolo.EnumTipo.doble, Math.pow(Double.valueOf(String.valueOf(expresion1.valor).charAt(0)), Double.valueOf(String.valueOf(expresion2.valor))));
                    case caracter:
                        //Caracter Caracter
                        return new Expresion(Simbolo.EnumTipo.doble, Math.pow(Double.valueOf(String.valueOf(expresion1.valor).charAt(0)), Double.valueOf(String.valueOf(expresion2.valor).charAt(0))));
                    default:
                        String cadena = "Potencia no definida entre los tipos " + expresion1.tipo + " y " + expresion2.tipo;
                        return new Expresion(Simbolo.EnumTipo.error, cadena);
                }
            case error:
                return expresion1;
            default:
                String cadena = "Potencia no definida entre los tipos " + expresion1.tipo + " y " + expresion2.tipo;
                return new Expresion(Simbolo.EnumTipo.error, cadena);
        }
    }

    private Expresion OperarNegativo(Expresion expresion1) {
        Object valor;
        switch (expresion1.tipo) {
            case entero:
            case caracter:
                valor = -(int) expresion1.valor;
                return new Expresion(Simbolo.EnumTipo.entero, valor);
            case doble:
                valor = -(double) expresion1.valor;
                return new Expresion(Simbolo.EnumTipo.doble, valor);
            default:
                return new Expresion(Simbolo.EnumTipo.error, "Se esperaba valor numerico, tipo " + expresion1.tipo + " encontrado");
        }
    }

    private Expresion OperarOrLogico(Expresion expresion1, Expresion expresion2) {
        if (expresion1.tipo == Simbolo.EnumTipo.booleano) {
            if (expresion2.tipo == Simbolo.EnumTipo.booleano) {
                boolean resultado = (boolean) expresion1.valor || (boolean) expresion2.valor;
                return new Expresion(Simbolo.EnumTipo.booleano, resultado);
            }
            return new Expresion(Simbolo.EnumTipo.error, "Tipo booleano requerido, tipo " + expresion1.tipo + " encontrado");
        }
        return new Expresion(Simbolo.EnumTipo.error, "Tipo booleano requerido, tipo " + expresion1.tipo + " encontrado");
    }

    private Expresion OperarAndLogico(Expresion expresion1, Expresion expresion2) {
        if (expresion1.tipo == Simbolo.EnumTipo.booleano) {
            if (expresion2.tipo == Simbolo.EnumTipo.booleano) {
                boolean resultado = (boolean) expresion1.valor && (boolean) expresion2.valor;
                return new Expresion(Simbolo.EnumTipo.booleano, resultado);
            }
            return new Expresion(Simbolo.EnumTipo.error, "Tipo booleano requerido, tipo " + expresion1.tipo + " encontrado");
        }
        return new Expresion(Simbolo.EnumTipo.error, "Tipo booleano requerido, tipo " + expresion1.tipo + " encontrado");
    }

    private Expresion OperarNotLogico(Expresion expresion1) {
        if (expresion1.tipo == Simbolo.EnumTipo.booleano) {
            boolean resultado = (boolean) expresion1.valor;
            return new Expresion(Simbolo.EnumTipo.booleano, !resultado);
        }
        return new Expresion(Simbolo.EnumTipo.error, "Tipo booleano requerido, tipo " + expresion1.tipo + " encontrado");
    }

    private Expresion OperarXorLogico(Expresion expresion1, Expresion expresion2) {
        if (expresion1.tipo == Simbolo.EnumTipo.booleano) {
            if (expresion2.tipo == Simbolo.EnumTipo.booleano) {
                boolean resultado = (boolean) expresion1.valor ^ (boolean) expresion2.valor;
                return new Expresion(Simbolo.EnumTipo.booleano, resultado);
            }
            return new Expresion(Simbolo.EnumTipo.error, "Tipo booleano requerido, tipo " + expresion1.tipo + " encontrado");
        }
        return new Expresion(Simbolo.EnumTipo.error, "Tipo booleano requerido, tipo " + expresion1.tipo + " encontrado");
    }

    private Expresion OperarMayorQue(Expresion expresion1, Expresion expresion2) {
        boolean resultado;
        switch (expresion1.tipo) {
            case entero:
                switch (expresion2.tipo) {
                    case entero:
                        //Entero Entero
                        resultado = (int) expresion1.valor > (int) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                    case doble:
                        //Entero Doble
                        resultado = (int) expresion1.valor > (double) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                    case caracter:
                        //Entero Caracter
                        resultado = (int) expresion1.valor > (int) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                }
                break;
            case doble:
                switch (expresion2.tipo) {
                    case entero:
                        //Doble Entero
                        resultado = (double) expresion1.valor > (int) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                    case doble:
                        //Doble Doble
                        resultado = (double) expresion1.valor > (double) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                    case caracter:
                        //Doble Caracter
                        resultado = (double) expresion1.valor > (int) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                }
                break;
            case caracter:
                switch (expresion2.tipo) {
                    case entero:
                        //Caracter Entero
                        resultado = (int) expresion1.valor > (int) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                    case doble:
                        //Caracter Doble
                        resultado = (int) expresion1.valor > (double) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                    case caracter:
                        //Caracter Caracter
                        resultado = (int) expresion1.valor > (int) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                }
                break;
        }
        return new Expresion(Simbolo.EnumTipo.error, "Operacion entre " + expresion1.tipo + " y " + expresion2.tipo + " no definida");
    }

    private Expresion OperarMenorQue(Expresion expresion1, Expresion expresion2) {
        boolean resultado;
        switch (expresion1.tipo) {
            case entero:
                switch (expresion2.tipo) {
                    case entero:
                        //Entero Entero
                        resultado = (int) expresion1.valor < (int) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                    case doble:
                        //Entero Doble
                        resultado = (int) expresion1.valor < (double) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                    case caracter:
                        //Entero Caracter
                        resultado = (int) expresion1.valor < (int) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                }
                break;
            case doble:
                switch (expresion2.tipo) {
                    case entero:
                        //Doble Entero
                        resultado = (double) expresion1.valor < (int) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                    case doble:
                        //Doble Doble
                        resultado = (double) expresion1.valor < (double) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                    case caracter:
                        //Doble Caracter
                        resultado = (double) expresion1.valor < (int) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                }
                break;
            case caracter:
                switch (expresion2.tipo) {
                    case entero:
                        //Caracter Entero
                        resultado = (int) expresion1.valor < (int) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                    case doble:
                        //Caracter Doble
                        resultado = (int) expresion1.valor < (double) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                    case caracter:
                        //Caracter Caracter
                        resultado = (int) expresion1.valor < (int) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                }
                break;
        }
        return new Expresion(Simbolo.EnumTipo.error, "Operacion entre " + expresion1.tipo + " y " + expresion2.tipo + " no definida");
    }

    private Expresion OperarMayorIgualQue(Expresion expresion1, Expresion expresion2) {
        boolean resultado;
        switch (expresion1.tipo) {
            case entero:
                switch (expresion2.tipo) {
                    case entero:
                        //Entero Entero
                        resultado = (int) expresion1.valor >= (int) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                    case doble:
                        //Entero Doble
                        resultado = (int) expresion1.valor >= (double) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                    case caracter:
                        //Entero Caracter
                        resultado = (int) expresion1.valor >= (int) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                }
                break;
            case doble:
                switch (expresion2.tipo) {
                    case entero:
                        //Doble Entero
                        resultado = (double) expresion1.valor >= (int) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                    case doble:
                        //Doble Doble
                        resultado = (double) expresion1.valor >= (double) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                    case caracter:
                        //Doble Caracter
                        resultado = (double) expresion1.valor >= (int) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                }
                break;
            case caracter:
                switch (expresion2.tipo) {
                    case entero:
                        //Caracter Entero
                        resultado = (int) expresion1.valor >= (int) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                    case doble:
                        //Caracter Doble
                        resultado = (int) expresion1.valor >= (double) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                    case caracter:
                        //Caracter Caracter
                        resultado = (int) expresion1.valor >= (int) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                }
                break;
        }
        return new Expresion(Simbolo.EnumTipo.error, "Operacion entre " + expresion1.tipo + " y " + expresion2.tipo + " no definida");
    }

    private Expresion OperarMenorIgualQue(Expresion expresion1, Expresion expresion2) {
        boolean resultado;
        switch (expresion1.tipo) {
            case entero:
                switch (expresion2.tipo) {
                    case entero:
                        //Entero Entero
                        resultado = (int) expresion1.valor <= (int) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                    case doble:
                        //Entero Doble
                        resultado = (int) expresion1.valor <= (double) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                    case caracter:
                        //Entero Caracter
                        resultado = (int) expresion1.valor <= (int) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                }
                break;
            case doble:
                switch (expresion2.tipo) {
                    case entero:
                        //Doble Entero
                        resultado = (double) expresion1.valor <= (int) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                    case doble:
                        //Doble Doble
                        resultado = (double) expresion1.valor <= (double) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                    case caracter:
                        //Doble Caracter
                        resultado = (double) expresion1.valor <= (int) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                }
                break;
            case caracter:
                switch (expresion2.tipo) {
                    case entero:
                        //Caracter Entero
                        resultado = (int) expresion1.valor <= (int) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                    case doble:
                        //Caracter Doble
                        resultado = (int) expresion1.valor <= (double) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                    case caracter:
                        //Caracter Caracter
                        resultado = (int) expresion1.valor <= (int) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                }
                break;
        }
        return new Expresion(Simbolo.EnumTipo.error, "Operacion entre " + expresion1.tipo + " y " + expresion2.tipo + " no definida");
    }

    private Expresion OperarIgual(Expresion expresion1, Expresion expresion2) {
        boolean resultado;
        switch (expresion1.tipo) {
            case entero:
                switch (expresion2.tipo) {
                    case entero:
                        //Entero Entero
                        resultado = (int) expresion1.valor == (int) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                    case doble:
                        //Entero Doble
                        resultado = (int) expresion1.valor == (double) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                    case caracter:
                        //Entero Caracter
                        resultado = (int) expresion1.valor == (int) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                }
                break;
            case doble:
                switch (expresion2.tipo) {
                    case entero:
                        //Doble Entero
                        resultado = (double) expresion1.valor == (int) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                    case doble:
                        //Doble Doble
                        resultado = (double) expresion1.valor == (double) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                    case caracter:
                        //Doble Caracter
                        resultado = (double) expresion1.valor == (int) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                }
                break;
            case caracter:
                switch (expresion2.tipo) {
                    case entero:
                        //Caracter Entero
                        resultado = (int) expresion1.valor == (int) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                    case doble:
                        //Caracter Doble
                        resultado = (int) expresion1.valor == (double) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                    case caracter:
                        //Caracter Caracter
                        resultado = (int) expresion1.valor == (int) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                }
                break;
            case cadena:
                switch (expresion2.tipo) {
                    case cadena:
                        //Cadena Cadena
                        String cadena1 = (String) expresion1.valor;
                        String cadena2 = (String) expresion2.valor;
                        resultado = cadena1.equals(cadena2);
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                }
                break;
        }
        return new Expresion(Simbolo.EnumTipo.error, "Operacion entre " + expresion1.tipo + " y " + expresion2.tipo + " no definida");
    }

    private Expresion OperarDiferente(Expresion expresion1, Expresion expresion2) {
        boolean resultado;
        switch (expresion1.tipo) {
            case entero:
                switch (expresion2.tipo) {
                    case entero:
                        //Entero Entero
                        resultado = (int) expresion1.valor != (int) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                    case doble:
                        //Entero Doble
                        resultado = (int) expresion1.valor != (double) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                    case caracter:
                        //Entero Caracter
                        resultado = (int) expresion1.valor != (int) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                }
                break;
            case doble:
                switch (expresion2.tipo) {
                    case entero:
                        //Doble Entero
                        resultado = (double) expresion1.valor != (int) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                    case doble:
                        //Doble Doble
                        resultado = (double) expresion1.valor != (double) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                    case caracter:
                        //Doble Caracter
                        resultado = (double) expresion1.valor != (int) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                }
                break;
            case caracter:
                switch (expresion2.tipo) {
                    case entero:
                        //Caracter Entero
                        resultado = (int) expresion1.valor != (int) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                    case doble:
                        //Caracter Doble
                        resultado = (int) expresion1.valor != (double) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                    case caracter:
                        //Caracter Caracter
                        resultado = (int) expresion1.valor != (int) expresion2.valor;
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                }
                break;
            case cadena:
                switch (expresion2.tipo) {
                    case cadena:
                        //Cadena Cadena
                        String cadena1 = (String) expresion1.valor;
                        String cadena2 = (String) expresion2.valor;
                        resultado = !cadena1.equals(cadena2);
                        return new Expresion(Simbolo.EnumTipo.booleano, resultado);
                }
                break;
        }
        return new Expresion(Simbolo.EnumTipo.error, "Operacion entre " + expresion1.tipo + " y " + expresion2.tipo + " no definida");
    }
}
