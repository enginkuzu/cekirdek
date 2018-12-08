package başlangıç;

import java.util.ArrayList;

import aşama1oku.Aşama1Oku;
import aşama2sözcükler.Aşama2Sözcükler;
import aşama2sözcükler.Sözcük;
import aşama3cümleler.Aşama3Cümleler;

public class Başlangıç {

	public Başlangıç() {
		derle("wiki04_01");
	}

	public void derle(String dosyaAdı) {

		String içerik = Aşama1Oku.oku(dosyaAdı);
		if (içerik == null) {
			System.err.println("Aşama1Oku : " + dosyaAdı + ".kod okunamadı !!!");
			return;
		}

		try {
			ArrayList<Sözcük[]> cümlecikler = Aşama2Sözcükler.işle(içerik, dosyaAdı);
			Aşama3Cümleler.işle(cümlecikler, dosyaAdı);
		} catch (Exception ex) {
			ex.printStackTrace();
			return;
		}

	}

	public static void main(String[] args) {
		new Başlangıç();
	}

}
