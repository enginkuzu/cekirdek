package asama3cumleler;

import yardimci.Degiskenler;
import yardimci.Degiskenler.CÜMLE;

public class Cumle_01DegiskenYeni extends Cumle {

	public int değişkenNo;
	public String değişkenİsmi;
	public int değişkenTipiId;

	public Cumle_01DegiskenYeni(int değişkenNo, String değişkenİsmi, int değişkenTipiId) {
		super(CÜMLE.TİP_01DEĞİŞKEN_YENİ);
		this.değişkenNo = değişkenNo;
		this.değişkenİsmi = değişkenİsmi;
		this.değişkenTipiId = değişkenTipiId;
	}

	@Override
	public String toString() {
		return "Cumle_01DegiskenYeni[" + değişkenİsmi + " " + Degiskenler.TİP_MAP_ID_STR.get(değişkenTipiId) + " > "
				+ değişkenNo + "]";
	}

}
