package monopoly;

import partida.*;
import java.util.ArrayList;
import java.util.HashMap;


public class Tablero {
    //Atributos.
    // Estrutura do taboleiro: 4 lados, cada lado é unha lista de casillas

    private ArrayList<ArrayList<Casilla>> posiciones; //Posiciones del tablero: se define como un arraylist de arraylists de casillas (uno por cada lado del tablero).
    private HashMap<String, Grupo> grupos; //Grupos del tablero, almacenados como un HashMap con clave String (será el color del grupo).
    private Jugador banca; //Un jugador que será la banca.
    private ArrayList<Jugador> jugadores; // Lista de xogadores para o cárcere

    //Constructor: únicamente le pasamos el jugador banca (que se creará desde el menú).
    public Tablero(Jugador banca) {

        //creamos o constructor onde inicializamos as estructuras da banca, posicions e grupos
        this.banca = banca; // Garda a referencia á banca que se pasa como parámetro
        this.jugadores = new ArrayList<>();
        this.posiciones = new ArrayList<>();
        this.grupos = new HashMap<>(); // Inicializa o mapa de grupos como baleiro

        //inicializamos os lados do taboleiro (0=Sur, 1=Oeste, 2=Norte, 3=Leste)
        for(int i = 0; i<4; i++){
            this.posiciones.add(new ArrayList<Casilla>()); // Engade unha lista baleira para cada lado do taboleiro
        }

        //creamos as casillas
        this.generarCasillas(); //crea e coloca todas as casillas do taboleiro
        this.inicializarGrupos(); //Inicializa os grupos de solares
    }

    //Metodo para crear todas las casillas del tablero. Formado a su vez por cuatro métodos (1/lado).
    private void generarCasillas() {
        this.insertarLadoSur(); // Crea e inserta as casillas do lado sur (posicións 20-30)
        this.insertarLadoOeste(); // Crea e inserta as casillas do lado oeste (posicións 11-19)
        this.insertarLadoNorte(); // Crea e inserta as casillas do lado norte (posicións 0-10)
        this.insertarLadoEste(); // Crea e inserta as casillas do lado este (posicións 31-39)

    }

    //Metodo para insertar las casillas del lado norte.
    private void insertarLadoNorte() {
        ArrayList<Casilla> norte = posiciones.get(2);// Obtén a lista do lado norte (índice 2)

        norte.add(new Casilla("Parking", "parking", 20, banca));
        norte.add(new Casilla("Solar12", "solar", 21, Valor.SOLAR12_PRECIO, banca));
        norte.add(new Casilla("Suerte", "suerte", 22, banca));
        norte.add(new Casilla("Solar13", "solar", 23, Valor.SOLAR13_PRECIO, banca));
        norte.add(new Casilla("Solar14", "solar", 24, Valor.SOLAR14_PRECIO, banca));
        norte.add(new Casilla("Trans3", "transporte", 25, Valor.TRANSPORTE_PRECIO, banca));
        norte.add(new Casilla("Solar15", "solar", 26, Valor.SOLAR15_PRECIO, banca));
        norte.add(new Casilla("Solar16", "solar", 27, Valor.SOLAR16_PRECIO, banca));
        norte.add(new Casilla("Serv2", "servicio", 28, Valor.SERVICIO_PRECIO, banca));
        norte.add(new Casilla("Solar17", "solar", 29, Valor.SOLAR17_PRECIO, banca));
        norte.add(new Casilla("IrCarcel", "ircarcel", 30, banca));
    }


    //Metodo para insertar las casillas del lado sur.
    private void insertarLadoSur() {
        ArrayList<Casilla> sur = posiciones.get(0); // Obtén a lista do lado sur (índice 0)

        sur.add(new Casilla("Salida", "salida", 0, banca));
        sur.add(new Casilla("Solar1", "solar", 1, Valor.SOLAR1_PRECIO, banca));
        sur.add(new Casilla("Caja", "comunidad", 2, banca));
        sur.add(new Casilla("Solar2", "solar", 3, Valor.SOLAR2_PRECIO, banca));
        sur.add(new Casilla("Imp1", 4, Valor.IMPUESTO_VALOR, banca));
        sur.add(new Casilla("Trans1", "transporte", 5, Valor.TRANSPORTE_PRECIO, banca));
        sur.add(new Casilla("Solar3", "solar", 6, Valor.SOLAR3_PRECIO, banca));
        sur.add(new Casilla("Suerte", "suerte", 7, banca));
        sur.add(new Casilla("Solar4", "solar", 8, Valor.SOLAR4_PRECIO, banca));
        sur.add(new Casilla("Solar5", "solar", 9, Valor.SOLAR5_PRECIO, banca));
        sur.add(new Casilla("Carcel", "carcel", 10, banca));
    }

    //Metodo que inserta casillas del lado oeste.
    private void insertarLadoOeste() {
        ArrayList<Casilla> oeste = posiciones.get(1); // Obtén a lista do lado oeste (índice 1)

        oeste.add(new Casilla("Solar6", "solar", 11, Valor.SOLAR6_PRECIO, banca));
        oeste.add(new Casilla("Serv1", "servicio", 12, Valor.SERVICIO_PRECIO, banca));
        oeste.add(new Casilla("Solar7", "solar", 13, Valor.SOLAR7_PRECIO, banca));
        oeste.add(new Casilla("Solar8", "solar", 14, Valor.SOLAR8_PRECIO, banca));
        oeste.add(new Casilla("Trans2", "transporte", 15, Valor.TRANSPORTE_PRECIO, banca));
        oeste.add(new Casilla("Solar9", "solar", 16, Valor.SOLAR9_PRECIO, banca));
        oeste.add(new Casilla("Caja", "comunidad", 17, banca));
        oeste.add(new Casilla("Solar10", "solar", 18, Valor.SOLAR10_PRECIO, banca));
        oeste.add(new Casilla("Solar11", "solar", 19, Valor.SOLAR11_PRECIO, banca));
    }

    //Metodo que inserta las casillas del lado este.
    private void insertarLadoEste() {
        ArrayList<Casilla> este = posiciones.get(3); // Obtén a lista do lado leste (índice 3)

        este.add(new Casilla("Solar18", "solar", 31, Valor.SOLAR18_PRECIO, banca));
        este.add(new Casilla("Solar19", "solar", 32, Valor.SOLAR19_PRECIO, banca));
        este.add(new Casilla("Caja", "comunidad", 33, banca));
        este.add(new Casilla("Solar20", "solar", 34, Valor.SOLAR20_PRECIO, banca));
        este.add(new Casilla("Trans4", "transporte", 35, Valor.TRANSPORTE_PRECIO, banca));
        este.add(new Casilla("Suerte", "suerte", 36, banca));
        este.add(new Casilla("Solar21", "solar", 37, Valor.SOLAR21_PRECIO, banca));
        este.add(new Casilla("Imp2", 38, Valor.IMPUESTO_VALOR, banca));
        este.add(new Casilla("Solar22", "solar", 39, Valor.SOLAR22_PRECIO, banca));
    }

    //Para imprimir el tablero.
    @Override
    public String toString() {
        // Obtén cada un dos lados do taboleiro para poder procesalos individualmente
        ArrayList<Casilla> sur = posiciones.get(0);
        ArrayList<Casilla> oeste = posiciones.get(1);
        ArrayList<Casilla> norte = posiciones.get(2);
        ArrayList<Casilla> este = posiciones.get(3);


        //ENCABEZADO Y CONCATENADOR DE CADENAS
        // Usamos StringBuilder para construír eficientemente a saída (mellor que concatenar Strings)
        StringBuilder sb = new StringBuilder();
        sb.append("\n                                 ======= TABLERO MONOPOLY =======\n\n");

        //TAMAÑOS MODIFICABLES
        int ancho = 12;
        int alto = 4;
        int hueco = ancho * norte.size();


        // LADO NORTE (imprímese na parte superior, de esquerda a derecha) ---
        for (int fila = 0; fila < alto; fila++) {
            sb.append("".repeat(ancho));
            // Obtén a casilla actual do lado norte
            for (Casilla c : norte) {
                String texto = getContenidoCasilla(c, fila, ancho);
                sb.append(texto);
            }
            sb.append("\n");
        }

        // --- LADOS OESTE y ESTE (verticales, imprímense en paralelo) ---
        //o Math.max devolve o máximo entro os dous parametros, realmente podriamos non empregarlo e poñer o maior, pero se cambiamos algo das casillas non temos q tocar esto
        int maxFilas = Math.max(oeste.size(), este.size()); // Determina cal lado ten máis casillas
        for (int i = 0; i < maxFilas; i++) {
            //imos seleccionando as casillas para imprimir sempre que esten dentro do rango de elementos que temos no seu punto cardinal
            Casilla izq = (i < oeste.size()) ? oeste.get(oeste.size() - 1 - i) : null;
            Casilla der = (i < este.size()) ? este.get(i) : null;

            for (int fila = 0; fila < alto; fila++) {
                if (izq != null) {
                    sb.append(getContenidoCasilla(izq, fila, ancho));
                }
                else sb.append(" ".repeat(ancho));

                sb.append(" ".repeat(hueco-12 - ancho));

                if (der != null){
                    sb.append(getContenidoCasilla(der, fila, ancho));
                }
                sb.append("\n"); // Nova liña despois de cada par de casillas (oeste-leste)
            }
        }

        // --- LADO SUR (izquierda a derecha) ---
        for (int fila = 0; fila < alto; fila++) {
            sb.append("".repeat(ancho));
            //aqui diminue para poder imprimir o taboleiro en orden natural
            for (int i = sur.size() - 1; i >= 0; i--) { //non se usa for-each porque vai ao reves
                Casilla c = sur.get(i);
                String texto = getContenidoCasilla(c, fila, ancho);
                sb.append(texto);
            }

            sb.append("\n");
        }
        return sb.toString();
    }

    //----------Metodos auxiliares

    // Pequeño metodo auxiliar para rellenar nombres cortos y que todo quede alineado
    private String pad(String texto, int ancho) {
        if (texto.length() >= ancho) {
            return texto.substring(0, ancho); //.substring colle unha porción do orixinal, delimitada entre o inidce do inicio e do fin
        }
        return texto + " ".repeat(ancho - texto.length());
    }


    //Metodo usado para buscar la casilla con el nombre pasado como argumento:
    public Casilla encontrar_casilla(String nombre) {
        // Percorre todos os lados do taboleiro (sur, oeste, norte, leste)
        for (ArrayList<Casilla> lado : posiciones) {
            // Percorre cada casilla deste lado específico
            for (Casilla casilla : lado) {
                // Compara o nome da casilla (ignorando maiúsculas/minúsculas) co nome buscado
                if (casilla.getNombre().equalsIgnoreCase(nombre)) {
                    return casilla;  // Devolve a casilla encontrada
                }
            }
        }
        return null;  // Non se atopou a casilla con ese nome en todo o taboleiro
    }


    public ArrayList<Casilla> getLado(int indice) {
        if(indice >= 0 && indice < posiciones.size()){
            return posiciones.get(indice);
        } else {
            return null;
        }
    }

    private String getContenidoCasilla(Casilla c, int fila, int ancho) {
        String nombre = c.getNombre();
        String tipo = c.getTipo();

        // Fila 0 → parte superior (borde)
        if (fila == 0) {
            return "┌" + "─".repeat(ancho - 2) + "┐"; // Borde superior da casilla
        }

        // Fila 1 → nombre de la casilla (centrado y coloreado)
        if (fila == 1) {
            String nombreColor = Grupo.calcularColor(c);
            // Remove os códigos de cor para calcular o largo real do texto
            String limpio = nombreColor.replaceAll("\u001B\\[[;\\d]*m", "");

            // centramos manualmente sin usar String.format (que rompe os colores)
            int espacio = ancho - 2 - limpio.length();
            int izq = Math.max(0, espacio / 2); // Espazo á esquerda
            int der = Math.max(0, espacio - izq); // Espazo á dereita

            // aplicamos RESET siempre ANTES del borde derecho
            return "│" + " ".repeat(izq) + nombreColor + " ".repeat(der) + "\u001B[0m│";
        }

        // Fila 2 → tipo de casilla (Solar, Transporte, Servicio, Especial, etc.)
        if (fila == 2) {
            return String.format("│%-" + (ancho - 2) + "s│",
                    recortarCentro(tipo, ancho - 2)); // Recorta o tipo se é longo
        }

        // Fila 3 → borde inf.append.appenderior
        if (fila == 3) {
            String avatares = c.getAvataresString(); // Obtén a lista de avatares
            return String.format("│%-" + (ancho - 2) + "s│", recortarCentro(avatares, ancho - 2));
        }

        // Por defecto, no imprime nada si se pide una fila fuera de rango
        return "";
    }

    // Centra o recorta texto dentro de una casilla
    //substring crea una cadena q empieza en el primer parametro y acaba en el segundo
    private String recortarCentro(String texto, int ancho) {
        if (texto.length() > ancho)
            return texto.substring(0, ancho);
        int espacio = ancho - texto.length();
        int izquierda = espacio / 2;
        int derecha = espacio - izquierda;
        return " ".repeat(izquierda) + texto + " ".repeat(derecha);
    }

    // Metodo para inicializar os grupos de solares, e que poidan aparecer ao imprimilos
    private void inicializarGrupos() {
        // Grupo Marrón (Solar1 e Solar2)
        Casilla solar1 = encontrar_casilla("Solar1");
        Casilla solar2 = encontrar_casilla("Solar2");
        if (solar1 != null && solar2 != null) {
            Grupo grupoMarron = new Grupo(solar1, solar2, "Marrón");
            this.grupos.put("Marrón", grupoMarron);
        }

        // Grupo Azul Claro (Solar3, Solar4, Solar5)
        Casilla solar3 = encontrar_casilla("Solar3");
        Casilla solar4 = encontrar_casilla("Solar4");
        Casilla solar5 = encontrar_casilla("Solar5");
        if (solar3 != null && solar4 != null && solar5 != null) {
            Grupo grupoAzulClaro = new Grupo(solar3, solar4, solar5, "Azul Claro");
            this.grupos.put("Azul Claro", grupoAzulClaro);
        }

        // Grupo Rosa (Solar6, Solar7, Solar8)
        Casilla solar6 = encontrar_casilla("Solar6");
        Casilla solar7 = encontrar_casilla("Solar7");
        Casilla solar8 = encontrar_casilla("Solar8");
        if (solar6 != null && solar7 != null && solar8 != null) {
            Grupo grupoRosa = new Grupo(solar6, solar7, solar8, "Rosa");
            this.grupos.put("Rosa", grupoRosa);
        }

        // Grupo Laranxa (Solar9, Solar10, Solar11)
        Casilla solar9 = encontrar_casilla("Solar9");
        Casilla solar10 = encontrar_casilla("Solar10");
        Casilla solar11 = encontrar_casilla("Solar11");
        if (solar9 != null && solar10 != null && solar11 != null) {
            Grupo grupoLaranxa = new Grupo(solar9, solar10, solar11, "Laranxa");
            this.grupos.put("Laranxa", grupoLaranxa);
        }

        // Grupo Vermello (Solar12, Solar13, Solar14)
        Casilla solar12 = encontrar_casilla("Solar12");
        Casilla solar13 = encontrar_casilla("Solar13");
        Casilla solar14 = encontrar_casilla("Solar14");
        if (solar12 != null && solar13 != null && solar14 != null) {
            Grupo grupoVermello = new Grupo(solar12, solar13, solar14, "Vermello");
            this.grupos.put("Vermello", grupoVermello);
        }

        // Grupo Amarelo (Solar15, Solar16, Solar17)
        Casilla solar15 = encontrar_casilla("Solar15");
        Casilla solar16 = encontrar_casilla("Solar16");
        Casilla solar17 = encontrar_casilla("Solar17");
        if (solar15 != null && solar16 != null && solar17 != null) {
            Grupo grupoAmarelo = new Grupo(solar15, solar16, solar17, "Amarelo");
            this.grupos.put("Amarelo", grupoAmarelo);
        }

        // Grupo Verde (Solar18, Solar19, Solar20)
        Casilla solar18 = encontrar_casilla("Solar18");
        Casilla solar19 = encontrar_casilla("Solar19");
        Casilla solar20 = encontrar_casilla("Solar20");
        if (solar18 != null && solar19 != null && solar20 != null) {
            Grupo grupoVerde = new Grupo(solar18, solar19, solar20, "Verde");
            this.grupos.put("Verde", grupoVerde);
        }

        // Grupo Azul Escuro (Solar21, Solar22)
        Casilla solar21 = encontrar_casilla("Solar21");
        Casilla solar22 = encontrar_casilla("Solar22");
        if (solar21 != null && solar22 != null) {
            Grupo grupoAzulEscuro = new Grupo(solar21, solar22, "Azul Escuro");
            this.grupos.put("Azul Escuro", grupoAzulEscuro);
        }
    }


    // ========== GETTERS ==========

    public Jugador getBanca() {
        return banca;
    }

    public ArrayList<ArrayList<Casilla>> getPosiciones() {
        return posiciones;
    }

    public ArrayList<ArrayList<Casilla>> getCasillas() {
        return posiciones;
    }


}