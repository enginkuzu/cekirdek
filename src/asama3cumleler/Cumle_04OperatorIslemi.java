package asama3cumleler;

import yardimci.Degiskenler.CÜMLE;

public class Cumle_04OperatorIslemi extends Cumle {

	public int değişkenNo;
	public String operatör;
	public int parametreNo1;
	public int parametreNo2;

	public Cumle_04OperatorIslemi(int değişkenNo, String operatör, int parametreNo1, int parametreNo2) {
		super(CÜMLE.TİP_04OPERATÖR_İŞLEMİ);
		this.değişkenNo = değişkenNo;
		this.operatör = operatör;
		this.parametreNo1 = parametreNo1;
		this.parametreNo2 = parametreNo2;
	}

	@Override
	public String toString() {
		return "Cumle_04OperatorIslemi[" + parametreNo1 + " " + operatör + " " + parametreNo2 + " > " + değişkenNo
				+ "]";
	}

}
