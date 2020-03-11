package asama3cumleler;

import java.util.HashMap;

public class Asama3Ciktisi {

	public HashMap<String, Fonksiyon_01OperatorFonksiyon> operatörFonksiyonMap;
	public HashMap<String, Fonksiyon_02IsimliFonksiyon> isimFonksiyonMap;
	public Fonksiyon_03AnaFonksiyon anaFonksiyon;

	public Asama3Ciktisi(HashMap<String, Fonksiyon_01OperatorFonksiyon> operatörFonksiyonMap,
			HashMap<String, Fonksiyon_02IsimliFonksiyon> isimFonksiyonMap, Fonksiyon_03AnaFonksiyon anaFonksiyon) {
		this.operatörFonksiyonMap = operatörFonksiyonMap;
		this.isimFonksiyonMap = isimFonksiyonMap;
		this.anaFonksiyon = anaFonksiyon;
	}

}
