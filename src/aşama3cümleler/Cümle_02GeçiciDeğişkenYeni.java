package aşama3cümleler;

import yardımcı.Değişkenler;
import yardımcı.Değişkenler.CÜMLE;

public class Cümle_02GeçiciDeğişkenYeni extends Cümle {

	public int değişkenNo;
	public int değişkenTipiId;

	public Cümle_02GeçiciDeğişkenYeni(int değişkenNo, int değişkenTipiId) {
		super(CÜMLE.TİP_02GEÇİCİ_DEĞİŞKEN_YENİ);
		this.değişkenNo = değişkenNo;
		this.değişkenTipiId = değişkenTipiId;
	}

	@Override
	public String toString() {
		return "Cümle_02GeçiciDeğişkenYeni[" + Değişkenler.TİP_MAP_ID_STR.get(değişkenTipiId) + " > " + değişkenNo
				+ "]";
	}

}
