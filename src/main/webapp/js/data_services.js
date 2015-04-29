
'use strict';
var testModule = angular.module('app.testData', []);

var testService = testModule.value('Data', [ 
    new Idea("First idea", "First idea","Olga", "First", new Date(2015, 0, 2, 2, 3, 4),"Sport",1),
     new Idea("Second idea", "qqqqq Second idea Second idea Second idea Second idea Second idea"+
      " Second idea Second idea Second idea  Hi Hi Hi Hi Second idea Second idea Hi" ,"Alex", "Second",
      new Date(2015, 2, 5, 2, 3, 4),"Culture",-100),
     new Idea("Third idea", "First idea","Igor", "Third", new Date(2015, 1, 28, 2, 3, 4),"Sport"),
     new Idea("Fourth idea", "Fourth idea","Nataly", "Fourth", new Date(2015, 1, 28, 2, 3, 4),"Culture", -100)
    ]);
    
 function Idea(name, description, author, url, date, tag, rate) {
  this.name = name || "";
  this.description = description || "";
  this.author = author || "";
  this.url = url || "";
  this.date = date || new Date(2015, 0, 1);
  this.rate = rate || 0;
  this.tag = tag || "";
  };


    
