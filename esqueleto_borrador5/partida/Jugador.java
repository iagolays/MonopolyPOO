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
    private ArrayList<Casilla> hipotecas;
    private ArrayList<String> edificios;

    private boolean enBancarrota;    //0 si el jugador no está en bancarrota; 1 si está en bancarrota

    //Constructor vacío. Se usará para crear la banca.
    public Jugador() {
        this.nombre = "Banca";
        this.fortuna = 0;
        this.gastos = 0;
        this.enCarcel  = false;
        this.tiradasCarcel = 0;
        this.vueltas = 0;
        this.propiedades = new ArrayList<>();
        this.hipotecas = new ArrayList<>();
        this.edificios = new ArrayList<>();
        this.enBancarrota = false;
        //A banca non ten avatar nin pode estar en bancarrota

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
        this.hipotecas = new ArrayList<>();
        this.edificios = new ArrayList<>();
        this.enBancarrota = false;       //Non é posible que un xogador empece a partida estando en bancarrota

        //creamos un avatar asociado
        this.avatar = new Avatar(tipoAvatar, this, inicio, avCreados);
    }

    //Otros métodos:
    //Metodo para añadir una propiedad al jugador. Como parámetro, la casilla a añadir.
    public void anhadirPropiedad(Casilla casilla) {
        if (!propiedades.contains(casilla)) { //compóbase que o xogador non teña esa propiedade
            propiedades.add(casilla);
            System.out.println((this.nombre + "adquiere" + casilla.getNombre()));
        }
    }

    //Metodo para eliminar una propiedad del arraylist de propiedades de jugador.
    public void eliminarPropiedad(Casilla casilla) {
        if (propiedades.contains(casilla)){
            propiedades.remove(casilla);
            System.out.println((this.nombre + "pierde" + casilla.getNombre()));
        }

    }

    //Metodo para añadir fortuna a un jugador
    //Como parámetro se pide el valor a añadir. Si hay que restar fortuna, se pasaría un valor negativo.
    public void sumarFortuna(float valor) {
        this.fortuna += valor;
        if (valor > 0){
            System.out.println(this.nombre + "recibe" + valor + "€. Su fortuna es: " + this.fortuna + "€");
        } else {
            System.out.println(this.nombre + "paga" + (-valor) + "€. Su fortuna es: " + this.fortuna + "€");
        }
    }

    //Metodo para sumar gastos a un jugador.
    //Parámetro: valor a añadir a los gastos del jugador (será el precio de un solar, impuestos pagados...).
    public void sumarGastos(float valor) {
        this.gastos += valor;
    }

    /*Metodo para establecer al jugador en la cárcel.
     * Se requiere disponer de las casillas del tablero para ello (por eso se pasan como parámetro).*/
    public void encarcelar(ArrayList<ArrayList<Casilla>> pos) {
        this.enCarcel = true;
        this.tiradasCarcel = 0;

        //buscar casilla carcel é mover o avatar alí
        Casilla carcel = null;
        for (ArrayList<Casilla> lado : pos){
            for (Casilla casilla : lado){
                if (casilla.getNombre().equalsIgnoreCase("Carcel")){
                    carcel = casilla;
                    break;
                }
            }
            if (carcel != null){
                break;
            }
        }
        if (carcel != null && this.avatar != null){
            //movemos o avatar á carcel
            this.avatar.getLugar().eliminarAvatar(this.avatar);
            this.avatar.moverAvatar("carcel", tiradasCarcel);
            carcel.anhadirAvatar(this.avatar);
        }
        System.out.println((this.nombre + "va a la carcel"));
    }

    //Metodo para saber si un jugador tiene suficiente dinero para pagar, y declarar en bancarrota si no puede
    public boolean puedePagar(float valor) {
        // Se o xogador ten que facer un pago, é necesario comprobar se o diñeiro que ten é suficiente
       if(this.fortuna + valor >= 0) {         //Se al restar el valor que hay que pagar, sigue quedando un valor positivo, entonces se puede pagar
           return true;
       }else{    //Si al restar el valor queda un valor negativo, el jugador actual no tiene dinero para pagar
           this.enBancarrota = true;       //Se declara al jugador en bancarrota
           return false;          //No puede pagar, se devuelve false
       }
    }

    public String getNombre() {
        return nombre;
    }
    // Getter para el avatar
    public Avatar getAvatar() {
        return this.avatar;
    }

    public float getFortuna() {
        return fortuna;
    }

    public ArrayList<Casilla> getPropiedades() {
        return propiedades;
    }

    public ArrayList<Casilla> getHipotecas() {
        return hipotecas;
    }

    public void setHipotecas(ArrayList<Casilla> hipotecas) {
        this.hipotecas = hipotecas;
    }

    public ArrayList<String> getEdificios() {
        return edificios;
    }

    public void setEdificios(ArrayList<String> edificios) {
        this.edificios = edificios;
    }

    public void anhadirHipoteca(Casilla casilla) {
        if (!hipotecas.contains(casilla)) {
            hipotecas.add(casilla);
        }
    }

    public void anhadirEdificio(String edificio) {
        edificios.add(edificio);
    }


}

