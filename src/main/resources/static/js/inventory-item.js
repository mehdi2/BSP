/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 13-May-2018.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

$(document).ready(function () {

});

function quantity(event){
    var $this = $(event);
    $this.parents('td').next().next().find('input').val(  $this.val()  * $this.parents('td').next().find('input').val()   );
}
function rate(event){
    var $this = $(event);
    $this.parents('td').next().find('input').val(    $this.val()   *   $this.parents('td').prev().find('input').val()     );
}