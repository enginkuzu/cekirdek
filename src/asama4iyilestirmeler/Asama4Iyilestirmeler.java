package asama4iyilestirmeler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Collections;

import asama3cumleler.Asama3Ciktisi;
import asama3cumleler.Cumle;
import asama3cumleler.Cumle_01DegiskenYeni;
import asama3cumleler.Cumle_02GeciciDegiskenYeni;
import asama3cumleler.Cumle_03DegiskenSil;
import asama3cumleler.Cumle_04OperatorIslemi;
import asama3cumleler.Cumle_05FonksiyonCagrisi;
import asama3cumleler.Cumle_11SabitAtama;
import asama3cumleler.Cumle_06DegiskenAtama;
import asama3cumleler.Fonksiyon_01OperatorFonksiyon;
import asama3cumleler.Fonksiyon_02IsimliFonksiyon;
import yardimci.Fonksiyonlar;

public class Asama4Iyilestirmeler {

	private static HashMap<Integer, DegiskenKullanimRaporu> değişkenKullanımAnalizi(ArrayList<Cumle> cümleler) {

		HashMap<Integer, DegiskenKullanimRaporu> map = new HashMap<Integer, DegiskenKullanimRaporu>();

		int i = 0;
		for (Cumle cümle : cümleler) {
			i++;
			if (cümle instanceof Cumle_01DegiskenYeni) {
				Cumle_01DegiskenYeni cümle01 = (Cumle_01DegiskenYeni) cümle;
				map.put(cümle01.değişkenNo, new DegiskenKullanimRaporu(cümle01.değişkenNo, i));
			} else if (cümle instanceof Cumle_02GeciciDegiskenYeni) {
				Cumle_02GeciciDegiskenYeni cümle02 = (Cumle_02GeciciDegiskenYeni) cümle;
				map.put(cümle02.değişkenNo, new DegiskenKullanimRaporu(cümle02.değişkenNo, i));
			} else if (cümle instanceof Cumle_04OperatorIslemi) {
				Cumle_04OperatorIslemi cümle03 = (Cumle_04OperatorIslemi) cümle;
				if (cümle03.parametreNo1 != 0)
					map.get(cümle03.parametreNo1).ekleOkuma(i);
				map.get(cümle03.parametreNo2).ekleOkuma(i);
				map.get(cümle03.değişkenNo).ekleYazma(-i);
			} else if (cümle instanceof Cumle_05FonksiyonCagrisi) {
				Cumle_05FonksiyonCagrisi cümle04 = (Cumle_05FonksiyonCagrisi) cümle;
				map.get(cümle04.parametre).ekleOkuma(i);
			} else if (cümle instanceof Cumle_06DegiskenAtama) {
				Cumle_06DegiskenAtama cümle06 = (Cumle_06DegiskenAtama) cümle;
				map.get(cümle06.değişkenNoKaynak).ekleOkuma(i);
				map.get(cümle06.değişkenNoHedef).ekleYazma(-i);
			} else if (cümle instanceof Cumle_11SabitAtama) {
				Cumle_11SabitAtama cümle05 = (Cumle_11SabitAtama) cümle;
				map.get(cümle05.değişkenNo).ekleYazma(-i);
			} else {
				System.out.println("BİLİNMEYEN OPTİMİZASYON : " + cümle);
			}
		}

		return map;
	}

	private static void sonİşlemler(Asama3Ciktisi çıktı, String dosyaAdı) {

		StringBuilder sb = new StringBuilder();

		for (Fonksiyon_01OperatorFonksiyon operatörFonksiyon : çıktı.operatörFonksiyonMap.values()) {
			sb.append(operatörFonksiyon.toString());
			sb.append("\n");
			for (Cumle cümle : operatörFonksiyon.cümleler) {
				sb.append("\t");
				sb.append(cümle.toString());
				sb.append("\n");
			}
		}
		for (Fonksiyon_02IsimliFonksiyon isimFonksiyon : çıktı.isimFonksiyonMap.values()) {
			sb.append(isimFonksiyon.toString());
			sb.append("\n");
			for (Cumle cümle : isimFonksiyon.cümleler) {
				sb.append("\t");
				sb.append(cümle.toString());
				sb.append("\n");
			}
		}
		{
			sb.append(çıktı.anaFonksiyon.toString());
			sb.append("\n");
			for (Cumle cümle : çıktı.anaFonksiyon.cümleler) {
				sb.append("\t");
				sb.append(cümle.toString());
				sb.append("\n");
			}
		}

		String metinÇıktı = sb.toString();
		// System.out.print(metinÇıktı);
		Fonksiyonlar.dosyaKaydet(dosyaAdı + ".4.log", metinÇıktı);
	}

	public static Asama3Ciktisi işle(Asama3Ciktisi çıktı, String dosyaAdı) {

		HashMap<Integer, DegiskenKullanimRaporu> map = değişkenKullanımAnalizi(çıktı.anaFonksiyon.cümleler);

		HashSet<Integer> silinecekOlanSatırlar = new HashSet<Integer>();
		HashSet<Integer> incelemesiEksikOlanlar = new HashSet<Integer>(map.keySet());

		while (incelemesiEksikOlanlar.size() > 0) {

			int değişkenNo = incelemesiEksikOlanlar.iterator().next();
			DegiskenKullanimRaporu rapor = map.get(değişkenNo);

			// System.out.println(map);
			// System.out.println(rapor);

			if (rapor.okumaSayaç == 0) {

				// Hiç okuma yapılmayan değişken kullanılmayan bir değişkendir. (Tanımlamayı ve
				// yazma ifadeleri silinir)
				silinecekOlanSatırlar.add(rapor.yeniIndeks);
				// System.out.println("SİL : " +
				// çıktı.anaFonksiyon.cümleler.get(rapor.yeniIndeks - 1));
				for (int i : rapor.işlemler) {
					int satirNo = -i;
					Cumle cümle = çıktı.anaFonksiyon.cümleler.get(satirNo - 1);
					if (cümle instanceof Cumle_04OperatorIslemi) {
						Cumle_04OperatorIslemi cümle03 = (Cumle_04OperatorIslemi) cümle;
						incelemesiEksikOlanlar.add(cümle03.parametreNo1);
						map.get(cümle03.parametreNo1).silOkuma(satirNo);
						incelemesiEksikOlanlar.add(cümle03.parametreNo2);
						map.get(cümle03.parametreNo2).silOkuma(satirNo);
					} else if (cümle instanceof Cumle_05FonksiyonCagrisi) {
						Cumle_05FonksiyonCagrisi cümle05 = (Cumle_05FonksiyonCagrisi) cümle;
						incelemesiEksikOlanlar.add(cümle05.parametre);
						map.get(cümle05.parametre).silOkuma(satirNo);
					} else if (cümle instanceof Cumle_06DegiskenAtama) {
						Cumle_06DegiskenAtama cümle06 = (Cumle_06DegiskenAtama) cümle;
						incelemesiEksikOlanlar.add(cümle06.değişkenNoKaynak);
						map.get(cümle06.değişkenNoKaynak).silOkuma(satirNo);
					} else if (cümle instanceof Cumle_11SabitAtama) {
						// Cumle_11SabitAtama cümle11 = (Cumle_11SabitAtama) cümle;
					}
					silinecekOlanSatırlar.add(satirNo);
					// System.out.println("SİL : " + çıktı.anaFonksiyon.cümleler.get(satirNo - 1));
				}

				incelemesiEksikOlanlar.remove(değişkenNo);
				map.remove(değişkenNo);

			} else {

				// Degiskene ait son okuma işleminden sonraki yazma işlemlerini kaldır
				while (true) {
					int i = rapor.işlemler.get(rapor.işlemler.size() - 1);
					if (i > 0) {
						break;
					}
					int satirNo = -i;
					Cumle cümle = çıktı.anaFonksiyon.cümleler.get(satirNo - 1);
					if (cümle instanceof Cumle_04OperatorIslemi) {
						Cumle_04OperatorIslemi cümle03 = (Cumle_04OperatorIslemi) cümle;
						incelemesiEksikOlanlar.add(cümle03.parametreNo1);
						map.get(cümle03.parametreNo1).silOkuma(satirNo);
						incelemesiEksikOlanlar.add(cümle03.parametreNo2);
						map.get(cümle03.parametreNo2).silOkuma(satirNo);
					} else if (cümle instanceof Cumle_05FonksiyonCagrisi) {
						Cumle_05FonksiyonCagrisi cümle05 = (Cumle_05FonksiyonCagrisi) cümle;
						incelemesiEksikOlanlar.add(cümle05.parametre);
						map.get(cümle05.parametre).silOkuma(satirNo);
					} else if (cümle instanceof Cumle_06DegiskenAtama) {
						Cumle_06DegiskenAtama cümle06 = (Cumle_06DegiskenAtama) cümle;
						incelemesiEksikOlanlar.add(cümle06.değişkenNoKaynak);
						map.get(cümle06.değişkenNoKaynak).silOkuma(satirNo);
					} else if (cümle instanceof Cumle_11SabitAtama) {
						// Cumle_11SabitAtama cümle11 = (Cumle_11SabitAtama) cümle;
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
							Cumle cümle = çıktı.anaFonksiyon.cümleler.get(satirNo - 1);
							if (cümle instanceof Cumle_04OperatorIslemi) {
								Cumle_04OperatorIslemi cümle03 = (Cumle_04OperatorIslemi) cümle;
								incelemesiEksikOlanlar.add(cümle03.parametreNo1);
								map.get(cümle03.parametreNo1).silOkuma(satirNo);
								incelemesiEksikOlanlar.add(cümle03.parametreNo2);
								map.get(cümle03.parametreNo2).silOkuma(satirNo);
							} else if (cümle instanceof Cumle_05FonksiyonCagrisi) {
								Cumle_05FonksiyonCagrisi cümle05 = (Cumle_05FonksiyonCagrisi) cümle;
								incelemesiEksikOlanlar.add(cümle05.parametre);
								map.get(cümle05.parametre).silOkuma(satirNo);
							} else if (cümle instanceof Cumle_06DegiskenAtama) {
								Cumle_06DegiskenAtama cümle06 = (Cumle_06DegiskenAtama) cümle;
								incelemesiEksikOlanlar.add(cümle06.değişkenNoKaynak);
								map.get(cümle06.değişkenNoKaynak).silOkuma(satirNo);
							} else if (cümle instanceof Cumle_11SabitAtama) {
								// Cumle_11SabitAtama cümle11 = (Cumle_11SabitAtama) cümle;
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
		ArrayList<DegiskenNoSatirNo> eklenecekOlanlar = new ArrayList<DegiskenNoSatirNo>();
		while (incelemesiEksikOlanlar.size() > 0) {

			int değişkenNo = incelemesiEksikOlanlar.iterator().next();
			DegiskenKullanimRaporu rapor = map.get(değişkenNo);

			// DegiskenSil komutunu ekle
			int satirNo = rapor.işlemler.get(rapor.işlemler.size() - 1);
			eklenecekOlanlar.add(new DegiskenNoSatirNo(değişkenNo, satirNo + 1));
			// System.out.println("DEĞİŞKEN SİL : " + değişkenNo);

			incelemesiEksikOlanlar.remove(değişkenNo);
		}

		ArrayList<Integer> silinecekOlanlar = new ArrayList<Integer>(silinecekOlanSatırlar);
		Collections.sort(silinecekOlanlar, Collections.reverseOrder());

		Collections.sort(eklenecekOlanlar);

		while (true) {
			if (silinecekOlanlar.isEmpty() && eklenecekOlanlar.isEmpty()) {
				break;
			} else if (silinecekOlanlar.isEmpty()) {
				for (DegiskenNoSatirNo entry : eklenecekOlanlar) {
					çıktı.anaFonksiyon.cümleler.add(entry.satırNo - 1, new Cumle_03DegiskenSil(entry.değişkenNo));
				}
				eklenecekOlanlar.clear();
			} else if (eklenecekOlanlar.isEmpty()) {
				for (int satirNo : silinecekOlanlar) {
					çıktı.anaFonksiyon.cümleler.remove(satirNo - 1);
				}
				silinecekOlanlar.clear();
			} else {
				int silmeSatırNo = silinecekOlanlar.get(0);
				DegiskenNoSatirNo eklemeEntry = eklenecekOlanlar.get(0);
				int eklemeSatırNo = eklemeEntry.satırNo;
				int eklemeDegiskenNo = eklemeEntry.değişkenNo;
				if (silmeSatırNo >= eklemeSatırNo) {
					silinecekOlanlar.remove(0);
					çıktı.anaFonksiyon.cümleler.remove(silmeSatırNo - 1);
				} else {
					eklenecekOlanlar.remove(0);
					çıktı.anaFonksiyon.cümleler.add(eklemeSatırNo - 1, new Cumle_03DegiskenSil(eklemeDegiskenNo));
				}
			}
		}

		sonİşlemler(çıktı, dosyaAdı);

		return çıktı;
	}

}
