/*******
** Credits to Daniel Shiffman from codingtra.in for the code's base
** Website for Stresshub @Lumohacks
** By Léo Abiguime @labiguime
********
*/
const kAlarmingScore = 80 ; // Everything superior or equal to that is alarming
const kCriticalScore = 90; // The score is critical

var score;
var database;


function setup() {
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

  var scores = data.val();
  var keys = Object.keys(scores);
  var myarr = [];
  for (var i = 0; i < keys.length; i++) {
	  myarr.push(i);
  }
  var len = myarr.length;
  for (var i = 0; i < len - 1; i++) {
	var min = i; 
	for (var j = i + 1; j < len; j++) {
		var key1 =keys[myarr[j]];
		var key2 =keys[myarr[min]];
		if (scores[key1].score < scores[key2].score) { 
			min = j; 
		}
	}
	if (min != i) {
		var tmp = myarr[i];
		myarr[i] = myarr[min];
		myarr[min] = tmp;
	}
  }
  var tbl = document.getElementById("myTable");
  
  while(tbl.rows.length!=0) {
	  tbl.deleteRow(0);
  }

  for (var i = 0; i < myarr.length; i++) {
    var k = keys[myarr[i]];
    var initials = scores[k].initials;
    var score = scores[k].score;
	myarra = initials + ': ' + score;
	myCreateFunction(initials, score, scores[k].phone, 0);
  }
  myCreateFunction("<b><strong>Full name</strong></b>", "<strong><b>Score</b></strong>", "<strong><b>Contact information</b></strong>", 1);
}

function errData(err) {
  console.log('Error!');
  console.log(err);
}
function myCreateFunction(ini, sco, pho, type) {
    var table = document.getElementById("myTable");
    var row = table.insertRow(0);
    var cell1 = row.insertCell(0);
    var cell2 = row.insertCell(1);
	var cell3 = row.insertCell(2);
	var cell4 = row.insertCell(3);
	
    cell1.innerHTML = ini;
	cell2.innerHTML = pho;
    cell3.innerHTML = sco;
	
	
	if(type == 0) {
		if( sco >= kAlarmingScore) {
			//row.style.backgroundColor = "#f4dede"; // green
			if(sco < kCriticalScore) {
				cell4.innerHTML = "The score is alarming. You should investigate.";
			}
			else {
				cell4.innerHTML = "Critical score. You must take action.";
			}
			
		}
		else {
			cell4.innerHTML = "The score doesn't suggest any abnormal behavior.";
		}
	}
	else{
		cell4.innerHTML = "<strong><b>Comment</strong></b>";
	}
	
}



