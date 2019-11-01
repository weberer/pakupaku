location = {
    x: 0, //integer from top right of board
    y: 0, // integer from top right of the board
    direction: "up" // see direction object
}

direction = ["up", "down", "left", "right"]

input = ["arrow_up", "arrow_down", "arrow_left", "arrow_right", "W", "A", "S", "D", "escape", "O", "enter"]

game_state = ["main_menu", "play", "high_score"]

ghost_state = ["normal", "scared", "eyes"];

power_up = [null, "cherry", "raspberry", "etc"]; // list of all valid powe-ups

power_ups = [0,6]; // array of 7 powerups shown on bottom of screen. Null of no power up in that location

dot_states = [null, "p", "s"]; // null if eaten, p for normal pellet, s for super pellet

board = [[],[]]; //2d array of pellets. top row in index 0. only show number of pellets for row. E.g. 2nd row from the top is only 6 in length

ghost_names = ["stinky", "kinky", "hinky", "blaine"];

score = {
    name: "ABC", //3 letter name,
    score: 01234 // integer score
}

score_list = {
    last_score: 0123, //integer of last score, or score object. Whichever is easier
    high_scores: [0 ,4] // array of 5 high score objects
}

ghost = {
    location: location, // instance of location
    state : "scared" // string representing state of the ghost. E.g normal, scared, flee
}

paku = {
    location: location, // instance of location
    lives: 0, //integer for number of lives paku has
}

// Object Received from UI
data = {
    frame: 0, // int representing game frame, can be used to determine 
    input: "arrow_up", //see input_arr
    initials: ""
}

// Main object passed to UI
data = {
    game_state: "", //see game_state arr
    high_score: score, //instance of score representing high score
    score: score, // instance of score representing current score
    sound: true, // boolean if sound is playing,
    board: board, // instance of board array,
    paku: paku, // instance of paku
    ghosts: {           // object with ghost names as keys, and instance of ghost object
        stinky: ghost,
        hinky: ghost,
        kinky: ghost,
        blaine: ghost
    }
}

