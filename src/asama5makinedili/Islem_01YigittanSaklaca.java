package asama5makinedili;

public class Islem_01YigittanSaklaca extends Islem {

	public int yığıtİndeks;
	public String saklaç;

	public Islem_01YigittanSaklaca(int yığıtİndeks, String saklaç) {
		this.yığıtİndeks = yığıtİndeks;
		this.saklaç = saklaç;
	}

	public String toString() {
		return saklaç + " < " + yığıtİndeks + "\n";
	}

}
