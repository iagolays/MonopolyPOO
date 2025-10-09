package monopoly;

import partida.*; /*impórtanse todas as clases do paquete*/
import java.io.FileNotFoundException; //obligatorio para que funcione new Scanner ao ler o arquivo
import java.util.ArrayList;
import java.io.File; //impórtanse a clase para ficheiros
import java.util.Scanner;

public class Menu {

    //Atributos
    private ArrayList<Jugador> jugadores = new ArrayList<>(); //Jugadores de la partida.
    private ArrayList<Avatar> avatares = new ArrayList<>(); //Avatares en la partida.
    private int turno = 0; //Índice correspondiente a la posición en el arrayList del jugador (y el avatar) que tienen el turno
    private int lanzamientos; //Variable para contar el número de lanzamientos de un jugador en un turno.
    private Tablero tablero; //Tablero en el que se juega.
    private Dado dado1; //Dos dados para lanzar y avanzar casillas.
    private Dado dado2;
    private Jugador banca; //El jugador banca.
    private boolean tirado; //Booleano para comprobar si el jugador que tiene el turno ha tirado o no.
    private boolean solvente; //Booleano para comprobar si el jugador que tiene el turno es solvente, es decir, si ha pagado sus deudas.

    public Menu() { //constructor de menu
        iniciarPartida();
        /*banca = new Jugador();
        tablero = new Tablero(banca);*/
    }

    private void crearJugador(String nombre, String tipoAvatar) {

        // Buscamos la casilla de salida
        Casilla salida = tablero.encontrar_casilla("Salida");

        // Creamos el jugador
        Jugador jugador = new Jugador(nombre, tipoAvatar, salida, avatares);

        // Añadimos jugador y avatar a los ArrayLists de Menu
        jugadores.add(jugador);
        avatares.add(jugador.getAvatar());

        // Mostramos mensaje de confirmación
        System.out.println("{");
        System.out.println("  \"Nome\": \"" + jugador.getNombre() + "\",");
        System.out.println("  \"Avatar\": \"" + jugador.getAvatar().getId() + "\"");
        System.out.println("}");
    }

    // Metodo para inciar una partida: crea los jugadores y avatares.
    private void iniciarPartida() { //inicia partida se non se mete nada por comandos

    }

    private void lerFicheiroComandos(String nomeFicheiro){
        File ficheiro = new File(nomeFicheiro); //creamos o obxecto file

        //comprobamos se o ficheiro existe
        if (!ficheiro.exists()){
            System.out.println("O ficheiro non existe: " + nomeFicheiro);
            return;
        }
        Scanner scan = null;
        try{
            scan = new Scanner(ficheiro); //lemos o ficheiro
        } catch (FileNotFoundException e) { //obligatorio para que funcione new Scanner
            System.out.println("Non se pode abrir o fichero: " + e.getMessage()); //mensaxe de erro porque non se abriu ben
            return;
        }
        //Lemos liña por liña
        while (scan.hasNextLine()){
            String linha = scan.nextLine().trim(); //eliminamos espacios ao inicio e ao final
            if (!linha.isEmpty()){ //non facemos caso das liñas baleiras
                analizarComando(linha); //Procesamos cada comando
            }
        }
        scan.close(); //cerramos o scanner
    }

    /*Metodo que interpreta el comando introducido y toma la accion correspondiente.
    * Parámetro: cadena de caracteres (el comando).
    */
    private void analizarComando(String comando) { //analizar o que mete o usuario na partida
        //eliminamos espacios ao principio e ao final, separamos por espacios as distintas palabras
        String[] partes = comando.trim().split("\\s+"); //\\s cualqueira caracter baleiro, + un ou mais caracteres seguidos

        //comprobamos que se o array está baleiro, polo que a liña tamén o está
        if (partes.length == 0){
            return;
        } //liña baleira

        switch (partes[0].toLowerCase()){ //usamos o primeiro comando en minúsculas para que sexa insensible a minúsculas/maiúsculas
            case "crear": //o comando a empregar é: crear jugador <nombre> <tipoAvatar>
                if (partes.length >= 4 && partes[1].equalsIgnoreCase("xogador")){ //teñen que ser polo menos 4 palabras no comando e que o segundo sexa "xogador"

                    crearJugador(partes[2], partes[3]); //gardamos o nome e tipoAvatar, e creamos o novo xogador
                }
                break;

            case "xogador": //o comando a empregar é: jugador, e mostra o xogador que ten o turno
                if (!jugadores.isEmpty()){ //verificamos que haxa, polo menos, un xogador na partida
                    Jugador actual = jugadores.get(turno); //obtemos o xogador que ten o turno
                    System.out.println("{");
                    System.out.println(" \"Nome\": \"" + actual.getNombre() + "\",");
                    System.out.println(" \"Avatar\": \"" + actual.getAvatar().getId() + "\",");
                    System.out.println("}");
                } else {
                    System.out.println("Non hai xogadores na partida.");
                }
                break;
            case "listar": //o comando a empregar é: listar xogadores
                if (partes.length >=2 && partes[1].equalsIgnoreCase("xogadores")){
                    listarJugadores();
                }
                break;

            default:
                System.out.println("Comando invalido"); //calqueira comando que non sexa algún dos anteriores
        } //AMBIAR ESTO A INICIAR PARTIDA
    }

    /*Metodo que realiza las acciones asociadas al comando 'describir jugador'.
    * Parámetro: comando introducido
     */
    private void descJugador(String[] partes) {
    }

    /*Metodo que realiza las acciones asociadas al comando 'describir avatar'.
    * Parámetro: id del avatar a describir.
    */
    private void descAvatar(String ID) {
    }

    /* Metodo que realiza las acciones asociadas al comando 'describir nombre_casilla'.
    * Parámetros: nombre de la casilla a describir.
    */
    private void descCasilla(String nombre) {
    }

    //Metodo que ejecuta todas las acciones relacionadas con el comando 'lanzar dados'.
    private void lanzarDados() {
    }

    /*Metodo que ejecuta todas las acciones realizadas con el comando 'comprar nombre_casilla'.
    * Parámetro: cadena de caracteres con el nombre de la casilla.
     */
    private void comprar(String nombre) {
    }

    //Metodo que ejecuta todas las acciones relacionadas con el comando 'salir carcel'.
    private void salirCarcel() {
    }

    // Metodo que realiza las acciones asociadas al comando 'listar enventa'.
    private void listarVenta() {
    }

    // Metodo que realiza las acciones asociadas al comando 'listar jugadores'.
    private void listarJugadores() {
    }

    // Metodo que realiza las acciones asociadas al comando 'listar avatares'.
    private void listarAvatares() {
    }

    // Metodo que realiza las acciones asociadas al comando 'acabar turno'.
    private void acabarTurno() {
    }

}
