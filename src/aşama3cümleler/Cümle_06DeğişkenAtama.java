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

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Cümle_06DeğişkenAtama) {
			return this.değişkenNoKaynak == ((Cümle_06DeğişkenAtama) obj).değişkenNoKaynak
					&& this.değişkenNoHedef == ((Cümle_06DeğişkenAtama) obj).değişkenNoHedef;
		}
		return false;
	}

}
