package aşama2sözcükler;

import yardımcı.Değişkenler.SÖZCÜK;

public class Sözcük_03TamSayı extends Sözcük {

	public String tamSayı = "";

	public Sözcük_03TamSayı(char karakter) {
		super(SÖZCÜK.TİP_03TAM_SAYI);
		this.tamSayı = this.tamSayı + karakter;
	}

	public Sözcük_03TamSayı(String sayi) {
		super(SÖZCÜK.TİP_03TAM_SAYI);
		this.tamSayı = sayi;
	}

	@Override
	public String toString() {
		return tip.toString() + "[" + tamSayı + "]";
	}

}
