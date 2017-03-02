function hello() {
	document.getElementById("demo").innerHTML = "hello";
}

function bye() {
	document.getElementById("demo").innerHTML = "bye";
}

(function() {
	var app = angular.module("ajsModule", []);
	
	var MainController = function($scope) {
		$scope.message = "The End!";
	};
	
	app.controller("MainController", MainController);
	
}());