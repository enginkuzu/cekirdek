package asama3cumleler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import asama2sozcukler.Sozcuk;
import asama2sozcukler.Sozcuk_01Isim;
import asama2sozcukler.Sozcuk_02Operator;
import asama2sozcukler.Sozcuk_03TamSayi;
import asama2sozcukler.Sozcuk_05Metin;
import yardimci.Fonksiyonlar;
import yardimci.Degiskenler;
import yardimci.Degiskenler.SÖZCÜK;

public class Asama3Cumleler {

	private static HashMap<String, Fonksiyon_01OperatorFonksiyon> operatörFonksiyonMap;
	private static HashMap<String, Fonksiyon_02IsimliFonksiyon> isimFonksiyonMap;
	private static Fonksiyon_03AnaFonksiyon anaFonksiyon;
	private static Fonksiyon aktifFonksiyon;
	private static StringBuilder hatalar;

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
				if (!Degiskenler.TİP_MAP_STR_ID.containsKey(değişken1Tip)) {
					hata01_BilinmeyenDegiskenTipi(değişken1Tip);
					continue;
				} else if (!Degiskenler.TİP_MAP_STR_ID.containsKey(değişken2Tip)) {
					hata01_BilinmeyenDegiskenTipi(değişken2Tip);
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
				operatörFonksiyon.sonuçTip = ((Sozcuk_01Isim) cümle[15]).isim;
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
			} else if (cümle.length == 4 && cümle[0].tip == SÖZCÜK.TİP_01İSİM && cümle[1].tip == SÖZCÜK.TİP_09ATAMA_SOLA
					&& (cümle[2].tip == SÖZCÜK.TİP_01İSİM || cümle[2].tip == SÖZCÜK.TİP_03TAM_SAYI)
					&& cümle[3].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				// sayı2 < sayı1;
				// sayı2 < 123;
				String isimHedef = ((Sozcuk_01Isim) cümle[0]).isim;
				if (!aktifFonksiyon.değişkenİsimMap.containsKey(isimHedef)) {
					hata02_TanımsızDegisken(isimHedef);
					continue;
				}
				if (cümle[2].tip == SÖZCÜK.TİP_01İSİM) {
					String isimKaynak = ((Sozcuk_01Isim) cümle[2]).isim;
					if (!aktifFonksiyon.değişkenİsimMap.containsKey(isimKaynak)) {
						hata02_TanımsızDegisken(isimKaynak);
						continue;
					}
					Cumle_06DegiskenAtama yeniCümle = new Cumle_06DegiskenAtama(
							aktifFonksiyon.değişkenİsimMap.get(isimKaynak).değişkenNo,
							aktifFonksiyon.değişkenİsimMap.get(isimHedef).değişkenNo);
					aktifFonksiyon.cümleler.add(yeniCümle);
				} else {
					String tamSayı = ((Sozcuk_03TamSayi) cümle[2]).tamSayı;
					if (Fonksiyonlar.parseLong(tamSayı) == null) {
						hata05_SayıVeriTipineSığmıyor(tamSayı, Degiskenler.STRING_i64);
						continue;
					}
					Cumle_11SabitAtama yeniCümle = new Cumle_11SabitAtama(
							aktifFonksiyon.değişkenİsimMap.get(isimHedef).değişkenNo, tamSayı, Degiskenler.ID_i64);
					aktifFonksiyon.cümleler.add(yeniCümle);
				}
			} else if (cümle.length == 4 && (cümle[0].tip == SÖZCÜK.TİP_01İSİM || cümle[0].tip == SÖZCÜK.TİP_03TAM_SAYI)
					&& cümle[1].tip == SÖZCÜK.TİP_10ATAMA_SAĞA && cümle[2].tip == SÖZCÜK.TİP_01İSİM
					&& cümle[3].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				// sayı1 > sayı2;
				// 123 > sayı2;
				String isimHedef = ((Sozcuk_01Isim) cümle[2]).isim;
				if (!aktifFonksiyon.değişkenİsimMap.containsKey(isimHedef)) {
					hata02_TanımsızDegisken(isimHedef);
					continue;
				}
				if (cümle[0].tip == SÖZCÜK.TİP_01İSİM) {
					String isimKaynak = ((Sozcuk_01Isim) cümle[0]).isim;
					if (!aktifFonksiyon.değişkenİsimMap.containsKey(isimKaynak)) {
						hata02_TanımsızDegisken(isimKaynak);
						continue;
					}
					Cumle_06DegiskenAtama cümleYeni = new Cumle_06DegiskenAtama(
							aktifFonksiyon.değişkenİsimMap.get(isimKaynak).değişkenNo,
							aktifFonksiyon.değişkenİsimMap.get(isimHedef).değişkenNo);
					aktifFonksiyon.cümleler.add(cümleYeni);
				} else {
					String tamSayı = ((Sozcuk_03TamSayi) cümle[0]).tamSayı;
					if (Fonksiyonlar.parseLong(tamSayı) == null) {
						hata05_SayıVeriTipineSığmıyor(tamSayı, Degiskenler.STRING_i64);
						continue;
					}
					Cumle_11SabitAtama cümleYeni = new Cumle_11SabitAtama(
							aktifFonksiyon.değişkenİsimMap.get(isimHedef).değişkenNo, tamSayı, Degiskenler.ID_i64);
					aktifFonksiyon.cümleler.add(cümleYeni);
				}
			} else if (cümle.length == 4 && (cümle[0].tip == SÖZCÜK.TİP_01İSİM || cümle[0].tip == SÖZCÜK.TİP_03TAM_SAYI)
					&& cümle[1].tip == SÖZCÜK.TİP_08NOKTA && cümle[2].tip == SÖZCÜK.TİP_01İSİM
					&& cümle[3].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				// sayı1.printhn;
				// 123.printhn;
				int değişkenNo;
				int değişkenTipiId;
				if (cümle[0].tip == SÖZCÜK.TİP_01İSİM) {
					String isim = ((Sozcuk_01Isim) cümle[0]).isim;
					if (!aktifFonksiyon.değişkenİsimMap.containsKey(isim)) {
						hata02_TanımsızDegisken(isim);
						continue;
					}
					Degisken değişken = aktifFonksiyon.değişkenİsimMap.get(isim);
					değişkenNo = değişken.değişkenNo;
					değişkenTipiId = değişken.değişkenTipiId;
				} else {
					String tamSayı = ((Sozcuk_03TamSayi) cümle[0]).tamSayı;
					if (Fonksiyonlar.parseLong(tamSayı) == null) {
						hata05_SayıVeriTipineSığmıyor(tamSayı, Degiskenler.STRING_i64);
						continue;
					}
					aktifFonksiyon.geçiciDegiskenNo--;
					aktifFonksiyon.değişkenNoMap.put(aktifFonksiyon.geçiciDegiskenNo,
							new Degisken(aktifFonksiyon.geçiciDegiskenNo, null, Degiskenler.ID_i64));
					Cumle_02GeciciDegiskenYeni komut1 = new Cumle_02GeciciDegiskenYeni(aktifFonksiyon.geçiciDegiskenNo,
							Degiskenler.ID_i64);
					aktifFonksiyon.cümleler.add(komut1);
					Cumle_11SabitAtama komut = new Cumle_11SabitAtama(aktifFonksiyon.geçiciDegiskenNo, tamSayı,
							Degiskenler.ID_i64);
					aktifFonksiyon.cümleler.add(komut);
					değişkenNo = komut.değişkenNo;
					değişkenTipiId = komut.sabitVeriTipiId;
				}
				String fonksiyonİsmi = ((Sozcuk_01Isim) cümle[2]).isim;
				String anahtar = fonksiyonİsmi + " " + değişkenTipiId;
				if (!isimFonksiyonMap.containsKey(anahtar)) {
					hata06_TanımsızFonksiyon(fonksiyonİsmi);
					continue;
				}
				Fonksiyon_02IsimliFonksiyon fonksiyon = isimFonksiyonMap.get(anahtar);
				aktifFonksiyon.geçiciDegiskenNo--;
				aktifFonksiyon.değişkenNoMap.put(aktifFonksiyon.geçiciDegiskenNo,
						new Degisken(aktifFonksiyon.geçiciDegiskenNo, null, Degiskenler.ID_i64));
				Cumle_02GeciciDegiskenYeni komut1 = new Cumle_02GeciciDegiskenYeni(aktifFonksiyon.geçiciDegiskenNo,
						Degiskenler.ID_i64);
				aktifFonksiyon.cümleler.add(komut1);
				Cumle_05FonksiyonCagrisi komut = new Cumle_05FonksiyonCagrisi(aktifFonksiyon.geçiciDegiskenNo,
						fonksiyonİsmi, fonksiyon.fonksiyonId, değişkenNo);
				aktifFonksiyon.cümleler.add(komut);
			} else if (cümle.length == 6 && cümle[0].tip == SÖZCÜK.TİP_01İSİM
					&& cümle[1].tip == SÖZCÜK.TİP_07DEĞİŞKEN_TİPİ && cümle[2].tip == SÖZCÜK.TİP_01İSİM
					&& cümle[3].tip == SÖZCÜK.TİP_09ATAMA_SOLA && (cümle[4].tip == SÖZCÜK.TİP_01İSİM
							|| cümle[4].tip == SÖZCÜK.TİP_03TAM_SAYI || cümle[4].tip == SÖZCÜK.TİP_05METİN)
					&& cümle[5].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				// sayı:i64 < sayı1;
				// sayı:i64 < 123;
				// isim:str < "Engin";
				String değişkenİsim = ((Sozcuk_01Isim) cümle[0]).isim;
				String değişkenTipi = ((Sozcuk_01Isim) cümle[2]).isim;
				if (değişkenİsmiKontrol(değişkenİsim)) {
					hata04_DegiskenİsmiUygunDeğil(değişkenİsim);
					continue;
				} else if (aktifFonksiyon.değişkenİsimMap.containsKey(değişkenİsim)) {
					hata03_DegiskenİsimÇakışması(değişkenİsim);
					continue;
				} else if (!Degiskenler.TİP_MAP_STR_ID.containsKey(değişkenTipi)) {
					hata01_BilinmeyenDegiskenTipi(değişkenTipi);
					continue;
				}
				aktifFonksiyon.gerçekDegiskenNo++;
				int değişkenTipiId = Degiskenler.TİP_MAP_STR_ID.get(değişkenTipi);
				Degisken değişken = new Degisken(aktifFonksiyon.gerçekDegiskenNo, değişkenİsim, değişkenTipiId);
				aktifFonksiyon.değişkenNoMap.put(değişken.değişkenNo, değişken);
				aktifFonksiyon.değişkenİsimMap.put(değişkenİsim, değişken);
				aktifFonksiyon.cümleler
						.add(new Cumle_01DegiskenYeni(değişken.değişkenNo, değişkenİsim, değişken.değişkenTipiId));
				if (cümle[4].tip == SÖZCÜK.TİP_01İSİM) {
					String isim = ((Sozcuk_01Isim) cümle[4]).isim;
					if (!aktifFonksiyon.değişkenİsimMap.containsKey(isim)) {
						hata02_TanımsızDegisken(isim);
						continue;
					}
					int değişkenNo = aktifFonksiyon.değişkenİsimMap.get(isim).değişkenNo;
					aktifFonksiyon.cümleler.add(new Cumle_06DegiskenAtama(değişkenNo, değişken.değişkenNo));
				} else if (cümle[4].tip == SÖZCÜK.TİP_03TAM_SAYI) {
					String tamSayı = ((Sozcuk_03TamSayi) cümle[4]).tamSayı;
					if (Fonksiyonlar.parseLong(tamSayı) == null) {
						hata05_SayıVeriTipineSığmıyor(tamSayı, Degiskenler.STRING_i64);
						continue;
					}
					aktifFonksiyon.cümleler
							.add(new Cumle_11SabitAtama(aktifFonksiyon.gerçekDegiskenNo, tamSayı, Degiskenler.ID_i64));
				} else {
					String metin = ((Sozcuk_05Metin) cümle[4]).metin;
					aktifFonksiyon.cümleler
							.add(new Cumle_11SabitAtama(aktifFonksiyon.gerçekDegiskenNo, metin, Degiskenler.ID_str));
				}
			} else if (cümle.length == 6 && (cümle[0].tip == SÖZCÜK.TİP_01İSİM || cümle[0].tip == SÖZCÜK.TİP_03TAM_SAYI)
					&& cümle[1].tip == SÖZCÜK.TİP_10ATAMA_SAĞA && cümle[2].tip == SÖZCÜK.TİP_01İSİM
					&& cümle[3].tip == SÖZCÜK.TİP_07DEĞİŞKEN_TİPİ && cümle[4].tip == SÖZCÜK.TİP_01İSİM
					&& cümle[5].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				// sayı1 > sayı:i64;
				// 123 > sayı:i64;
				String değişkenİsim = ((Sozcuk_01Isim) cümle[2]).isim;
				String değişkenTipi = ((Sozcuk_01Isim) cümle[4]).isim;
				if (değişkenİsmiKontrol(değişkenİsim)) {
					hata04_DegiskenİsmiUygunDeğil(değişkenİsim);
					continue;
				} else if (aktifFonksiyon.değişkenİsimMap.containsKey(değişkenİsim)) {
					hata03_DegiskenİsimÇakışması(değişkenİsim);
					continue;
				} else if (!Degiskenler.TİP_MAP_STR_ID.containsKey(değişkenTipi)) {
					hata01_BilinmeyenDegiskenTipi(değişkenTipi);
					continue;
				}
				aktifFonksiyon.gerçekDegiskenNo++;
				int değişkenTipiId = Degiskenler.TİP_MAP_STR_ID.get(değişkenTipi);
				Degisken değişken = new Degisken(aktifFonksiyon.gerçekDegiskenNo, değişkenİsim, değişkenTipiId);
				aktifFonksiyon.değişkenNoMap.put(değişken.değişkenNo, değişken);
				aktifFonksiyon.değişkenİsimMap.put(değişkenİsim, değişken);
				aktifFonksiyon.cümleler
						.add(new Cumle_01DegiskenYeni(değişken.değişkenNo, değişkenİsim, değişken.değişkenTipiId));
				if (cümle[0].tip == SÖZCÜK.TİP_01İSİM) {
					String isim = ((Sozcuk_01Isim) cümle[0]).isim;
					if (!aktifFonksiyon.değişkenİsimMap.containsKey(isim)) {
						hata02_TanımsızDegisken(isim);
						continue;
					}
					int değişkenNo = aktifFonksiyon.değişkenİsimMap.get(isim).değişkenNo;
					aktifFonksiyon.cümleler.add(new Cumle_06DegiskenAtama(değişkenNo, değişken.değişkenNo));
				} else {
					String tamSayı = ((Sozcuk_03TamSayi) cümle[0]).tamSayı;
					if (Fonksiyonlar.parseLong(tamSayı) == null) {
						hata05_SayıVeriTipineSığmıyor(tamSayı, Degiskenler.STRING_i64);
						continue;
					}
					aktifFonksiyon.cümleler
							.add(new Cumle_11SabitAtama(aktifFonksiyon.gerçekDegiskenNo, tamSayı, Degiskenler.ID_i64));
				}
			} else if (cümle.length == 4 && cümle[0].tip == SÖZCÜK.TİP_01İSİM
					&& cümle[1].tip == SÖZCÜK.TİP_11TANIMLAMA_SOLA
					&& (cümle[2].tip == SÖZCÜK.TİP_01İSİM || cümle[2].tip == SÖZCÜK.TİP_03TAM_SAYI)
					&& cümle[3].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				// sayı <: sayı1;
				// sayı <: 123;
				String değişkenİsim = ((Sozcuk_01Isim) cümle[0]).isim;
				if (değişkenİsmiKontrol(değişkenİsim)) {
					hata04_DegiskenİsmiUygunDeğil(değişkenİsim);
					continue;
				} else if (aktifFonksiyon.değişkenİsimMap.containsKey(değişkenİsim)) {
					hata03_DegiskenİsimÇakışması(değişkenİsim);
					continue;
				}
				aktifFonksiyon.gerçekDegiskenNo++;
				Degisken değişken = new Degisken(aktifFonksiyon.gerçekDegiskenNo, değişkenİsim, Degiskenler.ID_i64);
				aktifFonksiyon.değişkenNoMap.put(değişken.değişkenNo, değişken);
				aktifFonksiyon.değişkenİsimMap.put(değişkenİsim, değişken);
				aktifFonksiyon.cümleler
						.add(new Cumle_01DegiskenYeni(değişken.değişkenNo, değişkenİsim, değişken.değişkenTipiId));
				if (cümle[2].tip == SÖZCÜK.TİP_01İSİM) {
					String isim = ((Sozcuk_01Isim) cümle[2]).isim;
					if (!aktifFonksiyon.değişkenİsimMap.containsKey(isim)) {
						hata02_TanımsızDegisken(isim);
						continue;
					}
					int değişkenNo = aktifFonksiyon.değişkenİsimMap.get(isim).değişkenNo;
					aktifFonksiyon.cümleler.add(new Cumle_06DegiskenAtama(değişkenNo, değişken.değişkenNo));
				} else {
					String tamSayı = ((Sozcuk_03TamSayi) cümle[2]).tamSayı;
					if (Fonksiyonlar.parseLong(tamSayı) == null) {
						hata05_SayıVeriTipineSığmıyor(tamSayı, Degiskenler.STRING_i64);
						continue;
					}
					aktifFonksiyon.cümleler
							.add(new Cumle_11SabitAtama(aktifFonksiyon.gerçekDegiskenNo, tamSayı, Degiskenler.ID_i64));
				}
			} else if (cümle.length == 4 && (cümle[0].tip == SÖZCÜK.TİP_01İSİM || cümle[0].tip == SÖZCÜK.TİP_03TAM_SAYI)
					&& cümle[1].tip == SÖZCÜK.TİP_12TANIMLAMA_SAĞA && cümle[2].tip == SÖZCÜK.TİP_01İSİM
					&& cümle[3].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				// sayı1 :> sayı;
				// 123 :> sayı;
				String değişkenİsim = ((Sozcuk_01Isim) cümle[2]).isim;
				if (değişkenİsmiKontrol(değişkenİsim)) {
					hata04_DegiskenİsmiUygunDeğil(değişkenİsim);
					continue;
				} else if (aktifFonksiyon.değişkenİsimMap.containsKey(değişkenİsim)) {
					hata03_DegiskenİsimÇakışması(değişkenİsim);
					continue;
				}
				aktifFonksiyon.gerçekDegiskenNo++;
				Degisken değişken = new Degisken(aktifFonksiyon.gerçekDegiskenNo, değişkenİsim, Degiskenler.ID_i64);
				aktifFonksiyon.değişkenNoMap.put(değişken.değişkenNo, değişken);
				aktifFonksiyon.değişkenİsimMap.put(değişkenİsim, değişken);
				aktifFonksiyon.cümleler
						.add(new Cumle_01DegiskenYeni(değişken.değişkenNo, değişkenİsim, değişken.değişkenTipiId));
				if (cümle[0].tip == SÖZCÜK.TİP_01İSİM) {
					String isim = ((Sozcuk_01Isim) cümle[0]).isim;
					if (!aktifFonksiyon.değişkenİsimMap.containsKey(isim)) {
						hata02_TanımsızDegisken(isim);
						continue;
					}
					int değişkenNo = aktifFonksiyon.değişkenİsimMap.get(isim).değişkenNo;
					aktifFonksiyon.cümleler.add(new Cumle_06DegiskenAtama(değişkenNo, değişken.değişkenNo));
				} else {
					String tamSayı = ((Sozcuk_03TamSayi) cümle[0]).tamSayı;
					if (Fonksiyonlar.parseLong(tamSayı) == null) {
						hata05_SayıVeriTipineSığmıyor(tamSayı, Degiskenler.STRING_i64);
						continue;
					}
					aktifFonksiyon.cümleler
							.add(new Cumle_11SabitAtama(aktifFonksiyon.gerçekDegiskenNo, tamSayı, Degiskenler.ID_i64));
				}
			} else if (cümle.length == 6 && cümle[0].tip == SÖZCÜK.TİP_01İSİM
					&& (cümle[1].tip == SÖZCÜK.TİP_09ATAMA_SOLA || cümle[1].tip == SÖZCÜK.TİP_11TANIMLAMA_SOLA)
					&& (cümle[2].tip == SÖZCÜK.TİP_01İSİM || cümle[2].tip == SÖZCÜK.TİP_03TAM_SAYI)
					&& cümle[3].tip == SÖZCÜK.TİP_02OPERATÖR
					&& (cümle[4].tip == SÖZCÜK.TİP_01İSİM || cümle[4].tip == SÖZCÜK.TİP_03TAM_SAYI)
					&& cümle[5].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				// sayı3 < sayı1 + sayı2;
				// sayı3 < 123 + sayı2;
				// sayı3 <: sayı1 + 123;
				// sayı3 <: 123 + 123;
				int değişkenNo1;
				int değişkenNo1TipId;
				if (cümle[2].tip == SÖZCÜK.TİP_01İSİM) {
					String isim1 = ((Sozcuk_01Isim) cümle[2]).isim;
					if (!aktifFonksiyon.değişkenİsimMap.containsKey(isim1)) {
						hata02_TanımsızDegisken(isim1);
						continue;
					}
					Degisken değişken = aktifFonksiyon.değişkenİsimMap.get(isim1);
					değişkenNo1 = değişken.değişkenNo;
					değişkenNo1TipId = değişken.değişkenTipiId;
				} else {
					String tamSayı = ((Sozcuk_03TamSayi) cümle[2]).tamSayı;
					if (Fonksiyonlar.parseLong(tamSayı) == null) {
						hata05_SayıVeriTipineSığmıyor(tamSayı, Degiskenler.STRING_i64);
						continue;
					}
					aktifFonksiyon.geçiciDegiskenNo--;
					aktifFonksiyon.değişkenNoMap.put(aktifFonksiyon.geçiciDegiskenNo,
							new Degisken(aktifFonksiyon.geçiciDegiskenNo, null, Degiskenler.ID_i64));
					Cumle_02GeciciDegiskenYeni komut1 = new Cumle_02GeciciDegiskenYeni(aktifFonksiyon.geçiciDegiskenNo,
							Degiskenler.ID_i64);
					aktifFonksiyon.cümleler.add(komut1);
					Cumle_11SabitAtama komut = new Cumle_11SabitAtama(aktifFonksiyon.geçiciDegiskenNo, tamSayı,
							Degiskenler.ID_i64);
					aktifFonksiyon.cümleler.add(komut);
					değişkenNo1 = komut.değişkenNo;
					değişkenNo1TipId = komut.sabitVeriTipiId;
				}
				int değişkenNo2;
				int değişkenNo2TipId;
				if (cümle[4].tip == SÖZCÜK.TİP_01İSİM) {
					String isim2 = ((Sozcuk_01Isim) cümle[4]).isim;
					if (!aktifFonksiyon.değişkenİsimMap.containsKey(isim2)) {
						hata02_TanımsızDegisken(isim2);
						continue;
					}
					Degisken değişken = aktifFonksiyon.değişkenİsimMap.get(isim2);
					değişkenNo2 = değişken.değişkenNo;
					değişkenNo2TipId = değişken.değişkenTipiId;
				} else {
					String tamSayı = ((Sozcuk_03TamSayi) cümle[4]).tamSayı;
					if (Fonksiyonlar.parseLong(tamSayı) == null) {
						hata05_SayıVeriTipineSığmıyor(tamSayı, Degiskenler.STRING_i64);
						continue;
					}
					aktifFonksiyon.geçiciDegiskenNo--;
					aktifFonksiyon.değişkenNoMap.put(aktifFonksiyon.geçiciDegiskenNo,
							new Degisken(aktifFonksiyon.geçiciDegiskenNo, null, Degiskenler.ID_i64));
					Cumle_02GeciciDegiskenYeni komut1 = new Cumle_02GeciciDegiskenYeni(aktifFonksiyon.geçiciDegiskenNo,
							Degiskenler.ID_i64);
					aktifFonksiyon.cümleler.add(komut1);
					Cumle_11SabitAtama komut = new Cumle_11SabitAtama(aktifFonksiyon.geçiciDegiskenNo, tamSayı,
							Degiskenler.ID_i64);
					aktifFonksiyon.cümleler.add(komut);
					değişkenNo2 = komut.değişkenNo;
					değişkenNo2TipId = komut.sabitVeriTipiId;
				}
				String operatör = ((Sozcuk_02Operator) cümle[3]).operatör;
				String anahtar = değişkenNo1TipId + operatör + değişkenNo2TipId;
				if (!operatörFonksiyonMap.containsKey(anahtar)) {
					hata04_TanımsızOperatör(Degiskenler.TİP_MAP_ID_STR.get(değişkenNo1TipId) + " " + operatör + " "
							+ Degiskenler.TİP_MAP_ID_STR.get(değişkenNo2TipId));
					continue;
				}
				Fonksiyon_01OperatorFonksiyon fonksiyon = operatörFonksiyonMap.get(anahtar);
				int değişkenNo0;
				if (cümle[1].tip == SÖZCÜK.TİP_09ATAMA_SOLA) {
					String isimHedef = ((Sozcuk_01Isim) cümle[0]).isim;
					if (!aktifFonksiyon.değişkenİsimMap.containsKey(isimHedef)) {
						hata02_TanımsızDegisken(isimHedef);
						continue;
					}
					değişkenNo0 = aktifFonksiyon.değişkenİsimMap.get(isimHedef).değişkenNo;
				} else {
					String değişkenİsim = ((Sozcuk_01Isim) cümle[0]).isim;
					if (değişkenİsmiKontrol(değişkenİsim)) {
						hata04_DegiskenİsmiUygunDeğil(değişkenİsim);
						continue;
					} else if (aktifFonksiyon.değişkenİsimMap.containsKey(değişkenİsim)) {
						hata03_DegiskenİsimÇakışması(değişkenİsim);
						continue;
					}
					aktifFonksiyon.gerçekDegiskenNo++;
					Degisken değişken = new Degisken(aktifFonksiyon.gerçekDegiskenNo, değişkenİsim,
							Degiskenler.TİP_MAP_STR_ID.get(fonksiyon.sonuçTip));
					aktifFonksiyon.değişkenNoMap.put(değişken.değişkenNo, değişken);
					aktifFonksiyon.değişkenİsimMap.put(değişkenİsim, değişken);
					aktifFonksiyon.cümleler
							.add(new Cumle_01DegiskenYeni(değişken.değişkenNo, değişkenİsim, değişken.değişkenTipiId));
					değişkenNo0 = değişken.değişkenNo;
				}
				aktifFonksiyon.cümleler
						.add(new Cumle_04OperatorIslemi(değişkenNo0, operatör, değişkenNo1, değişkenNo2));
			} else if (cümle.length == 6 && (cümle[0].tip == SÖZCÜK.TİP_01İSİM || cümle[0].tip == SÖZCÜK.TİP_03TAM_SAYI)
					&& cümle[1].tip == SÖZCÜK.TİP_02OPERATÖR
					&& (cümle[2].tip == SÖZCÜK.TİP_01İSİM || cümle[2].tip == SÖZCÜK.TİP_03TAM_SAYI)
					&& (cümle[3].tip == SÖZCÜK.TİP_10ATAMA_SAĞA || cümle[3].tip == SÖZCÜK.TİP_12TANIMLAMA_SAĞA)
					&& cümle[4].tip == SÖZCÜK.TİP_01İSİM && cümle[5].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				// sayı1 + sayı2 > sayı3;
				// 123 + sayı2 > sayı3;
				// sayı1 + 123 :> sayı3;
				// 123 + 123 :> sayı3;
				int değişkenNo0;
				if (cümle[3].tip == SÖZCÜK.TİP_10ATAMA_SAĞA) {
					String isimHedef = ((Sozcuk_01Isim) cümle[4]).isim;
					if (!aktifFonksiyon.değişkenİsimMap.containsKey(isimHedef)) {
						hata02_TanımsızDegisken(isimHedef);
						continue;
					}
					değişkenNo0 = aktifFonksiyon.değişkenİsimMap.get(isimHedef).değişkenNo;
				} else {
					String değişkenİsim = ((Sozcuk_01Isim) cümle[4]).isim;
					if (değişkenİsmiKontrol(değişkenİsim)) {
						hata04_DegiskenİsmiUygunDeğil(değişkenİsim);
						continue;
					} else if (aktifFonksiyon.değişkenİsimMap.containsKey(değişkenİsim)) {
						hata03_DegiskenİsimÇakışması(değişkenİsim);
						continue;
					}
					aktifFonksiyon.gerçekDegiskenNo++;
					Degisken değişken = new Degisken(aktifFonksiyon.gerçekDegiskenNo, değişkenİsim, Degiskenler.ID_i64);
					aktifFonksiyon.değişkenNoMap.put(değişken.değişkenNo, değişken);
					aktifFonksiyon.değişkenİsimMap.put(değişkenİsim, değişken);
					aktifFonksiyon.cümleler
							.add(new Cumle_01DegiskenYeni(değişken.değişkenNo, değişkenİsim, değişken.değişkenTipiId));
					değişkenNo0 = değişken.değişkenNo;
				}
				int değişkenNo1;
				int değişkenNo1TipId;
				if (cümle[0].tip == SÖZCÜK.TİP_01İSİM) {
					String isim1 = ((Sozcuk_01Isim) cümle[0]).isim;
					if (!aktifFonksiyon.değişkenİsimMap.containsKey(isim1)) {
						hata02_TanımsızDegisken(isim1);
						continue;
					}
					Degisken değişken = aktifFonksiyon.değişkenİsimMap.get(isim1);
					değişkenNo1 = değişken.değişkenNo;
					değişkenNo1TipId = değişken.değişkenTipiId;
				} else {
					String tamSayı = ((Sozcuk_03TamSayi) cümle[0]).tamSayı;
					if (Fonksiyonlar.parseLong(tamSayı) == null) {
						hata05_SayıVeriTipineSığmıyor(tamSayı, Degiskenler.STRING_i64);
						continue;
					}
					aktifFonksiyon.geçiciDegiskenNo--;
					aktifFonksiyon.değişkenNoMap.put(aktifFonksiyon.geçiciDegiskenNo,
							new Degisken(aktifFonksiyon.geçiciDegiskenNo, null, Degiskenler.ID_i64));
					Cumle_02GeciciDegiskenYeni komut1 = new Cumle_02GeciciDegiskenYeni(aktifFonksiyon.geçiciDegiskenNo,
							Degiskenler.ID_i64);
					aktifFonksiyon.cümleler.add(komut1);
					Cumle_11SabitAtama komut = new Cumle_11SabitAtama(aktifFonksiyon.geçiciDegiskenNo, tamSayı,
							Degiskenler.ID_i64);
					aktifFonksiyon.cümleler.add(komut);
					değişkenNo1 = komut.değişkenNo;
					değişkenNo1TipId = komut.sabitVeriTipiId;
				}
				int değişkenNo2;
				int değişkenNo2TipId;
				if (cümle[2].tip == SÖZCÜK.TİP_01İSİM) {
					String isim2 = ((Sozcuk_01Isim) cümle[2]).isim;
					if (!aktifFonksiyon.değişkenİsimMap.containsKey(isim2)) {
						hata02_TanımsızDegisken(isim2);
						continue;
					}
					Degisken değişken = aktifFonksiyon.değişkenİsimMap.get(isim2);
					değişkenNo2 = değişken.değişkenNo;
					değişkenNo2TipId = değişken.değişkenTipiId;
				} else {
					String tamSayı = ((Sozcuk_03TamSayi) cümle[2]).tamSayı;
					if (Fonksiyonlar.parseLong(tamSayı) == null) {
						hata05_SayıVeriTipineSığmıyor(tamSayı, Degiskenler.STRING_i64);
						continue;
					}
					aktifFonksiyon.geçiciDegiskenNo--;
					aktifFonksiyon.değişkenNoMap.put(aktifFonksiyon.geçiciDegiskenNo,
							new Degisken(aktifFonksiyon.geçiciDegiskenNo, null, Degiskenler.ID_i64));
					Cumle_02GeciciDegiskenYeni komut1 = new Cumle_02GeciciDegiskenYeni(aktifFonksiyon.geçiciDegiskenNo,
							Degiskenler.ID_i64);
					aktifFonksiyon.cümleler.add(komut1);
					Cumle_11SabitAtama komut = new Cumle_11SabitAtama(aktifFonksiyon.geçiciDegiskenNo, tamSayı,
							Degiskenler.ID_i64);
					aktifFonksiyon.cümleler.add(komut);
					değişkenNo2 = komut.değişkenNo;
					değişkenNo2TipId = komut.sabitVeriTipiId;
				}
				String operatör = ((Sozcuk_02Operator) cümle[1]).operatör;
				String anahtar = değişkenNo1TipId + operatör + değişkenNo2TipId;
				if (!operatörFonksiyonMap.containsKey(anahtar)) {
					hata04_TanımsızOperatör(Degiskenler.TİP_MAP_ID_STR.get(değişkenNo1TipId) + " " + operatör + " "
							+ Degiskenler.TİP_MAP_ID_STR.get(değişkenNo2TipId));
					continue;
				}
				aktifFonksiyon.cümleler
						.add(new Cumle_04OperatorIslemi(değişkenNo0, operatör, değişkenNo1, değişkenNo2));
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
