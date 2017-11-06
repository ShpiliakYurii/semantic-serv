'use strict';


angular.module('myApp').controller("MainController", [
    '$scope',
    '$rootScope',
    '$mdDialog',
    'MainService',
    'TClassService',
    'CClassService',
    'ConceptService',
    'ThesisService',
    '$sce',
    'PageService',
    'OriginService',
    function ($scope, $rootScope, $mdDialog, MainService, TClassService, CClassService, ConceptService, ThesisService,
              $sce, PageService, OriginService) {

        $scope.searchStr = "";
        $scope.frame = document.createElement('iframe');
        $scope.preloader = {send: false};
        $scope.showWiki = true;
        $scope.domain = {nativeUrl: window.location.href};
        $scope.pageConcepts = [];
        $scope.searchConcepts = [];
        $scope.currentOrigin = {};
        $scope.tempOriginId = 0;

        $scope.sourceUrl = "";
        $scope.searchConceptStr = "";


        $scope.initConcept = function (concept, forms, cClass, page, these) {
            $scope.currentConcept = {
                concept: concept,
                forms: forms,
                cClass: cClass,
                cPage: page,
                theses: these
            };
            $scope.editConcept = false;
        };

        $scope.initConcept('', '', '', $scope.domain, []);

        $scope.initThesis = function (thesis, tClass, concept, page) {
            $scope.currentThesis = {
                thesis: thesis,
                tClass: tClass,
                concept: concept,
                tPage: page
            };
            $scope.editThesis = false;
        };

        $scope.initThesis('', '', $scope.currentConcept, $scope.domain);

        TClassService.getAll().then(function (data) {
            $scope.tClasses = data;
            if ($scope.tClasses.length !== 0)
                $scope.currentThesis.tClass = $scope.tClasses[0];
        });

        CClassService.getAll().then(function (data) {
            $scope.cClasses = data;
        });

        OriginService.getAll().then(function (data) {
            $scope.origins = data;
            $scope.sourceUrl = $scope.origins[0].origin;
            $scope.tempOriginId = 0;
            $scope.getPage();
            $scope.getPageConcepts();
        });


        $scope.originChanged = function () {
            console.log("origin changed");
            $scope.sourceUrl = $scope.origins[$scope.tempOriginId].origin;
            $scope.getPageConcepts();
            $scope.initConcept('', '', '', $scope.domain, []);
        };

        $scope.getPageConcepts = function () {
            ConceptService.findAllByOrigin($scope.origins[$scope.tempOriginId].id).then(function (data) {
                $scope.pageConcepts = data;
                if ($scope.currentConcept.id !== undefined) {
                    for (var i = 0; i < $scope.pageConcepts.length; i++) {
                        if ($scope.currentConcept.id === $scope.pageConcepts[i].id) {
                            $scope.currentConcept = $scope.pageConcepts[i];
                        }
                    }
                }
            });
        };

        // $scope.getPageConcepts();

        function frameListener(event) {
            if (event.origin !== "http://localhost:8090")
                return;
            if (event.data.text !== "getSelectedTextEvent") {
                $scope.domain = {id: event.data.url.split('=')[1]};
                $scope.prepareSelectedText(event.data.text, event.data.type);
            }
            $scope.$apply();
            $scope.preloader.send = false;
        }

        $scope.prepareSelectedText = function (text, type) {
            var selectedText = "";
            if (text.length !== 0) {
                selectedText = text[0].toUpperCase() + text.substring(1);
            }
            if (type === 'concept') {
                if (selectedText.length > 0) {
                    if (!ConceptService.checkIfExist($scope.pageConcepts, selectedText)) {
                        $scope.initConcept(selectedText, selectedText + ";", $scope.cClasses[0], $scope.domain, []);
                        $scope.editConcept = true;
                    } else {
                        // $scope.showMessageModalWindow('Помилка!', " Вибраний термін уже існує.");
                        alert('Selected concept already exist!');//TODO modal window with 'concept already exits' error message
                    }
                } else {
                    $scope.showMessageModalWindow('Повідомлення!', 'Виділіть текст на сторінці для ініціалізації нового терміну.');
                }
            }
            if (type === 'thesis') {
                if (selectedText.length === 0) {
                    selectedText = 'Нова теза.';
                }
                $scope.initThesis(selectedText, $scope.tClasses[0], $scope.currentConcept, $scope.domain);
                $scope.editThesis = true;
            }

        };


        if (window.addEventListener) {
            window.addEventListener("message", frameListener, false);
        } else {
            window.attachEvent("onmessage", frameListener);
        }


        $scope.getSelectedText = function (type) {
            if ($scope.showWiki) {
                document.getElementById('wiki-frame').contentWindow
                    .postMessage({event: 'getSelectedTextEvent', type: type}, "*");
            } else {
                $scope.prepareSelectedText(window.getSelection().toString(), type);
            }
        };

        $scope.changeCurrentConcept = function (index) {
            $scope.currentConcept = $scope.pageConcepts[index];
            $scope.initThesis('', $scope.tClasses[0], $scope.currentConcept, $scope.domain);
            // $scope.editConcept = true;
        };

        $scope.changeCurrentConceptSearch = function (index) {
            $scope.currentConcept = $scope.searchConcepts[index];
            $scope.initThesis('', $scope.tClasses[0], $scope.currentConcept, $scope.domain);
            for (var i = 0; i < $scope.origins.length; i++) {
                if ($scope.currentConcept.cPage.origin.id === $scope.origins[i].id) {
                    $scope.tempOriginId = i;
                    break;
                }
            }
            $scope.getPageConcepts();
            // $scope.editConcept = true;
        };

        $scope.findConcept = function () {
            ConceptService.findByConceptStr($scope.searchConceptStr).then(function (data) {
                $scope.searchConcepts = data;
            })
        };

        $scope.showMessageModalWindow = function (title, msg) {
            $mdDialog.show({
                controller: DialogMessageController,
                templateUrl: 'view/msg-modal.html',
                locals: {
                    title: title,
                    message: msg
                },
                parent: angular.element(document.body),
                // targetEvent: ev,
                clickOutsideToClose: true,
                escapeToClose: true
            }).then(function (answer) {
            });
        };

        function DialogMessageController($scope, $mdDialog, title, message) {
            $scope.title = title;
            $scope.message = message;
            $scope.hide = function () {
                $mdDialog.hide();
            };
            $scope.cancel = function () {
                $mdDialog.cancel();
            };
            $scope.answer = function (message) {
                $mdDialog.hide(message);
            };
        }

        $scope.getWikiPage = function (search) {
            $scope.preloader.send = true;
            PageService.findWikiPage(search).then(function (data) {
                // $scope.domain = {id: data.id};
                $scope.frame.setAttribute("src", 'http://localhost:8090/page?id=' + data.page.id);
                $scope.frame.setAttribute("width", '100%');
                $scope.frame.setAttribute("height", '100%');
                $scope.frame.setAttribute("id", 'wiki-frame');
                document.getElementById('sm-content-wiki').appendChild($scope.frame);
                setTimeout(function () {
                    $scope.preloader.send = false;
                    $scope.$apply();
                }, 750);
            }, function () {
                $scope.preloader.send = false;
            });
        };
// $scope.getWikiPage($scope.searchStr);

        $scope.getPage = function () {
            $scope.preloader.send = true;
            PageService.findPage($scope.sourceUrl).then(function (data) {
                $scope.domain = {id: data.page.id};
                $scope.frame.setAttribute("src", 'http://localhost:8090/page?id=' + data.page.id);
                $scope.frame.setAttribute("width", '100%');
                $scope.frame.setAttribute("height", '100%');
                $scope.frame.setAttribute("id", 'wiki-frame');
                document.getElementById('sm-content-wiki').appendChild($scope.frame);
                setTimeout(function () {
                    $scope.preloader.send = false;
                    $scope.$apply();
                }, 750);
            }, function () {
                $scope.preloader.send = false;
            });
        };
// $scope.getPage();

        $scope.addCurrentConcept = function () {
            ConceptService.add($scope.currentConcept).then(function (data) {
                $scope.currentConcept = data;
                $scope.pageConcepts.push($scope.currentConcept);
                $scope.showMessageModalWindow('Повідомлення!', 'Добавлено новий термін: ' + $scope.currentConcept.concept);
            });
        };

        $scope.saveCurrentConcept = function () {
            ConceptService.save($scope.currentConcept).then(function (data) {
                $scope.currentConcept = data;
                for (var i = 0; i < $scope.pageConcepts.length; i++) {
                    if ($scope.pageConcepts[i].id === $scope.currentConcept.id) {
                        $scope.pageConcepts[i] = $scope.currentConcept;
                        break;
                    }
                }
                $scope.showMessageModalWindow('Повідомлення!', 'Оновлено термін: ' + $scope.currentConcept.concept);
            });
        };

        $scope.removeCurrentConcept = function () {
            ConceptService.removeById($scope.currentConcept.id).then(function () {
                for (var i = 0; i < $scope.pageConcepts.length; i++) {
                    if ($scope.pageConcepts[i].id === $scope.currentConcept.id) {
                        $scope.pageConcepts.splice(i, 1);
                        break;
                    }
                }
                $scope.showMessageModalWindow('Повідомлення!', 'Видалено термін: ' + $scope.currentConcept.concept);
                $scope.initConcept('', '', '', $scope.domain, []);
            })
        };

        $scope.changeCurrentThesis = function (index) {
            $scope.currentThesis = $scope.currentConcept.theses[index];
        };

        $scope.addCurrentThesis = function () {
            $scope.currentThesis.concept = {id: $scope.currentConcept.id};
            ThesisService.add($scope.currentThesis).then(function (data) {
                $scope.currentThesis = data;
                $scope.getPageConcepts();
                $scope.showMessageModalWindow('Повідомлення!', 'Добавлено нову тезу: ' + $scope.currentThesis.thesis);
            });
        };


        $scope.saveCurrentThesis = function () {
            $scope.currentThesis.concept = {id: $scope.currentConcept.id};
            ThesisService.save($scope.currentThesis).then(function (data) {
                $scope.currentThesis = data;
                for (var i = 0; i < $scope.currentConcept.theses.length; i++) {
                    if ($scope.currentConcept.theses[i].id === $scope.currentThesis.id) {
                        $scope.currentConcept.theses[i] = $scope.currentThesis;
                        break;
                    }
                }
                $scope.showMessageModalWindow('Повідомлення!', 'Оновлено тезу: ' + $scope.currentThesis.thesis);
            })
        };

        $scope.removeCurrentThesis = function () {
            ThesisService.removeById($scope.currentThesis.id).then(function () {
                for (var i = 0; i < $scope.currentConcept.theses.length; i++) {
                    if ($scope.currentConcept.theses[i].id === $scope.currentThesis.id) {
                        $scope.currentConcept.theses.splice(i, 1);
                        break;
                    }
                }
                $scope.showMessageModalWindow('Повідомлення!', 'Видалено тезу: ' + $scope.currentThesis.thesis);
                $scope.initThesis('', '', $scope.currentConcept, $scope.domain);
            });
        };

        $scope.toggleWiki = function () {
            // $scope.showWiki = !$scope.showWiki;
            // console.log($scope.currentConcept.concept);
            if ($scope.showWiki && $scope.currentConcept.concept !== undefined && $scope.currentConcept.concept.length > 0) {
                $scope.getWikiPage($scope.currentConcept.concept);
            }
        };

        $scope.getConceptPage = function (page) {
            $scope.preloader.send = true;
            $scope.sourceUrl = MainService.prepareUrl(page);
            $scope.frame.setAttribute("src", 'http://localhost:8090/page?id=' + page.id);
            // $scope.frame.setAttribute("width", '100%');
            // $scope.frame.setAttribute("height", '100%');
            // $scope.frame.setAttribute("id", 'wiki-frame');
            document.getElementById('sm-content-wiki').appendChild($scope.frame);
            setTimeout(function () {
                $scope.preloader.send = false;
                $scope.$apply();
            }, 750);
        };

        $scope.getDetail = function (id) {
            window.open("/concept.html?id=" + id, '_blank');
        };

    }

]);
