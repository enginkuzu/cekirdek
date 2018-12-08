package test3cümleler;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import aşama2sözcükler.Sözcük;
import aşama2sözcükler.Sözcük_01İsim;
import aşama2sözcükler.Sözcük_02Operatör;
import aşama2sözcükler.Sözcük_03TamSayı;
import aşama3cümleler.Aşama3Cümleler;
import aşama3cümleler.Cümle;
import aşama3cümleler.Cümle_01DeğişkenYeni;
import aşama3cümleler.Cümle_03Operatörİşlemi;
import aşama3cümleler.Cümle_04FonksiyonÇağrısı;
import aşama3cümleler.Cümle_05SabitTanımlama;
import aşama3cümleler.Cümle_06DeğişkenAtama;
import yardımcı.Değişkenler.SÖZCÜK;

public class BirimTestleri {

	@Test
	public void test01DeğişkenTanımlama01() throws Exception {
		assertArrayEquals(
				Aşama3Cümleler.işle(new ArrayList<Sözcük[]>() {{
					add(new Sözcük[] {new Sözcük_01İsim("sayı1"), new Sözcük(SÖZCÜK.TİP_11TANIMLAMA_SOLA), new Sözcük_03TamSayı("123750"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)});
				}}, null).toArray(),
				new Cümle[] {new Cümle_01DeğişkenYeni(1, "sayı1", "i32"), new Cümle_05SabitTanımlama(-1, "123750", "i32"), new Cümle_06DeğişkenAtama(-1, 1)});
	}
	
	@Test
	public void test01DeğişkenTanımlama02() throws Exception {
		assertArrayEquals(
				Aşama3Cümleler.işle(new ArrayList<Sözcük[]>() {{
					add(new Sözcük[] {new Sözcük_03TamSayı("123750"), new Sözcük(SÖZCÜK.TİP_12TANIMLAMA_SAĞA), new Sözcük_01İsim("sayı1"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)});
				}}, null).toArray(),
				new Cümle[] {new Cümle_01DeğişkenYeni(1, "sayı1", "i32"), new Cümle_05SabitTanımlama(-1, "123750", "i32"), new Cümle_06DeğişkenAtama(-1, 1)});
	}
	
	@Test
	public void test01DeğişkenTanımlama03() throws Exception {
		assertArrayEquals(
				Aşama3Cümleler.işle(new ArrayList<Sözcük[]>() {{
					add(new Sözcük[] {new Sözcük_01İsim("sayı1"), new Sözcük(SÖZCÜK.TİP_07DEĞİŞKEN_TİPİ), new Sözcük_01İsim("i32"), new Sözcük(SÖZCÜK.TİP_09ATAMA_SOLA), new Sözcük_03TamSayı("123750"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)});
				}}, null).toArray(),
				new Cümle[] {new Cümle_01DeğişkenYeni(1, "sayı1", "i32"), new Cümle_05SabitTanımlama(-1, "123750", "i32"), new Cümle_06DeğişkenAtama(-1, 1)});
	}
	
	@Test
	public void test01DeğişkenTanımlama04() throws Exception {
		assertArrayEquals(
				Aşama3Cümleler.işle(new ArrayList<Sözcük[]>() {{
					add(new Sözcük[] {new Sözcük_03TamSayı("123750"), new Sözcük(SÖZCÜK.TİP_10ATAMA_SAĞA), new Sözcük_01İsim("sayı1"), new Sözcük(SÖZCÜK.TİP_07DEĞİŞKEN_TİPİ), new Sözcük_01İsim("i32"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)});
				}}, null).toArray(),
				new Cümle[] {new Cümle_01DeğişkenYeni(1, "sayı1", "i32"), new Cümle_05SabitTanımlama(-1, "123750", "i32"), new Cümle_06DeğişkenAtama(-1, 1)});
	}
	
	@Test
	public void test02DeğişkenAtama01() throws Exception {
		assertArrayEquals(
				Aşama3Cümleler.işle(new ArrayList<Sözcük[]>() {{
					add(new Sözcük[] {new Sözcük_01İsim("sayı1"), new Sözcük(SÖZCÜK.TİP_11TANIMLAMA_SOLA), new Sözcük_03TamSayı("123750"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)});
					add(new Sözcük[] {new Sözcük_01İsim("sayı2"), new Sözcük(SÖZCÜK.TİP_11TANIMLAMA_SOLA), new Sözcük_03TamSayı("123755"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)});
					add(new Sözcük[] {new Sözcük_01İsim("sayı1"), new Sözcük(SÖZCÜK.TİP_10ATAMA_SAĞA), new Sözcük_01İsim("sayı2"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)});
					add(new Sözcük[] {new Sözcük_01İsim("sayı2"), new Sözcük(SÖZCÜK.TİP_09ATAMA_SOLA), new Sözcük_01İsim("sayı1"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)});
				}}, null).toArray(),
				new Cümle[] {
						new Cümle_01DeğişkenYeni(1, "sayı1", "i32"), new Cümle_05SabitTanımlama(-1, "123750", "i32"), new Cümle_06DeğişkenAtama(-1, 1),
						new Cümle_01DeğişkenYeni(2, "sayı2", "i32"), new Cümle_05SabitTanımlama(-2, "123755", "i32"), new Cümle_06DeğişkenAtama(-2, 2),
						new Cümle_06DeğişkenAtama(1, 2),
						new Cümle_06DeğişkenAtama(1, 2)
						});
	}
	
	@Test
	public void test03FonksiyonÇağrısı01() throws Exception {
		assertArrayEquals(
				Aşama3Cümleler.işle(new ArrayList<Sözcük[]>() {{
					add(new Sözcük[] {new Sözcük_01İsim("sayı1"), new Sözcük(SÖZCÜK.TİP_11TANIMLAMA_SOLA), new Sözcük_03TamSayı("123750"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)});
					add(new Sözcük[] {new Sözcük_01İsim("sayı1"), new Sözcük(SÖZCÜK.TİP_08NOKTA), new Sözcük_01İsim("println"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)});
				}}, null).toArray(),
				new Cümle[] {
						new Cümle_01DeğişkenYeni(1, "sayı1", "i32"), new Cümle_05SabitTanımlama(-1, "123750", "i32"), new Cümle_06DeğişkenAtama(-1, 1),
						new Cümle_04FonksiyonÇağrısı(-2, "println").add(1)
						});
	}
	
	@Test
	public void test04Operatör01() throws Exception {
		assertArrayEquals(
				Aşama3Cümleler.işle(new ArrayList<Sözcük[]>() {{
					add(new Sözcük[] {new Sözcük_01İsim("sayı1"), new Sözcük(SÖZCÜK.TİP_11TANIMLAMA_SOLA), new Sözcük_03TamSayı("123750"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)});
					add(new Sözcük[] {new Sözcük_01İsim("sayı2"), new Sözcük(SÖZCÜK.TİP_11TANIMLAMA_SOLA), new Sözcük_03TamSayı("123755"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)});
					add(new Sözcük[] {new Sözcük_01İsim("sayı3"), new Sözcük(SÖZCÜK.TİP_11TANIMLAMA_SOLA), new Sözcük_03TamSayı("123760"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)});
					add(new Sözcük[] {new Sözcük_01İsim("sayı1"), new Sözcük_02Operatör("+"), new Sözcük_01İsim("sayı2"), new Sözcük(SÖZCÜK.TİP_10ATAMA_SAĞA), new Sözcük_01İsim("sayı3"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)});
					add(new Sözcük[] {new Sözcük_01İsim("sayı3"), new Sözcük(SÖZCÜK.TİP_09ATAMA_SOLA), new Sözcük_01İsim("sayı1"), new Sözcük_02Operatör("-"), new Sözcük_01İsim("sayı2"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)});
					add(new Sözcük[] {new Sözcük_01İsim("sayı1"), new Sözcük_02Operatör("*"), new Sözcük_01İsim("sayı2"), new Sözcük(SÖZCÜK.TİP_10ATAMA_SAĞA), new Sözcük_01İsim("sayı3"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)});
					add(new Sözcük[] {new Sözcük_01İsim("sayı3"), new Sözcük(SÖZCÜK.TİP_09ATAMA_SOLA), new Sözcük_01İsim("sayı1"), new Sözcük_02Operatör("//"), new Sözcük_01İsim("sayı2"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)});
				}}, null).toArray(),
				new Cümle[] {
						new Cümle_01DeğişkenYeni(1, "sayı1", "i32"), new Cümle_05SabitTanımlama(-1, "123750", "i32"), new Cümle_06DeğişkenAtama(-1, 1),
						new Cümle_01DeğişkenYeni(2, "sayı2", "i32"), new Cümle_05SabitTanımlama(-2, "123755", "i32"), new Cümle_06DeğişkenAtama(-2, 2),
						new Cümle_01DeğişkenYeni(3, "sayı3", "i32"), new Cümle_05SabitTanımlama(-3, "123760", "i32"), new Cümle_06DeğişkenAtama(-3, 3),
						new Cümle_03Operatörİşlemi(-4, "+", 1, 2), new Cümle_06DeğişkenAtama(-4, 3),
						new Cümle_03Operatörİşlemi(-5, "-", 1, 2), new Cümle_06DeğişkenAtama(-5, 3),
						new Cümle_03Operatörİşlemi(-6, "*", 1, 2), new Cümle_06DeğişkenAtama(-6, 3),
						new Cümle_03Operatörİşlemi(-7, "//", 1, 2), new Cümle_06DeğişkenAtama(-7, 3),
						});
	}
	
	@Test
	public void test05Hata01() throws Exception {
		// sayı2 < sayı1;
		try {
			Aşama3Cümleler.işle(new ArrayList<Sözcük[]>() {{
				add(new Sözcük[] {new Sözcük_01İsim("sayı2"), new Sözcük(SÖZCÜK.TİP_11TANIMLAMA_SOLA), new Sözcük_03TamSayı("123755"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)});
				add(new Sözcük[] {new Sözcük_01İsim("sayı2"), new Sözcük(SÖZCÜK.TİP_09ATAMA_SOLA), new Sözcük_01İsim("sayı1"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)});
			}}, null);
			fail("Exception Bekleniyordu...");
		} catch(Exception ex) {
			assertTrue(ex.getMessage().equals("Tanımsız Değişken : sayı1 !!!"));
		}
		try {
			Aşama3Cümleler.işle(new ArrayList<Sözcük[]>() {{
				add(new Sözcük[] {new Sözcük_01İsim("sayı1"), new Sözcük(SÖZCÜK.TİP_11TANIMLAMA_SOLA), new Sözcük_03TamSayı("123750"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)});
				add(new Sözcük[] {new Sözcük_01İsim("sayı2"), new Sözcük(SÖZCÜK.TİP_09ATAMA_SOLA), new Sözcük_01İsim("sayı1"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)});
			}}, null);
			fail("Exception Bekleniyordu...");
		} catch(Exception ex) {
			assertTrue(ex.getMessage().equals("Tanımsız Değişken : sayı2 !!!"));
		}
	}
	
	@Test
	public void test05Hata02() throws Exception {
		// sayı1 > sayı2;
		try {
			Aşama3Cümleler.işle(new ArrayList<Sözcük[]>() {{
				add(new Sözcük[] {new Sözcük_01İsim("sayı2"), new Sözcük(SÖZCÜK.TİP_11TANIMLAMA_SOLA), new Sözcük_03TamSayı("123755"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)});
				add(new Sözcük[] {new Sözcük_01İsim("sayı1"), new Sözcük(SÖZCÜK.TİP_10ATAMA_SAĞA), new Sözcük_01İsim("sayı2"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)});
			}}, null);
			fail("Exception Bekleniyordu...");
		} catch(Exception ex) {
			assertTrue(ex.getMessage().equals("Tanımsız Değişken : sayı1 !!!"));
		}
		try {
			Aşama3Cümleler.işle(new ArrayList<Sözcük[]>() {{
				add(new Sözcük[] {new Sözcük_01İsim("sayı1"), new Sözcük(SÖZCÜK.TİP_11TANIMLAMA_SOLA), new Sözcük_03TamSayı("123750"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)});
				add(new Sözcük[] {new Sözcük_01İsim("sayı1"), new Sözcük(SÖZCÜK.TİP_10ATAMA_SAĞA), new Sözcük_01İsim("sayı2"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)});
			}}, null);
			fail("Exception Bekleniyordu...");
		} catch(Exception ex) {
			assertTrue(ex.getMessage().equals("Tanımsız Değişken : sayı2 !!!"));
		}
	}
	
	@Test
	public void test05Hata03() throws Exception {
		// sayı1.println;
		try {
			Aşama3Cümleler.işle(new ArrayList<Sözcük[]>() {{
				add(new Sözcük[] {new Sözcük_01İsim("sayı1"), new Sözcük(SÖZCÜK.TİP_08NOKTA), new Sözcük_01İsim("println"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)});
			}}, null);
			fail("Exception Bekleniyordu...");
		} catch(Exception ex) {
			assertTrue(ex.getMessage().equals("Tanımsız Değişken : sayı1 !!!"));
		}
	}
	
	@Test
	public void test05Hata04() throws Exception {
		// sayı3 < sayı1 + sayı2;
		try {
			Aşama3Cümleler.işle(new ArrayList<Sözcük[]>() {{
				add(new Sözcük[] {new Sözcük_01İsim("sayı2"), new Sözcük(SÖZCÜK.TİP_11TANIMLAMA_SOLA), new Sözcük_03TamSayı("123755"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)});
				add(new Sözcük[] {new Sözcük_01İsim("sayı3"), new Sözcük(SÖZCÜK.TİP_11TANIMLAMA_SOLA), new Sözcük_03TamSayı("123760"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)});
				add(new Sözcük[] {new Sözcük_01İsim("sayı3"), new Sözcük(SÖZCÜK.TİP_09ATAMA_SOLA), new Sözcük_01İsim("sayı1"), new Sözcük_02Operatör("+"), new Sözcük_01İsim("sayı2"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)});
			}}, null);
			fail("Exception Bekleniyordu...");
		} catch(Exception ex) {
			assertTrue(ex.getMessage().equals("Tanımsız Değişken : sayı1 !!!"));
		}
		try {
			Aşama3Cümleler.işle(new ArrayList<Sözcük[]>() {{
				add(new Sözcük[] {new Sözcük_01İsim("sayı1"), new Sözcük(SÖZCÜK.TİP_11TANIMLAMA_SOLA), new Sözcük_03TamSayı("123750"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)});
				add(new Sözcük[] {new Sözcük_01İsim("sayı3"), new Sözcük(SÖZCÜK.TİP_11TANIMLAMA_SOLA), new Sözcük_03TamSayı("123760"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)});
				add(new Sözcük[] {new Sözcük_01İsim("sayı3"), new Sözcük(SÖZCÜK.TİP_09ATAMA_SOLA), new Sözcük_01İsim("sayı1"), new Sözcük_02Operatör("+"), new Sözcük_01İsim("sayı2"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)});
			}}, null);
			fail("Exception Bekleniyordu...");
		} catch(Exception ex) {
			assertTrue(ex.getMessage().equals("Tanımsız Değişken : sayı2 !!!"));
		}
		try {
			Aşama3Cümleler.işle(new ArrayList<Sözcük[]>() {{
				add(new Sözcük[] {new Sözcük_01İsim("sayı1"), new Sözcük(SÖZCÜK.TİP_11TANIMLAMA_SOLA), new Sözcük_03TamSayı("123750"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)});
				add(new Sözcük[] {new Sözcük_01İsim("sayı2"), new Sözcük(SÖZCÜK.TİP_11TANIMLAMA_SOLA), new Sözcük_03TamSayı("123755"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)});
				add(new Sözcük[] {new Sözcük_01İsim("sayı3"), new Sözcük(SÖZCÜK.TİP_09ATAMA_SOLA), new Sözcük_01İsim("sayı1"), new Sözcük_02Operatör("+"), new Sözcük_01İsim("sayı2"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)});
			}}, null);
			fail("Exception Bekleniyordu...");
		} catch(Exception ex) {
			assertTrue(ex.getMessage().equals("Tanımsız Değişken : sayı3 !!!"));
		}
	}
	
	@Test
	public void test05Hata05() throws Exception {
		// sayı1 + sayı2 > sayı3;
		try {
			Aşama3Cümleler.işle(new ArrayList<Sözcük[]>() {{
				add(new Sözcük[] {new Sözcük_01İsim("sayı2"), new Sözcük(SÖZCÜK.TİP_11TANIMLAMA_SOLA), new Sözcük_03TamSayı("123755"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)});
				add(new Sözcük[] {new Sözcük_01İsim("sayı3"), new Sözcük(SÖZCÜK.TİP_11TANIMLAMA_SOLA), new Sözcük_03TamSayı("123760"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)});
				add(new Sözcük[] {new Sözcük_01İsim("sayı1"), new Sözcük_02Operatör("+"), new Sözcük_01İsim("sayı2"), new Sözcük(SÖZCÜK.TİP_10ATAMA_SAĞA), new Sözcük_01İsim("sayı3"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)});
			}}, null);
			fail("Exception Bekleniyordu...");
		} catch(Exception ex) {
			assertTrue(ex.getMessage().equals("Tanımsız Değişken : sayı1 !!!"));
		}
		try {
			Aşama3Cümleler.işle(new ArrayList<Sözcük[]>() {{
				add(new Sözcük[] {new Sözcük_01İsim("sayı1"), new Sözcük(SÖZCÜK.TİP_11TANIMLAMA_SOLA), new Sözcük_03TamSayı("123750"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)});
				add(new Sözcük[] {new Sözcük_01İsim("sayı3"), new Sözcük(SÖZCÜK.TİP_11TANIMLAMA_SOLA), new Sözcük_03TamSayı("123760"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)});
				add(new Sözcük[] {new Sözcük_01İsim("sayı1"), new Sözcük_02Operatör("+"), new Sözcük_01İsim("sayı2"), new Sözcük(SÖZCÜK.TİP_10ATAMA_SAĞA), new Sözcük_01İsim("sayı3"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)});
			}}, null);
			fail("Exception Bekleniyordu...");
		} catch(Exception ex) {
			assertTrue(ex.getMessage().equals("Tanımsız Değişken : sayı2 !!!"));
		}
		try {
			Aşama3Cümleler.işle(new ArrayList<Sözcük[]>() {{
				add(new Sözcük[] {new Sözcük_01İsim("sayı1"), new Sözcük(SÖZCÜK.TİP_11TANIMLAMA_SOLA), new Sözcük_03TamSayı("123750"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)});
				add(new Sözcük[] {new Sözcük_01İsim("sayı2"), new Sözcük(SÖZCÜK.TİP_11TANIMLAMA_SOLA), new Sözcük_03TamSayı("123755"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)});
				add(new Sözcük[] {new Sözcük_01İsim("sayı1"), new Sözcük_02Operatör("+"), new Sözcük_01İsim("sayı2"), new Sözcük(SÖZCÜK.TİP_10ATAMA_SAĞA), new Sözcük_01İsim("sayı3"), new Sözcük(SÖZCÜK.TİP_06SATIR_SONU)});
			}}, null);
			fail("Exception Bekleniyordu...");
		} catch(Exception ex) {
			assertTrue(ex.getMessage().equals("Tanımsız Değişken : sayı3 !!!"));
		}
	}

}
