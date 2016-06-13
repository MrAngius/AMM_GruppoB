/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function(){
 
    $("form").submit( function(e){
        
       if ($(".input_nome input").val() === "" || $(".input_url input").val() === "" || $(".texta_desc textarea") === ""){
        
        e.preventDefault();
        
  
        if($(".input_nome input").val() === ""){
            $(".input_nome").append('<p class="error_b">Campo Vuoto</p>');
            $(".input_nome input").addClass("evidenzia_b");
        } else {
            $(".evidenzia_b").removeClass("evidenzia_b");
        $(".error_b").remove();
        }
        
        
        if($(".input_url input").val() === ""){
            $(".input_url").after('<p class="error_b">Campo Vuoto</p>');
            $(".input_url input").addClass("evidenzia_b");
        } else {
            $(".evidenzia_b").removeClass("evidenzia_b");
        $(".error_b").remove();
        }
        
        if($(".texta_desc textarea").val() === ""){
            $(".texta_desc").after('<p class="error_b">Campo Vuoto</p>');
            $(".texta_desc textarea").addClass("evidenzia_b");
        }else {
            $(".evidenzia_b").removeClass("evidenzia_b");
        $(".error_b").remove();
        }
           
    } else {
        $(".evidenzia_b").removeClass("evidenzia_b");
        $(".error_b").remove();
    }
        
    });
 
});

