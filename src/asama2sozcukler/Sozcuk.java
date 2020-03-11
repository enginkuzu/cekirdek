package asama2sozcukler;

import yardimci.Degiskenler.SÖZCÜK;

public class Sozcuk {

	public SÖZCÜK tip;

	public Sozcuk(SÖZCÜK tip) {
		this.tip = tip;
	}

	@Override
	public String toString() {
		return tip.toString();
	}

}
