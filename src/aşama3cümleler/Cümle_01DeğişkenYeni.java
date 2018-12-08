package aşama3cümleler;

import yardımcı.Değişkenler.CÜMLE;

public class Cümle_01DeğişkenYeni extends Cümle {

	public int değişkenNo;
	public String değişkenİsmi;
	public String değişkenTipi;

	public Cümle_01DeğişkenYeni(int değişkenNo, String değişkenİsmi, String değişkenTipi) {
		super(CÜMLE.TİP_01DEĞİŞKENYENİ);
		this.değişkenNo = değişkenNo;
		this.değişkenİsmi = değişkenİsmi;
		this.değişkenTipi = değişkenTipi;
	}

	@Override
	public String toString() {
		return "Cümle_01DeğişkenYeni[" + değişkenİsmi + " " + değişkenTipi + " > " + değişkenNo + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Cümle_01DeğişkenYeni) {
			return this.değişkenNo == ((Cümle_01DeğişkenYeni) obj).değişkenNo
					&& this.değişkenİsmi.equals(((Cümle_01DeğişkenYeni) obj).değişkenİsmi)
					&& this.değişkenTipi.equals(((Cümle_01DeğişkenYeni) obj).değişkenTipi);
		}
		return false;
	}

}
