package aşama2sözcükler;

import yardımcı.Değişkenler.SÖZCÜK;

public class Sözcük_01İsim extends Sözcük {

	public String isim = "";

	public Sözcük_01İsim(char karakter) {
		super(SÖZCÜK.TİP_01İSİM);
		this.isim = this.isim + karakter;
	}

	public Sözcük_01İsim(String isim) {
		super(SÖZCÜK.TİP_01İSİM);
		this.isim = isim;
	}

	@Override
	public String toString() {
		return tip.toString() + "[" + isim + "]";
	}

}
