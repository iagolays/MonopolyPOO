package monopoly;

import partida.*; /*impórtanse todas as clases do paquete*/
import java.io.FileNotFoundException; //obligatorio para que funcione new Scanner ao ler o arquivo
import java.util.ArrayList;
import java.io.File; //impórtanse a clase para ficheiros
import java.util.Scanner;

import static monopoly.Casilla.imprimir_en_venta;

public class Menu {

    //Atributos
    private ArrayList<Jugador> jugadores = new ArrayList<>(); //Jugadores de la partida.
    private ArrayList<Avatar> avatares = new ArrayList<>(); //Avatares en la partida.
    private int turno = 0; //Índice correspondiente a la posición en el arrayList del jugador (y el avatar) que tienen el turno
    private int lanzamientos; //Variable para contar el número de lanzamientos de un jugador en un turno.
    private Tablero tablero; //Tablero en el que se juega.
    private Dado dado1; //Dos dados para lanzar y avanzar casillas.
    private Dado dado2;
    private Jugador banca; //El jugador banca.
    private boolean tirado; //Booleano para comprobar si el jugador que tiene el turno ha tirado o no.
    private boolean solvente; //Booleano para comprobar si el jugador que tiene el turno es solvente, es decir, si ha pagado sus deudas.

    public Menu() { //constructor de menu

        //creamos banca (xogador) e tableiro
        banca = new Jugador();
        tablero = new Tablero(banca); //asociámolo coa banca

        //Creamos os dados
        dado1 = new Dado();
        dado2 = new Dado();

        //Inicializamos variables de cotrol
        jugadores = new ArrayList<>();
        avatares = new ArrayList<>();
        turno = 0;
        lanzamientos = 0;
        tirado = false;
        solvente = true;

        iniciarPartida();
    }


    private void crearJugador(String nombre, String tipoAvatar) {

        // Buscamos la casilla de salida
        Casilla salida = tablero.encontrar_casilla("Salida");

        // Creamos el jugador
        Jugador jugador = new Jugador(nombre, tipoAvatar, salida, avatares);

        // Añadimos jugador y avatar a los ArrayLists de Menu
        jugadores.add(jugador);
        avatares.add(jugador.getAvatar());

        // Mostramos mensaje de confirmación
        System.out.println("{");
        System.out.println("  \"Nome\": \"" + jugador.getNombre() + "\",");
        System.out.println("  \"Avatar\": \"" + jugador.getAvatar().getId() + "\"");
        System.out.println("}");
    }

    // Metodo para inciar una partida: crea los jugadores y avatares.
    private void iniciarPartida() { //inicia partida se non se mete nada por comandos
        System.out.println("=== MODO INTERACTIVO ===");
        System.out.println("Benvido ao Monopoly!");
        System.out.println("Usa os comandos dispoñibles...");

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("$> ");
            String comando = sc.nextLine().trim();

            if (comando.equalsIgnoreCase("saír") || comando.equalsIgnoreCase("salir")) {
                System.out.println("Saíndo do xogo...");
                break;
            }

            // Procesamos el comando usando el metodo ya definido
            procesarComando(comando);
        }
        sc.close();
    }

    public void lerComandos(String nomeFicheiro) {
        lerFicheiroComandos(nomeFicheiro);
    }

    private void lerFicheiroComandos(String nomeFicheiro){
        File ficheiro = new File(nomeFicheiro); //creamos o obxecto file

        //comprobamos se o ficheiro existe
        if (!ficheiro.exists()){
            System.out.println("O ficheiro non existe: " + nomeFicheiro);
            return;
        }
        Scanner scan = null;
        try{
            scan = new Scanner(ficheiro); //lemos o ficheiro
        } catch (FileNotFoundException e) { //obligatorio para que funcione new Scanner
            System.out.println("Non se pode abrir o fichero: " + e.getMessage()); //mensaxe de erro porque non se abriu ben
            return;
        }
        //Lemos liña por liña
        while (scan.hasNextLine()){
            String line = scan.nextLine().trim(); //eliminamos espacios ao inicio e ao final
            if (!line.isEmpty()){ //non facemos caso das liñas baleiras
                analizarComando(line); //Procesamos cada comando
            }
        }
        scan.close(); //cerramos o scanner
    }

    /*Metodo que interpreta el comando introducido y toma la accion correspondiente.
    * Parámetro: cadena de caracteres (el comando).
    */

    public void procesarComando(String comando) {
        analizarComando(comando);
    }

    private void analizarComando(String comando) { //analizar o que mete o usuario na partida
        //eliminamos espacios ao principio e ao final, separamos por espacios as distintas palabras
        String[] partes = comando.trim().split("\\s+"); //\\s cualqueira caracter baleiro, + un ou mais caracteres seguidos

        //comprobamos que se o array está baleiro, polo que a liña tamén o está
        if (partes.length == 0){
            return;
        } //liña baleira

        switch (partes[0].toLowerCase()) { //usamos o primeiro comando en minúsculas para que sexa insensible a minúsculas/maiúsculas
            case "crear": //o comando a empregar é: crear jugador <nombre> <tipoAvatar>
                if (partes.length >= 4 && partes[1].equalsIgnoreCase("jugador")) { //teñen que ser polo menos 4 palabras no comando e que o segundo sexa "xogador"
                    crearJugador(partes[2], partes[3]); //gardamos o nome e tipoAvatar, e creamos o novo xogador
                }
                break;

            case "jugador": //o comando a empregar é: jugador, e mostra o xogador que ten o turno
                if (!jugadores.isEmpty()) { //verificamos que haxa, polo menos, un xogador na partida
                    Jugador actual = jugadores.get(turno); //obtemos o xogador que ten o turno
                    System.out.println("{");
                    System.out.println(" \"Nome\": \"" + actual.getNombre() + "\",");
                    System.out.println(" \"Avatar\": \"" + actual.getAvatar().getId() + "\",");
                    System.out.println("}");
                } else {
                    System.out.println("Non hai xogadores na partida.");
                }
                break;
            case "listar": //o comando a empregar é: listar xogadores
                if (partes.length >= 2) {
                    if (partes[1].equalsIgnoreCase("jugadore")) {
                        listarJugadores();
                    } else if (partes[1].equalsIgnoreCase("enventa")) {
                        //amósase o tipo, o valor e o grupo das propiedades en venta
                        listarVenta(tablero, banca);
                    } else if (partes[1].equalsIgnoreCase("avatares")) {
                        listarAvatares();
                    }
                }
                break;
            case "lanzar":
                if (partes.length == 2 && partes[1].equalsIgnoreCase("dados")) {
                    lanzarDados(); // lanzamiento aleatorio
                }
                else if (partes.length == 3 && partes[1].equalsIgnoreCase("dados")) {
                    forzarDados(partes[2]); // Pasar "2+4" ou similar
                } else {
                    System.out.println("Formato: lanzar dados  ou  lanzar dados X+Y");
                    System.out.println("Exemplo: lanzar dados 2+4");
                }
                break;
            case "describir":
                if (partes.length >= 3 && partes[1].equalsIgnoreCase("jugador")){
                    descJugador(partes);
                } else if (partes.length == 2) { //describir casillas
                    descCasilla(partes[1]);
                }
                break;
            case "comprar":
                if (partes.length >= 2){
                    comprar(partes[1]);
                }
                break;
            case "salir":
                if (partes.length >= 2 && partes[1].equalsIgnoreCase("carcel")){
                    salirCarcel();
                }
                break;
            case "acabar":
                if (partes.length >= 2 && partes[1].equalsIgnoreCase("turno")) {
                    acabarTurno();
                }
                break;
            case "ver":
                if (partes.length >= 2 && partes[1].equalsIgnoreCase("tablero")){
                    System.out.println(tablero);
                }
            default:
                System.out.println("Comando invalido"); //calqueira comando que non sexa algún dos anteriores
        }
    }

    //Metodo que realiza las acciones asociadas al comando 'describir jugador'.
        // /* Parámetro: comando introducido*/
    private void descJugador(String[] partes) {

        if (partes.length < 3) {
            System.out.println("Uso: describir jugador <nombre>");
            return;
        }

        String nombreBuscado = partes[2];
        Jugador jugador = null;

        //Buscar jugador (debes tener una lista llamada 'jugadores' o similar)

        for (Jugador j: jugadores){
            if (j.getNombre().equalsIgnoreCase(nombreBuscado)) {
                jugador = j;
                break;
            }
        }

        if (jugador == null) {
            System.out.println("No existe ningún jugador con ese nombre.");
            return;
        }

        //Imprimir estructura

        System.out.println("{");
        System.out.println("nombre: " + jugador.getNombre() + ",");
        System.out.println("avatar: " + (jugador.getAvatar() != null ? jugador.getAvatar().getId() : "-") + ",");
        System.out.println("fortuna: " + jugador.getFortuna() + ",");

        //Propiedades

        System.out.print("propiedades: [");
        ArrayList<Casilla> props = jugador.getPropiedades();
        for (int i = 0; i < props.size(); i++) {
            System.out.print(props.get(i).getNombre());
            if (i < props.size() - 1) System.out.print(", ");
        }
        System.out.println("]");

            // Hipotecas

        System.out.print("hipotecas: [");
        ArrayList<Casilla> hips = jugador.getHipotecas();
        for (int i = 0; i < hips.size(); i++) {
            System.out.print(hips.get(i).getNombre());
            if (i < hips.size() - 1) System.out.print(", ");
        }
        System.out.println("],");

        // Edificios

        System.out.print("edificios: [");
        ArrayList<String> eds = jugador.getEdificios();
        for (int i = 0; i < eds.size(); i++) {
            System.out.print(eds.get(i));
            if (i < eds.size() - 1) System.out.print(", ");
        }
        System.out.println("]");
        System.out.println("}");
    }

    /*Metodo que realiza las acciones asociadas al comando 'describir avatar'.
    * Parámetro: id del avatar a describir.
    */
/*    ESTO SE SUPONE QUE VA BIEN
    private void descAvatar(String ID) {

        //Buscar el avatar por ID
        Avatar avatar = null;
        for (Avatar a : avatares) {
            if (a.getId().equalsIgnoreCase(ID)) {
                avatar = a;
                break;
            }
        }

        if (avatar == null) {
            System.out.println("No se encontró el avatar con ID: " + ID);
            return;
        }

 */
/*   ESTO SE SUPONE QUE VA FETÉN CON EL AVATAR "DEFINITIVO"
        //Imprimir información del avatar
        System.out.println("$> describir avatar " + avatar.getId());
        System.out.println("{");
        System.out.println("ID: " + avatar.getId() + ",");
        System.out.println("Tipo: " + avatar.tipo + ","); // atributo directo
        System.out.println("Jugador: " + avatar.jugador.getNombre() + ",");
        System.out.println("Posición: " + (avatar.lugar != null ? avatar.lugar.getNombre() : "-") + ",");
        System.out.println("Valor de la casilla: " + (avatar.lugar != null ? (int)avatar.lugar.getValor() : 0) + ",");
        System.out.println("Alquiler de la casilla: " + (avatar.lugar != null ? (int)avatar.lugar.getImpuesto() : 0));
        System.out.println("}");
    }
*/
    /* Metodo que realiza las acciones asociadas al comando 'describir nombre_casilla'.
    * Parámetros: nombre de la casilla a describir.
    */

    public void descCasilla(String nombre) {
        Casilla c = tablero.encontrar_casilla(nombre);
        if (c == null) {
            System.out.println("No se encontró la casilla: " + nombre);
            return;
        }

        String tipo = c.getTipo().toLowerCase();
        if (tipo.equals("suerte") || tipo.equals("comunidad") || tipo.equals("ir_carcel")) {
            System.out.println("No se puede describir esta casilla.");
            return;
        }

        System.out.println("$> describir " + c.getNombre());
        System.out.println("{");

        switch (tipo) {
            case "solar":
                System.out.println("tipo: solar,");
                System.out.println("grupo: " + (c.getGrupo() != null ? c.getGrupo().getColorGrupo() : "-") + ",");
                System.out.println("propietario: " + (c.getDuenho() != null ? c.getDuenho().getNombre() : "Banca") + ",");

                // Usamos el valor y hipoteca directamente de la casilla
                System.out.println("valor: " + (int)c.getValor() + ",");
                System.out.println("hipoteca: " + (int)c.getHipoteca() + ",");
                System.out.println("alquiler: " + (int)c.getImpuesto());

                break;

            case "transporte":
            case "serv":
                System.out.println("tipo: " + tipo + ",");
                System.out.println("propietario: " + (c.getDuenho() != null ? c.getDuenho().getNombre() : "Banca") + ",");
                System.out.println("valor: " + (int)c.getValor() + ",");
                System.out.println("alquiler: " + (int)c.getImpuesto());
                break;

            case "impuesto":
                System.out.println("tipo: impuesto,");
                System.out.println("apagar: " + (int)c.getImpuesto());
                break;

            case "parking":
                System.out.println("bote: " + (int)c.getValor() + ",");
                System.out.print("jugadores: [");
                for (int i = 0; i < c.getAvatares().size(); i++) {
                    System.out.print(c.getAvatares().get(i).getJugador().getNombre());
                    if (i < c.getAvatares().size() - 1) System.out.print(", ");
                }
                System.out.println("]");
                break;

            case "carcel":
                System.out.println("salir: " + (int)Valor.CARCEL_SALIDA + ",");
                System.out.print("jugadores: [");
                for (int i = 0; i < c.getAvatares().size(); i++) {
                    System.out.print(c.getAvatares().get(i).getJugador().getNombre());
                    if (i < c.getAvatares().size() - 1) System.out.print(", ");
                }
                System.out.println("]");
                break;

            default:
                System.out.println("Tipo de casilla no reconocido.");
                break;
        }

        System.out.println("}");
    }


    //Metodo que ejecuta todas las acciones relacionadas con el comando 'lanzar dados'.
    private void lanzarDados() {
        Jugador actual = jugadores.get(turno);
        Avatar avatar = actual.getAvatar();

        int valor1 = dado1.hacerTirada();
        int valor2 = dado2.hacerTirada();
        int total = valor1 + valor2;

        System.out.println(actual.getNombre() + " lanza os dados: [" + valor1 + "," +valor2 + "] = " + total);

        //movemos o avatar no taboleiro

        tirado = true;
        lanzamientos++;

        //mostramos a nova posición
        System.out.println("Nova posición: ");
    }

    private void forzarDados(String valores) {
        if (jugadores.isEmpty()) {
            System.out.println("Non hai xogadores na partida");
            return;
        }

        Jugador actual = jugadores.get(turno);

        // Verificar se está na cárcere
        if (actual.isEnCarcel()) {
            System.out.println(actual.getNombre() + " está na cárcere. Use 'salir carcel' para saír.");
            return;
        }

        try {
            // Separar os valores (ex: "2+4" → ["2", "4"])
            String[] partesValores = valores.split("\\+");

            if (partesValores.length != 2) {
                System.out.println("Formato incorrecto. Use: lanzar dados X+Y (ex: lanzar dados 2+4)");
                return;
            }

            // Converter a números
            int dado1Valor = Integer.parseInt(partesValores[0].trim());
            int dado2Valor = Integer.parseInt(partesValores[1].trim());

            // Validar rangos
            if (dado1Valor < 1 || dado1Valor > 6 || dado2Valor < 1 || dado2Valor > 6) {
                System.out.println("Erro: Os valores dos dados deben estar entre 1 e 6");
                return;
            }

            int total = dado1Valor + dado2Valor;

            System.out.println("Dados forzados: " + dado1Valor + " + " + dado2Valor + " = " + total);

            // Mover avatar
            Avatar avatar = actual.getAvatar();
            Casilla casillaAnterior = avatar.getLugar();

            //avatar.moverAvatar(tablero.getPosiciones(), total);

            Casilla casillaActual = avatar.getLugar();

            // Mensaxe de movemento (formato exacto dos requisitos)
            System.out.println("El avatar " + avatar.getId() + " avanza " + total +
                    " posiciones, desde " + casillaAnterior.getNombre() +
                    " hasta " + casillaActual.getNombre() + ".");

            // Avaliar a casilla (segundo requisitos do Comando 6)
            boolean solvente = casillaActual.evaluarCasilla(actual, banca, total);

            if (!solvente) {
                System.out.println(actual.getNombre() + " non é solvente!");
                System.out.println("Opcións: 'hipotecar <propiedade>' ou declararase en bancarrota");

                // Mostrar propiedades dispoñibles para hipoteca
                System.out.println("🏠 Propiedades dispoñibles para hipoteca:");
                boolean hayPropiedades = false;
                for (Casilla propiedad : actual.getPropiedades()) {
                    if (!propiedad.isHipotecada()) {
                        System.out.println("   - " + propiedad.getNombre() +
                                " (Hipoteca: " + propiedad.getHipoteca() + "€)");
                        hayPropiedades = true;
                    }
                }
                if (!hayPropiedades) {
                    System.out.println("Non ten propiedades para hipotecar");
                }
            }

            tirado = true;

            // Repintar taboleiro (Comando 1)


            // Verificar dados dobres (para turno extra)
            if (dado1Valor == dado2Valor) {
                System.out.println("¡Dados dobres! " + actual.getNombre() + " pode lanzar de novo.");
                tirado = false; // Permite lanzar de novo
            }

        } catch (NumberFormatException e) {
            System.out.println("Erro: Os valores deben ser números. Use: lanzar dados X+Y (ex: lanzar dados 2+4)");
        } catch (Exception e) {
            System.out.println("Erro ao procesar o comando: " + e.getMessage());
            System.out.println("Formato correcto: lanzar dados X+Y (ex: lanzar dados 2+4)");
        }
    }


    /*Metodo que ejecuta todas las acciones realizadas con el comando 'comprar nombre_casilla'.
    * Parámetro: cadena de caracteres con el nombre de la casilla.
     */

    private void comprar(String nombre) {
        Jugador jugadorActual = jugadores.get(turno); // Jugador que tiene el turno
        Casilla casilla = tablero.encontrar_casilla(nombre);

        if (casilla == null) {
            System.out.println("A casilla " + nombre + " non existe.");
            return;
        }

        if (!casilla.getTipo().equalsIgnoreCase("solar") &&
                !casilla.getTipo().equalsIgnoreCase("transporte") &&
                !casilla.getTipo().equalsIgnoreCase("servicio")) {
            System.out.println("Non se pode comprar a casilla " + nombre + ".");
            return;
        }

        if (casilla.getDuenho() != null) {
            System.out.println("A casilla " + nombre + " xa ten propietario.");
            return;
        }

        float precio = casilla.getValor();
        if (jugadorActual.getFortuna() >= precio) {
            jugadorActual.sumarFortuna(-precio);
            casilla.setDuenho(jugadorActual);
            jugadorActual.getPropiedades().add(casilla);

            System.out.println(jugadorActual.getNombre() + " compra a casilla " + nombre + " por " + precio + "€. A súa fortuna actual é " +
                    jugadorActual.getFortuna() + "€.");
        } else {
            System.out.println(jugadorActual.getNombre() + " non pode pagar " + precio + "€.");

        }
    }

    // Novo metodo: xestiona pagos e posibles bancarrotas
    private void pagar(Jugador pagador, Jugador receptor, float cantidade) {
        if (pagador.getFortuna() >= cantidade) {
            //Pode pagar normalmente
            pagador.setFortuna(pagador.getFortuna() - cantidade);

            if (receptor != null) {
                receptor.setFortuna(receptor.getFortuna() + cantidade);
                System.out.println(pagador.getNombre() + " paga " + (int)cantidade +
                        "€ a " + receptor.getNombre() + ".");
            } else {
                //Non hai receptor (imposto, sorte, etc.) → vai ao bote do Parking
                sumarParking(cantidade);
                System.out.println(pagador.getNombre() + " paga " + (int)cantidade +
                        "€ ao bote do Parking.");
            }
        } else {
            //Non pode pagar
            System.out.println(pagador.getNombre() + " non pode pagar " + (int)cantidade + "€.");
            System.out.println("// Aquí podería sumarse ao bote do Parking");
            System.out.println("// Non pode pagar → opción hipotecar ou bancarrota");

            // Declaramos bancarrota (isto chama internamente a transferirPropiedades)

        }
    }

    // Novo metodo: engade cartos ao bote do Parking
    private void sumarParking(float cantidade) {
        Casilla parking = tablero.encontrar_casilla("Parking");
        if (parking != null) {
            parking.setValor(parking.getValor() + cantidade);
            System.out.println("Engádense " + (int) cantidade + "€ ao bote do Parking.");
        }
    }


    //Metodo que ejecuta todas las acciones relacionadas con el comando 'salir carcel'.
    private void salirCarcel() {
        final float PRECIO_SALIR_CARCEL = 500_000;

        Jugador jugador = jugadores.get(turno); // jugador que tiene el turno

        if (!jugador.isEnCarcel()) {
            System.out.println(jugador.getNombre() + " no está en la cárcel.");
            return;
        }

        if (jugador.getFortuna() >= PRECIO_SALIR_CARCEL) {
            jugador.setFortuna(jugador.getFortuna() - PRECIO_SALIR_CARCEL);
            jugador.setEnCarcel(false);
            jugador.setTiradasCarcel(0);

            System.out.println(jugador.getNombre() + " paga " + (int)PRECIO_SALIR_CARCEL +
                    "€ y sale de la cárcel. Puede lanzar los dados.");
        } else {
            System.out.println(jugador.getNombre() + " no tiene suficiente dinero para salir de la cárcel.");
        }
    }


    // Metodo que realiza las acciones asociadas al comando 'listar enventa'.
    private void listarVenta(Tablero tablero, Jugador banca) {

        // Primero, imprimimos el tablero completo
        System.out.println(tablero);

        // Luego, imprimimos las casillas en venta por cada lado
        imprimir_en_venta(tablero.getLado(0), banca); // Sur
        imprimir_en_venta(tablero.getLado(1), banca); // Oeste
        imprimir_en_venta(tablero.getLado(2), banca); // Norte
        imprimir_en_venta(tablero.getLado(3), banca); // Este
    }



    // Metodo que realiza las acciones asociadas al comando 'listar jugadores'.
    private void listarJugadores() {
        if (jugadores.isEmpty()){
            System.out.println("Non hai xogadores na partida.");
            return;
        }

        System.out.println("Xogadores na partida: ");
        for (Jugador j : jugadores){
            System.out.println("- " + j.getNombre() + " (Avatar: " + j.getAvatar().getId() + ")");
        }
    }

    // Metodo que realiza las acciones asociadas al comando 'listar avatares'.
    private void listarAvatares() {
        if (avatares.isEmpty()){
            System.out.println("Non hai avatares creados.");
            return;
        }
        System.out.println("Avatares na partida: ");
        for (Avatar av : avatares) {
            System.out.println("- " + av.getId() + " (Propietario: " + av.getJugador().getNombre() + ")");
        }
    }

    // Metodo que realiza las acciones asociadas al comando 'acabar turno'.
    private void acabarTurno() {
        if (jugadores.isEmpty()){
            System.out.println("Non hai xogadores");
            return;
        }
        turno = (turno +1) % jugadores.size(); //pasamos ao seguinte xogador
        lanzamientos = 0;
        tirado = false;
        solvente = true;

        Jugador siguiente = jugadores.get(turno);
        System.out.println("----Novo turno----");
        System.out.println("É o turno de: " + siguiente.getNombre());
    }
}
