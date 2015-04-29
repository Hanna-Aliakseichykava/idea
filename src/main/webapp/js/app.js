'use strict';
/**
 * @name ideaApp
 *
 * @description
 * A main module.
 */
 
angular
	.module('ideaApp', [
		'app.directives',
		'app.services',
		'app.filters', 
		'app.testData',
		'app.Controller'
	]);
	
//	angular.module('ideaDirectives', []); // set Directives
//	angular.module('ideaServices', []); // set Services
	angular.module('app.Controller', []); // set Ctrls
//	angular.module('ideaFilters', []); // set Filters
