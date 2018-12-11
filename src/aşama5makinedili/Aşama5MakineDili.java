package aşama5makinedili;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import aşama3cümleler.Cümle;
import aşama3cümleler.Cümle_01DeğişkenYeni;
import aşama3cümleler.Cümle_02DeğişkenSil;
import aşama3cümleler.Cümle_03Operatörİşlemi;
import aşama3cümleler.Cümle_04FonksiyonÇağrısı;
import aşama3cümleler.Cümle_05SabitTanımlama;
import aşama3cümleler.Cümle_06DeğişkenAtama;

public class Aşama5MakineDili {

	public static String işle(ArrayList<Cümle> cümleler) {

		StringBuilder sb = new StringBuilder();
		sb.append("\t.intel_syntax noprefix\n");
		sb.append("\t.text\n");
		sb.append("\t.globl	_start\n");
		sb.append("\t.type	_start, @function\n");
		sb.append("_start:\n");

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

		for (Cümle cümle : cümleler) {
			sb.append("\n\t#" + cümle + "\n");
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
				if (cümle03.operatör.equals("+")) {
					sb.append("\tmov " + aktifDeğişkenler.get(cümle03.değişkenNo) + ","
							+ aktifDeğişkenler.get(cümle03.parametreNo1) + "\n");
					sb.append("\tadd " + aktifDeğişkenler.get(cümle03.değişkenNo) + ","
							+ aktifDeğişkenler.get(cümle03.parametreNo2) + "\n");
				} else if (cümle03.operatör.equals("-")) {
					sb.append("\tmov " + aktifDeğişkenler.get(cümle03.değişkenNo) + ","
							+ aktifDeğişkenler.get(cümle03.parametreNo1) + "\n");
					sb.append("\tsub " + aktifDeğişkenler.get(cümle03.değişkenNo) + ","
							+ aktifDeğişkenler.get(cümle03.parametreNo2) + "\n");
				} else if (cümle03.operatör.equals("*")) {
					sb.append("\tmov rax," + aktifDeğişkenler.get(cümle03.parametreNo1) + "\n");
					sb.append("\tmul " + aktifDeğişkenler.get(cümle03.parametreNo2) + "\n");
					sb.append("\tmov " + aktifDeğişkenler.get(cümle03.değişkenNo) + ",rax\n");
				} else if (cümle03.operatör.equals("//")) {
					sb.append("\tmov rdx,0\n");
					sb.append("\tmov rax," + aktifDeğişkenler.get(cümle03.parametreNo1) + "\n");
					sb.append("\tdiv " + aktifDeğişkenler.get(cümle03.parametreNo2) + "\n");
					sb.append("\tmov " + aktifDeğişkenler.get(cümle03.değişkenNo) + ",rax\n");
				} else {
					sb.append("TODO : " + cümle + "\n");
				}
			} else if (cümle instanceof Cümle_04FonksiyonÇağrısı) {
				Cümle_04FonksiyonÇağrısı cümle04 = (Cümle_04FonksiyonÇağrısı) cümle;
				sb.append("\tmov rax," + aktifDeğişkenler.get(cümle04.parametre) + "\n");
				sb.append("\tcall println\n");
			} else if (cümle instanceof Cümle_05SabitTanımlama) {
				Cümle_05SabitTanımlama cümle05 = (Cümle_05SabitTanımlama) cümle;
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

		return sb.toString();
	}

}
