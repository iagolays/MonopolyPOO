package monopoly;

import partida.*; /*impórtanse todas as clases do paquete*/
import java.util.ArrayList;
import java.util.Scanner; //Para ler a entrada do usuario
import java.io.File; //impórtanse a clase para ficheiros
import java.io.FileNotFoundException; //obligatorio para que funcione new Scanner ao ler o arquivo

public class Menu {

    //Atributos
    private ArrayList<Jugador> jugadores; //Jugadores de la partida.
    private ArrayList<Avatar> avatares; //Avatares en la partida.
    private int turno = 0; //Índice correspondiente a la posición en el arrayList del jugador (y el avatar) que tienen el turno
    private int lanzamientos; //Variable para contar el número de lanzamientos de un jugador en un turno.
    private Tablero tablero; //Tablero en el que se juega.
    private Dado dado1; //Dos dados para lanzar y avanzar casillas.
    private Dado dado2;
    private Jugador banca; //El jugador banca.
    private boolean tirado; //Booleano para comprobar si el jugador que tiene el turno ha tirado o no.
    private boolean solvente; //Booleano para comprobar si el jugador que tiene el turno es solvente, es decir, si ha pagado sus deudas.


    public Menu() { //constructor do menú
        // Inicializamos as listas de xogadores e avatares
        this.jugadores = new ArrayList<>();
        this.avatares = new ArrayList<>();
        this.banca = new Jugador(); // Creamos a banca do xogo
        this.tablero = new Tablero(banca); // Creamos o taboleiro asociado á banca
        // Creamos os dous dados para o xogo
        this.dado1 = new Dado();
        this.dado2 = new Dado();
        this.turno = 0; // Comeza o primeiro xogador
        this.lanzamientos = 0; // Aínda non houbo lanzamentos
        this.tirado = false; // Aínda non se lanzaron dados
        this.solvente = true; // Asumimos que é solvente inicialmente
        this.iniciarPartida(); // Iniciamos a partida en modo interactivo
    }

    // Metodo para iniciar unha partida: crea os xogadores e avatares
    private void iniciarPartida() {
        System.out.println("=== MODO INTERACTIVO ===");
        System.out.println("Benvido ao Monopoly!");
        System.out.println("Usa os comandos dispoñibles...");
        Scanner sc = new Scanner(System.in); // Creamos o scanner para ler a entrada do usuario

        while (true) { // Bucle principal do xogo
            System.out.print("$> "); // Mostramos o prompt para o comando
            String comando = sc.nextLine().trim(); // Leemos o comando introducido polo usuario

            if (comando.equalsIgnoreCase("salir")) { // Comprobamos se o usuario quere saír do xogo
                System.out.println("Saíndo do xogo...");
                System.out.println("Ata pronto!");
                break; // Saímos do bucle
            } else if (comando.startsWith("comandos ")) { // Executar comandos desde ficheiro
                String nomeFicheiro = comando.substring(9);
                this.lerFicheiroComandos(nomeFicheiro);
            } else {
                this.analizarComando(comando); // Procesamos o comando introducido
            }
        }
        sc.close(); // Pechamos o scanner ao saír do xogo
    }

    /* Metodo que interpreta o comando introducido e toma a acción correspondente
     * Parámetro: cadea de caracteres (o comando)
     */
    private void analizarComando(String comando) {
        String[] partes = comando.trim().split("\\s+"); // Separamos por espazos as distintas palabras

        if (partes.length == 0) return; // Se está baleiro, non facemos nada

        switch (partes[0].toLowerCase()) { // Usamos o primeiro comando en minúsculas
            case "crear": // Comando: crear jugador <nombre> <tipoAvatar>
                if (partes.length >= 4 && partes[1].equalsIgnoreCase("jugador")) {
                    this.crearJugador(partes[2], partes[3]); // Creamos o novo xogador
                }
                break;
            case "jugador": // Comando: jugador - mostra o xogador actual
                this.mostrarJugadorActual();
                break;
            case "listar": // Comando: listar xogadores ou listar enventa
                if (partes.length >= 2) {
                    if (partes[1].equalsIgnoreCase("jugadores")) {
                        this.listarJugadores();
                    } else if (partes[1].equalsIgnoreCase("enventa")) {
                        this.listarVenta(); // Lista propiedades en venda
                    } else if (partes[1].equalsIgnoreCase("avatares")) {
                        this.listarAvatares();
                    }
                }
                break;
            case "lanzar": // Comando: lanzar dados [valor]
                if (partes.length >= 2 && partes[1].equalsIgnoreCase("dados")) {
                    if (partes.length >= 3) {
                        this.forzarDados(partes[2]); // Lanzamento forzado
                    } else {
                        this.lanzarDados(); // Lanzamento aleatorio
                    }
                }
                break;
            case "describir": // Comando: describir jugador <nome> ou describir <casilla>
                if (partes.length >= 3 && partes[1].equalsIgnoreCase("jugador")) {
                    this.descJugador(partes);
                } else if (partes.length >= 2) {
                    this.descCasilla(partes[1]); // Describir casilla
                }
                break;
            case "comprar": // Comando: comprar <propiedade>
                if (partes.length >= 2) {
                    this.comprar(partes[1]);
                }
                break;
            case "salir": // Comando: salir carcel
                if (partes.length >= 2 && partes[1].equalsIgnoreCase("carcel")) {
                    this.salirCarcel();
                }
                break;
            case "acabar": // Comando: acabar turno
                if (partes.length >= 2 && partes[1].equalsIgnoreCase("turno")) {
                    this.acabarTurno();
                }
                break;
            case "ver": // Comando: ver tablero
                if (partes.length >= 2 && partes[1].equalsIgnoreCase("tablero")) {
                    this.verTablero();
                }
                break;
            default:
                System.out.println("Comando non recoñecido: " + comando);
        }
    }

    /* Metodo que realiza as accións asociadas ao comando 'describir jugador'
     * Parámetro: comando introducido
     */
    private void descJugador(String[] partes) {
        if (partes.length < 3) { // Verificamos que o comando teña o formato correcto
            System.out.println("Uso: describir jugador <nombre>");
            return;
        }

        String nombreBuscado = partes[2]; // Obtenemos o nome do xogador a describir
        Jugador jugador = null;

        for (Jugador j: jugadores) { // Buscamos o xogador na lista de xogadores
            if (j.getNombre().equalsIgnoreCase(nombreBuscado)) {
                jugador = j;
                break;
            }
        }

        if (jugador == null) { // Se non atopamos o xogador, mostramos erro
            System.out.println("Non existe ningún xogador con ese nome.");
            return;
        }

        // Mostramos a información do xogador no formato requirido
        System.out.println("{");
        System.out.println("nome: " + jugador.getNombre() + ",");
        System.out.println("avatar: " + jugador.getAvatar().getId() + ",");
        System.out.println("fortuna: " + (int)jugador.getFortuna() + ",");

        // Mostramos as propiedades do xogador
        System.out.print("propiedades: [");
        ArrayList<Casilla> props = jugador.getPropiedades();
        for (int i = 0; i < props.size(); i++) {
            System.out.print(props.get(i).getNombre());
            if (i < props.size() - 1) System.out.print(", ");
        }
        System.out.println("],");

        // Mostramos as hipotecas (baleiro na primeira entrega)
        System.out.print("hipotecas: [");
        ArrayList<Casilla> hips = jugador.getHipotecas();
        if (hips.isEmpty()) {
            System.out.print("-");
        } else {
            for (int i = 0; i < hips.size(); i++) {
                System.out.print(hips.get(i).getNombre());
                if (i < hips.size() - 1) System.out.print(", ");
            }
        }
        System.out.println("],");

        // Mostramos os edificios (baleiro na primeira entrega)
        System.out.print("edificios: [");
        ArrayList<String> eds = jugador.getEdificios();
        if (eds.isEmpty()) {
            System.out.print("-");
        } else {
            for (int i = 0; i < eds.size(); i++) {
                System.out.print(eds.get(i));
                if (i < eds.size() - 1) System.out.print(", ");
            }
        }
        System.out.println("]");
        System.out.println("}");
    }

    /* Metodo que realiza as accións asociadas ao comando 'describir nombre_casilla'
     * Parámetros: nome da casilla a describir
     */
    public void descCasilla(String nombre) {
        Casilla c = this.tablero.encontrar_casilla(nombre); // Buscamos a casilla no taboleiro
        if (c == null) { // Se non se atopa a casilla, mostramos erro
            System.out.println("Non se atopou a casilla: " + nombre);
            return;
        }

        String tipo = c.getTipo().toLowerCase();
        // Non se poden describir estas casillas na primeira entrega
        if (tipo.equals("suerte") || tipo.equals("comunidad") || tipo.equals("ircarcel")) {
            System.out.println("Non se pode describir esta casilla na primeira entrega.");
            return;
        }

        System.out.println(c.infoCasilla()); // Mostramos a información da casilla
    }

    // Metodo que executa todas as accións relacionadas co comando 'lanzar dados'
    private void lanzarDados() {
        if (this.jugadores.isEmpty()) { // Verificamos que haxa xogadores na partida
            System.out.println("Non hai xogadores na partida");
            return;
        }

        Jugador actual = this.jugadores.get(this.turno); // Obtenemos o xogador actual

        if (actual.isEnCarcel()) { // Verificamos se o xogador está no cárcere
            System.out.println(actual.getNombre() + " está no cárcere. Use 'salir carcel' para saír.");
            return;
        }

        // Facemos a tirada aleatoria dos dados
        int valor1 = this.dado1.hacerTirada();
        int valor2 = this.dado2.hacerTirada();
        int total = valor1 + valor2;

        // Procesamos o movemento do xogador
        this.procesarMovimento(actual, total, "Tirada: " + valor1 + " + " + valor2 + " = " + total);
    }

    /* Metodo que executa todas as accións realizadas co comando 'comprar nombre_casilla'
     * Parámetro: cadea de caracteres co nome da casilla
     */
    private void comprar(String nombre) {
        if (this.jugadores.isEmpty()) { // Verificamos que haxa xogadores na partida
            System.out.println("Non hai xogadores na partida");
            return;
        }

        Jugador jugadorActual = this.jugadores.get(this.turno); // Xogador que ten o turno
        Casilla casilla = this.tablero.encontrar_casilla(nombre); // Buscamos a casilla no taboleiro

        if (casilla == null) { // Verificamos que a casilla exista
            System.out.println("A casilla " + nombre + " non existe.");
            return;
        }

        // Verificamos que a casilla sexa comprable
        if (!casilla.getTipo().equalsIgnoreCase("solar") &&
                !casilla.getTipo().equalsIgnoreCase("transporte") &&
                !casilla.getTipo().equalsIgnoreCase("servicio")) {
            System.out.println("Non se pode comprar a casilla " + nombre + ".");
            return;
        }

        // Verificamos que a casilla non teña dono ou sexa da banca
        if (casilla.getDuenho() != null && casilla.getDuenho() != this.banca) {
            System.out.println("A casilla " + nombre + " xa ten propietario.");
            return;
        }

        casilla.comprarCasilla(jugadorActual, this.banca); // Chamamos ao método de compra da casilla
    }

    // Metodo que executa todas as accións relacionadas co comando 'salir carcel'
    private void salirCarcel() {
        if (this.jugadores.isEmpty()) return; // Verificamos que haxa xogadores

        Jugador jugador = this.jugadores.get(this.turno); // Xogador que ten o turno

        if (!jugador.isEnCarcel()) { // Verificamos que o xogador estea no cárcere
            System.out.println(jugador.getNombre() + " non está no cárcere.");
            return;
        }

        // Usamos o metodo salirCarcel() da clase Jugador que xa implementa a lóxica
        if (jugador.salirCarcel()) {
            // O metodo xa mostra a mensaxe, non necesitamos duplicar
        }
    }

    // Metodo que realiza as accións asociadas ao comando 'listar enventa'
    private void listarVenta() {
        boolean hayPropiedades = false; // Inicializamos unha bandeira para saber se hai propiedades en venda

        // Percorremos todos os lados do taboleiro
        for (int lado = 0; lado < 4; lado++) {
            ArrayList<Casilla> casillasLado = this.tablero.getLado(lado);
            for (Casilla casilla : casillasLado) { // Percorremos cada casilla do lado actual
                String infoVenta = casilla.casEnVenta(); // Obtenemos a información de venda da casilla
                if (!infoVenta.isEmpty()) { // Se a casilla está en venda, mostramos a información
                    System.out.println(infoVenta);
                    hayPropiedades = true;
                }
            }
        }

        if (!hayPropiedades) { // Se non hai propiedades en venda, mostramos mensaxe
            System.out.println("Non hai propiedades en venda");
        }
    }

    // Metodo que realiza as accións asociadas ao comando 'listar jugadores'
    private void listarJugadores() {
        if (jugadores.isEmpty()) { // Verificamos que haxa xogadores na partida
            System.out.println("Non hai xogadores na partida.");
            return;
        }

        // Percorremos todos os xogadores e mostramos a súa información
        for (int i = 0; i < this.jugadores.size(); i++) {
            Jugador j = this.jugadores.get(i);
            System.out.println("{");
            System.out.println("nome: " + j.getNombre() + ",");
            System.out.println("avatar: " + j.getAvatar().getId() + ",");
            System.out.println("fortuna: " + (int)j.getFortuna() + ",");

            // Mostramos as propiedades do xogador
            System.out.print("propiedades: [");
            ArrayList<Casilla> propiedades = j.getPropiedades();
            for (int k = 0; k < propiedades.size(); k++) {
                System.out.print(propiedades.get(k).getNombre());
                if (k < propiedades.size() - 1) System.out.print(", ");
            }
            System.out.println("],");

            // Mostramos as hipotecas
            System.out.print("hipotecas: [");
            ArrayList<Casilla> hips = j.getHipotecas();
            if (hips.isEmpty()) {
                System.out.print("-");
            } else {
                for (int k = 0; k < hips.size(); k++) {
                    System.out.print(hips.get(k).getNombre());
                    if (k < hips.size() - 1) System.out.print(", ");
                }
            }
            System.out.println("],");

            // Mostramos os edificios
            System.out.print("edificios: [");
            ArrayList<String> eds = j.getEdificios();
            if (eds.isEmpty()) {
                System.out.print("-");
            } else {
                for (int k = 0; k < eds.size(); k++) {
                    System.out.print(eds.get(k));
                    if (k < eds.size() - 1) System.out.print(", ");
                }
            }
            System.out.println("]");
            System.out.println("}");
            if (i < this.jugadores.size() - 1) System.out.println(","); // Separador entre xogadores
        }
    }

    // Metodo que realiza as accións asociadas ao comando 'listar avatares'
    private void listarAvatares() {
        System.out.println("Comando non dispoñible na primeira entrega"); // Non se require na primeira entrega
    }

    // Metodo que realiza as accións asociadas ao comando 'acabar turno'
    private void acabarTurno() {
        if (this.jugadores.isEmpty()) { // Verificamos que haxa xogadores na partida
            System.out.println("Non hai xogadores");
            return;
        }

        this.turno = (this.turno + 1) % this.jugadores.size(); // Avanzamos ao seguinte xogador
        this.lanzamientos = 0; // Reiniciamos o contador de lanzamentos
        this.tirado = false; // Marcamos que non se lanzaron dados no novo turno
        this.solvente = true; // Asumimos que o novo xogador é solvente

        Jugador siguiente = this.jugadores.get(this.turno); // Obtenemos o novo xogador actual
        System.out.println("O xogador actual é " + siguiente.getNombre() + "."); // Mostramos quen é o novo xogador
    }

    //----------Métodos auxiliares----------

    // Metodo para ler o ficheiro cos comandos introducidos
    public void lerFicheiroComandos(String nomeFicheiro) {
        File ficheiro = new File(nomeFicheiro); // Creamos un obxecto File co nome do ficheiro

        if (!ficheiro.exists()) { // Comprobamos se o ficheiro existe
            System.out.println("O ficheiro non existe: " + nomeFicheiro);
            return;
        }

        Scanner scanner = null;
        try {
            scanner = new Scanner(ficheiro); // Creamos un scanner para ler o ficheiro
            while (scanner.hasNextLine()) { // Leemos o ficheiro liña por liña
                String linea = scanner.nextLine().trim();
                if (!linea.isEmpty() && !linea.startsWith("#")) { // Ignoramos liñas baleiras e comentarios
                    System.out.println("$> " + linea); // Mostramos o comando que se vai executar
                    this.analizarComando(linea); // Procesamos o comando
                }
            }
        } catch (FileNotFoundException e) { // Mostramos erro se non se pode abrir o ficheiro
            System.out.println("Non se pode abrir o ficheiro: " + e.getMessage());
        } finally {
            if (scanner != null) scanner.close(); // Aseguramos que pechamos o scanner
        }
    }

    // Metodo auxiliar para procesar o movemento despois de lanzar dados
    private void procesarMovimento(Jugador actual, int total, String mensaxeTirada) {
        Avatar avatar = actual.getAvatar(); // Obtenemos o avatar do xogador actual
        Casilla casillaAnterior = avatar.getLugar(); // Gardamos a casilla anterior
        String nomeAnterior = casillaAnterior.getNombre();

        // Movemos o avatar polo taboleiro
        ArrayList<ArrayList<Casilla>> todasCasillas = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            todasCasillas.add(this.tablero.getLado(i));
        }
        avatar.moverAvatar(todasCasillas, total);

        Casilla casillaActual = avatar.getLugar(); // Obtenemos a nova casilla
        String nomeActual = casillaActual.getNombre();

        this.solvente = casillaActual.evaluarCasilla(actual, this.banca, total); // Avaliamos a casilla

        // Construímos o mensaxe de resultado
        StringBuilder resultado = new StringBuilder();
        resultado.append(mensaxeTirada).append("\n");
        resultado.append("O avatar ").append(avatar.getId())
                .append(" avanza ").append(total)
                .append(" posicións, desde ").append(nomeAnterior)
                .append(" ata ").append(nomeActual).append(".");

        // Engadimos información específica segundo o tipo de casilla
        if (casillaActual.getTipo().equalsIgnoreCase("solar") &&
                casillaActual.getDuenho() != null &&
                casillaActual.getDuenho() != actual &&
                casillaActual.getDuenho() != this.banca) {
            resultado.append(" Pagáronse ").append((int)casillaActual.getImpuesto()).append("€ de aluguer.");
        } else if (casillaActual.getTipo().equalsIgnoreCase("ircarcel")) {
            resultado.append(" O avatar colócase na casilla de Cárcel.");
        } else if (casillaActual.getTipo().equalsIgnoreCase("parking") && casillaActual.getValor() > 0) {
            resultado.append(" O xogador ").append(actual.getNombre())
                    .append(" recibe ").append((int)casillaActual.getValor()).append("€.");
        } else if (casillaActual.getTipo().equalsIgnoreCase("impuesto")) {
            resultado.append(" O xogador paga ").append((int)casillaActual.getImpuesto())
                    .append("€ que se depositan no Parking.");
        }

        System.out.println(resultado.toString()); // Mostramos o resultado do movemento
        System.out.println(this.tablero); // Mostramos o taboleiro actualizado

        if (!this.solvente) { // Se o xogador non é solvente, mostramos advertencia
            System.out.println(actual.getNombre() + " non ten diñeiro suficiente. Debe hipotecar propiedades ou declararse en bancarrota.");
        }

        this.tirado = true; // Marcamos que xa se lanzaron dados neste turno
    }

    // Metodo para lanzar dados con valores forzados
    private void forzarDados(String valores) {
        if (jugadores.isEmpty()) { // Verificamos que haxa xogadores na partida
            System.out.println("Non hai xogadores na partida");
            return;
        }

        Jugador actual = jugadores.get(turno); // Obtenemos o xogador actual

        if (actual.isEnCarcel()) { // Verificamos se o xogador está no cárcere
            System.out.println(actual.getNombre() + " está no cárcere. Use 'salir carcel' para saír.");
            return;
        }

        int[] valoresDados = Dado.procesarTiradaForzada(valores); // Procesamos o formato "2+4"
        if (valoresDados == null) {
            System.out.println("Formato de tirada forzada inválido. Use '2+4'");
            return;
        }

        int total = valoresDados[0] + valoresDados[1]; // Calculamos o total da tirada
        this.procesarMovimento(actual, total, "Tirada forzada: " + valoresDados[0] + " + " + valoresDados[1] + " = " + total);
    }

    // Metodo para mostrar o xogador actual
    private void mostrarJugadorActual() {
        if (this.jugadores.isEmpty()) { // Verificamos que haxa xogadores na partida
            System.out.println("Non hai xogadores na partida");
            return;
        }

        Jugador actual = this.jugadores.get(this.turno); // Obtenemos o xogador actual
        System.out.println("{");
        System.out.println("nome: " + actual.getNombre() + ",");
        System.out.println("avatar: " + actual.getAvatar().getId());
        System.out.println("}");
    }

    // Metodo para crear un xogador
    private void crearJugador(String nombre, String tipoAvatar) {
        for (Jugador j : this.jugadores) { // Verificamos se xa existe un xogador con ese nome
            if (j.getNombre().equalsIgnoreCase(nombre)) {
                System.out.println("Erro: Xa existe un xogador con ese nome");
                return;
            }
        }

        Casilla salida = this.tablero.encontrar_casilla("Salida"); // Buscamos a casilla de salida
        if (salida == null) {
            System.out.println("Erro: Non se atopou a casilla Salida");
            return;
        }

        // Creamos o novo xogador
        Jugador novoJugador = new Jugador(nombre, tipoAvatar, salida, this.avatares);
        this.jugadores.add(novoJugador); // Engadimos o xogador á lista
        this.avatares.add(novoJugador.getAvatar()); // Engadimos o avatar á lista

        System.out.println("{"); // Mostramos a confirmación da creación
        System.out.println("nome: " + novoJugador.getNombre() + ",");
        System.out.println("avatar: " + novoJugador.getAvatar().getId());
        System.out.println("}");

        System.out.println(this.tablero); // Mostramos o taboleiro actualizado
    }

    // Metodo auxiliar para ver o taboleiro
    private void verTablero() {
        System.out.println(this.tablero);
    }
}

/*System.out.println("- crear jugador <nome> <avatar>");
        System.out.println("- jugador");
        System.out.println("- listar jugadores");
        System.out.println("- lanzar dados [valor]");
        System.out.println("- acabar turno");
        System.out.println("- salir carcel");
        System.out.println("- describir <casilla>");
        System.out.println("- describir jugador <nome>");
        System.out.println("- comprar <propiedade>");
        System.out.println("- listar enventa");
        System.out.println("- ver tablero");
        System.out.println("- comandos <ficheiro>");
        System.out.println("- salir");
        System.out.println(); // Liña en branco para separar*/