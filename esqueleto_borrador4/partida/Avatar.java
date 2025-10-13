package partida;

import monopoly.*;

import java.util.ArrayList;
import java.util.Random;


public class Avatar {

    //Atributos
    private String id; //Identificador: una letra generada aleatoriamente.
    private String tipo; //Sombrero, Esfinge, Pelota, Coche
    private Jugador jugador; //Un jugador al que pertenece ese avatar.
    private Casilla lugar; //Los avatares se sitúan en casillas del tablero.

    //Constructor vacío
    public Avatar() {

    }

    /*Constructor principal. Requiere éstos parámetros:
    * Tipo del avatar, jugador al que pertenece, lugar en el que estará ubicado, y un arraylist con los
    * avatares creados (usado para crear un ID distinto del de los demás avatares).
     */

    public Avatar(String tipo, Jugador jugador, Casilla lugar, ArrayList<Avatar> avCreados) {
        this.tipo = tipo;
        this.jugador = jugador;
        this.lugar = lugar;
        this.generarId(avCreados); //xenera un ID único (A-Z y 0-9) que non estéa repetido en avCreados
        avCreados.add(this);
    }

    //A continuación, tenemos otros métodos útiles para el desarrollo del juego.
    /*Metodo que permite mover a un avatar a una casilla concreta. Parámetros:
    * - Un array con las casillas del tablero. Se trata de un arrayList de arrayList de casillas (uno por lado).
    * - Un entero que indica el numero de casillas a moverse (será el valor sacado en la tirada de los dados).
    * EN ESTA VERSIÓN SUPONEMOS QUE valorTirada siemrpe es positivo.
     */
    public void moverAvatar(ArrayList<ArrayList<Casilla>> casillas, int valorTirada) {
    }

    /*Metodo que permite generar un ID para un avatar. Sólo lo usamos en esta clase (por ello es privado).
    * El ID generado será una letra mayúscula. Parámetros:
    * - Un arraylist de los avatares ya creados, con el objetivo de evitar que se generen dos ID iguales.
     */
    private void generarId(ArrayList<Avatar> avCreados) {
        java.util.Random rand = new java.util.Random(); //xenera números aleatorios
        char nuevoId = 'A'; //gardamos o caracter que queremos usar para movernos
        boolean idValido = false; //indica que o ID non está repetido

        //buscamos mentras o ID non sexa válido
        while (!idValido){
            //xeramos números aleatorios entre 0 35, letras maiusculas + numeros
            int r = rand.nextInt(36);

            //si r < 26, usamos unha letra da A-Z
            if (r < 26){
                nuevoId = (char) ('A' + r);
            }
            //si r >= 26, usamos os números de 0-9
            else{
                nuevoId = (char) ('0' + (r - 26));
            }

            //comprobamod que o ID non estea en uso
            idValido = true;
            for (Avatar av : avCreados){
                if (av.id != null && av.id.charAt(0) == nuevoId){
                    idValido = false; //está repetido e temos que buscr outro
                    break; //non fai falta seguir buscando
                }
            }
        }
        //Asignamos o ID ao avatar
        this.id = String.valueOf(nuevoId);
    }

    public String getId() {
        return id;
    }

    public Jugador getJugador() {
        return jugador;
    }
}
