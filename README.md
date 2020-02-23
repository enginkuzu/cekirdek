# Çekirdek Programlama Dili

### Önemli Not :

Proje (v1.0 sürümüne kadar) Java ile geliştirildiği için derleme yapmak için sisteminizde openjdk-8-jre paketi yüklü olmalıdır.

### Derleme Yapmak :

```
$ cat kodlar/örnek01.kod				# Kaynak kodları ekrana basmak
sayı : i64 < 1;
sayı < sayı + sayı;
sayı.printhn;
$ ./run.sh kodlar/örnek01.kod				# Derleme yapmak
$ ls -l kodlar/örnek01.bin				# Derlenmiş dosyayı listelemek (4728 byte)
-rwxr-xr-x 1 engin engin 4728 Şub  8 14:51 kodlar/örnek01.bin
$ kodlar/örnek01.bin					# Çalıştırmak
0000000000000002
```

### Testleri Çalıştırmak :

```
$ ./run-tests.sh 
TEST : (1/12) test01genel.kod +
TEST : (2/12) test02açıklama01.kod +
TEST : (3/12) test03değişkenler.kod +
TEST : (4/12) test04operatörler.kod +
TEST : (5/12) test05fonksiyonlar.kod +
TEST : (6/12) test06tanimsizdegisken.kod +
TEST : (7/12) test07tekrartanimlama.kod +
TEST : (8/12) test08bilinmeyendegiskentipi.kod +
TEST : (9/12) test09değişkenismiuygundeğil.kod +
TEST : (10/12) test10tanımsızoperatör.kod +
TEST : (11/12) test11sayıveritipinesığmıyor.kod +
TEST : (12/12) test12tanımsızfonksiyon.kod +
TESTLER : 12 başarılı, 0 başarısız
```
