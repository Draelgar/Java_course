/**
 * Author: Gustaf Peter Hultgren
 */

(function() {
	var app = angular.module("snApp", []);
	
	var controller = function($scope) {
		$scope.hide1 = false;
		$scope.hide2 = true;
		$scope.hide3 = true;
		
		$scope.button1 = function() {
			$scope.hide1 = false;
			$scope.hide2 = true;
			$scope.hide3 = true;
		}
		
		$scope.button2 = function() {
			$scope.hide1 = true;
			$scope.hide2 = false;
			$scope.hide3 = true;
		}
		
		$scope.button3 = function() {
			$scope.hide1 = true;
			$scope.hide2 = true;
			$scope.hide3 = false;
		}
	};
	
	app.controller("snController", ["$scope", controller]);
	
}());