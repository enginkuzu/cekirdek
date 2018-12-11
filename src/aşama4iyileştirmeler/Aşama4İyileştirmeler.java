package aşama4iyileştirmeler;

import java.util.ArrayList;

import aşama3cümleler.Aşama3Çıktısı;
import aşama3cümleler.Cümle;
import aşama3cümleler.Cümle_02DeğişkenSil;
import aşama3cümleler.Cümle_03Operatörİşlemi;
import aşama3cümleler.Cümle_04FonksiyonÇağrısı;
import aşama3cümleler.Cümle_06DeğişkenAtama;
import yardımcı.Fonksiyonlar;

public class Aşama4İyileştirmeler {

	private static int sonOkumayıBul(ArrayList<Cümle> cümleler, int değişkenNo) {
		int ret = -1;
		int i = 0;
		for (Cümle cümle : cümleler) {
			if (cümle instanceof Cümle_03Operatörİşlemi) {
				Cümle_03Operatörİşlemi cümle03 = (Cümle_03Operatörİşlemi) cümle;
				if (cümle03.parametreNo1 == değişkenNo || cümle03.parametreNo2 == değişkenNo) {
					ret = i;
				}
			} else if (cümle instanceof Cümle_04FonksiyonÇağrısı) {
				Cümle_04FonksiyonÇağrısı cümle04 = (Cümle_04FonksiyonÇağrısı) cümle;
				if (cümle04.parametre == değişkenNo) {
					ret = i;
				}
			} else if (cümle instanceof Cümle_06DeğişkenAtama) {
				Cümle_06DeğişkenAtama cümle06 = (Cümle_06DeğişkenAtama) cümle;
				if (cümle06.değişkenNoKaynak == değişkenNo) {
					ret = i;
				}
			}
			i++;
		}
		return ret;
	}

	public static ArrayList<Cümle> işle(Aşama3Çıktısı cümleler, String dosyaAdı) {

		// TODO 1 : Bir değişken tanımlandıktan sonra hiç okuma işlemi yapılmamışsa
		// değişkenle ilgili tüm ifadeleri kaldır
		// TODO 2 : Değişkene ait son okuma işleminden sonraki yazma işlemlerini kaldır
		// TODO 3 : Değişkene ait son okuma işleminden sonra DeğişkenSil komutu eklensin
		// TODO 4 : Bir değişkene ard arda yapılan yazma işlemlerinin sonuncusu hariç
		// diğerlerini kaldır
		// TODO 5 : Bir değişkene dırasıyla 1 yazma 1 okuma yapılıyorsa o değişkeni
		// aradan çıkart (kaldır)
		// TODO 6 : Değişkenle ilgili işleri grupla (yer değiştirme yaparak)

		for (int i = -1; i >= cümleler.geçiciDeğişkenNo; i--) {
			int sıraNo = sonOkumayıBul(cümleler.cümleler, i);
			if (sıraNo > 0) {
				cümleler.cümleler.add(sıraNo + 1, new Cümle_02DeğişkenSil(i));
			}
		}
		for (int i = 1; i <= cümleler.gerçekDeğişkenNo; i++) {
			int sıraNo = sonOkumayıBul(cümleler.cümleler, i);
			if (sıraNo > 0) {
				cümleler.cümleler.add(sıraNo + 1, new Cümle_02DeğişkenSil(i));
			}
		}

		StringBuilder stringBuilder = new StringBuilder();
		for (Cümle cümle : cümleler.cümleler) {
			stringBuilder.append(cümle.toString());
			stringBuilder.append("\n");
		}
		String çıktı = stringBuilder.toString();
		// System.out.print(çıktı);
		if (dosyaAdı != null) {
			Fonksiyonlar.dosyaKaydet("kodlar/" + dosyaAdı + "_a4.txt", çıktı);
		}

		return cümleler.cümleler;
	}

}
