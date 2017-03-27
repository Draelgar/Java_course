function first() {
	document.getElementById("demo").innerHTML = "First";
}

function second() {
	document.getElementById("demo").innerHTML = "Second";
}

function third() {
	document.getElementById("demo").innerHTML = "Third";
}

function submit() {
	var name = "Hello ";
	name += document.getElementById("name").value;
	name += "!";
	
	document.getElementById("output").innerHTML = name;
}

function divide(string) {
	var values = string.split("/");
	
	if(values.length > 0)
	var sum = parseFloat(values[0]);
	
	for(i = 1; i < values.length; i++) {
		sum /= parseFloat(values[i]);
	}
	
	return sum;
}

function mult(string) {
	var values = string.split("*");
	var sum = 1.0;
	
	for(i = 0; i < values.length; i++) {
		if(values[i].indexOf("/") != -1) {
			sum *= divide(values[i]);
		}
		else {
			sum *= parseFloat(values[i]);
		}
	}
	
	return sum;
}

function calc() {
	var plus = document.getElementById("calc").value.split("+");
	var sum = 0.0;
	
	for(i = 0; i < plus.length; i++) {
		if(plus[i].indexOf("*") != -1) {
			sum += mult(plus[i]);
		}
		else {
			sum += parseFloat(plus[i]);
		}
	}
	
	document.getElementById("sum").innerHTML = sum;
}

(function() {
	var app = angular.module("ajsModule", []);
	
	var MainController = function($scope) {
		$scope.message = "The End!";
	};
	
	app.controller("MainController", MainController);
	
}());