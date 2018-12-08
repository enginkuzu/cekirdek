package aşama3cümleler;

import java.util.ArrayList;

import yardımcı.Değişkenler.CÜMLE;

public class Cümle_04FonksiyonÇağrısı extends Cümle {

	public int değişkenNo;
	public String fonksiyon;
	public ArrayList<Integer> parametreler = new ArrayList<Integer>();

	public Cümle_04FonksiyonÇağrısı(int değişkenNo, String fonksiyon) {
		super(CÜMLE.TİP_04FUNKSİYONÇAĞRISI);
		this.değişkenNo = değişkenNo;
		this.fonksiyon = fonksiyon;
	}

	public Cümle_04FonksiyonÇağrısı add(int değişkenNo) {
		parametreler.add(değişkenNo);
		return this;
	}

	@Override
	public String toString() {
		return "Cümle_04FonksiyonÇağrısı[" + fonksiyon + "(" + parametreler.toString() + ") > " + değişkenNo + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Cümle_04FonksiyonÇağrısı) {
			return this.değişkenNo == ((Cümle_04FonksiyonÇağrısı) obj).değişkenNo
					&& this.fonksiyon.equals(((Cümle_04FonksiyonÇağrısı) obj).fonksiyon)
					&& this.parametreler.equals(((Cümle_04FonksiyonÇağrısı) obj).parametreler);
		}
		return false;
	}

}
