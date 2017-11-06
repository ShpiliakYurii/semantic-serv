var head = document.getElementsByTagName("head")[0];
var html = document.getElementsByTagName("html")[0];
window.onload = function () {
    initialize();
};

function initialize() {
    html.setAttribute("ng-app", "myApp");
    console.log(window.document.location.pathname);
    if (window.document.location.pathname === '/dictionary.html')
        document.body.setAttribute("ng-controller", "DictionaryController");
    else if (window.document.location.pathname === '/concept.html')
        document.body.setAttribute("ng-controller", "ConceptController");
    else
        document.body.setAttribute("ng-controller", "MainController");
    loadFile("https://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular.js", "js");
    loadFile("https://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular-animate.min.js", "js");
    loadFile("https://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular-aria.min.js", "js");
    loadFile("https://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular-messages.min.js", "js");
    loadFile("https://ajax.googleapis.com/ajax/libs/angular_material/1.1.0/angular-material.min.js", "js");
    loadFile("http://localhost:8090/app.js", "js");
    loadFile("http://localhost:8090/controller/MainController.js", "js");
    loadFile("http://localhost:8090/controller/DictionaryController.js", "js");
    loadFile("http://localhost:8090/controller/ConceptController.js", "js");
    loadFile("http://localhost:8090/service/MainService.js", "js");
    loadFile("http://localhost:8090/service/TClassService.js", "js");
    loadFile("http://localhost:8090/service/CClassService.js", "js");
    loadFile("http://localhost:8090/service/ConceptService.js", "js");
    loadFile("http://localhost:8090/service/PageService.js", "js");
    loadFile("http://localhost:8090/service/ThesisService.js", "js");
    loadFile("http://localhost:8090/service/OriginService.js", "js");
    loadFile("http://localhost:8090/service/ConceptInThesisService.js", "js");
}

function loadFile(filename, extension) {
    var file = null;
    if (extension === "js") {
        file = document.createElement('script');
        file.setAttribute("type", "text/javascript");
        file.setAttribute("src", filename);
        file.async = false;
        file.defer = true;
    }
    else if (extension === "css") {
        file = document.createElement("link");
        file.setAttribute("rel", "stylesheet");
        file.setAttribute("type", "text/css");
        file.setAttribute("href", filename);
    }
    head.appendChild(file);
}
