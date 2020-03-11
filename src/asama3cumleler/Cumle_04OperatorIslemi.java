package aşama3cümleler;

import yardımcı.Değişkenler.CÜMLE;

public class Cümle_04Operatörİşlemi extends Cümle {

	public int değişkenNo;
	public String operatör;
	public int parametreNo1;
	public int parametreNo2;

	public Cümle_04Operatörİşlemi(int değişkenNo, String operatör, int parametreNo1, int parametreNo2) {
		super(CÜMLE.TİP_04OPERATÖR_İŞLEMİ);
		this.değişkenNo = değişkenNo;
		this.operatör = operatör;
		this.parametreNo1 = parametreNo1;
		this.parametreNo2 = parametreNo2;
	}

	@Override
	public String toString() {
		return "Cümle_04Operatörİşlemi[" + parametreNo1 + " " + operatör + " " + parametreNo2 + " > " + değişkenNo
				+ "]";
	}

}
