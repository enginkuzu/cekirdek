package aşama2sözcükler;

import yardımcı.Değişkenler.SÖZCÜK;

public class Sözcük_05Metin extends Sözcük {

	public String metin = "";

	public Sözcük_05Metin() {
		super(SÖZCÜK.TİP_05METİN);
	}

	public Sözcük_05Metin(String metin) {
		super(SÖZCÜK.TİP_05METİN);
		this.metin = metin;
	}

	@Override
	public String toString() {
		return tip.toString() + "[" + metin + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Sözcük_05Metin) {
			return this.metin.equals(((Sözcük_05Metin) obj).metin);
		}
		return false;
	}

}
