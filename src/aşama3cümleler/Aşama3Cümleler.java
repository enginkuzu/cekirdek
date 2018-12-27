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

	public static Aşama3Çıktısı işle(ArrayList<Sözcük[]> tümCümleler, String dosyaAdı) throws Exception {

		operatörFonksiyonMap = new HashMap<>();
		isimFonksiyonMap = new HashMap<>();
		anaFonksiyon = new Fonksiyon_03AnaFonksiyon();
		aktifFonksiyon = anaFonksiyon;

		for (Sözcük[] cümle : tümCümleler) {
			if (cümle[0].tip == SÖZCÜK.TİP_13ÖZELLİK && cümle[cümle.length - 1].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				HashSet<String> özellikSet = new HashSet<String>();
				for (int i = 2; i < cümle.length - 1; i++) {
					özellikSet.add(((Sözcük_01İsim) cümle[i]).isim);
				}
				aktifFonksiyon.özellikMap.put(((Sözcük_01İsim) cümle[1]).isim, özellikSet);
			} else if (cümle[1].tip == SÖZCÜK.TİP_14AÇPARANTEZ) {
				Fonksiyon_02İsimliFonksiyon isimFonksiyon = new Fonksiyon_02İsimliFonksiyon();
				isimFonksiyon.isim = ((Sözcük_01İsim) cümle[0]).isim;
				if (cümle.length > 5 && cümle[5].tip == SÖZCÜK.TİP_15KAPAPARANTEZ) {
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
				aktifFonksiyon.cümleler.add(new Cümle_08AssemblyData(((Sözcük_05Metin) cümle[0]).metin));
				aktifFonksiyon.özellikMap = new HashMap<>();
			} else if (aktifFonksiyon.özellikMap.containsKey("asm-rodata")) {
				aktifFonksiyon.cümleler.add(new Cümle_09AssemblyRoData(((Sözcük_05Metin) cümle[0]).metin));
				aktifFonksiyon.özellikMap = new HashMap<>();
			} else if (aktifFonksiyon.özellikMap.containsKey("asm")) {
				aktifFonksiyon.cümleler.add(new Cümle_07Assembly(((Sözcük_05Metin) cümle[0]).metin));
				aktifFonksiyon.özellikMap = new HashMap<>();
			} else if (cümle[0].tip == SÖZCÜK.TİP_17KAPASÜSLÜ && cümle[1].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				aktifFonksiyon = anaFonksiyon;
			} else if (cümle.length == 4 && cümle[0].tip == SÖZCÜK.TİP_01İSİM && cümle[1].tip == SÖZCÜK.TİP_09ATAMA_SOLA
					&& cümle[2].tip == SÖZCÜK.TİP_01İSİM && cümle[3].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				// sayı2 < sayı1;
				String isimKaynak = ((Sözcük_01İsim) cümle[2]).isim;
				String isimHedef = ((Sözcük_01İsim) cümle[0]).isim;
				if (!aktifFonksiyon.değişkenİsimMap.containsKey(isimKaynak)) {
					throw new Exception("Tanımsız Değişken : " + isimKaynak + " !!!");
				} else if (!aktifFonksiyon.değişkenİsimMap.containsKey(isimHedef)) {
					throw new Exception("Tanımsız Değişken : " + isimHedef + " !!!");
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
					throw new Exception("Tanımsız Değişken : " + isimKaynak + " !!!");
				} else if (!aktifFonksiyon.değişkenİsimMap.containsKey(isimHedef)) {
					throw new Exception("Tanımsız Değişken : " + isimHedef + " !!!");
				} else {
					aktifFonksiyon.cümleler
							.add(new Cümle_06DeğişkenAtama(aktifFonksiyon.değişkenİsimMap.get(isimKaynak).değişkenNo,
									aktifFonksiyon.değişkenİsimMap.get(isimHedef).değişkenNo));
				}
			} else if (cümle.length == 4 && cümle[0].tip == SÖZCÜK.TİP_01İSİM && cümle[1].tip == SÖZCÜK.TİP_08NOKTA
					&& cümle[2].tip == SÖZCÜK.TİP_01İSİM && cümle[3].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				// sayı1.println;
				String isim = ((Sözcük_01İsim) cümle[0]).isim;
				if (!aktifFonksiyon.değişkenİsimMap.containsKey(isim)) {
					throw new Exception("Tanımsız Değişken : " + isim + " !!!");
				} else {
					aktifFonksiyon.geçiciDeğişkenNo--;
					Cümle_04FonksiyonÇağrısı komut = new Cümle_04FonksiyonÇağrısı(aktifFonksiyon.geçiciDeğişkenNo,
							((Sözcük_01İsim) cümle[2]).isim, aktifFonksiyon.değişkenİsimMap.get(isim).değişkenNo);
					aktifFonksiyon.cümleler.add(komut);
				}
			} else if (cümle.length == 6 && cümle[0].tip == SÖZCÜK.TİP_01İSİM
					&& cümle[1].tip == SÖZCÜK.TİP_07DEĞİŞKEN_TİPİ && cümle[2].tip == SÖZCÜK.TİP_01İSİM
					&& cümle[3].tip == SÖZCÜK.TİP_09ATAMA_SOLA && cümle[4].tip == SÖZCÜK.TİP_03TAM_SAYI
					&& cümle[5].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				// sayı:i64 < 123;
				aktifFonksiyon.gerçekDeğişkenNo++;
				Değişken değişken = new Değişken(aktifFonksiyon.gerçekDeğişkenNo, ((Sözcük_01İsim) cümle[0]).isim,
						((Sözcük_01İsim) cümle[2]).isim);
				aktifFonksiyon.değişkenNoMap.put(değişken.değişkenNo, değişken);
				aktifFonksiyon.değişkenİsimMap.put(değişken.değişkenİsim, değişken);
				aktifFonksiyon.cümleler.add(
						new Cümle_01DeğişkenYeni(değişken.değişkenNo, değişken.değişkenİsim, değişken.değişkenTipi));
				aktifFonksiyon.cümleler.add(new Cümle_05SabitTanımlama(aktifFonksiyon.gerçekDeğişkenNo,
						((Sözcük_03TamSayı) cümle[4]).sayı, "i64"));
			} else if (cümle.length == 6 && cümle[0].tip == SÖZCÜK.TİP_03TAM_SAYI
					&& cümle[1].tip == SÖZCÜK.TİP_10ATAMA_SAĞA && cümle[2].tip == SÖZCÜK.TİP_01İSİM
					&& cümle[3].tip == SÖZCÜK.TİP_07DEĞİŞKEN_TİPİ && cümle[4].tip == SÖZCÜK.TİP_01İSİM
					&& cümle[5].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				// 123 > sayı:i64;
				aktifFonksiyon.gerçekDeğişkenNo++;
				Değişken değişken = new Değişken(aktifFonksiyon.gerçekDeğişkenNo, ((Sözcük_01İsim) cümle[2]).isim,
						((Sözcük_01İsim) cümle[4]).isim);
				aktifFonksiyon.değişkenNoMap.put(değişken.değişkenNo, değişken);
				aktifFonksiyon.değişkenİsimMap.put(değişken.değişkenİsim, değişken);
				aktifFonksiyon.cümleler.add(
						new Cümle_01DeğişkenYeni(değişken.değişkenNo, değişken.değişkenİsim, değişken.değişkenTipi));
				aktifFonksiyon.cümleler.add(new Cümle_05SabitTanımlama(aktifFonksiyon.gerçekDeğişkenNo,
						((Sözcük_03TamSayı) cümle[0]).sayı, "i64"));
			} else if (cümle.length == 4 && cümle[0].tip == SÖZCÜK.TİP_01İSİM
					&& cümle[1].tip == SÖZCÜK.TİP_11TANIMLAMA_SOLA && cümle[2].tip == SÖZCÜK.TİP_03TAM_SAYI
					&& cümle[3].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				// sayı <: 123;
				aktifFonksiyon.gerçekDeğişkenNo++;
				Değişken değişken = new Değişken(aktifFonksiyon.gerçekDeğişkenNo, ((Sözcük_01İsim) cümle[0]).isim,
						"i64");
				aktifFonksiyon.değişkenNoMap.put(değişken.değişkenNo, değişken);
				aktifFonksiyon.değişkenİsimMap.put(değişken.değişkenİsim, değişken);
				aktifFonksiyon.cümleler.add(
						new Cümle_01DeğişkenYeni(değişken.değişkenNo, değişken.değişkenİsim, değişken.değişkenTipi));
				aktifFonksiyon.cümleler.add(new Cümle_05SabitTanımlama(aktifFonksiyon.gerçekDeğişkenNo,
						((Sözcük_03TamSayı) cümle[2]).sayı, "i64"));
			} else if (cümle.length == 4 && cümle[0].tip == SÖZCÜK.TİP_03TAM_SAYI
					&& cümle[1].tip == SÖZCÜK.TİP_12TANIMLAMA_SAĞA && cümle[2].tip == SÖZCÜK.TİP_01İSİM
					&& cümle[3].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				// 123 :> sayı;
				aktifFonksiyon.gerçekDeğişkenNo++;
				Değişken değişken = new Değişken(aktifFonksiyon.gerçekDeğişkenNo, ((Sözcük_01İsim) cümle[2]).isim,
						"i64");
				aktifFonksiyon.değişkenNoMap.put(değişken.değişkenNo, değişken);
				aktifFonksiyon.değişkenİsimMap.put(değişken.değişkenİsim, değişken);
				aktifFonksiyon.cümleler.add(
						new Cümle_01DeğişkenYeni(değişken.değişkenNo, değişken.değişkenİsim, değişken.değişkenTipi));
				aktifFonksiyon.cümleler.add(new Cümle_05SabitTanımlama(aktifFonksiyon.gerçekDeğişkenNo,
						((Sözcük_03TamSayı) cümle[0]).sayı, "i64"));
			} else if (cümle.length == 6 && cümle[0].tip == SÖZCÜK.TİP_01İSİM && cümle[1].tip == SÖZCÜK.TİP_09ATAMA_SOLA
					&& cümle[2].tip == SÖZCÜK.TİP_01İSİM && cümle[3].tip == SÖZCÜK.TİP_02OPERATÖR
					&& cümle[4].tip == SÖZCÜK.TİP_01İSİM && cümle[5].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				// sayı3 < sayı1 + sayı2;
				String isimHedef = ((Sözcük_01İsim) cümle[0]).isim;
				String isim1 = ((Sözcük_01İsim) cümle[2]).isim;
				String isim2 = ((Sözcük_01İsim) cümle[4]).isim;
				if (!aktifFonksiyon.değişkenİsimMap.containsKey(isimHedef)) {
					throw new Exception("Tanımsız Değişken : " + isimHedef + " !!!");
				} else if (!aktifFonksiyon.değişkenİsimMap.containsKey(isim1)) {
					throw new Exception("Tanımsız Değişken : " + isim1 + " !!!");
				} else if (!aktifFonksiyon.değişkenİsimMap.containsKey(isim2)) {
					throw new Exception("Tanımsız Değişken : " + isim2 + " !!!");
				} else {
					aktifFonksiyon.cümleler
							.add(new Cümle_03Operatörİşlemi(aktifFonksiyon.değişkenİsimMap.get(isimHedef).değişkenNo,
									((Sözcük_02Operatör) cümle[3]).operatör,
									aktifFonksiyon.değişkenİsimMap.get(isim1).değişkenNo,
									aktifFonksiyon.değişkenİsimMap.get(isim2).değişkenNo));
				}
			} else if (cümle.length == 6 && cümle[0].tip == SÖZCÜK.TİP_01İSİM && cümle[1].tip == SÖZCÜK.TİP_02OPERATÖR
					&& cümle[2].tip == SÖZCÜK.TİP_01İSİM && cümle[3].tip == SÖZCÜK.TİP_10ATAMA_SAĞA
					&& cümle[4].tip == SÖZCÜK.TİP_01İSİM && cümle[5].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				// sayı1 + sayı2 > sayı3;
				String isimHedef = ((Sözcük_01İsim) cümle[4]).isim;
				String isim1 = ((Sözcük_01İsim) cümle[0]).isim;
				String isim2 = ((Sözcük_01İsim) cümle[2]).isim;
				if (!aktifFonksiyon.değişkenİsimMap.containsKey(isimHedef)) {
					throw new Exception("Tanımsız Değişken : " + isimHedef + " !!!");
				} else if (!aktifFonksiyon.değişkenİsimMap.containsKey(isim1)) {
					throw new Exception("Tanımsız Değişken : " + isim1 + " !!!");
				} else if (!aktifFonksiyon.değişkenİsimMap.containsKey(isim2)) {
					throw new Exception("Tanımsız Değişken : " + isim2 + " !!!");
				} else {
					aktifFonksiyon.cümleler
							.add(new Cümle_03Operatörİşlemi(aktifFonksiyon.değişkenİsimMap.get(isimHedef).değişkenNo,
									((Sözcük_02Operatör) cümle[1]).operatör,
									aktifFonksiyon.değişkenİsimMap.get(isim1).değişkenNo,
									aktifFonksiyon.değişkenİsimMap.get(isim2).değişkenNo));
				}
			} else {
				for (Sözcük c : cümle) {
					System.err.print(c);
					System.err.print(" ");
				}
				System.err.println();
				throw new Exception("TODO : ex6 !!!");
			}
		}

		StringBuilder stringBuilder = new StringBuilder();
		for (Fonksiyon_01OperatörFonksiyon operatörFonksiyon : operatörFonksiyonMap.values()) {
			stringBuilder.append(operatörFonksiyon.toString());
			stringBuilder.append("\n");
			for (Cümle cümle : operatörFonksiyon.cümleler) {
				stringBuilder.append("\t");
				stringBuilder.append(cümle.toString());
				stringBuilder.append("\n");
			}
		}
		for (Fonksiyon_02İsimliFonksiyon isimFonksiyon : isimFonksiyonMap.values()) {
			stringBuilder.append(isimFonksiyon.toString());
			stringBuilder.append("\n");
			for (Cümle cümle : isimFonksiyon.cümleler) {
				stringBuilder.append("\t");
				stringBuilder.append(cümle.toString());
				stringBuilder.append("\n");
			}
		}
		{
			stringBuilder.append(anaFonksiyon.toString());
			stringBuilder.append("\n");
			for (Cümle cümle : anaFonksiyon.cümleler) {
				stringBuilder.append("\t");
				stringBuilder.append(cümle.toString());
				stringBuilder.append("\n");
			}
		}
		String metinÇıktı = stringBuilder.toString();
		// System.out.print(metinÇıktı);
		if (dosyaAdı != null) {
			Fonksiyonlar.dosyaKaydet(dosyaAdı + ".3.log", metinÇıktı);
		}

		return new Aşama3Çıktısı(operatörFonksiyonMap, isimFonksiyonMap, anaFonksiyon);
	}

}
