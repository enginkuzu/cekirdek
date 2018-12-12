package aşama6çıktı;

import java.util.ArrayList;

import sınıflar.KomutÇıktısı;
import yardımcı.Fonksiyonlar;

public class Aşama6Çıktı {

	public static void hatalarıYazdır(ArrayList<String> hatalar) {
		for (String hata : hatalar) {
			System.out.println(hata);
		}
	}

	public static boolean işle(String komutlar) {

		Fonksiyonlar.dosyaKaydet("kodlar/main.s", komutlar);

		KomutÇıktısı kç1 = Fonksiyonlar.komutÇalıştır(false,
				new String[] { "as", "kodlar/main.s", "-o", "kodlar/main.o" });
		if (!kç1.hata.isEmpty()) {
			hatalarıYazdır(kç1.hata);
			return false;
		}

		KomutÇıktısı kç2 = Fonksiyonlar.komutÇalıştır(false,
				new String[] { "as", "kütüphane/exit.s", "-o", "kodlar/exit.o" });
		if (!kç2.hata.isEmpty()) {
			hatalarıYazdır(kç2.hata);
			return false;
		}

		KomutÇıktısı kç3 = Fonksiyonlar.komutÇalıştır(false,
				new String[] { "as", "kütüphane/println.s", "-o", "kodlar/println.o" });
		if (!kç3.hata.isEmpty()) {
			hatalarıYazdır(kç3.hata);
			return false;
		}

		KomutÇıktısı kç4 = Fonksiyonlar.komutÇalıştır(false,
				new String[] { "ld", "-s", "kodlar/main.o", "kodlar/println.o", "kodlar/exit.o", "-o", "kodlar/main" });
		if (!kç4.hata.isEmpty()) {
			hatalarıYazdır(kç4.hata);
			return false;
		}

		return true;
	}

}
