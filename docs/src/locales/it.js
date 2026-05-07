export default {
  "code": "it",
  "flag": "🇮🇹",
  "name": "Italiano",
  "title": "DashCast — Manuale utente",
  "meta": "v0.1.31 · BYD Seal EU · DiLink 3.0 · Android 10",
  "manualName": "Manuale utente",
  "tocTitle": "📋 Indice",
  "sections": [
    "1. Panoramica",
    "2. Primo avvio — Selezione della lingua",
    "3. Schermata principale",
    "4. Proiettare un'app sul quadro strumenti",
    "5. Durante la proiezione — Pannello di controllo",
    "6. Arrestare la proiezione",
    "7. Impostazioni",
    "8. Menu ⋮ — Strumenti aggiuntivi",
    "9. FAQ & risoluzione dei problemi"
  ],
  "overview": {
    "title": "1. Panoramica",
    "text": "DashCast è un'app Android che consente di proiettare qualsiasi applicazione dallo schermo dell'infotainment sul quadro strumenti digitale del veicolo BYD. Navigazione, musica, video — tutto ciò che è in esecuzione sullo schermo centrale può essere reindirizzato al display del cluster di fronte al conducente.",
    "bullets": [
      "✅ Compatibile con BYD Seal EU (DiLink 3.0, firmware Di3.0 / 6125F)",
      "✅ Nessuna modifica di sistema necessaria",
      "✅ ADB locale via TCP (localhost) — nessun PC necessario una volta configurato",
      "✅ Rilevamento automatico della chiusura dell'app"
    ],
    "note": "💡 Prerequisito: Attivare il debug ADB via TCP in Impostazioni → Opzioni sviluppatore → Debug wireless (o \"ADB tramite rete\"). Questa operazione è necessaria solo una volta. Al primo avvio di DashCast compare una finestra \"Consentire il debug USB?\" — premere Consenti sempre da questo computer."
  },
  "firstLaunch": {
    "title": "2. Primo avvio — Selezione della lingua",
    "text": "Al primo avvio viene mostrata la schermata di benvenuto. Toccare uno dei dieci pulsanti per scegliere la lingua. La scelta viene salvata — questa schermata non comparirà più, salvo se si cambia la lingua dal menu ⋮.",
    "welcomeSubtitle": "Dashboard Controller",
    "welcomeHint": "Choisissez votre langue\nPlease select your language",
    "caption": "Schermata di selezione lingua — visualizzata solo al primo avvio"
  },
  "main": {
    "title": "3. Schermata principale",
    "text": "La schermata principale è composta da due aree: una barra di stato in alto (sfondo blu scuro) e un elenco delle app installate in basso.",
    "status": "① Dashboard: non connesso",
    "buttons": [
      "② Attiva Proiezione",
      "③ Ferma Proiezione",
      "④ Ripristina Dashboard originale",
      "⑤ ⋮",
      "✕",
      "✕",
      "✕"
    ],
    "listTitle": "⑥ App installate",
    "apps": [
      "Maps",
      "YouTube",
      "Spotify"
    ],
    "caption": "Schermata principale — nessuna app proiettata (stato iniziale)",
    "annotations": [
      {
        "tone": "",
        "marker": "①",
        "label": "Stato",
        "text": "Indica lo stato della connessione al cluster. Cambia in \"Dashboard: [Nome app]\" quando un'app è attiva."
      },
      {
        "tone": "",
        "marker": "②",
        "label": "Attiva Proiezione",
        "text": "Stabilisce la connessione con il cluster e lo prepara. Da premere per primo."
      },
      {
        "tone": "red",
        "marker": "③",
        "label": "Ferma Proiezione",
        "text": "Termina la proiezione senza ripristinare il dashboard BYD originale."
      },
      {
        "tone": "green",
        "marker": "④",
        "label": "Ripristina Dashboard originale",
        "text": "Termina la proiezione E ripristina il cluster BYD nativo (velocità, indicatori…)."
      },
      {
        "tone": "gray",
        "marker": "⑤",
        "label": "Menu ⋮",
        "text": "Accesso a Impostazioni, Diagnostica, Report di sistema, Log e cambio lingua."
      },
      {
        "tone": "gray",
        "marker": "⑥",
        "label": "Elenco app",
        "text": "Tutte le app installate. Toccare per proiettare, ✕ per chiudere forzatamente."
      }
    ]
  },
  "projection": {
    "title": "4. Proiettare un'app sul quadro strumenti",
    "steps": [
      "Premere \"Attiva Proiezione\" (pulsante blu). Lo stato cambia in \"Avvio cluster…\". La connessione ADB locale viene stabilita.",
      "Toccare l'app desiderata nell'elenco. DashCast sposta l'app sul display del cluster. Lo stato cambia in \"Dashboard: [Nome app]\".",
      "Il pannello di controllo appare in fondo alla schermata principale."
    ],
    "activeStatus": "Dashboard: Maps ✓",
    "buttons": [
      "Attiva Proiezione",
      "📺 Specchio",
      "Ferma Proiezione",
      "Ripristina Dashboard originale",
      "⋮",
      "← Principale",
      "✕",
      "→ Cluster",
      "✕",
      "⬛⬛ Split",
      "Nascondi ▼"
    ],
    "listTitle": "App installate",
    "apps": [
      "Maps",
      "YouTube"
    ],
    "controlLabel": "Controllo cluster",
    "controlApp": "Maps",
    "mirrorText": "Display attivo sul cluster ✓",
    "caption": "Schermata principale — Maps è proiettata sul quadro strumenti",
    "annotations": []
  },
  "control": {
    "title": "5. Durante la proiezione — Pannello di controllo",
    "intro": "Quando un'app è attiva sul cluster, appare in basso un pannello scuro con tre funzionalità di controllo remoto:",
    "mirror": {
      "title": "5.1 Modalità specchio (📺 Specchio)",
      "text": "Premere 📺 Specchio per visualizzare una copia live del contenuto del cluster all'interno di DashCast. I tocchi vengono trasmessi al cluster.",
      "note": ""
    },
    "split": {
      "title": "5.2 Modalità Split (⬛⬛ Split)",
      "text": "Condividere il display del cluster tra due app:",
      "items": [
        "Schermo intero — Un'app occupa l'intero cluster",
        "⬜⬛ Sinistra (50%) — App principale a sinistra, seconda app a destra",
        "⬛⬜ Destra (50%) — App principale a destra"
      ],
      "extra": ""
    },
    "hide": {
      "title": "5.3 Nascondere il pannello",
      "text": "Premere Nascondi ▼ per comprimere il pannello di controllo."
    }
  },
  "stopping": {
    "title": "6. Arrestare la proiezione",
    "intro": "",
    "table": {
      "headers": [
        "Pulsante",
        "Comportamento",
        "Quando usarlo"
      ],
      "rows": [
        [
          "Ferma Proiezione",
          "Termina la proiezione. Il cluster rimane vuoto (nero).",
          "Per interrompere temporaneamente la visualizzazione."
        ],
        [
          "Ripristina Dashboard originale",
          "Termina la proiezione E ripristina il cluster BYD nativo (velocità, autonomia…).",
          "Al termine dell'utilizzo."
        ]
      ]
    },
    "warning": "⚠️ Se si esce da DashCast senza premere uno di questi pulsanti, la proiezione rimane attiva sul cluster fino al riavvio successivo del servizio."
  },
  "settings": {
    "title": "7. Impostazioni",
    "intro": "Accedere alle impostazioni tramite ⋮ → ⚙️ Impostazioni.",
    "titleLabel": "Impostazioni",
    "clusterTypeLabel": "Tipo di cluster",
    "clusterOptions": [
      "8,8 pollici (cmd=29)",
      "12,3 pollici (cmd=30) — Seal EU",
      "10,25 pollici (cmd=31)"
    ],
    "marginsLabel": "Margini display (overscan)",
    "horizontalMarginLabel": "Sinistra / Destra:",
    "verticalMarginLabel": "Alto / Basso:",
    "applyButton": "Applica ora",
    "resetButton": "Ripristina (80 / 50)",
    "caption": "Pagina Impostazioni",
    "type": {
      "title": "7.1 Tipo di cluster",
      "text": "Selezionare le dimensioni dello schermo del quadro strumenti. Per il BYD Seal EU, selezionare 12,3 pollici (cmd=30)."
    },
    "margins": {
      "title": "7.2 Margini display (overscan)",
      "text": "Regolare i margini per inquadrare perfettamente il contenuto nell'area visibile dello schermo del cluster. Gli schermi curvi hanno spesso bordi fisici che si estendono oltre la superficie di visualizzazione utile.",
      "items": [
        "Sinistra / Destra — Margine orizzontale (0–200 px per lato)",
        "Alto / Basso — Margine verticale (0–200 px in alto e in basso)"
      ],
      "applyText": "",
      "note": "💡 Valori predefiniti consigliati per il Seal EU: Sinistra/Destra = 80 px, Alto/Basso = 50 px."
    }
  },
  "tools": {
    "title": "8. Menu ⋮ — Strumenti aggiuntivi",
    "intro": "",
    "table": {
      "headers": [
        "Opzione",
        "Descrizione"
      ],
      "rows": [
        [
          "⚙️ Impostazioni",
          "Tipo di cluster + regolazione dei margini overscan"
        ],
        [
          "🔧 Diagnostica",
          "Test avanzati per sviluppatori — connessione ADB, display, dimensioni schermo cluster"
        ],
        [
          "📋 Report di sistema",
          "Genera un report completo (display, API BYD, permessi)"
        ],
        [
          "📜 Log",
          "Visualizzatore log in tempo reale — filtraggio per tag/livello, condivisione via email o file"
        ],
        [
          "🌐 Lingua",
          "Torna alla schermata di selezione della lingua"
        ]
      ]
    },
    "logs": null
  },
  "faq": {
    "title": "9. FAQ & risoluzione dei problemi",
    "items": [
      {
        "question": "❓ La finestra \"Consentire il debug USB?\" non compare",
        "answer": "Verificare che il debug ADB via TCP sia attivato nelle opzioni sviluppatore dell'infotainment. Se l'opzione è assente, attivare prima la modalità sviluppatore (toccare il numero di build 7 volte in Informazioni sul dispositivo).",
        "items": []
      },
      {
        "question": "❓ L'app non compare sul cluster dopo la selezione",
        "answer": "",
        "items": [
          "Assicurarsi di aver premuto Attiva Proiezione prima di selezionare l'app.",
          "Alcune app rifiutano di essere avviate su un display secondario. Controllare i Log per il messaggio di errore.",
          "Chiudere e riaprire DashCast, poi ripetere la procedura."
        ]
      },
      {
        "question": "❓ Il contenuto è ritagliato o spostato sul cluster",
        "answer": "Regolare i margini display in ⋮ → Impostazioni. Aumentare Sinistra/Destra in caso di overflow orizzontale, Alto/Basso in caso di overflow verticale. Premere Applica ora per vedere subito il risultato.",
        "items": []
      },
      {
        "question": "❓ I pulsanti \"← Principale\" e \"✕\" rimangono visibili dopo la chiusura dell'app",
        "answer": "DashCast rileva automaticamente la chiusura delle app (tramite monitoraggio /proc). Se l'interfaccia è bloccata, premere Ferma Proiezione per forzare un ripristino.",
        "items": []
      },
      {
        "question": "❓ Dopo un riavvio del veicolo devo riconfigurare tutto?",
        "answer": "No. Il tipo di cluster e i margini overscan vengono salvati. Solo la connessione ADB potrebbe richiedere di premere nuovamente Attiva Proiezione.",
        "items": []
      }
    ]
  },
  "footer": "DashCast · Manuale utente · Italiano · github.com/Kiroha/byd-dashcast"
};
