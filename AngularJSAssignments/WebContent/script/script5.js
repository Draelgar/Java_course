/**
 * Author: Gustaf Peter Hultgren
 */

(function() {
	var app = angular.module("tApp", []);
	
	var controller = function($scope, $interval) {
		$scope.time = 30;
		$scope.buttonText = "Stop";
		
		/* Function, delay between ticks, iterations. */
		var stop = $interval(function() { $scope.time--; }, 1000, 30);
		
		$scope.stopTimer = function() {
			if(angular.isDefined(stop)) {
				$interval.cancel(stop);
				stop = undefined;
				$scope.buttonText = "Start";
			}
			else {
				stop = $interval(function() { $scope.time--; }, 1000, 30);
				$scope.buttonText = "Stop";
			}
		}
		
		$scope.$on('$destroy', function() {
	          // Make sure that the interval is destroyed too
	          $scope.stopTimer();
	        });
	};
	
	app.controller("tController", ["$scope", "$interval", controller]);
	
}());