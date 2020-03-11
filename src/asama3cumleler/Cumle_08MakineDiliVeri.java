package aşama3cümleler;

import yardımcı.Değişkenler.CÜMLE;

public class Cümle_08MakineDiliVeri extends Cümle {

	public String veri;

	public Cümle_08MakineDiliVeri(String veri) {
		super(CÜMLE.TİP_08MAKİNE_DİLİ_VERİ);
		this.veri = veri;
	}

	@Override
	public String toString() {
		return "Cümle_08MakineDiliVeri[" + veri + "]";
	}

}
