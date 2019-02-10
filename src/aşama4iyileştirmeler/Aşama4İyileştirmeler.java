package aşama4iyileştirmeler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
			} else if (cümle instanceof Cümle_06DeğişkenAtama) {
				Cümle_06DeğişkenAtama cümle06 = (Cümle_06DeğişkenAtama) cümle;
				map.get(cümle06.değişkenNoKaynak).ekleOkuma(i);
				map.get(cümle06.değişkenNoHedef).ekleYazma(-i);
			} else if (cümle instanceof Cümle_10SabitAtama) {
				Cümle_10SabitAtama cümle05 = (Cümle_10SabitAtama) cümle;
				map.get(cümle05.değişkenNo).ekleYazma(-i);
			} else {
				System.out.println("BİLİNMEYEN : " + cümle);
			}
		}

		return map;
	}

	private static void sonİşlemler(Aşama3Çıktısı çıktı, String dosyaAdı) {

		StringBuilder sb = new StringBuilder();

		for (Fonksiyon_01OperatörFonksiyon operatörFonksiyon : çıktı.operatörFonksiyonMap.values()) {
			sb.append(operatörFonksiyon.toString());
			sb.append("\n");
			for (Cümle cümle : operatörFonksiyon.cümleler) {
				sb.append("\t");
				sb.append(cümle.toString());
				sb.append("\n");
			}
		}
		for (Fonksiyon_02İsimliFonksiyon isimFonksiyon : çıktı.isimFonksiyonMap.values()) {
			sb.append(isimFonksiyon.toString());
			sb.append("\n");
			for (Cümle cümle : isimFonksiyon.cümleler) {
				sb.append("\t");
				sb.append(cümle.toString());
				sb.append("\n");
			}
		}
		{
			sb.append(çıktı.anaFonksiyon.toString());
			sb.append("\n");
			for (Cümle cümle : çıktı.anaFonksiyon.cümleler) {
				sb.append("\t");
				sb.append(cümle.toString());
				sb.append("\n");
			}
		}

		String metinÇıktı = sb.toString();
		// System.out.print(metinÇıktı);
		Fonksiyonlar.dosyaKaydet(dosyaAdı + ".4.log", metinÇıktı);
	}

	public static Aşama3Çıktısı işle(Aşama3Çıktısı çıktı, String dosyaAdı) {

		HashMap<Integer, DeğişkenKullanımRaporu> map = değişkenKullanımAnalizi(çıktı.anaFonksiyon.cümleler);

		HashSet<Integer> silinecekOlanSatırlar = new HashSet<Integer>();
		HashSet<Integer> incelemesiEksikOlanlar = new HashSet<Integer>(map.keySet());

		while (incelemesiEksikOlanlar.size() > 0) {

			int değişkenNo = incelemesiEksikOlanlar.iterator().next();
			DeğişkenKullanımRaporu rapor = map.get(değişkenNo);

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
					Cümle cümle = çıktı.anaFonksiyon.cümleler.get(satirNo - 1);
					if (cümle instanceof Cümle_04Operatörİşlemi) {
						Cümle_04Operatörİşlemi cümle03 = (Cümle_04Operatörİşlemi) cümle;
						incelemesiEksikOlanlar.add(cümle03.parametreNo1);
						map.get(cümle03.parametreNo1).silOkuma(satirNo);
						incelemesiEksikOlanlar.add(cümle03.parametreNo2);
						map.get(cümle03.parametreNo2).silOkuma(satirNo);
					} else if (cümle instanceof Cümle_05FonksiyonÇağrısı) {
						Cümle_05FonksiyonÇağrısı cümle05 = (Cümle_05FonksiyonÇağrısı) cümle;
						incelemesiEksikOlanlar.add(cümle05.parametre);
						map.get(cümle05.parametre).silOkuma(satirNo);
					} else if (cümle instanceof Cümle_06DeğişkenAtama) {
						Cümle_06DeğişkenAtama cümle06 = (Cümle_06DeğişkenAtama) cümle;
						incelemesiEksikOlanlar.add(cümle06.değişkenNoKaynak);
						map.get(cümle06.değişkenNoKaynak).silOkuma(satirNo);
					} else if (cümle instanceof Cümle_10SabitAtama) {
						// Cümle_10SabitAtama cümle10 = (Cümle_10SabitAtama) cümle;
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
					} else if (cümle instanceof Cümle_05FonksiyonÇağrısı) {
						Cümle_05FonksiyonÇağrısı cümle05 = (Cümle_05FonksiyonÇağrısı) cümle;
						incelemesiEksikOlanlar.add(cümle05.parametre);
						map.get(cümle05.parametre).silOkuma(satirNo);
					} else if (cümle instanceof Cümle_06DeğişkenAtama) {
						Cümle_06DeğişkenAtama cümle06 = (Cümle_06DeğişkenAtama) cümle;
						incelemesiEksikOlanlar.add(cümle06.değişkenNoKaynak);
						map.get(cümle06.değişkenNoKaynak).silOkuma(satirNo);
					} else if (cümle instanceof Cümle_10SabitAtama) {
						// Cümle_10SabitAtama cümle10 = (Cümle_10SabitAtama) cümle;
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
							} else if (cümle instanceof Cümle_05FonksiyonÇağrısı) {
								Cümle_05FonksiyonÇağrısı cümle05 = (Cümle_05FonksiyonÇağrısı) cümle;
								incelemesiEksikOlanlar.add(cümle05.parametre);
								map.get(cümle05.parametre).silOkuma(satirNo);
							} else if (cümle instanceof Cümle_06DeğişkenAtama) {
								Cümle_06DeğişkenAtama cümle06 = (Cümle_06DeğişkenAtama) cümle;
								incelemesiEksikOlanlar.add(cümle06.değişkenNoKaynak);
								map.get(cümle06.değişkenNoKaynak).silOkuma(satirNo);
							} else if (cümle instanceof Cümle_10SabitAtama) {
								// Cümle_10SabitAtama cümle10 = (Cümle_10SabitAtama) cümle;
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
		ArrayList<DeğişkenNoSatırNo> eklenecekOlanlar = new ArrayList<DeğişkenNoSatırNo>();
		while (incelemesiEksikOlanlar.size() > 0) {

			int değişkenNo = incelemesiEksikOlanlar.iterator().next();
			DeğişkenKullanımRaporu rapor = map.get(değişkenNo);

			// DeğişkenSil komutunu ekle
			int satirNo = rapor.işlemler.get(rapor.işlemler.size() - 1);
			eklenecekOlanlar.add(new DeğişkenNoSatırNo(değişkenNo, satirNo + 1));
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
				for (DeğişkenNoSatırNo entry : eklenecekOlanlar) {
					çıktı.anaFonksiyon.cümleler.add(entry.satırNo - 1, new Cümle_03DeğişkenSil(entry.değişkenNo));
				}
				eklenecekOlanlar.clear();
			} else if (eklenecekOlanlar.isEmpty()) {
				for (int satirNo : silinecekOlanlar) {
					çıktı.anaFonksiyon.cümleler.remove(satirNo - 1);
				}
				silinecekOlanlar.clear();
			} else {
				int silmeSatırNo = silinecekOlanlar.get(0);
				DeğişkenNoSatırNo eklemeEntry = eklenecekOlanlar.get(0);
				int eklemeSatırNo = eklemeEntry.satırNo;
				int eklemeDeğişkenNo = eklemeEntry.değişkenNo;
				if (silmeSatırNo >= eklemeSatırNo) {
					silinecekOlanlar.remove(0);
					çıktı.anaFonksiyon.cümleler.remove(silmeSatırNo - 1);
				} else {
					eklenecekOlanlar.remove(0);
					çıktı.anaFonksiyon.cümleler.add(eklemeSatırNo - 1, new Cümle_03DeğişkenSil(eklemeDeğişkenNo));
				}
			}
		}

		sonİşlemler(çıktı, dosyaAdı);

		return çıktı;
	}

}
