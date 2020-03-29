package asama2sozcukler;

import yardimci.Degiskenler.SÖZCÜK;

public class Sozcuk_00Yok extends Sozcuk {

	public Sozcuk_00Yok() {
		super(SÖZCÜK.TİP_00YOK);
	}

	@Override
	public String toString() {
		return tip.toString();
	}

}
