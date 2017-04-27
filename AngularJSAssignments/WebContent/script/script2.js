/**
 * Author: Gustaf Peter Hultgren
 */

(function() {
	var app = angular.module("shApp", []);
	
	var controller = function($scope) {
		$scope.showText = false;
		$scope.buttonText = "hide";
		
		$scope.toggle = function() {
			$scope.showText = !$scope.showText
			
			if($scope.showText == false)
				$scope.buttonText = "hide";
			else
				$scope.buttonText = "show";
		}
	};
	
	app.controller("shController", ['$scope', controller]);
}());