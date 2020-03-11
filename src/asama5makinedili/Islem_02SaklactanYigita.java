package asama5makinedili;

public class Islem_02SaklactanYigita extends Islem {

	public String saklaç;
	public int yığıtİndeks;

	public Islem_02SaklactanYigita(String saklaç, int yığıtİndeks) {
		this.saklaç = saklaç;
		this.yığıtİndeks = yığıtİndeks;
	}

	public String toString() {
		return yığıtİndeks + " < " + saklaç + "\n";
	}

}
