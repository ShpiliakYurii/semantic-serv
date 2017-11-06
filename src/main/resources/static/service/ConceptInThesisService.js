'use strict';

angular.module('myApp').factory('ConceptInThesisService', ['$http', '$q', function ($http, $q) {

    var DOMAIN = "http://localhost:8090";
    var URL = DOMAIN + "/api/concept-in-thesis";

    return {
        getAll: getAll,
        get: get
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

    function get(id) {
        var deferred = $q.defer();
        $http.get(URL + "/" + id).then(
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
