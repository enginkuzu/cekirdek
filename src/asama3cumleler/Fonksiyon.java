package asama3cumleler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import yardimci.Degiskenler.FONKSİYON;

public abstract class Fonksiyon {

	private static int sonFonksiyonId = 0;

	public FONKSİYON fonksiyon;
	public int fonksiyonId;

	public int geçiciDegiskenNo = 0;
	public int gerçekDegiskenNo = 0;
	public HashMap<Integer, Degisken> değişkenNoMap = new HashMap<Integer, Degisken>();
	public HashMap<String, Degisken> değişkenİsimMap = new HashMap<String, Degisken>();

	public HashMap<String, HashSet<String>> özellikMap = new HashMap<String, HashSet<String>>();

	public ArrayList<Cumle> cümleler = new ArrayList<Cumle>();

	public Fonksiyon(FONKSİYON fonksiyon) {
		this.fonksiyon = fonksiyon;
	}

	public int yeniFonksiyonIdGetir() {
		return --sonFonksiyonId;
	}

}
