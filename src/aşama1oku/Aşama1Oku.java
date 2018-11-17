package aşama1oku;

import java.io.File;

import yardımcı.Fonksiyonlar;

public class Aşama1Oku {

	public static String oku(String dosyaAdı) {
		File dosya = new File("kodlar/" + dosyaAdı + ".kod");
		String içerik = Fonksiyonlar.dosyaOku(dosya);
		return içerik;
	}

}
