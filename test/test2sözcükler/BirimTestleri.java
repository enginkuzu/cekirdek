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

class BirimTestleri {
	
	@Test
	public void test01Açıklama01() throws Exception {
		// sayı < 1; '
		// sayı < 2;
		assertArrayEquals(
				new Sözcük[] {
						new Sözcük_01İsim("sayı"),
						new Sözcük(SÖZCÜK.TİP_09ATAMA_SOLA),
						new Sözcük_03TamSayı("1"),
						new Sözcük(SÖZCÜK.TİP_06SATIR_SONU),
						new Sözcük_01İsim("sayı"),
						new Sözcük(SÖZCÜK.TİP_09ATAMA_SOLA),
						new Sözcük_03TamSayı("2"),
						new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)},
				Aşama2Sözcükler.işle("sayı<1;'\nsayı<2;").toArray());
	}
	
	@Test
	public void test01Açıklama02() throws Exception {
		// sayı < 1; 'Açıklama satırı
		// sayı < 2;
		assertArrayEquals(
				new Sözcük[] {
						new Sözcük_01İsim("sayı"),
						new Sözcük(SÖZCÜK.TİP_09ATAMA_SOLA),
						new Sözcük_03TamSayı("1"),
						new Sözcük(SÖZCÜK.TİP_06SATIR_SONU),
						new Sözcük_01İsim("sayı"),
						new Sözcük(SÖZCÜK.TİP_09ATAMA_SOLA),
						new Sözcük_03TamSayı("2"),
						new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)},
				Aşama2Sözcükler.işle("sayı<1;'Açıklama satırı\nsayı<2;").toArray());
	}
	
	@Test
	public void test01Açıklama03() throws Exception {
		// sayı < 1;
		// 'Açıklama satırı
		// sayı < 2;
		assertArrayEquals(
				new Sözcük[] {
						new Sözcük_01İsim("sayı"),
						new Sözcük(SÖZCÜK.TİP_09ATAMA_SOLA),
						new Sözcük_03TamSayı("1"),
						new Sözcük(SÖZCÜK.TİP_06SATIR_SONU),
						new Sözcük_01İsim("sayı"),
						new Sözcük(SÖZCÜK.TİP_09ATAMA_SOLA),
						new Sözcük_03TamSayı("2"),
						new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)},
				Aşama2Sözcükler.işle("sayı<1;\n'Açıklama satırı\nsayı<2;").toArray());
	}
	
	@Test
	public void test01Açıklama04() throws Exception {
		// sayı < 1; '' Açıklama '' sayı<2;
		assertArrayEquals(
				new Sözcük[] {
						new Sözcük_01İsim("sayı"),
						new Sözcük(SÖZCÜK.TİP_09ATAMA_SOLA),
						new Sözcük_03TamSayı("1"),
						new Sözcük(SÖZCÜK.TİP_06SATIR_SONU),
						new Sözcük_01İsim("sayı"),
						new Sözcük(SÖZCÜK.TİP_09ATAMA_SOLA),
						new Sözcük_03TamSayı("2"),
						new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)},
				Aşama2Sözcükler.işle("sayı<1;'' Açıklama ''sayı<2;").toArray());
	}

	@Test
	public void test01Açıklama05() throws Exception {
		// sayı < 1; '' Açıklama
		// satırı '' sayı < 2;
		assertArrayEquals(
				new Sözcük[] {
						new Sözcük_01İsim("sayı"),
						new Sözcük(SÖZCÜK.TİP_09ATAMA_SOLA),
						new Sözcük_03TamSayı("1"),
						new Sözcük(SÖZCÜK.TİP_06SATIR_SONU),
						new Sözcük_01İsim("sayı"),
						new Sözcük(SÖZCÜK.TİP_09ATAMA_SOLA),
						new Sözcük_03TamSayı("2"),
						new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)},
				Aşama2Sözcükler.işle("sayı<1;'' Açıklama\nsatırı ''sayı<2;").toArray());
	}
	
	@Test
	public void test01Açıklama06() throws Exception {
		// sayı < 1;
		// '' Açıklama
		// satırı ''
		// sayı < 2;
		assertArrayEquals(
				new Sözcük[] {
						new Sözcük_01İsim("sayı"),
						new Sözcük(SÖZCÜK.TİP_09ATAMA_SOLA),
						new Sözcük_03TamSayı("1"),
						new Sözcük(SÖZCÜK.TİP_06SATIR_SONU),
						new Sözcük_01İsim("sayı"),
						new Sözcük(SÖZCÜK.TİP_09ATAMA_SOLA),
						new Sözcük_03TamSayı("2"),
						new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)},
				Aşama2Sözcükler.işle("sayı<1;\n'' Açıklama\nsatırı ''\nsayı<2;").toArray());
	}
	
	@Test
	public void test01Açıklama07() throws Exception {
		// sayı < 1;
		// '' Açıklama ' satırı ''
		// sayı < 2;
		assertArrayEquals(
				new Sözcük[] {
						new Sözcük_01İsim("sayı"),
						new Sözcük(SÖZCÜK.TİP_09ATAMA_SOLA),
						new Sözcük_03TamSayı("1"),
						new Sözcük(SÖZCÜK.TİP_06SATIR_SONU),
						new Sözcük_01İsim("sayı"),
						new Sözcük(SÖZCÜK.TİP_09ATAMA_SOLA),
						new Sözcük_03TamSayı("2"),
						new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)},
				Aşama2Sözcükler.işle("sayı<1;\n'' Açıklama ' satırı ''\nsayı<2;").toArray());
	}
	
	@Test
	public void test02Atama01() throws Exception {
		// 123 > sayı;
		assertArrayEquals(
				new Sözcük[] {
						new Sözcük_03TamSayı("123"),
						new Sözcük(SÖZCÜK.TİP_10ATAMA_SAĞA),
						new Sözcük_01İsim("sayı"),
						new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)},
				Aşama2Sözcükler.işle("123>sayı;").toArray());
	}
	
	@Test
	public void test02Atama02() throws Exception {
		// sayı < 123;
		assertArrayEquals(
				new Sözcük[] {
						new Sözcük_01İsim("sayı"),
						new Sözcük(SÖZCÜK.TİP_09ATAMA_SOLA),
						new Sözcük_03TamSayı("123"),
						new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)},
				Aşama2Sözcükler.işle("sayı<123;").toArray());
	}
	
	@Test
	public void test02Atama03() throws Exception {
		// 1 :> sayı;
		assertArrayEquals(
				new Sözcük[] {
						new Sözcük_03TamSayı("1"),
						new Sözcük(SÖZCÜK.TİP_12TANIMLAMA_SAĞA),
						new Sözcük_01İsim("sayı"),
						new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)},
				Aşama2Sözcükler.işle("1:>sayı;").toArray());
	}
	
	@Test
	public void test02Atama04() throws Exception {
		// sayı <: 1;
		assertArrayEquals(
				new Sözcük[] {
						new Sözcük_01İsim("sayı"),
						new Sözcük(SÖZCÜK.TİP_11TANIMLAMA_SOLA),
						new Sözcük_03TamSayı("1"),
						new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)},
				Aşama2Sözcükler.işle("sayı<:1;").toArray());
	}
	
	@Test
	public void test02Atama05() throws Exception {
		// 1 > sayı : i32;
		assertArrayEquals(
				new Sözcük[] {
						new Sözcük_03TamSayı("1"),
						new Sözcük(SÖZCÜK.TİP_10ATAMA_SAĞA),
						new Sözcük_01İsim("sayı"),
						new Sözcük(SÖZCÜK.TİP_07DEĞİŞKEN_TİPİ),
						new Sözcük_01İsim("i32"),
						new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)},
				Aşama2Sözcükler.işle("1>sayı:i32;").toArray());
	}
	
	@Test
	public void test02Atama06() throws Exception {
		// sayı : i32 < 1;
		assertArrayEquals(
			new Sözcük[] {
					new Sözcük_01İsim("sayı"),
					new Sözcük(SÖZCÜK.TİP_07DEĞİŞKEN_TİPİ),
					new Sözcük_01İsim("i32"),
					new Sözcük(SÖZCÜK.TİP_09ATAMA_SOLA),
					new Sözcük_03TamSayı("1"),
					new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)},
			Aşama2Sözcükler.işle("sayı:i32<1;").toArray());
	}
	
	@Test
	public void test02Atama07() throws Exception {
		// sayı < - 1;
		assertArrayEquals(
				new Sözcük[] {
						new Sözcük_01İsim("sayı"),
						new Sözcük(SÖZCÜK.TİP_09ATAMA_SOLA),
						new Sözcük_02Operatör("-"),
						new Sözcük_03TamSayı("1"),
						new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)},
				Aşama2Sözcükler.işle("sayı<-1;").toArray());
	}
	
	@Test
	public void test02Atama08() throws Exception {
		// 123.456 > onsalıklıSayı;
		assertArrayEquals(
				new Sözcük[] {
						new Sözcük_04OndalıklıSayı("123.456"),
						new Sözcük(SÖZCÜK.TİP_10ATAMA_SAĞA),
						new Sözcük_01İsim("onsalıklıSayı"),
						new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)},
				Aşama2Sözcükler.işle("123.456>onsalıklıSayı;").toArray());
	}
	
	@Test
	public void test02Atama09() throws Exception {
		// "engin kuzu" > isim;
		assertArrayEquals(
				new Sözcük[] {
						new Sözcük_05Metin("engin kuzu"),
						new Sözcük(SÖZCÜK.TİP_10ATAMA_SAĞA),
						new Sözcük_01İsim("isim"),
						new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)},
				Aşama2Sözcükler.işle("\"engin kuzu\">isim;").toArray());
	}
	
	@Test
	public void test03İşlem01() throws Exception {
		// sayı1 + sayı2 > sayı3;
		assertArrayEquals(
				new Sözcük[] {
						new Sözcük_01İsim("sayı1"),
						new Sözcük_02Operatör("+"),
						new Sözcük_01İsim("sayı2"),
						new Sözcük(SÖZCÜK.TİP_10ATAMA_SAĞA),
						new Sözcük_01İsim("sayı3"),
						new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)},
				Aşama2Sözcükler.işle("sayı1+sayı2>sayı3;").toArray());
	}
	
	@Test
	public void test03İşlem02() throws Exception {
		// sayı1 - sayı2 > sayı3;
		assertArrayEquals(
				new Sözcük[] {
						new Sözcük_01İsim("sayı1"),
						new Sözcük_02Operatör("-"),
						new Sözcük_01İsim("sayı2"),
						new Sözcük(SÖZCÜK.TİP_10ATAMA_SAĞA),
						new Sözcük_01İsim("sayı3"),
						new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)},
				Aşama2Sözcükler.işle("sayı1-sayı2>sayı3;").toArray());
	}
	
	@Test
	public void test03İşlem03() throws Exception {
		// sayı1 * sayı2 > sayı3;
		assertArrayEquals(
				new Sözcük[] {
						new Sözcük_01İsim("sayı1"),
						new Sözcük_02Operatör("*"),
						new Sözcük_01İsim("sayı2"),
						new Sözcük(SÖZCÜK.TİP_10ATAMA_SAĞA),
						new Sözcük_01İsim("sayı3"),
						new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)},
				Aşama2Sözcükler.işle("sayı1*sayı2>sayı3;").toArray());
	}
	
	@Test
	public void test03İşlem04() throws Exception {
		// sayı1 // sayı2 > sayı3;
		assertArrayEquals(
				new Sözcük[] {
						new Sözcük_01İsim("sayı1"),
						new Sözcük_02Operatör("//"),
						new Sözcük_01İsim("sayı2"),
						new Sözcük(SÖZCÜK.TİP_10ATAMA_SAĞA),
						new Sözcük_01İsim("sayı3"),
						new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)},
				Aşama2Sözcükler.işle("sayı1//sayı2>sayı3;").toArray());
	}
	
	@Test
	public void test03İşlem05() throws Exception {
		// +sayı1 + +sayı2 > sayı3;
		assertArrayEquals(
				new Sözcük[] {
						new Sözcük_02Operatör("+"),
						new Sözcük_01İsim("sayı1"),
						new Sözcük_02Operatör("+"),
						new Sözcük_02Operatör("+"),
						new Sözcük_01İsim("sayı2"),
						new Sözcük(SÖZCÜK.TİP_10ATAMA_SAĞA),
						new Sözcük_01İsim("sayı3"),
						new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)},
				Aşama2Sözcükler.işle("+sayı1 + +sayı2>sayı3;").toArray());
	}
	
	@Test
	public void test03İşlem06() throws Exception {
		// -sayı1 - -sayı2 > sayı3;
		assertArrayEquals(
				new Sözcük[] {
						new Sözcük_02Operatör("-"),
						new Sözcük_01İsim("sayı1"),
						new Sözcük_02Operatör("-"),
						new Sözcük_02Operatör("-"),
						new Sözcük_01İsim("sayı2"),
						new Sözcük(SÖZCÜK.TİP_10ATAMA_SAĞA),
						new Sözcük_01İsim("sayı3"),
						new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)},
				Aşama2Sözcükler.işle("-sayı1 - -sayı2>sayı3;").toArray());
	}
	
	@Test
	public void test03FonksiyonÇağrısı01() throws Exception {
		// sayı1.println;
		assertArrayEquals(
				new Sözcük[] {
						new Sözcük_01İsim("sayı1"),
						new Sözcük(SÖZCÜK.TİP_08NOKTA),
						new Sözcük_01İsim("println"),
						new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)},
				Aşama2Sözcükler.işle("sayı1.println;").toArray());
	}
	
	@Test
	public void test03FonksiyonÇağrısı02() throws Exception {
		// 123.println;
		assertArrayEquals(
				new Sözcük[] {
						new Sözcük_03TamSayı("123"),
						new Sözcük(SÖZCÜK.TİP_08NOKTA),
						new Sözcük_01İsim("println"),
						new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)},
				Aşama2Sözcükler.işle("123.println;").toArray());
	}
	
	@Test
	public void test03FonksiyonÇağrısı03() throws Exception {
		// 123.456.println;
		assertArrayEquals(
				new Sözcük[] {
						new Sözcük_04OndalıklıSayı("123.456"),
						new Sözcük(SÖZCÜK.TİP_08NOKTA),
						new Sözcük_01İsim("println"),
						new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)},
				Aşama2Sözcükler.işle("123.456.println;").toArray());
	}
	
	@Test
	public void test03GörünmeyenKarakterler01() throws Exception {
		// sayı1 + sayı2 > sayı3; (Aralara \r \n \t ' ' karakterleri serpiştirildi)
		assertArrayEquals(
				new Sözcük[] {
						new Sözcük_01İsim("sayı1"),
						new Sözcük_02Operatör("+"),
						new Sözcük_01İsim("sayı2"),
						new Sözcük(SÖZCÜK.TİP_10ATAMA_SAĞA),
						new Sözcük_01İsim("sayı3"),
						new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)},
				Aşama2Sözcükler.işle("\r\n\r\nsayı1\n\n+\t\tsayı2\r\n\t > sayı3   ;   ").toArray());
	}
	
	@Test
	public void test04Hata01() throws Exception {
		// sayı?sayı;
		try {
			assertArrayEquals(
					new Sözcük[] {
							new Sözcük_01İsim("sayı"),
							new Sözcük_02Operatör("?"),
							new Sözcük_01İsim("sayı"),
							new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)},
					Aşama2Sözcükler.işle("sayı?sayı;").toArray());
			fail("Exception Bekleniyordu...");
		}catch(Exception ex) {
			assertTrue(ex.getMessage().equals("Bilinmeyen Karakter : '?'"));
		}
	}
	
	@Test
	public void test04Hata02() throws Exception {
		// sayı + sayı > sayı
		try {
			assertArrayEquals(
					new Sözcük[] {
							new Sözcük_01İsim("sayı"),
							new Sözcük_02Operatör("+"),
							new Sözcük_01İsim("sayı"),
							new Sözcük(SÖZCÜK.TİP_10ATAMA_SAĞA),
							new Sözcük_01İsim("sayı")},
					Aşama2Sözcükler.işle("sayı+sayı>sayı").toArray());
			fail("Exception Bekleniyordu...");
		}catch(Exception ex) {
			assertTrue(ex.getMessage().equals("Anormal Sonlanma : Durum : 3"));
		}
	}
	
	@Test
	public void test04Hata03() throws Exception {
		// sayı + sayı > sayı;;
		try {
			assertArrayEquals(
					new Sözcük[] {
							new Sözcük_01İsim("sayı"),
							new Sözcük_02Operatör("+"),
							new Sözcük_01İsim("sayı"),
							new Sözcük(SÖZCÜK.TİP_10ATAMA_SAĞA),
							new Sözcük_01İsim("sayı"),
							new Sözcük(SÖZCÜK.TİP_06SATIR_SONU),
							new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)},
					Aşama2Sözcükler.işle("sayı+sayı>sayı;;").toArray());
			fail("Exception Bekleniyordu...");
		}catch(Exception ex) {
			assertTrue(ex.getMessage().equals("Gereksiz yere ';' kullanılmış"));
		}
	}

}
