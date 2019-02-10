package aşama3cümleler;

import yardımcı.Değişkenler.CÜMLE;

public class Cümle_07MakineDiliKod extends Cümle {

	public String kod;

	public Cümle_07MakineDiliKod(String kod) {
		super(CÜMLE.TİP_07MAKİNE_DİLİ_KOD);
		this.kod = kod;
	}

	@Override
	public String toString() {
		return "Cümle_07MakineDiliKod[" + kod + "]";
	}

}
