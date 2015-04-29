(function(){
    'use strict';
angular.module('ideaApp')
.controller('app.Controller', ['Data','IdeasSelectedByTag', 'Rate', AppController]);

    function AppController(Data, IdeasSelectedByTag, Rate) {
	
	var vm = this;

	vm.ideas = Data.getIdeas();

	vm.selectByCategory =function (tag) {
	     vm.ideas = IdeasSelectedByTag.getIdeas(tag);
	 };
	  
	vm.sortparam = '-rate';
	  
	vm.changeRate = function (mark, idea) {
	    idea=Rate.changeRate(mark, idea);
	};
  
  
};
})();

 

      
      
