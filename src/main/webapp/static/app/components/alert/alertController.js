var alertControllers = angular.module('alertControllers', []);

alertControllers.controller('AlertCtrl', ['$scope', '$q', 'alertService',
    function ($scope, $q, alertService) {
        $scope.alerts = [
            {type: 'error', msg: 'Oh snap! Change a few things up and try submitting again.'},
            {type: 'success', msg: 'Well done! You successfully read this important alert message.'}
        ];

        $scope.addAlert = function () {
            alertService.add("error", "test alert");
        };

        $scope.closeAlert = function (index) {
            $scope.alerts.splice(index, 1);
        };
    }]);