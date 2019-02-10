package aşama3cümleler;

import yardımcı.Değişkenler.CÜMLE;

public class Cümle_05FonksiyonÇağrısı extends Cümle {

	public int değişkenNo;
	public String fonksiyon;
	public int parametre;

	public Cümle_05FonksiyonÇağrısı(int değişkenNo, String fonksiyon, int parametre) {
		super(CÜMLE.TİP_05FUNKSİYON_ÇAĞRISI);
		this.değişkenNo = değişkenNo;
		this.fonksiyon = fonksiyon;
		this.parametre = parametre;
	}

	@Override
	public String toString() {
		return "Cümle_05FonksiyonÇağrısı[" + fonksiyon + "(" + parametre + ") > " + değişkenNo + "]";
	}

}
