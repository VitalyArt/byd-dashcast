export default {
  "code": "uz",
  "flag": "🇺🇿",
  "name": "Oʻzbekcha",
  "title": "DashCast — Foydalanuvchi qoʻllanmasi",
  "meta": "v0.1.31 · BYD Seal EU · DiLink 3.0 · Android 10",
  "manualName": "Foydalanuvchi qoʻllanmasi",
  "tocTitle": "📋 Mundarija",
  "sections": [
    "1. Umumiy koʻrinish",
    "2. Birinchi ishga tushirish — til tanlash",
    "3. Asosiy ekran",
    "4. Ilovani asboblar paneliga chiqarish",
    "5. Proyeksiya vaqtida — boshqaruv paneli",
    "6. Proyeksiyani toʻxtatish",
    "7. Sozlamalar",
    "8. ⋮ menyu — qoʻshimcha vositalar",
    "9. FAQ va nosozliklarni bartaraf etish"
  ],
  "overview": {
    "title": "1. Umumiy koʻrinish",
    "text": "DashCast — multimedia ekranidagi istalgan ilovani BYD avtomobilingizning raqamli asboblar paneliga chiqarishga imkon beruvchi Android ilovasi. Navigatsiya, musiqa, video — markaziy ekranda ishlayotgan hamma narsani haydovchi oldidagi displeyga yoʻnaltirish mumkin.",
    "bullets": [
      "✅ BYD Seal EU bilan mos (DiLink 3.0, Di3.0 / 6125F firmware)",
      "✅ Tizimni oʻzgartirish talab qilinmaydi",
      "✅ TCP orqali lokal ADB (localhost) — sozlangandan keyin kompyuter kerak emas",
      "✅ Ilova tashqaridan yopilganini avtomatik aniqlaydi"
    ],
    "note": "💡 Talab: Sozlamalar → Dasturchi parametrlari → Simsiz debugging (yoki “ADB over network”) boʻlimida ADB TCP debugging ni yoqing. Bu bir marta qilinadi. DashCast birinchi ishga tushganda “Allow USB debugging?” oynasi chiqadi — Always allow from this computer ni bosing."
  },
  "firstLaunch": {
    "title": "2. Birinchi ishga tushirish — til tanlash",
    "text": "Birinchi ishga tushganda xush kelibsiz ekrani koʻrsatiladi. Tilni tanlash uchun oʻnta tugmadan birini bosing. Tanlov saqlanadi — tilni ⋮ menyusidan oʻzgartirmaguningizcha bu ekran qayta chiqmaydi.",
    "welcomeSubtitle": "Asboblar paneli boshqaruvchisi",
    "welcomeHint": "Tilni tanlang\nPlease select your language",
    "caption": "Til tanlash ekrani — faqat birinchi ishga tushganda koʻrsatiladi"
  },
  "main": {
    "title": "3. Asosiy ekran",
    "text": "Asosiy ekran ikki qismdan iborat: yuqorida holat satri (toʻq koʻk) va pastda oʻrnatilgan ilovalar roʻyxati.",
    "status": "① Asboblar paneli: ulanmagan",
    "buttons": [
      "② Proyeksiyani yoqish",
      "③ Proyeksiyani toʻxtatish",
      "④ Asl panelni tiklash",
      "⑤ ⋮",
      "✕",
      "✕",
      "✕"
    ],
    "listTitle": "⑥ Oʻrnatilgan ilovalar",
    "apps": [
      "Xaritalar",
      "YouTube",
      "Spotify"
    ],
    "caption": "Asosiy ekran — ilova hali chiqarilmagan (boshlangʻich holat)",
    "annotations": [
      {
        "tone": "",
        "marker": "①",
        "label": "Holat",
        "text": "klaster ulanish holatini koʻrsatadi. Ilova faol boʻlganda «Asboblar paneli: [Ilova nomi]» ga oʻzgaradi."
      },
      {
        "tone": "",
        "marker": "②",
        "label": "Proyeksiyani yoqish",
        "text": "klasterni ulaydi va ilovalarni chiqarishga tayyorlaydi. Avval shu tugmani bosing."
      },
      {
        "tone": "red",
        "marker": "③",
        "label": "Proyeksiyani toʻxtatish",
        "text": "joriy proyeksiyani BYD asl panelini tiklamasdan tugatadi."
      },
      {
        "tone": "green",
        "marker": "④",
        "label": "Asl panelni tiklash",
        "text": "proyeksiyani tugatadi VA BYD klasterini qaytaradi (tezlik, shkala va h. k.)."
      },
      {
        "tone": "gray",
        "marker": "⑤",
        "label": "⋮ menyu",
        "text": "sozlamalar, diagnostika, tizim hisoboti, jurnallar va til almashtirishga kirish."
      },
      {
        "tone": "gray",
        "marker": "⑥",
        "label": "Ilovalar roʻyxati",
        "text": "barcha oʻrnatilgan ilovalar. Chiqarish uchun ilovani bosing yoki majburiy yopish uchun ✕ ni bosing."
      }
    ]
  },
  "projection": {
    "title": "4. Ilovani asboblar paneliga chiqarish",
    "steps": [
      "«Proyeksiyani yoqish» tugmasini bosing (koʻk tugma). Holat «Klaster ishga tushirilmoqda…» ga oʻzgaradi. Lokal ADB ulanishi oʻrnatiladi va klaster proyeksiya rejimiga oʻtadi.",
      "Roʻyxatdagi kerakli ilovani bosing. DashCast ilovani klaster displeyiga koʻchiradi. Holat «Asboblar paneli: [Ilova nomi]» ga oʻzgaradi.",
      "Ekran pastida boshqaruv paneli paydo boʻladi; u orqali chiqarilgan ilova bilan asosiy ekrandan ishlash mumkin."
    ],
    "activeStatus": "Asboblar paneli: Xaritalar ✓",
    "buttons": [
      "Proyeksiyani yoqish",
      "📺 Mirror",
      "Proyeksiyani toʻxtatish",
      "Asl panelni tiklash",
      "⋮",
      "← Asosiy",
      "✕",
      "→ Klaster",
      "✕",
      "→ Klaster",
      "✕",
      "⬛⬛ Ajratish",
      "Yashirish ▼"
    ],
    "listTitle": "Oʻrnatilgan ilovalar",
    "apps": [
      "Xaritalar",
      "YouTube",
      "Spotify"
    ],
    "controlLabel": "Klaster boshqaruvi",
    "controlApp": "Xaritalar",
    "mirrorText": "Klasterda faol tasvir ✓",
    "caption": "Asosiy ekran — Xaritalar asboblar paneliga chiqarilgan",
    "annotations": []
  },
  "control": {
    "title": "5. Proyeksiya vaqtida — boshqaruv paneli",
    "intro": "Ilova klasterda faol boʻlganda, asosiy ekran pastida uchta masofadan boshqarish funksiyasiga ega qorongʻi panel paydo boʻladi:",
    "mirror": {
      "title": "5.1 Koʻzgu (📺 Koʻzgu)",
      "text": "Klasterdagi kontentning jonli nusxasini DashCast ichida koʻrish uchun holat satrida 📺 Koʻzgu ni bosing. Nusxa bilan sensor orqali ishlash mumkin — hodisalar klaster displeyiga uzatiladi.",
      "note": "Koʻzgu displeyni olish uchun SurfaceControl dan foydalanadi. Agar u mavjud boʻlmasa, har 2 soniyada skrinshot olinadigan zaxira rejim ishlatiladi."
    },
    "split": {
      "title": "5.2 Ajratish rejimi (⬛⬛ Ajratish)",
      "text": "Klasterni ikki ilova oʻrtasida boʻlish uchun ⬛⬛ Ajratish ni bosing:",
      "items": [
        "Toʻliq ekran — bitta ilova butun klasterni egallaydi",
        "⬜⬛ Chap (50%) — asosiy ilova chapda, ikkinchi ilova oʻngda",
        "⬛⬜ Oʻng (50%) — asosiy ilova oʻngda"
      ],
      "extra": ""
    },
    "hide": {
      "title": "5.3 Panelni yashirish",
      "text": "Yashirish ▼ tugmasini bosib boshqaruv panelini yigʻing va toʻliq ilovalar roʻyxatini koʻring."
    }
  },
  "stopping": {
    "title": "6. Proyeksiyani toʻxtatish",
    "intro": "",
    "table": {
      "headers": [
        "Tugma",
        "Xatti-harakat",
        "Qachon ishlatish"
      ],
      "rows": [
        [
          "Proyeksiyani toʻxtatish",
          "Proyeksiyani tugatadi. Klaster boʻsh qoladi.",
          "Chiqarishni vaqtincha toʻxtatish kerak boʻlganda."
        ],
        [
          "Asl panelni tiklash",
          "Proyeksiyani tugatadi VA BYD asl klasterini qaytaradi (tezlik, yurish zaxirasi, shkala…).",
          "Foydalanish oxirida — odatiy BYD paneliga qaytish uchun."
        ]
      ]
    },
    "warning": "⚠️ Agar ushbu tugmalardan birini bosmasdan DashCastdan chiqsangiz, xizmat keyingi qayta ishga tushirilguncha proyeksiya klasterda faol qoladi."
  },
  "settings": {
    "title": "7. Sozlamalar",
    "intro": "Sozlamalarni ⋮ → ⚙️ Sozlamalar orqali oching.",
    "titleLabel": "Sozlamalar",
    "clusterTypeLabel": "Klaster turi",
    "clusterOptions": [
      "8.8 inches (cmd=29)",
      "12.3 inches (cmd=30) — Seal EU",
      "10.25 inches (cmd=31)"
    ],
    "marginsLabel": "Displey chetlari (overscan)",
    "horizontalMarginLabel": "Chap / Oʻng:",
    "verticalMarginLabel": "Yuqori / Pastki:",
    "applyButton": "Hozir qoʻllash",
    "resetButton": "Reset (80 / 50)",
    "caption": "Sozlamalar",
    "type": {
      "title": "7.1 Klaster turi",
      "text": "Asboblar paneli ekranining oʻlchamini tanlang. BYD Seal EU uchun 12,3 dyuym (cmd=30) ni tanlang."
    },
    "margins": {
      "title": "7.2 Displey chetlari (Overscan)",
      "text": "Kontentni klaster ekranining koʻrinadigan qismiga sigʻdirish uchun chetlarni sozlang. Egri oynali klasterlarda jismoniy chetlar foydali maydondan tashqariga chiqishi mumkin.",
      "items": [
        "Chap / Oʻng — gorizontal chet (har tomonda 0–200 px)",
        "Yuqori / Pastki — vertikal chet (yuqori va pastda 0–200 px)"
      ],
      "applyText": "Agar ilova hozir chiqarilgan boʻlsa, natijani darhol koʻrish uchun Hozir qoʻllash ni bosing. Qiymatlar sessiyalar orasida saqlanadi.",
      "note": "💡 Seal EU uchun tavsiya etilgan qiymatlar: Chap/Oʻng = 80 px, Yuqori/Pastki = 50 px."
    }
  },
  "tools": {
    "title": "8. ⋮ menyu — qoʻshimcha vositalar",
    "intro": "",
    "table": {
      "headers": [
        "Band",
        "Tavsif"
      ],
      "rows": [
        [
          "⚙️ Sozlamalar",
          "Klaster turi + overscan chetlarini sozlash"
        ],
        [
          "🔧 Diagnostika",
          "Dasturchi uchun kengaytirilgan testlar — ADB ulanishi, displeylar, klaster ekran oʻlchami"
        ],
        [
          "📋 Tizim hisoboti",
          "Toʻliq hisobot yaratadi (displeylar, BYD API, ruxsatlar) — qoʻllab-quvvatlash uchun foydali"
        ],
        [
          "📜 Jurnallar",
          "Jurnalni real vaqtda koʻrish — teg/daraja boʻyicha filtr, email yoki fayl bilan yuborish"
        ],
        [
          "🌐 Til",
          "Til tanlash ekraniga qaytaradi"
        ]
      ]
    },
    "logs": null
  },
  "faq": {
    "title": "9. FAQ va nosozliklarni bartaraf etish",
    "items": [
      {
        "question": "❓ “Allow USB debugging?” oynasi chiqmaydi",
        "answer": "ADB TCP debugging multimedia dasturchi sozlamalarida yoqilganini tekshiring. Agar band yoʻq boʻlsa, avval dasturchi rejimini yoqing (About boʻlimidagi build number ni 7 marta bosing).",
        "items": []
      },
      {
        "question": "❓ Tanlangandan keyin ilova klasterda koʻrinmaydi",
        "answer": "",
        "items": [
          "Ilovani tanlashdan oldin Proyeksiyani yoqish tugmasini bosganingizga ishonch hosil qiling.",
          "Baʼzi ilovalar ikkinchi displeyda ishga tushishga ruxsat bermaydi. Xato xabarini jurnaldan tekshiring.",
          "DashCastni yopib qayta oching, keyin ketma-ketlikni takrorlang."
        ]
      },
      {
        "question": "❓ Kontent klasterda qirqilgan yoki siljigan",
        "answer": "Displey chetlari ni ⋮ → Sozlamalar orqali sozlang. Gorizontal chiqib ketsa Chap/Oʻng qiymatini, vertikal chiqib ketsa Yuqori/Pastki qiymatini oshiring. Natijani darhol koʻrish uchun Hozir qoʻllash ni bosing.",
        "items": []
      },
      {
        "question": "❓ Ilova yopilgandan keyin «← Asosiy» va «✕» tugmalari koʻrinib qoladi",
        "answer": "DashCast ilova tugaganini avtomatik aniqlaydi (/proc monitoring orqali). Interfeys qotib qolsa, holatni majburan tozalash uchun Proyeksiyani toʻxtatish ni bosing.",
        "items": []
      },
      {
        "question": "❓ Avtomobil qayta ishga tushgandan keyin hammasini qayta sozlash kerakmi?",
        "answer": "Yoʻq. Klaster turi va overscan chetlari saqlanadi. Oxirgi chiqarilgan ilova ham eslab qolinadi. Faqat ADB ulanishi uchun Proyeksiyani yoqish tugmasini qayta bosish kerak boʻlishi mumkin.",
        "items": []
      }
    ]
  },
  "footer": "DashCast · Foydalanuvchi qoʻllanmasi · Oʻzbekcha · github.com/Kiroha/byd-dashcast"
};
