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

	public static boolean işle(String dosyaAdı) {

		KomutÇıktısı kç1 = Fonksiyonlar.komutÇalıştır(false,
				new String[] { "as", dosyaAdı + ".5.s", "-o", "kodlar/main.o" });
		if (!kç1.hata.isEmpty()) {
			hatalarıYazdır(kç1.hata);
			return false;
		}

		KomutÇıktısı kç4 = Fonksiyonlar.komutÇalıştır(false,
				new String[] { "ld", "-s", "kodlar/main.o", "-o", "kodlar/main" });
		if (!kç4.hata.isEmpty()) {
			hatalarıYazdır(kç4.hata);
			return false;
		}

		return true;
	}

}
