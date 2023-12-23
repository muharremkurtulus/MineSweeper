import java.util.Random;
import java.util.Scanner;

public class MineSweeper {

    private int satirSayisi;
    private int sutunSayisi;
    private int mayinSayisi;
    private char[][] oyunTahtasi;
    private boolean[][] mayinKonumlari;
    private boolean devamEdiyor;

    public MineSweeper(int satirSayisi, int sutunSayisi) {
        this.satirSayisi = satirSayisi;
        this.sutunSayisi = sutunSayisi;
        this.mayinSayisi = satirSayisi * sutunSayisi / 4;
        this.oyunTahtasi = new char[satirSayisi][sutunSayisi];
        this.mayinKonumlari = new boolean[satirSayisi][sutunSayisi];
        this.devamEdiyor = true;
    }

    public void mayinlariYerlestir() {
        Random random = new Random();

        for (int i = 0; i < mayinSayisi; i++) {
            int randomSatir = random.nextInt(satirSayisi);
            int randomSutun = random.nextInt(sutunSayisi);

            // Aynı konuma birden fazla mayın yerleşmemesi için kontrol
            while (mayinKonumlari[randomSatir][randomSutun]) {
                randomSatir = random.nextInt(satirSayisi);
                randomSutun = random.nextInt(sutunSayisi);
            }

            mayinKonumlari[randomSatir][randomSutun] = true;
        }
    }

    public void tahtayiGoster() {
        for (int i = 0; i < satirSayisi; i++) {
            for (int j = 0; j < sutunSayisi; j++) {
                System.out.print(oyunTahtasi[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void oyunuBaslat() {
        Scanner scanner = new Scanner(System.in);

        mayinlariYerlestir();

        while (devamEdiyor) {
            tahtayiGoster();

            System.out.print("Satır seçin: ");
            int satir = scanner.nextInt();

            System.out.print("Sütun seçin: ");
            int sutun = scanner.nextInt();

            if (!isValidMove(satir, sutun)) {
                System.out.println("Geçersiz bir konum girdiniz. Lütfen tekrar deneyin.");
                continue;
            }

            if (mayinKonumlari[satir][sutun]) {
                oyunuKaybet();
            } else {
                oyunuDevamEttir(satir, sutun);
                if (kazanildiMi()) {
                    tahtayiGoster();
                    System.out.println("Tebrikler! Oyunu kazandınız.");
                    devamEdiyor = false;
                }
            }
        }
    }

    private boolean isValidMove(int satir, int sutun) {
        return satir >= 0 && satir < satirSayisi && sutun >= 0 && sutun < sutunSayisi;
    }

    private void oyunuKaybet() {
        tahtayiGoster();
        System.out.println("Oyunu kaybettiniz. Mayına bastınız!");
        devamEdiyor = false;
    }

    private void oyunuDevamEttir(int satir, int sutun) {
        if (mayinKonumuKontrolEt(satir - 1, sutun - 1)) oyunTahtasi[satir - 1][sutun - 1]++;
        if (mayinKonumuKontrolEt(satir - 1, sutun)) oyunTahtasi[satir - 1][sutun]++;
        if (mayinKonumuKontrolEt(satir - 1, sutun + 1)) oyunTahtasi[satir - 1][sutun + 1]++;
        if (mayinKonumuKontrolEt(satir, sutun - 1)) oyunTahtasi[satir][sutun - 1]++;
        if (mayinKonumuKontrolEt(satir, sutun + 1)) oyunTahtasi[satir][sutun + 1]++;
        if (mayinKonumuKontrolEt(satir + 1, sutun - 1)) oyunTahtasi[satir + 1][sutun - 1]++;
        if (mayinKonumuKontrolEt(satir + 1, sutun)) oyunTahtasi[satir + 1][sutun]++;
        if (mayinKonumuKontrolEt(satir + 1, sutun + 1)) oyunTahtasi[satir + 1][sutun + 1]++;
    }

    private boolean mayinKonumuKontrolEt(int satir, int sutun) {
        return isValidMove(satir, sutun) && mayinKonumlari[satir][sutun];
    }

    private boolean kazanildiMi() {
        for (int i = 0; i < satirSayisi; i++) {
            for (int j = 0; j < sutunSayisi; j++) {
                if (!mayinKonumlari[i][j] && oyunTahtasi[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Satır sayısını girin: ");
        int satirSayisi = scanner.nextInt();

        System.out.print("Sütun sayısını girin: ");
        int sutunSayisi = scanner.nextInt();

        MineSweeper mineSweeper = new MineSweeper(satirSayisi, sutunSayisi);
        mineSweeper.oyunuBaslat();
    }
}
