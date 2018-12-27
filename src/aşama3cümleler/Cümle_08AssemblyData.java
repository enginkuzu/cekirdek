package aşama3cümleler;

import yardımcı.Değişkenler.CÜMLE;

public class Cümle_08AssemblyData extends Cümle {

	public String ifade;

	public Cümle_08AssemblyData(String ifade) {
		super(CÜMLE.TİP_08ASSEMBLYDATA);
		this.ifade = ifade;
	}

	@Override
	public String toString() {
		return "Cümle_08AssemblyData[" + ifade + "]";
	}

}
