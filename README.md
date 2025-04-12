Besoin 18 : Documentation d’installation et de test
— Système(s) d’exploitation sur lesquels votre serveur a été testé : 
    — Windows ≥ 10
    — Ubuntu ≥ 20.04

— Navigateur(s) web sur lesquels votre client a été testé incluant la version de celui-ci :
    — Firefox

Installer le projet :
    - placez vous dans l3c/Main_Projet

Lancement du serveur :
    - Rentrer dans le terminal les commandes "export DATABASE_NAME=<name>" et "export DATABASE_PASSWORD=<password>" avec les name et password de votre compte sur la database
    - Executer "mvn --projects backend spring-boot:run" depuis Main_Projet
    - Accéder à "http://localhost:8181/" depuis un navigateur fonctionnel

Commandes depuis le serveur :
    - GET image : GET "http://localhost:8181/images/{id}/content"
    - GET images : GET "http://localhost:8181/images"
    - GET folders : GET "http://localhost:8181/images/directories"
    - GET images from a folder : GET "http://localhost:8181/images/folder/{name}"
    - POST : curl -X POST -F "file=@<filepath>" "http://localhost:8181/images/{dir}"
    - POST folder : curl POST "http://localhost:8181/images/createdir/{dir}"
    - DELETE : curl -X DELETE "http://localhost:8181/images/{id}"

Commandes depuis le client :
    - GET id : http://localhost:8181/images/{id}/content
    - GET images : http://localhost:8181/images
    - POST : cliquer sur "Parcourir" dans la section "Déposez votre image ici", choisir le dossier dans lequel mettre l'image, puis une fois un fichier sélectionné cliquer sur Submit
    - DELETE : cliquer sur Delete de l'image correspondante dans la galerie 
    - Similarité : sélectionner une image de la galerie en cliquant dessus, vous aurez un bouton sur lequel appuyer pour chercher le nombres d'images les plus similaires souhaitées
    - Modifier une image : depuis le même menu, choisir un filtre à appliquer sur l'image
    - Galerie pour chaque dossier en cliquant sur le nom de dossier dans la galerie
    - Création de dossier depuis un bouton