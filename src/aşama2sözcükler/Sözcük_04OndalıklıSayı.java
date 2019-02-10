package aşama2sözcükler;

import yardımcı.Değişkenler.SÖZCÜK;

public class Sözcük_04OndalıklıSayı extends Sözcük {

	public String ondalıklıSayı = "";

	public Sözcük_04OndalıklıSayı(char karakter) {
		super(SÖZCÜK.TİP_04ONDALIKLI_SAYI);
		this.ondalıklıSayı = this.ondalıklıSayı + karakter;
	}

	public Sözcük_04OndalıklıSayı(String sayı) {
		super(SÖZCÜK.TİP_04ONDALIKLI_SAYI);
		this.ondalıklıSayı = sayı;
	}

	@Override
	public String toString() {
		return tip.toString() + "[" + ondalıklıSayı + "]";
	}

}
