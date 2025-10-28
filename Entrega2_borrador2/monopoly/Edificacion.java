package monopoly;

import partida.*;

import java.util.Scanner;

public class Edificacion {

    public enum Tipo { CASA, HOTEL, PISCINA, PISTA_DEPORTE }

    private final String id;
    private final Tipo tipo;
    private final Casilla solar;  // referencia al solar donde se construye
    private final float coste;

    public Edificacion(Tipo tipo, Casilla solar, float coste) {
        this.tipo = tipo;
        this.solar = solar;
        this.coste = coste;
        this.id = generarId(tipo);
    }

    public String getId() { return id; }
    public Tipo getTipo() { return tipo; }
    public Casilla getSolar() { return solar; }
    public float getCoste() { return coste; }

    // genera un ID único para la edificación
    public static String generarId(Tipo tipo) {
        long t = System.currentTimeMillis() % 100000;
        return tipo.name().toLowerCase() + "-" + t;
    }
}
