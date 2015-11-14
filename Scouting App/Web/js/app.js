Parse.initialize("SlG9zvrlCyjen53XU3WUaf3HAYoZQpra08iCLQNC", "GoVut8xNUmBMewqNWftkv0rXTz5RR66Vj2kTW9Kr");

var app = angular.module('app', []);

$(window).load(function() {
	angular.element($(".main")).scope().fetchCompetitions();
});

$(document).on('click', '#competitions li', function(event) {
	$("#competitions li").removeClass("selected");
	$(event.target).addClass("selected");
	angular.element($(".main")).scope().setQueryCompetition($(event.target).text().substring(0, $(event.target).text().length));
});

$("#tmnumber").on('keyup', function(e) {
    if (e.which == 13) {
        e.preventDefault();
    }
    angular.element($(".main")).scope().setQueryNumber($("#tmnumber").val());
});

