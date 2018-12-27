package başlangıç;

import java.util.ArrayList;

import aşama1oku.Aşama1Oku;
import aşama2sözcükler.Aşama2Sözcükler;
import aşama2sözcükler.Sözcük;
import aşama3cümleler.Aşama3Cümleler;
import aşama3cümleler.Aşama3Çıktısı;
import aşama4iyileştirmeler.Aşama4İyileştirmeler;
import aşama5makinedili.Aşama5MakineDili;
import aşama6çıktı.Aşama6Çıktı;

public class Başlangıç {

	public Başlangıç() {
		try {
			derle("kodlar/wiki04_01.kod");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void derle(String kaynakKodDosyası) throws Exception {

		String kütüphaneDosyası1 = "kütüphane/exit.kod";
		String kütüphane1 = Aşama1Oku.oku(kütüphaneDosyası1);
		if (kütüphane1 == null) {
			throw new Exception("Aşama1Oku : " + kütüphaneDosyası1 + " okunamadı !!!");
		}

		String kütüphaneDosyası2 = "kütüphane/i64.kod";
		String kütüphane2 = Aşama1Oku.oku(kütüphaneDosyası2);
		if (kütüphane2 == null) {
			throw new Exception("Aşama1Oku : " + kütüphaneDosyası2 + " okunamadı !!!");
		}

		String kütüphaneDosyası3 = "kütüphane/println.kod";
		String kütüphane3 = Aşama1Oku.oku(kütüphaneDosyası3);
		if (kütüphane3 == null) {
			throw new Exception("Aşama1Oku : " + kütüphaneDosyası3 + " okunamadı !!!");
		}

		String kaynakKod = Aşama1Oku.oku(kaynakKodDosyası);
		if (kaynakKod == null) {
			throw new Exception("Aşama1Oku : " + kaynakKodDosyası + " okunamadı !!!");
		}

		ArrayList<Sözcük[]> sözcükler = Aşama2Sözcükler
				.işle(kütüphane1 + "\n" + kütüphane2 + "\n" + kütüphane3 + "\n" + kaynakKod, kaynakKodDosyası);
		Aşama3Çıktısı cümleler = Aşama3Cümleler.işle(sözcükler, kaynakKodDosyası);
		Aşama3Çıktısı iyileştirmeler = Aşama4İyileştirmeler.işle(cümleler, kaynakKodDosyası);
		Aşama5MakineDili.işle(iyileştirmeler, kaynakKodDosyası);
		Aşama6Çıktı.işle(kaynakKodDosyası);
	}

	public static void main(String[] args) {
		new Başlangıç();
	}

}
