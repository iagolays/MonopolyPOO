package monopoly;

import java.util.ArrayList;
import partida.*; /*impórtanse todas as clases do paquete*/

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


    private void crearJugador(String nombre, String tipoAvatar) {
        // Buscamos la casilla de salida
        Casilla salida = tablero.encontrar_casilla("Salida");

        // Creamos el jugador
        Jugador jugador = new Jugador(nombre, tipoAvatar, salida, avatares);

        // Añadimos jugador y avatar a los ArrayLists de Menu
        jugadores.add(jugador);
        avatares.add(jugador.getAvatar());

        // Mostramos JSON de confirmación
        System.out.println("{");
        System.out.println("  \"nombre\": \"" + jugador.getNombre() + "\",");
        System.out.println("  \"avatar\": \"" + jugador.getAvatar().getId() + "\"");
        System.out.println("}");
    }

    // Metodo para inciar una partida: crea los jugadores y avatares.
    private void iniciarPartida() {
    }
    
    /*Metodo que interpreta el comando introducido y toma la accion correspondiente.
    * Parámetro: cadena de caracteres (el comando).
    */
    private void analizarComando(String comando) {
        //separaos a linea en palabras empregando espacios
        String[] partes = comando.trim().split("\\s+");

        if (partes.length == 0){
            return;
        } //liña baleira

        switch (partes[0].toLowerCase()){
            case "crear":
                if (partes.length >= 4 && partes[1].equalsIgnoreCase("jugador")){
                    //o comando a empregar é crear jugador <nombre> <tipoAvatar>
                    crearJugador (partes[2], partes[3]);
                }
        }
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
