package yardımcı;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import sınıflar.KomutÇıktısı;

public class Fonksiyonlar {

	/*
	 * public static Long parseLong(String str) { try { return Long.parseLong(str);
	 * } catch (NumberFormatException ex) { return null; } }
	 * 
	 * public static boolean isInteger(String letterDigit) { char[] charArray =
	 * letterDigit.toCharArray(); for (char c : charArray) { if (c < '0' || c > '9')
	 * { return false; } } return true; }
	 */

	public static String dosyaOku(File dosya) {
		try {
			byte[] içerik = Files.readAllBytes(Paths.get(dosya.getCanonicalPath()));
			return new String(içerik, Charset.forName("UTF-8"));
		} catch (IOException ex) {
			System.out.println("Fonksiyonlar : dosyaOku() : " + ex.getMessage());
		}
		return null;
	}

	public static boolean dosyaKaydet(String dosyaYolu, String içerik) {
		try {
			PrintWriter printWriter = new PrintWriter(dosyaYolu);
			printWriter.print(içerik);
			printWriter.close();
			return true;
		} catch (Exception ex) {
			System.out.println("Fonksiyonlar : dosyaKaydet() : " + ex.getMessage());
		}
		return false;
	}

	public static KomutÇıktısı komutÇalıştır(boolean detaylariEkranaBas, String[] parametreler) {
		if (detaylariEkranaBas) {
			System.out.println("Fonksiyonlar : komutÇalıştır() : " + Arrays.toString(parametreler));
		}
		Process process = null;
		BufferedReader çıktılar = null;
		BufferedReader hatalar = null;
		try {
			long zaman1 = System.currentTimeMillis();
			Runtime runtime = Runtime.getRuntime();
			process = runtime.exec(parametreler);
			çıktılar = new BufferedReader(new InputStreamReader(process.getInputStream()));
			hatalar = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			//
			int okunanByte;
			char[] tampon = new char[1024];
			KomutÇıktısı komutÇıktısı = new KomutÇıktısı();
			//
			StringBuilder normalÇıktı = new StringBuilder();
			while ((okunanByte = çıktılar.read(tampon)) != -1) {
				normalÇıktı.append(tampon, 0, okunanByte);
			}
			komutÇıktısı.normalÇıktı = normalÇıktı.toString();
			if (detaylariEkranaBas) {
				System.out.println("Fonksiyonlar : komutÇalıştır() : " + komutÇıktısı.normalÇıktı);
			}
			//
			StringBuilder hataÇıktısı = new StringBuilder();
			while ((okunanByte = hatalar.read(tampon)) != -1) {
				hataÇıktısı.append(tampon, 0, okunanByte);
			}
			komutÇıktısı.hataÇıktısı = hataÇıktısı.toString();
			if (detaylariEkranaBas) {
				System.err.println("Fonksiyonlar : komutÇalıştır() : " + komutÇıktısı.hataÇıktısı);
			}
			//
			int sonlanmaKodu = process.waitFor();
			long zaman2 = System.currentTimeMillis();
			if (detaylariEkranaBas) {
				System.out.println("Fonksiyonlar : komutÇalıştır() : SONLANMA KODU : " + sonlanmaKodu + " ("
						+ (zaman2 - zaman1) + " ms sürdü)");
			}
			return komutÇıktısı;
		} catch (Exception ex) {
			if (detaylariEkranaBas) {
				System.out.println(
						"Fonksiyonlar : komutÇalıştır() : " + Arrays.toString(parametreler) + " " + ex.toString());
			}
		} finally {
			try {
				çıktılar.close();
			} catch (Exception ex) {
			}
			try {
				hatalar.close();
			} catch (Exception ex) {
			}
			try {
				process.destroy();
			} catch (Exception ex) {
			}
		}
		return null;
	}

}
