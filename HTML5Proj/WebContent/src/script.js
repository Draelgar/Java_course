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

(function() {
	var app = angular.module("ajsModule", []);
	
	var MainController = function($scope) {
		$scope.message = "The End!";
	};
	
	app.controller("MainController", MainController);
	
}());