"use strict";

var alertServices = angular.module('alertServices', ['ngResource']);

alertServices.factory('alertService', ['$rootScope','$sce', '$timeout',
    function ($rootScope, $sce, $timeout) {
        $rootScope.alerts = [];
        var alertService;
        return alertService = {
            add: function(type, msg, timeout) {
                console.log("responseError: " + msg);
                $rootScope.alerts.push({
                    type: type,
                    msg: $sce.trustAsHtml(msg),
                    close: function() {
                        return alertService.closeAlert(this);
                    }
                });

                if (timeout) {
                    $timeout(function(){
                        alertService.closeAlert(this);
                    }, timeout);
                }
            },
            closeAlert: function(alert) {
                return this.closeAlertIdx($rootScope.alerts.indexOf(alert));
            },
            closeAlertIdx: function(index) {
                return $rootScope.alerts.splice(index, 1);
            },
            closeAlerts: function() {
                return $rootScope.alerts = [];
            }
        };
    }
]);