/**
 * Main module
 */
var main = (function(){

  /**
   * Init function
   */
  var init = function(){
    populateData();
  };

  /**
   * Populates data into a list in DOM
   */
  var populateData = function(){
    $.getJSON( "/listItems", function( data ) {
      var invList = $('#inventory_list');
      $.each( data, function( key, val ) {
        invList.append(
            $(document.createElement('li')).css({'width':'200px'})
            .append($(document.createElement('div')).append(val.id).css({'float':'left', 'margin-right':'10px'}))
            .append($(document.createElement('div')).append(val.name).css({'float':'left', 'margin-right':'10px'}))
            .append($(document.createElement('div')).append(val.desc))
        );
      });

    });
  };

  return {
    init: init
  };
})();