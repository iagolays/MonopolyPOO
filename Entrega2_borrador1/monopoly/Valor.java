package monopoly;

public class Valor {
    //Se incluyen una serie de constantes útiles para no repetir valores.
    //public static final float FORTUNA_BANCA = 500000f; // Cantidade que ten inicialmente a Banca, deberia ser ilimitado por eso está comentado
    public static final float FORTUNA_INICIAL = 15000000f; // Cantidade que recibe cada xogador ao comezar a partida
    public static final float SUMA_VUELTA = 2000000f; // Cantidade que recibe un xogador ao pasar pola Salida
    
    //Cores do texto:
    public static final String RESET = "\u001B[0m"; //establécese a cor por defecto
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";


    //----------Metodos auxiliares

    // --- Precios solares (compra e hipoteca) ---

    // Solar 1 e 2
    public static final float SOLAR1_PRECIO = 600000f;
    public static final float SOLAR1_HIPOTECA = 300000f;
    public static final float SOLAR1_PRECIO_CASA = 500000f;
    public static final float SOLAR1_PRECIO_HOTEL = 500000f;
    public static final float SOLAR1_PRECIO_PISCINA = 100000f;
    public static final float SOLAR1_PRECIO_PISTA = 200000f;

    public static final float SOLAR2_PRECIO = 600000f;
    public static final float SOLAR2_HIPOTECA = 300000f;
    public static final float SOLAR2_PRECIO_CASA = 500000f;
    public static final float SOLAR2_PRECIO_HOTEL = 500000f;
    public static final float SOLAR2_PRECIO_PISCINA = 100000f;
    public static final float SOLAR2_PRECIO_PISTA = 200000f;

    // Solar 3, 4 e 5
    public static final float SOLAR3_PRECIO = 1000000f;
    public static final float SOLAR3_HIPOTECA = 500000f;
    public static final float SOLAR3_PRECIO_CASA = 500000f;
    public static final float SOLAR3_PRECIO_HOTEL = 500000f;
    public static final float SOLAR3_PRECIO_PISCINA = 100000f;
    public static final float SOLAR3_PRECIO_PISTA = 200000f;

    public static final float SOLAR4_PRECIO = 1000000f;
    public static final float SOLAR4_HIPOTECA = 500000f;
    public static final float SOLAR4_PRECIO_CASA = 500000f;
    public static final float SOLAR4_PRECIO_HOTEL = 500000f;
    public static final float SOLAR4_PRECIO_PISCINA = 100000f;
    public static final float SOLAR4_PRECIO_PISTA = 200000f;

    public static final float SOLAR5_PRECIO = 1200000f;
    public static final float SOLAR5_HIPOTECA = 600000f;
    public static final float SOLAR5_PRECIO_CASA = 500000f;
    public static final float SOLAR5_PRECIO_HOTEL = 500000f;
    public static final float SOLAR5_PRECIO_PISCINA = 100000f;
    public static final float SOLAR5_PRECIO_PISTA = 200000f;

    // Solar 6, 7 e 8 (Grupo Rosa)
    public static final float SOLAR6_PRECIO = 1400000f;
    public static final float SOLAR6_HIPOTECA = 700000f;
    public static final float SOLAR6_PRECIO_CASA = 1000000f;
    public static final float SOLAR6_PRECIO_HOTEL = 1000000f;
    public static final float SOLAR6_PRECIO_PISCINA = 200000f;
    public static final float SOLAR6_PRECIO_PISTA = 400000f;

    public static final float SOLAR7_PRECIO = 1400000f;
    public static final float SOLAR7_HIPOTECA = 700000f;
    public static final float SOLAR7_PRECIO_CASA = 1000000f;
    public static final float SOLAR7_PRECIO_HOTEL = 1000000f;
    public static final float SOLAR7_PRECIO_PISCINA = 200000f;
    public static final float SOLAR7_PRECIO_PISTA = 400000f;

    public static final float SOLAR8_PRECIO = 1600000f;
    public static final float SOLAR8_HIPOTECA = 800000f;
    public static final float SOLAR8_PRECIO_CASA = 1000000f;
    public static final float SOLAR8_PRECIO_HOTEL = 1000000f;
    public static final float SOLAR8_PRECIO_PISCINA = 200000f;
    public static final float SOLAR8_PRECIO_PISTA = 400000f;

    // Solar 9, 10 e 11
    public static final float SOLAR9_PRECIO = 1800000f;
    public static final float SOLAR9_HIPOTECA = 900000f;
    public static final float SOLAR9_PRECIO_CASA = 1000000f;
    public static final float SOLAR9_PRECIO_HOTEL = 1000000f;
    public static final float SOLAR9_PRECIO_PISCINA = 200000f;
    public static final float SOLAR9_PRECIO_PISTA = 400000f;

    public static final float SOLAR10_PRECIO = 1800000f;
    public static final float SOLAR10_HIPOTECA = 900000f;
    public static final float SOLAR10_PRECIO_CASA = 1000000f;
    public static final float SOLAR10_PRECIO_HOTEL = 1000000f;
    public static final float SOLAR10_PRECIO_PISCINA = 200000f;
    public static final float SOLAR10_PRECIO_PISTA = 400000f;

    public static final float SOLAR11_PRECIO = 2200000f;
    public static final float SOLAR11_HIPOTECA = 1000000f;
    public static final float SOLAR11_PRECIO_CASA = 1000000f;
    public static final float SOLAR11_PRECIO_HOTEL = 1000000f;
    public static final float SOLAR11_PRECIO_PISCINA = 200000f;
    public static final float SOLAR11_PRECIO_PISTA = 400000f;

    // Solar 12, 13 e 14
    public static final float SOLAR12_PRECIO = 2200000f;
    public static final float SOLAR12_HIPOTECA = 1100000f;
    public static final float SOLAR12_PRECIO_CASA = 1500000f;
    public static final float SOLAR12_PRECIO_HOTEL = 1500000f;
    public static final float SOLAR12_PRECIO_PISCINA = 300000f;
    public static final float SOLAR12_PRECIO_PISTA = 600000f;

    public static final float SOLAR13_PRECIO = 2200000f;
    public static final float SOLAR13_HIPOTECA = 1100000f;
    public static final float SOLAR13_PRECIO_CASA = 1500000f;
    public static final float SOLAR13_PRECIO_HOTEL = 1500000f;
    public static final float SOLAR13_PRECIO_PISCINA = 300000f;
    public static final float SOLAR13_PRECIO_PISTA = 600000f;

    public static final float SOLAR14_PRECIO = 2400000f;
    public static final float SOLAR14_HIPOTECA = 1200000f;
    public static final float SOLAR14_PRECIO_CASA = 1500000f;
    public static final float SOLAR14_PRECIO_HOTEL = 1500000f;
    public static final float SOLAR14_PRECIO_PISCINA = 300000f;
    public static final float SOLAR14_PRECIO_PISTA = 600000f;

    // Solar 15, 16 e 17
    public static final float SOLAR15_PRECIO = 2600000f;
    public static final float SOLAR15_HIPOTECA = 1300000f;
    public static final float SOLAR15_PRECIO_CASA = 1500000f;
    public static final float SOLAR15_PRECIO_HOTEL = 1500000f;
    public static final float SOLAR15_PRECIO_PISCINA = 300000f;
    public static final float SOLAR15_PRECIO_PISTA = 600000f;

    public static final float SOLAR16_PRECIO = 2600000f;
    public static final float SOLAR16_HIPOTECA = 1300000f;
    public static final float SOLAR16_PRECIO_CASA = 1500000f;
    public static final float SOLAR16_PRECIO_HOTEL = 1500000f;
    public static final float SOLAR16_PRECIO_PISCINA = 300000f;
    public static final float SOLAR16_PRECIO_PISTA = 600000f;

    public static final float SOLAR17_PRECIO = 2800000f;
    public static final float SOLAR17_HIPOTECA = 1400000f;
    public static final float SOLAR17_PRECIO_CASA = 1500000f;
    public static final float SOLAR17_PRECIO_HOTEL = 1500000f;
    public static final float SOLAR17_PRECIO_PISCINA = 300000f;
    public static final float SOLAR17_PRECIO_PISTA = 600000f;

    // Solar 18, 19 e 20
    public static final float SOLAR18_PRECIO = 3000000f;
    public static final float SOLAR18_HIPOTECA = 1500000f;
    public static final float SOLAR18_PRECIO_CASA = 2000000f;
    public static final float SOLAR18_PRECIO_HOTEL = 2000000f;
    public static final float SOLAR18_PRECIO_PISCINA = 400000f;
    public static final float SOLAR18_PRECIO_PISTA = 800000f;

    public static final float SOLAR19_PRECIO = 3000000f;
    public static final float SOLAR19_HIPOTECA = 1500000f;
    public static final float SOLAR19_PRECIO_CASA = 2000000f;
    public static final float SOLAR19_PRECIO_HOTEL = 2000000f;
    public static final float SOLAR19_PRECIO_PISCINA = 400000f;
    public static final float SOLAR19_PRECIO_PISTA = 800000f;

    public static final float SOLAR20_PRECIO = 3200000f;
    public static final float SOLAR20_HIPOTECA = 1600000f;
    public static final float SOLAR20_PRECIO_CASA = 2000000f;
    public static final float SOLAR20_PRECIO_HOTEL = 2000000f;
    public static final float SOLAR20_PRECIO_PISCINA = 400000f;
    public static final float SOLAR20_PRECIO_PISTA = 800000f;

    // Solar 21 e 22
    public static final float SOLAR21_PRECIO = 3500000f;
    public static final float SOLAR21_HIPOTECA = 1750000f;
    public static final float SOLAR21_PRECIO_CASA = 2000000f;
    public static final float SOLAR21_PRECIO_HOTEL = 2000000f;
    public static final float SOLAR21_PRECIO_PISCINA = 400000f;
    public static final float SOLAR21_PRECIO_PISTA = 800000f;

    public static final float SOLAR22_PRECIO = 4000000f;
    public static final float SOLAR22_HIPOTECA = 2000000f;
    public static final float SOLAR22_PRECIO_CASA = 2000000f;
    public static final float SOLAR22_PRECIO_HOTEL = 2000000f;
    public static final float SOLAR22_PRECIO_PISCINA = 400000f;
    public static final float SOLAR22_PRECIO_PISTA = 800000f;


    // --- Alugueres solares ---

    // Solar 1 e 2
    public static final float SOLAR1_ALQUILER = 20000f;
    public static final float SOLAR1_ALQUILER_CASA = 400000f;
    public static final float SOLAR1_ALQUILER_HOTEL = 2500000f;
    public static final float SOLAR1_ALQUILER_PISCINA = 500000f;
    public static final float SOLAR1_ALQUILER_PISTA = 500000f;

    public static final float SOLAR2_ALQUILER = 40000f;
    public static final float SOLAR2_ALQUILER_CASA = 800000f;
    public static final float SOLAR2_ALQUILER_HOTEL = 4500000f;
    public static final float SOLAR2_ALQUILER_PISCINA = 900000f;
    public static final float SOLAR2_ALQUILER_PISTA = 900000f;

    // Solar 3, 4 e 5
    public static final float SOLAR3_ALQUILER = 60000f;
    public static final float SOLAR3_ALQUILER_CASA = 1000000f;
    public static final float SOLAR3_ALQUILER_HOTEL = 5500000f;
    public static final float SOLAR3_ALQUILER_PISCINA = 1100000f;
    public static final float SOLAR3_ALQUILER_PISTA = 1100000f;

    public static final float SOLAR4_ALQUILER = 60000f;
    public static final float SOLAR4_ALQUILER_CASA = 1000000f;
    public static final float SOLAR4_ALQUILER_HOTEL = 5500000f;
    public static final float SOLAR4_ALQUILER_PISCINA = 1100000f;
    public static final float SOLAR4_ALQUILER_PISTA = 1100000f;

    public static final float SOLAR5_ALQUILER = 80000f;
    public static final float SOLAR5_ALQUILER_CASA = 1250000f;
    public static final float SOLAR5_ALQUILER_HOTEL = 6000000f;
    public static final float SOLAR5_ALQUILER_PISCINA = 1200000f;
    public static final float SOLAR5_ALQUILER_PISTA = 1200000f;

    // Solar 6, 7 e 8
    public static final float SOLAR6_ALQUILER = 100000f;
    public static final float SOLAR6_ALQUILER_CASA = 1500000f;
    public static final float SOLAR6_ALQUILER_HOTEL = 7500000f;
    public static final float SOLAR6_ALQUILER_PISCINA = 1500000f;
    public static final float SOLAR6_ALQUILER_PISTA = 1500000f;

    public static final float SOLAR7_ALQUILER = 100000f;
    public static final float SOLAR7_ALQUILER_CASA = 1500000f;
    public static final float SOLAR7_ALQUILER_HOTEL = 7500000f;
    public static final float SOLAR7_ALQUILER_PISCINA = 1500000f;
    public static final float SOLAR7_ALQUILER_PISTA = 1500000f;

    public static final float SOLAR8_ALQUILER = 120000f;
    public static final float SOLAR8_ALQUILER_CASA = 1750000f;
    public static final float SOLAR8_ALQUILER_HOTEL = 9000000f;
    public static final float SOLAR8_ALQUILER_PISCINA = 1800000f;
    public static final float SOLAR8_ALQUILER_PISTA = 1800000f;

    // Solar 9, 10 e 11
    public static final float SOLAR9_ALQUILER = 140000f;
    public static final float SOLAR9_ALQUILER_CASA = 1850000f;
    public static final float SOLAR9_ALQUILER_HOTEL = 9500000f;
    public static final float SOLAR9_ALQUILER_PISCINA = 1900000f;
    public static final float SOLAR9_ALQUILER_PISTA = 1900000f;

    public static final float SOLAR10_ALQUILER = 140000f;
    public static final float SOLAR10_ALQUILER_CASA = 1850000f;
    public static final float SOLAR10_ALQUILER_HOTEL = 9500000f;
    public static final float SOLAR10_ALQUILER_PISCINA = 1900000f;
    public static final float SOLAR10_ALQUILER_PISTA = 1900000f;

    public static final float SOLAR11_ALQUILER = 160000f;
    public static final float SOLAR11_ALQUILER_CASA = 2000000f;
    public static final float SOLAR11_ALQUILER_HOTEL = 10000000f;
    public static final float SOLAR11_ALQUILER_PISCINA = 2000000f;
    public static final float SOLAR11_ALQUILER_PISTA = 2000000f;

    // Solar 12, 13 e 14
    public static final float SOLAR12_ALQUILER = 180000f;
    public static final float SOLAR12_ALQUILER_CASA = 2200000f;
    public static final float SOLAR12_ALQUILER_HOTEL = 10500000f;
    public static final float SOLAR12_ALQUILER_PISCINA = 2100000f;
    public static final float SOLAR12_ALQUILER_PISTA = 2100000f;

    public static final float SOLAR13_ALQUILER = 180000f;
    public static final float SOLAR13_ALQUILER_CASA = 2200000f;
    public static final float SOLAR13_ALQUILER_HOTEL = 10500000f;
    public static final float SOLAR13_ALQUILER_PISCINA = 2100000f;
    public static final float SOLAR13_ALQUILER_PISTA = 2100000f;

    public static final float SOLAR14_ALQUILER = 200000f;
    public static final float SOLAR14_ALQUILER_CASA = 2325000f;
    public static final float SOLAR14_ALQUILER_HOTEL = 11000000f;
    public static final float SOLAR14_ALQUILER_PISCINA = 2200000f;
    public static final float SOLAR14_ALQUILER_PISTA = 2200000f;

    // Solar 15, 16 e 17
    public static final float SOLAR15_ALQUILER = 220000f;
    public static final float SOLAR15_ALQUILER_CASA = 2450000f;
    public static final float SOLAR15_ALQUILER_HOTEL = 11500000f;
    public static final float SOLAR15_ALQUILER_PISCINA = 2300000f;
    public static final float SOLAR15_ALQUILER_PISTA = 2300000f;

    public static final float SOLAR16_ALQUILER = 220000f;
    public static final float SOLAR16_ALQUILER_CASA = 2450000f;
    public static final float SOLAR16_ALQUILER_HOTEL = 11500000f;
    public static final float SOLAR16_ALQUILER_PISCINA = 2300000f;
    public static final float SOLAR16_ALQUILER_PISTA = 2300000f;

    public static final float SOLAR17_ALQUILER = 240000f;
    public static final float SOLAR17_ALQUILER_CASA = 2600000f;
    public static final float SOLAR17_ALQUILER_HOTEL = 12000000f;
    public static final float SOLAR17_ALQUILER_PISCINA = 2400000f;
    public static final float SOLAR17_ALQUILER_PISTA = 2400000f;

    // Solar 18, 19 e 20
    public static final float SOLAR18_ALQUILER = 260000f;
    public static final float SOLAR18_ALQUILER_CASA = 2750000f;
    public static final float SOLAR18_ALQUILER_HOTEL = 12750000f;
    public static final float SOLAR18_ALQUILER_PISCINA = 2550000f;
    public static final float SOLAR18_ALQUILER_PISTA = 2550000f;

    public static final float SOLAR19_ALQUILER = 260000f;
    public static final float SOLAR19_ALQUILER_CASA = 2750000f;
    public static final float SOLAR19_ALQUILER_HOTEL = 12750000f;
    public static final float SOLAR19_ALQUILER_PISCINA = 2550000f;
    public static final float SOLAR19_ALQUILER_PISTA = 2550000f;

    public static final float SOLAR20_ALQUILER = 280000f;
    public static final float SOLAR20_ALQUILER_CASA = 3000000f;
    public static final float SOLAR20_ALQUILER_HOTEL = 14000000f;
    public static final float SOLAR20_ALQUILER_PISCINA = 2800000f;
    public static final float SOLAR20_ALQUILER_PISTA = 2800000f;

    // Solar 21 e 22
    public static final float SOLAR21_ALQUILER = 350000f;
    public static final float SOLAR21_ALQUILER_CASA = 3250000f;
    public static final float SOLAR21_ALQUILER_HOTEL = 17000000f;
    public static final float SOLAR21_ALQUILER_PISCINA = 3400000f;
    public static final float SOLAR21_ALQUILER_PISTA = 3400000f;

    public static final float SOLAR22_ALQUILER = 500000f;
    public static final float SOLAR22_ALQUILER_CASA = 4250000f;
    public static final float SOLAR22_ALQUILER_HOTEL = 20000000f;
    public static final float SOLAR22_ALQUILER_PISCINA = 4000000f;
    public static final float SOLAR22_ALQUILER_PISTA = 4000000f;


    // --- Transportes y servicios ---
    public static final float TRANSPORTE_PRECIO = 500000f; //Prezo de compra inicial para casillas de transporte
    public static final float TRANSPORTE_ALQUILER = 250000f; //Aluguer base para casillas de transporte
    public static final float SERVICIO_PRECIO = 500000f; //Prezo de compra inicial para casillas de servicio
    public static final float FACTOR_SERVICIO = 50000f; //Factor de servizo para casillas de servizo

    // --- Impuestos y cárcel ---
    public static final float IMPUESTO_VALOR = 2000000f; //Prezo a pagar en casillas de impostos
    public static final float CARCEL_SALIDA = 500000f; //Prezo para sair da cárcere
}
