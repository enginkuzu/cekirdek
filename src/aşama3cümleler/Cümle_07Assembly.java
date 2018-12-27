package aşama3cümleler;

import yardımcı.Değişkenler.CÜMLE;

public class Cümle_07Assembly extends Cümle {

	public String ifade;

	public Cümle_07Assembly(String ifade) {
		super(CÜMLE.TİP_07ASSEMBLY);
		this.ifade = ifade;
	}

	@Override
	public String toString() {
		return "Cümle_07Assembly[" + ifade + "]";
	}

}
