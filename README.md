# Trade-Generator
Generateur de trades

### TODO
- Check XML Parsing (no file/missing attributes/wrong data)
- Tree organisation : better split between static/setting/algo
- 3  Packages to set : Referential/ Trade event/ Generals
- Select Product according to currency and country
- Singleton for Generals and Referential
- For sell, Choose a trader according to currency, type instrument and portefeuille
- Quantity null for action---> dont create TradeEvent
- GetRandom to override for random under Criteria( example: buy product respect instrument type/ devise
- add to book: list of product owned 
- optimiser:exemple: Math.random  -> Random //parallelise.
- xml en dur
- un fichier par jour
- volumetry a changer
- manque book +portfolio
- interet du ratio
- date a implementer
- pouvoir faire un bilan pour chaque jour
(- re-think equity generation...)

Notes rush2

Normalisation :

amount au lieu de montant
partloan devient partsell
tolerance rep et volumetry tol = meme chose -> mettre dans montant budget
budget tolerance partout au lieu de tolerance rep
normaliser les champs, garder une certaine coh�rence, mettre tout en anglais
businessUnit plutot que business
pour les reports, mettre � chaque fois le m�me nombre de chiffre
meme format yyyy-MM-dd
price output 0
montants 2 chiffres apr�s la virgule et . pas ,
visibilit� dans TradeEvent des acc�s -> revoir l'encapsulation (getters setters) -> cf mod�le Pierre
sur les rapports, revoir typographie
nombre de champs toujours les m�me

Norme classes :

ne pas m�langer objet m�tier et output -> TradeEvent.java


Performances :

Ajouter des output ne doit pas ralentir la production -> parall�lisation des outputs , d�coupage entre la production et l'�criture (output)
si un output crash il ne doit pas faire crasher les autres ...
c'est l'instrument qui donne la vitesse de production du trade, pas l'output

pour batch bufferiser
buffer en m�moire
buffer sur les I - O fichiers de tampon -> flush

off heap?
int�grer probl�matique de performances -> en faire bcp rapidement avec les differents outputs, threads
d�couper par zone fonctionelle

Mod�le :

R�cup�rer mod�le Pierre
data model de Pierre recup�rer champs -> use case reprendre objets m�tiers � refaire

Rangement des trades :

book -> g�n�re des trades qui matche ce book -> pour l instant
on produit et on range
instruments qui vont etre des g�n�rateurs
Rangement dans les books
ranger dans l'ordre s�quentiel des books
cr�er les books qui vont bien
si pas de match, �a va dans book others 
partie hi�rarchie
match instrument et currency avant puis rangement
si pas de match on met rien -> exception
rdm sur les cur -> mettre dans les books qui matchent

Divers :

Conteneur autour des trades dans les fichiers de sortie
Ajouter un warning -> ce book est vide quand pas de filter
Ajouter le fait que l'on puisse dupliquer les trades en sortie
contrepartie sur les trades non affich�
base de calcul pas demand�
get random enum pas correct � enlever 
g�n�rer tout les champs -> mettre des champs vide si il y a plus de champs dans d'autre classes
Eviter de modifier les choses en param�tre afin d'�viter les effets de bords