package monopoly;

import partida.Avatar;
import partida.Jugador;
import monopoly.MonopolyETSE;


public class CartasSuerte {

    //indice da carta actual
    private int indice = 1;

    public void sacarCarta(Jugador jugador, Menu menu){
        Tablero tablero = menu.getTablero();

        System.out.println("Saca unha carta de Sorte!");
        System.out.println(jugador.getNombre() + ", tocoulle a carta: " + indice + ".");

        //imos facer un swicht que aplique cada carte en funcion do indice
        switch (indice){
            case 1:
                System.out.println("Acción: Decides facer unha viaxe de pracer. Avanza ata Solar19. Se pasas pola Saída, cobra 2.000.000€.");
                jugador.getAvatar().moverAvatarHasta("Solar19", tablero.getCasillas(), tablero);
            break;
            case 2:
                System.out.println("Acción: Os acredores persiguenche por impago. Vai direcamente ao cárcere, sen pasar pola saída e sen cobrar os 2.000.000€.");
                jugador.encarcelar(menu.getTablero().getPosiciones(), menu.getTablero());
                break;
            case 3:
                System.out.println("Acción: Ganas o bote da lotería. Recibe 1.000.000€.");
                jugador.sumarFortuna(1000000);
                break;
            case 4:
                //esperar a que hipotecar este feito
                break;
            case 5:
                System.out.println("Hora punta de tráfico! Retrocede 3 casillas.");
                //aqui me quedeeeEeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee


    }
}
