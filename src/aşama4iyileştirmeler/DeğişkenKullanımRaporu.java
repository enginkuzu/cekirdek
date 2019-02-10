package aşama4iyileştirmeler;

import java.util.ArrayList;

public class DeğişkenKullanımRaporu {

	public int değişkenNo;
	public int yeniIndeks; // İşlem Sıra Numarası
	//
	public int okumaSayaç = 0;
	public int yazmaSayaç = 0;
	public ArrayList<Integer> işlemler = new ArrayList<Integer>(); // İşlem Sıra Numarası : Okuma +, Yazma -

	public DeğişkenKullanımRaporu(int değişkenNo, int yeniIndeks) {
		this.değişkenNo = değişkenNo;
		this.yeniIndeks = yeniIndeks;
	}

	public void ekleOkuma(Integer işlemSıraNo) {
		okumaSayaç++;
		işlemler.add(işlemSıraNo);
	}

	public void silOkuma(Integer işlemSıraNo) {
		okumaSayaç--;
		if (!işlemler.remove(işlemSıraNo)) {
			System.out.println("ERROR : DeğişkenKullanımRaporu.silOkuma() : işlemSıraNo = " + işlemSıraNo);
		}
	}

	public void ekleYazma(Integer işlemSıraNo) {
		yazmaSayaç++;
		işlemler.add(işlemSıraNo);
	}

	public void silYazma(Integer işlemSıraNo) {
		yazmaSayaç--;
		if (!işlemler.remove(işlemSıraNo)) {
			System.out.println("ERROR : DeğişkenKullanımRaporu.silYazma() : işlemSıraNo = " + işlemSıraNo);
		}
	}

	@Override
	public String toString() {
		return "değişkenNo[" + değişkenNo + "],oku:" + okumaSayaç + ",yaz:" + yazmaSayaç + ",işlemler" + işlemler;
	}

}
