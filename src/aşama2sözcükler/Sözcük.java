package aşama2sözcükler;

import yardımcı.Değişkenler.SÖZCÜK;

public class Sözcük {

	public SÖZCÜK tip;

	public Sözcük(SÖZCÜK tip) {
		this.tip = tip;
	}

	@Override
	public String toString() {
		return tip.toString();
	}

}
