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
normaliser les champs, garder une certaine cohérence, mettre tout en anglais
businessUnit plutot que business
pour les reports, mettre à chaque fois le même nombre de chiffre
meme format yyyy-MM-dd
price output 0
montants 2 chiffres après la virgule et . pas ,
visibilité dans TradeEvent des accès -> revoir l'encapsulation (getters setters) -> cf modèle Pierre
sur les rapports, revoir typographie
nombre de champs toujours les même

Norme classes :

ne pas mélanger objet métier et output -> TradeEvent.java


Performances :

Ajouter des output ne doit pas ralentir la production -> parallélisation des outputs , découpage entre la production et l'écriture (output)
si un output crash il ne doit pas faire crasher les autres ...
c'est l'instrument qui donne la vitesse de production du trade, pas l'output

pour batch bufferiser
buffer en mémoire
buffer sur les I - O fichiers de tampon -> flush

off heap?
intégrer problèmatique de performances -> en faire bcp rapidement avec les differents outputs, threads
découper par zone fonctionelle

Modèle :

Récupérer modèle Pierre
data model de Pierre recupérer champs -> use case reprendre objets métiers à refaire

Rangement des trades :

book -> génère des trades qui matche ce book -> pour l instant
on produit et on range
instruments qui vont etre des générateurs
Rangement dans les books
ranger dans l'ordre séquentiel des books
créer les books qui vont bien
si pas de match, ça va dans book others 
partie hiérarchie
match instrument et currency avant puis rangement
si pas de match on met rien -> exception
rdm sur les cur -> mettre dans les books qui matchent

Divers :

Conteneur autour des trades dans les fichiers de sortie
Ajouter un warning -> ce book est vide quand pas de filter
Ajouter le fait que l'on puisse dupliquer les trades en sortie
contrepartie sur les trades non affiché
base de calcul pas demandé
get random enum pas correct à enlever 
générer tout les champs -> mettre des champs vide si il y a plus de champs dans d'autre classes
Eviter de modifier les choses en paramètre afin d'éviter les effets de bords