package test2sözcükler;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import aşama2sözcükler.Aşama2Sözcükler;
import aşama2sözcükler.Sözcük;
import aşama2sözcükler.Sözcük_01İsim;
import aşama2sözcükler.Sözcük_02Operatör;
import aşama2sözcükler.Sözcük_03TamSayı;
import aşama2sözcükler.Sözcük_04OndalıklıSayı;
import aşama2sözcükler.Sözcük_05Metin;
import yardımcı.Değişkenler.SÖZCÜK;

public class BirimTestleri {
	
	@Test
	public void test01Açıklama01() throws Exception {
		// sayı < 1; '
		// sayı < 2;
		assertArrayEquals(
				Aşama2Sözcükler.işle("sayı<1;'\nsayı<2;", null).toArray(),
				new Sözcük[][] {
					new Sözcük[] {new Sözcük_01İsim("sayı"), new Sözcük(SÖZCÜK.TİP_09ATAMA_SOLA), new Sözcük_03TamSayı("1"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)},
					new Sözcük[] {new Sözcük_01İsim("sayı"), new Sözcük(SÖZCÜK.TİP_09ATAMA_SOLA), new Sözcük_03TamSayı("2"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)}
				});
	}
	
	@Test
	public void test01Açıklama02() throws Exception {
		// sayı < 1; 'Açıklama satırı
		// sayı < 2;
		assertArrayEquals(
				Aşama2Sözcükler.işle("sayı<1;'Açıklama satırı\nsayı<2;", null).toArray(),
				new Sözcük[][] {
					new Sözcük[] {new Sözcük_01İsim("sayı"), new Sözcük(SÖZCÜK.TİP_09ATAMA_SOLA), new Sözcük_03TamSayı("1"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)},
					new Sözcük[] {new Sözcük_01İsim("sayı"), new Sözcük(SÖZCÜK.TİP_09ATAMA_SOLA), new Sözcük_03TamSayı("2"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)}
				});
	}
	
	@Test
	public void test01Açıklama03() throws Exception {
		// sayı < 1;
		// 'Açıklama satırı
		// sayı < 2;
		assertArrayEquals(
				Aşama2Sözcükler.işle("sayı<1;\n'Açıklama satırı\nsayı<2;", null).toArray(),
				new Sözcük[][] {
						new Sözcük[] {new Sözcük_01İsim("sayı"), new Sözcük(SÖZCÜK.TİP_09ATAMA_SOLA), new Sözcük_03TamSayı("1"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)},
						new Sözcük[] {new Sözcük_01İsim("sayı"), new Sözcük(SÖZCÜK.TİP_09ATAMA_SOLA), new Sözcük_03TamSayı("2"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)}
				});
	}
	
	@Test
	public void test01Açıklama04() throws Exception {
		// sayı < 1; '' Açıklama '' sayı<2;
		assertArrayEquals(
				Aşama2Sözcükler.işle("sayı<1;'' Açıklama ''sayı<2;", null).toArray(),
				new Sözcük[][] {
					new Sözcük[] {new Sözcük_01İsim("sayı"), new Sözcük(SÖZCÜK.TİP_09ATAMA_SOLA), new Sözcük_03TamSayı("1"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)},
					new Sözcük[] {new Sözcük_01İsim("sayı"), new Sözcük(SÖZCÜK.TİP_09ATAMA_SOLA), new Sözcük_03TamSayı("2"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)}
				});
	}

	@Test
	public void test01Açıklama05() throws Exception {
		// sayı < 1; '' Açıklama
		// satırı '' sayı < 2;
		assertArrayEquals(
				Aşama2Sözcükler.işle("sayı<1;'' Açıklama\nsatırı ''sayı<2;", null).toArray(),
				new Sözcük[][] {
					new Sözcük[] {new Sözcük_01İsim("sayı"), new Sözcük(SÖZCÜK.TİP_09ATAMA_SOLA), new Sözcük_03TamSayı("1"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)},
					new Sözcük[] {new Sözcük_01İsim("sayı"), new Sözcük(SÖZCÜK.TİP_09ATAMA_SOLA), new Sözcük_03TamSayı("2"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)}
				});
	}
	
	@Test
	public void test01Açıklama06() throws Exception {
		// sayı < 1;
		// '' Açıklama
		// satırı ''
		// sayı < 2;
		assertArrayEquals(
				Aşama2Sözcükler.işle("sayı<1;\n'' Açıklama\nsatırı ''\nsayı<2;", null).toArray(),
				new Sözcük[][] {
					new Sözcük[] {new Sözcük_01İsim("sayı"), new Sözcük(SÖZCÜK.TİP_09ATAMA_SOLA), new Sözcük_03TamSayı("1"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)},
					new Sözcük[] {new Sözcük_01İsim("sayı"), new Sözcük(SÖZCÜK.TİP_09ATAMA_SOLA), new Sözcük_03TamSayı("2"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)}
				});
	}
	
	@Test
	public void test01Açıklama07() throws Exception {
		// sayı < 1;
		// '' Açıklama ' satırı ''
		// sayı < 2;
		assertArrayEquals(
				Aşama2Sözcükler.işle("sayı<1;\n'' Açıklama ' satırı ''\nsayı<2;", null).toArray(),
				new Sözcük[][] {
					new Sözcük[] {new Sözcük_01İsim("sayı"), new Sözcük(SÖZCÜK.TİP_09ATAMA_SOLA), new Sözcük_03TamSayı("1"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)},
					new Sözcük[] {new Sözcük_01İsim("sayı"), new Sözcük(SÖZCÜK.TİP_09ATAMA_SOLA), new Sözcük_03TamSayı("2"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)}
				});
	}
	
	@Test
	public void test02Atama01() throws Exception {
		// 123 > sayı;
		assertArrayEquals(
				Aşama2Sözcükler.işle("123>sayı;", null).toArray(),
				new Sözcük[][] {
					new Sözcük[] {new Sözcük_03TamSayı("123"), new Sözcük(SÖZCÜK.TİP_10ATAMA_SAĞA), new Sözcük_01İsim("sayı"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)}
				});
	}
	
	@Test
	public void test02Atama02() throws Exception {
		// sayı < 123;
		assertArrayEquals(
				Aşama2Sözcükler.işle("sayı<123;", null).toArray(),
				new Sözcük[][] {
					new Sözcük[] {new Sözcük_01İsim("sayı"), new Sözcük(SÖZCÜK.TİP_09ATAMA_SOLA), new Sözcük_03TamSayı("123"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)}
				});
	}
	
	@Test
	public void test02Atama03() throws Exception {
		// 1 :> sayı;
		assertArrayEquals(
				Aşama2Sözcükler.işle("1:>sayı;", null).toArray(),
				new Sözcük[][] {
					new Sözcük[] {new Sözcük_03TamSayı("1"), new Sözcük(SÖZCÜK.TİP_12TANIMLAMA_SAĞA), new Sözcük_01İsim("sayı"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)}
				});
	}
	
	@Test
	public void test02Atama04() throws Exception {
		// sayı <: 1;
		assertArrayEquals(
				Aşama2Sözcükler.işle("sayı<:1;", null).toArray(),
				new Sözcük[][] {
					new Sözcük[] {new Sözcük_01İsim("sayı"), new Sözcük(SÖZCÜK.TİP_11TANIMLAMA_SOLA), new Sözcük_03TamSayı("1"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)}
				});
	}
	
	@Test
	public void test02Atama05() throws Exception {
		// 1 > sayı : i32;
		assertArrayEquals(
				Aşama2Sözcükler.işle("1>sayı:i32;", null).toArray(),
				new Sözcük[][] {
					new Sözcük[] {new Sözcük_03TamSayı("1"), new Sözcük(SÖZCÜK.TİP_10ATAMA_SAĞA), new Sözcük_01İsim("sayı"), new Sözcük(SÖZCÜK.TİP_07DEĞİŞKEN_TİPİ), new Sözcük_01İsim("i32"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)}
				});
	}
	
	@Test
	public void test02Atama06() throws Exception {
		// sayı : i32 < 1;
		assertArrayEquals(
				Aşama2Sözcükler.işle("sayı:i32<1;", null).toArray(),
				new Sözcük[][] {
					new Sözcük[] {new Sözcük_01İsim("sayı"), new Sözcük(SÖZCÜK.TİP_07DEĞİŞKEN_TİPİ), new Sözcük_01İsim("i32"), new Sözcük(SÖZCÜK.TİP_09ATAMA_SOLA), new Sözcük_03TamSayı("1"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)}
			});
	}
	
	@Test
	public void test02Atama07() throws Exception {
		// sayı < - 1;
		assertArrayEquals(
				new Sözcük[][] {
					new Sözcük[] {new Sözcük_01İsim("sayı"), new Sözcük(SÖZCÜK.TİP_09ATAMA_SOLA), new Sözcük_02Operatör("-"), new Sözcük_03TamSayı("1"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)}
				},
				Aşama2Sözcükler.işle("sayı<-1;", null).toArray());
	}
	
	@Test
	public void test02Atama08() throws Exception {
		// 123.456 > onsalıklıSayı;
		assertArrayEquals(
				Aşama2Sözcükler.işle("123.456>onsalıklıSayı;", null).toArray(),
				new Sözcük[][] {
					new Sözcük[] {new Sözcük_04OndalıklıSayı("123.456"), new Sözcük(SÖZCÜK.TİP_10ATAMA_SAĞA), new Sözcük_01İsim("onsalıklıSayı"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)}
				});
	}
	
	@Test
	public void test02Atama09() throws Exception {
		// "engin kuzu" > isim;
		assertArrayEquals(
				Aşama2Sözcükler.işle("\"engin kuzu\">isim;", null).toArray(),
				new Sözcük[][] {
					new Sözcük[] {new Sözcük_05Metin("engin kuzu"), new Sözcük(SÖZCÜK.TİP_10ATAMA_SAĞA), new Sözcük_01İsim("isim"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)}
				});
	}
	
	@Test
	public void test03İşlem01() throws Exception {
		// sayı1 + sayı2 > sayı3;
		assertArrayEquals(
				Aşama2Sözcükler.işle("sayı1+sayı2>sayı3;", null).toArray(),
				new Sözcük[][] {
					new Sözcük[] {new Sözcük_01İsim("sayı1"), new Sözcük_02Operatör("+"), new Sözcük_01İsim("sayı2"), new Sözcük(SÖZCÜK.TİP_10ATAMA_SAĞA), new Sözcük_01İsim("sayı3"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)}
				});
	}
	
	@Test
	public void test03İşlem02() throws Exception {
		// sayı1 - sayı2 > sayı3;
		assertArrayEquals(
				Aşama2Sözcükler.işle("sayı1-sayı2>sayı3;", null).toArray(),
				new Sözcük[][] {
					new Sözcük[] {new Sözcük_01İsim("sayı1"), new Sözcük_02Operatör("-"), new Sözcük_01İsim("sayı2"), new Sözcük(SÖZCÜK.TİP_10ATAMA_SAĞA), new Sözcük_01İsim("sayı3"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)}
				});
	}
	
	@Test
	public void test03İşlem03() throws Exception {
		// sayı1 * sayı2 > sayı3;
		assertArrayEquals(
				Aşama2Sözcükler.işle("sayı1*sayı2>sayı3;", null).toArray(),
				new Sözcük[][] {
					new Sözcük[] {new Sözcük_01İsim("sayı1"), new Sözcük_02Operatör("*"), new Sözcük_01İsim("sayı2"), new Sözcük(SÖZCÜK.TİP_10ATAMA_SAĞA), new Sözcük_01İsim("sayı3"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)}
				});
	}
	
	@Test
	public void test03İşlem04() throws Exception {
		// sayı1 // sayı2 > sayı3;
		assertArrayEquals(
				Aşama2Sözcükler.işle("sayı1//sayı2>sayı3;", null).toArray(),
				new Sözcük[][] {
					new Sözcük[] {new Sözcük_01İsim("sayı1"), new Sözcük_02Operatör("//"), new Sözcük_01İsim("sayı2"), new Sözcük(SÖZCÜK.TİP_10ATAMA_SAĞA), new Sözcük_01İsim("sayı3"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)}
				});
	}
	
	@Test
	public void test03İşlem05() throws Exception {
		// +sayı1 + +sayı2 > sayı3;
		assertArrayEquals(
				Aşama2Sözcükler.işle("+sayı1 + +sayı2>sayı3;", null).toArray(),
				new Sözcük[][] {
					new Sözcük[] {new Sözcük_02Operatör("+"), new Sözcük_01İsim("sayı1"), new Sözcük_02Operatör("+"), new Sözcük_02Operatör("+"), new Sözcük_01İsim("sayı2"), new Sözcük(SÖZCÜK.TİP_10ATAMA_SAĞA), new Sözcük_01İsim("sayı3"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)}
				});
	}
	
	@Test
	public void test03İşlem06() throws Exception {
		// -sayı1 - -sayı2 > sayı3;
		assertArrayEquals(
				Aşama2Sözcükler.işle("-sayı1 - -sayı2>sayı3;", null).toArray(),
				new Sözcük[][] {
					new Sözcük[] {new Sözcük_02Operatör("-"), new Sözcük_01İsim("sayı1"), new Sözcük_02Operatör("-"), new Sözcük_02Operatör("-"), new Sözcük_01İsim("sayı2"), new Sözcük(SÖZCÜK.TİP_10ATAMA_SAĞA), new Sözcük_01İsim("sayı3"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)}
				});
	}
	
	@Test
	public void test03FonksiyonÇağrısı01() throws Exception {
		// sayı1.println;
		assertArrayEquals(
				Aşama2Sözcükler.işle("sayı1.println;", null).toArray(),
				new Sözcük[][] {
					new Sözcük[] {new Sözcük_01İsim("sayı1"), new Sözcük(SÖZCÜK.TİP_08NOKTA), new Sözcük_01İsim("println"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)}
				});
	}
	
	@Test
	public void test03FonksiyonÇağrısı02() throws Exception {
		// 123.println;
		assertArrayEquals(
				Aşama2Sözcükler.işle("123.println;", null).toArray(),
				new Sözcük[][] {
					new Sözcük[] {new Sözcük_03TamSayı("123"), new Sözcük(SÖZCÜK.TİP_08NOKTA), new Sözcük_01İsim("println"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)}
				});
	}
	
	@Test
	public void test03FonksiyonÇağrısı03() throws Exception {
		// 123.456.println;
		assertArrayEquals(
				Aşama2Sözcükler.işle("123.456.println;", null).toArray(),
				new Sözcük[][] {
					new Sözcük[] {new Sözcük_04OndalıklıSayı("123.456"), new Sözcük(SÖZCÜK.TİP_08NOKTA), new Sözcük_01İsim("println"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)}
				});
	}
	
	@Test
	public void test03GörünmeyenKarakterler01() throws Exception {
		// sayı1 + sayı2 > sayı3; (Aralara \r \n \t ' ' karakterleri serpiştirildi)
		assertArrayEquals(
				Aşama2Sözcükler.işle("\r\n\r\nsayı1\n\n+\t\tsayı2\r\n\t > sayı3   ;   ", null).toArray(),
				new Sözcük[][] {
					new Sözcük[] {new Sözcük_01İsim("sayı1"), new Sözcük_02Operatör("+"), new Sözcük_01İsim("sayı2"), new Sözcük(SÖZCÜK.TİP_10ATAMA_SAĞA), new Sözcük_01İsim("sayı3"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)}
				});
	}
	
	@Test
	public void test04Hata01() throws Exception {
		// sayı?sayı;
		try {
			Aşama2Sözcükler.işle("sayı?sayı;", null);
			fail("Exception Bekleniyordu...");
		} catch(Exception ex) {
			assertTrue(ex.getMessage().equals("Bilinmeyen Karakter : '?'"));
		}
	}
	
	@Test
	public void test04Hata02() throws Exception {
		// sayı + sayı > sayı
		try {
			Aşama2Sözcükler.işle("sayı+sayı>sayı", null);
			fail("Exception Bekleniyordu...");
		} catch(Exception ex) {
			assertTrue(ex.getMessage().equals("Anormal Sonlanma : Durum : 3"));
		}
	}
	
	@Test
	public void test04Hata03() throws Exception {
		// sayı + sayı > sayı;;
		try {
			Aşama2Sözcükler.işle("sayı+sayı>sayı;;", null);
			fail("Exception Bekleniyordu...");
		} catch(Exception ex) {
			assertTrue(ex.getMessage().equals("Gereksiz yere ';' kullanılmış"));
		}
	}

}
