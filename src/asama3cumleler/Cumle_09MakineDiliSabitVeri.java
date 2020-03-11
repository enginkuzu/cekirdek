package asama3cumleler;

import yardimci.Degiskenler.CÜMLE;

public class Cumle_09MakineDiliSabitVeri extends Cumle {

	public String sabitVeri;

	public Cumle_09MakineDiliSabitVeri(String sabitVeri) {
		super(CÜMLE.TİP_09MAKİNE_DİLİ_SABİT_VERİ);
		this.sabitVeri = sabitVeri;
	}

	@Override
	public String toString() {
		return "Cumle_09MakineDiliSabitVeri[" + sabitVeri + "]";
	}

}
