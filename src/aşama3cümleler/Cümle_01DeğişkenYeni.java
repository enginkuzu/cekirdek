package aşama3cümleler;

import yardımcı.Değişkenler.CÜMLE;

public class Cümle_01DeğişkenYeni extends Cümle {

	public int değişkenNo;
	public String değişkenİsmi;
	public String değişkenTipi;

	public Cümle_01DeğişkenYeni(int değişkenNo, String değişkenİsmi, String değişkenTipi) {
		super(CÜMLE.TİP_01DEĞİŞKEN_YENİ);
		this.değişkenNo = değişkenNo;
		this.değişkenİsmi = değişkenİsmi;
		this.değişkenTipi = değişkenTipi;
	}

	@Override
	public String toString() {
		return "Cümle_01DeğişkenYeni[" + değişkenİsmi + " " + değişkenTipi + " > " + değişkenNo + "]";
	}

}
