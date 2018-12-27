package aşama1oku;

import java.io.File;

import yardımcı.Fonksiyonlar;

public class Aşama1Oku {

	public static String oku(String dosyaYoluVeAdı) {
		File dosya = new File(dosyaYoluVeAdı);
		String içerik = Fonksiyonlar.dosyaOku(dosya);
		return içerik;
	}

}
