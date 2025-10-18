package monopoly;

import partida.*;
import java.util.ArrayList;

public class Casilla {

    //Atributos:
    private String nombre; //Nombre de la casilla
    private String tipo; //Tipo de casilla (Solar, Especial, Transporte, Servicios, Comunidad, Suerte y Impuesto).
    private float valor; //Valor de esa casilla (en la mayoría será valor de compra, en la casilla parking se usará como el bote).
    private int posicion; //Posición que ocupa la casilla en el tablero (entero entre 1 y 40).
    private Jugador duenho; //Dueño de la casilla (por defecto sería la banca).
    private Grupo grupo; //Grupo al que pertenece la casilla (si es solar).
    private float impuesto; //Cantidad a pagar por caer en la casilla: el alquiler en solares/servicios/transportes o impuestos.
    private float hipoteca; //Valor otorgado por hipotecar una casilla
    private ArrayList<Avatar> avatares; //Avatares que están situados en la casilla.
    private boolean hipotecada;  //false se no nestá hipotecada ou non se pode,true si está hipotecada

    //Constructores:
    public Casilla() {
        this.duenho = null; //Inicialmente non ten dono
        this.avatares = new ArrayList<>();
        this.hipotecada = false; //inicialmente non está hipotecada

    }//Parámetros vacíos (definición de una nueva casilla)

    /*Constructor para casillas tipo Solar, Servicios o Transporte:
     * Parámetros: nombre casilla, tipo (debe ser solar, serv. o transporte), posición en el tablero, valor y dueño.
     */
    public Casilla(String nombre, String tipo, int posicion, float valor, Jugador duenho) {

        this.nombre = nombre;             //Definimos los parámetros que se nos pasan como argumentos de la función...
        this.tipo = tipo;
        this.posicion = posicion;
        this.valor = valor;
        this.duenho = duenho;
        this.hipotecada = false;
        this.avatares = new ArrayList<>();

        // Establecemos valores por defecto segundo o tipo
        if ("Solar".equalsIgnoreCase(tipo)) {
            this.impuesto = Solares.obtenerAlquiler(nombre);
        } else if ("Transporte".equalsIgnoreCase(tipo)) {
            this.impuesto = Valor.TRANSPORTE_ALQUILER; // Aluguer base para transportes
        } else if ("Servicio".equalsIgnoreCase(tipo)) {
            this.impuesto = 0; // Calcúlase baseado nos dados
        }

    }

    /*Constructor utilizado para inicializar las casillas de tipo IMPUESTOS.
     * Parámetros: nombre, posición en el tablero, impuesto establecido y dueño.
     */
    public Casilla(String nombre, int posicion, float impuesto, Jugador duenho) {

        this.nombre = nombre;
        this.tipo = "Impuesto";
        this.posicion = posicion;
        this.impuesto = impuesto;
        this.duenho = duenho;

        this.valor = 0; //non teñen valor de compra
        this.avatares = new ArrayList<>();
        this.hipotecada = false;
    }

    /*Constructor utilizado para crear las otras casillas (Suerte, Caja de comunidad y Especiales):
     * Parámetros: nombre, tipo de la casilla (será uno de los que queda), posición en el tablero y dueño.
     */
    public Casilla(String nombre, String tipo, int posicion, Jugador duenho) {

        this.nombre = nombre;
        this.tipo = tipo;
        this.posicion = posicion;
        this.duenho = duenho;

        this.valor = 0; //non teñen valor de compra
        this.impuesto = 0; //non cobran aluguer
        this.avatares = new ArrayList<>();
        this.hipotecada = false;

        //Valor especial para o Parking que acumula o bote
        if ("Parking".equalsIgnoreCase(tipo)) {
            this.valor = 0; //inicialmente está a 0 o bote
        }
    }

    //Metodo utilizado para añadir un avatar al array de avatares en casilla.
    public void anhadirAvatar(Avatar av) {
        if (av != null && !avatares.contains(av)) { //Comprobamos que o avatar a engadir ao array de avatares non está (no queremos duplicados)
            avatares.add(av); //Se non está, engádese
        } else {
            System.out.println("O avatar xa estaba nesta casilla");
        }
    }

    //Metodo utilizado para eliminar un avatar del array de avatares en casilla.
    public void eliminarAvatar(Avatar av) {
        if (av != null && avatares.contains(av)) { //Comprobamos que o avatar está no array
            avatares.remove(av);        //Se está en la lista de avatares, eliminámolo
        } else {
            System.out.println("Non se pode eliminar o avatar");
        }
    }

    /*Metodo para evaluar qué hacer en una casilla concreta. Parámetros:
     * - Jugador cuyo avatar está en esa casilla.
     * - La banca (para ciertas comprobaciones).
     * - El valor de la tirada: para determinar impuesto a pagar en casillas de servicios.
     * Valor devuelto: true en caso de ser solvente (es decir, de cumplir las deudas), y false
     * en caso de no cumplirlas.*/
    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada) {

        //Segundo o tipo de casilla avalíase de maneira diferente
        switch (tipo.toLowerCase()) {
            case "solar":
                return evaluarSolar(actual, banca);
            case "transporte":
                return evaluarTransporte(actual, banca);
            case "servicio":
                return evaluarServicio(actual, banca, tirada);
            case "impuesto":
                return pagarImpuesto(actual, banca);
            case "suerte":
                return realizarCartaSuerte(actual, banca);
            case "comunidad":
                return realizarCartaComunidad(actual, banca);
            case "parking":
                return cobrarParking(actual, banca);
            case "ircarcel":
                irACarcel(actual);
                return true;
            case "carcel":
                return true;
            case "salida":
                actual.sumarFortuna(Valor.SUMA_VUELTA);
                return true;
            default:
                System.out.println("Tipo de casilla non recoñecida: " + nombre);
                return true;
        }
    }

    /*Metodo usado para comprar una casilla determinada. Parámetros:
     * - Jugador que solicita la compra de la casilla.
     * - Banca del monopoly (es el dueño de las casillas no compradas aún).*/
    public void comprarCasilla(Jugador solicitante, Jugador banca){
        if (duenho == null || duenho == banca) { //se non ten dono ou o dono é a banca pódese comprar
            if (solicitante.puedePagar(valor)) {
                solicitante.sumarFortuna(-valor); // Restamos o valor da fortuna do xogador
                this.duenho = solicitante; // Asignamos o dueño
                solicitante.anhadirPropiedad(this); // Engadimos a casilla ás propiedades do xogador

                System.out.println("O xogador " + solicitante.getNombre() + " compra a casilla " + nombre + " por " +
                        (int)valor + "€. A súa fortuna actual é " + (int)solicitante.getFortuna() + "€.");
            } else {
                System.out.println(solicitante.getNombre() + " non ten diñeiro suficiente para comprar " + nombre + ".");
            }
        } else {
            System.out.println("A casilla " + nombre + " xa ten dono: " + duenho.getNombre());
        }
    }

    /*Metodo para añadir valor a una casilla. Utilidad:
     * - Sumar valor a la casilla de parking.
     * - Sumar valor a las casillas de solar al no comprarlas tras cuatro vueltas de todos los jugadores.
     * Este metodo toma como argumento la cantidad a añadir del valor de la casilla.*/
    public void sumarValor(float suma) {
        this.valor += suma;
    }

    /*Metodo para mostrar información sobre una casilla.
     * Devuelve una cadena con información específica de cada tipo de casilla.*/
    public String infoCasilla() {
        StringBuilder info = new StringBuilder();
        info.append("{\n");

        switch (tipo.toLowerCase()) {
            case "solar":
                // Obter todos os datos do solar dunha vez, da clase Solares
                Solares.DatosSolar datos = Solares.obtenerDatos(this.nombre);

                if (datos != null) {
                    info.append("tipo: Solar,\n");
                    info.append("grupo: ").append(grupo != null ? grupo.getColorGrupo() : "-").append(",\n");
                    info.append("propietario: ").append(duenho != null ? duenho.getNombre() : "Banca").append(",\n");
                    info.append("valor: ").append((int) datos.precio).append(",\n");
                    info.append("alquiler: ").append((int) datos.alquiler).append(",\n");
                    info.append("valor hotel: ").append((int) datos.valorHotel).append(",\n");
                    info.append("valor casa: ").append((int) datos.valorCasa).append(",\n");
                    info.append("valor piscina: ").append((int) datos.valorPiscina).append(",\n");
                    info.append("valor pista de deporte: ").append((int) datos.valorPista).append(",\n");
                    info.append("alquiler casa: ").append((int) datos.alquilerCasa).append(",\n");
                    info.append("alquiler hotel: ").append((int) datos.alquilerHotel).append(",\n");
                    info.append("alquiler piscina: ").append((int) datos.alquilerPiscina).append(",\n");
                    info.append("alquiler pista de deporte: ").append((int) datos.alquilerPista).append("\n");
                }

                break;
            case "transporte":
            case "servicio":
                info.append("tipo: ").append(tipo).append(",\n");
                info.append("propietario: ").append(duenho != null ? duenho.getNombre() : "Banca").append(",\n");
                info.append("valor: ").append((int) valor).append(",\n");
                break;
            case "impuesto":
                info.append("tipo: Impuesto,\n");
                info.append("apagar: ").append((int) impuesto).append("\n");
                break;
            case "parking":
                info.append("bote: ").append((int) valor).append(",\n");
                info.append("jugadores: [");
                for (int i = 0; i < avatares.size(); i++) {
                    info.append(avatares.get(i).getJugador().getNombre());
                    if (i < avatares.size() - 1){
                        info.append(", ");
                    }
                }
                info.append("]\n");
                break;
            case "carcel":
                info.append("salir: ").append((int) Valor.CARCEL_SALIDA).append(",\n");
                info.append("jugadores: [");
                for (int i = 0; i < avatares.size(); i++) {
                    info.append(avatares.get(i).getJugador().getNombre());
                    if (i < avatares.size() - 1) info.append(", ");
                }
                info.append("]\n");
                break;
        }
        info.append("}");
        return info.toString();
    }


    /* Metodo para mostrar información de una casilla en venta.
     * Valor devuelto: texto con esa información.
     */
    public String casEnVenta() {
        if ((duenho == null || "Banca".equalsIgnoreCase(duenho.getNombre())) && "solar".equalsIgnoreCase(tipo) ||
                "transporte".equalsIgnoreCase(tipo) || "servicio".equalsIgnoreCase(tipo)) {

            return "{\n" + "nome: " + this.nombre + ",\n" + "tipo: " + this.tipo +
                    (grupo != null ? ",\ngrupo: " + grupo.getColorGrupo() : "") +
                    ",\nvalor: " + (int)this.valor + "\n" +"}";
        }
        return "";
    }

    //----------Metodos auxiliares

    // Avalía un solar, é privado polo que só pode ser chamado dende Casilla
    private boolean evaluarSolar(Jugador actual, Jugador banca) {

        // 1) Se non ten dono ou pertence á banca, o xogador pode mercar
        if (duenho == null || duenho == banca) {
            float precioCompra = Solares.obtenerPrecio(nombre);
            System.out.println("O solar está dispoñible para comprar por " + (int)precioCompra + "€");
            return true;
        }
        // Se o dono é o propio xogador
        else if (duenho == actual) {
            System.out.println("É o dono desta propiedade");
            return true;
        }
        // 2) Se ten dono e non está hipotecado, págase aluguer
        else if (!hipotecada) {
            return actual.pagarJugador(duenho, impuesto);
        }
        // 3) Se está hipotecado, non se paga aluguer
        else {
            System.out.println("O solar está hipotecado, non se paga aluguer.");
            return true;
        }
    }

    // Avalía unha casilla de transporte
    private boolean evaluarTransporte(Jugador actual, Jugador banca) {
        if (duenho == null || duenho == banca) {
            System.out.println("O transporte está dispoñible para comprar por " + (int)Valor.TRANSPORTE_PRECIO + "€");
            return true;
        } else if (duenho == actual) {
            System.out.println("É o dono deste transporte");
            return true;
        }
        else if (!hipotecada) {
            return actual.pagarJugador(duenho, Valor.TRANSPORTE_PRECIO);
        }
        return true; //falta engadir se está hipotecada pero en próximas entregas
    }

    // Avalía unha casilla de servizo
    private boolean evaluarServicio(Jugador actual, Jugador banca, int tirada) {
        if (duenho == null || duenho == banca) {
            return true;
        } else if (duenho == actual) {
            System.out.println("É o dono deste servizo");
            return true;
        }
        else if (!hipotecada) {
            //Nesta entrega o alquiler é catro veces os dados polo factor de servicio

            float pago = tirada * Valor.FACTOR_SERVICIO * 4;
            return actual.pagarJugador(duenho, pago);
        }
        return true; //falta engadir se está hipotecada pero en próximas entregas
    }

    // Avalía imposto
    private boolean pagarImpuesto(Jugador actual, Jugador banca) {
        if (actual.puedePagar(impuesto)) {
            actual.sumarFortuna(-impuesto);
            //Engádese ao parking
            return true;
        } else{
            //System.out.println(actual.getNombre() + " non pode pagar o imposto!");
            //System.out.println("Debe hipotecar propiedades ou declararse en bancarrota");
            return false;
        }
    }

    // Avalía cartas de sorte
    private boolean realizarCartaSuerte(Jugador actual, Jugador banca) {
        System.out.println("Saca unha carta de Sorte!");
        // Aquí poderiamos implementar efectos concretos na proxima entrega
        return true;
    }

    // Avalía cartas de comunidad
    private boolean realizarCartaComunidad(Jugador actual, Jugador banca) {
        System.out.println("Saca unha carta de Comunidade!");
        //Imlementanse efectos concretos na próxima entrega
        return true;
    }

    // Cobrar o bote do Parking
    private boolean cobrarParking(Jugador actual, Jugador banca) {
        if (valor > 0) {
            System.out.println(actual.getNombre() + " recibe o bote do Parking: " + (int)valor + "€");
            actual.sumarFortuna(valor);
            valor = 0; // Reiniciamos o bote
        } else {
            System.out.println("O Parking está baleiro");
        }
        return true;
    }

    // Metodo público para acceder á evaluación desde outras clases
    public boolean evaluarServicioPublico(Jugador actual, Jugador banca, int tirada) {
        if ("servicio".equalsIgnoreCase(this.tipo)) {
            return evaluarServicio(actual, banca, tirada);
        }
        return true;
    }

    private void irACarcel(Jugador actual) {
        actual.setEnCarcel(true);
    }

    public String getAvataresString(){
        if (avatares == null || avatares.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for(Avatar av : avatares){
            sb.append(av.getId()).append(" ");
        }
        return sb.toString();
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public float getValor() {
        return valor;
    }

    public int getPosicion() {
        return posicion;
    }

    public Jugador getDuenho() {
        return duenho;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public float getImpuesto() {
        return impuesto;
    }

    public float getHipoteca() {
        return hipoteca;
    }

    public ArrayList<Avatar> getAvatares() {
        return avatares;
    }

    public boolean isHipotecada() {
        return hipotecada;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public void setDuenho(Jugador duenho) {
        this.duenho = duenho;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public void setImpuesto(float impuesto) {
        this.impuesto = impuesto;
    }

    public void setHipoteca(float hipoteca) {
        this.hipoteca = hipoteca;
    }

    public void setAvatares(ArrayList<Avatar> avatares) {
        this.avatares = avatares;
    }

    public void setHipotecada(boolean hipotecada) {
        this.hipotecada = hipotecada;
    }
}