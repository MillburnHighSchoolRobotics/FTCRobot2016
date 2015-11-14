app.controller('MainController', ['$scope', function($scope) {

	$scope.competitions = [];
	$scope.q = {
		competition: "",
		tmnumber: 0,
		isMatch: false
	};
	$scope.matchData = {
		name: "",
		number: 0,
		date: "",
		data: []
	}
	$scope.teamData = {
		name: "",
		number: 0,
		matches: []
	};

	function search() {
		$(".data-window").hide();
		if ($scope.q.competition != "" && $scope.q.tmnumber != "" && $.isNumeric($scope.q.tmnumber)) {
			if ($scope.q.isMatch) {
				$scope.fetchMatchData();
			}
			else {
				$scope.fetchTeamData();
			}
		}
	}

	$scope.setQueryCompetition = function(c) {
		$scope.$apply(function() {
			$scope.q.competition = c;
		});
		search();
	}

	$scope.setQueryNumber = function(n) {
		$scope.$apply(function() {
			$scope.q.tmnumber = n;
			$scope.q.isMatch = n < 15;
		});
		search();
	}

	$scope.getQueryCompetition = function() {
		return $scope.q.competition;
	}

	$scope.getQueryNumber = function() {
		return $scope.q.tmnumber;
	}

	$scope.fetchCompetitions = function() {
		var Competition = Parse.Object.extend("Competition");
		var query = new Parse.Query(Competition);
		query.find({
			success: function(results) {
				$scope.$apply(function() {
					$scope.competitions = results;
				});
			},
			error: function(error) {

			}
		});
	}

	$scope.fetchTeamData = function() {
		var Team = Parse.Object.extend("Team");
		var query = new Parse.Query(Team);
		query.equalTo("number", parseInt($scope.q.tmnumber));
		query.find({
			success: function(results) {
				if (results.length == 0) {
					$(".data-window").hide();
					$("#no-data").show();
					return;
				}
				$scope.$apply(function() {
					$scope.teamData.name = results[0].attributes.name;
					$scope.teamData.number = results[0].attributes.number;
					$(".data-window").hide();
					$("#team-dw").show();
				});
			},
			error: function(error) {
				return false;
			}
		});

			var MatchData = Parse.Object.extend("MatchData");
			var mdquery = new Parse.Query(MatchData);
			//mdquery.equalTo("teamNumber", parseInt($scope.teamData.number));
			//mdquery.equalTo("competitionName", parseInt($scope.q.competition));
			mdquery.find({
				success: function(results) {
					if (results.length == 0) {
						console.log("This team did not compete in this competition.");
						return;
					}
					$scope.$apply(function() {
						$scope.teamData.matches = results;
					});
				},
				error: function(error) {
					return;
				}
			});
	}

	$scope.fetchMatchData = function() {
		var Match = Parse.Object.extend("Match");
		var query = new Parse.Query(Match);
		query.equalTo("matchNumber", parseInt($scope.q.tmnumber));
		query.equalTo("competitionName", $scope.q.competition);
		query.find({
			success: function(results) {
				if (results.length == 0) {
					$(".data-window").hide();
					$("#no-data").show();
					return;
				}
				$scope.$apply(function() {
					$scope.matchData.name = results[0].attributes.competitionName;
					$scope.matchData.number = results[0].attributes.matchNumber;
					$scope.matchData.date = results[0].attributes.competitionDate.toString();
					$(".data-window").hide();
					$("#match-dw").show();
					return;
				});
			},
			error: function(error) {
				return false;
			}
		});

			var MatchData = Parse.Object.extend("MatchData");
			mdquery = new Parse.Query(MatchData);
			mdquery.equalTo("matchNumber", parseInt($scope.q.tmnumber));
			mdquery.find({
				success: function(results) {
					if (results.length == 0) {
						console.log("There is no data on this match.");
						return;
					}
					$scope.$apply(function() {
						$scope.matchData.data = results;
					});
				},
				error: function(error) {
					return;
				}
			});
	}

}]);