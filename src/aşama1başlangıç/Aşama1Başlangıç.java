package aşama1başlangıç;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import aşama2sözcükler.Aşama2Sözcükler;
import aşama2sözcükler.Sözcük;
import aşama3cümleler.Aşama3Cümleler;
import aşama3cümleler.Aşama3Çıktısı;
import aşama4iyileştirmeler.Aşama4İyileştirmeler;
import aşama5makinedili.Aşama5MakineDili;
import aşama6çıktı.Aşama6Çıktı;
import sınıflar.KomutÇıktısı;
import yardımcı.Değişkenler;
import yardımcı.Fonksiyonlar;

public class Aşama1Başlangıç {

	public Aşama1Başlangıç() {

		File testKlasörü = new File("testler");
		ArrayList<String> kodDosyaları = new ArrayList<String>();
		HashSet<String> tümDosyalar = new HashSet<String>();
		for (String dosyaİsmi : testKlasörü.list()) {
			if (dosyaİsmi.endsWith(".kod")) {
				kodDosyaları.add(dosyaİsmi);
			}
			tümDosyalar.add(dosyaİsmi);
		}
		Collections.sort(kodDosyaları);

		int testNo = 0;
		int başarılıSayaç = 0;
		int başarısızSayaç = 0;
		for (String dosyaİsmiVeUzantısı : kodDosyaları) {
			System.out.print("TEST : (" + ++testNo + "/" + kodDosyaları.size() + ") " + dosyaİsmiVeUzantısı + " ");
			String dosyaİsmi = dosyaİsmiVeUzantısı.substring(0, dosyaİsmiVeUzantısı.length() - 4);
			boolean başarılıMı = true;
			String hatalar = derle("testler/" + dosyaİsmiVeUzantısı);
			if (hatalar != null) {
				String hataÇıktıDosyası = dosyaİsmi + ".err";
				if (tümDosyalar.contains(hataÇıktıDosyası)) {
					String hataÇıktıDosyasıİçeriği = Fonksiyonlar.dosyaOku(new File("testler/" + hataÇıktıDosyası));
					if (!hataÇıktıDosyasıİçeriği.equals(hatalar)) {
						başarılıMı = false;
					}
				} else {
					başarılıMı = false;
				}
			} else {
				String normalÇıktıDosyası = dosyaİsmiVeUzantısı + ".out";
				if (tümDosyalar.contains(normalÇıktıDosyası)) {
					KomutÇıktısı çıktı = Fonksiyonlar.komutÇalıştır(false,
							new String[] { "testler/" + dosyaİsmi + ".bin" });
					String normalÇıktıDosyasıİçeriği = Fonksiyonlar.dosyaOku(new File("testler/" + normalÇıktıDosyası));
					if (!normalÇıktıDosyasıİçeriği.equals(çıktı.normalÇıktı)) {
						başarılıMı = false;
					}
				}
			}
			if (başarılıMı) {
				başarılıSayaç++;
			} else {
				başarısızSayaç++;
			}
			System.out.println(başarılıMı ? "+" : "-");
		}
		System.out.println("TESTLER : " + başarılıSayaç + " başarılı, " + başarısızSayaç + " başarısız");
	}

	public Aşama1Başlangıç(String file) {
		String hatalar = derle(file);
		if (hatalar != null) {
			System.out.println(hatalar);
		}
	}

	private static String oku(String dosyaYoluVeAdı) {
		File dosya = new File(dosyaYoluVeAdı);
		String içerik = Fonksiyonlar.dosyaOku(dosya);
		return içerik;
	}

	public String derle(String kaynakKodDosyası) {

		StringBuilder sb = new StringBuilder();
		String kütüphaneler[] = { "mem_alloc.kod", "exit.kod", "i64.kod", "str.kod", "printhn.kod", "printn.kod" };
		for (int i = 0; i < kütüphaneler.length; i++) {
			String kütüphaneDosyası = "kütüphane/" + kütüphaneler[i];
			String kütüphaneKaynakKodu = oku(kütüphaneDosyası);
			if (kütüphaneKaynakKodu == null) {
				return "Aşama1Başlangıç : " + kütüphaneDosyası + " okunamadı !!!";
			}
			sb.append(kütüphaneKaynakKodu);
			sb.append("\n");
		}
		String kaynakKod = oku(kaynakKodDosyası);
		if (kaynakKod == null) {
			return "Aşama1Başlangıç : " + kaynakKodDosyası + " okunamadı !!!";
		}
		sb.append(kaynakKod);

		Object sözcükler = Aşama2Sözcükler.işle(sb.toString(), kaynakKodDosyası);
		if (sözcükler instanceof String) {
			return (String) sözcükler;
		}
		Object cümleler = Aşama3Cümleler.işle((ArrayList<Sözcük[]>) sözcükler, kaynakKodDosyası);
		if (cümleler instanceof String) {
			return (String) cümleler;
		}
		Aşama3Çıktısı iyileştirmeler = Aşama4İyileştirmeler.işle((Aşama3Çıktısı) cümleler, kaynakKodDosyası);
		Aşama5MakineDili.işle(iyileştirmeler, kaynakKodDosyası);
		return Aşama6Çıktı.işle(kaynakKodDosyası);
	}

	private static void çalışmaParametreleriniEkranaYaz() {
		if (Değişkenler.DİL_TÜRKÇE_Mİ) {
			System.out.println("Kullanım: derleyici dosya");
			System.out.println("Kullanım: derleyici [parametreler]");
			System.out.println("  --help        Parametreleri görüntüle.");
			System.out.println("  --version     Derleyici sürümünü görüntüle.");
			System.out.println("  --tests       Tüm testleri çalıştır ve sonuçları görüntüle.");
			System.out.println("  --lang tr/en  Çıktılar için dil seçimi.");
		} else {
			System.out.println("Usage: derleyici file");
			System.out.println("Usage: derleyici [options]");
			System.out.println("  --help        Display parameters.");
			System.out.println("  --version     Display compiler version.");
			System.out.println("  --tests       Run all tests and display results.");
			System.out.println("  --lang tr/en  Select output language.");
		}
	}

	private static void hiçParametreYok() {
		if (Değişkenler.DİL_TÜRKÇE_Mİ) {
			System.out.println("ölümcül hata: Hiçbir şey istenmemiş.");
			System.out.println("detaylar için --help parametresini kullanınız.");
		} else {
			System.out.println("fatal error: Nothing requested.");
			System.out.println("use --help for details.");
		}
	}

	private static void hatalıParametreler() {
		if (Değişkenler.DİL_TÜRKÇE_Mİ) {
			System.out.println("ölümcül hata: Hatalı parametreler.");
			System.out.println("detaylar için --help parametresini kullanınız.");
		} else {
			System.out.println("fatal error: Bad parameters.");
			System.out.println("use --help for details.");
		}
	}

	private static void sürümGörüntüle() {
		System.out.println(Değişkenler.UYGULAMA_ADI_VE_SÜRÜMÜ);
	}

	public static void main(String[] parametreler) {

		if (parametreler.length > 1) {
			String p1 = parametreler[0];
			String p2 = parametreler[1];
			if (p1.equals("--lang")) {
				Değişkenler.DİL_TÜRKÇE_Mİ = p2.equals("tr");
			} else {
				hatalıParametreler();
			}
		} else if (parametreler.length == 1) {
			String p1 = parametreler[0];
			if (p1.equals("--help")) {
				çalışmaParametreleriniEkranaYaz();
			} else if (p1.equals("--version")) {
				sürümGörüntüle();
			} else if (p1.equals("--tests")) {
				Değişkenler.DİL_TÜRKÇE_Mİ = true;
				new Aşama1Başlangıç();
			} else {
				new Aşama1Başlangıç(p1);
			}
		} else {
			hiçParametreYok();
		}

	}

}
