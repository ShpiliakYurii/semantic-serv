'use strict';

angular.module('myApp').factory('MainService', ['$http', '$q', function ($http, $q) {


    return {
        prepareUrl: prepareUrl
    };

    function prepareUrl(page) {
        var url = page.nativeUrl;
        if (url.length > 5) {
            if (url.substr(0, 4) === 'http')
                return url;
            else
                return page.origin + url;
        }
        return url;
    }

}]);
