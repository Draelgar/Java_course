/**
 * 
 */
class Fish {
		constructor(name, type) {
			this.name = name;
			this.type = type;
		}
	}
	
var app = angular.module('fish_app', []);
app.controller('fish_controller', function($scope, $http) {
	
	var fish1 = new Fish("Sharky", "Shark");
	var fish2 = new Fish("Sparky", "Eel");
	var fish3 = new Fish("Crusty", "Clownfish");
	
	$scope.fishes = [fish1, fish2, fish3];
});