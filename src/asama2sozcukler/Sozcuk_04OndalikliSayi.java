package asama2sozcukler;

import yardimci.Degiskenler.SÖZCÜK;

public class Sozcuk_04OndalikliSayi extends Sozcuk {

	public String ondalıklıSayı = "";

	public Sozcuk_04OndalikliSayi(char karakter) {
		super(SÖZCÜK.TİP_04ONDALIKLI_SAYI);
		this.ondalıklıSayı = this.ondalıklıSayı + karakter;
	}

	public Sozcuk_04OndalikliSayi(String sayı) {
		super(SÖZCÜK.TİP_04ONDALIKLI_SAYI);
		this.ondalıklıSayı = sayı;
	}

	@Override
	public String toString() {
		return tip.toString() + "[" + ondalıklıSayı + "]";
	}

}
