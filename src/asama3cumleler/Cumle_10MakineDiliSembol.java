package asama3cumleler;

import yardimci.Degiskenler.CÜMLE;

public class Cumle_10MakineDiliSembol extends Cumle {

	public String sembol;

	public Cumle_10MakineDiliSembol(String sembol) {
		super(CÜMLE.TİP_10MAKİNE_DİLİ_SEMBOL);
		this.sembol = sembol;
	}

	@Override
	public String toString() {
		return "Cumle_10MakineDiliSembol[" + sembol + "]";
	}

}
