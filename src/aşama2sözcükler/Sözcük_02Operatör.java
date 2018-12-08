package aşama2sözcükler;

import yardımcı.Değişkenler.SÖZCÜK;

public class Sözcük_02Operatör extends Sözcük {

	public String operatör = "";

	public Sözcük_02Operatör(char karakter) {
		super(SÖZCÜK.TİP_02OPERATÖR);
		this.operatör = this.operatör + karakter;
	}

	public Sözcük_02Operatör(String operatör) {
		super(SÖZCÜK.TİP_02OPERATÖR);
		this.operatör = operatör;
	}

	@Override
	public String toString() {
		return tip.toString() + "[" + operatör + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Sözcük_02Operatör) {
			return this.operatör.equals(((Sözcük_02Operatör) obj).operatör);
		}
		return false;
	}

}
