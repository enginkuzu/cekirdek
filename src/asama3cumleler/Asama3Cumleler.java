package asama3cumleler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import asama2sozcukler.Sozcuk;
import asama2sozcukler.Sozcuk_01Isim;
import asama2sozcukler.Sozcuk_02Operator;
import asama2sozcukler.Sozcuk_03TamSayi;
import asama2sozcukler.Sozcuk_05Metin;
import asama2sozcukler.Sozcuk_06Degisken;
import yardimci.Fonksiyonlar;
import yardimci.Degiskenler;
import yardimci.Degiskenler.SÖZCÜK;

public class Asama3Cumleler {

	private static HashMap<String, Fonksiyon_01OperatorFonksiyon> operatörFonksiyonMap;
	private static HashMap<String, Fonksiyon_02IsimliFonksiyon> isimFonksiyonMap;
	private static Fonksiyon_03AnaFonksiyon anaFonksiyon;
	private static Fonksiyon aktifFonksiyon;
	private static StringBuilder hatalar;

	private static boolean değişkenİsmiKontrol(String değişkenİsmi) {
		// Tüm karakterler : _, a-z, A-Z, 0-9
		for (int i = 0; i < değişkenİsmi.length(); i++) {
			char ch = değişkenİsmi.charAt(i);
			if (!(ch == '_' || Character.isLetterOrDigit(ch))) {
				return true;
			}
		}
		if (Degiskenler.ANAHTAR_KELIMELER.contains(değişkenİsmi)) {
			return true;
		}
		return false;
	}

	private static void hata00_BeklenmeyenHata(String hataDetay) {
		hatalar.append("Beklenmeyen Hata : " + hataDetay + " !!!\n");
	}

	private static void hata01_BilinmeyenDegiskenTipi(String değişkenTipi) {
		hatalar.append("Bilinmeyen Değişken Tipi : " + değişkenTipi + " !!!\n");
	}

	private static void hata02_TanımsızDegisken(String değişkenİsmi) {
		hatalar.append("Tanımsız Değişken : " + değişkenİsmi + " !!!\n");
	}

	private static void hata03_DegiskenİsimÇakışması(String değişkenİsmi) {
		hatalar.append("Çok Defa Değişken Tanımlama : " + değişkenİsmi + " !!!\n");
	}

	private static void hata04_DegiskenİsmiUygunDeğil(String değişkenİsmi) {
		hatalar.append("Değişken İsmi Uygun Değil : " + değişkenİsmi + " !!!\n");
	}

	private static void hata04_TanımsızOperatör(String operatörKullanımı) {
		hatalar.append("Tanımsız Operatör : " + operatörKullanımı + " !!!\n");
	}

	private static void hata05_SayıVeriTipineSığmıyor(String sayı, String veriTipi) {
		hatalar.append("Sayı Veri Tipine Sığmıyor : " + sayı + " : " + veriTipi + " !!!\n");
	}

	private static void hata06_TanımsızFonksiyon(String fonksiyonİsmi) {
		hatalar.append("Tanımsız Fonksiyon : " + fonksiyonİsmi + " !!!\n");
	}

	private static Sozcuk_06Degisken değişkeneDönüştür(Sozcuk sozcuk) {
		if (sozcuk.tip == SÖZCÜK.TİP_01İSİM) {
			String isim = ((Sozcuk_01Isim) sozcuk).isim;
			if (!aktifFonksiyon.değişkenİsimMap.containsKey(isim)) {
				hata02_TanımsızDegisken(isim);
				return null;
			}
			return aktifFonksiyon.değişkenİsimMap.get(isim);
		} else if (sozcuk.tip == SÖZCÜK.TİP_03TAM_SAYI) {
			String tamSayı = ((Sozcuk_03TamSayi) sozcuk).tamSayı;
			if (Fonksiyonlar.parseLong(tamSayı) == null) {
				hata05_SayıVeriTipineSığmıyor(tamSayı, Degiskenler.STRING_i64);
				return null;
			}
			int kaynakDeğişkenNo = --aktifFonksiyon.geçiciDegiskenNo;
			Sozcuk_06Degisken kaynakDeğişken = new Sozcuk_06Degisken(kaynakDeğişkenNo, null, Degiskenler.ID_i64);
			aktifFonksiyon.değişkenNoMap.put(kaynakDeğişkenNo, kaynakDeğişken);
			Cumle_02GeciciDegiskenYeni yeniCümle1 = new Cumle_02GeciciDegiskenYeni(kaynakDeğişkenNo,
					Degiskenler.ID_i64);
			aktifFonksiyon.cümleler.add(yeniCümle1);
			Cumle_11SabitAtama yeniCümle2 = new Cumle_11SabitAtama(kaynakDeğişkenNo, tamSayı, Degiskenler.ID_i64);
			aktifFonksiyon.cümleler.add(yeniCümle2);
			return kaynakDeğişken;
		} else if (sozcuk.tip == SÖZCÜK.TİP_05METİN) {
			String metin = ((Sozcuk_05Metin) sozcuk).metin;
			int kaynakDeğişkenNo = ++aktifFonksiyon.gerçekDegiskenNo;
			Sozcuk_06Degisken kaynakDeğişken = new Sozcuk_06Degisken(kaynakDeğişkenNo, null, Degiskenler.ID_str);
			aktifFonksiyon.değişkenNoMap.put(kaynakDeğişkenNo, kaynakDeğişken);
			Cumle_02GeciciDegiskenYeni yeniCümle1 = new Cumle_02GeciciDegiskenYeni(kaynakDeğişkenNo,
					Degiskenler.ID_str);
			aktifFonksiyon.cümleler.add(yeniCümle1);
			Cumle_11SabitAtama yeniCümle2 = new Cumle_11SabitAtama(kaynakDeğişkenNo, metin, Degiskenler.ID_str);
			aktifFonksiyon.cümleler.add(yeniCümle2);
			return kaynakDeğişken;
		} else {
			return (Sozcuk_06Degisken) sozcuk;
		}
	}

	private static boolean satırıİşle(Sozcuk_06Degisken hedefDeğişken, Sozcuk[] cümle, int minIndex, int maxIndex) {
		while (minIndex != maxIndex) {
			//
			// System.out.println("min:" + minIndex + ", max:" + maxIndex);
			// for (int i = minIndex; i <= maxIndex; i++) {
			// System.out.println(cümle[i]);
			// }
			// System.out.println("---");
			//
			if (minIndex + 2 <= maxIndex) {
				//
				Sozcuk sozcuk0 = cümle[minIndex];
				Sozcuk sozcuk1 = cümle[minIndex + 1];
				Sozcuk sozcuk2 = cümle[minIndex + 2];
				//
				if ((sozcuk0.tip == SÖZCÜK.TİP_01İSİM || sozcuk0.tip == SÖZCÜK.TİP_03TAM_SAYI
						|| sozcuk0.tip == SÖZCÜK.TİP_05METİN || sozcuk0.tip == SÖZCÜK.TİP_99DEĞİŞKEN)
						&& sozcuk1.tip == SÖZCÜK.TİP_08NOKTA && sozcuk2.tip == SÖZCÜK.TİP_01İSİM) {
					// -----------------
					// sayı1.printhn
					// 123.printhn
					// -----------------
					Sozcuk_06Degisken kaynakDeğişken = değişkeneDönüştür(sozcuk0);
					if (kaynakDeğişken == null)
						return false;
					//
					String fonksiyonİsmi = ((Sozcuk_01Isim) sozcuk2).isim;
					String anahtar = fonksiyonİsmi + " " + kaynakDeğişken.değişkenTipiId;
					if (!isimFonksiyonMap.containsKey(anahtar)) {
						hata06_TanımsızFonksiyon(fonksiyonİsmi);
						return false;
					}
					Fonksiyon_02IsimliFonksiyon fonksiyon = isimFonksiyonMap.get(anahtar);
					//
					int yeniDeğişkenNo = --aktifFonksiyon.geçiciDegiskenNo;
					Sozcuk_06Degisken yeniDeğişken = new Sozcuk_06Degisken(yeniDeğişkenNo, null, Degiskenler.ID_i64);
					aktifFonksiyon.değişkenNoMap.put(yeniDeğişkenNo, yeniDeğişken);
					Cumle_02GeciciDegiskenYeni yeniCümle1 = new Cumle_02GeciciDegiskenYeni(yeniDeğişkenNo,
							Degiskenler.ID_i64);
					aktifFonksiyon.cümleler.add(yeniCümle1);
					Cumle_05FonksiyonCagrisi yeniCümle2 = new Cumle_05FonksiyonCagrisi(yeniDeğişkenNo, fonksiyonİsmi,
							fonksiyon.fonksiyonId, kaynakDeğişken.değişkenNo);
					aktifFonksiyon.cümleler.add(yeniCümle2);
					//
					cümle[minIndex + 2] = yeniDeğişken;
					minIndex += 2;
					//
				} else if ((sozcuk0.tip == SÖZCÜK.TİP_01İSİM || sozcuk0.tip == SÖZCÜK.TİP_03TAM_SAYI
						|| sozcuk0.tip == SÖZCÜK.TİP_05METİN || sozcuk0.tip == SÖZCÜK.TİP_99DEĞİŞKEN)
						&& sozcuk1.tip == SÖZCÜK.TİP_02OPERATÖR
						&& (sozcuk2.tip == SÖZCÜK.TİP_01İSİM || sozcuk2.tip == SÖZCÜK.TİP_03TAM_SAYI
								|| sozcuk2.tip == SÖZCÜK.TİP_05METİN || sozcuk2.tip == SÖZCÜK.TİP_99DEĞİŞKEN)) {
					// -----------------
					// sayı1 + sayı2
					// 123 + sayı2
					// sayı1 + 123
					// 123 + 123
					// -----------------
					Sozcuk_06Degisken kaynakDeğişken1 = değişkeneDönüştür(sozcuk0);
					if (kaynakDeğişken1 == null)
						return false;
					//
					Sozcuk_06Degisken kaynakDeğişken2 = değişkeneDönüştür(sozcuk2);
					if (kaynakDeğişken2 == null)
						return false;
					//
					String operatör = ((Sozcuk_02Operator) sozcuk1).operatör;
					String anahtar = kaynakDeğişken1.değişkenTipiId + operatör + kaynakDeğişken2.değişkenTipiId;
					if (!operatörFonksiyonMap.containsKey(anahtar)) {
						hata04_TanımsızOperatör(Degiskenler.TİP_MAP_ID_STR.get(kaynakDeğişken1.değişkenTipiId) + " "
								+ operatör + " " + Degiskenler.TİP_MAP_ID_STR.get(kaynakDeğişken2.değişkenTipiId));
						return false;
					}
					Fonksiyon_01OperatorFonksiyon fonksiyon = operatörFonksiyonMap.get(anahtar);
					//
					int yeniDeğişkenNo = --aktifFonksiyon.geçiciDegiskenNo;
					int yeniDeğişkenTipId = fonksiyon.sonuçTipId;
					Sozcuk_06Degisken yeniDeğişken = new Sozcuk_06Degisken(yeniDeğişkenNo, null, yeniDeğişkenTipId);
					aktifFonksiyon.değişkenNoMap.put(yeniDeğişkenNo, yeniDeğişken);
					Cumle_02GeciciDegiskenYeni yeniCümle1 = new Cumle_02GeciciDegiskenYeni(yeniDeğişkenNo,
							yeniDeğişkenTipId);
					aktifFonksiyon.cümleler.add(yeniCümle1);
					Cumle_04OperatorIslemi yeniCümle2 = new Cumle_04OperatorIslemi(yeniDeğişkenNo, operatör,
							kaynakDeğişken1.değişkenNo, kaynakDeğişken2.değişkenNo);
					aktifFonksiyon.cümleler.add(yeniCümle2);
					//
					cümle[minIndex + 2] = yeniDeğişken;
					minIndex += 2;
					//
				} else {
					hata00_BeklenmeyenHata("1");
					return false;
				}
			} else {
				hata00_BeklenmeyenHata("2");
				return false;
			}
		}
		Sozcuk sozcuk = cümle[minIndex];
		if (sozcuk.tip == SÖZCÜK.TİP_01İSİM) {
			String kaynakDeğişkenİsim = ((Sozcuk_01Isim) sozcuk).isim;
			if (!aktifFonksiyon.değişkenİsimMap.containsKey(kaynakDeğişkenİsim)) {
				hata02_TanımsızDegisken(kaynakDeğişkenİsim);
				return false;
			}
			Sozcuk_06Degisken kaynakDeğişken = aktifFonksiyon.değişkenİsimMap.get(kaynakDeğişkenİsim);
			if (hedefDeğişken.değişkenTipiId == 0) {
				hedefDeğişken.değişkenTipiId = kaynakDeğişken.değişkenTipiId;
				Cumle_01DegiskenYeni yeniCümle1 = new Cumle_01DegiskenYeni(hedefDeğişken.değişkenNo,
						hedefDeğişken.değişkenİsim, hedefDeğişken.değişkenTipiId);
				aktifFonksiyon.cümleler.add(yeniCümle1);
			}
			Cumle_06DegiskenAtama yeniCümle2 = new Cumle_06DegiskenAtama(kaynakDeğişken.değişkenNo,
					hedefDeğişken.değişkenNo);
			aktifFonksiyon.cümleler.add(yeniCümle2);
			return true;
		} else if (sozcuk.tip == SÖZCÜK.TİP_03TAM_SAYI) {
			String tamSayı = ((Sozcuk_03TamSayi) sozcuk).tamSayı;
			if (Fonksiyonlar.parseLong(tamSayı) == null) {
				hata05_SayıVeriTipineSığmıyor(tamSayı, Degiskenler.STRING_i64);
				return false;
			}
			int değişkenTipiId = Degiskenler.ID_i64;
			if (hedefDeğişken.değişkenTipiId == 0) {
				hedefDeğişken.değişkenTipiId = değişkenTipiId;
				Cumle_01DegiskenYeni yeniCümle1 = new Cumle_01DegiskenYeni(hedefDeğişken.değişkenNo,
						hedefDeğişken.değişkenİsim, hedefDeğişken.değişkenTipiId);
				aktifFonksiyon.cümleler.add(yeniCümle1);
			}
			Cumle_11SabitAtama yeniCümle2 = new Cumle_11SabitAtama(hedefDeğişken.değişkenNo, tamSayı, değişkenTipiId);
			aktifFonksiyon.cümleler.add(yeniCümle2);
			return true;
		} else if (sozcuk.tip == SÖZCÜK.TİP_05METİN) {
			String metin = ((Sozcuk_05Metin) sozcuk).metin;
			int değişkenTipiId = Degiskenler.ID_str;
			if (hedefDeğişken.değişkenTipiId == 0) {
				hedefDeğişken.değişkenTipiId = değişkenTipiId;
				Cumle_01DegiskenYeni yeniCümle1 = new Cumle_01DegiskenYeni(hedefDeğişken.değişkenNo,
						hedefDeğişken.değişkenİsim, hedefDeğişken.değişkenTipiId);
				aktifFonksiyon.cümleler.add(yeniCümle1);
			}
			Cumle_11SabitAtama yeniCümle2 = new Cumle_11SabitAtama(hedefDeğişken.değişkenNo, metin, değişkenTipiId);
			aktifFonksiyon.cümleler.add(yeniCümle2);
			return true;
		} else if (sozcuk.tip == SÖZCÜK.TİP_99DEĞİŞKEN) {
			if (hedefDeğişken != null) {
				Sozcuk_06Degisken kaynakDeğişken = (Sozcuk_06Degisken) sozcuk;
				if (hedefDeğişken.değişkenTipiId == 0) {
					hedefDeğişken.değişkenTipiId = kaynakDeğişken.değişkenTipiId;
					Cumle_01DegiskenYeni yeniCümle1 = new Cumle_01DegiskenYeni(hedefDeğişken.değişkenNo,
							hedefDeğişken.değişkenİsim, hedefDeğişken.değişkenTipiId);
					aktifFonksiyon.cümleler.add(yeniCümle1);
				}
				Cumle_06DegiskenAtama yeniCümle2 = new Cumle_06DegiskenAtama(kaynakDeğişken.değişkenNo,
						hedefDeğişken.değişkenNo);
				aktifFonksiyon.cümleler.add(yeniCümle2);
			}
			return true;
		} else {
			hata00_BeklenmeyenHata("3");
			return false;
		}
	}

	public static Object işle(ArrayList<Sozcuk[]> tümCumleler, String dosyaAdı) {

		operatörFonksiyonMap = new HashMap<>();
		isimFonksiyonMap = new HashMap<>();
		anaFonksiyon = new Fonksiyon_03AnaFonksiyon();
		aktifFonksiyon = anaFonksiyon;
		hatalar = new StringBuilder();

		for (Sozcuk[] cümle : tümCumleler) {
			if (cümle[0].tip == SÖZCÜK.TİP_13ÖZELLİK && cümle[cümle.length - 1].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				// Fonksiyon Özelliği
				HashSet<String> özellikSet = new HashSet<String>();
				for (int i = 2; i < cümle.length - 1; i++) {
					özellikSet.add(((Sozcuk_01Isim) cümle[i]).isim);
				}
				aktifFonksiyon.özellikMap.put(((Sozcuk_01Isim) cümle[1]).isim, özellikSet);
			} else if (cümle[1].tip == SÖZCÜK.TİP_14AÇ_PARANTEZ) {
				// Normal Fonksiyon Başlangıcı
				Fonksiyon_02IsimliFonksiyon isimFonksiyon = new Fonksiyon_02IsimliFonksiyon(aktifFonksiyon.özellikMap);
				isimFonksiyon.isim = ((Sozcuk_01Isim) cümle[0]).isim;
				if (cümle.length > 5 && cümle[7].tip == SÖZCÜK.TİP_15KAPA_PARANTEZ) {
					isimFonksiyon.değişken1İsim = ((Sozcuk_01Isim) cümle[2]).isim;
					String değişkenTipi = ((Sozcuk_01Isim) cümle[4]).isim;
					if (!Degiskenler.TİP_MAP_STR_ID.containsKey(değişkenTipi)) {
						hata01_BilinmeyenDegiskenTipi(değişkenTipi);
						continue;
					}
					isimFonksiyon.değişken1TipId = Degiskenler.TİP_MAP_STR_ID.get(değişkenTipi);
					isimFonksiyon.değişken1TipAssembly = ((Sozcuk_01Isim) cümle[6]).isim;
				}
				if (cümle.length > 5 && cümle[cümle.length - 8].tip == SÖZCÜK.TİP_15KAPA_PARANTEZ) {
					String sonuçDegiskenTipi = ((Sozcuk_01Isim) cümle[cümle.length - 5]).isim;
					if (!Degiskenler.TİP_MAP_STR_ID.containsKey(sonuçDegiskenTipi)) {
						hata01_BilinmeyenDegiskenTipi(sonuçDegiskenTipi);
						continue;
					}
					isimFonksiyon.sonuçİsim = ((Sozcuk_01Isim) cümle[cümle.length - 7]).isim;
					isimFonksiyon.sonuçTipId = Degiskenler.TİP_MAP_STR_ID.get(sonuçDegiskenTipi);
					isimFonksiyon.sonuçTipAssembly = ((Sozcuk_01Isim) cümle[cümle.length - 3]).isim;
				}
				aktifFonksiyon.özellikMap = new HashMap<>();
				aktifFonksiyon = isimFonksiyon;
				String anahtar = isimFonksiyon.isim + " " + isimFonksiyon.değişken1TipId;
				isimFonksiyonMap.put(anahtar, isimFonksiyon);
			} else if (cümle[0].tip == SÖZCÜK.TİP_01İSİM && ((Sozcuk_01Isim) cümle[0]).isim.equals("inline")) {
				// Inline Fonksiyon Başlangıcı
				String değişken1Tip = ((Sozcuk_01Isim) cümle[5]).isim;
				String değişken2Tip = ((Sozcuk_01Isim) cümle[11]).isim;
				String sonucTip = ((Sozcuk_01Isim) cümle[15]).isim;
				if (!Degiskenler.TİP_MAP_STR_ID.containsKey(değişken1Tip)) {
					hata01_BilinmeyenDegiskenTipi(değişken1Tip);
					continue;
				} else if (!Degiskenler.TİP_MAP_STR_ID.containsKey(değişken2Tip)) {
					hata01_BilinmeyenDegiskenTipi(değişken2Tip);
					continue;
				} else if (!Degiskenler.TİP_MAP_STR_ID.containsKey(sonucTip)) {
					hata01_BilinmeyenDegiskenTipi(sonucTip);
					continue;
				}
				Fonksiyon_01OperatorFonksiyon operatörFonksiyon = new Fonksiyon_01OperatorFonksiyon(
						aktifFonksiyon.özellikMap);
				operatörFonksiyon.inline = true;
				operatörFonksiyon.öncelik = ((Sozcuk_01Isim) cümle[1]).isim.equals("p1") ? 1 : 2;
				operatörFonksiyon.değişken1İsim = ((Sozcuk_01Isim) cümle[3]).isim;
				operatörFonksiyon.değişken1TipId = Degiskenler.TİP_MAP_STR_ID.get(değişken1Tip);
				operatörFonksiyon.operatör = ((Sozcuk_02Operator) cümle[7]).operatör;
				operatörFonksiyon.değişken2İsim = ((Sozcuk_01Isim) cümle[9]).isim;
				operatörFonksiyon.değişken2TipId = Degiskenler.TİP_MAP_STR_ID.get(değişken2Tip);
				operatörFonksiyon.sonuçİsim = ((Sozcuk_01Isim) cümle[13]).isim;
				operatörFonksiyon.sonuçTipId = Degiskenler.TİP_MAP_STR_ID.get(sonucTip);
				aktifFonksiyon.özellikMap = new HashMap<>();
				aktifFonksiyon = operatörFonksiyon;
				//
				String anahtar = operatörFonksiyon.değişken1TipId + operatörFonksiyon.operatör
						+ operatörFonksiyon.değişken2TipId;
				operatörFonksiyonMap.put(anahtar, operatörFonksiyon);
			} else if (aktifFonksiyon.özellikMap.containsKey("asm-data")) {
				// Assembly Data Kodu
				aktifFonksiyon.cümleler.add(new Cumle_08MakineDiliVeri(((Sozcuk_05Metin) cümle[0]).metin));
				aktifFonksiyon.özellikMap = new HashMap<>();
			} else if (aktifFonksiyon.özellikMap.containsKey("asm-rodata")) {
				// Assembly RO Data Kodu
				aktifFonksiyon.cümleler.add(new Cumle_09MakineDiliSabitVeri(((Sozcuk_05Metin) cümle[0]).metin));
				aktifFonksiyon.özellikMap = new HashMap<>();
			} else if (aktifFonksiyon.özellikMap.containsKey("asm-bssdata")) {
				// Assembly BSS Kodu
				aktifFonksiyon.cümleler.add(new Cumle_10MakineDiliSembol(((Sozcuk_05Metin) cümle[0]).metin));
				aktifFonksiyon.özellikMap = new HashMap<>();
			} else if (aktifFonksiyon.özellikMap.containsKey("asm")) {
				// Assembly Kodu
				aktifFonksiyon.cümleler.add(new Cumle_07MakineDiliKod(((Sozcuk_05Metin) cümle[0]).metin));
				aktifFonksiyon.özellikMap = new HashMap<>();
			} else if (cümle[0].tip == SÖZCÜK.TİP_17KAPA_SÜSLÜ && cümle[1].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				// Fonksiyon Sonu
				aktifFonksiyon = anaFonksiyon;
			} else if (cümle.length == 4 && cümle[0].tip == SÖZCÜK.TİP_01İSİM
					&& (cümle[1].tip == SÖZCÜK.TİP_09ATAMA_SOLA || cümle[1].tip == SÖZCÜK.TİP_11TANIMLAMA_SOLA)
					&& (cümle[2].tip == SÖZCÜK.TİP_01İSİM || cümle[2].tip == SÖZCÜK.TİP_03TAM_SAYI
							|| cümle[2].tip == SÖZCÜK.TİP_05METİN)
					&& cümle[3].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				// sayı2 < sayı1;
				// sayı2 < 123;
				// sayı <: sayı1;
				// sayı <: 123;
				Sozcuk_06Degisken hedefDeğişken;
				if (cümle[1].tip == SÖZCÜK.TİP_09ATAMA_SOLA) {
					String isimHedef = ((Sozcuk_01Isim) cümle[0]).isim;
					if (!aktifFonksiyon.değişkenİsimMap.containsKey(isimHedef)) {
						hata02_TanımsızDegisken(isimHedef);
						continue;
					}
					hedefDeğişken = aktifFonksiyon.değişkenİsimMap.get(isimHedef);
				} else {
					String isimHedef = ((Sozcuk_01Isim) cümle[0]).isim;
					if (değişkenİsmiKontrol(isimHedef)) {
						hata04_DegiskenİsmiUygunDeğil(isimHedef);
						continue;
					} else if (aktifFonksiyon.değişkenİsimMap.containsKey(isimHedef)) {
						hata03_DegiskenİsimÇakışması(isimHedef);
						continue;
					}
					int hedefDeğişkenNo = ++aktifFonksiyon.gerçekDegiskenNo;
					hedefDeğişken = new Sozcuk_06Degisken(hedefDeğişkenNo, isimHedef, 0);
					aktifFonksiyon.değişkenNoMap.put(hedefDeğişkenNo, hedefDeğişken);
					aktifFonksiyon.değişkenİsimMap.put(isimHedef, hedefDeğişken);
				}
				//
				satırıİşle(hedefDeğişken, cümle, 2, 2);
				//
			} else if (cümle.length == 4
					&& (cümle[0].tip == SÖZCÜK.TİP_01İSİM || cümle[0].tip == SÖZCÜK.TİP_03TAM_SAYI
							|| cümle[0].tip == SÖZCÜK.TİP_05METİN)
					&& (cümle[1].tip == SÖZCÜK.TİP_10ATAMA_SAĞA || cümle[1].tip == SÖZCÜK.TİP_12TANIMLAMA_SAĞA)
					&& cümle[2].tip == SÖZCÜK.TİP_01İSİM && cümle[3].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				// sayı1 > sayı2;
				// 123 > sayı2;
				// sayı1 :> sayı;
				// 123 :> sayı;
				Sozcuk_06Degisken hedefDeğişken;
				if (cümle[1].tip == SÖZCÜK.TİP_10ATAMA_SAĞA) {
					String isimHedef = ((Sozcuk_01Isim) cümle[2]).isim;
					if (!aktifFonksiyon.değişkenİsimMap.containsKey(isimHedef)) {
						hata02_TanımsızDegisken(isimHedef);
						continue;
					}
					hedefDeğişken = aktifFonksiyon.değişkenİsimMap.get(isimHedef);
				} else {
					String isimHedef = ((Sozcuk_01Isim) cümle[2]).isim;
					if (değişkenİsmiKontrol(isimHedef)) {
						hata04_DegiskenİsmiUygunDeğil(isimHedef);
						continue;
					} else if (aktifFonksiyon.değişkenİsimMap.containsKey(isimHedef)) {
						hata03_DegiskenİsimÇakışması(isimHedef);
						continue;
					}
					int hedefDeğişkenNo = ++aktifFonksiyon.gerçekDegiskenNo;
					hedefDeğişken = new Sozcuk_06Degisken(hedefDeğişkenNo, isimHedef, 0);
					aktifFonksiyon.değişkenNoMap.put(hedefDeğişkenNo, hedefDeğişken);
					aktifFonksiyon.değişkenİsimMap.put(isimHedef, hedefDeğişken);
				}
				//
				satırıİşle(hedefDeğişken, cümle, 0, 0);
				//
			} else if (cümle.length >= 6 && cümle[cümle.length - 5].tip == SÖZCÜK.TİP_10ATAMA_SAĞA
					&& cümle[cümle.length - 4].tip == SÖZCÜK.TİP_01İSİM
					&& cümle[cümle.length - 3].tip == SÖZCÜK.TİP_07DEĞİŞKEN_TİPİ
					&& cümle[cümle.length - 2].tip == SÖZCÜK.TİP_01İSİM
					&& cümle[cümle.length - 1].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				// sayı1 > sayı:i64;
				// 123 > sayı:i64;
				String hedefDeğişkenİsim = ((Sozcuk_01Isim) cümle[cümle.length - 4]).isim;
				String hedefDeğişkenTipi = ((Sozcuk_01Isim) cümle[cümle.length - 2]).isim;
				if (değişkenİsmiKontrol(hedefDeğişkenİsim)) {
					hata04_DegiskenİsmiUygunDeğil(hedefDeğişkenİsim);
					continue;
				} else if (aktifFonksiyon.değişkenİsimMap.containsKey(hedefDeğişkenİsim)) {
					hata03_DegiskenİsimÇakışması(hedefDeğişkenİsim);
					continue;
				} else if (!Degiskenler.TİP_MAP_STR_ID.containsKey(hedefDeğişkenTipi)) {
					hata01_BilinmeyenDegiskenTipi(hedefDeğişkenTipi);
					continue;
				}
				int hedefDeğişkenNo = ++aktifFonksiyon.gerçekDegiskenNo;
				int değişkenTipiId = Degiskenler.TİP_MAP_STR_ID.get(hedefDeğişkenTipi);
				Sozcuk_06Degisken hedefDeğişken = new Sozcuk_06Degisken(hedefDeğişkenNo, hedefDeğişkenİsim,
						değişkenTipiId);
				aktifFonksiyon.değişkenNoMap.put(hedefDeğişkenNo, hedefDeğişken);
				aktifFonksiyon.değişkenİsimMap.put(hedefDeğişkenİsim, hedefDeğişken);
				Cumle_01DegiskenYeni yeniCümle1 = new Cumle_01DegiskenYeni(hedefDeğişkenNo, hedefDeğişkenİsim,
						değişkenTipiId);
				aktifFonksiyon.cümleler.add(yeniCümle1);
				//
				satırıİşle(hedefDeğişken, cümle, 0, cümle.length - 6);
				//
			} else if (cümle.length >= 6 && cümle[0].tip == SÖZCÜK.TİP_01İSİM
					&& cümle[1].tip == SÖZCÜK.TİP_07DEĞİŞKEN_TİPİ && cümle[2].tip == SÖZCÜK.TİP_01İSİM
					&& cümle[3].tip == SÖZCÜK.TİP_09ATAMA_SOLA
					&& cümle[cümle.length - 1].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				// sayı:i64 < sayı1;
				// sayı:i64 < 123;
				// isim:str < "Engin";
				String hedefDeğişkenİsim = ((Sozcuk_01Isim) cümle[0]).isim;
				String hedefDeğişkenTipi = ((Sozcuk_01Isim) cümle[2]).isim;
				if (değişkenİsmiKontrol(hedefDeğişkenİsim)) {
					hata04_DegiskenİsmiUygunDeğil(hedefDeğişkenİsim);
					continue;
				} else if (aktifFonksiyon.değişkenİsimMap.containsKey(hedefDeğişkenİsim)) {
					hata03_DegiskenİsimÇakışması(hedefDeğişkenİsim);
					continue;
				} else if (!Degiskenler.TİP_MAP_STR_ID.containsKey(hedefDeğişkenTipi)) {
					hata01_BilinmeyenDegiskenTipi(hedefDeğişkenTipi);
					continue;
				}
				int hedefDeğişkenNo = ++aktifFonksiyon.gerçekDegiskenNo;
				int değişkenTipiId = Degiskenler.TİP_MAP_STR_ID.get(hedefDeğişkenTipi);
				Sozcuk_06Degisken hedefDeğişken = new Sozcuk_06Degisken(hedefDeğişkenNo, hedefDeğişkenİsim,
						değişkenTipiId);
				aktifFonksiyon.değişkenNoMap.put(hedefDeğişkenNo, hedefDeğişken);
				aktifFonksiyon.değişkenİsimMap.put(hedefDeğişkenİsim, hedefDeğişken);
				Cumle_01DegiskenYeni yeniCümle1 = new Cumle_01DegiskenYeni(hedefDeğişkenNo, hedefDeğişkenİsim,
						değişkenTipiId);
				aktifFonksiyon.cümleler.add(yeniCümle1);
				//
				satırıİşle(hedefDeğişken, cümle, 4, cümle.length - 2);
				//
			} else if (cümle.length == 4
					&& (cümle[0].tip == SÖZCÜK.TİP_01İSİM || cümle[0].tip == SÖZCÜK.TİP_03TAM_SAYI
							|| cümle[0].tip == SÖZCÜK.TİP_05METİN)
					&& cümle[1].tip == SÖZCÜK.TİP_08NOKTA && cümle[2].tip == SÖZCÜK.TİP_01İSİM
					&& cümle[3].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				// sayı1.printhn;
				// 123.printhn;
				//
				satırıİşle(null, cümle, 0, 2);
				//
			} else if (cümle.length >= 4 && cümle[0].tip == SÖZCÜK.TİP_01İSİM
					&& (cümle[1].tip == SÖZCÜK.TİP_09ATAMA_SOLA || cümle[1].tip == SÖZCÜK.TİP_11TANIMLAMA_SOLA)
					&& cümle[cümle.length - 1].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				// sayı3 < sayı1 + sayı2;
				// sayı3 < 123 + sayı2;
				// sayı3 <: sayı1 + 123;
				// sayı3 <: 123 + 123;
				//
				Sozcuk_06Degisken hedefDeğişken;
				if (cümle[1].tip == SÖZCÜK.TİP_09ATAMA_SOLA) {
					String isimHedef = ((Sozcuk_01Isim) cümle[0]).isim;
					if (!aktifFonksiyon.değişkenİsimMap.containsKey(isimHedef)) {
						hata02_TanımsızDegisken(isimHedef);
						continue;
					}
					hedefDeğişken = aktifFonksiyon.değişkenİsimMap.get(isimHedef);
				} else {
					String isimHedef = ((Sozcuk_01Isim) cümle[0]).isim;
					if (değişkenİsmiKontrol(isimHedef)) {
						hata04_DegiskenİsmiUygunDeğil(isimHedef);
						continue;
					} else if (aktifFonksiyon.değişkenİsimMap.containsKey(isimHedef)) {
						hata03_DegiskenİsimÇakışması(isimHedef);
						continue;
					}
					int hedefDeğişkenNo = ++aktifFonksiyon.gerçekDegiskenNo;
					hedefDeğişken = new Sozcuk_06Degisken(hedefDeğişkenNo, isimHedef, 0);
					aktifFonksiyon.değişkenNoMap.put(hedefDeğişkenNo, hedefDeğişken);
					aktifFonksiyon.değişkenİsimMap.put(isimHedef, hedefDeğişken);
				}
				//
				satırıİşle(hedefDeğişken, cümle, 2, cümle.length - 2);
				//
			} else if (cümle.length >= 4
					&& (cümle[cümle.length - 3].tip == SÖZCÜK.TİP_10ATAMA_SAĞA
							|| cümle[cümle.length - 3].tip == SÖZCÜK.TİP_12TANIMLAMA_SAĞA)
					&& cümle[cümle.length - 2].tip == SÖZCÜK.TİP_01İSİM
					&& cümle[cümle.length - 1].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				// sayı1 + sayı2 > sayı3;
				// 123 + sayı2 > sayı3;
				// sayı1 + 123 :> sayı3;
				// 123 + 123 :> sayı3;
				//
				Sozcuk_06Degisken hedefDeğişken;
				if (cümle[cümle.length - 3].tip == SÖZCÜK.TİP_10ATAMA_SAĞA) {
					String isimHedef = ((Sozcuk_01Isim) cümle[cümle.length - 2]).isim;
					if (!aktifFonksiyon.değişkenİsimMap.containsKey(isimHedef)) {
						hata02_TanımsızDegisken(isimHedef);
						continue;
					}
					hedefDeğişken = aktifFonksiyon.değişkenİsimMap.get(isimHedef);
				} else {
					String isimHedef = ((Sozcuk_01Isim) cümle[cümle.length - 2]).isim;
					if (değişkenİsmiKontrol(isimHedef)) {
						hata04_DegiskenİsmiUygunDeğil(isimHedef);
						continue;
					} else if (aktifFonksiyon.değişkenİsimMap.containsKey(isimHedef)) {
						hata03_DegiskenİsimÇakışması(isimHedef);
						continue;
					}
					int hedefDeğişkenNo = ++aktifFonksiyon.gerçekDegiskenNo;
					hedefDeğişken = new Sozcuk_06Degisken(hedefDeğişkenNo, isimHedef, 0);
					aktifFonksiyon.değişkenNoMap.put(hedefDeğişkenNo, hedefDeğişken);
					aktifFonksiyon.değişkenİsimMap.put(isimHedef, hedefDeğişken);
				}
				//
				satırıİşle(hedefDeğişken, cümle, 0, cümle.length - 4);
				//
			} else {
				// Bilinmeyen Cumle
				hatalar.append("BİLİNMEYEN CÜMLE : ");
				for (Sozcuk c : cümle) {
					hatalar.append(c);
					hatalar.append(" ");
				}
				hatalar.append("!!!\n");
			}
		}

		StringBuilder sb = new StringBuilder();
		for (Fonksiyon_01OperatorFonksiyon operatörFonksiyon : operatörFonksiyonMap.values()) {
			sb.append(operatörFonksiyon.toString());
			sb.append("\n");
			for (Cumle cümle : operatörFonksiyon.cümleler) {
				sb.append("\t");
				sb.append(cümle.toString());
				sb.append("\n");
			}
		}
		for (Fonksiyon_02IsimliFonksiyon isimFonksiyon : isimFonksiyonMap.values()) {
			sb.append(isimFonksiyon.toString());
			sb.append("\n");
			for (Cumle cümle : isimFonksiyon.cümleler) {
				sb.append("\t");
				sb.append(cümle.toString());
				sb.append("\n");
			}
		}
		{
			sb.append(anaFonksiyon.toString());
			sb.append("\n");
			for (Cumle cümle : anaFonksiyon.cümleler) {
				sb.append("\t");
				sb.append(cümle.toString());
				sb.append("\n");
			}
		}
		String metinÇıktı = sb.toString();
		// System.out.print(metinÇıktı);
		Fonksiyonlar.dosyaKaydet(dosyaAdı + ".3.log", metinÇıktı);

		if (hatalar.length() > 0) {
			return hatalar.toString();
		}
		return new Asama3Ciktisi(operatörFonksiyonMap, isimFonksiyonMap, anaFonksiyon);
	}

}
