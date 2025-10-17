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
        this.fortuna = Valor.FORTUNA_BANCA;
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
        this.fortuna = Valor.FORTUNA_INICIAL; //saldo inicial do xogador
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
        if (casilla!= null && !propiedades.contains(casilla)) { //compóbase que un xogador non teña esa propiedade
            propiedades.add(casilla);
            System.out.println(this.nombre + " adquire " + casilla.getNombre());
            // IMPORTANTE: Aquí deberíamos tamén establecer o xogador como dono da casilla
        }
    }

    //Metodo para eliminar una propiedad del arraylist de propiedades de jugador.
    public void eliminarPropiedad(Casilla casilla) {
        if (casilla!= null && propiedades.contains(casilla)){
            propiedades.remove(casilla);
            System.out.println((this.nombre + "perde" + casilla.getNombre()));
            // IMPORTANTE: Aquí deberíamos tamén eliminar o xogador como dono da casilla
        }

    }

    //Metodo para añadir fortuna a un jugador
    //Como parámetro se pide el valor a añadir. Si hay que restar fortuna, se pasaría un valor negativo.
    public void sumarFortuna(float valor) {
        this.fortuna += valor;
        if (valor > 0){
            System.out.println(this.nombre + "recibe" + valor + "€. A súa fortuna é: " + this.fortuna + "€");
        } else {
            sumarGastos(Math.abs(valor)); // Usamos valor absoluto para evitar gastos negativos
            System.out.println(this.nombre + "paga" + (-valor) + "€. A súa fortuna é: " + this.fortuna + "€");
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
        for (ArrayList<Casilla> fila : pos){ // Percorremos cada lado do taboleiro
            for (Casilla casilla : fila){ // Percorremos cada casilla do lado
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

            // Colocamos o avatar na cárcere
            this.avatar.setLugar(carcel);
            carcel.anhadirAvatar(this.avatar);
            System.out.println("O xogador " + this.nombre + " foi encarcelado.");
        }
    }

    //Metodo para saber si un jugador tiene suficiente dinero para pagar, y declarar en bancarrota si no puede
    public boolean puedePagar(float valor) {
        // Se o xogador ten que facer un pago, é necesario comprobar se o diñeiro que ten é suficiente
       if(this.fortuna >= valor) {
           return true;
       }else{
           this.enBancarrota = true;       //Declárase ao xogador en bancarrota
           System.out.println(this.nombre + " declárase en BANCARROTA! Non pode pagar " + valor + "€");
           return false;          //Non pode pagar, volvese false
       }
    }
    /**
     * Transfire todas as propiedades dun xogador a outro xogador (por bancarrota)
     * @param acreedor - Xogador que recibirá as propiedades
     */
    public boolean transferirPropiedades(Jugador acreedor) {
        if (acreedor == null) {
            System.out.println("Erro: Acreedor non válido");
            return false;
        }

        System.out.println("Transferindo propiedades de " + this.nombre + " a " + acreedor.getNombre());

        // Contar propiedades antes da transferencia
        int propiedadesIniciais = this.propiedades.size();
        int hipotecasIniciais = this.hipotecas.size();

        // Transferir todas as propiedades
        for (Casilla propiedad : new ArrayList<>(this.propiedades)) {
            // Eliminar da lista do deudor
            this.eliminarPropiedad(propiedad);

            // Engadir á lista do acreedor
            propiedad.setDuenho(acreedor);
            acreedor.anhadirPropiedad(propiedad);

            System.out.println("  - " + propiedad.getNombre() + " transfírese a " + acreedor.getNombre());
        }

        // Transferir hipotecas (se as hai)
        for (Casilla hipoteca : new ArrayList<>(this.hipotecas)) {
            this.hipotecas.remove(hipoteca);
            acreedor.getHipotecas().add(hipoteca);
            System.out.println("  - Hipoteca de " + hipoteca.getNombre() + " transfírese");
        }

        // Marcar como en bancarrota
        this.enBancarrota = true;
        this.fortuna = 0f;

        System.out.println("Transferencia completada.");
        System.out.println("   Propiedades transferidas: " + propiedadesIniciais);
        System.out.println("   Hipotecas transferidas: " + hipotecasIniciais);
        System.out.println("   Fortuna final: " + this.fortuna + "€");
        // Devolver true só se non quedan propiedades
        return this.propiedades.isEmpty() && this.hipotecas.isEmpty();
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

