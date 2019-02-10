package aşama3cümleler;

import yardımcı.Değişkenler.CÜMLE;

public class Cümle_09MakineDiliSabitVeri extends Cümle {

	public String sabitVeri;

	public Cümle_09MakineDiliSabitVeri(String sabitVeri) {
		super(CÜMLE.TİP_09MAKİNE_DİLİ_SABİT_VERİ);
		this.sabitVeri = sabitVeri;
	}

	@Override
	public String toString() {
		return "Cümle_09MakineDiliSabitVeri[" + sabitVeri + "]";
	}

}
