import { queryMaze, queryAutoGen, queryDecision } from "./query.js";

var loadButton = document.getElementById("loadMaze");
var genButton = document.getElementById("generateMaze");
var decButton = document.getElementById("showDecision");
loadButton.addEventListener('click', queryMaze);
genButton.addEventListener('click', queryAutoGen);
decButton.addEventListener('click', queryDecision);