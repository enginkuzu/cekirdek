package başlangıç;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import aşama1oku.Aşama1Oku;
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

public class Başlangıç {

	public Başlangıç() {
		File testKlasörü = new File("testler");
		ArrayList<String> kodDosyaları = new ArrayList<String>();
		HashSet<String> tümDosyalar = new HashSet<String>();
		for (String dosyaİsmi : testKlasörü.list()) {
			if (dosyaİsmi.endsWith(".kod")) {
				kodDosyaları.add(dosyaİsmi);
			}
			tümDosyalar.add(dosyaİsmi);
		}
		int i = 0;
		int başarılı = 0;
		int başarısız = 0;
		Collections.sort(kodDosyaları);
		for (String dosya : kodDosyaları) {
			System.out.print("TEST : (" + ++i + "/" + kodDosyaları.size() + ") " + dosya + " ");
			String dosyaİsmi = dosya.substring(0, dosya.length() - 4);
			boolean başarılıMı = true;
			String hatalar = derle("testler/" + dosya);
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
				String normalÇıktıDosyası = dosya + ".out";
				if (tümDosyalar.contains(normalÇıktıDosyası)) {
					KomutÇıktısı çıktı = Fonksiyonlar.komutÇalıştır(false, new String[] { "testler/" + dosyaİsmi });
					String normalÇıktıDosyasıİçeriği = Fonksiyonlar.dosyaOku(new File("testler/" + normalÇıktıDosyası));
					if (!normalÇıktıDosyasıİçeriği.equals(çıktı.normalÇıktı)) {
						başarılıMı = false;
					}
				}
			}
			if (başarılıMı) {
				başarılı++;
			} else {
				başarısız++;
			}
			System.out.println(başarılıMı ? "+" : "-");
		}
		System.out.println("TESTLER : " + başarılı + " başarılı, " + başarısız + " başarısız");
	}

	public Başlangıç(String file) {
		String hatalar = derle(file);
		if (hatalar != null) {
			System.out.println(hatalar);
		}
	}

	public String derle(String kaynakKodDosyası) {

		String kütüphaneDosyası1 = "kütüphane/exit.kod";
		String kütüphane1 = Aşama1Oku.oku(kütüphaneDosyası1);
		if (kütüphane1 == null) {
			return "Aşama1Oku : " + kütüphaneDosyası1 + " okunamadı !!!";
		}

		String kütüphaneDosyası2 = "kütüphane/i64.kod";
		String kütüphane2 = Aşama1Oku.oku(kütüphaneDosyası2);
		if (kütüphane2 == null) {
			return "Aşama1Oku : " + kütüphaneDosyası2 + " okunamadı !!!";
		}

		String kütüphaneDosyası3 = "kütüphane/printhn.kod";
		String kütüphane3 = Aşama1Oku.oku(kütüphaneDosyası3);
		if (kütüphane3 == null) {
			return "Aşama1Oku : " + kütüphaneDosyası3 + " okunamadı !!!";
		}

		String kaynakKod = Aşama1Oku.oku(kaynakKodDosyası);
		if (kaynakKod == null) {
			return "Aşama1Oku : " + kaynakKodDosyası + " okunamadı !!!";
		}

		Object sözcükler = Aşama2Sözcükler.işle(kütüphane1 + "\n" + kütüphane2 + "\n" + kütüphane3 + "\n" + kaynakKod,
				kaynakKodDosyası);
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
		if (Değişkenler.IS_TURKISH) {
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
		if (Değişkenler.IS_TURKISH) {
			System.out.println("ölümcül hata: Hiçbir şey istenmemiş.");
			System.out.println("detaylar için --help parametresini kullanınız.");
		} else {
			System.out.println("fatal error: Nothing requested.");
			System.out.println("use --help for details.");
		}
	}

	private static void hatalıParametreler() {
		if (Değişkenler.IS_TURKISH) {
			System.out.println("ölümcül hata: Hatalı parametreler.");
			System.out.println("detaylar için --help parametresini kullanınız.");
		} else {
			System.out.println("fatal error: Bad parameters.");
			System.out.println("use --help for details.");
		}
	}

	private static void sürümGörüntüle() {
		System.out.println(Değişkenler.UYGULAMA_ADI_VE_SURUMU);
	}

	public static void main(String[] args) {

		if (args.length > 1) {
			String arg1 = args[0];
			String arg2 = args[1];
			if (arg1.equals("--lang")) {
				Değişkenler.IS_TURKISH = arg2.equals("tr");
			} else {
				hatalıParametreler();
			}
		} else if (args.length == 1) {
			String arg1 = args[0];
			if (arg1.equals("--help")) {
				çalışmaParametreleriniEkranaYaz();
			} else if (arg1.equals("--version")) {
				sürümGörüntüle();
			} else if (arg1.equals("--tests")) {
				Değişkenler.IS_TURKISH = true;
				new Başlangıç();
			} else {
				new Başlangıç(arg1);
			}
		} else {
			hiçParametreYok();
		}

	}

}
