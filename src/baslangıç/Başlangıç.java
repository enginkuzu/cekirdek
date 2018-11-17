package baslangıç;

import java.util.ArrayList;

import aşama1oku.Aşama1Oku;
import aşama2sözcükler.Aşama2Sözcükler;
import aşama2sözcükler.Sözcük;
import yardımcı.Fonksiyonlar;
import yardımcı.Değişkenler.SÖZCÜK;

public class Başlangıç {

	public Başlangıç() {
		derle("wiki04_01");
	}

	public void kaydet2sözcükler(String dosyaAdı, ArrayList<Sözcük> tümCümleler) {

		StringBuilder stringBuilder = new StringBuilder();
		for (Sözcük kelime : tümCümleler) {
			stringBuilder.append(kelime.toString());
			stringBuilder.append(" ");
			if (kelime.tip == SÖZCÜK.TİP_06SATIR_SONU) {
				stringBuilder.append("\n");
			}
		}
		String çıktı = stringBuilder.toString();

		System.out.print(çıktı);
		Fonksiyonlar.dosyaKaydet("kodlar/" + dosyaAdı + ".a2.txt", çıktı);
	}

	public void derle(String dosyaAdı) {

		String içerik = Aşama1Oku.oku(dosyaAdı);
		if (içerik == null) {
			System.err.println("Aşama1Oku : " + dosyaAdı + ".kod okunamadı !!!");
			return;
		}

		try {
			ArrayList<Sözcük> cümlecikler = Aşama2Sözcükler.işle(içerik);
			kaydet2sözcükler(dosyaAdı, cümlecikler);
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
			return;
		}

	}

	public static void main(String[] args) {
		new Başlangıç();
	}

}
