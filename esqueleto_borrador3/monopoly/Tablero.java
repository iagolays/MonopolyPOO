package monopoly;

import partida.*;
import java.util.ArrayList;
import java.util.HashMap;


public class Tablero {
    //Atributos.
    private ArrayList<ArrayList<Casilla>> posiciones; //Posiciones del tablero: se define como un arraylist de arraylists de casillas (uno por cada lado del tablero).
    private HashMap<String, Grupo> grupos; //Grupos del tablero, almacenados como un HashMap con clave String (será el color del grupo).
    private Jugador banca; //Un jugador que será la banca.

    //Constructor: únicamente le pasamos el jugador banca (que se creará desde el menú).
    public Tablero(Jugador banca) {

        //creamos o constructor onde inicializamos as estructuras da banca, posicions e grupos
        this.banca = banca;
        this.posiciones = new ArrayList<>();
        this.grupos = new HashMap<>();

        //inicializamos os lados do taboleiro, sendo o 0 o norte(pq ten a saida)
        for(int i = 0; i<4; i++){
            this.posiciones.add(new ArrayList<Casilla>());
        }

        //creamos as casillas
        this.generarCasillas();
    }


    //Método para crear todas las casillas del tablero. Formado a su vez por cuatro métodos (1/lado).
    private void generarCasillas() {
        this.insertarLadoSur();
        this.insertarLadoOeste();
        this.insertarLadoNorte();
        this.insertarLadoEste();
    }

    //Método para insertar las casillas del lado norte.
    private void insertarLadoNorte() {
        ArrayList<Casilla> norte = posiciones.get(2);
        norte.add(new Casilla("Parking", "Especial", 21, banca));
        norte.add(new Casilla("Solar12", "Solar", 22, Valor.SOLAR12_PRECIO, banca));
        norte.add(new Casilla("Suerte", "Suerte", 23, banca));
        norte.add(new Casilla("Solar13", "Solar", 24, Valor.SOLAR13_PRECIO, banca));
        norte.add(new Casilla("Solar14", "Solar", 25, Valor.SOLAR14_PRECIO, banca));
        norte.add(new Casilla("Trans3", "Transporte", 26, Valor.TRANSPORTE_PRECIO, banca));
        norte.add(new Casilla("Solar15", "Solar", 27, Valor.SOLAR15_PRECIO, banca));
        norte.add(new Casilla("Solar16", "Solar", 28, Valor.SOLAR16_PRECIO, banca));
        norte.add(new Casilla("Serv2", "Servicio", 29, Valor.SERVICIO_PRECIO, banca));
        norte.add(new Casilla("Solar17", "Solar", 30, Valor.SOLAR17_PRECIO, banca));
        norte.add(new Casilla("IrCarcel", "Especial", 31, banca));

    }

    //Método para insertar las casillas del lado sur.
    private void insertarLadoSur() {
        ArrayList<Casilla> sur = posiciones.get(0);

        sur.add(new Casilla("Salida", "Especial", 1, banca));
        sur.add(new Casilla("Solar1", "Solar", 2, Valor.SOLAR1_PRECIO, banca));
        sur.add(new Casilla("Caja", "Comunidad", 3, banca));
        sur.add(new Casilla("Solar2", "Solar", 4, Valor.SOLAR2_PRECIO, banca));
        sur.add(new Casilla("Imp1", 5, 2_000_000f, banca));
        sur.add(new Casilla("Trans1", "Transporte", 6, Valor.TRANSPORTE_PRECIO, banca));
        sur.add(new Casilla("Solar3", "Solar", 7, Valor.SOLAR3_PRECIO, banca));
        sur.add(new Casilla("Suerte", "Suerte", 8, banca));
        sur.add(new Casilla("Solar4", "Solar", 9, Valor.SOLAR4_PRECIO, banca));
        sur.add(new Casilla("Solar5", "Solar", 10, Valor.SOLAR5_PRECIO, banca));
        sur.add(new Casilla("Carcel", "Especial", 11, banca));
    }

    //Método que inserta casillas del lado oeste.
    private void insertarLadoOeste() {
        ArrayList<Casilla> oeste = posiciones.get(1);

        oeste.add(new Casilla("Solar6", "Solar", 12, Valor.SOLAR6_PRECIO, banca));
        oeste.add(new Casilla("Serv1", "Servicios", 13, Valor.SERVICIO_PRECIO, banca));
        oeste.add(new Casilla("Solar7", "Solar", 14, Valor.SOLAR7_PRECIO, banca));
        oeste.add(new Casilla("Solar8", "Solar", 15, Valor.SOLAR8_PRECIO, banca));
        oeste.add(new Casilla("Trans2", "Transporte", 16, Valor.TRANSPORTE_PRECIO, banca));
        oeste.add(new Casilla("Solar9", "Solar", 17, Valor.SOLAR9_PRECIO, banca));
        oeste.add(new Casilla("Caja", "Comunidad", 18, banca));
        oeste.add(new Casilla("Solar10", "Solar", 19, Valor.SOLAR10_PRECIO, banca));
        oeste.add(new Casilla("Solar11", "Solar", 20, Valor.SOLAR11_PRECIO, banca));


    }

    //Método que inserta las casillas del lado este.
    private void insertarLadoEste() {
        ArrayList<Casilla> este = posiciones.get(3);

        este.add(new Casilla("Solar18", "Solar", 32, Valor.SOLAR18_PRECIO, banca));
        este.add(new Casilla("Solar19", "Solar", 33, Valor.SOLAR19_PRECIO, banca));
        este.add(new Casilla("Caja", "Comunidad", 34, banca));
        este.add(new Casilla("Solar20", "Solar", 35, Valor.SOLAR20_PRECIO, banca));
        este.add(new Casilla("Trans4", "Transporte", 36, Valor.TRANSPORTE_PRECIO, banca));
        este.add(new Casilla("Suerte", "Suerte", 37, banca));
        este.add(new Casilla("Solar21", "Solar", 38, Valor.SOLAR21_PRECIO, banca));
        este.add(new Casilla("Imp2", 39, Valor.IMPUESTO_VALOR, banca));
        este.add(new Casilla("Solar22", "Solar", 40, Valor.SOLAR22_PRECIO, banca));
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
        int alto = 3;
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



    /////////////////////////////////MIERDAS AUXILIARES///////////////////////////////////////
    // Pequeño método auxiliar para rellenar nombres cortos y que todo quede alineado
    private String pad(String texto, int ancho) {
        if (texto.length() >= ancho) return texto.substring(0, ancho);
        return texto + " ".repeat(ancho - texto.length());
    }


    //Método usado para buscar la casilla con el nombre pasado como argumento:
    public Casilla encontrar_casilla(String nombre){
        //pongo un return NULL para ver si funciona, luego se quita esto
        return null;
    }

    private String getContenidoCasilla(Casilla c, int fila, int ancho) {
        String nombre = c.getNombre();
        String tipo = c.getTipo();

        // Cada casilla tendrá 3 filas de texto
        //.format es similar a un printf pero en vez de sacarlo por pantalla lo guarda en una cadena
        if (fila == 0) return String.format("┌%-" + (ancho - 2) + "s┐", "─".repeat(ancho - 2));
        if (fila == 1) return String.format("│%-" + (ancho - 2) + "s│", recortarCentro(nombre, ancho - 2));
        if (fila == 2) return String.format("│%-" + (ancho - 2) + "s│", recortarCentro(tipo, ancho - 2));
        return "";
    }

    // Centra o recorta texto dentro de una casilla
    //substring crea una cadena q empieza en el primer parametro y acaba en el segundo
    private String recortarCentro(String texto, int ancho) {
        if (texto.length() > ancho) return texto.substring(0, ancho);
        int espacio = ancho - texto.length();
        int izquierda = espacio / 2;
        int derecha = espacio - izquierda;
        return " ".repeat(izquierda) + texto + " ".repeat(derecha);
    }
}
