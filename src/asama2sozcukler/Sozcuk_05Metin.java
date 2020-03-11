package asama2sozcukler;

import yardimci.Degiskenler.SÖZCÜK;

public class Sozcuk_05Metin extends Sozcuk {

	public String metin = "";

	public Sozcuk_05Metin() {
		super(SÖZCÜK.TİP_05METİN);
	}

	public Sozcuk_05Metin(String metin) {
		super(SÖZCÜK.TİP_05METİN);
		this.metin = metin;
	}

	@Override
	public String toString() {
		return tip.toString() + "[" + metin + "]";
	}

}
