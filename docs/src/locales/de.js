export default {
  "code": "de",
  "flag": "🇩🇪",
  "name": "Deutsch",
  "title": "DashCast — Bedienungsanleitung",
  "meta": "v0.1.31 · BYD Seal EU · DiLink 3.0 · Android 10",
  "manualName": "Bedienungsanleitung",
  "tocTitle": "📋 Inhaltsverzeichnis",
  "sections": [
    "1. Übersicht",
    "2. Erster Start — Sprachauswahl",
    "3. Hauptbildschirm",
    "4. App auf das Kombiinstrument projizieren",
    "5. Während der Projektion — Steuerungsfeld",
    "6. Projektion beenden",
    "7. Einstellungen",
    "8. ⋮ Menü — Weitere Werkzeuge",
    "9. FAQ & Fehlerbehebung"
  ],
  "overview": {
    "title": "1. Übersicht",
    "text": "DashCast ist eine Android-App, mit der beliebige Apps vom Infotainment-Bildschirm auf das digitale Kombiinstrument Ihres BYD-Fahrzeugs projiziert werden können. Navigation, Musik, Videos – alles, was auf dem Zentraldisplay läuft, kann auf das fahrerseitige Cluster-Display umgeleitet werden.",
    "bullets": [
      "✅ Kompatibel mit BYD Seal EU (DiLink 3.0, Firmware Di3.0 / 6125F)",
      "✅ Keine Systemmodifikation erforderlich",
      "✅ Lokales ADB über TCP (localhost) — nach der Einrichtung kein PC mehr nötig",
      "✅ Automatische Erkennung, wenn eine App extern geschlossen wird"
    ],
    "note": "💡 Voraussetzung: Aktivieren Sie ADB-Debugging über TCP in Einstellungen → Entwickleroptionen → Kabelloses Debugging (oder „ADB über Netzwerk\"). Dieser Schritt ist einmalig. Beim ersten Start von DashCast erscheint ein Dialog „USB-Debugging zulassen?\" — tippen Sie auf Von diesem Computer immer zulassen."
  },
  "firstLaunch": {
    "title": "2. Erster Start — Sprachauswahl",
    "text": "Beim ersten Start erscheint der Willkommensbildschirm. Tippen Sie auf eine der zehn Schaltflächen, um Ihre Sprache auszuwählen. Diese Auswahl wird gespeichert — Sie sehen diesen Bildschirm nur noch, wenn Sie die Sprache im ⋮-Menü ändern.",
    "welcomeSubtitle": "Dashboard Controller",
    "welcomeHint": "Choisissez votre langue\nPlease select your language",
    "caption": "Sprachauswahl — nur beim ersten Start angezeigt"
  },
  "main": {
    "title": "3. Hauptbildschirm",
    "text": "Der Hauptbildschirm besteht aus zwei Bereichen: einer Statusleiste oben (dunkelblauer Hintergrund) und einer Liste installierter Apps darunter.",
    "status": "① Dashboard: nicht verbunden",
    "buttons": [
      "② Projektion aktivieren",
      "③ Projektion stoppen",
      "④ Original-Dashboard wiederherstellen",
      "⑤ ⋮",
      "✕",
      "✕",
      "✕"
    ],
    "listTitle": "⑥ Installierte Apps",
    "apps": [
      "Maps",
      "YouTube",
      "Spotify"
    ],
    "caption": "Hauptbildschirm — keine App projiziert (Ausgangszustand)",
    "annotations": [
      {
        "tone": "",
        "marker": "①",
        "label": "Status",
        "text": "Zeigt den Verbindungsstatus zum Cluster. Wechselt zu „Dashboard: [App-Name]\", wenn eine App aktiv ist."
      },
      {
        "tone": "",
        "marker": "②",
        "label": "Projektion aktivieren",
        "text": "Stellt die Verbindung zum Cluster her. Zuerst antippen."
      },
      {
        "tone": "red",
        "marker": "③",
        "label": "Projektion stoppen",
        "text": "Beendet die aktuelle Projektion, stellt das BYD-Dashboard aber nicht wieder her."
      },
      {
        "tone": "green",
        "marker": "④",
        "label": "Original-Dashboard wiederherstellen",
        "text": "Beendet die Projektion UND stellt das native BYD-Cluster wieder her (Geschwindigkeit, Anzeigen…)."
      },
      {
        "tone": "gray",
        "marker": "⑤",
        "label": "⋮ Menü",
        "text": "Zugriff auf Einstellungen, Diagnose, Systembericht, Logs und Sprachwechsel."
      },
      {
        "tone": "gray",
        "marker": "⑥",
        "label": "App-Liste",
        "text": "Alle installierten Apps. Antippen zum Projizieren, ✕ zum Schließen."
      }
    ]
  },
  "projection": {
    "title": "4. App auf das Kombiinstrument projizieren",
    "steps": [
      "Tippen Sie auf „Projektion aktivieren\" (blaue Schaltfläche). Der Status wechselt zu „Cluster wird gestartet…\". Die lokale ADB-Verbindung wird hergestellt.",
      "Tippen Sie auf die gewünschte App in der Liste. DashCast verschiebt die App auf das Cluster-Display. Der Status wechselt zu „Dashboard: [App-Name]\".",
      "Das Steuerungsfeld erscheint am unteren Bildschirmrand."
    ],
    "activeStatus": "Dashboard: Maps ✓",
    "buttons": [
      "Projektion aktivieren",
      "📺 Spiegel",
      "Projektion stoppen",
      "Original-Dashboard wiederherstellen",
      "⋮",
      "← Haupt",
      "✕",
      "→ Cluster",
      "✕",
      "⬛⬛ Split",
      "Ausblenden ▼"
    ],
    "listTitle": "Installierte Apps",
    "apps": [
      "Maps",
      "YouTube"
    ],
    "controlLabel": "Cluster-Steuerung",
    "controlApp": "Maps",
    "mirrorText": "Anzeige aktiv auf Cluster ✓",
    "caption": "Hauptbildschirm — Maps wird auf dem Kombiinstrument angezeigt",
    "annotations": []
  },
  "control": {
    "title": "5. Während der Projektion — Steuerungsfeld",
    "intro": "Wenn eine App auf dem Cluster aktiv ist, erscheint unten ein dunkles Steuerungsfeld mit drei Fernsteuerungsfunktionen:",
    "mirror": {
      "title": "5.1 Spiegelmodus (📺 Spiegel)",
      "text": "Tippen Sie auf 📺 Spiegel, um eine Live-Kopie des Cluster-Inhalts in DashCast anzuzeigen. Berührungsereignisse werden an das Cluster weitergeleitet.",
      "note": ""
    },
    "split": {
      "title": "5.2 Split-Modus (⬛⬛ Split)",
      "text": "Teilen Sie das Cluster-Display zwischen zwei Apps auf:",
      "items": [
        "Vollbild — Eine App belegt das gesamte Cluster",
        "⬜⬛ Links (50 %) — Haupt-App links, zweite App rechts",
        "⬛⬜ Rechts (50 %) — Haupt-App rechts"
      ],
      "extra": ""
    },
    "hide": {
      "title": "5.3 Panel ausblenden",
      "text": "Tippen Sie auf Ausblenden ▼, um das Steuerungsfeld einzuklappen."
    }
  },
  "stopping": {
    "title": "6. Projektion beenden",
    "intro": "",
    "table": {
      "headers": [
        "Schaltfläche",
        "Verhalten",
        "Wann verwenden"
      ],
      "rows": [
        [
          "Projektion stoppen",
          "Beendet die Projektion. Cluster bleibt leer (schwarz).",
          "Um die Anzeige vorübergehend zu stoppen."
        ],
        [
          "Original-Dashboard wiederherstellen",
          "Beendet die Projektion UND stellt das native BYD-Cluster wieder her (Geschwindigkeit, Reichweite…).",
          "Am Ende der Nutzung."
        ]
      ]
    },
    "warning": "⚠️ Wenn Sie DashCast beenden, ohne eine dieser Schaltflächen zu drücken, bleibt die Projektion bis zum nächsten Neustart des Dienstes aktiv."
  },
  "settings": {
    "title": "7. Einstellungen",
    "intro": "Öffnen Sie die Einstellungen über ⋮ → ⚙️ Einstellungen.",
    "titleLabel": "Einstellungen",
    "clusterTypeLabel": "Cluster-Typ",
    "clusterOptions": [
      "8,8 Zoll (cmd=29)",
      "12,3 Zoll (cmd=30) — Seal EU",
      "10,25 Zoll (cmd=31)"
    ],
    "marginsLabel": "Anzeigeränder (Overscan)",
    "horizontalMarginLabel": "Links / Rechts:",
    "verticalMarginLabel": "Oben / Unten:",
    "applyButton": "Jetzt anwenden",
    "resetButton": "Zurücksetzen (80 / 50)",
    "caption": "Einstellungsseite",
    "type": {
      "title": "7.1 Cluster-Typ",
      "text": "Wählen Sie die Bildschirmgröße Ihres Kombiinstruments. Für den BYD Seal EU wählen Sie 12,3 Zoll (cmd=30)."
    },
    "margins": {
      "title": "7.2 Anzeigeränder (Overscan)",
      "text": "Passen Sie die Ränder an, um den Inhalt in der sichtbaren Fläche des Cluster-Displays einzurahmen. Gekrümmte Displays haben häufig physische Ränder, die über die nutzbare Anzeigefläche hinausgehen.",
      "items": [
        "Links / Rechts — Horizontaler Rand (0–200 px auf jeder Seite)",
        "Oben / Unten — Vertikaler Rand (0–200 px oben und unten)"
      ],
      "applyText": "",
      "note": "💡 Empfohlene Standardwerte für den Seal EU: Links/Rechts = 80 px, Oben/Unten = 50 px."
    }
  },
  "tools": {
    "title": "8. ⋮ Menü — Weitere Werkzeuge",
    "intro": "",
    "table": {
      "headers": [
        "Option",
        "Beschreibung"
      ],
      "rows": [
        [
          "⚙️ Einstellungen",
          "Cluster-Typ + Overscan-Randanpassung"
        ],
        [
          "🔧 Diagnose",
          "Erweiterte Entwicklertests — ADB-Verbindung, Displays, Cluster-Bildschirmgröße"
        ],
        [
          "📋 Systembericht",
          "Erstellt einen vollständigen Bericht (Displays, BYD-APIs, Berechtigungen)"
        ],
        [
          "📜 Protokoll",
          "Echtzeit-Protokollansicht — Filter nach Tag/Ebene, Teilen per E-Mail oder Datei"
        ],
        [
          "🌐 Sprache",
          "Kehrt zur Sprachauswahl zurück"
        ]
      ]
    },
    "logs": null
  },
  "faq": {
    "title": "9. FAQ & Fehlerbehebung",
    "items": [
      {
        "question": "❓ Der Dialog „USB-Debugging zulassen?\" erscheint nicht",
        "answer": "Stellen Sie sicher, dass ADB-Debugging über TCP in den Entwickleroptionen des Infotainmentsystems aktiviert ist. Falls die Option fehlt, aktivieren Sie zuerst den Entwicklermodus (7× auf die Build-Nummer in „Über dieses Gerät\" tippen).",
        "items": []
      },
      {
        "question": "❓ Die App erscheint nach der Auswahl nicht auf dem Cluster",
        "answer": "",
        "items": [
          "Vergewissern Sie sich, dass Sie Projektion aktivieren angetippt haben, bevor Sie eine App auswählen.",
          "Einige Apps verweigern den Start auf einem sekundären Display. Prüfen Sie das Protokoll auf Fehlermeldungen.",
          "Schließen und öffnen Sie DashCast neu, und wiederholen Sie die Schritte."
        ]
      },
      {
        "question": "❓ Der Inhalt ist auf dem Cluster abgeschnitten oder verschoben",
        "answer": "Passen Sie die Anzeigeränder unter ⋮ → Einstellungen an. Erhöhen Sie Links/Rechts bei horizontalem Überlauf, Oben/Unten bei vertikalem Überlauf. Klicken Sie auf Jetzt anwenden, um das Ergebnis sofort zu sehen.",
        "items": []
      },
      {
        "question": "❓ Die Schaltflächen „← Haupt\" und „✕\" bleiben nach dem Schließen der App sichtbar",
        "answer": "DashCast erkennt das Schließen von Apps automatisch (über /proc-Überwachung). Falls die Oberfläche hängt, tippen Sie auf Projektion stoppen, um einen Reset zu erzwingen.",
        "items": []
      },
      {
        "question": "❓ Muss ich nach einem Fahrzeugneustart alles neu einrichten?",
        "answer": "Nein. Cluster-Typ und Overscan-Ränder werden gespeichert. Nur die ADB-Verbindung erfordert möglicherweise erneutes Antippen von Projektion aktivieren.",
        "items": []
      }
    ]
  },
  "footer": "DashCast · Bedienungsanleitung · Deutsch · github.com/Kiroha/byd-dashcast"
};
