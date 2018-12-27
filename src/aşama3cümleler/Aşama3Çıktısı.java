package aşama3cümleler;

import java.util.HashMap;

public class Aşama3Çıktısı {

	public HashMap<String, Fonksiyon_01OperatörFonksiyon> operatörFonksiyonMap;
	public HashMap<String, Fonksiyon_02İsimliFonksiyon> isimFonksiyonMap;
	public Fonksiyon_03AnaFonksiyon anaFonksiyon;

	public Aşama3Çıktısı(HashMap<String, Fonksiyon_01OperatörFonksiyon> operatörFonksiyonMap,
			HashMap<String, Fonksiyon_02İsimliFonksiyon> isimFonksiyonMap, Fonksiyon_03AnaFonksiyon anaFonksiyon) {
		this.operatörFonksiyonMap = operatörFonksiyonMap;
		this.isimFonksiyonMap = isimFonksiyonMap;
		this.anaFonksiyon = anaFonksiyon;
	}

}
