# getir
 
Bu proje getir için özel olarak hazırlanmıştır.

Projede yazılım dili olarak java, Mobil otomasyon için appium, BDD olarak da gauge kullanılmıştır. Test uygulaması olarak: https://bit.ly/3719CDE bu linkten faydalanılmıştır. 

Gauge'in kullanılabilmesi için cihazınıza gauge yüklemeniz gerekmektedir. Mac için brew kullanılabilir, terminalde "brew install gauge" komutu ile gauge yükleyebilirsiniz. gauge -v ile java versiyonu kontrol edip gauge install java --version 0.7.15 ile pom.xml de kullanılan versiyona güncelleyebilirsiniz.

Windows da ise npm kullanarak "npm install -g @getgauge/cli" komutu ile yükleyebilirsiniz.

Bu işlemlerden sonra projenin içinde bulunan spec dosyalarını çalıştırarak caselerimiz koşabiliriz.

Eğer cihazınızda mvn yüklü ise "mvn gauge:execute -DspecsDir=specs" komutu ile specs dosyasında bulunan bütün caseleri koşabilirsiniz veya case özelinde "mvn gauge:execute -DspecsDir=specs -Dscenario="Scenario Name"" komutu ile istediğiniz senaryoyu koşabilirsiniz.

