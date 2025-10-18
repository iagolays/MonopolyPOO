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
    private ArrayList<Casilla> hipotecas; //nova
    private ArrayList<String> edificios; //nova
    private boolean enBancarrota;    //novo: 0 si el jugador no está en bancarrota; 1 si está en bancarrota



    //Constructor vacío. Se usará para crear la banca.
    public Jugador() {
        this.nombre = "Banca";
        this.fortuna = 0f; //A banca ten diñeiro ilimitado
        this.gastos = 0f;
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
        this.fortuna = Valor.FORTUNA_INICIAL; //saldo inicial do xogador
        this.gastos = 0f;
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
        if (casilla!= null && !propiedades.contains(casilla)) { //compóbase que un xogador non teña esa propiedade
            propiedades.add(casilla);
            casilla.setDuenho(this);
        }
    }

    //Metodo para eliminar una propiedad del arraylist de propiedades de jugador.
    public void eliminarPropiedad(Casilla casilla) {
        if (casilla!= null && propiedades.contains(casilla)){
            propiedades.remove(casilla);
            System.out.println((this.nombre + " perde a propiedade: " + casilla.getNombre()));
        }

    }

    //Metodo para añadir fortuna a un jugador
    //Como parámetro se pide el valor a añadir. Si hay que restar fortuna, se pasaría un valor negativo.
    public void sumarFortuna(float valor) {
        this.fortuna += valor;
        if (valor < 0) {
            this.sumarGastos(Math.abs(valor)); // Só sumar gastos se é negativo (pago)
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

        //buscar casilla carcel
        Casilla carcel = null;
        for (ArrayList<Casilla> lado : pos){ // Percorremos cada lado do taboleiro
            for (Casilla casilla : lado){ // Percorremos cada casilla do lado
                if ("Carcel".equalsIgnoreCase(casilla.getNombre())){
                    carcel = casilla;
                    break;
                }
            }
            if (carcel != null){
                break; // Se xa atopamos a cárcere, saímos do bucle externo
            }
        }
        // Movemos o avatar á cárcere
        if (carcel != null && this.avatar != null) {
            // Quitamos o avatar da súa casilla actual
            Casilla casillaActual = this.avatar.getLugar();
            if (casillaActual != null) {
                casillaActual.eliminarAvatar(this.avatar);
            }

            // Colocamos o avatar no cárcere
            this.avatar.setLugar(carcel);
            carcel.anhadirAvatar(this.avatar);
        }
    }

    //----------Metodos auxiliares

    //Metodo para saber se un xogador ten suficiente diñeiro para pagar
    public boolean puedePagar(float valor) {
        if(this.fortuna >= valor) { //se o diñeiro que ten é maior ca o que debe pagar entón pode pagar
           return true;
       }else{
           System.out.println(this.nombre + " non ten diñeiro suficiente para pagar " + (int)valor + "€");
           return false; //Non pode pagar pero non se declara en bancarrota por se quere hipotecar
       }
    }

    // Pagar a outro xogador automaticamente
    public boolean pagarJugador(Jugador receptor, float cantidad) {
        if (this.puedePagar(cantidad)) {
            this.sumarFortuna(-cantidad); //restamos o que ten que pagar
            receptor.sumarFortuna(cantidad); //sumamos ao que lle debe
            return true;
        } else {
            System.out.println(this.nombre + " non pode pagar " + (int)cantidad + "€ a " + receptor.nombre);
            System.out.println("Debe hipotecar propiedades ou declararse en bancarrota");
            return false;
        }
    }

    // Para o comando "salir cárcel"
    public boolean salirCarcel() {
        if (this.enCarcel && this.puedePagar(Valor.CARCEL_SALIDA)) { //se está no cárcere e pode pagar a saida
            this.sumarFortuna(-Valor.CARCEL_SALIDA); //réstaselle o que debe pagar
            this.enCarcel = false; //sae do cárcere
            this.tiradasCarcel = 0;
            System.out.println(this.nombre + " paga " + (int)Valor.CARCEL_SALIDA + "€ e sae do cárcere. Pode lanzar os dados.");
            return true;
        } else if (this.enCarcel && !this.puedePagar(Valor.CARCEL_SALIDA)) { //se non pode pagar a saida do cárcere
            System.out.println(this.nombre + " non ten diñeiro para sair do cárcere.");
            return false;
        } else {
            System.out.println(this.nombre + " non está no cárcere.");
            return false;
        }
    }

    // Matodo para bancarrota (solo informativo)
    public void declararBancarrotaInformativa() {
        System.out.println(this.nombre + " debe declararse en bancarrota.");
        System.out.println("Todas as propiedades pasarían a un xogador.");
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

    public ArrayList<String> getEdificios() {
        return edificios;
    }

    public void setFortuna(float fortuna) {
        this.fortuna = fortuna;
    }
    public boolean isEnCarcel() {
        return enCarcel;
    }

    public void setEnCarcel(boolean enCarcel) {
        this.enCarcel = enCarcel;
    }

    public void setTiradasCarcel(int tiradasCarcel) {
        this.tiradasCarcel = tiradasCarcel;
    }

    @Override
    public String toString() {
        return "{nombre: " + nombre +
                ", avatar: " + avatar.getId() +
                ", fortuna: " + fortuna +
                ", propiedades: " + propiedades.size() + "}";
    }

}

