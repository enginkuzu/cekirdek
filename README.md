# Çekirdek Programlama Dili

### Geliştirmelerle ilgili son durum :

Örnek kaynak kodlar derlenebiliyor olsa da şu anda çok sayıda altyapı çalışması yapılmakta. Pek çok kısımda hatalar bulunabilir. İlk kararlı sürüm için v0.1'i bekleyiniz...

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
TEST : (1/7) test01genel.kod +
TEST : (2/7) test02açıklama01.kod +
TEST : (3/7) test03değişkenler.kod +
TEST : (4/7) test04operatörler.kod +
TEST : (5/7) test05fonksiyonlar.kod +
TEST : (6/7) test06tanimsizdegisken.kod +
TEST : (7/7) test07tekrartanimlama.kod +
TESTLER : 7 başarılı, 0 başarısız
```
