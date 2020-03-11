package asama5makinedili;

public class DegiskenDetaySaklac {

	public int değişkenNo;
	public String saklaçAdresi;
	public int ilkKullanımİndeksi;

	public DegiskenDetaySaklac(int değişkenNo, String saklaçAdresi) {
		this.değişkenNo = değişkenNo;
		this.saklaçAdresi = saklaçAdresi;
		this.ilkKullanımİndeksi = -1;
	}

	public DegiskenDetaySaklac(int değişkenNo, String saklaçAdresi, int ilkKullanımİndeksi) {
		this.değişkenNo = değişkenNo;
		this.saklaçAdresi = saklaçAdresi;
		this.ilkKullanımİndeksi = ilkKullanımİndeksi;
	}

	public String toString() {
		return "(" + saklaçAdresi + "," + ilkKullanımİndeksi + ")";
	}

}
