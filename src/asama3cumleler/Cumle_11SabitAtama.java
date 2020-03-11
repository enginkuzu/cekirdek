package asama3cumleler;

import yardimci.Degiskenler;
import yardimci.Degiskenler.CÜMLE;

public class Cumle_11SabitAtama extends Cumle {

	public int değişkenNo;
	public String sabitVeri;
	public int sabitVeriTipiId;

	public Cumle_11SabitAtama(int değişkenNo, String sabitVeri, int sabitVeriTipiId) {
		super(CÜMLE.TİP_11SABİT_ATAMA);
		this.değişkenNo = değişkenNo;
		this.sabitVeri = sabitVeri;
		this.sabitVeriTipiId = sabitVeriTipiId;
	}

	@Override
	public String toString() {
		return "Cumle_11SabitAtama[" + sabitVeri + " " + Degiskenler.TİP_MAP_ID_STR.get(sabitVeriTipiId) + " > "
				+ değişkenNo + "]";
	}

}
