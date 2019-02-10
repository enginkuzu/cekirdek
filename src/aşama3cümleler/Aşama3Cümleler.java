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
import yardımcı.Değişkenler.SÖZCÜK;

public class Aşama3Cümleler {

	private static HashMap<String, Fonksiyon_01OperatörFonksiyon> operatörFonksiyonMap;
	private static HashMap<String, Fonksiyon_02İsimliFonksiyon> isimFonksiyonMap;
	private static Fonksiyon_03AnaFonksiyon anaFonksiyon;
	private static Fonksiyon aktifFonksiyon;

	public static Object işle(ArrayList<Sözcük[]> tümCümleler, String dosyaAdı) {

		operatörFonksiyonMap = new HashMap<>();
		isimFonksiyonMap = new HashMap<>();
		anaFonksiyon = new Fonksiyon_03AnaFonksiyon();
		aktifFonksiyon = anaFonksiyon;

		StringBuilder hatalar = new StringBuilder();

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
				Fonksiyon_01OperatörFonksiyon operatörFonksiyon = new Fonksiyon_01OperatörFonksiyon();
				operatörFonksiyon.özellikMap = aktifFonksiyon.özellikMap;
				operatörFonksiyon.inline = true;
				operatörFonksiyon.öncelik = ((Sözcük_01İsim) cümle[1]).isim.equals("p1") ? 1 : 2;
				operatörFonksiyon.değişken1İsim = ((Sözcük_01İsim) cümle[3]).isim;
				operatörFonksiyon.değişken1Tip = ((Sözcük_01İsim) cümle[5]).isim;
				operatörFonksiyon.operatör = ((Sözcük_02Operatör) cümle[7]).operatör;
				operatörFonksiyon.değişken2İsim = ((Sözcük_01İsim) cümle[9]).isim;
				operatörFonksiyon.değişken2Tip = ((Sözcük_01İsim) cümle[11]).isim;
				operatörFonksiyon.sonuçİsim = ((Sözcük_01İsim) cümle[13]).isim;
				operatörFonksiyon.sonuçTip = ((Sözcük_01İsim) cümle[15]).isim;
				aktifFonksiyon.özellikMap = new HashMap<>();
				aktifFonksiyon = operatörFonksiyon;
				//
				String anahtar = operatörFonksiyon.değişken1Tip + operatörFonksiyon.operatör
						+ operatörFonksiyon.değişken2Tip;
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
					hatalar.append("Tanımsız Değişken : " + isimKaynak + " !!!\n");
				} else if (!aktifFonksiyon.değişkenİsimMap.containsKey(isimHedef)) {
					hatalar.append("Tanımsız Değişken : " + isimHedef + " !!!\n");
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
					hatalar.append("Tanımsız Değişken : " + isimKaynak + " !!!\n");
				} else if (!aktifFonksiyon.değişkenİsimMap.containsKey(isimHedef)) {
					hatalar.append("Tanımsız Değişken : " + isimHedef + " !!!\n");
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
				if (!aktifFonksiyon.değişkenİsimMap.containsKey(isimHedef)) {
					hatalar.append("Tanımsız Değişken : " + isimHedef + " !!!\n");
				} else {
					aktifFonksiyon.cümleler
							.add(new Cümle_10SabitAtama(aktifFonksiyon.değişkenİsimMap.get(isimHedef).değişkenNo,
									((Sözcük_03TamSayı) cümle[0]).tamSayı, "i64"));
				}
			} else if (cümle.length == 4 && cümle[0].tip == SÖZCÜK.TİP_01İSİM && cümle[1].tip == SÖZCÜK.TİP_09ATAMA_SOLA
					&& cümle[2].tip == SÖZCÜK.TİP_03TAM_SAYI && cümle[3].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				// sayı2 < 123;
				String isimHedef = ((Sözcük_01İsim) cümle[0]).isim;
				if (!aktifFonksiyon.değişkenİsimMap.containsKey(isimHedef)) {
					hatalar.append("Tanımsız Değişken : " + isimHedef + " !!!\n");
				} else {
					aktifFonksiyon.cümleler
							.add(new Cümle_10SabitAtama(aktifFonksiyon.değişkenİsimMap.get(isimHedef).değişkenNo,
									((Sözcük_03TamSayı) cümle[2]).tamSayı, "i64"));
				}
			} else if (cümle.length == 4 && (cümle[0].tip == SÖZCÜK.TİP_01İSİM || cümle[0].tip == SÖZCÜK.TİP_03TAM_SAYI)
					&& cümle[1].tip == SÖZCÜK.TİP_08NOKTA && cümle[2].tip == SÖZCÜK.TİP_01İSİM
					&& cümle[3].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				// sayı1.printhn;
				// 123.printhn;
				int değişkenNo;
				if (cümle[0].tip == SÖZCÜK.TİP_01İSİM) {
					String isim = ((Sözcük_01İsim) cümle[0]).isim;
					if (!aktifFonksiyon.değişkenİsimMap.containsKey(isim)) {
						hatalar.append("Tanımsız Değişken : " + isim + " !!!\n");
						continue;
					}
					değişkenNo = aktifFonksiyon.değişkenİsimMap.get(isim).değişkenNo;
				} else {
					Sözcük_03TamSayı tamsayı = (Sözcük_03TamSayı) cümle[0];
					aktifFonksiyon.geçiciDeğişkenNo--;
					aktifFonksiyon.değişkenNoMap.put(aktifFonksiyon.geçiciDeğişkenNo,
							new Değişken(aktifFonksiyon.geçiciDeğişkenNo, null, "i64"));
					Cümle_02GeçiciDeğişkenYeni komut1 = new Cümle_02GeçiciDeğişkenYeni(aktifFonksiyon.geçiciDeğişkenNo,
							"i64");
					aktifFonksiyon.cümleler.add(komut1);
					Cümle_10SabitAtama komut = new Cümle_10SabitAtama(aktifFonksiyon.geçiciDeğişkenNo, tamsayı.tamSayı,
							"i64");
					aktifFonksiyon.cümleler.add(komut);
					değişkenNo = komut.değişkenNo;
				}
				aktifFonksiyon.geçiciDeğişkenNo--;
				aktifFonksiyon.değişkenNoMap.put(aktifFonksiyon.geçiciDeğişkenNo,
						new Değişken(aktifFonksiyon.geçiciDeğişkenNo, null, "i64"));
				Cümle_02GeçiciDeğişkenYeni komut1 = new Cümle_02GeçiciDeğişkenYeni(aktifFonksiyon.geçiciDeğişkenNo,
						"i64");
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
				if (aktifFonksiyon.değişkenİsimMap.containsKey(değişkenİsim)) {
					hatalar.append("Çok Defa Değişken Tanımlama : " + değişkenİsim + " !!!\n");
					continue;
				}
				aktifFonksiyon.gerçekDeğişkenNo++;
				Değişken değişken = new Değişken(aktifFonksiyon.gerçekDeğişkenNo, değişkenİsim,
						((Sözcük_01İsim) cümle[2]).isim);
				aktifFonksiyon.değişkenNoMap.put(değişken.değişkenNo, değişken);
				aktifFonksiyon.değişkenİsimMap.put(değişkenİsim, değişken);
				aktifFonksiyon.cümleler
						.add(new Cümle_01DeğişkenYeni(değişken.değişkenNo, değişkenİsim, değişken.değişkenTipi));
				if (cümle[4].tip == SÖZCÜK.TİP_01İSİM) {
					String isim = ((Sözcük_01İsim) cümle[4]).isim;
					if (!aktifFonksiyon.değişkenİsimMap.containsKey(isim)) {
						hatalar.append("Tanımsız Değişken : " + isim + " !!!\n");
						continue;
					}
					int değişkenNo = aktifFonksiyon.değişkenİsimMap.get(isim).değişkenNo;
					aktifFonksiyon.cümleler.add(new Cümle_06DeğişkenAtama(değişkenNo, değişken.değişkenNo));
				} else {
					aktifFonksiyon.cümleler.add(new Cümle_10SabitAtama(aktifFonksiyon.gerçekDeğişkenNo,
							((Sözcük_03TamSayı) cümle[4]).tamSayı, "i64"));
				}
			} else if (cümle.length == 6 && (cümle[0].tip == SÖZCÜK.TİP_01İSİM || cümle[0].tip == SÖZCÜK.TİP_03TAM_SAYI)
					&& cümle[1].tip == SÖZCÜK.TİP_10ATAMA_SAĞA && cümle[2].tip == SÖZCÜK.TİP_01İSİM
					&& cümle[3].tip == SÖZCÜK.TİP_07DEĞİŞKEN_TİPİ && cümle[4].tip == SÖZCÜK.TİP_01İSİM
					&& cümle[5].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				// sayı1 > sayı:i64;
				// 123 > sayı:i64;
				String değişkenİsim = ((Sözcük_01İsim) cümle[2]).isim;
				if (aktifFonksiyon.değişkenİsimMap.containsKey(değişkenİsim)) {
					hatalar.append("Çok Defa Değişken Tanımlama : " + değişkenİsim + " !!!\n");
					continue;
				}
				aktifFonksiyon.gerçekDeğişkenNo++;
				Değişken değişken = new Değişken(aktifFonksiyon.gerçekDeğişkenNo, değişkenİsim,
						((Sözcük_01İsim) cümle[4]).isim);
				aktifFonksiyon.değişkenNoMap.put(değişken.değişkenNo, değişken);
				aktifFonksiyon.değişkenİsimMap.put(değişkenİsim, değişken);
				aktifFonksiyon.cümleler
						.add(new Cümle_01DeğişkenYeni(değişken.değişkenNo, değişkenİsim, değişken.değişkenTipi));
				if (cümle[0].tip == SÖZCÜK.TİP_01İSİM) {
					String isim = ((Sözcük_01İsim) cümle[0]).isim;
					if (!aktifFonksiyon.değişkenİsimMap.containsKey(isim)) {
						hatalar.append("Tanımsız Değişken : " + isim + " !!!\n");
						continue;
					}
					int değişkenNo = aktifFonksiyon.değişkenİsimMap.get(isim).değişkenNo;
					aktifFonksiyon.cümleler.add(new Cümle_06DeğişkenAtama(değişkenNo, değişken.değişkenNo));
				} else {
					aktifFonksiyon.cümleler.add(new Cümle_10SabitAtama(aktifFonksiyon.gerçekDeğişkenNo,
							((Sözcük_03TamSayı) cümle[0]).tamSayı, "i64"));
				}
			} else if (cümle.length == 4 && cümle[0].tip == SÖZCÜK.TİP_01İSİM
					&& cümle[1].tip == SÖZCÜK.TİP_11TANIMLAMA_SOLA
					&& (cümle[2].tip == SÖZCÜK.TİP_01İSİM || cümle[2].tip == SÖZCÜK.TİP_03TAM_SAYI)
					&& cümle[3].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				// sayı <: sayı1;
				// sayı <: 123;
				String değişkenİsim = ((Sözcük_01İsim) cümle[0]).isim;
				if (aktifFonksiyon.değişkenİsimMap.containsKey(değişkenİsim)) {
					hatalar.append("Çok Defa Değişken Tanımlama : " + değişkenİsim + " !!!\n");
					continue;
				}
				aktifFonksiyon.gerçekDeğişkenNo++;
				Değişken değişken = new Değişken(aktifFonksiyon.gerçekDeğişkenNo, değişkenİsim, "i64");
				aktifFonksiyon.değişkenNoMap.put(değişken.değişkenNo, değişken);
				aktifFonksiyon.değişkenİsimMap.put(değişkenİsim, değişken);
				aktifFonksiyon.cümleler
						.add(new Cümle_01DeğişkenYeni(değişken.değişkenNo, değişkenİsim, değişken.değişkenTipi));
				if (cümle[2].tip == SÖZCÜK.TİP_01İSİM) {
					String isim = ((Sözcük_01İsim) cümle[2]).isim;
					if (!aktifFonksiyon.değişkenİsimMap.containsKey(isim)) {
						hatalar.append("Tanımsız Değişken : " + isim + " !!!\n");
						continue;
					}
					int değişkenNo = aktifFonksiyon.değişkenİsimMap.get(isim).değişkenNo;
					aktifFonksiyon.cümleler.add(new Cümle_06DeğişkenAtama(değişkenNo, değişken.değişkenNo));
				} else {
					aktifFonksiyon.cümleler.add(new Cümle_10SabitAtama(aktifFonksiyon.gerçekDeğişkenNo,
							((Sözcük_03TamSayı) cümle[2]).tamSayı, "i64"));
				}
			} else if (cümle.length == 4 && (cümle[0].tip == SÖZCÜK.TİP_01İSİM || cümle[0].tip == SÖZCÜK.TİP_03TAM_SAYI)
					&& cümle[1].tip == SÖZCÜK.TİP_12TANIMLAMA_SAĞA && cümle[2].tip == SÖZCÜK.TİP_01İSİM
					&& cümle[3].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				// sayı1 :> sayı;
				// 123 :> sayı;
				String değişkenİsim = ((Sözcük_01İsim) cümle[2]).isim;
				if (aktifFonksiyon.değişkenİsimMap.containsKey(değişkenİsim)) {
					hatalar.append("Çok Defa Değişken Tanımlama : " + değişkenİsim + " !!!\n");
					continue;
				}
				aktifFonksiyon.gerçekDeğişkenNo++;
				Değişken değişken = new Değişken(aktifFonksiyon.gerçekDeğişkenNo, değişkenİsim, "i64");
				aktifFonksiyon.değişkenNoMap.put(değişken.değişkenNo, değişken);
				aktifFonksiyon.değişkenİsimMap.put(değişkenİsim, değişken);
				aktifFonksiyon.cümleler
						.add(new Cümle_01DeğişkenYeni(değişken.değişkenNo, değişkenİsim, değişken.değişkenTipi));
				if (cümle[0].tip == SÖZCÜK.TİP_01İSİM) {
					String isim = ((Sözcük_01İsim) cümle[0]).isim;
					if (!aktifFonksiyon.değişkenİsimMap.containsKey(isim)) {
						hatalar.append("Tanımsız Değişken : " + isim + " !!!\n");
						continue;
					}
					int değişkenNo = aktifFonksiyon.değişkenİsimMap.get(isim).değişkenNo;
					aktifFonksiyon.cümleler.add(new Cümle_06DeğişkenAtama(değişkenNo, değişken.değişkenNo));
				} else {
					aktifFonksiyon.cümleler.add(new Cümle_10SabitAtama(aktifFonksiyon.gerçekDeğişkenNo,
							((Sözcük_03TamSayı) cümle[0]).tamSayı, "i64"));
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
						hatalar.append("Tanımsız Değişken : " + isimHedef + " !!!\n");
						continue;
					}
					değişkenNo0 = aktifFonksiyon.değişkenİsimMap.get(isimHedef).değişkenNo;
				} else {
					String değişkenİsim = ((Sözcük_01İsim) cümle[0]).isim;
					if (aktifFonksiyon.değişkenİsimMap.containsKey(değişkenİsim)) {
						hatalar.append("Çok Defa Değişken Tanımlama : " + değişkenİsim + " !!!\n");
						continue;
					}
					aktifFonksiyon.gerçekDeğişkenNo++;
					Değişken değişken = new Değişken(aktifFonksiyon.gerçekDeğişkenNo, değişkenİsim, "i64");
					aktifFonksiyon.değişkenNoMap.put(değişken.değişkenNo, değişken);
					aktifFonksiyon.değişkenİsimMap.put(değişkenİsim, değişken);
					aktifFonksiyon.cümleler
							.add(new Cümle_01DeğişkenYeni(değişken.değişkenNo, değişkenİsim, değişken.değişkenTipi));
					değişkenNo0 = değişken.değişkenNo;
				}
				int değişkenNo1;
				if (cümle[2].tip == SÖZCÜK.TİP_01İSİM) {
					String isim1 = ((Sözcük_01İsim) cümle[2]).isim;
					if (!aktifFonksiyon.değişkenİsimMap.containsKey(isim1)) {
						hatalar.append("Tanımsız Değişken : " + isim1 + " !!!\n");
						continue;
					}
					değişkenNo1 = aktifFonksiyon.değişkenİsimMap.get(isim1).değişkenNo;
				} else {
					Sözcük_03TamSayı tamsayı = (Sözcük_03TamSayı) cümle[2];
					aktifFonksiyon.geçiciDeğişkenNo--;
					aktifFonksiyon.değişkenNoMap.put(aktifFonksiyon.geçiciDeğişkenNo,
							new Değişken(aktifFonksiyon.geçiciDeğişkenNo, null, "i64"));
					Cümle_02GeçiciDeğişkenYeni komut1 = new Cümle_02GeçiciDeğişkenYeni(aktifFonksiyon.geçiciDeğişkenNo,
							"i64");
					aktifFonksiyon.cümleler.add(komut1);
					Cümle_10SabitAtama komut = new Cümle_10SabitAtama(aktifFonksiyon.geçiciDeğişkenNo, tamsayı.tamSayı,
							"i64");
					aktifFonksiyon.cümleler.add(komut);
					değişkenNo1 = komut.değişkenNo;
				}
				int değişkenNo2;
				if (cümle[4].tip == SÖZCÜK.TİP_01İSİM) {
					String isim2 = ((Sözcük_01İsim) cümle[4]).isim;
					if (!aktifFonksiyon.değişkenİsimMap.containsKey(isim2)) {
						hatalar.append("Tanımsız Değişken : " + isim2 + " !!!\n");
						continue;
					}
					değişkenNo2 = aktifFonksiyon.değişkenİsimMap.get(isim2).değişkenNo;
				} else {
					Sözcük_03TamSayı tamsayı = (Sözcük_03TamSayı) cümle[4];
					aktifFonksiyon.geçiciDeğişkenNo--;
					aktifFonksiyon.değişkenNoMap.put(aktifFonksiyon.geçiciDeğişkenNo,
							new Değişken(aktifFonksiyon.geçiciDeğişkenNo, null, "i64"));
					Cümle_02GeçiciDeğişkenYeni komut1 = new Cümle_02GeçiciDeğişkenYeni(aktifFonksiyon.geçiciDeğişkenNo,
							"i64");
					aktifFonksiyon.cümleler.add(komut1);
					Cümle_10SabitAtama komut = new Cümle_10SabitAtama(aktifFonksiyon.geçiciDeğişkenNo, tamsayı.tamSayı,
							"i64");
					aktifFonksiyon.cümleler.add(komut);
					değişkenNo2 = komut.değişkenNo;
				}
				aktifFonksiyon.cümleler.add(new Cümle_04Operatörİşlemi(değişkenNo0,
						((Sözcük_02Operatör) cümle[3]).operatör, değişkenNo1, değişkenNo2));
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
						hatalar.append("Tanımsız Değişken : " + isimHedef + " !!!\n");
						continue;
					}
					değişkenNo0 = aktifFonksiyon.değişkenİsimMap.get(isimHedef).değişkenNo;
				} else {
					String değişkenİsim = ((Sözcük_01İsim) cümle[4]).isim;
					if (aktifFonksiyon.değişkenİsimMap.containsKey(değişkenİsim)) {
						hatalar.append("Çok Defa Değişken Tanımlama : " + değişkenİsim + " !!!\n");
						continue;
					}
					aktifFonksiyon.gerçekDeğişkenNo++;
					Değişken değişken = new Değişken(aktifFonksiyon.gerçekDeğişkenNo, değişkenİsim, "i64");
					aktifFonksiyon.değişkenNoMap.put(değişken.değişkenNo, değişken);
					aktifFonksiyon.değişkenİsimMap.put(değişkenİsim, değişken);
					aktifFonksiyon.cümleler
							.add(new Cümle_01DeğişkenYeni(değişken.değişkenNo, değişkenİsim, değişken.değişkenTipi));
					değişkenNo0 = değişken.değişkenNo;
				}
				int değişkenNo1;
				if (cümle[0].tip == SÖZCÜK.TİP_01İSİM) {
					String isim1 = ((Sözcük_01İsim) cümle[0]).isim;
					if (!aktifFonksiyon.değişkenİsimMap.containsKey(isim1)) {
						hatalar.append("Tanımsız Değişken : " + isim1 + " !!!\n");
					}
					değişkenNo1 = aktifFonksiyon.değişkenİsimMap.get(isim1).değişkenNo;
				} else {
					Sözcük_03TamSayı tamsayı = (Sözcük_03TamSayı) cümle[0];
					aktifFonksiyon.geçiciDeğişkenNo--;
					aktifFonksiyon.değişkenNoMap.put(aktifFonksiyon.geçiciDeğişkenNo,
							new Değişken(aktifFonksiyon.geçiciDeğişkenNo, null, "i64"));
					Cümle_02GeçiciDeğişkenYeni komut1 = new Cümle_02GeçiciDeğişkenYeni(aktifFonksiyon.geçiciDeğişkenNo,
							"i64");
					aktifFonksiyon.cümleler.add(komut1);
					Cümle_10SabitAtama komut = new Cümle_10SabitAtama(aktifFonksiyon.geçiciDeğişkenNo, tamsayı.tamSayı,
							"i64");
					aktifFonksiyon.cümleler.add(komut);
					değişkenNo1 = komut.değişkenNo;
				}
				int değişkenNo2;
				if (cümle[2].tip == SÖZCÜK.TİP_01İSİM) {
					String isim2 = ((Sözcük_01İsim) cümle[2]).isim;
					if (!aktifFonksiyon.değişkenİsimMap.containsKey(isim2)) {
						hatalar.append("Tanımsız Değişken : " + isim2 + " !!!\n");
					}
					değişkenNo2 = aktifFonksiyon.değişkenİsimMap.get(isim2).değişkenNo;
				} else {
					Sözcük_03TamSayı tamsayı = (Sözcük_03TamSayı) cümle[2];
					aktifFonksiyon.geçiciDeğişkenNo--;
					aktifFonksiyon.değişkenNoMap.put(aktifFonksiyon.geçiciDeğişkenNo,
							new Değişken(aktifFonksiyon.geçiciDeğişkenNo, null, "i64"));
					Cümle_02GeçiciDeğişkenYeni komut1 = new Cümle_02GeçiciDeğişkenYeni(aktifFonksiyon.geçiciDeğişkenNo,
							"i64");
					aktifFonksiyon.cümleler.add(komut1);
					Cümle_10SabitAtama komut = new Cümle_10SabitAtama(aktifFonksiyon.geçiciDeğişkenNo, tamsayı.tamSayı,
							"i64");
					aktifFonksiyon.cümleler.add(komut);
					değişkenNo2 = komut.değişkenNo;
				}
				aktifFonksiyon.cümleler.add(new Cümle_04Operatörİşlemi(değişkenNo0,
						((Sözcük_02Operatör) cümle[1]).operatör, değişkenNo1, değişkenNo2));
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
