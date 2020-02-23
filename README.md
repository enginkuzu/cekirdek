# Çekirdek Programlama Dili

### Önemli Not :

Çekirdek derleyicisi 1.0 sürümünde kendi kaynak kodunu derleyebilecek olgunluğa ulaşacak. Şu anda Java ile geliştirilmektedir. Derleme yapabilmek için sisteminizde openjdk-8-jre paketi yüklü olmalıdır. Hata bildirimleri ve dilekleriniz için "Issues" bölümünü kullanabilirsiniz. Katkı yapmakla ilgili detaylar için "Contributing" isimli dökümanı inceleyebilirsiniz.

### Derleme Yapmak :

```
$ cat kodlar/örnek01.kod				# Kaynak kodları ekrana basmak
sayı : i64 < 1;
sayı < sayı + sayı;
sayı.printhn;
$ ./run.sh kodlar/örnek01.kod				# Derleme yapmak
$ file kodlar/örnek01.bin				# Dosya bilgilerini görüntülemek (statically linked ELF64 executable)
kodlar/örnek01.bin: ELF 64-bit LSB executable, x86-64, version 1 (SYSV), statically linked, stripped
$ ls -l kodlar/örnek01.bin				# Derlenmiş dosyanın boyutu (4960 byte)
-rwxr-xr-x 1 engin engin 4960 Şub 23 22:06 kodlar/örnek01.bin
$ kodlar/örnek01.bin					# Uygulamayı çalıştırmak
0000000000000002
```

### Testleri Çalıştırmak :

```
$ ./run-tests.sh 
TEST 1/13 : + test01genel.kod 
TEST 2/13 : + test02açıklama01.kod 
TEST 3/13 : + test03değişkenler.kod 
TEST 4/13 : + test04operatörler.kod 
TEST 5/13 : + test05fonksiyonlar.kod 
TEST 6/13 : + test06tanimsizdegisken.kod 
TEST 7/13 : + test07tekrartanimlama.kod 
TEST 8/13 : + test08bilinmeyendegiskentipi.kod 
TEST 9/13 : + test09değişkenismiuygundeğil.kod 
TEST 10/13 : + test10tanımsızoperatör.kod 
TEST 11/13 : + test11sayıveritipinesığmıyor.kod 
TEST 12/13 : + test12tanımsızfonksiyon.kod 
TEST 13/13 : + test13str.kod 
TESTLER : 13 başarılı, 0 başarısız
```
