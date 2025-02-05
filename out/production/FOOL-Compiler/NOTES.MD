# Code Notes  #
Le classi Lexer e Parser vengono usate per generare l'oggetto ```ParseTree```. Con la classe ```SimpleCalcSTVisitor``` si visita tutto l'albero.
!!! Il metodo ```visit()``` chiama ```accept()``` che chiama ```visitNode()```


### Class: (Simple)-CalcSTVisitor ###
Contiene tutti i metodi ```visit```, uno per ogni regola della grammatica, per calcolare delle espressioni tra interi. 
Ad esempio per la somma di interi si fa: 
```
     return visit( ctx.exp(0) ) + visit( ctx.exp(1) ); 
```

Per ogni regola della grammatica viene creata una classe ```Context```, che rappresenta un nodo del parse tree per quella regola. Quindi abbiamo tipologie di Nodi diversi per ogni regola definita nella grammatica.
<br/>
<br/>

### Class: AST & ASTGenerationSTVisitor ######
```AST.java```: Definisce delle classi statiche, una per ogni tipologia di nodo dell'AST, come ```ProgNode```, ```PlusNode```, ```IntNode```, ecc. Ogni classe ha un metodo accept per accettare una visita da un ```BaseASTVisitor``` (tipo ```PrintASTVisitor```). Il Visitor chiama accept e passa sé stesso. Ricevuto il visitor chiama visit e passa sé stesso come nodo da visitare.

```ASTGenerationSTVisitor```: definisce i metodi per visitare il ParseTree e generare l'AST. Visita i contesti e non i nodi, quindi non passa per il metodo accept. Il risultato delle visite sono di tipo Node, ossia ritorna il tipo di nodo che ha visitato in base al tipo di contesto ricevuto. Questo è l'AST.

Dopo che è stato generato l'AST con le classi statiche definite in ```AST.java```, i suoi nodi vengono visitati usando il metodo accept chimato da ```BaseASTVisitor```, che passa sé stesso ai nodi come visitor. Loro lo ricevono e lo invocano. Gli eredi di ```BaseASTVisitor``` devono invocare ```accept()``` indirettamente tramite ```super()```. 

<b>Ciclo Accept-Visit</b>: c'è un ciclo in cui il visitor passa sé stesso al nodo tramite ```accept()```, e poi il nodo passa sé stesso al visitor tramite ```visit()```. Questo perché conosciamo il tipo del Nodo solo a run-time, il visitor quando riceve il nodo la prima non né conosce il tipo, quindi delega al Nodo, il quale poi chiama su di sé il visitor che ora riceve il tipo di nodo specifico e quindi riesce a discernere su quale metodo usare. Il ```Visitor``` non poteva ricevere subito il nodo specifico perché deve fare da interfaccia generica inizialmente. 

Quando visitavamo i syntax tree utilizzavamo l'implementazione del visitor pattern generata da ANTLR4. Per gli AST abbiamo dovuto implementarlo noi.
<br/>
<br/>

### BaseASTVisitor_S ####
Usiamo i generics perché Visitor diversi avranno tipo di ritorno diversi. ```S``` viene usato come tipo di ritorno di visit. ```S``` sarà 
* ```Integer``` nel ```CalcASTVisitor```;  
* ```Void``` nel ```PrintASTVisitor```; 

```CalcASTVisitor``` semplicemente calcola il risultato di espressioni tra interi, fa i calcoli sulla base del tipo di nodo (eg ```PlusNode```) usando i valori che ritornano i metodi visit (in questo caso ```S = Int```).
<br/>
<br/>


### LetIn Construct ###
Contiene una sequenza di dichiarazioni, variabili o funzioni. 
- ```ProgLetInNode``` : contiene la lista di dichiarazioni (miste)
- ```IdNode```        : contiene l'ID e la STentry
- ```VarNode```       : dichiarazione base di una variabile (id, tipo, exp iniziale)
- ```CallNode```      : contiene la lista di argomenti della chiamata, ed una STentry.
- ```FunNode```       : dichiarazione per una funzione, controlla parentesi e dichiarazioni interne, contiene anche il tipo di ritorno. 

L'ASTGenerator usa i ContextLetIn e FUN per accedere a queste informazioni e creare i nodi di cui sopra. 
<br/>
<br/>


### Symbol Table ###
La Symbol Table è usata per generare l'Enriched AST (EAST), ossia associamo usi di identificatori (variabili o funzioni) a dichiarazioni tramite symbol table, usando le regole di scoping statico.


Static Scoping Rules: 
1. A use of an identifier ```x``` matches the declaration in the most closely enclosing  scope (such that the declaration precedes the use) 
2. Inner scope identifier ```x``` declaration hides ```x``` declared in an outer scope

```SymbolTableASTVisitor``` attacca alla foglia dell'AST che rappresenta l'uso di un identificatore ```x``` la symbol table entry che contiene le informazioni prese dalla dichiarazione di ```x``` (type and nesting level).

Per ogni nuovo ```ProgLetInNode``` o ```FunNode``` visitato viene create una nuova tabella (nuovo nesting level) e viene aggiunta alla lista. I nodi successivi, tipo ```VarNode``` o ```FunNode```, aggiungono le loro entry all'ultima tabella creata. 

La funzione ```stLookup()``` cerca in tutte le tabelle la presenza di un identificatore (da quella più innestata fino a quella globale). L'```entry``` scrive a quale livello è la dichiarazione, non l'uso o la chiamata. 
<br/>
<br/>


### Type Checking ###
Vengono usati dei ```TypeNode``` oltre ad i semplici nodi (eg ```BoolTypeNode```), vengono inseriti nella Symbol Table per rappresentare il tipo dichiarato con l'indentificatore.

- <b>```ArrowTypeNode```</b> = tipo delle funzioni, va solo nelle ```STentry```, le visite normali non ritornano mai questo tipo. 
- <b>```isSubtype()```</b> controlla se le classi sono uguali oppure compatibili (tipo ```IntTypeNode``` e ```BoolTypeNode``` lo sono)
- <b>```TypeCheckVisitor```</b>: visita l'enriched AST determinando i tipi delle espressioni (TypeNode) in modo bottom-up.

Visitando un IntNode si ottiene un ```IntTypeNode```, si percorre l'albero fino ad arrivare alle foglie ```IntNode``` o ```BoolNode```. Così poi questi tipi base vengono propagati verso l'alto per controllare la compatibilità di tipo nelle operazioni, nell'assegnazione di una valore ad una variabile o nella chiamata a funzione.

Casi Utili: 
* ```IF``` --> Lowest Common Ancestor
* ```EQUALS``` --> At least a common child type
<br/>
<br/>
<br/>
<br/>


# To Do 
### Fase 1: Aggiungere Operatori
Operatori "<=", ">=", "||", "&&", "/", "-" e "!":
- Implementare metodi visit in ASTGeneration
- Implementare metodi visit in PrintAST
- Implementare metodi visit in TypeCheck
- Implementare metodi visit in SymbolTableASTVisitor
- Implementare metodi visit in CodeGenerationASTVisitor
<br/>
<br/>
<br/>

### Fase 2: Object Orientation
1. Sintassi & Layouts
     - Aggiungere in ```FOOL.G4``` sintassi classe, metodi, new, invocazione
     - Aggiungere metodi visit per classi e metodi
     - Implementare layout degli oggetti nell'Heap
     - Implementare layout della dispatch table nell'Heap
     - Implementare layout AR nel caso dei metodi

Abbiamo usato classID


2. OO Enriched AST
     - Dichiarazioni: ClassNode, FieldNode(come ParNode), MethodNode(come FunNode)
     - Aggiornare VarNode, FunNode, ParNode con type/getType()
     - Espressioni: CallClassNode, NewNode, EmptyNode
     - Tipi: RefTypeNode, EmptyTypeNode
     - Symbol Table1: calcolare offset in STentry
     - Symbol Table2: STentry per i nomi delle Classi
     - Implementare Virtual Table (scope dentro le classi)
     - Implementare Class Table (mappa classe con sua virtual table)


3. OO Code Generation
     - Implementare Dispatch Table (heap, pointer)
     - Allocare Dispatch Table per ogni classe
     - Implementare MethodNode code generation
<br/>
<br/>
<br/>



### Fase 3: Ereditarietà
- Aggiungere in ```FOOL.G4``` sintassi per estendere una classe
- Aggiugere campo "superID" nell'AST di ClassNode 
- Copiare tipo da Classe da cui si eredita
- Aggiornare Virtual Table per includere campi ereditati
- Implementare overriding in VirtualTable
- Aggiungere func superType in TypeRels che mappa ID di classe in ID di sua super classe
- Estendere isSubtype() in TypeRels estesa per ereditarietà, co-varianza e contro-varianza
- Aggiungere metodi ereditati alla Dispatch Table