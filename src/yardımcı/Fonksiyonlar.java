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

	public static KomutÇıktısı komutÇalıştır(boolean detaylariGöster, String[] parametreler) {
		if (detaylariGöster) {
			System.out.println("Fonksiyonlar : komutÇalıştır() : " + Arrays.toString(parametreler));
		}
		Process process = null;
		BufferedReader çıktılar = null;
		BufferedReader hatalar = null;
		try {
			long zaman1 = System.currentTimeMillis();
			String birSatırBilgi = null;
			Runtime runtime = Runtime.getRuntime();
			process = runtime.exec(parametreler);
			çıktılar = new BufferedReader(new InputStreamReader(process.getInputStream()));
			hatalar = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			KomutÇıktısı komutÇıktısı = new KomutÇıktısı();
			while ((birSatırBilgi = çıktılar.readLine()) != null) {
				komutÇıktısı.çıktı.add(birSatırBilgi);
				if (detaylariGöster) {
					System.out.println("Fonksiyonlar : komutÇalıştır() : " + birSatırBilgi);
				}
			}
			while ((birSatırBilgi = hatalar.readLine()) != null) {
				komutÇıktısı.hata.add(birSatırBilgi);
				if (detaylariGöster) {
					System.out.println("Fonksiyonlar : komutÇalıştır() : " + birSatırBilgi);
				}
			}
			int sonlanmaKodu = process.waitFor();
			long zaman2 = System.currentTimeMillis();
			if (detaylariGöster) {
				System.out.println("Fonksiyonlar : komutÇalıştır() : SONLANMA KODU : " + sonlanmaKodu + " (" + (zaman2 - zaman1) + " ms sürdü)");
			}
			return komutÇıktısı;
		} catch (Exception ex) {
			if (detaylariGöster) {
				System.out.println("Fonksiyonlar : komutÇalıştır() : " + Arrays.toString(parametreler) + " " + ex.toString());
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
