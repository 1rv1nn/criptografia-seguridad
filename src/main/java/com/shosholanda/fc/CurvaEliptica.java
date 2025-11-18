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
      this.a = 1;
      this.b = 1;
      this.primo = 3;
    }

    /**
     * Construye una curva elíptica dados sus parámetros.
     * @param a primer coeficiente
     * @param b el segundo coeficiente
     * @param primo el primo al módulo
     * @throws IllegalArgumentException si no es una curva válida.
     */
    public CurvaEliptica(int a, int b, int primo){
        if (primo <= 3 || !Funciones.esPrimo(primo))
            throw new IllegalArgumentException("El primo debe ser mayor que 3 y primo");
        long discr = 4L * a * a * a + 27L * b * b;
        if (discr == 0)
            throw new IllegalArgumentException("Curva singular (discriminante 0)");
        this.a = a;
        this.b = b;
        this.primo = primo;
    }
    
    /**
     * Regresa el coeficiente A de esta curva elíptica.
     * @return el coeficiente A de esta curva elíptica.
     */
    public int getA(){
      return this.a;
    }
    
    /**
     * Regresa el coeficiente B de esta curva elíptica.
     * @return el coeficiente B de esta curva elíptica.
     */
    public int getB(){
      return this.b;
    }

    /**
     * Regresa el primo relacionado a esta curva elíptica.
     * @return el primo relacionado a esta curva elíptica.
     */
    public int getPrimo(){
      return this.primo;
    }
    
    /**
     * Nos dice si el punto recibido como parámetro es parte de la
     * curva
     * @param p el punto a verificar si es parte de esta curva
     * @return si el punto pertenece o no a la curva.
     */
    public boolean pertenece(Punto p){
      if (p == null) return true;
      return pertenece(p.getX(), p.getY());
    }

    /**
     * Nos dice si los valores recibidos como parámetro son parte de
     * la curva
     */
    private boolean pertenece(int x, int y){
        int lhs = Funciones.modulo(y * y, this.primo);
        int rhs = Funciones.modulo((x * x * x + this.a * x + this.b), this.primo);
        return lhs == rhs;
    }


    /**
     * Nos regresa todos los puntos que pertenecen a esta lista.
     * @return todos los puntos (a, b) que cumplen la congruencia
     */
    public List<Punto> puntos(){
        List<Punto> lista = new ArrayList<>();
        for (int x = 0; x < this.primo; x++){
            for (int y = 0; y < this.primo; y++){
                if (pertenece(x, y))
                    lista.add(new Punto(x, y));
            }
        }
        // Punto al infinito representado por null
        lista.add(null);
        return lista;
    }


    /**
     * Nos genera todos los posibles puntos empezando desde el punto
     * P, sumados consigo mismo hasta llegar al punto al infinito.
     * @param P el punto p que queremos empezar a generar los demás puntos.
     * @return una lista de los puntos generados de P.
     * @throws IllegalArgumentException si el punto P no pertenece a la curva.
     */
    public List<Punto> genera(Punto p){
        if (!pertenece(p))
            throw new IllegalArgumentException("El punto no pertenece a la curva");
        List<Punto> lista = new ArrayList<>();
        if (p == null){
            lista.add(null);
            return lista;
        }
        Punto cur = p;
        // Generamos p, 2p, 3p, ... hasta llegar al infinito
        while (cur != null){
            lista.add(cur);
            cur = suma(p, cur);
        }
        lista.add(null);
        return lista;
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
        // Identidad
        if (p == null) return q;
        if (q == null) return p;

        if (!pertenece(p) || !pertenece(q))
            throw new IllegalArgumentException("Alguno de los puntos no pertenece a la curva");

        int x1 = Funciones.modulo(p.getX(), this.primo);
        int y1 = Funciones.modulo(p.getY(), this.primo);
        int x2 = Funciones.modulo(q.getX(), this.primo);
        int y2 = Funciones.modulo(q.getY(), this.primo);

        // Si x1 == x2 y y1 == -y2 modulo p => suma infinito
        if (x1 == x2 && y1 == Funciones.modulo(-y2, this.primo))
            return null;

        int lambda;
        if (x1 == x2 && y1 == y2){
            // Duplicación:  lambda = (3*x1^2 + a) / (2*y1)
            if (y1 == 0)
                return null;
            int num = Funciones.modulo(3 * x1 * x1 + this.a, this.primo);
            int den = Funciones.modulo(2 * y1, this.primo);
            int inv = Funciones.inversoMultiplicativo(den, this.primo);
            lambda = Funciones.modulo(num * inv, this.primo);
        } else {
            // Punto distinto: lambda = (y2 - y1) / (x2 - x1)
            int num = Funciones.modulo(y2 - y1, this.primo);
            int den = Funciones.modulo(x2 - x1, this.primo);
            int inv = Funciones.inversoMultiplicativo(den, this.primo);
            lambda = Funciones.modulo(num * inv, this.primo);
        }

        int xr = Funciones.modulo(lambda * lambda - x1 - x2, this.primo);
        int yr = Funciones.modulo(lambda * (x1 - xr) - y1, this.primo);
        return new Punto(xr, yr);
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
    
