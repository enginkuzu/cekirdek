package aşama3cümleler;

import yardımcı.Değişkenler.CÜMLE;

public class Cümle_03Operatörİşlemi extends Cümle {

	public int değişkenNo;
	public String operatör;
	public int parametreNo1;
	public int parametreNo2;

	public Cümle_03Operatörİşlemi(int değişkenNo, String operatör, int parametreNo1, int parametreNo2) {
		super(CÜMLE.TİP_03OPERATÖRİŞLEMİ);
		this.değişkenNo = değişkenNo;
		this.operatör = operatör;
		this.parametreNo1 = parametreNo1;
		this.parametreNo2 = parametreNo2;
	}

	@Override
	public String toString() {
		return "Cümle_03Operatörİşlemi[" + parametreNo1 + " " + operatör + " " + parametreNo2 + " > " + değişkenNo
				+ "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Cümle_03Operatörİşlemi) {
			return this.değişkenNo == ((Cümle_03Operatörİşlemi) obj).değişkenNo
					&& this.operatör.equals(((Cümle_03Operatörİşlemi) obj).operatör)
					&& this.parametreNo1 == ((Cümle_03Operatörİşlemi) obj).parametreNo1
					&& this.parametreNo2 == ((Cümle_03Operatörİşlemi) obj).parametreNo2;
		}
		return false;
	}

}
