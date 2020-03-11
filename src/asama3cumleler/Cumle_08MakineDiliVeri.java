package asama3cumleler;

import yardimci.Degiskenler.CÜMLE;

public class Cumle_08MakineDiliVeri extends Cumle {

	public String veri;

	public Cumle_08MakineDiliVeri(String veri) {
		super(CÜMLE.TİP_08MAKİNE_DİLİ_VERİ);
		this.veri = veri;
	}

	@Override
	public String toString() {
		return "Cumle_08MakineDiliVeri[" + veri + "]";
	}

}
