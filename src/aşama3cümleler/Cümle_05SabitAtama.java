package aşama3cümleler;

import yardımcı.Değişkenler.CÜMLE;

public class Cümle_05SabitAtama extends Cümle {

	public int değişkenNo;
	public String sabitVeri;
	public String sabitVeriTipi;

	public Cümle_05SabitAtama(int değişkenNo, String sabitVeri, String sabitVeriTipi) {
		super(CÜMLE.TİP_05SABİTATAMA);
		this.değişkenNo = değişkenNo;
		this.sabitVeri = sabitVeri;
		this.sabitVeriTipi = sabitVeriTipi;
	}

	@Override
	public String toString() {
		return "Cümle_05SabitAtama[" + sabitVeri + " " + sabitVeriTipi + " > " + değişkenNo + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Cümle_05SabitAtama) {
			return this.değişkenNo == ((Cümle_05SabitAtama) obj).değişkenNo
					&& this.sabitVeri.equals(((Cümle_05SabitAtama) obj).sabitVeri)
					&& this.sabitVeriTipi.equals(((Cümle_05SabitAtama) obj).sabitVeriTipi);
		}
		return false;
	}

}
