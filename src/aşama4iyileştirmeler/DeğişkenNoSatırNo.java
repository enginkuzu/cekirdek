package aşama4iyileştirmeler;

public class DeğişkenNoSatırNo implements Comparable<DeğişkenNoSatırNo> {

	public int değişkenNo;
	public int satırNo;

	public DeğişkenNoSatırNo(int değişkenNo, int satırNo) {
		this.değişkenNo = değişkenNo;
		this.satırNo = satırNo;
	}

	@Override
	public int compareTo(DeğişkenNoSatırNo diğerNesne) {
		if (this.satırNo > diğerNesne.satırNo) {
			return -1;
		} else if (this.satırNo == diğerNesne.satırNo) {
			return 0;
		} else {
			return 1;
		}
	}

}
