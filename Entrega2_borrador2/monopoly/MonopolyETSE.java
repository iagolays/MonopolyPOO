package monopoly;

import partida.*;

public class MonopolyETSE {

    public static void main(String[] args) {
        // Controla se se executa en modo ficheiro ou modo interactivo

        // --- MODO A: Execución con ficheiro de comandos ---
        // Verificamos se se proporcionou algún argumento na liña de comandos

        //javac monopoly/*.java partida/*.java
        //java -cp . monopoly.MonopolyETSE prueba.txt

        if (args.length > 0) {
            // Obtenemos o primeiro argumento como nome do ficheiro de comandos
            String nomeFicheiro = args[0];

            System.out.println("Modo ficheiro activado. Lendo comandos desde: " + nomeFicheiro);

            // Creamos o menú principal do xogo e executamos os comandos do ficheiro
            Menu menu = new Menu(false); //modo ficheiro

            //lee liña por liña do ficheiro
            menu.lerFicheiroComandos(nomeFicheiro);

            System.out.println("Fin da execución do ficheiro. Programa rematado.");
            return; // Pechamos o programa despois de ler o ficheiro
        }

        // --- MODO B: Execución interactiva (sen argumentos) ---
        // Se non se proporcionaron argumentos, iniciamos o modo interactivo

        System.out.println("Modo interactivo activado.");

        // O obxecto Menu xestiona a lóxica do xogo e a lectura de comandos por teclado
        // incluíndo a lectura de comandos por teclado e a xestión do xogo

        Menu menu = new Menu(true); //modo interactivo
    }
}