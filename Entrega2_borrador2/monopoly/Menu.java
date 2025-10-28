package monopoly;

import partida.*; //impórtanse todas as clases do paquete partida
import java.util.ArrayList;
import java.util.Scanner; //Para ler a entrada do usuario
import java.io.File; // Para xestionar ficheiros
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

    public Menu(boolean modoInteractivo) { //constructor do menú se non se recibe un arquivo
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

        if (modoInteractivo) {
            this.iniciarPartida(); // Iniciamos a partida en modo interactivo
        }
    }


    // Metodo para iniciar unha partida: crea os xogadores e avatares
    private void iniciarPartida() {
        System.out.println("Benvido ao Monopoly!");

        System.out.println("\n- crear jugador <nome> <avatar>");
        System.out.println("- jugador");
        System.out.println("- listar jugadores");
        System.out.println("- lanzar dados suma");
        System.out.println("- acabar turno");
        System.out.println("- salir carcel");
        System.out.println("- describir NomeCasilla");
        System.out.println("- describir jugador Nome");
        System.out.println("- comprar NomePropiedade");
        System.out.println("- listar enventa");
        System.out.println("- ver tablero");
        System.out.println("- edificar tipoEdificacion (casa, hotel, piscina, pista_deporte)");
        System.out.println("- comandos NomeFicheiro");
        System.out.println("- salir");
        System.out.println(); // Liña en branco para separar*/

        // Scanner permite ler entrada de texto. Neste caso, do teclado (System.in)
        Scanner sc = new Scanner(System.in);

        while (true) { // Bucle infinito
            System.out.print("$> "); // Mostramos o prompt para o comando
            String comando = sc.nextLine().trim();
            /* sc.nextLine(), le unha liña completa do teclado ata premer Enter
             * trim(), elimina espazos en branco do inicio e final da liña
             */

            if (comando.equalsIgnoreCase("salir")) { //Se o comando é "salir", saímos do xogo
                System.out.println("Saíndo do xogo...");
                System.out.println("Ata pronto!");
                break; // Saímos do bucle

            } else if (comando.startsWith("comandos ")) { //Se o comando empeza por "comandos ", lense os comandos dende un ficheiro
                String nomeFicheiro = comando.substring(9);
                /* substring(9), toma a parte da cadea que vai desde a posición 9 ata o final.
                 * Isto elimina a palabra "comandos " e deixa só o nome do ficheiro
                 */
                this.lerFicheiroComandos(nomeFicheiro);

            } else { // Calquera outro comando envíase ao metodo que interpreta comandos
                this.analizarComando(comando);
            }
        }
        sc.close(); // Pechamos o scanner ao saír do xogo
    }

    /* Metodo que interpreta o comando introducido e toma a acción correspondente
     * Parámetro: cadea de caracteres (o comando)
     */
    private void analizarComando(String comando) {
        // Separa a cadea por espazos entre palabras
        String[] partes = comando.trim().split("\\s+");
        //split("\\s+"), divide a cadea en partes usando un ou varios espazos como separador

        if (partes.length == 0){
            return; // Se está baleiro, non facemos nada
        }

        switch (partes[0].toLowerCase()) { //Analiza o primeiro termo do comando en minúsculas

            case "crear": // Comando: crear jugador <nombre> <tipoAvatar>
                if (partes.length >= 4 && partes[1].equalsIgnoreCase("jugador")) {
                    this.crearJugador(partes[2], partes[3]); // Creamos o novo xogador
                }
                break;
            case "jugador": // Comando: jugador - mostra o xogador actual
                this.mostrarJugadorActual();
                break;
            case "listar": // Comando: listar xogadores, avatares ou listar enventa
                if (partes.length >= 2) {
                    if (partes[1].equalsIgnoreCase("jugadores")) {
                        this.listarJugadores();
                    } else if (partes[1].equalsIgnoreCase("enventa")) {
                        this.listarVenta(); // Lista propiedades en venda
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
                    this.descJugador(partes[2]); //Describir Jugador
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
            case "edificar":
                if (partes.length >= 2) {
                    this.edificar(partes[1], tablero);
                }
                break;
            default:
                System.out.println("Comando non recoñecido: " + comando);
        }
    }

    /* Metodo que realiza as accións asociadas ao comando 'describir jugador'
     * Parámetro: comando introducido
     */
    private void descJugador(String nombreBuscar) {

        Jugador jugador = null;

        for (Jugador j: jugadores) { // Buscamos o xogador na lista de xogadores
            if (j.getNombre().equalsIgnoreCase(nombreBuscar)) {
                jugador = j;
                break;
            }
        }

        if (jugador == null) { // Se non atopamos o xogador, mostramos erro
            System.out.println("Non existe ningún xogador con ese nome.");
            return;
        }
        System.out.println(jugador.infoJugador()); // Mostramos a información do xogador
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
        System.out.println(c.infoCasilla()); // Mostramos a información da casilla
    }

    // Metodo que executa todas as accións relacionadas co comando 'lanzar dados'
    private void lanzarDados() {
        if (this.jugadores.isEmpty()) { // Verificamos que haxa xogadores na partida
            System.out.println("Non hai xogadores na partida, non se poden lanzar os dados.");
            return;
        } else if (this.jugadores.size() < 2){
            System.out.println("Non se poden lanzar os dados ata que haxa polo menos 2 xogadores.");
            return;
        }

        Jugador actual = this.jugadores.get(this.turno); // Obtenemos o xogador actual

        if (actual.isEnCarcel()) { // Verificamos se o xogador está no cárcere
            if (!this.tirado){
                boolean mover = actual.intentarSalirCarcel(dado1, dado2, tablero);
                if (!mover){
                    System.out.println(actual.getNombre() + " está no cárcere (turno " + actual.getTiradasCarcel() + "/3");
                    return; //segue no carcere e non move
                }
                this.tirado=true;
            }else {
                System.out.println("Xa se tiraron os dados, remata o turno.");
            }
        } else {
            if (!this.tirado){ //solo se non se tirou con anterioridade
                // Facemos a tirada aleatoria dos dados
                int valor1 = this.dado1.hacerTirada();
                int valor2 = this.dado2.hacerTirada();

                gestionarTirada(actual, valor1, valor2, tablero);
            } else {
                System.out.println("Xa se tiraron os dados, remata o turno.");
            }
        }
    }

    /* Metodo que executa todas as accións realizadas co comando 'comprar nombre_casilla'
     * Parámetro: cadea de caracteres co nome da casilla
     */
    private void comprar(String nombre) {
        if (this.jugadores.isEmpty()) { // Verificamos que haxa xogadores na partida
            System.out.println("Non hai xogadores na partida, non se poden comprar casillas.");
            return;
        }

        Jugador jugadorActual = this.jugadores.get(this.turno); // Xogador que ten o turno
        Casilla casilla = this.tablero.encontrar_casilla(nombre); // Buscamos a casilla no taboleiro

        if (casilla == null) { // Verificamos que a casilla exista
            System.out.println("Propiedade non atopada: " + nombre);
            return;
        }

        // Verificamos que o xogador está na casilla que quere comprar
        if (!jugadorActual.getAvatar().getLugar().equals(casilla)) {
            //equalsIgnoreCase só funciona con String, pero casilla é un obxecto Casilla polo que se usa equals
            System.out.println("Só podes comprar a casilla na que estás actualmente: " + jugadorActual.getAvatar().getLugar().getNombre());
            return;
        }

        // Verificamos que a casilla sexa comprable
        if (!casilla.getTipo().equalsIgnoreCase("solar") && !casilla.getTipo().equalsIgnoreCase("transporte") &&
                !casilla.getTipo().equalsIgnoreCase("servicio")) {
            System.out.println("Non se pode comprar a casilla " + nombre + ".");
            return;
        }

        // Verificamos que a casilla non teña dono ou sexa da banca
        if (casilla.getDuenho() != null && !"Banca".equalsIgnoreCase(casilla.getDuenho().getNombre())) {
            System.out.println("A casilla " + nombre + " xa ten propietario.");
            return;
        }
        casilla.comprarCasilla(jugadorActual, this.banca); // Chamamos ao metodo de compra da casilla
    }

    // Metodo que executa todas as accións relacionadas co comando 'salir carcel'
    private void salirCarcel() {
        if (this.jugadores.isEmpty()){
            return; // Verificamos que haxa xogadores
        }

        Jugador jugador = this.jugadores.get(this.turno); // Xogador que ten o turno

        if (!jugador.isEnCarcel()) { // Verificamos que o xogador estea no cárcere
            System.out.println(jugador.getNombre() + " non está no cárcere.");
            return;
        }

        jugador.salirCarcel(tablero);
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
        for (Jugador j : this.jugadores) {
            System.out.println(j.infoJugador()); // Mostramos a información dos xogadores
        }
    }

    // Metodo que realiza as accións asociadas ao comando 'acabar turno'
    private void acabarTurno() {
        if (this.jugadores.isEmpty()) { // Verificamos que haxa xogadores na partida
            System.out.println("Non hai xogadores");
            return;
        }

        this.turno = (this.turno + 1) % this.jugadores.size(); // Avanzamos ao seguinte xogador
        this.tirado = false; // Marcamos que non se lanzaron dados no novo turno
        this.solvente = true; // Asumimos que o novo xogador é solvente

        Jugador siguiente = this.jugadores.get(this.turno); // Obtenemos o novo xogador actual
        System.out.println("É o turno de: " + siguiente.getNombre() + "."); // Mostramos quen é o novo xogador
        System.out.println("Estado actual: " + siguiente); //toString creado en jugador
    }

    //----------Métodos auxiliares----------

    // Metodo para ler o ficheiro cos comandos introducidos
    public void lerFicheiroComandos(String nomeFicheiro) {
        File ficheiro = new File(nomeFicheiro); // Creamos un obxecto File co nome do ficheiro

        if (!ficheiro.exists()) { // Comprobamos se o ficheiro existe
            System.out.println("O ficheiro non existe: " + nomeFicheiro);
            return;
        }

        Scanner scanner = null; //declara o scanner pero non o inicializa
        try {
            scanner = new Scanner(ficheiro); // Ábrese o ficheiro en modo lectura
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine().trim(); //lemos a seguinte liña e eniminamos espazos en branco ao inicio/fin
                if (!linea.isEmpty() && !linea.startsWith("#")) { // Ignoramos liñas baleiras e comentarios
                    System.out.println("$> " + linea); // Mostramos o comando que se vai executar
                    this.analizarComando(linea); // Procesamos o comando
                }
            }
        } catch (FileNotFoundException e) { // Mostramos erro se non se pode abrir o ficheiro
            System.out.println("Non se pode abrir o ficheiro: " + e.getMessage());
        } finally {
            if (scanner != null) {
                scanner.close(); // Aseguramos que pechamos o scanner
            }
        }
    }

    // Metodo auxiliar para procesar o movemento despois de lanzar dados
    private void procesarMovimento(Jugador actual, int total) {
        Avatar avatar = actual.getAvatar(); // Obtenemos o avatar do xogador actual
        Casilla casillaAnterior = avatar.getLugar(); // Gardamos a casilla anterior

        // Movemos o avatar polo taboleiro
        ArrayList<ArrayList<Casilla>> todasCasillas = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            todasCasillas.add(this.tablero.getLado(i));
        }
        avatar.moverAvatar(todasCasillas, total, tablero);

        Casilla casillaActual = avatar.getLugar(); // Obtenemos a nova casilla
        String nomeActual = casillaActual.getNombre();

        this.solvente = casillaActual.evaluarCasilla(actual, this.banca, total, tablero); // Avaliamos a casilla

        // Construímos o mensaxe de resultado

        System.out.println(this.tablero); // Mostramos o taboleiro actualizado

        this.tirado = true; // Marcamos que xa se lanzaron dados neste turno
    }

    //metodo para dobles
    public void gestionarTirada(Jugador actual, int valor1, int valor2, Tablero tablero) {
        int total = valor1 + valor2; // Calculamos o total da tirada

        if (valor1 == valor2) { // Comprobar dobres
            actual.incrementarDobles(); // Incrementamos dobles consecutivos

            if (actual.getDoblesConsecutivos() == 3) { // Tres dobles consecutivos implica que debe ir ao cárcere
                System.out.println(actual.getNombre() + " sacou dobles 3 veces seguidas, vai directamente ao cárcere.");
                actual.encarcelar(tablero.getPosiciones(), tablero);
                actual.resetearDobles();
                acabarTurno();
                return;
            } else {
                this.procesarMovimento(actual, total);
                System.out.println("¡Dobles! " + actual.getNombre() + " tira outra vez.");
                this.lanzamientos++;
                // Se o xogador quedou sen avatar (bancarrota), non permitir máis tiradas
                // Turno acabado
                this.tirado = actual.getAvatar() == null || !this.solvente; // Pode tirar de novo
            }
        } else { // No son dobles
            actual.resetearDobles();
            this.procesarMovimento(actual, total);
            this.lanzamientos++;
            this.tirado = true; // Turno terminado
        }
        //comprobamos que non esté en bancarrota
        if (actual.getFortuna() <= 0 || actual.isEnBancarrota()) {
            this.jugadores.remove(actual);

            // Se só queda un xogador, declaramos o gañador e rematamos
            if (this.jugadores.size() == 1) {
                Jugador ganador = this.jugadores.get(0);
                System.out.println("\nO xogo rematou!");
                System.out.println("O gañador é " + ganador.getNombre() + " cunha fortuna de " + ganador.getFortuna() + "€");
                System.exit(0);
            } else {
                System.out.println("Quedan " + this.jugadores.size() + " xogadores en xogo.");
                acabarTurno();
            }
        }
    }

    // Metodo para lanzar dados con valores forzados
    private void forzarDados(String valores) {
        if (this.jugadores.isEmpty()) { // Verificamos que haxa xogadores na partida
            System.out.println("Non hai xogadores na partida");
            return;
        } else if (this.jugadores.size() < 2){
            System.out.println("Non se poden lanzar os dados ata que haxa polo menos 2 xogadores.");
            return;
        }

        Jugador actual = this.jugadores.get(turno); // Obtenemos o xogador actual

        if (actual.isEnCarcel()) { // Verificamos se o xogador está no cárcere
            boolean mover = actual.intentarSalirCarcel(dado1, dado2, tablero);
            if (!mover){
                System.out.println(actual.getNombre() + " está no cárcere (turno " + actual.getTiradasCarcel() + "/3)");
                return; //segue no carcere e non move
            }
            System.out.println(actual.getNombre() + " sae do cárcere!");
        } else {
            if (!tirado){
                int[] valoresDados = Dado.procesarTiradaForzada(valores); // Procesamos o formato "2+4"
                if (valoresDados == null) {
                    System.out.println("Formato de tirada forzada inválido. Use 'numero + numero'");
                    return;
                }
                gestionarTirada(actual, valoresDados[0], valoresDados[1], tablero);

            }else{
                System.out.println("Os dados xa foron tirados, remate o turno.");
            }
        }
    }

    // Metodo para mostrar o xogador actual
    private void mostrarJugadorActual() {
        if (this.jugadores.isEmpty()) { // Verificamos que haxa xogadores na partida
            System.out.println("Non hai xogadores na partida");
            return;
        }

        Jugador actual = this.jugadores.get(this.turno); // Obtenemos o xogador actual
        System.out.println("É o turno de: " + actual.getNombre() + "."); // Mostramos quen é o novo xogador
        System.out.println("Estado actual: " + actual); //toString creado en jugador
    }

    // Metodo para crear un xogador
    private void crearJugador(String nombre, String tipoAvatar) {
        //Verificamos se xa existen demasiados xogadores
        if (this.jugadores.size() >= 4) {
            System.out.println("Xa hai 4 xogadores, non poden engadrse mais.");
            return;
        }

        for (Jugador j : this.jugadores) { // Verificamos se xa existe un xogador con ese nome
            if (j.getNombre().equalsIgnoreCase(nombre)) {
                System.out.println("Xa existe un xogador con ese nome");
                return;
            }
        }

        //Validamos que o tipo de avatar sexa correcto
        String[] tiposValidos = {"Coche", "Esfinge", "Sombrero", "Pelota"};
        boolean tipoValido = false; //poñemos unha bandeira
        for (String tipo : tiposValidos) {
            if (tipo.equalsIgnoreCase(tipoAvatar)) {
                tipoValido = true;
                break;
            }
        }
        if (!tipoValido) {
            System.out.println("Tipo de avatar non válido. Usa: Coche, Esfinge, Sombrero ou Pelota.");
            return;
        }

        //Comprobamos que o tipo de avatar non estea xa collido
        for (Avatar av : this.avatares) {
            if (av.getTipo().equalsIgnoreCase(tipoAvatar)) {
                System.out.println("O tipo de avatar '" + tipoAvatar + "' xa está en uso por outro xogador.");
                return;
            }
        }

        Casilla salida = this.tablero.encontrar_casilla("Salida"); // Buscamos a casilla de salida
        if (salida == null) {
            System.out.println("Non se atopou a casilla Salida");
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

    // En la clase del menú:
    public void edificar(String tipoEdificio, Tablero tablero) {
        Jugador jugadorActual = jugadores.get(turno);
        jugadorActual.edificar(tipoEdificio);
    }


    public Tablero getTablero() {
        return this.tablero;
    }



}