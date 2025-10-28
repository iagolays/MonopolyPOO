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
    private Solares.DatosEdificios datosedificios; //Datos dos edificios construídos nesta casilla (se é solar)

    //Constructores:
    public Casilla() {
        this.duenho = null; //Inicialmente non ten dono
        this.avatares = new ArrayList<>();
        this.hipotecada = false; //inicialmente non está hipotecada
        this.datosedificios = new Solares.DatosEdificios(); //inicializamos os datos de edificios

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
        this.datosedificios = new Solares.DatosEdificios(); // Inicializar

        // Establecemos valores por defecto segundo o tipo
        if ("Solar".equalsIgnoreCase(tipo)) {
            this.impuesto = Solares.obtenerAlquiler(nombre); //como ObtenerAlquiler é static chámase coa clase, non hai que crear un obxecto
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
        this.datosedificios = new Solares.DatosEdificios(); // Inicializar
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
        this.datosedificios = new Solares.DatosEdificios(); // Inicializar

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
    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada, Tablero tablero) {

        //Segundo o tipo de casilla avalíase de maneira diferente
        switch (tipo.toLowerCase()) {
            case "solar":
                return evaluarSolar(actual, banca, tablero);
            case "transporte":
                return evaluarTransporte(actual, banca, tablero);
            case "servicio":
                return evaluarServicio(actual, banca, tirada, tablero);
            case "impuesto":
                return pagarImpuesto(actual, banca, tablero);
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
                    info.append("valor: ").append((int) datos.getPrecio()).append(",\n");
                    info.append("alquiler: ").append((int) datos.getAlquiler()).append(",\n");
                    info.append("valor hotel: ").append((int) datos.getValorHotel()).append(",\n");
                    info.append("valor casa: ").append((int) datos.getValorCasa()).append(",\n");
                    info.append("valor piscina: ").append((int) datos.getValorPiscina()).append(",\n");
                    info.append("valor pista de deporte: ").append((int) datos.getValorPista()).append(",\n");
                    info.append("alquiler casa: ").append((int) datos.getAlquilerCasa()).append(",\n");
                    info.append("alquiler hotel: ").append((int) datos.getAlquilerHotel()).append(",\n");
                    info.append("alquiler piscina: ").append((int) datos.getAlquilerPiscina()).append(",\n");
                    info.append("alquiler pista de deporte: ").append((int) datos.getAlquilerPista()).append("\n");
                    info.append("edificios construidos: ");

                    //indicar los edificios que se construyeron en dicha casilla y, además, se deberá de actualizar el alquiler total
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
                info.append("apagar: ").append((int)impuesto).append("\n");
                break;
            case "parking":
                info.append("bote: ").append((int) valor).append(",\n");
                info.append("jugadores: [");
                int count = 0;
                for (Avatar j : avatares) {
                    info.append(j.getJugador().getNombre());
                    if (++count < avatares.size()){
                        info.append(", ");
                    }
                }
                info.append("]\n");
                break;
            case "carcel":
                info.append("salir: ").append((int) Valor.CARCEL_SALIDA).append(",\n");
                info.append("jugadores: [");
                count = 0;
                for (Avatar j : avatares) {
                    info.append(j.getJugador().getNombre());
                    if (++count < avatares.size()){
                        info.append(", ");
                    }
                }
                info.append("]\n");
                break;
            default:
                System.out.println("Non se pode describir este tipo de casilla.");
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
    private boolean evaluarSolar(Jugador actual, Jugador banca, Tablero tablero) {

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
            return actual.pagarJugador(duenho, impuesto, tablero);
        }
        // 3) Se está hipotecado, non se paga aluguer
        else {
            System.out.println("O solar está hipotecado, non se paga aluguer.");
            return true;
        }
    }

    // Avalía unha casilla de transporte
    private boolean evaluarTransporte(Jugador actual, Jugador banca, Tablero tablero) {
        if (duenho == null || duenho == banca) {
            System.out.println("O transporte está dispoñible para comprar por " + (int)impuesto + "€");
            return true;
        } else if (duenho == actual) {
            System.out.println("É o dono deste transporte");
            return true;
        }
        else if (!hipotecada) {
            return actual.pagarJugador(duenho, impuesto, tablero);
        }
        return true; //falta engadir se está hipotecada pero en próximas entregas
    }

    // Avalía unha casilla de servizo
    private boolean evaluarServicio(Jugador actual, Jugador banca, int tirada, Tablero tablero) {
        if (duenho == null || duenho == banca) {
            return true;
        } else if (duenho == actual) {
            System.out.println("É o dono deste servizo");
            return true;
        }
        else if (!hipotecada) {
            //Nesta entrega o alquiler é catro veces os dados polo factor de servicio
            float pago = tirada * Valor.FACTOR_SERVICIO * 4;
            return actual.pagarJugador(duenho, pago, tablero);
        }
        return true; //falta engadir se está hipotecada pero en próximas entregas
    }

    // Avalía imposto
    private boolean pagarImpuesto(Jugador actual, Jugador banca, Tablero tablero) {
        if (actual.puedePagar(impuesto)) {
            actual.sumarFortuna(-impuesto);
            //Engádese ao parking
            return true;
        } else{
            actual.declararBancarrota(tablero);
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
    public boolean cobrarParking(Jugador actual, Jugador banca) {
        if (valor > 0) {
            System.out.println(actual.getNombre() + " recibe o bote do Parking: " + (int)valor + "€");
            actual.sumarFortuna(valor);
            valor = 0; // Reiniciamos o bote
        } else {
            System.out.println("O Parking está baleiro");
        }
        return true;
    }

    //Metodos para xestionar hipotecas
    public boolean hipotecar (Jugador jugador){
        //Verificar que o xogador é dono
        if (this.duenho == null || !this.duenho.equals(jugador)){
            System.out.println(jugador.getNombre() + " non pode hipotecar " + this.nombre + " porque non é o seu dono.");
            return false;
        }
        //Verificar que non está hipotecada
        if(this.hipotecada){
            System.out.println(this.nombre + " xa está hipotecada.");
            return false;
        }
        //Comprobar se én solar, e en caso de selo que non teña edificios
        if ("solar".equalsIgnoreCase(this.tipo.toLowerCase())){
            if (this.datosedificios.tieneEdificios()) {
                System.out.println(jugador.getNombre() + " non pode hipotecar " + this.nombre + ". Ten edificios que deben venderse primeiro.");
                return false;
            }
        }
        //Hipotecar
        this.hipotecada = true;
        float valorHipoteca = calcularValorHipoteca();
        jugador.sumarFortuna(valorHipoteca);
        System.out.println(jugador.getNombre() + " hipoteca " + this.nombre + " por " + (int)valorHipoteca + "€. Fortuna actual: " + (int)jugador.getFortuna() + "€.");
        return true;
    }

    private float calcularValorHipoteca(){
        //O valor da hipoteca é a metade do valor de compra
        return this.valor/2f;
    }

    private float calcularValorDeshipoteca(){
        //Mesmo valor ca hipoteca e comezase a cobrar alquiler
        return calcularValorHipoteca();
    }

    public boolean venderEdificios(){
        if (!this.datosedificios.tieneEdificios()){
            System.out.println("Non hai edificios que vender nesta casilla.");
            return false;
        }
        //Vender todos os edificios
        float totalVenta = calcularValorVentaEdificios();
        this.datosedificios = new Solares.DatosEdificios(); //Reiniciamos os datos de edificios

        if (this.duenho != null){
            this.duenho.sumarFortuna(totalVenta);
            System.out.println(this.duenho.getNombre() + " vende todos os edificios de " + this.nombre + " por " + (int)totalVenta + "€. Fortuna actual: " + (int)this.duenho.getFortuna() + "€.");
            return true;
        }
        return false;
    }

    private float calcularValorVentaEdificios(){
        float valorTotal = 0;
        String nombreSolar = this.nombre.toLowerCase();

        // Valor das casas (venden ao 50%)
        if (this.datosedificios.getNumCasas() > 0) {
            float valorCasa = Solares.obtenerPrecioCasa(nombreSolar);
            valorTotal += this.datosedificios.getNumCasas() * valorCasa * 0.5f;
        }

        // Valor dos hoteis
        if (this.datosedificios.getNumHoteles() > 0) {
            float valorHotel = Solares.obtenerPrecioHotel(nombreSolar);
            valorTotal += this.datosedificios.getNumHoteles() * valorHotel * 0.5f;
        }

        // Valor das piscinas
        if (this.datosedificios.getNumPiscinas() > 0) {
            float valorPiscina = Solares.obtenerPrecioPiscina(nombreSolar);
            valorTotal += this.datosedificios.getNumPiscinas() * valorPiscina * 0.5f;
        }

        // Valor das pistas
        if (this.datosedificios.getNumPistas() > 0) {
            float valorPista = Solares.obtenerPrecioPista(nombreSolar);
            valorTotal += this.datosedificios.getNumPistas() * valorPista * 0.5f;
        }

        return valorTotal;
    }
    // Engadir método deshipotecar
    public boolean deshipotecar(Jugador jugador) {
        // Verificar que o xogador é dono
        if (this.duenho == null || !this.duenho.equals(jugador)) {
            System.out.println(jugador.getNombre() + " non pode deshipotecar " + this.nombre + ". Non é unha propiedade que lle pertenza.");
            return false;
        }

        // Verificar que está hipotecada
        if (!this.hipotecada) {
            System.out.println(jugador.getNombre() + " non pode deshipotecar " + this.nombre + ". Non está hipotecada.");
            return false;
        }

        // Verificar que ten diñeiro para pagar
        float valorDeshipoteca = calcularValorDeshipoteca();
        if (!jugador.puedePagar(valorDeshipoteca)) {
            System.out.println(jugador.getNombre() + " non pode deshipotecar " + this.nombre + ". Non ten diñeiro suficiente.");
            return false;
        }

        // Realizar a deshipoteca
        this.hipotecada = false;
        jugador.sumarFortuna(-valorDeshipoteca);

        System.out.println(jugador.getNombre() + " paga " + (int)valorDeshipoteca + "€ por deshipotecar " + this.nombre +
                ". Agora pode recibir alquileres e edificar no grupo " +
                (grupo != null ? grupo.getColorGrupo() : "correspondente") + ".");
        return true;
    }


    public void irACarcel(Jugador actual) {
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

    public float getImpuesto() {
        return impuesto;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public void setDuenho(Jugador duenho) {
        this.duenho = duenho;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public Solares.DatosEdificios getDatosEdificios() {
        return this.datosedificios;
    }

}

