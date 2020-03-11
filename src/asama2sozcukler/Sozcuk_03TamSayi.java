package asama2sozcukler;

import yardimci.Degiskenler.SÖZCÜK;

public class Sozcuk_03TamSayi extends Sozcuk {

	public String tamSayı = "";

	public Sozcuk_03TamSayi(char karakter) {
		super(SÖZCÜK.TİP_03TAM_SAYI);
		this.tamSayı = this.tamSayı + karakter;
	}

	public Sozcuk_03TamSayi(String sayi) {
		super(SÖZCÜK.TİP_03TAM_SAYI);
		this.tamSayı = sayi;
	}

	@Override
	public String toString() {
		return tip.toString() + "[" + tamSayı + "]";
	}

}
