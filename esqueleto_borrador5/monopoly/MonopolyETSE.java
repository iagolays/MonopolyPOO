package monopoly;

import partida.*;
import java.util.ArrayList;

public class MonopolyETSE {

    public static void main(String[] args) {
        //PRUEBA IMPRESION TABLERO

        Jugador banca = new Jugador();
        Tablero t = new Tablero(banca);

        System.out.println(t);
        //new Menu();


    }

}
