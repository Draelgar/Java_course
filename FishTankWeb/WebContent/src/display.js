/**
 * 
 */
class Fish {
		constructor(name, type, age, weight, length) {
			this.name = name;
			this.type = type;
			this.age = age;
			this.weight = weight;
			this.length = length;
		}
	}

var module = angular.module('fish_app', []);
module.controller('fish_controller', function($scope, $http) {
	
	$http.get("FetchDataServlet").then(function (response) {
        
        var fishes = response.data;
        $scope.fishes = fishes;
        
    });
	
});