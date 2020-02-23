package aşama3cümleler;

import java.util.HashMap;
import java.util.HashSet;

import yardımcı.Değişkenler;
import yardımcı.Fonksiyonlar;
import yardımcı.Değişkenler.FONKSİYON;

public class Fonksiyon_01OperatörFonksiyon extends Fonksiyon {

	public HashMap<String, HashSet<String>> özellikMap;
	public boolean inline;
	public int öncelik;
	public String değişken1İsim;
	public int değişken1TipId;
	public String operatör;
	public String değişken2İsim;
	public int değişken2TipId;
	public String sonuçİsim;
	public String sonuçTip;

	public Fonksiyon_01OperatörFonksiyon(HashMap<String, HashSet<String>> özellikMap) {
		super(FONKSİYON.TİP_01OPERATÖRFONKSİYON);
		this.özellikMap = özellikMap;
		//
		if (özellikMap.containsKey("functionid")) {
			this.fonksiyonId = Fonksiyonlar.parseInt(özellikMap.get("functionid").iterator().next());
		} else {
			this.fonksiyonId = yeniFonksiyonIdGetir();
		}
	}

	@Override
	public String toString() {
		return "Fonksiyon_01OperatörFonksiyon[" + Değişkenler.TİP_MAP_ID_STR.get(değişken1TipId) + " " + operatör + " "
				+ Değişkenler.TİP_MAP_ID_STR.get(değişken2TipId) + "] " + sonuçTip;
	}

}
