'use strict';

angular.module('myApp').factory('CClassService', ['$http', '$q', function ($http, $q) {

    var DOMAIN = "http://localhost:8090";
    var URL = DOMAIN + "/api/c-class";

    return {
        getAll: getAll
    };

    function getAll() {
        var deferred = $q.defer();
        $http.get(URL).then(
            function (response) {
                deferred.resolve(response.data);
            },
            function (errResponse) {
                console.error(errResponse.toString());
                deferred.reject(errResponse);
            });
        return deferred.promise;
    }

}]);
