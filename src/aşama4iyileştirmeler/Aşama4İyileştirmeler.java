package aşama4iyileştirmeler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.Collections;

import aşama3cümleler.Aşama3Çıktısı;
import aşama3cümleler.Cümle;
import aşama3cümleler.Cümle_01DeğişkenYeni;
import aşama3cümleler.Cümle_02GeçiciDeğişkenYeni;
import aşama3cümleler.Cümle_03DeğişkenSil;
import aşama3cümleler.Cümle_04Operatörİşlemi;
import aşama3cümleler.Cümle_05FonksiyonÇağrısı;
import aşama3cümleler.Cümle_10SabitAtama;
import aşama3cümleler.Cümle_06DeğişkenAtama;
import aşama3cümleler.Fonksiyon_01OperatörFonksiyon;
import aşama3cümleler.Fonksiyon_02İsimliFonksiyon;
import yardımcı.Fonksiyonlar;

public class Aşama4İyileştirmeler {

	private static HashMap<Integer, DeğişkenKullanımRaporu> değişkenKullanımAnalizi(ArrayList<Cümle> cümleler) {

		HashMap<Integer, DeğişkenKullanımRaporu> map = new HashMap<Integer, DeğişkenKullanımRaporu>();

		int i = 0;
		for (Cümle cümle : cümleler) {
			i++;
			if (cümle instanceof Cümle_01DeğişkenYeni) {
				Cümle_01DeğişkenYeni cümle01 = (Cümle_01DeğişkenYeni) cümle;
				map.put(cümle01.değişkenNo, new DeğişkenKullanımRaporu(cümle01.değişkenNo, i));
			} else if (cümle instanceof Cümle_02GeçiciDeğişkenYeni) {
				Cümle_02GeçiciDeğişkenYeni cümle02 = (Cümle_02GeçiciDeğişkenYeni) cümle;
				map.put(cümle02.değişkenNo, new DeğişkenKullanımRaporu(cümle02.değişkenNo, i));
			} else if (cümle instanceof Cümle_04Operatörİşlemi) {
				Cümle_04Operatörİşlemi cümle03 = (Cümle_04Operatörİşlemi) cümle;
				map.get(cümle03.parametreNo1).ekleOkuma(i);
				map.get(cümle03.parametreNo2).ekleOkuma(i);
				map.get(cümle03.değişkenNo).ekleYazma(-i);
			} else if (cümle instanceof Cümle_05FonksiyonÇağrısı) {
				Cümle_05FonksiyonÇağrısı cümle04 = (Cümle_05FonksiyonÇağrısı) cümle;
				map.get(cümle04.parametre).ekleOkuma(i);
			} else if (cümle instanceof Cümle_10SabitAtama) {
				Cümle_10SabitAtama cümle05 = (Cümle_10SabitAtama) cümle;
				map.get(cümle05.değişkenNo).ekleYazma(-i);
			} else if (cümle instanceof Cümle_06DeğişkenAtama) {
				Cümle_06DeğişkenAtama cümle06 = (Cümle_06DeğişkenAtama) cümle;
				map.get(cümle06.değişkenNoKaynak).ekleOkuma(i);
				map.get(cümle06.değişkenNoHedef).ekleYazma(-i);
			}
		}

		return map;
	}

	private static void sonİşlemler(Aşama3Çıktısı çıktı, String dosyaAdı) {

		StringBuilder stringBuilder = new StringBuilder();

		for (Fonksiyon_01OperatörFonksiyon operatörFonksiyon : çıktı.operatörFonksiyonMap.values()) {
			stringBuilder.append(operatörFonksiyon.toString());
			stringBuilder.append("\n");
			for (Cümle cümle : operatörFonksiyon.cümleler) {
				stringBuilder.append("\t");
				stringBuilder.append(cümle.toString());
				stringBuilder.append("\n");
			}
		}
		for (Fonksiyon_02İsimliFonksiyon isimFonksiyon : çıktı.isimFonksiyonMap.values()) {
			stringBuilder.append(isimFonksiyon.toString());
			stringBuilder.append("\n");
			for (Cümle cümle : isimFonksiyon.cümleler) {
				stringBuilder.append("\t");
				stringBuilder.append(cümle.toString());
				stringBuilder.append("\n");
			}
		}
		{
			stringBuilder.append(çıktı.anaFonksiyon.toString());
			stringBuilder.append("\n");
			for (Cümle cümle : çıktı.anaFonksiyon.cümleler) {
				stringBuilder.append("\t");
				stringBuilder.append(cümle.toString());
				stringBuilder.append("\n");
			}
		}

		String metinÇıktı = stringBuilder.toString();
		// System.out.print(metinÇıktı);
		if (dosyaAdı != null) {
			Fonksiyonlar.dosyaKaydet(dosyaAdı + ".4.log", metinÇıktı);
		}
	}

	public static Aşama3Çıktısı işle(Aşama3Çıktısı çıktı, String dosyaAdı) {

		HashMap<Integer, DeğişkenKullanımRaporu> map = değişkenKullanımAnalizi(çıktı.anaFonksiyon.cümleler);

		HashSet<Integer> silinecekOlanSatırlar = new HashSet<Integer>();
		HashMap<Integer, Integer> eklenecekOlanlar = new HashMap<Integer, Integer>();
		HashSet<Integer> incelemesiEksikOlanlar = new HashSet<Integer>(map.keySet());

		while (incelemesiEksikOlanlar.size() > 0) {

			// System.out.println(map);

			int değişkenNo = incelemesiEksikOlanlar.iterator().next();
			DeğişkenKullanımRaporu rapor = map.get(değişkenNo);

			// System.out.println(rapor);

			if (rapor.okumaSayaç == 0) {

				// Hiç okuma yapılmayan değişken kullanılmayan bir değişkendir. (Tanımlamayı ve
				// yazma ifadelerini silinir)
				silinecekOlanSatırlar.add(rapor.yeniIndeks);
				// System.out.println("SİL : " +
				// çıktı.anaFonksiyon.cümleler.get(rapor.yeniIndeks - 1));
				for (int i : rapor.işlemler) {
					int satirNo = -i;
					Cümle cümle = çıktı.anaFonksiyon.cümleler.get(satirNo - 1);
					if (cümle instanceof Cümle_04Operatörİşlemi) {
						Cümle_04Operatörİşlemi cümle03 = (Cümle_04Operatörİşlemi) cümle;
						incelemesiEksikOlanlar.add(cümle03.parametreNo1);
						map.get(cümle03.parametreNo1).silOkuma(satirNo);
						incelemesiEksikOlanlar.add(cümle03.parametreNo2);
						map.get(cümle03.parametreNo2).silOkuma(satirNo);
					} else if (cümle instanceof Cümle_06DeğişkenAtama) {
						Cümle_06DeğişkenAtama cümle06 = (Cümle_06DeğişkenAtama) cümle;
						incelemesiEksikOlanlar.add(cümle06.değişkenNoKaynak);
						map.get(cümle06.değişkenNoKaynak).silOkuma(satirNo);
					}
					silinecekOlanSatırlar.add(satirNo);
					// System.out.println("SİL : " + çıktı.anaFonksiyon.cümleler.get(satirNo - 1));
				}

				incelemesiEksikOlanlar.remove(değişkenNo);
				map.remove(değişkenNo);

			} else {

				// Değişkene ait son okuma işleminden sonraki yazma işlemlerini kaldır
				while (true) {
					int i = rapor.işlemler.get(rapor.işlemler.size() - 1);
					if (i > 0) {
						break;
					}
					int satirNo = -i;
					Cümle cümle = çıktı.anaFonksiyon.cümleler.get(satirNo - 1);
					if (cümle instanceof Cümle_04Operatörİşlemi) {
						Cümle_04Operatörİşlemi cümle03 = (Cümle_04Operatörİşlemi) cümle;
						incelemesiEksikOlanlar.add(cümle03.parametreNo1);
						map.get(cümle03.parametreNo1).silOkuma(satirNo);
						incelemesiEksikOlanlar.add(cümle03.parametreNo2);
						map.get(cümle03.parametreNo2).silOkuma(satirNo);
					} else if (cümle instanceof Cümle_06DeğişkenAtama) {
						Cümle_06DeğişkenAtama cümle06 = (Cümle_06DeğişkenAtama) cümle;
						incelemesiEksikOlanlar.add(cümle06.değişkenNoKaynak);
						map.get(cümle06.değişkenNoKaynak).silOkuma(satirNo);
					}
					rapor.silYazma(-satirNo);
					silinecekOlanSatırlar.add(satirNo);
					// System.out.println("SİL : " + çıktı.anaFonksiyon.cümleler.get(satirNo - 1));
				}

				// Bir değişkene ard arda yapılan yazma işlemlerinin sonuncusu hariç diğerlerini
				// kaldır
				boolean öncekiİşlemYazmaİdi = false;
				for (int i = rapor.işlemler.size() - 1; i >= 0; i--) {
					int işlemSıraNo = rapor.işlemler.get(i);
					if (öncekiİşlemYazmaİdi) {
						if (işlemSıraNo < 0) {
							int satirNo = -işlemSıraNo;
							Cümle cümle = çıktı.anaFonksiyon.cümleler.get(satirNo - 1);
							if (cümle instanceof Cümle_04Operatörİşlemi) {
								Cümle_04Operatörİşlemi cümle03 = (Cümle_04Operatörİşlemi) cümle;
								incelemesiEksikOlanlar.add(cümle03.parametreNo1);
								map.get(cümle03.parametreNo1).silOkuma(satirNo);
								incelemesiEksikOlanlar.add(cümle03.parametreNo2);
								map.get(cümle03.parametreNo2).silOkuma(satirNo);
							} else if (cümle instanceof Cümle_06DeğişkenAtama) {
								Cümle_06DeğişkenAtama cümle06 = (Cümle_06DeğişkenAtama) cümle;
								incelemesiEksikOlanlar.add(cümle06.değişkenNoKaynak);
								map.get(cümle06.değişkenNoKaynak).silOkuma(satirNo);
							}
							rapor.silYazma(-satirNo);
							silinecekOlanSatırlar.add(satirNo);
							// System.out.println("SİL : " + çıktı.anaFonksiyon.cümleler.get(satirNo - 1));
						} else {
							öncekiİşlemYazmaİdi = false;
						}
					} else if (işlemSıraNo < 0) {
						öncekiİşlemYazmaİdi = true;
					}
				}

				incelemesiEksikOlanlar.remove(değişkenNo);
			}
		}

		incelemesiEksikOlanlar = new HashSet<Integer>(map.keySet());
		while (incelemesiEksikOlanlar.size() > 0) {

			int değişkenNo = incelemesiEksikOlanlar.iterator().next();
			DeğişkenKullanımRaporu rapor = map.get(değişkenNo);

			// DeğişkenSil komutunu ekle
			int i = rapor.işlemler.get(rapor.işlemler.size() - 1);
			int satirNo = i;
			eklenecekOlanlar.put(değişkenNo, satirNo + 1);
			// System.out.println("DEĞİŞKEN SİL : " + değişkenNo);

			incelemesiEksikOlanlar.remove(değişkenNo);
		}

		ArrayList<Integer> silinecekOlanlar = new ArrayList<Integer>(silinecekOlanSatırlar);
		Collections.sort(silinecekOlanlar, Collections.reverseOrder());

		TreeMap<Integer, Integer> eklenecekler = new TreeMap<Integer, Integer>(Collections.reverseOrder());
		for (Entry<Integer, Integer> entry : eklenecekOlanlar.entrySet()) {
			eklenecekler.put(entry.getValue(), entry.getKey());
		}

		while (true) {
			if (silinecekOlanlar.isEmpty() && eklenecekler.isEmpty()) {
				break;
			} else if (silinecekOlanlar.isEmpty()) {
				for (Entry<Integer, Integer> entry : eklenecekler.entrySet()) {
					çıktı.anaFonksiyon.cümleler.add(entry.getKey() - 1, new Cümle_03DeğişkenSil(entry.getValue()));
				}
				eklenecekler.clear();
			} else if (eklenecekler.isEmpty()) {
				for (int satirNo : silinecekOlanlar) {
					çıktı.anaFonksiyon.cümleler.remove(satirNo - 1);
				}
				silinecekOlanlar.clear();
			} else {
				int silmeSatırNo = silinecekOlanlar.get(0);
				Entry<Integer, Integer> eklemeEntry = eklenecekler.entrySet().iterator().next();
				int eklemeSatırNo = eklemeEntry.getKey();
				int eklemeDeğişkenNo = eklemeEntry.getValue();
				if (silmeSatırNo >= eklemeSatırNo) {
					silinecekOlanlar.remove(0);
					çıktı.anaFonksiyon.cümleler.remove(silmeSatırNo - 1);
				} else {
					eklenecekler.remove(eklemeSatırNo);
					çıktı.anaFonksiyon.cümleler.add(eklemeSatırNo - 1, new Cümle_03DeğişkenSil(eklemeDeğişkenNo));
				}
			}
		}

		sonİşlemler(çıktı, dosyaAdı);

		return çıktı;
	}

}
