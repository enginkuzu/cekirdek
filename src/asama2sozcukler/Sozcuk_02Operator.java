package asama2sozcukler;

import yardimci.Degiskenler.SÖZCÜK;

public class Sozcuk_02Operator extends Sozcuk {

	public String operatör = "";

	public Sozcuk_02Operator(char karakter) {
		super(SÖZCÜK.TİP_02OPERATÖR);
		this.operatör = this.operatör + karakter;
	}

	public Sozcuk_02Operator(String operatör) {
		super(SÖZCÜK.TİP_02OPERATÖR);
		this.operatör = operatör;
	}

	@Override
	public String toString() {
		return tip.toString() + "[" + operatör + "]";
	}

}
