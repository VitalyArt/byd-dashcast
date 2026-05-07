export default {
  "code": "tr",
  "flag": "🇹🇷",
  "name": "Türkçe",
  "title": "DashCast — Kullanım Kılavuzu",
  "meta": "v0.1.31 · BYD Seal EU · DiLink 3.0 · Android 10",
  "manualName": "Kullanım Kılavuzu",
  "tocTitle": "📋 İçindekiler",
  "sections": [
    "1. Genel Bakış",
    "2. İlk Çalıştırma — Dil Seçimi",
    "3. Ana Ekran",
    "4. Uygulamayı Gösterge Paneline Yansıtma",
    "5. Yansıtma Sırasında — Kontrol Paneli",
    "6. Yansıtmayı Durdurma",
    "7. Ayarlar",
    "8. ⋮ Menü — Ek Araçlar",
    "9. SSS & Sorun Giderme"
  ],
  "overview": {
    "title": "1. Genel Bakış",
    "text": "DashCast, BYD aracınızın bilgi-eğlence ekranındaki herhangi bir uygulamayı dijital gösterge paneline yansıtmanızı sağlayan bir Android uygulamasıdır. Navigasyon, müzik, video — merkez ekranda çalışan her şey, sürücünün karşısındaki küme ekranına yönlendirilebilir.",
    "bullets": [
      "✅ BYD Seal EU ile uyumlu (DiLink 3.0, Di3.0 / 6125F firmware)",
      "✅ Sistem değişikliği gerektirmez",
      "✅ TCP üzerinden yerel ADB (localhost) — kurulum sonrası PC gerekmez",
      "✅ Uygulama harici olarak kapatıldığında otomatik algılama"
    ],
    "note": "💡 Ön koşul: Bilgi-eğlence sistemi Ayarlar → Geliştirici seçenekleri → Kablosuz hata ayıklama (veya \"Ağ üzerinden ADB\") bölümünden TCP ADB hata ayıklamasını etkinleştirin. Bu işlem yalnızca bir kez yapılır. DashCast'ın ilk çalıştırılmasında \"USB hata ayıklamaya izin verilsin mi?\" penceresi görünür — Bu bilgisayardan her zaman izin ver seçeneğine dokunun."
  },
  "firstLaunch": {
    "title": "2. İlk Çalıştırma — Dil Seçimi",
    "text": "İlk çalıştırmada hoş geldiniz ekranı görünür. Dilinizi seçmek için on düğmeden birine dokunun. Bu tercih kaydedilir — ⋮ menüsünden dil değiştirmediğiniz sürece bu ekran bir daha görünmez.",
    "welcomeSubtitle": "Dashboard Controller",
    "welcomeHint": "Choisissez votre langue\nPlease select your language",
    "caption": "Dil seçim ekranı — yalnızca ilk çalıştırmada görüntülenir"
  },
  "main": {
    "title": "3. Ana Ekran",
    "text": "Ana ekran iki bölümden oluşur: üstte durum çubuğu (koyu mavi arka plan) ve altında yüklü uygulamalar listesi.",
    "status": "① Gösterge: bağlı değil",
    "buttons": [
      "② Projeksiyonu etkinleştir",
      "③ Projeksiyonu durdur",
      "④ Orijinal Dashboard'u geri yükle",
      "⑤ ⋮",
      "✕",
      "✕",
      "✕"
    ],
    "listTitle": "⑥ Yüklü uygulamalar",
    "apps": [
      "Maps",
      "YouTube",
      "Spotify"
    ],
    "caption": "Ana ekran — uygulama yansıtılmıyor (başlangıç durumu)",
    "annotations": [
      {
        "tone": "",
        "marker": "①",
        "label": "Durum",
        "text": "Kümeyle bağlantı durumunu gösterir. Uygulama aktifken \"Gösterge: [Uygulama adı]\" olarak değişir."
      },
      {
        "tone": "",
        "marker": "②",
        "label": "Projeksiyonu etkinleştir",
        "text": "Kümeyle bağlantı kurar ve yansıtmaya hazırlar. İlk önce buna dokunun."
      },
      {
        "tone": "red",
        "marker": "③",
        "label": "Projeksiyonu durdur",
        "text": "BYD göstergesini geri yüklemeden mevcut yansıtmayı sonlandırır."
      },
      {
        "tone": "green",
        "marker": "④",
        "label": "Orijinal Dashboard'u geri yükle",
        "text": "Yansıtmayı sonlandırır VE yerel BYD kümesini geri yükler (hız, göstergeler…)."
      },
      {
        "tone": "gray",
        "marker": "⑤",
        "label": "⋮ Menü",
        "text": "Ayarlar, Tanılama, Sistem Raporu, Günlükler ve dil değiştirme erişimi."
      },
      {
        "tone": "gray",
        "marker": "⑥",
        "label": "Uygulama listesi",
        "text": "Tüm yüklü uygulamalar. Yansıtmak için dokunun, ✕ ile zorla kapatın."
      }
    ]
  },
  "projection": {
    "title": "4. Uygulamayı Gösterge Paneline Yansıtma",
    "steps": [
      "\"Projeksiyonu etkinleştir\" düğmesine (mavi) dokunun. Durum \"Küme başlatılıyor…\" olarak değişir. Yerel ADB bağlantısı kurulur.",
      "İstediğiniz uygulamaya listeden dokunun. DashCast uygulamayı küme ekranına taşır. Durum \"Gösterge: [Uygulama adı]\" olarak değişir.",
      "Kontrol paneli ana ekranın alt kısmında görünür."
    ],
    "activeStatus": "Gösterge: Maps ✓",
    "buttons": [
      "Projeksiyonu etkinleştir",
      "📺 Yansıt",
      "Projeksiyonu durdur",
      "Orijinal Dashboard'u geri yükle",
      "⋮",
      "← Ana",
      "✕",
      "→ Küme",
      "✕",
      "⬛⬛ Böl",
      "Gizle ▼"
    ],
    "listTitle": "Yüklü uygulamalar",
    "apps": [
      "Maps",
      "YouTube"
    ],
    "controlLabel": "Küme kontrolü",
    "controlApp": "Maps",
    "mirrorText": "Kümede ekran aktif ✓",
    "caption": "Ana ekran — Maps gösterge panelinde yansıtılıyor",
    "annotations": []
  },
  "control": {
    "title": "5. Yansıtma Sırasında — Kontrol Paneli",
    "intro": "Kümede bir uygulama aktifken, ana ekranın altında üç uzaktan kumanda özelliğine sahip koyu bir panel görünür:",
    "mirror": {
      "title": "5.1 Yansıtma modu (📺 Yansıt)",
      "text": "Durum çubuğundaki 📺 Yansıt düğmesine dokunarak küme içeriğinin canlı bir kopyasını DashCast içinde görüntüleyin. Dokunma olayları küme ekranına iletilir.",
      "note": ""
    },
    "split": {
      "title": "5.2 Bölünmüş ekran modu (⬛⬛ Böl)",
      "text": "Küme ekranını iki uygulama arasında paylaştırın:",
      "items": [
        "Tam ekran — Bir uygulama kümenin tamamını kaplar",
        "⬜⬛ Sol (%50) — Ana uygulama solda, ikinci uygulama sağda",
        "⬛⬜ Sağ (%50) — Ana uygulama sağda"
      ],
      "extra": ""
    },
    "hide": {
      "title": "5.3 Paneli gizle",
      "text": "Kontrol panelini daraltmak için Gizle ▼ düğmesine dokunun."
    }
  },
  "stopping": {
    "title": "6. Yansıtmayı Durdurma",
    "intro": "",
    "table": {
      "headers": [
        "Düğme",
        "Davranış",
        "Ne zaman kullanılır"
      ],
      "rows": [
        [
          "Projeksiyonu durdur",
          "Yansıtmayı sonlandırır. Küme boş (siyah) kalır.",
          "Görüntüyü geçici olarak durdurmak istediğinizde."
        ],
        [
          "Orijinal Dashboard'u geri yükle",
          "Yansıtmayı sonlandırır VE yerel BYD kümesini geri yükler (hız, menzil…).",
          "Kullanımın sonunda — normal BYD göstergesine dönmek için."
        ]
      ]
    },
    "warning": "⚠️ Bu düğmelerden birine basmadan DashCast'tan çıkarsanız, servis yeniden başlatılana kadar yansıtma kümede aktif kalmaya devam eder."
  },
  "settings": {
    "title": "7. Ayarlar",
    "intro": "Ayarlara ⋮ → ⚙️ Ayarlar üzerinden erişin.",
    "titleLabel": "Ayarlar",
    "clusterTypeLabel": "Küme türü",
    "clusterOptions": [
      "8,8 inç (cmd=29)",
      "12,3 inç (cmd=30) — Seal EU",
      "10,25 inç (cmd=31)"
    ],
    "marginsLabel": "Ekran kenar boşlukları (overscan)",
    "horizontalMarginLabel": "Sol / Sağ:",
    "verticalMarginLabel": "Üst / Alt:",
    "applyButton": "Şimdi uygula",
    "resetButton": "Sıfırla (80 / 50)",
    "caption": "Ayarlar sayfası",
    "type": {
      "title": "7.1 Küme Türü",
      "text": "Gösterge paneli ekranınızın boyutunu seçin. BYD Seal EU için 12,3 inç (cmd=30) seçin."
    },
    "margins": {
      "title": "7.2 Ekran Kenar Boşlukları (Overscan)",
      "text": "İçeriği küme ekranının görünür alanına tam olarak sığdırmak için kenar boşluklarını ayarlayın. Kavisli ekranlarda fiziksel kenarlar çoğunlukla kullanılabilir görüntü alanının dışına taşar.",
      "items": [
        "Sol / Sağ — Yatay kenar boşluğu (her iki tarafta 0–200 px)",
        "Üst / Alt — Dikey kenar boşluğu (üstte ve altta 0–200 px)"
      ],
      "applyText": "",
      "note": "💡 Seal EU için önerilen varsayılan değerler: Sol/Sağ = 80 px, Üst/Alt = 50 px."
    }
  },
  "tools": {
    "title": "8. ⋮ Menü — Ek Araçlar",
    "intro": "",
    "table": {
      "headers": [
        "Seçenek",
        "Açıklama"
      ],
      "rows": [
        [
          "⚙️ Ayarlar",
          "Küme türü + overscan kenar boşluğu ayarı"
        ],
        [
          "🔧 Tanılama",
          "Gelişmiş geliştirici testleri — ADB bağlantısı, ekranlar, küme ekran boyutu"
        ],
        [
          "📋 Sistem Raporu",
          "Tam rapor oluşturur (ekranlar, BYD API'leri, izinler)"
        ],
        [
          "📜 Günlükler",
          "Gerçek zamanlı günlük görüntüleyici — etiket/seviyeye göre filtre, e-posta veya dosya olarak paylaşma"
        ],
        [
          "🌐 Dil",
          "Dil seçim ekranına geri döner"
        ]
      ]
    },
    "logs": null
  },
  "faq": {
    "title": "9. SSS & Sorun Giderme",
    "items": [
      {
        "question": "❓ \"USB hata ayıklamaya izin verilsin mi?\" penceresi görünmüyor",
        "answer": "Bilgi-eğlence sistemi geliştirici ayarlarında TCP ADB hata ayıklamasının etkinleştirildiğinden emin olun. Seçenek yoksa önce geliştirici modunu etkinleştirin (Hakkında bölümündeki yapı numarasına 7 kez dokunun).",
        "items": []
      },
      {
        "question": "❓ Uygulama seçildikten sonra kümede görünmüyor",
        "answer": "",
        "items": [
          "Uygulama seçmeden önce Projeksiyonu etkinleştir düğmesine dokunduğunuzdan emin olun.",
          "Bazı uygulamalar ikincil ekranda başlatılmayı reddeder. Hata mesajı için Günlükleri kontrol edin.",
          "DashCast'ı kapatıp yeniden açın, ardından adımları tekrarlayın."
        ]
      },
      {
        "question": "❓ İçerik kümede kırpılmış veya kaymış görünüyor",
        "answer": "⋮ → Ayarlar'dan Ekran kenar boşluklarını ayarlayın. Yatay taşma varsa Sol/Sağ değerini, dikey taşma varsa Üst/Alt değerini artırın. Sonucu hemen görmek için Şimdi uygula'ya tıklayın.",
        "items": []
      },
      {
        "question": "❓ Uygulama kapatıldıktan sonra \"← Ana\" ve \"✕\" düğmeleri görünmeye devam ediyor",
        "answer": "DashCast uygulama sonlanmalarını otomatik olarak algılar (/proc izleme). Arayüz takılıp kaldıysa sıfırlamayı zorlamak için Projeksiyonu durdur'a dokunun.",
        "items": []
      },
      {
        "question": "❓ Araç yeniden başlatıldıktan sonra her şeyi yeniden yapılandırmam gerekiyor mu?",
        "answer": "Hayır. Küme türü ve overscan kenar boşlukları kaydedilir. Yalnızca ADB bağlantısı, Projeksiyonu etkinleştir düğmesine tekrar dokunmayı gerektirebilir.",
        "items": []
      }
    ]
  },
  "footer": "DashCast · Kullanım Kılavuzu · Türkçe · github.com/Kiroha/byd-dashcast"
};
