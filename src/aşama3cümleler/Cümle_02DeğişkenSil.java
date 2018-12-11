package aşama3cümleler;

import yardımcı.Değişkenler.CÜMLE;

public class Cümle_02DeğişkenSil extends Cümle {

	public int değişkenNo;

	public Cümle_02DeğişkenSil(int değişkenNo) {
		super(CÜMLE.TİP_02DEĞİŞKENSİL);
		this.değişkenNo = değişkenNo;
	}

	@Override
	public String toString() {
		return "Cümle_02DeğişkenSil[" + değişkenNo + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Cümle_02DeğişkenSil) {
			return this.değişkenNo == ((Cümle_02DeğişkenSil) obj).değişkenNo;
		}
		return false;
	}

}
