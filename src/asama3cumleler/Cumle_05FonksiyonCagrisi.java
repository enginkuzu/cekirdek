package asama3cumleler;

import yardimci.Degiskenler.CÜMLE;

public class Cumle_05FonksiyonCagrisi extends Cumle {

	public int değişkenNo;
	public String fonksiyon;
	public int fonksiyonId;
	public int parametre;

	public Cumle_05FonksiyonCagrisi(int değişkenNo, String fonksiyon, int fonksiyonId, int parametre) {
		super(CÜMLE.TİP_05FUNKSİYON_ÇAĞRISI);
		this.değişkenNo = değişkenNo;
		this.fonksiyon = fonksiyon;
		this.fonksiyonId = fonksiyonId;
		this.parametre = parametre;
	}

	@Override
	public String toString() {
		return "Cumle_05FonksiyonCagrisi[" + fonksiyon + "(" + parametre + ") > " + değişkenNo + "]";
	}

}
