package asama4iyilestirmeler;

public class DegiskenNoSatirNo implements Comparable<DegiskenNoSatirNo> {

	public int değişkenNo;
	public int satırNo;

	public DegiskenNoSatirNo(int değişkenNo, int satırNo) {
		this.değişkenNo = değişkenNo;
		this.satırNo = satırNo;
	}

	@Override
	public int compareTo(DegiskenNoSatirNo diğerNesne) {
		if (this.satırNo > diğerNesne.satırNo) {
			return -1;
		} else if (this.satırNo == diğerNesne.satırNo) {
			return 0;
		} else {
			return 1;
		}
	}

}
