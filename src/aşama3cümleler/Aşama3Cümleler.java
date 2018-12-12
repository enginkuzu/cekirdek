package aşama3cümleler;

import java.util.ArrayList;
import java.util.HashMap;

import aşama2sözcükler.Sözcük;
import aşama2sözcükler.Sözcük_01İsim;
import aşama2sözcükler.Sözcük_02Operatör;
import aşama2sözcükler.Sözcük_03TamSayı;
import yardımcı.Fonksiyonlar;
import yardımcı.Değişkenler.SÖZCÜK;

public class Aşama3Cümleler {

	public static Aşama3Çıktısı işle(ArrayList<Sözcük[]> tümCümleler, String dosyaAdı) throws Exception {

		int geçiciDeğişkenNo = 0;
		int gerçekDeğişkenNo = 0;
		HashMap<Integer, Değişken> değişkenNoMap = new HashMap<Integer, Değişken>();
		HashMap<String, Değişken> değişkenİsimMap = new HashMap<String, Değişken>();

		ArrayList<Cümle> cümleler = new ArrayList<Cümle>();

		for (Sözcük[] cümle : tümCümleler) {
			if (cümle.length == 4 && cümle[0].tip == SÖZCÜK.TİP_01İSİM && cümle[1].tip == SÖZCÜK.TİP_09ATAMA_SOLA
					&& cümle[2].tip == SÖZCÜK.TİP_01İSİM && cümle[3].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				// sayı2 < sayı1;
				String isimKaynak = ((Sözcük_01İsim) cümle[2]).isim;
				String isimHedef = ((Sözcük_01İsim) cümle[0]).isim;
				if (!değişkenİsimMap.containsKey(isimKaynak)) {
					throw new Exception("Tanımsız Değişken : " + isimKaynak + " !!!");
				} else if (!değişkenİsimMap.containsKey(isimHedef)) {
					throw new Exception("Tanımsız Değişken : " + isimHedef + " !!!");
				} else {
					cümleler.add(new Cümle_06DeğişkenAtama(değişkenİsimMap.get(isimKaynak).değişkenNo,
							değişkenİsimMap.get(isimHedef).değişkenNo));
				}
			} else if (cümle.length == 4 && cümle[0].tip == SÖZCÜK.TİP_01İSİM && cümle[1].tip == SÖZCÜK.TİP_10ATAMA_SAĞA
					&& cümle[2].tip == SÖZCÜK.TİP_01İSİM && cümle[3].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				// sayı1 > sayı2;
				String isimKaynak = ((Sözcük_01İsim) cümle[0]).isim;
				String isimHedef = ((Sözcük_01İsim) cümle[2]).isim;
				if (!değişkenİsimMap.containsKey(isimKaynak)) {
					throw new Exception("Tanımsız Değişken : " + isimKaynak + " !!!");
				} else if (!değişkenİsimMap.containsKey(isimHedef)) {
					throw new Exception("Tanımsız Değişken : " + isimHedef + " !!!");
				} else {
					cümleler.add(new Cümle_06DeğişkenAtama(değişkenİsimMap.get(isimKaynak).değişkenNo,
							değişkenİsimMap.get(isimHedef).değişkenNo));
				}
			} else if (cümle.length == 4 && cümle[0].tip == SÖZCÜK.TİP_01İSİM && cümle[1].tip == SÖZCÜK.TİP_08NOKTA
					&& cümle[2].tip == SÖZCÜK.TİP_01İSİM && cümle[3].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				// sayı1.println;
				String isim = ((Sözcük_01İsim) cümle[0]).isim;
				if (!değişkenİsimMap.containsKey(isim)) {
					throw new Exception("Tanımsız Değişken : " + isim + " !!!");
				} else {
					geçiciDeğişkenNo--;
					Cümle_04FonksiyonÇağrısı komut = new Cümle_04FonksiyonÇağrısı(geçiciDeğişkenNo,
							((Sözcük_01İsim) cümle[2]).isim, değişkenİsimMap.get(isim).değişkenNo);
					cümleler.add(komut);
				}
			} else if (cümle.length == 6 && cümle[0].tip == SÖZCÜK.TİP_01İSİM
					&& cümle[1].tip == SÖZCÜK.TİP_07DEĞİŞKEN_TİPİ && cümle[2].tip == SÖZCÜK.TİP_01İSİM
					&& cümle[3].tip == SÖZCÜK.TİP_09ATAMA_SOLA && cümle[4].tip == SÖZCÜK.TİP_03TAM_SAYI
					&& cümle[5].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				// sayı:i64 < 123;
				gerçekDeğişkenNo++;
				Değişken değişken = new Değişken(gerçekDeğişkenNo, ((Sözcük_01İsim) cümle[0]).isim,
						((Sözcük_01İsim) cümle[2]).isim);
				değişkenNoMap.put(değişken.değişkenNo, değişken);
				değişkenİsimMap.put(değişken.değişkenİsim, değişken);
				cümleler.add(
						new Cümle_01DeğişkenYeni(değişken.değişkenNo, değişken.değişkenİsim, değişken.değişkenTipi));
				cümleler.add(new Cümle_05SabitTanımlama(gerçekDeğişkenNo, ((Sözcük_03TamSayı) cümle[4]).sayı, "i64"));
			} else if (cümle.length == 6 && cümle[0].tip == SÖZCÜK.TİP_03TAM_SAYI
					&& cümle[1].tip == SÖZCÜK.TİP_10ATAMA_SAĞA && cümle[2].tip == SÖZCÜK.TİP_01İSİM
					&& cümle[3].tip == SÖZCÜK.TİP_07DEĞİŞKEN_TİPİ && cümle[4].tip == SÖZCÜK.TİP_01İSİM
					&& cümle[5].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				// 123 > sayı:i64;
				gerçekDeğişkenNo++;
				Değişken değişken = new Değişken(gerçekDeğişkenNo, ((Sözcük_01İsim) cümle[2]).isim,
						((Sözcük_01İsim) cümle[4]).isim);
				değişkenNoMap.put(değişken.değişkenNo, değişken);
				değişkenİsimMap.put(değişken.değişkenİsim, değişken);
				cümleler.add(
						new Cümle_01DeğişkenYeni(değişken.değişkenNo, değişken.değişkenİsim, değişken.değişkenTipi));
				cümleler.add(new Cümle_05SabitTanımlama(gerçekDeğişkenNo, ((Sözcük_03TamSayı) cümle[0]).sayı, "i64"));
			} else if (cümle.length == 4 && cümle[0].tip == SÖZCÜK.TİP_01İSİM
					&& cümle[1].tip == SÖZCÜK.TİP_11TANIMLAMA_SOLA && cümle[2].tip == SÖZCÜK.TİP_03TAM_SAYI
					&& cümle[3].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				// sayı <: 123;
				gerçekDeğişkenNo++;
				Değişken değişken = new Değişken(gerçekDeğişkenNo, ((Sözcük_01İsim) cümle[0]).isim, "i64");
				değişkenNoMap.put(değişken.değişkenNo, değişken);
				değişkenİsimMap.put(değişken.değişkenİsim, değişken);
				cümleler.add(
						new Cümle_01DeğişkenYeni(değişken.değişkenNo, değişken.değişkenİsim, değişken.değişkenTipi));
				cümleler.add(new Cümle_05SabitTanımlama(gerçekDeğişkenNo, ((Sözcük_03TamSayı) cümle[2]).sayı, "i64"));
			} else if (cümle.length == 4 && cümle[0].tip == SÖZCÜK.TİP_03TAM_SAYI
					&& cümle[1].tip == SÖZCÜK.TİP_12TANIMLAMA_SAĞA && cümle[2].tip == SÖZCÜK.TİP_01İSİM
					&& cümle[3].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				// 123 :> sayı;
				gerçekDeğişkenNo++;
				Değişken değişken = new Değişken(gerçekDeğişkenNo, ((Sözcük_01İsim) cümle[2]).isim, "i64");
				değişkenNoMap.put(değişken.değişkenNo, değişken);
				değişkenİsimMap.put(değişken.değişkenİsim, değişken);
				cümleler.add(
						new Cümle_01DeğişkenYeni(değişken.değişkenNo, değişken.değişkenİsim, değişken.değişkenTipi));
				cümleler.add(new Cümle_05SabitTanımlama(gerçekDeğişkenNo, ((Sözcük_03TamSayı) cümle[0]).sayı, "i64"));
			} else if (cümle.length == 6 && cümle[0].tip == SÖZCÜK.TİP_01İSİM && cümle[1].tip == SÖZCÜK.TİP_09ATAMA_SOLA
					&& cümle[2].tip == SÖZCÜK.TİP_01İSİM && cümle[3].tip == SÖZCÜK.TİP_02OPERATÖR
					&& cümle[4].tip == SÖZCÜK.TİP_01İSİM && cümle[5].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				// sayı3 < sayı1 + sayı2;
				String isimHedef = ((Sözcük_01İsim) cümle[0]).isim;
				String isim1 = ((Sözcük_01İsim) cümle[2]).isim;
				String isim2 = ((Sözcük_01İsim) cümle[4]).isim;
				if (!değişkenİsimMap.containsKey(isimHedef)) {
					throw new Exception("Tanımsız Değişken : " + isimHedef + " !!!");
				} else if (!değişkenİsimMap.containsKey(isim1)) {
					throw new Exception("Tanımsız Değişken : " + isim1 + " !!!");
				} else if (!değişkenİsimMap.containsKey(isim2)) {
					throw new Exception("Tanımsız Değişken : " + isim2 + " !!!");
				} else {
					cümleler.add(new Cümle_03Operatörİşlemi(değişkenİsimMap.get(isimHedef).değişkenNo,
							((Sözcük_02Operatör) cümle[3]).operatör, değişkenİsimMap.get(isim1).değişkenNo,
							değişkenİsimMap.get(isim2).değişkenNo));
				}
			} else if (cümle.length == 6 && cümle[0].tip == SÖZCÜK.TİP_01İSİM && cümle[1].tip == SÖZCÜK.TİP_02OPERATÖR
					&& cümle[2].tip == SÖZCÜK.TİP_01İSİM && cümle[3].tip == SÖZCÜK.TİP_10ATAMA_SAĞA
					&& cümle[4].tip == SÖZCÜK.TİP_01İSİM && cümle[5].tip == SÖZCÜK.TİP_06SATIR_SONU) {
				// sayı1 + sayı2 > sayı3;
				String isimHedef = ((Sözcük_01İsim) cümle[4]).isim;
				String isim1 = ((Sözcük_01İsim) cümle[0]).isim;
				String isim2 = ((Sözcük_01İsim) cümle[2]).isim;
				if (!değişkenİsimMap.containsKey(isimHedef)) {
					throw new Exception("Tanımsız Değişken : " + isimHedef + " !!!");
				} else if (!değişkenİsimMap.containsKey(isim1)) {
					throw new Exception("Tanımsız Değişken : " + isim1 + " !!!");
				} else if (!değişkenİsimMap.containsKey(isim2)) {
					throw new Exception("Tanımsız Değişken : " + isim2 + " !!!");
				} else {
					cümleler.add(new Cümle_03Operatörİşlemi(değişkenİsimMap.get(isimHedef).değişkenNo,
							((Sözcük_02Operatör) cümle[1]).operatör, değişkenİsimMap.get(isim1).değişkenNo,
							değişkenİsimMap.get(isim2).değişkenNo));
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
		for (Cümle cümle : cümleler) {
			stringBuilder.append(cümle.toString());
			stringBuilder.append("\n");
		}
		String çıktı = stringBuilder.toString();
		// System.out.print(çıktı);
		if (dosyaAdı != null) {
			Fonksiyonlar.dosyaKaydet("kodlar/" + dosyaAdı + "_a3.txt", çıktı);
		}

		return new Aşama3Çıktısı(cümleler, geçiciDeğişkenNo, gerçekDeğişkenNo);
	}

}
