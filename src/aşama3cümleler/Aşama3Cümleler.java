package aşama3cümleler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import aşama2sözcükler.Sözcük;
import aşama2sözcükler.Sözcük_01İsim;
import aşama2sözcükler.Sözcük_02Operatör;
import aşama2sözcükler.Sözcük_03TamSayı;
import aşama2sözcükler.Sözcük_05Metin;
import yardımcı.Fonksiyonlar;
import yardımcı.Değişkenler;
import yardımcı.Değişkenler.SÖZCÜK;

public class Aşama3Cümleler {

	private static HashMap<String, Fonksiyon_01OperatörFonksiyon> operatörFonksiyonMap;
	private static HashMap<String, Fonksiyon_02İsimliFonksiyon> isimFonksiyonMap;
	private static Fonksiyon_03AnaFonksiyon anaFonksiyon;
	private static Fonksiyon aktifFonksiyon;
	private static StringBuilder hatalar;

	private static void hata01_BilinmeyenDeğişkenTipi(String değişkenTipi) {
		hatalar.append("Bilinmeyen Değişken Tipi : " + değişkenTipi + " !!!\n");
	}

	private static void hata02_TanımsızDeğişken(String değişkenİsmi) {
		hatalar.append("Tanımsız Değişken : " + değişkenİsmi + " !!!\n");
	}

	private static void hata03_DeğişkenİsimÇakışması(String değişkenİsmi) {
		hatalar.append("Çok Defa Değişken Tanımlama : " + değişkenİsmi + " !!!\n");
	}

	private static void hata04_DeğişkenİsmiUygunDeğil(String değişkenİsmi) {
		hatalar.append("Değişken İsmi Uygun Değil : " + değişkenİsmi + " !!!\n");
	}

	private static void hata04_TanımsızOperatör(String operatörKullanımı) {
		hatalar.append("Tanımsız Operatör : " + operatörKullanımı + " !!!\n");
	}

	private static void hata05_SayıVeriTipineSığmıyor(String sayı, String veriTipi) {
		hatalar.append("Sayı Veri Tipine Sığmıyor : " + sayı + " : " + veriTipi + " !!!!\n");
	}

	private static boolean değişkenİsmiKontrol(String değişkenİsmi) {
		// İlk karakter : _, a-z, A-Z
		// Diğer karakterler : _, a-z, A-Z, 0-9
		char ch = değişkenİsmi.charAt(0);
		if (!(ch == '_' || Character.isLetter(ch))) {
			return true;
		}
		for (int i = 1; i < değişkenİsmi.length(); i++) {
			ch = değişkenİsmi.charAt(i);
			if (!(ch == '_' || Character.isLetterOrDigit(ch))) {
				return true;
			}
		}
		if (Değişkenler.ANAHTAR_KELIMELER.contains(değişkenİsmi)) {
			return true;
		}
		return false;
	}

	public static Object işle(ArrayList<Sözcük[]> tümCümleler, String dosyaAdı) {

		operatörFonksiyonMap = new HashMap<>();
		isimFonksiyonMap = new HashMap<>();
		anaFonksiyon = new Fonksiyon_03AnaFonksiyon();
		aktifFonksiyon = anaFonksiyon;
		hatalar = new StringBuilder();

		for (Sözcük[] cümle : tümCümleler) {
			if (cümle[0].tip == SÖZCÜK.TİP_13ÖZELLİK && cümle[cümle.length - 1].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				HashSet<String> özellikSet = new HashSet<String>();
				for (int i = 2; i < cümle.length - 1; i++) {
					özellikSet.add(((Sözcük_01İsim) cümle[i]).isim);
				}
				aktifFonksiyon.özellikMap.put(((Sözcük_01İsim) cümle[1]).isim, özellikSet);
			} else if (cümle[1].tip == SÖZCÜK.TİP_14AÇ_PARANTEZ) {
				Fonksiyon_02İsimliFonksiyon isimFonksiyon = new Fonksiyon_02İsimliFonksiyon();
				isimFonksiyon.isim = ((Sözcük_01İsim) cümle[0]).isim;
				if (cümle.length > 5 && cümle[5].tip == SÖZCÜK.TİP_15KAPA_PARANTEZ) {
					isimFonksiyon.değişken1İsim = ((Sözcük_01İsim) cümle[2]).isim;
					isimFonksiyon.değişken1Tip = ((Sözcük_01İsim) cümle[4]).isim;
				}
				aktifFonksiyon = isimFonksiyon;
				isimFonksiyonMap.put(isimFonksiyon.isim, isimFonksiyon);
			} else if (cümle[0].tip == SÖZCÜK.TİP_01İSİM && ((Sözcük_01İsim) cümle[0]).isim.equals("inline")) {
				String değişken1Tip = ((Sözcük_01İsim) cümle[5]).isim;
				String değişken2Tip = ((Sözcük_01İsim) cümle[11]).isim;
				if (!Değişkenler.TİP_MAP_STR_ID.containsKey(değişken1Tip)) {
					hata01_BilinmeyenDeğişkenTipi(değişken1Tip);
					continue;
				} else if (!Değişkenler.TİP_MAP_STR_ID.containsKey(değişken2Tip)) {
					hata01_BilinmeyenDeğişkenTipi(değişken2Tip);
					continue;
				}
				Fonksiyon_01OperatörFonksiyon operatörFonksiyon = new Fonksiyon_01OperatörFonksiyon();
				operatörFonksiyon.özellikMap = aktifFonksiyon.özellikMap;
				operatörFonksiyon.inline = true;
				operatörFonksiyon.öncelik = ((Sözcük_01İsim) cümle[1]).isim.equals("p1") ? 1 : 2;
				operatörFonksiyon.değişken1İsim = ((Sözcük_01İsim) cümle[3]).isim;
				operatörFonksiyon.değişken1TipId = Değişkenler.TİP_MAP_STR_ID.get(değişken1Tip);
				operatörFonksiyon.operatör = ((Sözcük_02Operatör) cümle[7]).operatör;
				operatörFonksiyon.değişken2İsim = ((Sözcük_01İsim) cümle[9]).isim;
				operatörFonksiyon.değişken2TipId = Değişkenler.TİP_MAP_STR_ID.get(değişken2Tip);
				operatörFonksiyon.sonuçİsim = ((Sözcük_01İsim) cümle[13]).isim;
				operatörFonksiyon.sonuçTip = ((Sözcük_01İsim) cümle[15]).isim;
				aktifFonksiyon.özellikMap = new HashMap<>();
				aktifFonksiyon = operatörFonksiyon;
				//
				String anahtar = operatörFonksiyon.değişken1TipId + operatörFonksiyon.operatör
						+ operatörFonksiyon.değişken2TipId;
				operatörFonksiyonMap.put(anahtar, operatörFonksiyon);
			} else if (aktifFonksiyon.özellikMap.containsKey("asm-data")) {
				aktifFonksiyon.cümleler.add(new Cümle_08MakineDiliVeri(((Sözcük_05Metin) cümle[0]).metin));
				aktifFonksiyon.özellikMap = new HashMap<>();
			} else if (aktifFonksiyon.özellikMap.containsKey("asm-rodata")) {
				aktifFonksiyon.cümleler.add(new Cümle_09MakineDiliSabitVeri(((Sözcük_05Metin) cümle[0]).metin));
				aktifFonksiyon.özellikMap = new HashMap<>();
			} else if (aktifFonksiyon.özellikMap.containsKey("asm")) {
				aktifFonksiyon.cümleler.add(new Cümle_07MakineDiliKod(((Sözcük_05Metin) cümle[0]).metin));
				aktifFonksiyon.özellikMap = new HashMap<>();
			} else if (cümle[0].tip == SÖZCÜK.TİP_17KAPA_SÜSLÜ && cümle[1].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				aktifFonksiyon = anaFonksiyon;
			} else if (cümle.length == 4 && cümle[0].tip == SÖZCÜK.TİP_01İSİM && cümle[1].tip == SÖZCÜK.TİP_09ATAMA_SOLA
					&& cümle[2].tip == SÖZCÜK.TİP_01İSİM && cümle[3].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				// sayı2 < sayı1;
				String isimKaynak = ((Sözcük_01İsim) cümle[2]).isim;
				String isimHedef = ((Sözcük_01İsim) cümle[0]).isim;
				if (!aktifFonksiyon.değişkenİsimMap.containsKey(isimKaynak)) {
					hata02_TanımsızDeğişken(isimKaynak);
				} else if (!aktifFonksiyon.değişkenİsimMap.containsKey(isimHedef)) {
					hata02_TanımsızDeğişken(isimHedef);
				} else {
					aktifFonksiyon.cümleler
							.add(new Cümle_06DeğişkenAtama(aktifFonksiyon.değişkenİsimMap.get(isimKaynak).değişkenNo,
									aktifFonksiyon.değişkenİsimMap.get(isimHedef).değişkenNo));
				}
			} else if (cümle.length == 4 && cümle[0].tip == SÖZCÜK.TİP_01İSİM && cümle[1].tip == SÖZCÜK.TİP_10ATAMA_SAĞA
					&& cümle[2].tip == SÖZCÜK.TİP_01İSİM && cümle[3].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				// sayı1 > sayı2;
				String isimKaynak = ((Sözcük_01İsim) cümle[0]).isim;
				String isimHedef = ((Sözcük_01İsim) cümle[2]).isim;
				if (!aktifFonksiyon.değişkenİsimMap.containsKey(isimKaynak)) {
					hata02_TanımsızDeğişken(isimKaynak);
				} else if (!aktifFonksiyon.değişkenİsimMap.containsKey(isimHedef)) {
					hata02_TanımsızDeğişken(isimHedef);
				} else {
					aktifFonksiyon.cümleler
							.add(new Cümle_06DeğişkenAtama(aktifFonksiyon.değişkenİsimMap.get(isimKaynak).değişkenNo,
									aktifFonksiyon.değişkenİsimMap.get(isimHedef).değişkenNo));
				}
			} else if (cümle.length == 4 && cümle[0].tip == SÖZCÜK.TİP_03TAM_SAYI
					&& cümle[1].tip == SÖZCÜK.TİP_10ATAMA_SAĞA && cümle[2].tip == SÖZCÜK.TİP_01İSİM
					&& cümle[3].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				// 123 > sayı2;
				String isimHedef = ((Sözcük_01İsim) cümle[2]).isim;
				String tamSayı = ((Sözcük_03TamSayı) cümle[0]).tamSayı;
				if (!aktifFonksiyon.değişkenİsimMap.containsKey(isimHedef)) {
					hata02_TanımsızDeğişken(isimHedef);
					continue;
				} else if (Fonksiyonlar.parseLong(tamSayı) == null) {
					hata05_SayıVeriTipineSığmıyor(tamSayı, Değişkenler.STRING_i64);
					continue;
				}
				aktifFonksiyon.cümleler.add(new Cümle_10SabitAtama(
						aktifFonksiyon.değişkenİsimMap.get(isimHedef).değişkenNo, tamSayı, Değişkenler.ID_i64));
			} else if (cümle.length == 4 && cümle[0].tip == SÖZCÜK.TİP_01İSİM && cümle[1].tip == SÖZCÜK.TİP_09ATAMA_SOLA
					&& cümle[2].tip == SÖZCÜK.TİP_03TAM_SAYI && cümle[3].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				// sayı2 < 123;
				String isimHedef = ((Sözcük_01İsim) cümle[0]).isim;
				String tamSayı = ((Sözcük_03TamSayı) cümle[2]).tamSayı;
				if (!aktifFonksiyon.değişkenİsimMap.containsKey(isimHedef)) {
					hata02_TanımsızDeğişken(isimHedef);
					continue;
				} else if (Fonksiyonlar.parseLong(tamSayı) == null) {
					hata05_SayıVeriTipineSığmıyor(tamSayı, Değişkenler.STRING_i64);
					continue;
				}
				aktifFonksiyon.cümleler.add(new Cümle_10SabitAtama(
						aktifFonksiyon.değişkenİsimMap.get(isimHedef).değişkenNo, tamSayı, Değişkenler.ID_i64));
			} else if (cümle.length == 4 && (cümle[0].tip == SÖZCÜK.TİP_01İSİM || cümle[0].tip == SÖZCÜK.TİP_03TAM_SAYI)
					&& cümle[1].tip == SÖZCÜK.TİP_08NOKTA && cümle[2].tip == SÖZCÜK.TİP_01İSİM
					&& cümle[3].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				// sayı1.printhn;
				// 123.printhn;
				int değişkenNo;
				if (cümle[0].tip == SÖZCÜK.TİP_01İSİM) {
					String isim = ((Sözcük_01İsim) cümle[0]).isim;
					if (!aktifFonksiyon.değişkenİsimMap.containsKey(isim)) {
						hata02_TanımsızDeğişken(isim);
						continue;
					}
					değişkenNo = aktifFonksiyon.değişkenİsimMap.get(isim).değişkenNo;
				} else {
					String tamSayı = ((Sözcük_03TamSayı) cümle[0]).tamSayı;
					if (Fonksiyonlar.parseLong(tamSayı) == null) {
						hata05_SayıVeriTipineSığmıyor(tamSayı, Değişkenler.STRING_i64);
						continue;
					}
					aktifFonksiyon.geçiciDeğişkenNo--;
					aktifFonksiyon.değişkenNoMap.put(aktifFonksiyon.geçiciDeğişkenNo,
							new Değişken(aktifFonksiyon.geçiciDeğişkenNo, null, Değişkenler.ID_i64));
					Cümle_02GeçiciDeğişkenYeni komut1 = new Cümle_02GeçiciDeğişkenYeni(aktifFonksiyon.geçiciDeğişkenNo,
							Değişkenler.ID_i64);
					aktifFonksiyon.cümleler.add(komut1);
					Cümle_10SabitAtama komut = new Cümle_10SabitAtama(aktifFonksiyon.geçiciDeğişkenNo, tamSayı,
							Değişkenler.ID_i64);
					aktifFonksiyon.cümleler.add(komut);
					değişkenNo = komut.değişkenNo;
				}
				aktifFonksiyon.geçiciDeğişkenNo--;
				aktifFonksiyon.değişkenNoMap.put(aktifFonksiyon.geçiciDeğişkenNo,
						new Değişken(aktifFonksiyon.geçiciDeğişkenNo, null, Değişkenler.ID_i64));
				Cümle_02GeçiciDeğişkenYeni komut1 = new Cümle_02GeçiciDeğişkenYeni(aktifFonksiyon.geçiciDeğişkenNo,
						Değişkenler.ID_i64);
				aktifFonksiyon.cümleler.add(komut1);
				Cümle_05FonksiyonÇağrısı komut = new Cümle_05FonksiyonÇağrısı(aktifFonksiyon.geçiciDeğişkenNo,
						((Sözcük_01İsim) cümle[2]).isim, değişkenNo);
				aktifFonksiyon.cümleler.add(komut);
			} else if (cümle.length == 6 && cümle[0].tip == SÖZCÜK.TİP_01İSİM
					&& cümle[1].tip == SÖZCÜK.TİP_07DEĞİŞKEN_TİPİ && cümle[2].tip == SÖZCÜK.TİP_01İSİM
					&& cümle[3].tip == SÖZCÜK.TİP_09ATAMA_SOLA
					&& (cümle[4].tip == SÖZCÜK.TİP_01İSİM || cümle[4].tip == SÖZCÜK.TİP_03TAM_SAYI)
					&& cümle[5].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				// sayı:i64 < sayı1;
				// sayı:i64 < 123;
				String değişkenİsim = ((Sözcük_01İsim) cümle[0]).isim;
				String değişkenTipi = ((Sözcük_01İsim) cümle[2]).isim;
				if (değişkenİsmiKontrol(değişkenİsim)) {
					hata04_DeğişkenİsmiUygunDeğil(değişkenİsim);
					continue;
				} else if (aktifFonksiyon.değişkenİsimMap.containsKey(değişkenİsim)) {
					hata03_DeğişkenİsimÇakışması(değişkenİsim);
					continue;
				} else if (!Değişkenler.TİP_MAP_STR_ID.containsKey(değişkenTipi)) {
					hata01_BilinmeyenDeğişkenTipi(değişkenTipi);
					continue;
				}
				aktifFonksiyon.gerçekDeğişkenNo++;
				int değişkenTipiId = Değişkenler.TİP_MAP_STR_ID.get(değişkenTipi);
				Değişken değişken = new Değişken(aktifFonksiyon.gerçekDeğişkenNo, değişkenİsim, değişkenTipiId);
				aktifFonksiyon.değişkenNoMap.put(değişken.değişkenNo, değişken);
				aktifFonksiyon.değişkenİsimMap.put(değişkenİsim, değişken);
				aktifFonksiyon.cümleler
						.add(new Cümle_01DeğişkenYeni(değişken.değişkenNo, değişkenİsim, değişken.değişkenTipiId));
				if (cümle[4].tip == SÖZCÜK.TİP_01İSİM) {
					String isim = ((Sözcük_01İsim) cümle[4]).isim;
					if (!aktifFonksiyon.değişkenİsimMap.containsKey(isim)) {
						hata02_TanımsızDeğişken(isim);
						continue;
					}
					int değişkenNo = aktifFonksiyon.değişkenİsimMap.get(isim).değişkenNo;
					aktifFonksiyon.cümleler.add(new Cümle_06DeğişkenAtama(değişkenNo, değişken.değişkenNo));
				} else {
					String tamSayı = ((Sözcük_03TamSayı) cümle[4]).tamSayı;
					if (Fonksiyonlar.parseLong(tamSayı) == null) {
						hata05_SayıVeriTipineSığmıyor(tamSayı, Değişkenler.STRING_i64);
						continue;
					}
					aktifFonksiyon.cümleler
							.add(new Cümle_10SabitAtama(aktifFonksiyon.gerçekDeğişkenNo, tamSayı, Değişkenler.ID_i64));
				}
			} else if (cümle.length == 6 && (cümle[0].tip == SÖZCÜK.TİP_01İSİM || cümle[0].tip == SÖZCÜK.TİP_03TAM_SAYI)
					&& cümle[1].tip == SÖZCÜK.TİP_10ATAMA_SAĞA && cümle[2].tip == SÖZCÜK.TİP_01İSİM
					&& cümle[3].tip == SÖZCÜK.TİP_07DEĞİŞKEN_TİPİ && cümle[4].tip == SÖZCÜK.TİP_01İSİM
					&& cümle[5].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				// sayı1 > sayı:i64;
				// 123 > sayı:i64;
				String değişkenİsim = ((Sözcük_01İsim) cümle[2]).isim;
				String değişkenTipi = ((Sözcük_01İsim) cümle[4]).isim;
				if (değişkenİsmiKontrol(değişkenİsim)) {
					hata04_DeğişkenİsmiUygunDeğil(değişkenİsim);
					continue;
				} else if (aktifFonksiyon.değişkenİsimMap.containsKey(değişkenİsim)) {
					hata03_DeğişkenİsimÇakışması(değişkenİsim);
					continue;
				} else if (!Değişkenler.TİP_MAP_STR_ID.containsKey(değişkenTipi)) {
					hata01_BilinmeyenDeğişkenTipi(değişkenTipi);
					continue;
				}
				aktifFonksiyon.gerçekDeğişkenNo++;
				int değişkenTipiId = Değişkenler.TİP_MAP_STR_ID.get(değişkenTipi);
				Değişken değişken = new Değişken(aktifFonksiyon.gerçekDeğişkenNo, değişkenİsim, değişkenTipiId);
				aktifFonksiyon.değişkenNoMap.put(değişken.değişkenNo, değişken);
				aktifFonksiyon.değişkenİsimMap.put(değişkenİsim, değişken);
				aktifFonksiyon.cümleler
						.add(new Cümle_01DeğişkenYeni(değişken.değişkenNo, değişkenİsim, değişken.değişkenTipiId));
				if (cümle[0].tip == SÖZCÜK.TİP_01İSİM) {
					String isim = ((Sözcük_01İsim) cümle[0]).isim;
					if (!aktifFonksiyon.değişkenİsimMap.containsKey(isim)) {
						hata02_TanımsızDeğişken(isim);
						continue;
					}
					int değişkenNo = aktifFonksiyon.değişkenİsimMap.get(isim).değişkenNo;
					aktifFonksiyon.cümleler.add(new Cümle_06DeğişkenAtama(değişkenNo, değişken.değişkenNo));
				} else {
					String tamSayı = ((Sözcük_03TamSayı) cümle[0]).tamSayı;
					if (Fonksiyonlar.parseLong(tamSayı) == null) {
						hata05_SayıVeriTipineSığmıyor(tamSayı, Değişkenler.STRING_i64);
						continue;
					}
					aktifFonksiyon.cümleler
							.add(new Cümle_10SabitAtama(aktifFonksiyon.gerçekDeğişkenNo, tamSayı, Değişkenler.ID_i64));
				}
			} else if (cümle.length == 4 && cümle[0].tip == SÖZCÜK.TİP_01İSİM
					&& cümle[1].tip == SÖZCÜK.TİP_11TANIMLAMA_SOLA
					&& (cümle[2].tip == SÖZCÜK.TİP_01İSİM || cümle[2].tip == SÖZCÜK.TİP_03TAM_SAYI)
					&& cümle[3].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				// sayı <: sayı1;
				// sayı <: 123;
				String değişkenİsim = ((Sözcük_01İsim) cümle[0]).isim;
				if (değişkenİsmiKontrol(değişkenİsim)) {
					hata04_DeğişkenİsmiUygunDeğil(değişkenİsim);
					continue;
				} else if (aktifFonksiyon.değişkenİsimMap.containsKey(değişkenİsim)) {
					hata03_DeğişkenİsimÇakışması(değişkenİsim);
					continue;
				}
				aktifFonksiyon.gerçekDeğişkenNo++;
				Değişken değişken = new Değişken(aktifFonksiyon.gerçekDeğişkenNo, değişkenİsim, Değişkenler.ID_i64);
				aktifFonksiyon.değişkenNoMap.put(değişken.değişkenNo, değişken);
				aktifFonksiyon.değişkenİsimMap.put(değişkenİsim, değişken);
				aktifFonksiyon.cümleler
						.add(new Cümle_01DeğişkenYeni(değişken.değişkenNo, değişkenİsim, değişken.değişkenTipiId));
				if (cümle[2].tip == SÖZCÜK.TİP_01İSİM) {
					String isim = ((Sözcük_01İsim) cümle[2]).isim;
					if (!aktifFonksiyon.değişkenİsimMap.containsKey(isim)) {
						hata02_TanımsızDeğişken(isim);
						continue;
					}
					int değişkenNo = aktifFonksiyon.değişkenİsimMap.get(isim).değişkenNo;
					aktifFonksiyon.cümleler.add(new Cümle_06DeğişkenAtama(değişkenNo, değişken.değişkenNo));
				} else {
					String tamSayı = ((Sözcük_03TamSayı) cümle[2]).tamSayı;
					if (Fonksiyonlar.parseLong(tamSayı) == null) {
						hata05_SayıVeriTipineSığmıyor(tamSayı, Değişkenler.STRING_i64);
						continue;
					}
					aktifFonksiyon.cümleler
							.add(new Cümle_10SabitAtama(aktifFonksiyon.gerçekDeğişkenNo, tamSayı, Değişkenler.ID_i64));
				}
			} else if (cümle.length == 4 && (cümle[0].tip == SÖZCÜK.TİP_01İSİM || cümle[0].tip == SÖZCÜK.TİP_03TAM_SAYI)
					&& cümle[1].tip == SÖZCÜK.TİP_12TANIMLAMA_SAĞA && cümle[2].tip == SÖZCÜK.TİP_01İSİM
					&& cümle[3].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				// sayı1 :> sayı;
				// 123 :> sayı;
				String değişkenİsim = ((Sözcük_01İsim) cümle[2]).isim;
				if (değişkenİsmiKontrol(değişkenİsim)) {
					hata04_DeğişkenİsmiUygunDeğil(değişkenİsim);
					continue;
				} else if (aktifFonksiyon.değişkenİsimMap.containsKey(değişkenİsim)) {
					hata03_DeğişkenİsimÇakışması(değişkenİsim);
					continue;
				}
				aktifFonksiyon.gerçekDeğişkenNo++;
				Değişken değişken = new Değişken(aktifFonksiyon.gerçekDeğişkenNo, değişkenİsim, Değişkenler.ID_i64);
				aktifFonksiyon.değişkenNoMap.put(değişken.değişkenNo, değişken);
				aktifFonksiyon.değişkenİsimMap.put(değişkenİsim, değişken);
				aktifFonksiyon.cümleler
						.add(new Cümle_01DeğişkenYeni(değişken.değişkenNo, değişkenİsim, değişken.değişkenTipiId));
				if (cümle[0].tip == SÖZCÜK.TİP_01İSİM) {
					String isim = ((Sözcük_01İsim) cümle[0]).isim;
					if (!aktifFonksiyon.değişkenİsimMap.containsKey(isim)) {
						hata02_TanımsızDeğişken(isim);
						continue;
					}
					int değişkenNo = aktifFonksiyon.değişkenİsimMap.get(isim).değişkenNo;
					aktifFonksiyon.cümleler.add(new Cümle_06DeğişkenAtama(değişkenNo, değişken.değişkenNo));
				} else {
					String tamSayı = ((Sözcük_03TamSayı) cümle[0]).tamSayı;
					if (Fonksiyonlar.parseLong(tamSayı) == null) {
						hata05_SayıVeriTipineSığmıyor(tamSayı, Değişkenler.STRING_i64);
						continue;
					}
					aktifFonksiyon.cümleler
							.add(new Cümle_10SabitAtama(aktifFonksiyon.gerçekDeğişkenNo, tamSayı, Değişkenler.ID_i64));
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
				int değişkenNo0;
				if (cümle[1].tip == SÖZCÜK.TİP_09ATAMA_SOLA) {
					String isimHedef = ((Sözcük_01İsim) cümle[0]).isim;
					if (!aktifFonksiyon.değişkenİsimMap.containsKey(isimHedef)) {
						hata02_TanımsızDeğişken(isimHedef);
						continue;
					}
					değişkenNo0 = aktifFonksiyon.değişkenİsimMap.get(isimHedef).değişkenNo;
				} else {
					String değişkenİsim = ((Sözcük_01İsim) cümle[0]).isim;
					if (değişkenİsmiKontrol(değişkenİsim)) {
						hata04_DeğişkenİsmiUygunDeğil(değişkenİsim);
						continue;
					} else if (aktifFonksiyon.değişkenİsimMap.containsKey(değişkenİsim)) {
						hata03_DeğişkenİsimÇakışması(değişkenİsim);
						continue;
					}
					aktifFonksiyon.gerçekDeğişkenNo++;
					Değişken değişken = new Değişken(aktifFonksiyon.gerçekDeğişkenNo, değişkenİsim, Değişkenler.ID_i64);
					aktifFonksiyon.değişkenNoMap.put(değişken.değişkenNo, değişken);
					aktifFonksiyon.değişkenİsimMap.put(değişkenİsim, değişken);
					aktifFonksiyon.cümleler
							.add(new Cümle_01DeğişkenYeni(değişken.değişkenNo, değişkenİsim, değişken.değişkenTipiId));
					değişkenNo0 = değişken.değişkenNo;
				}
				int değişkenNo1;
				int değişkenNo1TipId;
				if (cümle[2].tip == SÖZCÜK.TİP_01İSİM) {
					String isim1 = ((Sözcük_01İsim) cümle[2]).isim;
					if (!aktifFonksiyon.değişkenİsimMap.containsKey(isim1)) {
						hata02_TanımsızDeğişken(isim1);
						continue;
					}
					Değişken değişken = aktifFonksiyon.değişkenİsimMap.get(isim1);
					değişkenNo1 = değişken.değişkenNo;
					değişkenNo1TipId = değişken.değişkenTipiId;
				} else {
					String tamSayı = ((Sözcük_03TamSayı) cümle[2]).tamSayı;
					if (Fonksiyonlar.parseLong(tamSayı) == null) {
						hata05_SayıVeriTipineSığmıyor(tamSayı, Değişkenler.STRING_i64);
						continue;
					}
					aktifFonksiyon.geçiciDeğişkenNo--;
					aktifFonksiyon.değişkenNoMap.put(aktifFonksiyon.geçiciDeğişkenNo,
							new Değişken(aktifFonksiyon.geçiciDeğişkenNo, null, Değişkenler.ID_i64));
					Cümle_02GeçiciDeğişkenYeni komut1 = new Cümle_02GeçiciDeğişkenYeni(aktifFonksiyon.geçiciDeğişkenNo,
							Değişkenler.ID_i64);
					aktifFonksiyon.cümleler.add(komut1);
					Cümle_10SabitAtama komut = new Cümle_10SabitAtama(aktifFonksiyon.geçiciDeğişkenNo, tamSayı,
							Değişkenler.ID_i64);
					aktifFonksiyon.cümleler.add(komut);
					değişkenNo1 = komut.değişkenNo;
					değişkenNo1TipId = komut.sabitVeriTipiId;
				}
				int değişkenNo2;
				int değişkenNo2TipId;
				if (cümle[4].tip == SÖZCÜK.TİP_01İSİM) {
					String isim2 = ((Sözcük_01İsim) cümle[4]).isim;
					if (!aktifFonksiyon.değişkenİsimMap.containsKey(isim2)) {
						hata02_TanımsızDeğişken(isim2);
						continue;
					}
					Değişken değişken = aktifFonksiyon.değişkenİsimMap.get(isim2);
					değişkenNo2 = değişken.değişkenNo;
					değişkenNo2TipId = değişken.değişkenTipiId;
				} else {
					String tamSayı = ((Sözcük_03TamSayı) cümle[4]).tamSayı;
					if (Fonksiyonlar.parseLong(tamSayı) == null) {
						hata05_SayıVeriTipineSığmıyor(tamSayı, Değişkenler.STRING_i64);
						continue;
					}
					aktifFonksiyon.geçiciDeğişkenNo--;
					aktifFonksiyon.değişkenNoMap.put(aktifFonksiyon.geçiciDeğişkenNo,
							new Değişken(aktifFonksiyon.geçiciDeğişkenNo, null, Değişkenler.ID_i64));
					Cümle_02GeçiciDeğişkenYeni komut1 = new Cümle_02GeçiciDeğişkenYeni(aktifFonksiyon.geçiciDeğişkenNo,
							Değişkenler.ID_i64);
					aktifFonksiyon.cümleler.add(komut1);
					Cümle_10SabitAtama komut = new Cümle_10SabitAtama(aktifFonksiyon.geçiciDeğişkenNo, tamSayı,
							Değişkenler.ID_i64);
					aktifFonksiyon.cümleler.add(komut);
					değişkenNo2 = komut.değişkenNo;
					değişkenNo2TipId = komut.sabitVeriTipiId;
				}
				String operatör = ((Sözcük_02Operatör) cümle[3]).operatör;
				String anahtar = değişkenNo1TipId + operatör + değişkenNo2TipId;
				if (!operatörFonksiyonMap.containsKey(anahtar)) {
					hata04_TanımsızOperatör(Değişkenler.TİP_MAP_ID_STR.get(değişkenNo1TipId) + " " + operatör + " "
							+ Değişkenler.TİP_MAP_ID_STR.get(değişkenNo2TipId));
					continue;
				}
				aktifFonksiyon.cümleler
						.add(new Cümle_04Operatörİşlemi(değişkenNo0, operatör, değişkenNo1, değişkenNo2));
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
					String isimHedef = ((Sözcük_01İsim) cümle[4]).isim;
					if (!aktifFonksiyon.değişkenİsimMap.containsKey(isimHedef)) {
						hata02_TanımsızDeğişken(isimHedef);
						continue;
					}
					değişkenNo0 = aktifFonksiyon.değişkenİsimMap.get(isimHedef).değişkenNo;
				} else {
					String değişkenİsim = ((Sözcük_01İsim) cümle[4]).isim;
					if (değişkenİsmiKontrol(değişkenİsim)) {
						hata04_DeğişkenİsmiUygunDeğil(değişkenİsim);
						continue;
					} else if (aktifFonksiyon.değişkenİsimMap.containsKey(değişkenİsim)) {
						hata03_DeğişkenİsimÇakışması(değişkenİsim);
						continue;
					}
					aktifFonksiyon.gerçekDeğişkenNo++;
					Değişken değişken = new Değişken(aktifFonksiyon.gerçekDeğişkenNo, değişkenİsim, Değişkenler.ID_i64);
					aktifFonksiyon.değişkenNoMap.put(değişken.değişkenNo, değişken);
					aktifFonksiyon.değişkenİsimMap.put(değişkenİsim, değişken);
					aktifFonksiyon.cümleler
							.add(new Cümle_01DeğişkenYeni(değişken.değişkenNo, değişkenİsim, değişken.değişkenTipiId));
					değişkenNo0 = değişken.değişkenNo;
				}
				int değişkenNo1;
				int değişkenNo1TipId;
				if (cümle[0].tip == SÖZCÜK.TİP_01İSİM) {
					String isim1 = ((Sözcük_01İsim) cümle[0]).isim;
					if (!aktifFonksiyon.değişkenİsimMap.containsKey(isim1)) {
						hata02_TanımsızDeğişken(isim1);
					}
					Değişken değişken = aktifFonksiyon.değişkenİsimMap.get(isim1);
					değişkenNo1 = değişken.değişkenNo;
					değişkenNo1TipId = değişken.değişkenTipiId;
				} else {
					String tamSayı = ((Sözcük_03TamSayı) cümle[0]).tamSayı;
					if (Fonksiyonlar.parseLong(tamSayı) == null) {
						hata05_SayıVeriTipineSığmıyor(tamSayı, Değişkenler.STRING_i64);
						continue;
					}
					aktifFonksiyon.geçiciDeğişkenNo--;
					aktifFonksiyon.değişkenNoMap.put(aktifFonksiyon.geçiciDeğişkenNo,
							new Değişken(aktifFonksiyon.geçiciDeğişkenNo, null, Değişkenler.ID_i64));
					Cümle_02GeçiciDeğişkenYeni komut1 = new Cümle_02GeçiciDeğişkenYeni(aktifFonksiyon.geçiciDeğişkenNo,
							Değişkenler.ID_i64);
					aktifFonksiyon.cümleler.add(komut1);
					Cümle_10SabitAtama komut = new Cümle_10SabitAtama(aktifFonksiyon.geçiciDeğişkenNo, tamSayı,
							Değişkenler.ID_i64);
					aktifFonksiyon.cümleler.add(komut);
					değişkenNo1 = komut.değişkenNo;
					değişkenNo1TipId = komut.sabitVeriTipiId;
				}
				int değişkenNo2;
				int değişkenNo2TipId;
				if (cümle[2].tip == SÖZCÜK.TİP_01İSİM) {
					String isim2 = ((Sözcük_01İsim) cümle[2]).isim;
					if (!aktifFonksiyon.değişkenİsimMap.containsKey(isim2)) {
						hata02_TanımsızDeğişken(isim2);
					}
					Değişken değişken = aktifFonksiyon.değişkenİsimMap.get(isim2);
					değişkenNo2 = değişken.değişkenNo;
					değişkenNo2TipId = değişken.değişkenTipiId;
				} else {
					String tamSayı = ((Sözcük_03TamSayı) cümle[2]).tamSayı;
					if (Fonksiyonlar.parseLong(tamSayı) == null) {
						hata05_SayıVeriTipineSığmıyor(tamSayı, Değişkenler.STRING_i64);
						continue;
					}
					aktifFonksiyon.geçiciDeğişkenNo--;
					aktifFonksiyon.değişkenNoMap.put(aktifFonksiyon.geçiciDeğişkenNo,
							new Değişken(aktifFonksiyon.geçiciDeğişkenNo, null, Değişkenler.ID_i64));
					Cümle_02GeçiciDeğişkenYeni komut1 = new Cümle_02GeçiciDeğişkenYeni(aktifFonksiyon.geçiciDeğişkenNo,
							Değişkenler.ID_i64);
					aktifFonksiyon.cümleler.add(komut1);
					Cümle_10SabitAtama komut = new Cümle_10SabitAtama(aktifFonksiyon.geçiciDeğişkenNo, tamSayı,
							Değişkenler.ID_i64);
					aktifFonksiyon.cümleler.add(komut);
					değişkenNo2 = komut.değişkenNo;
					değişkenNo2TipId = komut.sabitVeriTipiId;
				}
				String operatör = ((Sözcük_02Operatör) cümle[1]).operatör;
				String anahtar = değişkenNo1TipId + operatör + değişkenNo2TipId;
				if (!operatörFonksiyonMap.containsKey(anahtar)) {
					hata04_TanımsızOperatör(Değişkenler.TİP_MAP_ID_STR.get(değişkenNo1TipId) + " " + operatör + " "
							+ Değişkenler.TİP_MAP_ID_STR.get(değişkenNo2TipId));
					continue;
				}
				aktifFonksiyon.cümleler
						.add(new Cümle_04Operatörİşlemi(değişkenNo0, operatör, değişkenNo1, değişkenNo2));
			} else {
				hatalar.append("BİLİNMEYEN : ");
				for (Sözcük c : cümle) {
					hatalar.append(c);
					hatalar.append(" ");
				}
				hatalar.append("!!!\n");
			}
		}

		StringBuilder sb = new StringBuilder();
		for (Fonksiyon_01OperatörFonksiyon operatörFonksiyon : operatörFonksiyonMap.values()) {
			sb.append(operatörFonksiyon.toString());
			sb.append("\n");
			for (Cümle cümle : operatörFonksiyon.cümleler) {
				sb.append("\t");
				sb.append(cümle.toString());
				sb.append("\n");
			}
		}
		for (Fonksiyon_02İsimliFonksiyon isimFonksiyon : isimFonksiyonMap.values()) {
			sb.append(isimFonksiyon.toString());
			sb.append("\n");
			for (Cümle cümle : isimFonksiyon.cümleler) {
				sb.append("\t");
				sb.append(cümle.toString());
				sb.append("\n");
			}
		}
		{
			sb.append(anaFonksiyon.toString());
			sb.append("\n");
			for (Cümle cümle : anaFonksiyon.cümleler) {
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
		return new Aşama3Çıktısı(operatörFonksiyonMap, isimFonksiyonMap, anaFonksiyon);
	}

}
