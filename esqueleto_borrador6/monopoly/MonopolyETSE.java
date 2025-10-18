package monopoly;

import partida.*;

public class MonopolyETSE {

    public static void main(String[] args) {

        // Punto de entrada principal do programa Monopoly ETSE
        // Controla se se executa en modo ficheiro ou modo interactivo

        // --- MODO A: Execución con ficheiro de comandos ---
        // Verificamos se se proporcionou algún argumento na liña de comandos
        if (args.length > 0) {
            // Obtenemos o primeiro argumento como nome do ficheiro de comandos
            String nomeFicheiro = args[0];

            // Mostramos mensaxe informativa para o usuario
            System.out.println("=== MODO FICHEIRO ===");
            System.out.println("Lendo comandos desde o ficheiro: " + nomeFicheiro);
            System.out.println("----------------------------------------");

            // Creamos unha instancia do menú do xogo
            Menu menu = new Menu();

            //lee liña por liña do ficheiro
            menu.lerFicheiroComandos(nomeFicheiro);

            // Mensaxe final ao rematar a execución do ficheiro
            System.out.println("----------------------------------------");
            System.out.println("Fin da execución do ficheiro. Programa rematado.");

            return; // Finalizamos o programa despois de procesar o ficheiro
        }

        // --- MODO B: Execución interactiva (sen argumentos) ---
        // Se non se proporcionaron argumentos, iniciamos o modo interactivo

        // Mensaxe de benvida e información para o usuario
        System.out.println("=== MODO INTERACTIVO ===");
        System.out.println("Benvido ao Monopoly ETSE - Primeira Entrega");
        System.out.println("----------------------------------------");

        // Creamos unha instancia do menú que xestionará toda a lóxica do xogo
        // O constructor de Menu xa inicia automaticamente o bucle interactivo
        // incluíndo a lectura de comandos por teclado e a xestión do xogo
        Menu menu = new Menu();

        // Nota: O control do fluxo interactivo xestionase internamente na clase Menu
        // O constructor Menu() xa chama a iniciarPartida() que ten o bucle principal
    }
}