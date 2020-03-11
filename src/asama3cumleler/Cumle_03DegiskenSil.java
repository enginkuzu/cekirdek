package asama3cumleler;

import yardimci.Degiskenler.CÜMLE;

public class Cumle_03DegiskenSil extends Cumle {

	public int değişkenNo;

	public Cumle_03DegiskenSil(int değişkenNo) {
		super(CÜMLE.TİP_03DEĞİŞKEN_SİL);
		this.değişkenNo = değişkenNo;
	}

	@Override
	public String toString() {
		return "Cumle_03DegiskenSil[" + değişkenNo + "]";
	}

}
