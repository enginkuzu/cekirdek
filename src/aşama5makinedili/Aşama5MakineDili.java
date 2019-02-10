package aşama5makinedili;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;

import aşama3cümleler.Aşama3Çıktısı;
import aşama3cümleler.Cümle;
import aşama3cümleler.Cümle_01DeğişkenYeni;
import aşama3cümleler.Cümle_02GeçiciDeğişkenYeni;
import aşama3cümleler.Cümle_03DeğişkenSil;
import aşama3cümleler.Cümle_04Operatörİşlemi;
import aşama3cümleler.Cümle_05FonksiyonÇağrısı;
import aşama3cümleler.Cümle_10SabitAtama;
import aşama3cümleler.Cümle_06DeğişkenAtama;
import aşama3cümleler.Cümle_07Assembly;
import aşama3cümleler.Cümle_08AssemblyData;
import aşama3cümleler.Cümle_09AssemblyRoData;
import aşama3cümleler.Fonksiyon_01OperatörFonksiyon;
import aşama3cümleler.Fonksiyon_02İsimliFonksiyon;
import yardımcı.Fonksiyonlar;

public class Aşama5MakineDili {

	private static Stack<String> saklaçStack;
	private static int yığıtAdres;
	private static HashMap<Integer, DeğişkenDetaySaklaç> saklaçtakiDeğişkenler;
	private static HashMap<Integer, DeğişkenDetayYığıt> yığıttakiDeğişkenler;
	private static ArrayList<İşlem> işlemler;

	private static int saklaçİlkKullanımİndeksiniBul(Aşama3Çıktısı çıktı, int aktifİndeks, int değişkenNo) {

		for (int i = aktifİndeks; i < çıktı.anaFonksiyon.cümleler.size(); i++) {
			Cümle cümle = çıktı.anaFonksiyon.cümleler.get(i);
			//
			if (cümle instanceof Cümle_03DeğişkenSil) {
				Cümle_03DeğişkenSil cümle02 = (Cümle_03DeğişkenSil) cümle;
				if (cümle02.değişkenNo == değişkenNo) {
					return i;
				}
			} else if (cümle instanceof Cümle_04Operatörİşlemi) {
				Cümle_04Operatörİşlemi cümle03 = (Cümle_04Operatörİşlemi) cümle;
				if (cümle03.değişkenNo == değişkenNo) {
					return i;
				}
				if (cümle03.parametreNo1 == değişkenNo) {
					return i;
				}
				if (cümle03.parametreNo2 == değişkenNo) {
					return i;
				}
			} else if (cümle instanceof Cümle_05FonksiyonÇağrısı) {
				Cümle_05FonksiyonÇağrısı cümle04 = (Cümle_05FonksiyonÇağrısı) cümle;
				if (cümle04.parametre == değişkenNo) {
					return i;
				}
			} else if (cümle instanceof Cümle_10SabitAtama) {
				Cümle_10SabitAtama cümle05 = (Cümle_10SabitAtama) cümle;
				if (cümle05.değişkenNo == değişkenNo) {
					return i;
				}
			} else if (cümle instanceof Cümle_06DeğişkenAtama) {
				Cümle_06DeğişkenAtama cümle06 = (Cümle_06DeğişkenAtama) cümle;
				if (cümle06.değişkenNoHedef == değişkenNo) {
					return i;
				}
				if (cümle06.değişkenNoKaynak == değişkenNo) {
					return i;
				}
			}
		}

		işlemler.add(new İşlem_03AssemblyKomutu("-TODO-(saklaçİlkKullanımİndeksiniBul() fonksiyonu -1 döndü)-\n"));
		return -1;
	}

	private static void saklaçtaYerAç(Aşama3Çıktısı çıktı, int aktifİndeks) {

		for (Iterator<DeğişkenDetaySaklaç> it = saklaçtakiDeğişkenler.values().iterator(); it.hasNext();) {
			DeğişkenDetaySaklaç dd = it.next();
			if (dd.ilkKullanımİndeksi < aktifİndeks) {
				dd.ilkKullanımİndeksi = saklaçİlkKullanımİndeksiniBul(çıktı, aktifİndeks, dd.değişkenNo);
			}
		}

		Iterator<DeğişkenDetaySaklaç> it = saklaçtakiDeğişkenler.values().iterator();
		DeğişkenDetaySaklaç seçilenDeğişken = it.next();
		while (it.hasNext()) {
			DeğişkenDetaySaklaç dd = it.next();
			if (dd.ilkKullanımİndeksi > seçilenDeğişken.ilkKullanımİndeksi) {
				seçilenDeğişken = dd;
			}
		}

		// System.out.println(saklaçtakiDeğişkenler);
		saklaçStack.push(seçilenDeğişken.saklaçAdresi);
		saklaçtakiDeğişkenler.remove(seçilenDeğişken.değişkenNo);
		yığıtAdres++;
		işlemler.add(new İşlem_02SaklaçtanYığıta(seçilenDeğişken.saklaçAdresi, yığıtAdres));
		DeğişkenDetayYığıt ddy = new DeğişkenDetayYığıt(seçilenDeğişken.değişkenNo, yığıtAdres,
				seçilenDeğişken.ilkKullanımİndeksi);
		yığıttakiDeğişkenler.put(seçilenDeğişken.değişkenNo, ddy);
		// System.out.println("Yığıta > " + seçilenDeğişken.değişkenNo);
	}

	private static void değişkeniYığıttanSaklacaTaşı(int değişkenNo) {
		DeğişkenDetayYığıt ddy = yığıttakiDeğişkenler.remove(değişkenNo);
		String saklaç = saklaçStack.pop();
		işlemler.add(new İşlem_01YığıttanSaklaca(ddy.yığıtAdresi, saklaç));
		saklaçtakiDeğişkenler.put(değişkenNo, new DeğişkenDetaySaklaç(değişkenNo, saklaç, ddy.ilkKullanımİndeksi));
		// System.out.println("Saklaca < " + değişkenNo);
	}

	public static void işle(Aşama3Çıktısı çıktı, String dosyaAdı) {

		saklaçStack = new Stack<String>() {
			{
				// push("r10");
				// push("r8");
				// push("r9");
				// push("rbx");
				push("r12");
				push("r13");
				push("r14");
				push("r15");
			}
		};
		yığıtAdres = 0;
		saklaçtakiDeğişkenler = new HashMap<Integer, DeğişkenDetaySaklaç>();
		yığıttakiDeğişkenler = new HashMap<Integer, DeğişkenDetayYığıt>();
		işlemler = new ArrayList<İşlem>();

		StringBuilder sb = new StringBuilder();
		sb.append("\t.intel_syntax noprefix\n");
		sb.append("\n");

		sb.append("\t.section\t.data\n\n");
		for (Fonksiyon_02İsimliFonksiyon isimFonksiyon : çıktı.isimFonksiyonMap.values()) {
			for (Cümle cümle : isimFonksiyon.cümleler) {
				if (cümle instanceof Cümle_08AssemblyData) {
					String assembly = ((Cümle_08AssemblyData) cümle).ifade;
					sb.append("\t" + assembly + "\n");
				}
			}
		}
		sb.append("\n");

		sb.append("\t.section\t.rodata\n\n");
		for (Fonksiyon_02İsimliFonksiyon isimFonksiyon : çıktı.isimFonksiyonMap.values()) {
			for (Cümle cümle : isimFonksiyon.cümleler) {
				if (cümle instanceof Cümle_09AssemblyRoData) {
					String assembly = ((Cümle_09AssemblyRoData) cümle).ifade;
					sb.append("\t" + assembly + "\n");
				}
			}
		}
		sb.append("\n");

		sb.append("\t.section\t.text\n");

		for (Fonksiyon_02İsimliFonksiyon isimFonksiyon : çıktı.isimFonksiyonMap.values()) {
			sb.append("\n\t.globl	" + isimFonksiyon.isim + "\n");
			sb.append(isimFonksiyon.isim + ":\n");
			for (Cümle cümle : isimFonksiyon.cümleler) {
				if (cümle instanceof Cümle_07Assembly) {
					String assembly = ((Cümle_07Assembly) cümle).ifade;
					if (isimFonksiyon.değişken1İsim != null && isimFonksiyon.değişken1Tip != null) {
						assembly = assembly.replace(isimFonksiyon.değişken1İsim, isimFonksiyon.değişken1Tip);
					}
					sb.append("\t" + assembly + "\n");
				}
			}
		}

		sb.append("\n\t.globl	_start\n");
		sb.append("_start:\n");

		for (int i = 0; i < çıktı.anaFonksiyon.cümleler.size(); i++) {
			Cümle cümle = çıktı.anaFonksiyon.cümleler.get(i);
			//
			if (cümle instanceof Cümle_01DeğişkenYeni) {
				Cümle_01DeğişkenYeni cümle01 = (Cümle_01DeğişkenYeni) cümle;
				if (saklaçStack.isEmpty()) {
					saklaçtaYerAç(çıktı, i);
				}
				saklaçtakiDeğişkenler.put(cümle01.değişkenNo,
						new DeğişkenDetaySaklaç(cümle01.değişkenNo, saklaçStack.pop()));
				işlemler.add(new İşlem_03AssemblyKomutu("\n\t# " + cümle + "\n"));
			} else if (cümle instanceof Cümle_02GeçiciDeğişkenYeni) {
				Cümle_02GeçiciDeğişkenYeni cümle02 = (Cümle_02GeçiciDeğişkenYeni) cümle;
				if (saklaçStack.isEmpty()) {
					saklaçtaYerAç(çıktı, i);
				}
				saklaçtakiDeğişkenler.put(cümle02.değişkenNo,
						new DeğişkenDetaySaklaç(cümle02.değişkenNo, saklaçStack.pop()));
				işlemler.add(new İşlem_03AssemblyKomutu("\n\t# " + cümle + "\n"));
			} else if (cümle instanceof Cümle_03DeğişkenSil) {
				Cümle_03DeğişkenSil cümle02 = (Cümle_03DeğişkenSil) cümle;
				işlemler.add(new İşlem_03AssemblyKomutu("\n\t# " + cümle + "\n"));
				if (saklaçtakiDeğişkenler.containsKey(cümle02.değişkenNo)) {
					saklaçStack.push(saklaçtakiDeğişkenler.remove(cümle02.değişkenNo).saklaçAdresi);
				} else {
					işlemler.add(new İşlem_03AssemblyKomutu("-TODO-(değişken yığıttan silinecek)-\n"));
				}
			} else if (cümle instanceof Cümle_04Operatörİşlemi) {
				Cümle_04Operatörİşlemi cümle03 = (Cümle_04Operatörİşlemi) cümle;
				String anahtar = çıktı.anaFonksiyon.değişkenNoMap.get(cümle03.parametreNo1).değişkenTipi
						+ cümle03.operatör + çıktı.anaFonksiyon.değişkenNoMap.get(cümle03.parametreNo2).değişkenTipi;
				Fonksiyon_01OperatörFonksiyon operatörFonksiyon = çıktı.operatörFonksiyonMap.get(anahtar);
				if (!saklaçtakiDeğişkenler.containsKey(cümle03.parametreNo1)) {
					if (saklaçStack.isEmpty()) {
						saklaçtaYerAç(çıktı, i);
					}
					değişkeniYığıttanSaklacaTaşı(cümle03.parametreNo1);
				}
				if (!saklaçtakiDeğişkenler.containsKey(cümle03.parametreNo2)) {
					if (saklaçStack.isEmpty()) {
						saklaçtaYerAç(çıktı, i);
					}
					değişkeniYığıttanSaklacaTaşı(cümle03.parametreNo2);
				}
				if (!saklaçtakiDeğişkenler.containsKey(cümle03.değişkenNo)) {
					if (saklaçStack.isEmpty()) {
						saklaçtaYerAç(çıktı, i);
					}
					değişkeniYığıttanSaklacaTaşı(cümle03.değişkenNo);
				}
				String assembly = ((Cümle_07Assembly) operatörFonksiyon.cümleler.get(0)).ifade;
				assembly = assembly.replace(operatörFonksiyon.değişken1İsim,
						saklaçtakiDeğişkenler.get(cümle03.parametreNo1).saklaçAdresi);
				assembly = assembly.replace(operatörFonksiyon.değişken2İsim,
						saklaçtakiDeğişkenler.get(cümle03.parametreNo2).saklaçAdresi);
				assembly = assembly.replace(operatörFonksiyon.sonuçİsim,
						saklaçtakiDeğişkenler.get(cümle03.değişkenNo).saklaçAdresi);
				işlemler.add(new İşlem_03AssemblyKomutu("\n\t# " + cümle + "\n"));
				işlemler.add(new İşlem_03AssemblyKomutu("\t" + assembly + "\n"));
			} else if (cümle instanceof Cümle_05FonksiyonÇağrısı) {
				Cümle_05FonksiyonÇağrısı cümle04 = (Cümle_05FonksiyonÇağrısı) cümle;
				if (!saklaçtakiDeğişkenler.containsKey(cümle04.parametre)) {
					if (saklaçStack.isEmpty()) {
						saklaçtaYerAç(çıktı, i);
					}
					değişkeniYığıttanSaklacaTaşı(cümle04.parametre);
				}
				String saklaç = saklaçtakiDeğişkenler.get(cümle04.parametre).saklaçAdresi;
				işlemler.add(new İşlem_03AssemblyKomutu("\n\t# " + cümle + "\n"));
				işlemler.add(new İşlem_03AssemblyKomutu("\tmov rax," + saklaç + "\n"));
				işlemler.add(new İşlem_03AssemblyKomutu("\tcall printhn\n"));
			} else if (cümle instanceof Cümle_10SabitAtama) {
				Cümle_10SabitAtama cümle05 = (Cümle_10SabitAtama) cümle;
				if (!saklaçtakiDeğişkenler.containsKey(cümle05.değişkenNo)) {
					if (saklaçStack.isEmpty()) {
						saklaçtaYerAç(çıktı, i);
					}
					değişkeniYığıttanSaklacaTaşı(cümle05.değişkenNo);
				}
				String saklaç = saklaçtakiDeğişkenler.get(cümle05.değişkenNo).saklaçAdresi;
				işlemler.add(new İşlem_03AssemblyKomutu("\n\t# " + cümle + "\n"));
				işlemler.add(new İşlem_03AssemblyKomutu("\tmov " + saklaç + "," + cümle05.sabitVeri + "\n"));
			} else if (cümle instanceof Cümle_06DeğişkenAtama) {
				Cümle_06DeğişkenAtama cümle06 = (Cümle_06DeğişkenAtama) cümle;
				if (!saklaçtakiDeğişkenler.containsKey(cümle06.değişkenNoKaynak)) {
					if (saklaçStack.isEmpty()) {
						saklaçtaYerAç(çıktı, i);
					}
					değişkeniYığıttanSaklacaTaşı(cümle06.değişkenNoKaynak);
				}
				if (!saklaçtakiDeğişkenler.containsKey(cümle06.değişkenNoHedef)) {
					if (saklaçStack.isEmpty()) {
						saklaçtaYerAç(çıktı, i);
					}
					değişkeniYığıttanSaklacaTaşı(cümle06.değişkenNoHedef);
				}
				String saklaçKaynak = saklaçtakiDeğişkenler.get(cümle06.değişkenNoKaynak).saklaçAdresi;
				String saklaçHedef = saklaçtakiDeğişkenler.get(cümle06.değişkenNoHedef).saklaçAdresi;
				işlemler.add(new İşlem_03AssemblyKomutu("\n\t# " + cümle + "\n"));
				işlemler.add(new İşlem_03AssemblyKomutu("\tmov " + saklaçHedef + "," + saklaçKaynak + "\n"));
			} else {
				işlemler.add(new İşlem_03AssemblyKomutu("\n\t# " + cümle + "\n"));
				işlemler.add(new İşlem_03AssemblyKomutu("-TODO-(" + cümle + ")-\n"));
			}
			//
			// System.out.println(cümle);
		}

		sb.append("\tpush rbp\n");
		sb.append("\tmov rbp, rsp\n");
		sb.append("\tsub rsp, " + (yığıtAdres * 8) + "\n");
		for (Iterator<İşlem> it = işlemler.iterator(); it.hasNext();) {
			İşlem işlem = it.next();
			//
			if (işlem instanceof İşlem_01YığıttanSaklaca) {
				İşlem_01YığıttanSaklaca işlem01 = (İşlem_01YığıttanSaklaca) işlem;
				int adres = (yığıtAdres - işlem01.yığıtİndeks + 1) * 8;
				sb.append("\n");
				sb.append("\t# Yığıt-Saklaç : " + işlem01);
				sb.append("\tmov " + işlem01.saklaç + ", QWORD PTR -" + adres + "[rbp]\n");
			} else if (işlem instanceof İşlem_02SaklaçtanYığıta) {
				İşlem_02SaklaçtanYığıta işlem02 = (İşlem_02SaklaçtanYığıta) işlem;
				int adres = (yığıtAdres - işlem02.yığıtİndeks + 1) * 8;
				sb.append("\n");
				sb.append("\t# Saklaç-Yığıt : " + işlem02);
				sb.append("\tmov QWORD PTR -" + adres + "[rbp], " + işlem02.saklaç + "\n");
			} else if (işlem instanceof İşlem_03AssemblyKomutu) {
				İşlem_03AssemblyKomutu işlem03 = (İşlem_03AssemblyKomutu) işlem;
				sb.append(işlem03.asssembly);
			}
		}
		sb.append("\tleave\n");

		sb.append("\n");
		sb.append("\tcall exit\n");
		sb.append("\tret\n");

		Fonksiyonlar.dosyaKaydet(dosyaAdı + ".5.s", sb.toString());
	}

}