package aşama5makinedili;

public class İşlem_01YığıttanSaklaca extends İşlem {

	public int yığıtİndeks;
	public String saklaç;

	public İşlem_01YığıttanSaklaca(int yığıtİndeks, String saklaç) {
		this.yığıtİndeks = yığıtİndeks;
		this.saklaç = saklaç;
	}

	public String toString() {
		return saklaç + " < " + yığıtİndeks + "\n";
	}

}
