# Simulazione d’esame del 03/06/2015

Si consideri il data-set messo a disposizione dal servizio di Bike Sharing dell’area di San Francisco (Bay Area).	 

Il data-set contiene, nella tabella “station”, le informazioni sui punti di interscambio installati, caratterizzato ciascuno da un nome, dalle coordinate (latitudine e longitudine), il numero di posti-bici disponibili (dockcount), e la città (landmark).

I singoli viaggi compiuti con le biciclette sono rappresentati nella tabella “trip”, che registra la durata (in secondi) di ciascun viaggio, unitamente alle stazioni di partenza ed arrivo, data e ora di presa e riporto, e l’identificativo numerico della bicicletta.

Si intende costruire un’applicazione JavaFX che permetta di interrogare tale base dati, e calcolare informazioni a proposito del funzionamento del servizio.

L’applicazione dovrà svolgere le seguenti funzioni:

1.	Permettere all’utente di selezionare una data ed elencare, per ogni stazione, il numero totale di trip in partenza da tale stazione nella data selezionata, ed il numero dotale di trip in arrivo nella stessa data. Le stazioni devono essere elencate in ordine di latitudine (partendo da Nord ed andando verso Sud). Nel caso in cui nella data selezionata non siano presenti trip, indicarlo con un messaggio d’errore.

2.	Si supponga di essere il responsabile del servizio e di voler valutare attraverso una simulazione il bilanciamento ottimale dei posti disponibili. La data precedentemente inserita rappresenta il giorno da simulare. Tale giorno deve essere feriale (tra lunedì o venerdì), altrimenti la simulazione non deve essere effettuata. Si ipotizzi che all’inizio di tale giornata tutte le stazioni siano piene al K% della loro capienza massima, dove K è un valore tra 0 e 100 indicato dall’utente. Si simuli poi il traffico di biciclette tra le varie stazioni, senza tenere conto dell’identificativo della bicicletta, ma tenendo traccia solamente dell’occupazione delle stazioni. Il simulatore deve calcolare due parametri (in funzione della data specificata e del valore di K): il numero di “prese mancate” (trip in partenza da una stazione priva di bici) ed il numero di “ritorni mancati” (trip in arrivi in una stazione già piena).
 
