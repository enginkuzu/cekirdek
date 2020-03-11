package asama3cumleler;

import yardimci.Degiskenler.CÜMLE;

public class Cumle_06DegiskenAtama extends Cumle {

	public int değişkenNoKaynak;
	public int değişkenNoHedef;

	public Cumle_06DegiskenAtama(int değişkenNoKaynak, int değişkenNoHedef) {
		super(CÜMLE.TİP_06DEĞİŞKEN_ATAMA);
		this.değişkenNoKaynak = değişkenNoKaynak;
		this.değişkenNoHedef = değişkenNoHedef;
	}

	@Override
	public String toString() {
		return "Cumle_06DegiskenAtama[" + değişkenNoKaynak + " > " + değişkenNoHedef + "]";
	}

}
