# OpenCvJava


Actuellement technicien dans l’audiovisuel, je voudrais entamer une reconversion professionnelle et devenir développeur. 

Je me suis formé seul et ce programme est avant tout un exemple concret de mon niveau en programmation. Le code est toujours en cours d'écriture.


Le projet a pour but de pouvoir tester la librairie de vision par ordinateur "Open CV". L'ensemble du code fonctionne comme un framework, et s'utilise en deux étapes : coder l'algorithme de traitement d’image / tester à travers une interface graphique.



* D'abord, l'utilisateur/développeur écrit ses algorithmes en créant une nouvelle classe dans le package "algo.dataBase" de type "AdjustControlFloat" (l'algorithme en lui-même est écrit dans la méthode "compute"). Il y renseigne également différentes informations liées aux paramètres utilisés par l'algorithme et qui pourront être réglés au cours de l'exécution du programme (nombre de paramètres, noms des paramètres, valeurs par défaut etc). Il doit ensuite ajouter un objet du type nouvellement créé dans les arguments de la classe "DbControls" (toujours dans le package "algo.dataBase").



* Ensuite, l'utilisateur peut compiler le projet et tester ses algorithmes grâce à une interface graphique simple où les paramètres sont représentés sous forme de slider (un par paramètres). Il peut ajouter ses algorithmes ou les supprimer (chacun travaillant sur l'image produite par le précédent) ; il peut aussi les reseter pour ramener tous les paramètres à leurs valeurs par défaut. L'image finale est affichée dans sa propre fenêtre et est recalculée à chaque changement de paramètre. L'utilisateur ne pourra modifier qu'un paramètres à la fois, mais n'importe lequel, et à tout moment. Ces algorithmes sont placés dans des groupements d’algorithmes appelés “layer” (à nouveau, l'utilisateur peut ajouter ou supprimer des layers) dont l'opacité peut être réglée. Une restriction importante est à souligner ici : on ne pourra ajouter qu'un seul type d'algorithme par layer. Donc si l'utilisateur veut enchaîner plusieurs fois le même algorithme, il devra créer un nouveau layer à chaque fois.

La plupart des actions peuvent être défaites et refaites sans restrictions : ajouter/supprimer un algorithme, ajouter/supprimer un layer, variation de paramètre (opacité du calque comprise).



**Enfin, en plus de ces spécifications je me suis imposé certaines restrictions qui ont grandement influencé l’architecture globale du code :**


* Le code devrait pouvoir être adapté facilement pour être utilisé avec n’importe quelle librairie de retouche d’image.
* Les parties concernant le calcul d'image et l’interface graphique doivent être complètement indépendantes.



Finalement, je dois préciser que j’ai commencé à coder le projet en C++ (premier langage que j’ai appris). Ce n’est qu’assez récemment que je me suis mis à sérieusement apprendre le JAVA. J’ai donc décidé de traduire le projet en JAVA, en essayant de coller au maximum à l’ancien code C++ qui était déjà très avancé ; peut-être cela peut-il expliquer certaines bizarreries comme par exemple le contenu du package "baseClasses.enums_struct".

**Plus de précisions sur le fonctionnement du code :** 

La classe App contient principalement deux objets : un de type Renderer chargé de calculé l'image, et l'autre de type UIImp chargé de créer le GUI et d'appeler les différentes fonctionnalités du Renderer et du GUI en fonction des signaux émis par le GUI. L'App initialise le Renderer et le passe à l'UIImp.
Celui-ci créera ensuite le GUI, et se chargera de récupérer les signaux émis par le GUI avec comme paramètre un objet de type Action. Cet objet sert ensuite à déterminer l'action à mener et à appeler les différentes fonctions du Renderer et du GUI en y passant les paramètres requis dans la fonction dealOrder.

Aujourd'hui (10/10/21) le GUI n'est pas fini et la fonction test dans la classe Test permet d'imiter les signaux du GUI. Pour cela on appel directement DealOrder (en passant par la méthode getMainWin() de App en y passant des objets Action dans la fonction dealOrder. 


En date du 09/10/21 :

A cette date, la partie de rendu de l’image est finie. Il me reste à coder la partie GUI que je compte faire avec Swing. 

Contacts : Arthur Lormeau-Wall, arthurlormeau@gmail.com

