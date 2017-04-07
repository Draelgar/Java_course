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
	
	$http.post("FetchDataServlet").then(function (response) {
        var rows = response.data.split(":");
        
        var fishes = [];
        
        for(var i = 0; i < rows.length; i++) {
        	var values = rows[i].split(",");
        	fishes.push(new Fish(values[0], values[1], values[2], values[3], values[4]));
        }
        
        $scope.fishes = fishes;
    });
	
});