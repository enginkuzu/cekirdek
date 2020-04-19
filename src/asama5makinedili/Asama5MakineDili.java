package asama5makinedili;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;

import asama3cumleler.Asama3Ciktisi;
import asama3cumleler.Cumle;
import asama3cumleler.Cumle_01DegiskenYeni;
import asama3cumleler.Cumle_02GeciciDegiskenYeni;
import asama3cumleler.Cumle_03DegiskenSil;
import asama3cumleler.Cumle_04OperatorIslemi;
import asama3cumleler.Cumle_05FonksiyonCagrisi;
import asama3cumleler.Cumle_11SabitAtama;
import asama3cumleler.Cumle_06DegiskenAtama;
import asama3cumleler.Cumle_07MakineDiliKod;
import asama3cumleler.Cumle_08MakineDiliVeri;
import asama3cumleler.Cumle_09MakineDiliSabitVeri;
import asama3cumleler.Cumle_10MakineDiliSembol;
import asama3cumleler.Fonksiyon_01OperatorFonksiyon;
import asama3cumleler.Fonksiyon_02IsimliFonksiyon;
import yardimci.Degiskenler;
import yardimci.Fonksiyonlar;

public class Asama5MakineDili {

	private static Stack<String> saklaçStack;
	private static int yığıtAdres;
	private static HashMap<Integer, DegiskenDetaySaklac> saklaçtakiDegiskenler;
	private static HashMap<Integer, DegiskenDetayYigit> yığıttakiDegiskenler;
	private static ArrayList<Islem> işlemler;

	private static int saklaçİlkKullanımİndeksiniBul(Asama3Ciktisi çıktı, int aktifİndeks, int değişkenNo) {

		for (int i = aktifİndeks; i < çıktı.anaFonksiyon.cümleler.size(); i++) {
			Cumle cümle = çıktı.anaFonksiyon.cümleler.get(i);
			//
			if (cümle instanceof Cumle_03DegiskenSil) {
				Cumle_03DegiskenSil cümle02 = (Cumle_03DegiskenSil) cümle;
				if (cümle02.değişkenNo == değişkenNo) {
					return i;
				}
			} else if (cümle instanceof Cumle_04OperatorIslemi) {
				Cumle_04OperatorIslemi cümle03 = (Cumle_04OperatorIslemi) cümle;
				if (cümle03.değişkenNo == değişkenNo) {
					return i;
				}
				if (cümle03.parametreNo1 == değişkenNo) {
					return i;
				}
				if (cümle03.parametreNo2 == değişkenNo) {
					return i;
				}
			} else if (cümle instanceof Cumle_05FonksiyonCagrisi) {
				Cumle_05FonksiyonCagrisi cümle04 = (Cumle_05FonksiyonCagrisi) cümle;
				if (cümle04.parametre == değişkenNo) {
					return i;
				}
			} else if (cümle instanceof Cumle_11SabitAtama) {
				Cumle_11SabitAtama cümle05 = (Cumle_11SabitAtama) cümle;
				if (cümle05.değişkenNo == değişkenNo) {
					return i;
				}
			} else if (cümle instanceof Cumle_06DegiskenAtama) {
				Cumle_06DegiskenAtama cümle06 = (Cumle_06DegiskenAtama) cümle;
				if (cümle06.değişkenNoHedef == değişkenNo) {
					return i;
				}
				if (cümle06.değişkenNoKaynak == değişkenNo) {
					return i;
				}
			}
		}

		işlemler.add(
				new Islem_03MakineDiliKomutu("-BİLİNMEYEN-(saklaçİlkKullanımİndeksiniBul() fonksiyonu -1 döndü)-\n"));
		return -1;
	}

	private static void saklaçtaYerAç(Asama3Ciktisi çıktı, int aktifİndeks) {

		for (Iterator<DegiskenDetaySaklac> it = saklaçtakiDegiskenler.values().iterator(); it.hasNext();) {
			DegiskenDetaySaklac detay = it.next();
			if (detay.ilkKullanımİndeksi < aktifİndeks) {
				detay.ilkKullanımİndeksi = saklaçİlkKullanımİndeksiniBul(çıktı, aktifİndeks, detay.değişkenNo);
			}
		}

		Iterator<DegiskenDetaySaklac> it = saklaçtakiDegiskenler.values().iterator();
		DegiskenDetaySaklac seçilenDegisken = it.next();
		while (it.hasNext()) {
			DegiskenDetaySaklac detay = it.next();
			if (detay.ilkKullanımİndeksi > seçilenDegisken.ilkKullanımİndeksi) {
				seçilenDegisken = detay;
			}
		}

		// System.out.println(saklaçtakiDegiskenler);
		saklaçStack.push(seçilenDegisken.saklaçAdresi);
		saklaçtakiDegiskenler.remove(seçilenDegisken.değişkenNo);
		yığıtAdres++;
		işlemler.add(new Islem_02SaklactanYigita(seçilenDegisken.saklaçAdresi, yığıtAdres));
		DegiskenDetayYigit detay = new DegiskenDetayYigit(seçilenDegisken.değişkenNo, yığıtAdres,
				seçilenDegisken.ilkKullanımİndeksi);
		yığıttakiDegiskenler.put(seçilenDegisken.değişkenNo, detay);
		// System.out.println("Yığıta > " + seçilenDegisken.değişkenNo);
	}

	private static void değişkeniYığıttanSaklacaTaşı(int değişkenNo) {
		DegiskenDetayYigit detay = yığıttakiDegiskenler.remove(değişkenNo);
		String saklaç = saklaçStack.pop();
		işlemler.add(new Islem_01YigittanSaklaca(detay.yığıtAdresi, saklaç));
		saklaçtakiDegiskenler.put(değişkenNo, new DegiskenDetaySaklac(değişkenNo, saklaç, detay.ilkKullanımİndeksi));
		// System.out.println("Saklaca < " + değişkenNo);
	}

	private static String fonksiyonIdHazırla(int fonksiyonId) {
		return fonksiyonId < 0 ? "_" + (-fonksiyonId) : "" + fonksiyonId;
	}

	public static void işle(Asama3Ciktisi çıktı, String dosyaAdı) {

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
		saklaçtakiDegiskenler = new HashMap<Integer, DegiskenDetaySaklac>();
		yığıttakiDegiskenler = new HashMap<Integer, DegiskenDetayYigit>();
		işlemler = new ArrayList<Islem>();

		StringBuilder sb = new StringBuilder();
		sb.append("\t.intel_syntax noprefix\n");
		sb.append("\n");

		sb.append("\t.section\t.data\n\n");
		for (Fonksiyon_02IsimliFonksiyon isimFonksiyon : çıktı.isimFonksiyonMap.values()) {
			for (Cumle cümle : isimFonksiyon.cümleler) {
				if (cümle instanceof Cumle_08MakineDiliVeri) {
					String veri = ((Cumle_08MakineDiliVeri) cümle).veri;
					sb.append("\t" + veri + "\n");
				}
			}
		}
		sb.append("\n");

		sb.append("\t.section\t.rodata\n\n");
		for (Fonksiyon_02IsimliFonksiyon isimFonksiyon : çıktı.isimFonksiyonMap.values()) {
			for (Cumle cümle : isimFonksiyon.cümleler) {
				if (cümle instanceof Cumle_09MakineDiliSabitVeri) {
					String sabitVeri = ((Cumle_09MakineDiliSabitVeri) cümle).sabitVeri;
					sb.append("\t" + sabitVeri + "\n");
				}
			}
		}
		for (int i = 0; i < çıktı.anaFonksiyon.cümleler.size(); i++) {
			Cumle cümle = çıktı.anaFonksiyon.cümleler.get(i);
			if (cümle instanceof Cumle_11SabitAtama) {
				Cumle_11SabitAtama cümle11 = (Cumle_11SabitAtama) cümle;
				if (cümle11.sabitVeriTipiId == Degiskenler.ID_str) {
					sb.append("\t.str_" + cümle11.değişkenNo + ":\n");
					sb.append("\t.byte " + cümle11.sabitVeri.getBytes().length + "\n");
					sb.append("\t.string \"" + cümle11.sabitVeri + "\"\n");
				}
			}
		}
		sb.append("\n");

		sb.append("\t.section\t.bss\n\n");
		for (Fonksiyon_02IsimliFonksiyon isimFonksiyon : çıktı.isimFonksiyonMap.values()) {
			for (Cumle cümle : isimFonksiyon.cümleler) {
				if (cümle instanceof Cumle_10MakineDiliSembol) {
					String sembol = ((Cumle_10MakineDiliSembol) cümle).sembol;
					sb.append("\t" + sembol + "\n");
				}
			}
		}
		sb.append("\n");

		sb.append("\t.section\t.text\n");

		for (Fonksiyon_02IsimliFonksiyon isimFonksiyon : çıktı.isimFonksiyonMap.values()) {
			String fonksiyonId = fonksiyonIdHazırla(isimFonksiyon.fonksiyonId);
			sb.append("\n\t.globl\tfn" + fonksiyonId + "\n");
			sb.append("fn" + fonksiyonId + ":\n");
			for (Cumle cümle : isimFonksiyon.cümleler) {
				if (cümle instanceof Cumle_07MakineDiliKod) {
					String kod = ((Cumle_07MakineDiliKod) cümle).kod;
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
			Cumle cümle = çıktı.anaFonksiyon.cümleler.get(i);
			//
			if (cümle instanceof Cumle_01DegiskenYeni) {
				Cumle_01DegiskenYeni cümle01 = (Cumle_01DegiskenYeni) cümle;
				if (saklaçStack.isEmpty()) {
					saklaçtaYerAç(çıktı, i);
				}
				saklaçtakiDegiskenler.put(cümle01.değişkenNo,
						new DegiskenDetaySaklac(cümle01.değişkenNo, saklaçStack.pop()));
				işlemler.add(new Islem_03MakineDiliKomutu("\n\t# " + cümle + "\n"));
			} else if (cümle instanceof Cumle_02GeciciDegiskenYeni) {
				Cumle_02GeciciDegiskenYeni cümle02 = (Cumle_02GeciciDegiskenYeni) cümle;
				if (saklaçStack.isEmpty()) {
					saklaçtaYerAç(çıktı, i);
				}
				saklaçtakiDegiskenler.put(cümle02.değişkenNo,
						new DegiskenDetaySaklac(cümle02.değişkenNo, saklaçStack.pop()));
				işlemler.add(new Islem_03MakineDiliKomutu("\n\t# " + cümle + "\n"));
			} else if (cümle instanceof Cumle_03DegiskenSil) {
				Cumle_03DegiskenSil cümle02 = (Cumle_03DegiskenSil) cümle;
				işlemler.add(new Islem_03MakineDiliKomutu("\n\t# " + cümle + "\n"));
				if (saklaçtakiDegiskenler.containsKey(cümle02.değişkenNo)) {
					saklaçStack.push(saklaçtakiDegiskenler.remove(cümle02.değişkenNo).saklaçAdresi);
				} else {
					işlemler.add(new Islem_03MakineDiliKomutu("-BİLİNMEYEN-(değişken yığıttan silinecek)-\n"));
				}
			} else if (cümle instanceof Cumle_04OperatorIslemi) {
				Cumle_04OperatorIslemi cümle03 = (Cumle_04OperatorIslemi) cümle;
				String anahtar = null;
				if (cümle03.parametreNo1 == 0) {
					anahtar = Degiskenler.ID_none + cümle03.operatör
							+ çıktı.anaFonksiyon.değişkenNoMap.get(cümle03.parametreNo2).değişkenTipiId;
				} else {
					anahtar = çıktı.anaFonksiyon.değişkenNoMap.get(cümle03.parametreNo1).değişkenTipiId
							+ cümle03.operatör
							+ çıktı.anaFonksiyon.değişkenNoMap.get(cümle03.parametreNo2).değişkenTipiId;
					if (!saklaçtakiDegiskenler.containsKey(cümle03.parametreNo1)) {
						if (saklaçStack.isEmpty()) {
							saklaçtaYerAç(çıktı, i);
						}
						değişkeniYığıttanSaklacaTaşı(cümle03.parametreNo1);
					}
				}
				Fonksiyon_01OperatorFonksiyon operatörFonksiyon = çıktı.operatörFonksiyonMap.get(anahtar);
				if (!saklaçtakiDegiskenler.containsKey(cümle03.parametreNo2)) {
					if (saklaçStack.isEmpty()) {
						saklaçtaYerAç(çıktı, i);
					}
					değişkeniYığıttanSaklacaTaşı(cümle03.parametreNo2);
				}
				if (!saklaçtakiDegiskenler.containsKey(cümle03.değişkenNo)) {
					if (saklaçStack.isEmpty()) {
						saklaçtaYerAç(çıktı, i);
					}
					değişkeniYığıttanSaklacaTaşı(cümle03.değişkenNo);
				}
				String kod = ((Cumle_07MakineDiliKod) operatörFonksiyon.cümleler.get(0)).kod;
				if (cümle03.parametreNo1 != 0) {
					kod = kod.replace(operatörFonksiyon.değişken1İsim,
							saklaçtakiDegiskenler.get(cümle03.parametreNo1).saklaçAdresi);
				}
				kod = kod.replace(operatörFonksiyon.değişken2İsim,
						saklaçtakiDegiskenler.get(cümle03.parametreNo2).saklaçAdresi);
				kod = kod.replace(operatörFonksiyon.sonuçİsim,
						saklaçtakiDegiskenler.get(cümle03.değişkenNo).saklaçAdresi);
				işlemler.add(new Islem_03MakineDiliKomutu("\n\t# " + cümle + "\n"));
				işlemler.add(new Islem_03MakineDiliKomutu("\t" + kod + "\n"));
			} else if (cümle instanceof Cumle_05FonksiyonCagrisi) {
				Cumle_05FonksiyonCagrisi cümle04 = (Cumle_05FonksiyonCagrisi) cümle;
				if (!saklaçtakiDegiskenler.containsKey(cümle04.parametre)) {
					if (saklaçStack.isEmpty()) {
						saklaçtaYerAç(çıktı, i);
					}
					değişkeniYığıttanSaklacaTaşı(cümle04.parametre);
				}
				String saklaç = saklaçtakiDegiskenler.get(cümle04.parametre).saklaçAdresi;
				işlemler.add(new Islem_03MakineDiliKomutu("\n\t# " + cümle + "\n"));
				işlemler.add(new Islem_03MakineDiliKomutu("\tmov rax, " + saklaç + "\n"));
				işlemler.add(
						new Islem_03MakineDiliKomutu("\tcall fn" + fonksiyonIdHazırla(cümle04.fonksiyonId) + "\n"));
			} else if (cümle instanceof Cumle_11SabitAtama) {
				Cumle_11SabitAtama cümle05 = (Cumle_11SabitAtama) cümle;
				if (!saklaçtakiDegiskenler.containsKey(cümle05.değişkenNo)) {
					if (saklaçStack.isEmpty()) {
						saklaçtaYerAç(çıktı, i);
					}
					değişkeniYığıttanSaklacaTaşı(cümle05.değişkenNo);
				}
				String saklaç = saklaçtakiDegiskenler.get(cümle05.değişkenNo).saklaçAdresi;
				işlemler.add(new Islem_03MakineDiliKomutu("\n\t# " + cümle + "\n"));
				if (cümle05.sabitVeriTipiId == Degiskenler.ID_i64) {
					işlemler.add(new Islem_03MakineDiliKomutu("\tmov " + saklaç + ", " + cümle05.sabitVeri + "\n"));
				} else if (cümle05.sabitVeriTipiId == Degiskenler.ID_str) {
					işlemler.add(new Islem_03MakineDiliKomutu(
							"\tlea " + saklaç + ", " + ".str_" + cümle05.değişkenNo + "\n"));
				}
			} else if (cümle instanceof Cumle_06DegiskenAtama) {
				Cumle_06DegiskenAtama cümle06 = (Cumle_06DegiskenAtama) cümle;
				if (!saklaçtakiDegiskenler.containsKey(cümle06.değişkenNoKaynak)) {
					if (saklaçStack.isEmpty()) {
						saklaçtaYerAç(çıktı, i);
					}
					değişkeniYığıttanSaklacaTaşı(cümle06.değişkenNoKaynak);
				}
				if (!saklaçtakiDegiskenler.containsKey(cümle06.değişkenNoHedef)) {
					if (saklaçStack.isEmpty()) {
						saklaçtaYerAç(çıktı, i);
					}
					değişkeniYığıttanSaklacaTaşı(cümle06.değişkenNoHedef);
				}
				String saklaçKaynak = saklaçtakiDegiskenler.get(cümle06.değişkenNoKaynak).saklaçAdresi;
				String saklaçHedef = saklaçtakiDegiskenler.get(cümle06.değişkenNoHedef).saklaçAdresi;
				işlemler.add(new Islem_03MakineDiliKomutu("\n\t# " + cümle + "\n"));
				işlemler.add(new Islem_03MakineDiliKomutu("\tmov " + saklaçHedef + ", " + saklaçKaynak + "\n"));
			} else {
				işlemler.add(new Islem_03MakineDiliKomutu("\n\t# " + cümle + "\n"));
				işlemler.add(new Islem_03MakineDiliKomutu("-BİLİNMEYEN-(" + cümle + ")-\n"));
			}
			//
			// System.out.println(cümle);
		}

		int fonksiyonIdMemAllocInit = çıktı.isimFonksiyonMap.get("mem_alloc_init 0").fonksiyonId;

		sb.append("\tpush rbp\n");
		sb.append("\tmov rbp, rsp\n");
		sb.append("\tsub rsp, " + (yığıtAdres * 8) + "\n");
		sb.append("\tcall fn" + fonksiyonIdHazırla(fonksiyonIdMemAllocInit) + "\n");
		for (Iterator<Islem> it = işlemler.iterator(); it.hasNext();) {
			Islem işlem = it.next();
			//
			if (işlem instanceof Islem_01YigittanSaklaca) {
				Islem_01YigittanSaklaca işlem01 = (Islem_01YigittanSaklaca) işlem;
				int adres = (yığıtAdres - işlem01.yığıtİndeks + 1) * 8;
				sb.append("\n");
				sb.append("\t# Yığıt-Saklaç : " + işlem01);
				sb.append("\tmov " + işlem01.saklaç + ", QWORD PTR -" + adres + "[rbp]\n");
			} else if (işlem instanceof Islem_02SaklactanYigita) {
				Islem_02SaklactanYigita işlem02 = (Islem_02SaklactanYigita) işlem;
				int adres = (yığıtAdres - işlem02.yığıtİndeks + 1) * 8;
				sb.append("\n");
				sb.append("\t# Saklaç-Yığıt : " + işlem02);
				sb.append("\tmov QWORD PTR -" + adres + "[rbp], " + işlem02.saklaç + "\n");
			} else if (işlem instanceof Islem_03MakineDiliKomutu) {
				Islem_03MakineDiliKomutu işlem03 = (Islem_03MakineDiliKomutu) işlem;
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