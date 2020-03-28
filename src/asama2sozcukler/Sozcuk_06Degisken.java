package asama2sozcukler;

import yardimci.Degiskenler.SÖZCÜK;

public class Sozcuk_06Degisken extends Sozcuk {

	public int değişkenNo;
	public String değişkenİsim;
	public int değişkenTipiId;

	public Sozcuk_06Degisken(int değişkenNo, String değişkenİsim, int değişkenTipiId) {
		super(SÖZCÜK.TİP_99DEĞİŞKEN);
		this.değişkenNo = değişkenNo;
		this.değişkenİsim = değişkenİsim;
		this.değişkenTipiId = değişkenTipiId;
	}

	@Override
	public String toString() {
		return tip.toString() + "[no:" + değişkenNo + ",isim:" + değişkenİsim + ",tip:" + değişkenTipiId + "]";
	}

}
