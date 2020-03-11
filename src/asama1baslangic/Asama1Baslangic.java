package asama1baslangic;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import asama2sozcukler.Asama2Sozcukler;
import asama2sozcukler.Sozcuk;
import asama3cumleler.Asama3Cumleler;
import asama3cumleler.Asama3Ciktisi;
import asama4iyilestirmeler.Asama4Iyilestirmeler;
import asama5makinedili.Asama5MakineDili;
import asama6cikti.Asama6Cikti;
import siniflar.KomutCiktisi;
import yardimci.Degiskenler;
import yardimci.Fonksiyonlar;

public class Asama1Baslangic {

	public Asama1Baslangic() {

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
					KomutCiktisi çıktı = Fonksiyonlar.komutÇalıştır(false,
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
			System.out.println("TEST " + ++testNo + "/" + kodDosyaları.size() + " : " + (başarılıMı ? "+" : "-") + " "
					+ dosyaİsmiVeUzantısı + " ");
		}
		System.out.println("TESTLER : " + başarılıSayaç + " başarılı, " + başarısızSayaç + " başarısız");
	}

	public Asama1Baslangic(String file) {
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
		String kütüphaneler[] = { "mem.kod", "exit.kod", "i64.kod", "str.kod", "printhn.kod", "printn.kod" };
		for (int i = 0; i < kütüphaneler.length; i++) {
			String kütüphaneDosyası = "kutuphane/" + kütüphaneler[i];
			String kütüphaneKaynakKodu = oku(kütüphaneDosyası);
			if (kütüphaneKaynakKodu == null) {
				return "Asama1Baslangic : " + kütüphaneDosyası + " okunamadı !!!";
			}
			sb.append(kütüphaneKaynakKodu);
			sb.append("\n");
		}
		String kaynakKod = oku(kaynakKodDosyası);
		if (kaynakKod == null) {
			return "Asama1Baslangic : " + kaynakKodDosyası + " okunamadı !!!";
		}
		sb.append(kaynakKod);

		Object sözcükler = Asama2Sozcukler.işle(sb.toString(), kaynakKodDosyası);
		if (sözcükler instanceof String) {
			return (String) sözcükler;
		}
		Object cümleler = Asama3Cumleler.işle((ArrayList<Sozcuk[]>) sözcükler, kaynakKodDosyası);
		if (cümleler instanceof String) {
			return (String) cümleler;
		}
		Asama3Ciktisi iyileştirmeler = Asama4Iyilestirmeler.işle((Asama3Ciktisi) cümleler, kaynakKodDosyası);
		Asama5MakineDili.işle(iyileştirmeler, kaynakKodDosyası);
		return Asama6Cikti.işle(kaynakKodDosyası);
	}

	private static void çalışmaParametreleriniEkranaYaz() {
		if (Degiskenler.DİL_TÜRKÇE_Mİ) {
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
		if (Degiskenler.DİL_TÜRKÇE_Mİ) {
			System.out.println("ölümcül hata: Hiçbir şey istenmemiş.");
			System.out.println("detaylar için --help parametresini kullanınız.");
		} else {
			System.out.println("fatal error: Nothing requested.");
			System.out.println("use --help for details.");
		}
	}

	private static void hatalıParametreler() {
		if (Degiskenler.DİL_TÜRKÇE_Mİ) {
			System.out.println("ölümcül hata: Hatalı parametreler.");
			System.out.println("detaylar için --help parametresini kullanınız.");
		} else {
			System.out.println("fatal error: Bad parameters.");
			System.out.println("use --help for details.");
		}
	}

	private static void sürümGörüntüle() {
		System.out.println(Degiskenler.UYGULAMA_ADI_VE_SÜRÜMÜ);
	}

	public static void main(String[] parametreler) {

		if (parametreler.length > 1) {
			String p1 = parametreler[0];
			String p2 = parametreler[1];
			if (p1.equals("--lang")) {
				Degiskenler.DİL_TÜRKÇE_Mİ = p2.equals("tr");
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
				Degiskenler.DİL_TÜRKÇE_Mİ = true;
				new Asama1Baslangic();
			} else {
				new Asama1Baslangic(p1);
			}
		} else {
			hiçParametreYok();
		}

	}

}
