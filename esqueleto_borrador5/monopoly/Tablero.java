package monopoly;

import partida.*;
import java.util.ArrayList;
import java.util.HashMap;


public class Tablero {
    //Atributos.
    private ArrayList<ArrayList<Casilla>> posiciones; //Posiciones del tablero: se define como un arraylist de arraylists de casillas (uno por cada lado del tablero).
    private HashMap<String, Grupo> grupos; //Grupos del tablero, almacenados como un HashMap con clave String (será el color del grupo).
    private Jugador banca; //Un jugador que será la banca.
    private ArrayList<Jugador> jugadores; // Lista de xogadores para o cárcere

    //Constructor: únicamente le pasamos el jugador banca (que se creará desde el menú).
    public Tablero(Jugador banca) {

        //creamos o constructor onde inicializamos as estructuras da banca, posicions e grupos
        this.banca = banca;
        this.jugadores = new ArrayList<>();
        this.posiciones = new ArrayList<>();
        this.grupos = new HashMap<>();

        //inicializamos os lados do taboleiro, sendo o 0 o norte(pq ten a saida)
        for(int i = 0; i<4; i++){
            this.posiciones.add(new ArrayList<Casilla>());
        }

        //creamos as casillas
        this.generarCasillas();
    }

    public ArrayList<Casilla> getLado(int indice) {
        if(indice >= 0 && indice < posiciones.size()){
            return posiciones.get(indice);
        } else {
            return null; // O lanzar excepción si prefieres
        }
    }

    //Metodo para crear todas las casillas del tablero. Formado a su vez por cuatro métodos (1/lado).
    private void generarCasillas() {
        this.insertarLadoSur();
        this.insertarLadoOeste();
        this.insertarLadoNorte();
        this.insertarLadoEste();

    }

    //Metodo para insertar las casillas del lado norte.
    private void insertarLadoNorte() {
        ArrayList<Casilla> norte = posiciones.get(2);
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
        norte.add(new Casilla("IrCarcel", "ir_carcel", 30, banca));
    }


    //Metodo para insertar las casillas del lado sur.
    private void insertarLadoSur() {
        ArrayList<Casilla> sur = posiciones.get(0);

        sur.add(new Casilla("Salida", "salida", 0, banca));
        sur.add(new Casilla("Solar1", "solar", 1, Valor.SOLAR1_PRECIO, banca));
        sur.add(new Casilla("Caja", "comunidad", 2, banca));
        sur.add(new Casilla("Solar2", "solar", 3, Valor.SOLAR2_PRECIO, banca));
        sur.add(new Casilla("Imp1", "impuesto",4, Valor.IMPUESTO_VALOR, banca));
        sur.add(new Casilla("Trans1", "transporte", 5, Valor.TRANSPORTE_PRECIO, banca));
        sur.add(new Casilla("Solar3", "solar", 6, Valor.SOLAR3_PRECIO, banca));
        sur.add(new Casilla("Suerte", "suerte", 7, banca));
        sur.add(new Casilla("Solar4", "solar", 8, Valor.SOLAR4_PRECIO, banca));
        sur.add(new Casilla("Solar5", "solar", 9, Valor.SOLAR5_PRECIO, banca));
        sur.add(new Casilla("Carcel", "carcel", 10, banca));
    }

    //Metodo que inserta casillas del lado oeste.
    private void insertarLadoOeste() {
        ArrayList<Casilla> oeste = posiciones.get(1);

        oeste.add(new Casilla("Solar6", "solar", 11, Valor.SOLAR6_PRECIO, banca));
        oeste.add(new Casilla("Serv1", "servicios", 12, Valor.SERVICIO_PRECIO, banca));
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
        ArrayList<Casilla> este = posiciones.get(3);

        este.add(new Casilla("Solar18", "solar", 31, Valor.SOLAR18_PRECIO, banca));
        este.add(new Casilla("Solar19", "solar", 32, Valor.SOLAR19_PRECIO, banca));
        este.add(new Casilla("Caja", "comunidad", 33, banca));
        este.add(new Casilla("Solar20", "solar", 34, Valor.SOLAR20_PRECIO, banca));
        este.add(new Casilla("Trans4", "transporte", 35, Valor.TRANSPORTE_PRECIO, banca));
        este.add(new Casilla("Suerte", "suerte", 36, banca));
        este.add(new Casilla("Solar21", "solar", 37, Valor.SOLAR21_PRECIO, banca));
        este.add(new Casilla("Imp2", "impuesto", 38, Valor.IMPUESTO_VALOR, banca));
        este.add(new Casilla("Solar22", "solar", 39, Valor.SOLAR22_PRECIO, banca));
    }

    //Para imprimir el tablero, modificamos el método toString().
    @Override
    public String toString() {
        // Lados del tablero
        ArrayList<Casilla> sur = posiciones.get(0);
        ArrayList<Casilla> oeste = posiciones.get(1);
        ArrayList<Casilla> norte = posiciones.get(2);
        ArrayList<Casilla> este = posiciones.get(3);


        //ENCABEZADO Y CONCATENADOR DE CADENAS
        StringBuilder sb = new StringBuilder();
        sb.append("\n                                 ======= TABLERO MONOPOLY =======\n\n");

        //TAMAÑOS MODIFICABLES
        int ancho = 12;
        int alto = 4;
        int hueco = ancho * norte.size();


        // LADO NORTE (se imprime de derecha a izquierda) ---
        for (int fila = 0; fila < alto; fila++) {
            sb.append("".repeat(ancho));
            for (int i = 0; i < norte.size(); i++) {
                Casilla c = norte.get(i);
                String texto = getContenidoCasilla(c, fila, ancho);
                sb.append(texto);
            }
            sb.append("\n");
        }

        // --- LADOS OESTE y ESTE (verticales) ---
        //o Math.max devolve o máximo entro os dous parametros, realmente podriamos non empregarlo e poñer o maior, pero se cambiamos algo das casillas non temos q tocar esto
        int maxFilas = Math.max(oeste.size(), este.size());
        for (int i = 0; i < maxFilas; i++) {
            //imos seleccionando as casillas para imprimir sempre que esten dentro do rango de elementos que temos no seu punto cardinal
            Casilla izq = (i < oeste.size()) ? oeste.get(oeste.size() - 1 - i) : null;
            Casilla der = (i < este.size()) ? este.get(i) : null;

            for (int fila = 0; fila < alto; fila++) {
                if (izq != null) sb.append(getContenidoCasilla(izq, fila, ancho));
                else sb.append(" ".repeat(ancho));

                sb.append(" ".repeat(hueco-12 - ancho));

                if (der != null) sb.append(getContenidoCasilla(der, fila, ancho));
                sb.append("\n");
            }
        }

        // --- LADO SUR (izquierda a derecha) ---
        for (int fila = 0; fila < alto; fila++) {
            sb.append("".repeat(ancho));
            //aqui diminue para poder imprimir o taboleiro en orden natural
            for (int i = sur.size() - 1; i >= 0; i--) {
                Casilla c = sur.get(i);
                String texto = getContenidoCasilla(c, fila, ancho);
                sb.append(texto);
            }
            sb.append("\n");
        }
        return sb.toString();
    }



    /////////////////////////////////MÉTODOS AUXILIARES///////////////////////////////////////
    // Pequeño metodo auxiliar para rellenar nombres cortos y que todo quede alineado
    private String pad(String texto, int ancho) {
        if (texto.length() >= ancho) return texto.substring(0, ancho);
        return texto + " ".repeat(ancho - texto.length());
    }


    //Metodo usado para buscar la casilla con el nombre pasado como argumento:
    public Casilla encontrar_casilla(String nombre){
        //pongo un return NULL para ver si funciona, luego se quita esto
        return null;
    }


    private String getContenidoCasilla(Casilla c, int fila, int ancho) {
        String nombre = c.getNombre();
        String tipo = c.getTipo();

        // Fila 0 → parte superior (borde)
        if (fila == 0) {
            return "┌" + "─".repeat(ancho - 2) + "┐";
        }

        // Fila 1 → nombre de la casilla (centrado y coloreado)
        if (fila == 1) {
            String nombreColor = Grupo.calcularColor(c);
            String limpio = nombreColor.replaceAll("\u001B\\[[;\\d]*m", ""); // para medir sin ANSI

            // centramos manualmente sin usar String.format (que rompe los colores)
            int espacio = ancho - 2 - limpio.length();
            int izq = Math.max(0, espacio / 2);
            int der = Math.max(0, espacio - izq);

            // aplicamos RESET siempre ANTES del borde derecho
            return "│" + " ".repeat(izq) + nombreColor + " ".repeat(der) + "\u001B[0m│";
        }

        // Fila 2 → tipo de casilla (Solar, Transporte, Servicio, Especial, etc.)
        if (fila == 2) {
            return String.format("│%-" + (ancho - 2) + "s│",
                    recortarCentro(tipo, ancho - 2));
        }

        // Fila 3 → borde inferior
        if (fila == 3) {
            String avatares = c.getAvataresString();
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



    // ========== GETTERS ==========

    public HashMap<String, Grupo> getGrupos() {
        return grupos;
    }

    public Jugador getBanca() {
        return banca;
    }

    public ArrayList<Jugador> getJugadores() {
        return new ArrayList<>(jugadores);
    }
}