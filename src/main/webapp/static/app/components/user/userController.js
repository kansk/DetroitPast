var userControllers = angular.module('userControllers', []);

userControllers.controller('UserCtrl', ['$scope', '$q', 'userService',
    function($scope, $q, userService) {

        $scope.user = {
            firstName: "",
            lastName: "",
            email: "",
            password: ""
        };

        $scope.saveUser = function(user) {
            console.log($scope.user);
            userService.save($scope.user).then(function(data) {
                console.debug("User Saved")
            });
        }
    }]);