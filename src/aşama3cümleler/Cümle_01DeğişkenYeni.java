package aşama3cümleler;

import yardımcı.Değişkenler;
import yardımcı.Değişkenler.CÜMLE;

public class Cümle_01DeğişkenYeni extends Cümle {

	public int değişkenNo;
	public String değişkenİsmi;
	public int değişkenTipiId;

	public Cümle_01DeğişkenYeni(int değişkenNo, String değişkenİsmi, int değişkenTipiId) {
		super(CÜMLE.TİP_01DEĞİŞKEN_YENİ);
		this.değişkenNo = değişkenNo;
		this.değişkenİsmi = değişkenİsmi;
		this.değişkenTipiId = değişkenTipiId;
	}

	@Override
	public String toString() {
		return "Cümle_01DeğişkenYeni[" + değişkenİsmi + " " + Değişkenler.TİP_MAP_ID_STR.get(değişkenTipiId) + " > "
				+ değişkenNo + "]";
	}

}
