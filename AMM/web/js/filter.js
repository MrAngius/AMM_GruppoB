/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */




$(document).ready(function () {
    $("#filter").keyup(function () {

        var valore = $("#filter").val();
        
        $.ajax({
            url: "filter.json",
            data: {q: valore},
            dataType: "json",
            
            success: function (data, state) {
                
                if(data.length === 0){
                    nessunRisultato();
                } else {
                   poppola(data); 
                }  
            },
            error: function (data, state) {

            }

        });
    });
});


function nessunRisultato(){
    
    // prima eliminiamo il vecchio conenuto
    $("#client_table").empty();
    $("#empty").remove();
    
    // inserisco un messaggio
    $("#client_table").before('<div id="empty" >Nessun Risultato della ricerca</div>');
    
}

function poppola(lista) {

    // prima eliminiamo il vecchio conenuto
    $("#client_table").empty();
    $("#empty").remove();

    // ricostruiamo l html 
    $("#client_table").append("<tr><th></th><th>Prodotto</th><th>Quantità</th><th>Prezzzo</th><th></th></tr>");

    for (var obj = 0; obj < lista.length; obj++) {
   
        $("#client_table:last-child").append('<tr></tr>');
        $("#client_table tr:last-child").append('<td class = "imag_row"><img src = "'+ lista[obj].img +'" alt = "' + lista[obj].desc + '" width = "20px" height = "20px"/></td>');
        $("#client_table tr:last-child").append('<td>' + lista[obj].nome + '</td>');
        $("#client_table tr:last-child").append('<td class ="column_text_centered">' + lista[obj].quantita + '</td>');
        $("#client_table tr:last-child").append('<td class ="column_text_centered">' + lista[obj].prezzo + '</td>');
        $("#client_table tr:last-child").append('<td><a href = "cliente.html?oggetto_scelto=' + lista[obj].id + '"> Aggiungi Carrello </a></td>');
    }
}
