/**
 * Main JS module
 */
var main = (function(){

  /**
   * Init function
   *
   * @memberOf functions
   */
  var init = function(){
    populateData();
  };

  /**
   * Populates data into a list in DOM
   *
   * @memberOf functions
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
              )
            )
        );
      });
    });
  };

  /**
   * Exposed API
   */
  return {
    init: init
  };
})();