package yardımcı;

public class Değişkenler {

	public static final String UYGULAMA_ADI_VE_SURUMU = "Derleyici 0.0.4";
	
	public static boolean IS_TURKISH = "tr".equals(System.getProperty("user.language"));
	
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
		TİP_14AÇPARANTEZ,
		TİP_15KAPAPARANTEZ,
		TİP_16AÇSÜSLÜ,
		TİP_17KAPASÜSLÜ,
		TİP_18VİRGÜL
	}
	
	public enum CÜMLE {
		TİP_01DEĞİŞKENYENİ,
		TİP_02GEÇİCİDEĞİŞKENYENİ,
		TİP_03DEĞİŞKENSİL,
		TİP_04OPERATÖRİŞLEMİ,
		TİP_05FUNKSİYONÇAĞRISI,
		TİP_06DEĞİŞKENATAMA,
		TİP_07ASSEMBLY,
		TİP_08ASSEMBLYDATA,
		TİP_09ASSEMBLYRODATA,
		TİP_10SABİTATAMA
	}
	
	public enum FONKSİYON {
		TİP_01OPERATÖRFONKSİYON,
		TİP_02İSİMLİFONKSİYON,
		TİP_03ANAFONKSİYON
	}
	
	public enum SAKLAÇ {
		RAX, RBX, RCX, RDX, RBP, RSI, RDI, RSP, R8, R9, R10, R11, R12, R13, R14, R15
	}

}
