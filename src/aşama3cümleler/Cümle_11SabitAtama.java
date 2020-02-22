package aşama3cümleler;

import yardımcı.Değişkenler;
import yardımcı.Değişkenler.CÜMLE;

public class Cümle_11SabitAtama extends Cümle {

	public int değişkenNo;
	public String sabitVeri;
	public int sabitVeriTipiId;

	public Cümle_11SabitAtama(int değişkenNo, String sabitVeri, int sabitVeriTipiId) {
		super(CÜMLE.TİP_11SABİT_ATAMA);
		this.değişkenNo = değişkenNo;
		this.sabitVeri = sabitVeri;
		this.sabitVeriTipiId = sabitVeriTipiId;
	}

	@Override
	public String toString() {
		return "Cümle_11SabitAtama[" + sabitVeri + " " + Değişkenler.TİP_MAP_ID_STR.get(sabitVeriTipiId) + " > "
				+ değişkenNo + "]";
	}

}
