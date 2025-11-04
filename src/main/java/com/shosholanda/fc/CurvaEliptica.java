package com.shosholanda.fc;

import java.util.List;
import java.util.ArrayList;

/**
 * Clase que crea una curva elíptica usando un campo finito módulo
 * mayor a 3. Las curvas elípticas cumplen la ecuación:
 *
 * y² ≡ x³ + ax + b   mod p
 *
 * Donde a, b, p son números enteros y p un primo positivo
 */

public class CurvaEliptica {

    /* Parámetro a de la curva */
    private int a;
    /* Parámetro b de la curva */
    private int b;
    /* El primo */
    private int primo;


    /**
     * Construye la más pequeña de las curvas elípticas válida por
     * la construcción:  4a³ + 27b² != 0
     */
    public CurvaEliptica(){

    }

    /**
     * Construye una curva elíptica dados sus parámetros.
     * @param a primer coeficiente
     * @param b el segundo coeficiente
     * @param primo el primo al módulo
     * @throws IllegalArgumentException si no es una curva válida.
     */
    public CurvaEliptica(int a, int b, int primo){

    }
    
    /**
     * Regresa el coeficiente A de esta curva elíptica.
     * @return el coeficiente A de esta curva elíptica.
     */
    public int getA(){

    }
    
    /**
     * Regresa el coeficiente B de esta curva elíptica.
     * @return el coeficiente B de esta curva elíptica.
     */
    public int getB(){

    }

    /**
     * Regresa el primo relacionado a esta curva elíptica.
     * @return el primo relacionado a esta curva elíptica.
     */
    public int getPrimo(){

    }
    
    /**
     * Nos dice si el punto recibido como parámetro es parte de la
     * curva
     * @param p el punto a verificar si es parte de esta curva
     * @return si el punto pertenece o no a la curva.
     */
    public boolean pertenece(Punto p){

    }

    /**
     * Nos dice si los valores recibidos como parámetro son parte de
     * la curva
     */
    private boolean pertenece(int x, int y){

    }


    /**
     * Nos regresa todos los puntos que pertenecen a esta lista.
     * @return todos los puntos (a, b) que cumplen la congruencia
     */
    public List<Punto> puntos(){

    }


    /**
     * Nos genera todos los posibles puntos empezando desde el punto
     * P, sumados consigo mismo hasta llegar al punto al infinito.
     * @param P el punto p que queremos empezar a generar los demás puntos.
     * @return una lista de los puntos generados de P.
     * @throws IllegalArgumentException si el punto P no pertenece a la curva.
     */
    public List<Punto> genera(Punto p){

    }


    /**
     * Nos regresa la suma de cualquiera dos puntos que pertenecen a
     * la curva elíptica.
     * @param p el primero punto
     * @param q el segundo punto
     * @return la suma de p y q
     * @throws IllegalArgumentException si p o q no son parte de la curva.
     */
    public Punto suma(Punto p, Punto q){

    }


    /**
     * Suma k veces el punto p consigo mismo. En otras palabras
     * calcula k * P.
     * @param k el número de veces a multiplicar consigo mismo.
     * @param p el punto a multiplicar k veces consigo mismo.
     * @return el resultado de k*P (suma multiple).
     */
    public Punto multiplicacion(int k, Punto p){

    }



    /**
     * Calcula el orden del punto p en esta curva.  El orden de un
     * punto P se calcula como cuántas veces se debe sumar P consigo
     * mismo hasta llegar al punto al infnito. Si P es null, regresa
     * infinito.
     * @param p el punto a sumar hasta el infinito.
     * @return el número de iteraciones hechas.
     * @throws IllegalArgumentException si el punto p no pertenece a la cuva.
     */
    public int orden(Punto p){

    }
 
    /**
     * Define el cofactor del punto P sobre esta curva.  El cofactor
     * se calcula como el total de puntos entre el orden de este
     * punto. el Orden no puede ser 0.
     * @param p el punto de donde sacar el cofactor.
     * @return el cofactor de este punto P en la curva.
     */
    public double cofactor(Punto p){

    }
    

    /**
     * Regresa una representación en cadena de la curva elíptica.
     * @return una representación en cadena de la curva elíptica.
     */
    @Override
    public String toString(){

    }

    /**
     * Nos dice si el objeto recibido es igual a este objeto.
     * @param o el objeto contra quien comparar.
     * @return true si son iguales, false en otro caso.
     */
    @Override
    public boolean equals(Object o){

    }


    /**
     * Mëtodo privado que nos regresa el punto inverso de p.
     * tal que p + p' = punto al infinito.
     * @param p el punto de quien sacar el punto inverso.
     * @return el punto inverso de p.
     */
    private Punto inverso(Punto p){

    }
	
}
    
