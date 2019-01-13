package aşama5makinedili;

public class DeğişkenDetaySaklaç {

	public int değişkenNo;
	public String saklaçAdresi;
	public int ilkKullanımİndeksi;

	public DeğişkenDetaySaklaç(int değişkenNo, String saklaçAdresi) {
		this.değişkenNo = değişkenNo;
		this.saklaçAdresi = saklaçAdresi;
		this.ilkKullanımİndeksi = -1;
	}

	public DeğişkenDetaySaklaç(int değişkenNo, String saklaçAdresi, int ilkKullanımİndeksi) {
		this.değişkenNo = değişkenNo;
		this.saklaçAdresi = saklaçAdresi;
		this.ilkKullanımİndeksi = ilkKullanımİndeksi;
	}

	public String toString() {
		return "(" + saklaçAdresi + "," + ilkKullanımİndeksi + ")";
	}

}
