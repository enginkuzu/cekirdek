package aşama5makinedili;

public class İşlem_02SaklaçtanYığıta extends İşlem {

	public String saklaç;
	public int yığıtİndeks;

	public İşlem_02SaklaçtanYığıta(String saklaç, int yığıtİndeks) {
		this.saklaç = saklaç;
		this.yığıtİndeks = yığıtİndeks;
	}

	public String toString() {
		return yığıtİndeks + " < " + saklaç + "\n";
	}

}
