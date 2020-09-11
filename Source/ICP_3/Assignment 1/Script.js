<!-- VARIABLES -->

var minRandomNumber = 0;
var maxRandomNumber = 9;
var rockBtn = document.getElementById("Rock");
var paperBtn = document.getElementById("Paper");
var scissorBtn = document.getElementById("Scissor");
var playerSelect = "";
var cScore = 0;
var pScore = 0;



<!-- LISTENERS -->

//Listener for the ROCK image button
rockBtn.addEventListener('click', function(){
    clear();
    playerSelect = "Rock";
    playGame(playerSelect);
});

//Listener for the PAPER image button
paperBtn.addEventListener('click', function(){
    clear();
    playerSelect = "Paper";
    playGame(playerSelect);
});

//Listener for the SCISSOR image button
scissorBtn.addEventListener('click', function(){
    clear();
    playerSelect = "Scissor";
    playGame(playerSelect);
});



<!-- FUNCTIONS -->

//Function that begin's a single game against the computer
function playGame (pSelect){
    var compSelect = playerComputer();
    var results = comparison(pSelect, compSelect);
    var oM = "Computer picked " + compSelect + ". You picked " + pSelect + ".";
    document.getElementById("message").innerHTML = results;
    document.getElementById("output").innerHTML = oM;
    calculateScore(results);
}

//Function that update's the scoreboard
function calculateScore(res){
    if (res === "You Win!"){
        pScore++;
        document.getElementById("playerScore").innerHTML = pScore.toString();;
    }
    else if (res === "You Lose."){
        cScore++;
        document.getElementById("compScore").innerHTML = cScore.toString();
    }
}

//Function that clears the player's selection
function clear(){
    playerSelect = "";
}

//Function that returns the string outcome of the player's selection vs the computer's selection
function comparison(pS, cS){
    var win = "You Win!";
    var lose = "You Lose.";
    var tie = "Its a Tie.";
    switch (pS){
        case "Rock": {
            if (cS === "Paper")
                return lose;
            else if (cS === "Scissor")
                return win;
            else
                return tie;
        }
        case "Paper": {
            if (cS === "Rock")
                return win;
            else if (cS === "Scissor")
                return lose;
            else
                return tie;
        }
        case "Scissor": {
            if (cS === "Rock")
                return lose;
            else if (cS === "Paper")
                return win;
            else
                return tie;
        }
    }
}

//Function that returns a random number between the min and max values
function getRandomArbitrary(min, max) {
    return Math.random() * (max - min) + min;
}

//Function that returns the string element of specified number ranges
//Rock 0 through 3
//Paper 3 through 6
//Scissor 6 through 9
function playerComputer(){
    var computerSelected = getRandomArbitrary(minRandomNumber, maxRandomNumber)
    if (computerSelected <= 3){
        return "Rock";
    }
    else if (computerSelected >= 3 && computerSelected <= 6){
        return "Paper";
    }
    else{
        return "Scissor";
    }
}