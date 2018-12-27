package aşama3cümleler;

import yardımcı.Değişkenler.CÜMLE;

public class Cümle_09AssemblyRoData extends Cümle {

	public String ifade;

	public Cümle_09AssemblyRoData(String ifade) {
		super(CÜMLE.TİP_09ASSEMBLYRODATA);
		this.ifade = ifade;
	}

	@Override
	public String toString() {
		return "Cümle_09AssemblyRoData[" + ifade + "]";
	}

}
