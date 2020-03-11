package asama2sozcukler;

import java.util.ArrayList;

import yardimci.Fonksiyonlar;
import yardimci.Degiskenler.SÖZCÜK;

public class Asama2Sozcukler {

	private static Sozcuk ikiÖncekiSozcuk = null;
	private static Sozcuk birÖncekiSozcuk = null;
	private static ArrayList<Sozcuk> aktifCumle = null;
	private static ArrayList<Sozcuk[]> tümCumleler = null;

	public static void sözcükEkle(Sozcuk sözcük) {
		if (sözcük.tip == SÖZCÜK.TİP_06SATIR_SONU) {
			aktifCumle.add(sözcük);
			Sozcuk[] cümle = new Sozcuk[aktifCumle.size()];
			aktifCumle.toArray(cümle);
			tümCumleler.add(cümle);
			aktifCumle = new ArrayList<Sozcuk>();
		} else {
			if (birÖncekiSozcuk.tip == SÖZCÜK.TİP_07DEĞİŞKEN_TİPİ && sözcük.tip == SÖZCÜK.TİP_10ATAMA_SAĞA) {
				aktifCumle.remove(aktifCumle.size() - 1);
				sözcük = new Sozcuk(SÖZCÜK.TİP_12TANIMLAMA_SAĞA);
			} else if (birÖncekiSozcuk.tip == SÖZCÜK.TİP_09ATAMA_SOLA && sözcük.tip == SÖZCÜK.TİP_07DEĞİŞKEN_TİPİ) {
				aktifCumle.remove(aktifCumle.size() - 1);
				sözcük = new Sozcuk(SÖZCÜK.TİP_11TANIMLAMA_SOLA);
			} else if (ikiÖncekiSozcuk.tip == SÖZCÜK.TİP_07DEĞİŞKEN_TİPİ && birÖncekiSozcuk.tip == SÖZCÜK.TİP_02OPERATÖR
					&& sözcük.tip == SÖZCÜK.TİP_01İSİM) {
				aktifCumle.remove(aktifCumle.size() - 1);
				((Sozcuk_01Isim) sözcük).isim = ((Sozcuk_02Operator) birÖncekiSozcuk).operatör
						+ ((Sozcuk_01Isim) sözcük).isim;
			}
			aktifCumle.add(sözcük);
		}
		ikiÖncekiSozcuk = birÖncekiSozcuk;
		birÖncekiSozcuk = sözcük;
	}

	public static Object işle(String içerik, String dosyaAdı) {

		ikiÖncekiSozcuk = new Sozcuk(SÖZCÜK.TİP_00YOK);
		birÖncekiSozcuk = new Sozcuk(SÖZCÜK.TİP_00YOK);
		aktifCumle = new ArrayList<Sozcuk>();
		tümCumleler = new ArrayList<Sozcuk[]>();

		StringBuilder hatalar = new StringBuilder();

		// durum 0 : Normal durum
		// durum 1 : Operatör
		// durum 3 : isim
		// durum 4,5,6 : Tam sayı ve ondalıklı sayı
		// durum 7,8,9,10 : Açıklamalar
		// durum 11 : Özellik
		// durum 12,13 : Metin
		int durum = 0;
		Sozcuk sözcük = new Sozcuk(SÖZCÜK.TİP_00YOK);

		char[] karakterler = içerik.toCharArray();
		for (int i = 0; i < karakterler.length; i++) {
			char karakter = karakterler[i];
			//
			if (durum == 0) {
				// durum 0 : Normal durum
				if (karakter == '\r') {
					;
				} else if (karakter == '\'') {
					if (sözcük.tip != SÖZCÜK.TİP_00YOK) {
						sözcükEkle(sözcük);
						sözcük = new Sozcuk(SÖZCÜK.TİP_00YOK);
					}
					durum = 7;
				} else if (karakter == ' ' || karakter == '\t' || karakter == '\n') {
					if (sözcük.tip != SÖZCÜK.TİP_00YOK) {
						sözcükEkle(sözcük);
						sözcük = new Sozcuk(SÖZCÜK.TİP_00YOK);
					}
				} else if (karakter == ';') {
					if (sözcük.tip != SÖZCÜK.TİP_00YOK) {
						sözcükEkle(sözcük);
						sözcük = new Sozcuk(SÖZCÜK.TİP_00YOK);
					}
					if (aktifCumle.isEmpty()) {
						hatalar.append("Gereksiz yere ';' kullanılmış\n");
					} else {
						sözcükEkle(new Sozcuk(SÖZCÜK.TİP_06SATIR_SONU));
					}
				} else if (karakter == ':') {
					if (sözcük.tip != SÖZCÜK.TİP_00YOK) {
						sözcükEkle(sözcük);
						sözcük = new Sozcuk(SÖZCÜK.TİP_00YOK);
					}
					sözcükEkle(new Sozcuk(SÖZCÜK.TİP_07DEĞİŞKEN_TİPİ));
				} else if (karakter == '.') {
					if (sözcük.tip != SÖZCÜK.TİP_00YOK) {
						sözcükEkle(sözcük);
						sözcük = new Sozcuk(SÖZCÜK.TİP_00YOK);
					}
					sözcükEkle(new Sozcuk(SÖZCÜK.TİP_08NOKTA));
				} else if (karakter == '<') {
					if (sözcük.tip != SÖZCÜK.TİP_00YOK) {
						sözcükEkle(sözcük);
						sözcük = new Sozcuk(SÖZCÜK.TİP_00YOK);
					}
					sözcükEkle(new Sozcuk(SÖZCÜK.TİP_09ATAMA_SOLA));
				} else if (karakter == '>') {
					if (sözcük.tip != SÖZCÜK.TİP_00YOK) {
						sözcükEkle(sözcük);
						sözcük = new Sozcuk(SÖZCÜK.TİP_00YOK);
					}
					sözcükEkle(new Sozcuk(SÖZCÜK.TİP_10ATAMA_SAĞA));
				} else if (karakter == '@') {
					if (sözcük.tip != SÖZCÜK.TİP_00YOK) {
						sözcükEkle(sözcük);
						sözcük = new Sozcuk(SÖZCÜK.TİP_00YOK);
					}
					sözcükEkle(new Sozcuk(SÖZCÜK.TİP_13ÖZELLİK));
					durum = 11;
				} else if (karakter == '(') {
					if (sözcük.tip != SÖZCÜK.TİP_00YOK) {
						sözcükEkle(sözcük);
						sözcük = new Sozcuk(SÖZCÜK.TİP_00YOK);
					}
					sözcükEkle(new Sozcuk(SÖZCÜK.TİP_14AÇ_PARANTEZ));
				} else if (karakter == ')') {
					if (sözcük.tip != SÖZCÜK.TİP_00YOK) {
						sözcükEkle(sözcük);
						sözcük = new Sozcuk(SÖZCÜK.TİP_00YOK);
					}
					sözcükEkle(new Sozcuk(SÖZCÜK.TİP_15KAPA_PARANTEZ));
				} else if (karakter == '{') {
					if (sözcük.tip != SÖZCÜK.TİP_00YOK) {
						sözcükEkle(sözcük);
						sözcük = new Sozcuk(SÖZCÜK.TİP_00YOK);
					}
					sözcükEkle(new Sozcuk(SÖZCÜK.TİP_16AÇ_SÜSLÜ));
					sözcükEkle(new Sozcuk(SÖZCÜK.TİP_06SATIR_SONU));
				} else if (karakter == '}') {
					if (sözcük.tip != SÖZCÜK.TİP_00YOK) {
						sözcükEkle(sözcük);
						sözcük = new Sozcuk(SÖZCÜK.TİP_00YOK);
					}
					sözcükEkle(new Sozcuk(SÖZCÜK.TİP_17KAPA_SÜSLÜ));
					sözcükEkle(new Sozcuk(SÖZCÜK.TİP_06SATIR_SONU));
				} else if (karakter == ',') {
					if (sözcük.tip != SÖZCÜK.TİP_00YOK) {
						sözcükEkle(sözcük);
						sözcük = new Sozcuk(SÖZCÜK.TİP_00YOK);
					}
					sözcükEkle(new Sozcuk(SÖZCÜK.TİP_18VİRGÜL));
				} else if (karakter == '"') {
					if (sözcük.tip != SÖZCÜK.TİP_00YOK) {
						sözcükEkle(sözcük);
					}
					sözcük = new Sozcuk_05Metin();
					durum = 12;
				} else {
					if (sözcük.tip != SÖZCÜK.TİP_00YOK) {
						sözcükEkle(sözcük);
					}
					if (Character.isDigit(karakter)) {
						durum = 4;
						sözcük = new Sozcuk_03TamSayi(karakter);
					} else if (karakter == '_' || Character.isLetter(karakter)) {
						durum = 3;
						sözcük = new Sozcuk_01Isim(karakter);
					} else if (karakter == '+' || karakter == '-' || karakter == '*' || karakter == '/') {
						durum = 1;
						sözcük = new Sozcuk_02Operator(karakter);
					} else {
						hatalar.append("Bilinmeyen Karakter : '" + karakter + "'\n");
					}
				}
			} else if (durum == 1) {
				// durum 1 : Operatör
				if (karakter == '+' || karakter == '-' || karakter == '*' || karakter == '/' || karakter == '<'
						|| karakter == '>') {
					((Sozcuk_02Operator) sözcük).operatör += karakter;
				} else {
					i--;
					durum = 0;
					sözcükEkle(sözcük);
					sözcük = new Sozcuk(SÖZCÜK.TİP_00YOK);
				}
			} else if (durum == 3) {
				// durum 3 : isim
				if (karakter == '_' || Character.isLetter(karakter) || Character.isDigit(karakter)) {
					((Sozcuk_01Isim) sözcük).isim += karakter;
				} else {
					i--;
					durum = 0;
					sözcükEkle(sözcük);
					sözcük = new Sozcuk(SÖZCÜK.TİP_00YOK);
				}
			} else if (durum == 4) {
				// durum 4,5,6 : Tam sayı ve ondalıklı sayı
				if (Character.isDigit(karakter)) {
					((Sozcuk_03TamSayi) sözcük).tamSayı += karakter;
				} else if (karakter == '.') {
					durum = 5;
					sözcük = new Sozcuk_04OndalikliSayi(((Sozcuk_03TamSayi) sözcük).tamSayı);
					((Sozcuk_04OndalikliSayi) sözcük).ondalıklıSayı += karakter;
				} else if (karakter == '_' || Character.isLetter(karakter)) {
					durum = 3;
					sözcük = new Sozcuk_01Isim(((Sozcuk_03TamSayi) sözcük).tamSayı + karakter);
				} else {
					i--;
					durum = 0;
				}
			} else if (durum == 5) {
				// durum 4,5,6 : Tam sayı ve ondalıklı sayı
				if (Character.isDigit(karakter)) {
					durum = 6;
					((Sozcuk_04OndalikliSayi) sözcük).ondalıklıSayı += karakter;
				} else {
					i -= 2;
					sözcük = new Sozcuk_03TamSayi(((Sozcuk_04OndalikliSayi) sözcük).ondalıklıSayı.substring(0,
							((Sozcuk_04OndalikliSayi) sözcük).ondalıklıSayı.length() - 1));
					durum = 0;
				}
			} else if (durum == 6) {
				// durum 4,5,6 : Tam sayı ve ondalıklı sayı
				if (Character.isDigit(karakter)) {
					((Sozcuk_04OndalikliSayi) sözcük).ondalıklıSayı += karakter;
				} else {
					i--;
					durum = 0;
				}
			} else if (durum == 7) {
				// durum 7,8,9,10 : Açıklamalar
				if (karakter == '\n') {
					i--;
					durum = 0;
				} else if (karakter == '\'') {
					durum = 9;
				} else {
					durum = 8;
				}
			} else if (durum == 8) {
				// durum 7,8,9,10 : Açıklamalar
				if (karakter == '\n') {
					i--;
					durum = 0;
				}
			} else if (durum == 9) {
				// durum 7,8,9,10 : Açıklamalar
				if (karakter == '\'') {
					durum = 10;
				}
			} else if (durum == 10) {
				// durum 7,8,9,10 : Açıklamalar
				if (karakter == '\'') {
					durum = 0;
				} else {
					durum = 9;
				}
			} else if (durum == 11) {
				// durum 11 : Özellik
				if (karakter == '\n') {
					if (sözcük.tip != SÖZCÜK.TİP_00YOK) {
						sözcükEkle(sözcük);
						sözcük = new Sozcuk(SÖZCÜK.TİP_00YOK);
					}
					sözcükEkle(new Sozcuk(SÖZCÜK.TİP_06SATIR_SONU));
					durum = 0;
				} else if (karakter == ' ') {
					if (sözcük.tip != SÖZCÜK.TİP_00YOK) {
						sözcükEkle(sözcük);
						sözcük = new Sozcuk(SÖZCÜK.TİP_00YOK);
					}
				} else {
					if (sözcük.tip == SÖZCÜK.TİP_00YOK) {
						sözcük = new Sozcuk_01Isim(karakter);
					} else {
						((Sozcuk_01Isim) sözcük).isim += karakter;
					}
				}
			} else if (durum == 12) {
				// durum 12,13 : Metin
				if (karakter == '"') {
					durum = 13;
				} else {
					((Sozcuk_05Metin) sözcük).metin += karakter;
				}
			} else if (durum == 13) {
				if (karakter == '"') {
					((Sozcuk_05Metin) sözcük).metin += karakter;
					durum = 12;
				} else {
					i--;
					durum = 0;
					sözcükEkle(sözcük);
					sözcük = new Sozcuk(SÖZCÜK.TİP_00YOK);
				}
			}
		}

		if (durum != 0) {
			hatalar.append("Anormal Sonlanma : Durum : " + durum + "\n");
		}

		StringBuilder sb = new StringBuilder();
		for (Sozcuk[] cümle : tümCumleler) {
			for (Sozcuk kelime : cümle) {
				sb.append(kelime.toString());
				sb.append(" ");
			}
			sb.append("\n");
		}
		String çıktı = sb.toString();
		// System.out.print(çıktı);
		Fonksiyonlar.dosyaKaydet(dosyaAdı + ".2.log", çıktı);
		if (hatalar.length() > 0) {
			return hatalar.toString();
		}
		return tümCumleler;
	}

}
