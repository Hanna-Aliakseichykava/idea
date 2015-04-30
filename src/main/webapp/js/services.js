(function() {
'use strict';

angular.module('app.services')
.service('IdeasSelectedByTag',  ['Data', IdeasSelectedByTag]); 
function IdeasSelectedByTag (Data) { 
       return {
        getIdeas:function (tag) {
          var newData = Data.getIdeas();
          if(tag !== undefined) {
              newData = [];
              for(var i=0;i<Data.getIdeas().length; i++)
                if(tag.toLowerCase() ==Data.getIdeas()[i].tag.toLowerCase())
                  newData.push(Data.getIdeas()[i]);
            }
            return newData;
          }
        };
       
};

angular.module('app.services')
.service('Rate',  Rate); 
function Rate() {
       return {
        changeRate:function (mark, idea) {
          idea.rate= idea.rate + (+mark);
          return idea;
          }
      };
};
})();
