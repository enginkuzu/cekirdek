package aşama3cümleler;

import yardımcı.Değişkenler.CÜMLE;

public class Cümle_03DeğişkenSil extends Cümle {

	public int değişkenNo;

	public Cümle_03DeğişkenSil(int değişkenNo) {
		super(CÜMLE.TİP_03DEĞİŞKENSİL);
		this.değişkenNo = değişkenNo;
	}

	@Override
	public String toString() {
		return "Cümle_03DeğişkenSil[" + değişkenNo + "]";
	}

}
