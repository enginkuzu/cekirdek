package asama4iyilestirmeler;

import java.util.ArrayList;

public class DegiskenKullanimRaporu {

	public int değişkenNo;
	public int yeniIndeks; // İşlem Sıra Numarası
	//
	public int okumaSayaç = 0;
	public int yazmaSayaç = 0;
	public ArrayList<Integer> işlemler = new ArrayList<Integer>(); // İşlem Sıra Numarası : Okuma +, Yazma -

	public DegiskenKullanimRaporu(int değişkenNo, int yeniIndeks) {
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
			System.out.println("HATA : DegiskenKullanimRaporu.silOkuma() : işlemSıraNo = " + işlemSıraNo);
		}
	}

	public void ekleYazma(Integer işlemSıraNo) {
		yazmaSayaç++;
		işlemler.add(işlemSıraNo);
	}

	public void silYazma(Integer işlemSıraNo) {
		yazmaSayaç--;
		if (!işlemler.remove(işlemSıraNo)) {
			System.out.println("HATA : DegiskenKullanimRaporu.silYazma() : işlemSıraNo = " + işlemSıraNo);
		}
	}

	@Override
	public String toString() {
		return "değişkenNo[" + değişkenNo + "],oku:" + okumaSayaç + ",yaz:" + yazmaSayaç + ",işlemler" + işlemler;
	}

}
