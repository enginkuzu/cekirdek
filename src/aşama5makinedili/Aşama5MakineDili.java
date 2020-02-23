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
import aşama3cümleler.Cümle_11SabitAtama;
import aşama3cümleler.Cümle_06DeğişkenAtama;
import aşama3cümleler.Cümle_07MakineDiliKod;
import aşama3cümleler.Cümle_08MakineDiliVeri;
import aşama3cümleler.Cümle_09MakineDiliSabitVeri;
import aşama3cümleler.Cümle_10MakineDiliSembol;
import aşama3cümleler.Fonksiyon_01OperatörFonksiyon;
import aşama3cümleler.Fonksiyon_02İsimliFonksiyon;
import yardımcı.Değişkenler;
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
			} else if (cümle instanceof Cümle_11SabitAtama) {
				Cümle_11SabitAtama cümle05 = (Cümle_11SabitAtama) cümle;
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

		işlemler.add(
				new İşlem_03MakineDiliKomutu("-BİLİNMEYEN-(saklaçİlkKullanımİndeksiniBul() fonksiyonu -1 döndü)-\n"));
		return -1;
	}

	private static void saklaçtaYerAç(Aşama3Çıktısı çıktı, int aktifİndeks) {

		for (Iterator<DeğişkenDetaySaklaç> it = saklaçtakiDeğişkenler.values().iterator(); it.hasNext();) {
			DeğişkenDetaySaklaç detay = it.next();
			if (detay.ilkKullanımİndeksi < aktifİndeks) {
				detay.ilkKullanımİndeksi = saklaçİlkKullanımİndeksiniBul(çıktı, aktifİndeks, detay.değişkenNo);
			}
		}

		Iterator<DeğişkenDetaySaklaç> it = saklaçtakiDeğişkenler.values().iterator();
		DeğişkenDetaySaklaç seçilenDeğişken = it.next();
		while (it.hasNext()) {
			DeğişkenDetaySaklaç detay = it.next();
			if (detay.ilkKullanımİndeksi > seçilenDeğişken.ilkKullanımİndeksi) {
				seçilenDeğişken = detay;
			}
		}

		// System.out.println(saklaçtakiDeğişkenler);
		saklaçStack.push(seçilenDeğişken.saklaçAdresi);
		saklaçtakiDeğişkenler.remove(seçilenDeğişken.değişkenNo);
		yığıtAdres++;
		işlemler.add(new İşlem_02SaklaçtanYığıta(seçilenDeğişken.saklaçAdresi, yığıtAdres));
		DeğişkenDetayYığıt detay = new DeğişkenDetayYığıt(seçilenDeğişken.değişkenNo, yığıtAdres,
				seçilenDeğişken.ilkKullanımİndeksi);
		yığıttakiDeğişkenler.put(seçilenDeğişken.değişkenNo, detay);
		// System.out.println("Yığıta > " + seçilenDeğişken.değişkenNo);
	}

	private static void değişkeniYığıttanSaklacaTaşı(int değişkenNo) {
		DeğişkenDetayYığıt detay = yığıttakiDeğişkenler.remove(değişkenNo);
		String saklaç = saklaçStack.pop();
		işlemler.add(new İşlem_01YığıttanSaklaca(detay.yığıtAdresi, saklaç));
		saklaçtakiDeğişkenler.put(değişkenNo, new DeğişkenDetaySaklaç(değişkenNo, saklaç, detay.ilkKullanımİndeksi));
		// System.out.println("Saklaca < " + değişkenNo);
	}

	private static String fonksiyonIdHazırla(int fonksiyonId) {
		return fonksiyonId < 0 ? "_" + (-fonksiyonId) : "" + fonksiyonId;
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
				if (cümle instanceof Cümle_08MakineDiliVeri) {
					String veri = ((Cümle_08MakineDiliVeri) cümle).veri;
					sb.append("\t" + veri + "\n");
				}
			}
		}
		sb.append("\n");

		sb.append("\t.section\t.rodata\n\n");
		for (Fonksiyon_02İsimliFonksiyon isimFonksiyon : çıktı.isimFonksiyonMap.values()) {
			for (Cümle cümle : isimFonksiyon.cümleler) {
				if (cümle instanceof Cümle_09MakineDiliSabitVeri) {
					String sabitVeri = ((Cümle_09MakineDiliSabitVeri) cümle).sabitVeri;
					sb.append("\t" + sabitVeri + "\n");
				}
			}
		}
		for (int i = 0; i < çıktı.anaFonksiyon.cümleler.size(); i++) {
			Cümle cümle = çıktı.anaFonksiyon.cümleler.get(i);
			if (cümle instanceof Cümle_11SabitAtama) {
				Cümle_11SabitAtama cümle11 = (Cümle_11SabitAtama) cümle;
				if (cümle11.sabitVeriTipiId == Değişkenler.ID_str) {
					sb.append("\t.str_" + cümle11.değişkenNo + ":\n");
					sb.append("\t.byte " + cümle11.sabitVeri.getBytes().length + "\n");
					sb.append("\t.string \"" + cümle11.sabitVeri + "\"\n");
				}
			}
		}
		sb.append("\n");

		sb.append("\t.section\t.bss\n\n");
		for (Fonksiyon_02İsimliFonksiyon isimFonksiyon : çıktı.isimFonksiyonMap.values()) {
			for (Cümle cümle : isimFonksiyon.cümleler) {
				if (cümle instanceof Cümle_10MakineDiliSembol) {
					String sembol = ((Cümle_10MakineDiliSembol) cümle).sembol;
					sb.append("\t" + sembol + "\n");
				}
			}
		}
		sb.append("\n");

		sb.append("\t.section\t.text\n");

		for (Fonksiyon_02İsimliFonksiyon isimFonksiyon : çıktı.isimFonksiyonMap.values()) {
			String fonksiyonId = fonksiyonIdHazırla(isimFonksiyon.fonksiyonId);
			sb.append("\n\t.globl\tfn" + fonksiyonId + "\n");
			sb.append("fn" + fonksiyonId + ":\n");
			for (Cümle cümle : isimFonksiyon.cümleler) {
				if (cümle instanceof Cümle_07MakineDiliKod) {
					String kod = ((Cümle_07MakineDiliKod) cümle).kod;
					if (isimFonksiyon.değişken1İsim != null && isimFonksiyon.değişken1TipAssembly != null) {
						kod = kod.replace(isimFonksiyon.değişken1İsim, isimFonksiyon.değişken1TipAssembly);
					}
					if (isimFonksiyon.sonuçİsim != null && isimFonksiyon.sonuçTipAssembly != null) {
						kod = kod.replace(isimFonksiyon.sonuçİsim, isimFonksiyon.sonuçTipAssembly);
					}
					sb.append("\t" + kod + "\n");
				}
			}
		}

		sb.append("\n\t.globl\t_start\n");
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
				işlemler.add(new İşlem_03MakineDiliKomutu("\n\t# " + cümle + "\n"));
			} else if (cümle instanceof Cümle_02GeçiciDeğişkenYeni) {
				Cümle_02GeçiciDeğişkenYeni cümle02 = (Cümle_02GeçiciDeğişkenYeni) cümle;
				if (saklaçStack.isEmpty()) {
					saklaçtaYerAç(çıktı, i);
				}
				saklaçtakiDeğişkenler.put(cümle02.değişkenNo,
						new DeğişkenDetaySaklaç(cümle02.değişkenNo, saklaçStack.pop()));
				işlemler.add(new İşlem_03MakineDiliKomutu("\n\t# " + cümle + "\n"));
			} else if (cümle instanceof Cümle_03DeğişkenSil) {
				Cümle_03DeğişkenSil cümle02 = (Cümle_03DeğişkenSil) cümle;
				işlemler.add(new İşlem_03MakineDiliKomutu("\n\t# " + cümle + "\n"));
				if (saklaçtakiDeğişkenler.containsKey(cümle02.değişkenNo)) {
					saklaçStack.push(saklaçtakiDeğişkenler.remove(cümle02.değişkenNo).saklaçAdresi);
				} else {
					işlemler.add(new İşlem_03MakineDiliKomutu("-BİLİNMEYEN-(değişken yığıttan silinecek)-\n"));
				}
			} else if (cümle instanceof Cümle_04Operatörİşlemi) {
				Cümle_04Operatörİşlemi cümle03 = (Cümle_04Operatörİşlemi) cümle;
				String anahtar = çıktı.anaFonksiyon.değişkenNoMap.get(cümle03.parametreNo1).değişkenTipiId
						+ cümle03.operatör + çıktı.anaFonksiyon.değişkenNoMap.get(cümle03.parametreNo2).değişkenTipiId;
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
				String kod = ((Cümle_07MakineDiliKod) operatörFonksiyon.cümleler.get(0)).kod;
				kod = kod.replace(operatörFonksiyon.değişken1İsim,
						saklaçtakiDeğişkenler.get(cümle03.parametreNo1).saklaçAdresi);
				kod = kod.replace(operatörFonksiyon.değişken2İsim,
						saklaçtakiDeğişkenler.get(cümle03.parametreNo2).saklaçAdresi);
				kod = kod.replace(operatörFonksiyon.sonuçİsim,
						saklaçtakiDeğişkenler.get(cümle03.değişkenNo).saklaçAdresi);
				işlemler.add(new İşlem_03MakineDiliKomutu("\n\t# " + cümle + "\n"));
				işlemler.add(new İşlem_03MakineDiliKomutu("\t" + kod + "\n"));
			} else if (cümle instanceof Cümle_05FonksiyonÇağrısı) {
				Cümle_05FonksiyonÇağrısı cümle04 = (Cümle_05FonksiyonÇağrısı) cümle;
				if (!saklaçtakiDeğişkenler.containsKey(cümle04.parametre)) {
					if (saklaçStack.isEmpty()) {
						saklaçtaYerAç(çıktı, i);
					}
					değişkeniYığıttanSaklacaTaşı(cümle04.parametre);
				}
				String saklaç = saklaçtakiDeğişkenler.get(cümle04.parametre).saklaçAdresi;
				işlemler.add(new İşlem_03MakineDiliKomutu("\n\t# " + cümle + "\n"));
				işlemler.add(new İşlem_03MakineDiliKomutu("\tmov rax," + saklaç + "\n"));
				işlemler.add(
						new İşlem_03MakineDiliKomutu("\tcall fn" + fonksiyonIdHazırla(cümle04.fonksiyonId) + "\n"));
			} else if (cümle instanceof Cümle_11SabitAtama) {
				Cümle_11SabitAtama cümle05 = (Cümle_11SabitAtama) cümle;
				if (!saklaçtakiDeğişkenler.containsKey(cümle05.değişkenNo)) {
					if (saklaçStack.isEmpty()) {
						saklaçtaYerAç(çıktı, i);
					}
					değişkeniYığıttanSaklacaTaşı(cümle05.değişkenNo);
				}
				String saklaç = saklaçtakiDeğişkenler.get(cümle05.değişkenNo).saklaçAdresi;
				işlemler.add(new İşlem_03MakineDiliKomutu("\n\t# " + cümle + "\n"));
				if (cümle05.sabitVeriTipiId == Değişkenler.ID_i64) {
					işlemler.add(new İşlem_03MakineDiliKomutu("\tmov " + saklaç + "," + cümle05.sabitVeri + "\n"));
				} else if (cümle05.sabitVeriTipiId == Değişkenler.ID_str) {
					işlemler.add(new İşlem_03MakineDiliKomutu(
							"\tlea " + saklaç + "," + ".str_" + cümle05.değişkenNo + "\n"));
				}
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
				işlemler.add(new İşlem_03MakineDiliKomutu("\n\t# " + cümle + "\n"));
				işlemler.add(new İşlem_03MakineDiliKomutu("\tmov " + saklaçHedef + "," + saklaçKaynak + "\n"));
			} else {
				işlemler.add(new İşlem_03MakineDiliKomutu("\n\t# " + cümle + "\n"));
				işlemler.add(new İşlem_03MakineDiliKomutu("-BİLİNMEYEN-(" + cümle + ")-\n"));
			}
			//
			// System.out.println(cümle);
		}

		int fonksiyonIdMemAllocInit = çıktı.isimFonksiyonMap.get("mem_alloc_init 0").fonksiyonId;

		sb.append("\tpush rbp\n");
		sb.append("\tmov rbp, rsp\n");
		sb.append("\tsub rsp, " + (yığıtAdres * 8) + "\n");
		sb.append("\tcall fn" + fonksiyonIdHazırla(fonksiyonIdMemAllocInit) + "\n");
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
			} else if (işlem instanceof İşlem_03MakineDiliKomutu) {
				İşlem_03MakineDiliKomutu işlem03 = (İşlem_03MakineDiliKomutu) işlem;
				sb.append(işlem03.komut);
			}
		}
		sb.append("\tleave\n");

		int fonksiyonIdMemAllocDestroy = çıktı.isimFonksiyonMap.get("mem_alloc_destroy 0").fonksiyonId;
		int fonksiyonIdExit = çıktı.isimFonksiyonMap.get("exit 0").fonksiyonId;

		sb.append("\n");
		sb.append("\tcall fn" + fonksiyonIdHazırla(fonksiyonIdMemAllocDestroy) + "\n");
		sb.append("\tcall fn" + fonksiyonIdHazırla(fonksiyonIdExit) + "\n");
		sb.append("\tret\n");

		Fonksiyonlar.dosyaKaydet(dosyaAdı + ".5.s", sb.toString());
	}

}