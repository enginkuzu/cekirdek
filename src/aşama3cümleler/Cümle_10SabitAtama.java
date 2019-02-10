package aşama3cümleler;

import yardımcı.Değişkenler.CÜMLE;

public class Cümle_10SabitAtama extends Cümle {

	public int değişkenNo;
	public String sabitVeri;
	public String sabitVeriTipi;

	public Cümle_10SabitAtama(int değişkenNo, String sabitVeri, String sabitVeriTipi) {
		super(CÜMLE.TİP_10SABİT_ATAMA);
		this.değişkenNo = değişkenNo;
		this.sabitVeri = sabitVeri;
		this.sabitVeriTipi = sabitVeriTipi;
	}

	@Override
	public String toString() {
		return "Cümle_10SabitAtama[" + sabitVeri + " " + sabitVeriTipi + " > " + değişkenNo + "]";
	}

}
