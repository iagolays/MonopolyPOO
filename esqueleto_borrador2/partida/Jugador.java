package partida;

import java.util.ArrayList;

import monopoly.*;


public class Jugador {

    //Atributos:
    private String nombre; //Nombre del jugador
    private Avatar avatar; //Avatar que tiene en la partida.
    private float fortuna; //Dinero que posee.
    private float gastos; //Gastos realizados a lo largo del juego.
    private boolean enCarcel; //Será true si el jugador está en la carcel
    private int tiradasCarcel; //Cuando está en la carcel, contará las tiradas sin éxito que ha hecho allí para intentar salir (se usa para limitar el numero de intentos).
    private int vueltas; //Cuenta las vueltas dadas al tablero.
    private ArrayList<Casilla> propiedades; //Propiedades que posee el jugador.

    //Constructor vacío. Se usará para crear la banca.
    public Jugador() {
        this.nombre = "Banca";
        this.fortuna = 0;
        this.gastos = 0;
        this.enCarcel  = false;
        this.tiradasCarcel = 0;
        this.vueltas = 0;
        this.propiedades = new ArrayList<>();
        //A banca non ten avatar.

    }

    /*Constructor principal. Requiere parámetros:
     * Nombre del jugador, tipo del avatar que tendrá, casilla en la que empezará y ArrayList de
     * avatares creados (usado para dos propósitos: evitar que dos jugadores tengan el mismo nombre y
     * que dos avatares tengan mismo ID). Desde este constructor también se crea el avatar.
     */
    public Jugador(String nombre, String tipoAvatar, Casilla inicio, ArrayList<Avatar> avCreados) {
        //Comprobar que non exista un xogador co mesmo nome ou avatar repetido
        this.nombre = nombre;
        this.fortuna = 15000000; //saldo inicial do xogador
        this.gastos = 0;
        this.enCarcel  = false;
        this.tiradasCarcel = 0;
        this.vueltas = 0;
        this.propiedades = new ArrayList<>();

        //creamos un avatar asociado
        this.avatar = new Avatar(tipoAvatar, this, inicio, avCreados);
    }

    //Otros métodos:
    //Método para añadir una propiedad al jugador. Como parámetro, la casilla a añadir.
    public void anhadirPropiedad(Casilla casilla) {
        propiedades.add(casilla);
    }

    //Método para eliminar una propiedad del arraylist de propiedades de jugador.
    public void eliminarPropiedad(Casilla casilla) {
        propiedades.remove(casilla);
    }

    //Método para añadir fortuna a un jugador
    //Como parámetro se pide el valor a añadir. Si hay que restar fortuna, se pasaría un valor negativo.
    public void sumarFortuna(float valor) {
        this.fortuna += valor;
    }

    //Método para sumar gastos a un jugador.
    //Parámetro: valor a añadir a los gastos del jugador (será el precio de un solar, impuestos pagados...).
    public void sumarGastos(float valor) {
        this.gastos += valor;
    }

    /*Método para establecer al jugador en la cárcel.
     * Se requiere disponer de las casillas del tablero para ello (por eso se pasan como parámetro).*/
    public void encarcelar(ArrayList<ArrayList<Casilla>> pos) {
        this.enCarcel = true;
        this.tiradasCarcel = 0;
    }

    //Método para saber si un jugador tiene suficiente dinero para pagar
    public boolean puedePagar(float valor) {
        // Si el jugador tiene que hacer un pago, hay que comprobar si el dinero que tiene es suficiente
        if (valor < 0) {
            return this.fortuna + valor >= 0;
        }
        // Si el valor es positivo, puede pagar
        return true;
    }


    public String getNombre() {
        return nombre;
    }
    // Getter para el avatar
    public Avatar getAvatar() {
        return this.avatar;
    }

    /////constructor prueba iago
    public Jugador(String nombre) {
        this.nombre = nombre;
        this.fortuna = 15000000;
        this.gastos = 0;
        this.enCarcel = false;
        this.tiradasCarcel = 0;
        this.vueltas = 0;
        this.propiedades = new ArrayList<>();
        this.avatar = null; // aún no tiene avatar
    }

}


