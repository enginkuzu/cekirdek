package aşama3cümleler;

import yardımcı.Değişkenler.CÜMLE;

public class Cümle_05SabitTanımlama extends Cümle {

	public int değişkenNo;
	public String sabitVeri;
	public String sabitVeriTipi;

	public Cümle_05SabitTanımlama(int değişkenNo, String sabitVeri, String sabitVeriTipi) {
		super(CÜMLE.TİP_05SABİTTANIMLAMA);
		this.değişkenNo = değişkenNo;
		this.sabitVeri = sabitVeri;
		this.sabitVeriTipi = sabitVeriTipi;
	}

	@Override
	public String toString() {
		return "Cümle_05SabitTanımlama[" + sabitVeri + " " + sabitVeriTipi + " > " + değişkenNo + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Cümle_05SabitTanımlama) {
			return this.değişkenNo == ((Cümle_05SabitTanımlama) obj).değişkenNo
					&& this.sabitVeri.equals(((Cümle_05SabitTanımlama) obj).sabitVeri)
					&& this.sabitVeriTipi.equals(((Cümle_05SabitTanımlama) obj).sabitVeriTipi);
		}
		return false;
	}

}
