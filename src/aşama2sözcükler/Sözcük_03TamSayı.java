package aşama2sözcükler;

import yardımcı.Değişkenler.SÖZCÜK;

public class Sözcük_03TamSayı extends Sözcük {

	public String sayı = "";

	public Sözcük_03TamSayı(char karakter) {
		super(SÖZCÜK.TİP_03TAM_SAYI);
		this.sayı = this.sayı + karakter;
	}

	public Sözcük_03TamSayı(String sayi) {
		super(SÖZCÜK.TİP_03TAM_SAYI);
		this.sayı = sayi;
	}

	@Override
	public String toString() {
		return tip.toString() + "[" + sayı + "]";
	}

}
