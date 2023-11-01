# Searecklocka
# NTP- och Systemtidsjämförelse i Android

Välkommen till detta projekt! Här hittar du en enkel Android-app som visar dig hur du kan hämta tid från en NTP-server och jämföra den med enhetens systemtid. Detta är användbart om du behöver säkerställa att din app har exakt och korrekt tid.

## Vad gör den här appen?

- Hämtar tiden från en NTP-server (i det här fallet använder vi "time.google.com").
- Jämför NTP-tiden med systemtiden.
- Uppdaterar tidvisningen på skärmen varje sekund.
- Ändrar bakgrundsfärgen beroende på om du är ansluten till Wi-Fi eller inte.

**Observera:** För att se systemtiden, stäng av Wi-Fi på din enhet. För att se NTP-tiden, se till att du har en aktiv Wi-Fi-anslutning.

## Så här använder du det

För att använda den här koden i din egen Android-app, följ dessa enkla steg:

1. Klona eller ladda ner koden till din dator.

2. Öppna Android Studio och importera projektet.

3. Kör appen på din Android-enhet eller emulator.

4. Appen kommer att visa den tiden som hämtas från NTP-servern och uppdatera den varje sekund när du har en Wi-Fi-anslutning. Om du stänger av Wi-Fi kommer appen att visa systemtiden istället.

## Anpassa till dina behov

Om du vill använda en annan NTP-server, kan du enkelt ändra serveradressen genom att redigera `NTP_SERVER` i filen `MainActivity.java`.

```java
private final String NTP_SERVER = "time.google.com"; // Byt ut detta med din önskade NTP-serveradress
