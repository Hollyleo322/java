function startGame(gameId) {
    var req = new XMLHttpRequest();
    req.open("POST", "http://localhost:8080/api/games/" + gameId, false);
    req.send(null);
}