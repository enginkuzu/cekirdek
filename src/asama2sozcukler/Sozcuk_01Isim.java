package asama2sozcukler;

import yardimci.Degiskenler.SÖZCÜK;

public class Sozcuk_01Isim extends Sozcuk {

	public String isim = "";

	public Sozcuk_01Isim(char karakter) {
		super(SÖZCÜK.TİP_01İSİM);
		this.isim = this.isim + karakter;
	}

	public Sozcuk_01Isim(String isim) {
		super(SÖZCÜK.TİP_01İSİM);
		this.isim = isim;
	}

	@Override
	public String toString() {
		return tip.toString() + "[" + isim + "]";
	}

}
