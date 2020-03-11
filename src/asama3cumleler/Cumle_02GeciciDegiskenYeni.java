package asama3cumleler;

import yardimci.Degiskenler;
import yardimci.Degiskenler.CÜMLE;

public class Cumle_02GeciciDegiskenYeni extends Cumle {

	public int değişkenNo;
	public int değişkenTipiId;

	public Cumle_02GeciciDegiskenYeni(int değişkenNo, int değişkenTipiId) {
		super(CÜMLE.TİP_02GEÇİCİ_DEĞİŞKEN_YENİ);
		this.değişkenNo = değişkenNo;
		this.değişkenTipiId = değişkenTipiId;
	}

	@Override
	public String toString() {
		return "Cumle_02GeciciDegiskenYeni[" + Degiskenler.TİP_MAP_ID_STR.get(değişkenTipiId) + " > " + değişkenNo
				+ "]";
	}

}
