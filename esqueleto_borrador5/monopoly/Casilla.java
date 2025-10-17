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
    private Tablero tableroReferencia;

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
        if ("solar".equalsIgnoreCase(tipo)) {
            this.impuesto = 0; // O aluguer calcúlase dinamicamente
        } else if ("transporte".equalsIgnoreCase(tipo)) {
            this.impuesto = Valor.TRANSPORTE_ALQUILER; // Aluguer base para transportes
        } else if ("servicio".equalsIgnoreCase(tipo)) {
            this.impuesto = 0; // Calcúlase baseado nos dados
        }

    }

    /*Constructor utilizado para inicializar las casillas de tipo IMPUESTOS.
     * Parámetros: nombre, posición en el tablero, impuesto establecido y dueño.
     */
    public Casilla(String nombre, int posicion, float impuesto, Jugador duenho) {

        this.nombre = nombre;
        this.tipo = "impuesto";
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
        if ("parking".equalsIgnoreCase(tipo)){
            this.valor = 0; //inicialmente está a 0 o bote
        }

    }

    //Metodo utilizado para añadir un avatar al array de avatares en casilla.
    public void anhadirAvatar(Avatar av) {

        if (av != null && !avatares.contains(av)) { //Comprobamos que o avatar a engadir ao array de avatares non está (no queremos duplicados)
            avatares.add(av); //Se non está, engádese
            System.out.println("O avatar foi engadido con éxito");
        } else {
            System.out.println("O avatar xa estaba nesta casilla");
        }
    }

    //Metodo utilizado para eliminar un avatar del array de avatares en casilla.
    public void eliminarAvatar(Avatar av) {

        if (av != null && avatares.contains(av)) { //Comprobamos que o avatar está no array
            avatares.remove(av);        //Se está en la lista de avatares, eliminámolo
            System.out.println("Avatar eliminado da casilla");
        } else {
            System.out.println("Non se pode eliminar o avatar");
        }
    }

    //Permite que un xogador compre a casilla se está dispoñible.

    public void comprar(Jugador comprador) {
        //se non ten dono ou o dono é a banca pódese comprar
        if (duenho == null || "Banca".equals(duenho.getNombre())) {
            if (comprador.puedePagar(valor)) {
                // Restamos o valor da fortuna do xogador
                comprador.sumarFortuna(-valor);
                // Asignamos o dueño
                this.duenho = comprador;
                // Engadimos a casilla ás propiedades do xogador
                comprador.anhadirPropiedad(this);
                // Mensaxe informativa
                System.out.println("O xogador " + comprador.getNombre() + " compra a casilla "
                        + nombre + " por " + valor + "€. A súa fortuna actual é " + comprador.getFortuna() + "€.");
            } else {
                System.out.println(comprador.getNombre() + " non ten diñeiro suficiente para comprar " + nombre + ".");
            }
        } else {
            System.out.println("A casilla " + nombre + " a ten dono: " + duenho.getNombre());
        }
    }

    //Metodo para avaliar que hacer en una casilla concreta
    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada) {
        System.out.println("\nO xogador " + actual.getNombre() + " cae en " + nombre);

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

            case "ir_carcel":
                System.out.println("Vai directamente á Cárcere");
                actual.encarcelar(null); // Aquí podemos pasar o taboleiro se queremos
                return true;

            case "carcel":
                System.out.println("Só de visita no Cárcere");
                return true;

            case "salida":
                System.out.println("Pasou pola Salida! Recibe " + Valor.SUMA_VUELTA + "€");
                actual.sumarFortuna(Valor.SUMA_VUELTA);
                return true;

            default:
                System.out.println("Tipo de casilla non recoñecida: " + nombre);
                return true;
        }
    }

    // Avalía un solar, é privado polo que só pode ser chamado dende Casilla
    private boolean evaluarSolar(Jugador actual, Jugador banca) {

        // 1) Se non ten dono ou pertence á banca, o xogador pode mercar
        if (duenho == null || "Banca".equalsIgnoreCase(duenho.getNombre())) {
            float precioCompra = obtenerPrecioSolar(nombre);
            System.out.println("O solar está dispoñible para comprar por " + precioCompra + "€");
        }
        // Se o dono é o propio xogador
        else if (duenho == actual) {
            System.out.println("É o dono desta propiedade");
        }
        // 2) Se ten dono e non está hipotecado, págase aluguer
        else if (!hipotecada) {
            float alquiler = obtenerAlquilerSolar(nombre, 0); // sen casas nin hoteis
            System.out.println("Debe pagar " + alquiler + "€ de aluguer a " + duenho.getNombre());
            if (actual.puedePagar(alquiler)) {
                actual.sumarFortuna(-alquiler);
                duenho.sumarFortuna(alquiler);
                System.out.println(actual.getNombre() + " paga aluguer a " + duenho.getNombre() + " de " + alquiler + "€");
            } else{
                System.out.println(actual.getNombre() + " non pode pagar o aluguer!");
                System.out.println("Use 'hipotecar <propiedade>' ou declararase en bancarrota");
                return false;
            }
        }
        // 3) Se está hipotecado, non se paga aluguer
        else {
            System.out.println("O solar está hipotecado, non se paga aluguer.");
        }
        return true;
    }

    // Avalía unha casilla de transporte
    private boolean evaluarTransporte(Jugador actual, Jugador banca) {
        if (duenho == null || "Banca".equalsIgnoreCase(duenho.getNombre())) {
            System.out.println("O transporte está dispoñible para comprar por " + Valor.TRANSPORTE_PRECIO + "€");
        } else if (duenho == actual) {
            System.out.println("É o dono deste transporte");
            return true;
        }
        else if (!hipotecada) {
            //Sin considerar o número de casillas de transporte para esta entrega
            float alquilerTotal = Valor.TRANSPORTE_ALQUILER;
            System.out.println("Debe pagar " + alquilerTotal + "€ de aluguer a " + duenho.getNombre());
            if (actual.puedePagar(alquilerTotal)) {
                actual.sumarFortuna(-alquilerTotal);
                duenho.sumarFortuna(alquilerTotal);
                System.out.println(actual.getNombre() + " paga aluguer de transporte a " + duenho.getNombre() + " de " + alquilerTotal + "€");
            } else{
                System.out.println(actual.getNombre() + " non pode pagar o aluguer!");
                System.out.println("Use 'hipotecar <propiedade>' ou declararase en bancarrota");
                return false;
            }
        }
        return true;
    }

    // Avalía unha casilla de servizo
    private boolean evaluarServicio(Jugador actual, Jugador banca, int tirada) {
        if (duenho == null || "Banca".equalsIgnoreCase(duenho.getNombre())) {
            System.out.println("O servizo está dispoñible para comprar por " + Valor.SERVICIO_PRECIO + "€");
        } else if (duenho == actual) {
            System.out.println("É o dono deste servizo");
        }
        else if (!hipotecada) {
            //alquiler al otro jugador considerando siempre la multiplicación de cuatro veces el valor de los dados por el factor de servicio
            //(sin tener en cuenta cuántas casillas de servicio tiene el jugador).

            float pago = tirada * Valor.FACTOR_SERVICIO * 4;
            System.out.println("Debe pagar " + pago + "€ de servizo a " + duenho.getNombre());

            if (actual.puedePagar(pago)) {
                actual.sumarFortuna(-pago);
                duenho.sumarFortuna(pago);
                System.out.println(actual.getNombre() + " paga servizo a " + duenho.getNombre() + ": " + pago + "€");
            } else{
                System.out.println(actual.getNombre() + " non pode pagar o aluguer!");
                System.out.println("Use 'hipotecar <propiedade>' ou declararase en bancarrota");
                return false;
            }
        }
        return true;
    }

    // Avalía imposto
    private boolean pagarImpuesto(Jugador actual, Jugador banca) {
        System.out.println("Debe pagar " + impuesto + "€ de impostos");
        if (actual.puedePagar(impuesto)) {
            actual.sumarFortuna(-impuesto);
            System.out.println(actual.getNombre() + " paga " + impuesto + "€ de impostos. Engadidos ao Parking.");
            //Engádese ao parking
            sumarAlParking(impuesto);
        } else{
            System.out.println(actual.getNombre() + " non pode pagar o imposto!");
            System.out.println("Use 'hipotecar <propiedade>' ou declararase en bancarrota");
            return false;
        }
        return true;
    }

    // Avalía cartas de sorte (simplificado)
    private boolean realizarCartaSuerte(Jugador actual, Jugador banca) {
        System.out.println("Saca unha carta de Sorte!");
        // Aquí poderiamos implementar efectos concretos na proxima entrega
        return true;
    }

    // Avalía cartas de comunidad (simplificado)
    private boolean realizarCartaComunidad(Jugador actual, Jugador banca) {
        System.out.println("Saca unha carta de Comunidade!");
        //Imlementanse efectos concretos na próxima entrega
        return true;
    }

    // Cobrar o bote do Parking
    private boolean cobrarParking(Jugador actual, Jugador banca) {
        if (valor > 0) {
            System.out.println(actual.getNombre() + " recibe o bote do Parking: " + valor + "€");
            actual.sumarFortuna(valor);
            valor = 0; // Reiniciamos o bote
        } else {
            System.out.println("O Parking está baleiro");
        }
        return true;
    }

    //Metodo para engadir valor a unha casilla (usado para o Parking)
    public void sumarValor(float suma) {
        this.valor += suma;
    }

    //Engade diñeiro ao bote do Parking
    private void sumarAlParking ( float cantidad){
        if (tableroReferencia != null) {
            Casilla parking = tableroReferencia.encontrar_casilla("Parking");
            if (parking != null) {
                parking.sumarValor(cantidad);
            }
        }
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

    // Metodo para declarar bancarrota
    public void declararBancarrota (Jugador deudor, Jugador acreedor) {
        System.out.println(deudor.getNombre() + " non pode pagar. Declárase en bancarrota ou hipoteca propiedades!");
        // Transferir todas as propiedades ao acreedor
        boolean eliminado = deudor.transferirPropiedades(acreedor);
        if (eliminado) {
            System.out.println(deudor.getNombre() + " foi eliminado do xogo por bancarrota.");
        } else {
            System.out.println(deudor.getNombre() + " aínda ten propiedades ou hipotecas pendentes.");
        }
    }

    //Metodo para mostrar información de una casilla en venta.
    public String casillaEnVenta(Jugador banca) {
        if ((duenho == null || "Banca".equals(duenho.getNombre())) && !"especial".equals(tipo)) {
            return "{\n  tipo: " + tipo + ",\n  valor: " + valor + "\n}";
        }
        return ""; // Se non está en venda, devolve cadea baleira
    }



    public static void imprimir_en_venta(ArrayList<Casilla> casillas_lados, Jugador banca) {
        for (Casilla e : casillas_lados) {
            if (e.getDuenho() == null || e.getDuenho() == banca) {
                System.out.println("Casilla: " + e.getNombre());
                System.out.println("Tipo: " + e.getTipo());
                System.out.println("Grupo: " + (e.getGrupo() != null ? e.getGrupo().getColorGrupo() : "-"));
                System.out.println("Valor: " + e.getValor() + "€\n");
            }
        }
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

    private float obtenerPrecioSolar (String nombreSolar){
        // Devolve o prezo de compra segundo o nome
        switch (nombreSolar.toLowerCase()) {
            case "solar1":
                return Valor.SOLAR1_PRECIO;
            case "solar2":
                return Valor.SOLAR2_PRECIO;
            case "solar3":
                return Valor.SOLAR3_PRECIO;
            case "solar4":
                return Valor.SOLAR4_PRECIO;
            case "solar5":
                return Valor.SOLAR5_PRECIO;
            case "solar6":
                return Valor.SOLAR6_PRECIO;
            case "solar7":
                return Valor.SOLAR7_PRECIO;
            case "solar8":
                return Valor.SOLAR8_PRECIO;
            case "solar9":
                return Valor.SOLAR9_PRECIO;
            case "solar10":
                return Valor.SOLAR10_PRECIO;
            case "solar11":
                return Valor.SOLAR11_PRECIO;
            case "solar12":
                return Valor.SOLAR12_PRECIO;
            case "solar13":
                return Valor.SOLAR13_PRECIO;
            case "solar14":
                return Valor.SOLAR14_PRECIO;
            case "solar15":
                return Valor.SOLAR15_PRECIO;
            case "solar16":
                return Valor.SOLAR16_PRECIO;
            case "solar17":
                return Valor.SOLAR17_PRECIO;
            case "solar18":
                return Valor.SOLAR18_PRECIO;
            case "solar19":
                return Valor.SOLAR19_PRECIO;
            case "solar20":
                return Valor.SOLAR20_PRECIO;
            case "solar21":
                return Valor.SOLAR21_PRECIO;
            case "solar22":
                return Valor.SOLAR22_PRECIO;
            default:
                return 0;
        }
    }

    private float obtenerAlquilerSolar (String nombreSolar,int numEdificios){
        // Para simplificar devolvemos un aluguer base (sen edificios)
        switch (nombreSolar.toLowerCase()) {
            case "solar1":
                return 20000f;
            case "solar2":
                return 40000f;
            case "solar3":
                return 60000f;
            case "solar4":
                return 60000f;
            case "solar5":
                return 80000f;
            case "solar6":
                return 100000f;
            case "solar7":
                return 100000f;
            case "solar8":
                return 120000f;
            case "solar9":
                return 140000f;
            case "solar10":
                return 140000f;
            case "solar11":
                return 160000f;
            case "solar12":
                return 180000f;
            case "solar13":
                return 180000f;
            case "solar14":
                return 200000f;
            case "solar15":
                return 220000f;
            case "solar16":
                return 220000f;
            case "solar17":
                return 240000f;
            case "solar18":
                return 260000f;
            case "solar19":
                return 260000f;
            case "solar20":
                return 280000f;
            case "solar21":
                return 350000f;
            case "solar22":
                return 500000f;
            default:
                return 0f;
        }
    }
}