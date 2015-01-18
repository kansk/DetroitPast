var loginControllers = angular.module('loginControllers', []);

loginControllers.controller('LoginCtrl', ['$scope', '$rootScope', '$location', 'authenticationService',
    function ($scope, $rootScope, $location, authenticationService) {
        // reset login status
        authenticationService.ClearCredentials();

        $scope.login = function () {
            $scope.dataLoading = true;
            authenticationService.Login($scope.username, $scope.password, function (success) {
                if (success) {
                    console.log("Authentication Success, setting credentials");
                    authenticationService.SetCredentials($scope.username, $scope.password);
                    $location.path('/places');
                } else {
                    console.log("Authentication Error");
                    $scope.error = response.message;
                    $scope.dataLoading = false;
                }
            });
        };
    }]);