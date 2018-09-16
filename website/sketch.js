// Daniel Shiffman
// http://codingtra.in
// http://patreon.com/codingtrain
// Code for: https://youtu.be/NcewaPfFR6Y

var canvas;
var score;
var button;
var initialInput;
var submitButton;
var database;
const ALARMING_SCORE = 80 ; // Everything superior or equal to that is alarming

function setup() {
  //canvas = createCanvas(100, 100);
  /*canvas.parent('game');
  score = 0;
  createP('Click the button to get points.').parent('game');
  button = createButton('click');
  button.mousePressed(increaseScore);
  button.parent('game');
  initialInput = createInput('initials');
  initialInput.parent('game');
  submitButton = createButton('submit');
  submitButton.parent('game');
  submitButton.mousePressed(submitScore);*/

  var config = {
    apiKey: "AIzaSyAiYfR4oA1TqeScxK3MuOWfUw6fcMkndl8",
    authDomain: "lumohacks-project-d9ce0.firebaseapp.com",
    databaseURL: "https://lumohacks-project-d9ce0.firebaseio.com",
    projectId: "lumohacks-project-d9ce0",
    storageBucket: "lumohacks-project-d9ce0.appspot.com",
    messagingSenderId: "97283405722"
  };
  firebase.initializeApp(config);
  database = firebase.database();

  var ref = database.ref('processed');
  ref.on('value', gotData, errData);
}

function gotData(data) {

  var scorelistings = selectAll('.scorelisting');
  for (var i = 0; i < scorelistings.length; i++) {
    scorelistings[i].remove();
  }

  //console.log(data.val());
  var scores = data.val();
  var keys = Object.keys(scores);
  //console.log(keys);
  var myarr = [];
  for (var i = 0; i < keys.length; i++) {
	  myarr.push(i);
	  //myarr.push("Hi");
  }
  var len = myarr.length;
  for (var i = 0; i < len - 1; i++) {
	//Number of passes
	var min = i; //min holds the current minimum number position for each pass; i holds the Initial min number
	for (var j = i + 1; j < len; j++) { //Note that j = i + 1 as we only need to go through unsorted array
		var key1 =keys[myarr[j]];
		var key2 =keys[myarr[min]];
		if (scores[key1].score < scores[key2].score) { //Compare the numbers
			min = j; //Change the current min number position if a smaller num is found
		}
	}
	if (min != i) {
		//After each pass, if the current min num != initial min num, exchange the position.
		//Swap the numbers 
		var tmp = myarr[i];
		myarr[i] = myarr[min];
		myarr[min] = tmp;
	}
  }
  var tbl = document.getElementById("myTable");
  //var r=tbl.rows.length;
  while(tbl.rows.length!=0) {
	  tbl.deleteRow(0);
  }
//while(row=table.rows[r++])
//{
	//continue;
//}
  //.deleteRow(0);
  //if(tbl) tbl.parentNode.removeChild(tbl);
  /*var elem = documenet.getElementById('toc');

if (typeof elem != 'undefined')
{
  elem.parentNode.removeChild(elem);
}*/
  for (var i = 0; i < myarr.length; i++) {
    var k = keys[myarr[i]];
    var initials = scores[k].initials;
    var score = scores[k].score;
    //console.log(initials, score);
	myarra = initials + ': ' + score;
    //var li = createElement('li', myarra);
    //li.class('scorelisting');
    //li.parent('scorelist');
	myCreateFunction(initials, score);
  }
  //myFunction();
  myCreateFunctionEx("<b>Employee's name</b>", "<b>Employee's score</b>");
  //myCreateFunctionT();
/*
  for (var i = 0; i < keys.length; i++) {
    var k = keys[i];
    var initials = scores[k].initials;
    var score = scores[k].score;
    //console.log(initials, score);
    var li = createElement('li', initials + ': ' + score);
    li.class('scorelisting');
    li.parent('scorelist');
  }*/
}

function errData(err) {
  console.log('Error!');
  console.log(err);
}
function myCreateFunction(ini, sco) {
    var table = document.getElementById("myTable");
    var row = table.insertRow(0);
    var cell1 = row.insertCell(0);
    var cell2 = row.insertCell(1);
	
    cell1.innerHTML = ini;
    cell2.innerHTML = sco;
	if( sco >= ALARMING_SCORE) {
		row.style.backgroundColor = "#f75a42";
	}
	else {
		row.style.backgroundColor = "#40e57a";
	}
	
}

function myCreateFunctionEx(ini, sco) {
    var table = document.getElementById("myTable");
    var row = table.insertRow(0);
    var cell1 = row.insertCell(0);
    var cell2 = row.insertCell(1);
	
    cell1.innerHTML = ini;
    cell2.innerHTML = sco;
	
}

function myFunction() {
    var table = document.getElementById("myTable");
	
    var header = table.createTHead();
    var row = header.insertRow(0);
    var cell = row.insertCell(0);
	
    cell1.innerHTML = "Employee's name";
    cell2.innerHTML = "Employee's score";
}
function selectionSort(myarr) {
	var len = myarr.length;
	for (var i = 0; i < len - 1; i++) {
		//Number of passes
		var min = i; //min holds the current minimum number position for each pass; i holds the Initial min number
		for (var j = i + 1; j < len; j++) { //Note that j = i + 1 as we only need to go through unsorted array
			if (key[myarr[j]] < key[myarr[min]]) { //Compare the numbers
				min = j; //Change the current min number position if a smaller num is found
			}
		}
		if (min != i) {
			//After each pass, if the current min num != initial min num, exchange the position.
			//Swap the numbers 
			var tmp = myarr[i];
			myarr[i] = myarr[min];
			myarr[min] = tmp;
		}
	}
}
function submitScore() {
  var data = {
    initials: initialInput.value(),
    score: score
  }
  console.log(data);
  var ref = database.ref('scores');
  var result = ref.push(data);
  console.log(result.key);
}

function increaseScore() {
  score++;
}

