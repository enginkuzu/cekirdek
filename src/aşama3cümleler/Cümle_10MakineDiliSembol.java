package aşama3cümleler;

import yardımcı.Değişkenler.CÜMLE;

public class Cümle_10MakineDiliSembol extends Cümle {

	public String sembol;

	public Cümle_10MakineDiliSembol(String sembol) {
		super(CÜMLE.TİP_10MAKİNE_DİLİ_SEMBOL);
		this.sembol = sembol;
	}

	@Override
	public String toString() {
		return "Cümle_10MakineDiliSembol[" + sembol + "]";
	}

}
