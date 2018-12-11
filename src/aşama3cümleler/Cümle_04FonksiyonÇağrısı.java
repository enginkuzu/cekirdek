package aşama3cümleler;

import yardımcı.Değişkenler.CÜMLE;

public class Cümle_04FonksiyonÇağrısı extends Cümle {

	public int değişkenNo;
	public String fonksiyon;
	public int parametre;

	public Cümle_04FonksiyonÇağrısı(int değişkenNo, String fonksiyon, int parametre) {
		super(CÜMLE.TİP_04FUNKSİYONÇAĞRISI);
		this.değişkenNo = değişkenNo;
		this.fonksiyon = fonksiyon;
		this.parametre = parametre;
	}

	@Override
	public String toString() {
		return "Cümle_04FonksiyonÇağrısı[" + fonksiyon + "(" + parametre + ") > " + değişkenNo + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Cümle_04FonksiyonÇağrısı) {
			return this.değişkenNo == ((Cümle_04FonksiyonÇağrısı) obj).değişkenNo
					&& this.fonksiyon.equals(((Cümle_04FonksiyonÇağrısı) obj).fonksiyon)
					&& this.parametre == ((Cümle_04FonksiyonÇağrısı) obj).parametre;
		}
		return false;
	}

}
