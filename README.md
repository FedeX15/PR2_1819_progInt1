## Programmazione 2 - AA 2018-2019
### Primo progetto

Il	progetto	ha	l’obiettivo	di	applicare	i	concetti	e	le	tecniche	di	programmazione	Object-Oriented	esaminate	
durante	il	corso.	Lo	scopo	del	progetto	è	lo	sviluppo	di	un	componente	software	di	supporto	alla	gestione	di	
insiemi di	dati.	
Si	 richiede	 di	 progettare,	 realizzare	 e	 documentare	 la	 collezione **SecureDataContainer<E>**.	
SecureDataContainer<E>	 è	 un	 **contenitore	 di	 oggetti di	 tipo	 E**.	 Intuitivamente	 la collezione	 si	 comporta	
come	**una	specie	Data	Storage per	la	memorizzazione	e	condivisione	di	dati**	(rappresentati	nella	simulazione	
da	oggetti	di	tipo	E).		La	collezione	deve	**garantire	un	meccanismo	di	sicurezza	dei	dati	fornendo	un	proprio	
meccanismo di	gestione	delle	identità degli	 utenti**.	Inoltre,	la collezione deve fornire un **meccanismo di
controllo degli accessi che permette al proprietario del dato di eseguire una restrizione selettiva
dell'accesso ai suoi dati inseriti nella collezione**. Alcuni utenti possono essere autorizzati dal proprietario
ad accedere ai dati, mentre altri non possono accedervi senza autorizzazione.

1. [X] Si	definisca la	**specifica	completa	come interfaccia Java** del	 tipo	di	dato	SecureDataContainer<E>	,	
fornendo le	motivazioni delle	scelte	effettuate.
2. [X] Si	 definisca **l’implementazione	 del	 tipo	 di	 SecureDataContainer<E>	 fornendo almeno	 due	
implementazioni	 che	 utilizzano	 differenti	 strutture	 di	 supporto**.	 In	 entrambi	 i	 casi	 si devono
comunque	descrivere	sia	la	funzione	di	astrazione	sia	l’invariante	di	rappresentazione.	 Si	discutano
le	caratteristiche	delle	due	implementazioni.
3. [X] Per	 valutare	 il	 comportamento	 dell’implementazioni proposte **si	 realizzi una	 batteria	 di	 test	 in	 grado	 di operare,	senza	modifiche	specifiche,	su	entrambe	le	implementazioni	proposte**.
4. [X] Scrivere una relazione di massimo due pagine
