package aşama5makinedili;

import java.util.HashMap;
import java.util.Stack;

import aşama3cümleler.Aşama3Çıktısı;
import aşama3cümleler.Cümle;
import aşama3cümleler.Cümle_01DeğişkenYeni;
import aşama3cümleler.Cümle_02DeğişkenSil;
import aşama3cümleler.Cümle_03Operatörİşlemi;
import aşama3cümleler.Cümle_04FonksiyonÇağrısı;
import aşama3cümleler.Cümle_05SabitAtama;
import aşama3cümleler.Cümle_06DeğişkenAtama;
import aşama3cümleler.Cümle_07Assembly;
import aşama3cümleler.Cümle_08AssemblyData;
import aşama3cümleler.Cümle_09AssemblyRoData;
import aşama3cümleler.Fonksiyon_01OperatörFonksiyon;
import aşama3cümleler.Fonksiyon_02İsimliFonksiyon;
import yardımcı.Fonksiyonlar;

public class Aşama5MakineDili {

	public static void işle(Aşama3Çıktısı çıktı, String dosyaAdı) {

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

		Stack<String> stack = new Stack<String>();
		stack.push("r10");
		stack.push("r8");
		stack.push("r9");
		stack.push("rbx");
		stack.push("r12");
		stack.push("r13");
		stack.push("r14");
		stack.push("r15");

		HashMap<Integer, String> aktifDeğişkenler = new HashMap<Integer, String>();

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

		for (Cümle cümle : çıktı.anaFonksiyon.cümleler) {
			sb.append("\n\t# " + cümle + "\n");
			if (cümle instanceof Cümle_01DeğişkenYeni) {
				Cümle_01DeğişkenYeni cümle01 = (Cümle_01DeğişkenYeni) cümle;
				aktifDeğişkenler.put(cümle01.değişkenNo, stack.pop());
			} else if (cümle instanceof Cümle_02DeğişkenSil) {
				Cümle_02DeğişkenSil cümle02 = (Cümle_02DeğişkenSil) cümle;
				stack.push(aktifDeğişkenler.remove(cümle02.değişkenNo));
			} else if (cümle instanceof Cümle_03Operatörİşlemi) {
				Cümle_03Operatörİşlemi cümle03 = (Cümle_03Operatörİşlemi) cümle;
				if (!aktifDeğişkenler.containsKey(cümle03.değişkenNo)) {
					aktifDeğişkenler.put(cümle03.değişkenNo, stack.pop());
				}
				String anahtar = çıktı.anaFonksiyon.değişkenNoMap.get(cümle03.parametreNo1).değişkenTipi
						+ cümle03.operatör + çıktı.anaFonksiyon.değişkenNoMap.get(cümle03.parametreNo2).değişkenTipi;
				Fonksiyon_01OperatörFonksiyon operatörFonksiyon = çıktı.operatörFonksiyonMap.get(anahtar);
				String assembly = ((Cümle_07Assembly) operatörFonksiyon.cümleler.get(0)).ifade;
				assembly = assembly.replace(operatörFonksiyon.değişken1İsim,
						aktifDeğişkenler.get(cümle03.parametreNo1));
				assembly = assembly.replace(operatörFonksiyon.değişken2İsim,
						aktifDeğişkenler.get(cümle03.parametreNo2));
				assembly = assembly.replace(operatörFonksiyon.sonuçİsim, aktifDeğişkenler.get(cümle03.değişkenNo));
				sb.append("\t" + assembly + "\n");
			} else if (cümle instanceof Cümle_04FonksiyonÇağrısı) {
				Cümle_04FonksiyonÇağrısı cümle04 = (Cümle_04FonksiyonÇağrısı) cümle;
				sb.append("\tmov rax," + aktifDeğişkenler.get(cümle04.parametre) + "\n");
				sb.append("\tcall printhn\n");
			} else if (cümle instanceof Cümle_05SabitAtama) {
				Cümle_05SabitAtama cümle05 = (Cümle_05SabitAtama) cümle;
				String saklaç = stack.pop();
				aktifDeğişkenler.put(cümle05.değişkenNo, saklaç);
				sb.append("\tmov " + saklaç + "," + cümle05.sabitVeri + "\n");
			} else if (cümle instanceof Cümle_06DeğişkenAtama) {
				Cümle_06DeğişkenAtama cümle06 = (Cümle_06DeğişkenAtama) cümle;
				sb.append("\tmov " + aktifDeğişkenler.get(cümle06.değişkenNoHedef) + ","
						+ aktifDeğişkenler.get(cümle06.değişkenNoKaynak) + "\n");
			} else {
				sb.append("TODO : " + cümle + "\n");
			}
		}

		sb.append("\n");
		sb.append("\tcall exit\n");
		sb.append("\tret\n");

		Fonksiyonlar.dosyaKaydet(dosyaAdı + ".5.s", sb.toString());
	}

}
