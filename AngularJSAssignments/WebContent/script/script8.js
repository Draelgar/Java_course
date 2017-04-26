/**
 * Author: Gustaf Peter Hultgren
 */

(function() {
	
	var app = angular.module("exApp", []);
	
	var controller = function($scope) {
		$scope.items = [{name: "item1", price: "10 kr", description: "First item"}, 
			{name: "item2", price: "50 kr", description: "Second item"},
			{name: "item3", price: "100 kr", description: "Third item"}];
		
		$scope.test = "unavailable";
		
		$scope.showToolTip = function(index) {
			$scope.test = $scope.items[index].description;
		}
	};
	
	app.controller("exController", ["$scope", controller]);
	
	var tooltip = function($scope, $index) {
		return { link: "Tooltip" };
		/*return { link: function (scope, element, attr) {
		      // use tag's title text for a popup
		      element
		        .addClass('tooltip-wrap')
		        .append(angular.element('<div class="tooltip-text">'+attr.title+'</div>'))
		        .attr('title', null);*/
	};
	
	app.directive("toolTip", ["$scope", "$index", tooltip]);
	
}());