package asama3cumleler;

import yardimci.Degiskenler.CÜMLE;

public class Cumle_07MakineDiliKod extends Cumle {

	public String kod;

	public Cumle_07MakineDiliKod(String kod) {
		super(CÜMLE.TİP_07MAKİNE_DİLİ_KOD);
		this.kod = kod;
	}

	@Override
	public String toString() {
		return "Cumle_07MakineDiliKod[" + kod + "]";
	}

}
