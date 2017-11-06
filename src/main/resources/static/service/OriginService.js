'use strict';

angular.module('myApp').factory('OriginService', ['$http', '$q', function ($http, $q) {

    var URL = "http://localhost:8090/api/origin";

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
