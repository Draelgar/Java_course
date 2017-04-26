/**
 * Author: Gustaf Peter Hultgren
 */

(function() {
	
	var app = angular.module("alApp", []);
	
	var controller = function($scope) {
		$scope.numbers = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
	};
	
	app.controller("alController", ["$scope", controller]);
	
}());