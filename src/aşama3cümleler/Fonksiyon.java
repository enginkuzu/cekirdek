package aşama3cümleler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import yardımcı.Değişkenler.FONKSİYON;

public abstract class Fonksiyon {

	private static int sonFonksiyonId = 0;

	public FONKSİYON fonksiyon;
	public int fonksiyonId;

	public int geçiciDeğişkenNo = 0;
	public int gerçekDeğişkenNo = 0;
	public HashMap<Integer, Değişken> değişkenNoMap = new HashMap<Integer, Değişken>();
	public HashMap<String, Değişken> değişkenİsimMap = new HashMap<String, Değişken>();

	public HashMap<String, HashSet<String>> özellikMap = new HashMap<String, HashSet<String>>();

	public ArrayList<Cümle> cümleler = new ArrayList<Cümle>();

	public Fonksiyon(FONKSİYON fonksiyon) {
		this.fonksiyon = fonksiyon;
		this.fonksiyonId = ++sonFonksiyonId;
	}

}
