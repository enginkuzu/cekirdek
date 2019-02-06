package aşama3cümleler;

import yardımcı.Değişkenler.CÜMLE;

public class Cümle_06DeğişkenAtama extends Cümle {

	public int değişkenNoKaynak;
	public int değişkenNoHedef;

	public Cümle_06DeğişkenAtama(int değişkenNoKaynak, int değişkenNoHedef) {
		super(CÜMLE.TİP_06DEĞİŞKENATAMA);
		this.değişkenNoKaynak = değişkenNoKaynak;
		this.değişkenNoHedef = değişkenNoHedef;
	}

	@Override
	public String toString() {
		return "Cümle_06DeğişkenAtama[" + değişkenNoKaynak + " > " + değişkenNoHedef + "]";
	}

}
