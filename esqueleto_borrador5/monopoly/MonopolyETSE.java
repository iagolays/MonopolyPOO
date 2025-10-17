package monopoly;

import partida.*;
import java.util.ArrayList;
import java.util.Scanner;


public class MonopolyETSE {

/*    ESTO LO DEJO POR SI ACASO

    public static void main(String[] args) {
        //PRUEBA IMPRESION TABLERO

        Jugador banca = new Jugador();
        Tablero t = new Tablero(banca);

        System.out.println(t);
        //new Menu();


    }

 */


    public static void main(String[] args) {

        // Crear el menú (inicializa tablero, banca, etc.)
        Menu menu = new Menu();

        // --- MODO A: con fichero ---
        if (args.length > 0) {
            String nomeFicheiro = args[0];
            System.out.println("Lendo comandos desde o ficheiro: " + nomeFicheiro);
            menu.lerComandos(nomeFicheiro); // procesa y ejecuta todo

            System.out.println("\n--- Fin do ficheiro. Saíndo do xogo... ---");
            return; // fin del programa
        }

        // --- MODO B: sin fichero (interactivo) ---
        Scanner sc = new Scanner(System.in);
        System.out.println("Modo interactivo iniciado. Introduce comandos (escribe 'saír' ou 'salir' para rematar):");

        while (true) {
            System.out.print("$> ");
            String comando = sc.nextLine().trim();

            if (comando.equalsIgnoreCase("saír") || comando.equalsIgnoreCase("salir")) {
                System.out.println("Saíndo do xogo...");
                break;
            }

            menu.procesarComando(comando); // ejecuta cada comando
        }

        sc.close();
    }

}
