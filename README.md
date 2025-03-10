Besoin 18 : Documentation d’installation et de test
— Système(s) d’exploitation sur lesquels votre serveur a été testé : 
    — Windows ≥ 10
    — Ubuntu ≥ 20.04

— Navigateur(s) web sur lesquels votre client a été testé incluant la version de celui-ci :
    — Firefox


Lancement du serveur :
    - Rentrer dans le terminal les commandes "export DATABASE_NAME=<name>" et "export DATABASE_PASSWORD=<password>" avec les name et password de votre compte sur la database
    - Executer "mvn --projects backend spring-boot:run" depuis Main_Projet
    - Executer "npx vite --port=4000" depuis Main_Projet/frontend
    - Accéder à "http://localhost:4000/" depuis un navigateur fonctionnel

Commandes depuis le serveur :
    - GET images : GET "http://localhost:8181/images"
    - POST : curl -X POST -F "file=@<filepath>" "http://localhost:8181/images"
    - DELETE : curl -X DELETE "http://localhost:8181/images/{id}"

Commandes depuis le client :
    - GET id : http://localhost:4000/images/{id}
    - GET images : http://localhost:4000/images
    - POST : cliquer sur "Parcourir" dans la section "Déposez votre image ici" puis, une fois un fichier sélectionné cliquer sur Submit
    - DELETE : cliquer sur Delete de l'image correspondante dans la gallerie 
    - Similarité : sélectionner une image depuis "choose an image", rentrer le nombres d'images les plus similaires souhaité, choisir le type de similarité recherché (RGB ou Hue) et cliquer sur "More images!"