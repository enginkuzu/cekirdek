package asama3cumleler;

import java.util.HashMap;
import java.util.HashSet;

import yardimci.Degiskenler;
import yardimci.Degiskenler.FONKSİYON;
import yardimci.Fonksiyonlar;

public class Fonksiyon_02IsimliFonksiyon extends Fonksiyon {

	public HashMap<String, HashSet<String>> özellikMap;
	public String isim;
	public String değişken1İsim;
	public int değişken1TipId;
	public String değişken1TipAssembly;
	public String sonuçİsim;
	public int sonuçTipId;
	public String sonuçTipAssembly;

	public Fonksiyon_02IsimliFonksiyon(HashMap<String, HashSet<String>> özellikMap) {
		super(FONKSİYON.TİP_02İSİMLİFONKSİYON);
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
		return "Fonksiyon_02IsimliFonksiyon[" + isim + "(" + değişken1İsim + " : " + değişken1TipId + ")] "
				+ Degiskenler.TİP_MAP_ID_STR.get(sonuçTipId);
	}

}
