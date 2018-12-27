package aşama3cümleler;

import java.util.HashMap;
import java.util.HashSet;

import yardımcı.Değişkenler.FONKSİYON;

public class Fonksiyon_01OperatörFonksiyon extends Fonksiyon {

	public HashMap<String, HashSet<String>> özellikMap;
	public boolean inline;
	public int öncelik;
	public String değişken1İsim;
	public String değişken1Tip;
	public String operatör;
	public String değişken2İsim;
	public String değişken2Tip;
	public String sonuçİsim;
	public String sonuçTip;

	public Fonksiyon_01OperatörFonksiyon() {
		super(FONKSİYON.TİP_01OPERATÖRFONKSİYON);
	}

	@Override
	public String toString() {
		return "Fonksiyon_01OperatörFonksiyon[" + değişken1Tip + " " + operatör + " " + değişken2Tip + "]";
	}

}
