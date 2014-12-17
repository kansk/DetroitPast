var placeControllers = angular.module('placeControllers', []);

placeControllers.controller('PlaceCtrl', ['$scope', '$q', 'placeService',
    function($scope, $q, placeService) {

        $scope.place = {
            name: "default",
            year: 1916,
            additionalInformation: [{}],
            lat: 34.25,
            long: -53.15
        };

        placeService.getPlaces().then(function(data) {
            $scope.places = data;
        });

        //$scope.addAdditionalInformation = function(place) {
        //    $scope.place.push()
        //};
        //
        //$scope.showAddAdditionalInformation = function(additionalInformation) {
        //    return additionalInformation === $scope.place.additionalInformation[$scope.place.additionalInformation.length-1];
        //};

        $scope.savePlace = function(place) {
            console.log($scope.place);
            placeService.save($scope.place).then(function(data) {
                console.debug("Place Saved")
            });
        }
}]);