/**
 * Author: Gustaf Peter Hultgren
 */

(function() {
	
	var app = angular.module("exApp", []);
	
	var controller = function($scope) {
		$scope.items = [{name: "item1", price: "10 kr", description: "First item"}, 
			{name: "item2", price: "50 kr", description: "Second item"},
			{name: "item3", price: "100 kr", description: "Third item"}];
		
		$scope.hidden = [true, true, true];
		
		$scope.showToolTip = function(index) {
			$scope.hidden[index] = false;
		}
		
		$scope.hideToolTip = function(index) {
			$scope.hidden[index] = true;
		}
	};
	
	app.controller("exController", ["$scope", controller]);
	
}());