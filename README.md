- Acceptance test ? : Quand X voitures entrent et Y voitures sortent, le nombre de voitures final est de X - Y
- Le parking envoie des événements
- Le bus prend des événements et les envoie à ceux qui sont inscrits
- Un compteur s'abonne au bus pour compter les voitures


- Ajout d'un contrôle pour la capacité max (Acceptance test)
- Refactor : Le parking peut être instancié à partir d'événements passés

- Ajout du calcul du taux de remplissage en fonction de l'heure (Acceptance test)
- Ajout d'un abonné pour compter les entrées / sortie par heure.