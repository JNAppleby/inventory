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
      var invList = $('#inventory-list');
      $.each( data, function( key, val ) {
        invList.append(
            $(document.createElement('tr'))
            .append($(document.createElement('td')).append(val.id))
            .append($(document.createElement('td')).append(val.name))
            .append($(document.createElement('td')).append(val.desc))
            .append($(document.createElement('td')).append(
                $(document.createElement('a')).attr({'href':('/removeItem/'+val.id)}).addClass('btn btn-info').append('Remove')
            ))
        );
      });
    });
  };

  return {
    init: init
  };
})();