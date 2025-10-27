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
    private int doblesConsecutivos; //contador de dobles consecutivos para cada xogador


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
        this.doblesConsecutivos = 0; //inicalizamos a 0
    }

    //Otros métodos:
    //Metodo para añadir una propiedad al jugador. Como parámetro, la casilla a añadir.
    public void anhadirPropiedad(Casilla casilla) {
        if (casilla != null && !propiedades.contains(casilla)) { //compóbase que un xogador non teña esa propiedade
            this.propiedades.add(casilla);
            casilla.setDuenho(this);
        }
    }

    //Metodo para eliminar una propiedad del arraylist de propiedades de jugador.
    public void eliminarPropiedad(Casilla casilla) {
        if (casilla!= null){
            this.propiedades.remove(casilla); //non se comproba se esta nas propiedades do xogador porque faino remove directamente
        }

    }

    //Metodo para añadir fortuna a un jugador
    //Como parámetro se pide el valor a añadir. Si hay que restar fortuna, se pasaría un valor negativo.
    public void sumarFortuna(float valor) {
        this.fortuna += valor;
        if (valor < 0) {
            this.sumarGastos(Math.abs(valor)); // Só sumar gastos se é negativo (pago)
            //Math.abs é o valor absoluto
        } else if (this.fortuna < 0){
            this.fortuna = 0; //se a fortuna é negativa establecemos a 0
            this.enBancarrota = true; //pasa a estar en bancarrota porque "debe"
        }
    }

    //Metodo para sumar gastos a un jugador.
    //Parámetro: valor a añadir a los gastos del jugador (será el precio de un solar, impuestos pagados...).
    public void sumarGastos(float valor) {
        this.gastos += valor;
    }

    /*Metodo para establecer al jugador en la cárcel.
     * Se requiere disponer de las casillas del tablero para ello (por eso se pasan como parámetro).*/
    public void encarcelar(ArrayList<ArrayList<Casilla>> pos, Tablero tablero) {
        this.enCarcel = true;
        this.tiradasCarcel = 0;

        //buscar casilla carcel
        Casilla carcel = tablero.encontrar_casilla("Carcel");

        // Movemos o avatar ao cárcere
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
        //se o diñeiro que ten é maior ca o que debe pagar entón pode pagar
        return this.fortuna >= valor;
    }

    // Pagar a outro xogador automaticamente
    public boolean pagarJugador(Jugador receptor, float cantidad, Tablero tablero) {
        if (this.puedePagar(cantidad)) {
            this.sumarFortuna(-cantidad); //restamos o que ten que pagar
            receptor.sumarFortuna(cantidad); //sumamos ao que lle debe
            return true;
        } else {
            this.declararBancarrota(tablero);
            return false;
        }
    }

    // Para o comando "salir cárcel"
    public void salirCarcel(Tablero tablero) {
        if (this.enCarcel && this.puedePagar(Valor.CARCEL_SALIDA)) { //se está no cárcere e pode pagar a saida
            this.sumarFortuna(-Valor.CARCEL_SALIDA); //réstaselle o que debe pagar
            this.enCarcel = false; //sae do cárcere
            this.tiradasCarcel = 0;
            System.out.println(this.nombre + " paga " + (int)Valor.CARCEL_SALIDA + "€ e sae do cárcere. Pode lanzar os dados.");
        } else if (this.enCarcel && !this.puedePagar(Valor.CARCEL_SALIDA)) { //se non pode pagar a saida do cárcere
            System.out.println(this.nombre + " non ten diñeiro para sair do cárcere.");
            this.declararBancarrota(tablero);
        } else {
            System.out.println(this.nombre + " non está no cárcere.");
        }
    }

    // Metodo que se chama no turno para lanzar dados estando no cárcere
    public boolean intentarSalirCarcel(Dado dado1, Dado dado2, Tablero tablero) {
        if (!enCarcel){
            return true; // non está en cárcere, pode mover
        }

        tiradasCarcel++; // incremento turno en cárcere

        int v1 = dado1.hacerTirada();
        int v2 = dado2.hacerTirada();

        if (v1 == v2) { // sacou dobles
            System.out.println(nombre + " saca dobles e sae do cárcere sen pagar.");
            salirCarcel(tablero);
            enCarcel = false; //sae do carcere
            tiradasCarcel = 0; //se reinician
            return true; // pode mover

        } else if (tiradasCarcel >= 3) { // 3 turnos sen sacar dobles, paga necesariamente
            if (puedePagar(Valor.CARCEL_SALIDA)) {
                System.out.println(nombre + " non sacou dobles en 3 turnos.");
                salirCarcel(tablero);
                return true; // pode mover
            } else {
                this.declararBancarrota(tablero);
                return false; // non pode mover
            }
        }
        return false; // segue no cárcere
    }

    // Declara ao xogador en bancarrota
    public void declararBancarrota(Tablero tablero) {
        if (this.enBancarrota){
            return; // Se xa está en bancarrota, non facemos nada
        }
        enBancarrota = true; // Marcamos ao xogador en bancarrota
        fortuna = 0;

        // Liberamos propiedades (volven á banca)
        for (Casilla c : propiedades) {
            c.setDuenho(tablero.getBanca());
        }
        propiedades.clear(); //clear elimina todos os elementos do arrayList

        // Liberamos hipotecas
        hipotecas.clear();

        // Liberamos edificios
        edificios.clear();

        // Movemos avatar a null (ou deixámolo no tablero sen acción)
        if (avatar != null) {
            Casilla lugarActual = avatar.getLugar();
            if (lugarActual != null) {
                lugarActual.eliminarAvatar(avatar);
            }
            avatar = null; // O xogador xa non participa
        }
    }

    public String infoJugador(){
        StringBuilder info = new StringBuilder();
        info.append("{\n");

        info.append("nome: ").append(nombre).append("\n");
        info.append("avatar: ").append(avatar.getId()).append("\n");
        info.append("fortuna: ").append((int)fortuna).append("\n");
        info.append("propiedades: [");
        int count = 0; // contador para engadir comas entre elementos
        for (Casilla p : propiedades){
            info.append(p.getNombre());
            if (++count < propiedades.size()){
                info.append(", ");
            }
        }
        info.append("]\n");
        info.append("hipotecas: [");
        count = 0; //reiniciamos
        for (Casilla h : hipotecas){
            info.append(h.getNombre());
            if (++count < hipotecas.size()){
                info.append(", ");
            }
        }
        info.append("]\n");
        info.append("edificios: [");
        count = 0; //reiniciamos
        for (String e :  edificios){
            info.append(e);
            if (++count < edificios.size()){
                info.append(", ");
            }
        }
        info.append("]");
        System.out.println("}");
        return info.toString(); // devolvemos a información como String
    }

    // Incrementa o contador de dobles
    public void incrementarDobles() {
        this.doblesConsecutivos++;
    }

    // Resetea o contador de dobles
    public void resetearDobles() {
        this.doblesConsecutivos = 0;
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

    public int getTiradasCarcel() {
        return tiradasCarcel;
    }

    public boolean isEnCarcel() {
        return enCarcel;
    }

    public int getDoblesConsecutivos() {
        return doblesConsecutivos;
    }

    public void setEnCarcel(boolean enCarcel) {
        this.enCarcel = enCarcel;
    }

    public boolean isEnBancarrota() {
        return enBancarrota;
    }

    public ArrayList<Casilla> getPropiedades() {
        return propiedades;
    }

    public ArrayList<String> getEdificios() {
        return edificios;
    }

    public void anhadirEdificio(String id) {
        edificios.add(id);
    }

    @Override
    public String toString() {
        return "{nombre: " + nombre +", avatar: " + avatar.getId() +
                ", fortuna: " + (int)fortuna + ", propiedades: " + propiedades.size() +
                ", hipotecas: " + hipotecas.size() + ", edificios: " + edificios.size() + "}";
    }

}

