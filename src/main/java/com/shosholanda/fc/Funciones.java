package com.shosholanda.fc;

/** Funciones auxiliarles para las curvas elípticas 
 */
public class Funciones {

    /**
     * Nos dice si el número pasado como argumento es primo
     * independientemente de su signo.
     * @param n el número a verificar si es primo.
     * @return true si el número es primo, false en otro caso.
     */
    public static boolean esPrimo(int numero){
        numero = Math.abs(numero);

        if (numero < 2) {
            return false;
        }   

        if (numero == 2){
            return true;
        }

        if (numero % 2 == 0) {
            return false;
        }
        
        int limite = (int)Math.sqrt(numero);

        for (int divisor = 3; divisor <= limite; divisor += 2) {
            if (numero % divisor == 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Nos da el inverso aditivo módulo m.
     * Es decir a + k es congruente con 0 módulo m.
     * @param a el número a sacar el inverso.
     * @param mod el módulo de donde sacar el inverso.
     * @return i tal que a + i sea congruente con 0 módulo m.
     */
    public static int inversoAditivo(int valor, int modulo){
        return modulo(-valor, modulo);
    }

    /**
     * Nos da el inverso multiplicativo módulo m.
     * Es decir a * k es congruente con 1 módulo m.
     * @param a el número a sacar el inverso.
     * @param mod el módulo de donde sacar el inverso.
     * @return i tal que a * i sea congruente con 1 módulo m.
     */
    public static int inversoMultiplicativo(int valor, int modulo){
        int moduloPositivo = modulo;
        int aMod = modulo(valor, moduloPositivo);

        if (aMod == 0){
           throw new IllegalArgumentException("No existe inverso multiplicativo para 0"); 
        }

        int coeficiente = 0, nuevoCoeficiente = 1;
        int residuo = moduloPositivo, nuevoResiduo = aMod;

        while (nuevoResiduo != 0) {
            int cociente = residuo / nuevoResiduo;
            int tempCoeficiente = coeficiente - cociente * nuevoCoeficiente;
            coeficiente = nuevoCoeficiente;
            nuevoCoeficiente = tempCoeficiente;
            int tempResiduo = residuo - cociente * nuevoResiduo;
            residuo = nuevoResiduo;
            nuevoResiduo = tempResiduo;
        }

        if (residuo != 1){
            throw new IllegalArgumentException("No existe inverso multiplicativo para " + valor + " modulo " + modulo);
        }

        if (coeficiente < 0){
            coeficiente += moduloPositivo;
        }

        return coeficiente;
    }

    /**
     * Nos da el módulo porque -a%p no funciona como se espera
     */
    public static int modulo(int numero, int modulo){
        int resto = numero % modulo;
        if (resto < 0){
            resto += modulo;
        }
        return resto;
    }
        
}