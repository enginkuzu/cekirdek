package aşama2sözcükler;

import yardımcı.Değişkenler.SÖZCÜK;

public class Sözcük_04OndalıklıSayı extends Sözcük {

	public String sayı = "";

	public Sözcük_04OndalıklıSayı(char karakter) {
		super(SÖZCÜK.TİP_04ONDALIKLI_SAYI);
		this.sayı = this.sayı + karakter;
	}

	public Sözcük_04OndalıklıSayı(String sayı) {
		super(SÖZCÜK.TİP_04ONDALIKLI_SAYI);
		this.sayı = sayı;
	}

	@Override
	public String toString() {
		return tip.toString() + "[" + sayı + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Sözcük_04OndalıklıSayı) {
			return this.sayı.equals(((Sözcük_04OndalıklıSayı) obj).sayı);
		}
		return false;
	}

}
