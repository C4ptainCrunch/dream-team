# CreaTikZ : projet de génie logiciel et gestion de projet (info-f-307)

CreaTikZ est une application graphique écrite en Java/Swing permettant de créer en WYSIWYG des diagrammes TikZ.

Ce projet est écrit dans de cadre du cours "Génie logiciel et gestion de projets" (info-f-307)
donné en bachelier en sciences informatiques à l'ULB.

# Utilisation

CreaTikZ utilise `gradle` comme système de build et Java8, assurez vous d'avoir les deux installés sur votre machine.

Compilation:

    gradle assemble # installation des dépendances java
   
Démarrage : 

    gradle run
    
Crétion d'un package self-contained (.zip) dans `build/distributions/`: 

    gradle distZip

# Tests

Les tests se trouvent dans `/tests` et peuvent être exécutés avec `gradle test`.
Ils sont aussi exécutés automatiquement pour chaque commit et avant chaque merge par [Travis](http://travis-ci.com)

# Misc

## Développement

Ce projet est développé avec IntelliJ.

## Screenshot

![a](http://i.imgur.com/Lb8OelD.png)

## License

Le code de ce projet est sous licence MIT (voir `/LICENSE`)
