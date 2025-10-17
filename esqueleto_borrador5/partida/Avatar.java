package partida;

import monopoly.*;

import java.util.ArrayList;
import java.util.Random;


public class Avatar {

    //Atributos
    private String id; //Identificador: una letra generada aleatoriamente.
    private String tipo; //Sombrero, Esfinge, Pelota, Coche
    private Jugador jugador; //Un jugador al que pertenece ese avatar, cada avatar pertenece a un único jugador.
    private Casilla lugar; //Los avatares se sitúan en casillas del tablero.

    //Constructor vacío
    public Avatar() {

    }

    /*Constructor principal. Requiere éstos parámetros:
    * Tipo del avatar, jugador al que pertenece, lugar en el que estará ubicado, y un arraylist con los
    * avatares creados (usado para crear un ID distinto del de los demás avatares).
    */

    public Avatar(String tipo, Jugador jugador, Casilla lugar, ArrayList<Avatar> avCreados) {
        //Asignamos os parámetros recividos os atributos do obxecto
        this.tipo = tipo;
        this.jugador = jugador;
        this.lugar = lugar;
        this.generarId(avCreados); //xenera un ID único (A-Z y 0-9) que non estéa repetido en avCreados
        avCreados.add(this); //engadimos o avatar a unha lista de avatares creados

        // Engadimos o avatar á casilla inicial (se o metodo existe)
        if (this.lugar != null) {
            this.lugar.anhadirAvatar(this);
        }
    }

    //A continuación, tenemos otros métodos útiles para el desarrollo del juego.
    /*Metodo que permite mover a un avatar a una casilla concreta. Parámetros:
    * - Un array con las casillas del tablero. Se trata de un arrayList de arrayList de casillas (uno por lado).
    * - Un entero que indica el numero de casillas a moverse (será el valor sacado en la tirada de los dados).
    * EN ESTA VERSIÓN SUPONEMOS QUE valorTirada siemrpe es positivo.
     */
    public void moverAvatar(ArrayList<ArrayList<Casilla>> casillas, int valorTirada) {
        //vamos a crear unha lista que conteña en orden todos os lados do taboleiro para que sea mais comodo traballar
        ArrayList<Casilla> lineal = new ArrayList<>();
        for(ArrayList<Casilla> lado : casillas){
            lineal.addAll(lado); // Engadimos todas as casillas de cada lado á lista
        }
        //collemos a posicion actual do avatar
        int posicionActual = lineal.indexOf(lugar);

        // Gardamos o nome da casilla antiga (para imprimir logo)
        String casillaAnterior = lugar.getNombre();

        // Calculamos a nova posición aplicando módulo (volta ao inicio se chega ao final)
        int novaPos = (posicionActual + valorTirada) % lineal.size();

        //desplazamos o avatar
        lugar.eliminarAvatar(this); //primeiro eliminamos o anterior da casilla actual
        lugar = lineal.get(novaPos); //Actualizamos referencia a nova casilla
        lugar.anhadirAvatar(this); //Engadimos avatar a nova casilla

        System.out.println("O avatar " + this.id + " avanza " + valorTirada +
                " posicións, desde " + casillaAnterior + " ata " + lugar.getNombre() + ".");
    }

    /*Metodo que permite generar un ID para un avatar. Sólo lo usamos en esta clase (por ello es privado).
    * El ID generado será una letra mayúscula. Parámetros:
    * - Un arraylist de los avatares ya creados, con el objetivo de evitar que se generen dos ID iguales.
     */
    private void generarId(ArrayList<Avatar> avCreados) {
        Random rand = new Random(); //xenera números aleatorios
        String novoId = "";
        boolean idValido = false; //indica que o ID non está repetido

        //buscamos mentras o ID non sexa válido
        do {
            //xeramos números aleatorios entre 0-35, letras maiusculas + numeros
            int r = rand.nextInt(36);

            //si r < 26, usamos unha letra da A-Z
            if (r < 26){
                novoId = String.valueOf((char) ('A' + r)); // Convertimos o número a letra (0--A, 1--B)
            }
            //si r >= 26, usamos os números de 0-9
            else{
                novoId = String.valueOf((char) ('0' + (r -26))); // Convertimos o número a díxito (26--0, 27--1)
            }

            //comprobamos que o ID non estea en uso
            idValido = true; //asumimos que é válido inicialmente
            for (Avatar av : avCreados){
                if (av.getId().equalsIgnoreCase(novoId)){
                    idValido = false; //está repetido e temos que buscar outro
                    break; //non fai falta seguir buscando
                }
            }
        } while (!idValido);

        //Asignamos o ID ao avatar
        this.id = novoId;
    }

    public String getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public Casilla getLugar() {
        return lugar;
    }

    public void setLugar(Casilla lugar) {
        this.lugar = lugar;
    }

    @Override
    public String toString() {
        // Útil para comandos tipo "listar jugadores"
        return "{avatar: " + this.id + ", tipo: " + this.tipo + "}";
    }
}
