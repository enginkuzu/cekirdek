package yardimci;

import java.util.HashMap;
import java.util.HashSet;

public class Degiskenler {

	public static final String UYGULAMA_ADI_VE_SÜRÜMÜ = "Çekirdek sürüm 0.1.2";
	
	public static boolean DİL_TÜRKÇE_Mİ = "tr".equals(System.getProperty("user.language"));
	
	public enum SÖZCÜK {
		TİP_00YOK,
		TİP_01İSİM,
		TİP_02OPERATÖR,
		TİP_03TAM_SAYI,
		TİP_04ONDALIKLI_SAYI,
		TİP_05METİN,
		TİP_06SATIR_SONU,
		TİP_07DEĞİŞKEN_TİPİ,
		TİP_08NOKTA,
		TİP_09ATAMA_SOLA,
		TİP_10ATAMA_SAĞA,
		TİP_11TANIMLAMA_SOLA,
		TİP_12TANIMLAMA_SAĞA,
		TİP_13ÖZELLİK,
		TİP_14AÇ_PARANTEZ,
		TİP_15KAPA_PARANTEZ,
		TİP_16AÇ_SÜSLÜ,
		TİP_17KAPA_SÜSLÜ,
		TİP_18VİRGÜL
	}
	
	public enum CÜMLE {
		TİP_01DEĞİŞKEN_YENİ,
		TİP_02GEÇİCİ_DEĞİŞKEN_YENİ,
		TİP_03DEĞİŞKEN_SİL,
		TİP_04OPERATÖR_İŞLEMİ,
		TİP_05FUNKSİYON_ÇAĞRISI,
		TİP_06DEĞİŞKEN_ATAMA,
		TİP_07MAKİNE_DİLİ_KOD,
		TİP_08MAKİNE_DİLİ_VERİ,
		TİP_09MAKİNE_DİLİ_SABİT_VERİ,
		TİP_10MAKİNE_DİLİ_SEMBOL,
		TİP_11SABİT_ATAMA
	}
	
	public enum FONKSİYON {
		TİP_01OPERATÖRFONKSİYON,
		TİP_02İSİMLİFONKSİYON,
		TİP_03ANAFONKSİYON
	}
	
	public enum SAKLAÇ {
		RAX, RBX, RCX, RDX, RBP, RSI, RDI, RSP, R8, R9, R10, R11, R12, R13, R14, R15
	}
	
	public static final HashSet<String> SAKLAÇLAR_64BİT = new HashSet<String>() {{
		add("RAX");
		add("RBX");
		add("RCX");
		add("RDX");
		add("RBP");
		add("RSI");
		add("RDI");
		add("RSP");
		add("R8");
		add("R9");
		add("R10");
		add("R11");
		add("R12");
		add("R13");
		add("R14");
		add("R15");
	}};
	
	public static final HashSet<String> ANAHTAR_KELIMELER = new HashSet<String>() {{
		add("i64");
		add("str");
	}};
	
	public static final int ID_i64 = 7;
	public static final int ID_str = 15;
	public static final int ID_Pstr = 75;
	public static final String STRING_i64 = "i64";
	public static final String STRING_str = "str";
	public static final String PSTRING_str = "+str";
	public static final HashMap<Integer, String> TİP_MAP_ID_STR = new HashMap<Integer, String>(){{
		put(ID_i64, STRING_i64);
		put(ID_str, STRING_str);
		put(ID_Pstr, PSTRING_str);
	}};
	public static final HashMap<String, Integer> TİP_MAP_STR_ID = new HashMap<String, Integer>(){{
		put(STRING_i64, ID_i64);
		put(STRING_str, ID_str);
		put(PSTRING_str, ID_Pstr);
	}};

}
