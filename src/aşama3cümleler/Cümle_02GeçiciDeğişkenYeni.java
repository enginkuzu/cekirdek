package aşama3cümleler;

import yardımcı.Değişkenler.CÜMLE;

public class Cümle_02GeçiciDeğişkenYeni extends Cümle {

	public int değişkenNo;
	public String değişkenTipi;

	public Cümle_02GeçiciDeğişkenYeni(int değişkenNo, String değişkenTipi) {
		super(CÜMLE.TİP_02GEÇİCİDEĞİŞKENYENİ);
		this.değişkenNo = değişkenNo;
		this.değişkenTipi = değişkenTipi;
	}

	@Override
	public String toString() {
		return "Cümle_02GeçiciDeğişkenYeni[" + değişkenTipi + " > " + değişkenNo + "]";
	}

}
