package aşama3cümleler;

import yardımcı.Değişkenler.FONKSİYON;

public class Fonksiyon_02İsimliFonksiyon extends Fonksiyon {

	public String isim;
	public String değişken1İsim;
	public int değişken1TipId;
	public String değişken1TipAssembly;

	public Fonksiyon_02İsimliFonksiyon() {
		super(FONKSİYON.TİP_02İSİMLİFONKSİYON);
	}

	@Override
	public String toString() {
		return "Fonksiyon_02İsimliFonksiyon[" + isim + "(" + değişken1İsim + " : " + değişken1TipId + ")]";
	}

}
