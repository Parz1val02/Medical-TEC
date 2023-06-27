var tipoFiltro = {
    0:['Seleccione un tipo de filtro'],
    1:['Cardiología','Traumatología','Ginecología','Urología','Medicina Interna','Neumología', 'Pediatría','Neurología','Gastroenterología', 'Endocrinología','Otorrinolaringología','Nefrología','Dermatología','Psiquiatría','Cirugía general','Oncología','Oftalmología','Otorrinolaringología','Odontología' ],
    2:['Rimac-EPS','Rimac seguros','Pacifico EPS','Pacifico Seguros','Mapfre','Plan Salud','Sin seguro'],
    3:['Enero', 'Febrero','Marzo','Abril','Mayo','Junio','Julio','Agosto','Setiembre','Octubre', 'Noviembre','Diciembre']
}

//var newsub = document.getElementById('floatingInputGrid100')?document.getElementById('floatingInputGrid100').value:"";
var main = document.getElementById('tipoFiltro'); //padre
var sub = document.getElementById('filtrado');  //dependiente


function selectDinamico(idTipoFiltro) {
    var selected_option = tipoFiltro[idTipoFiltro];
    while(sub.options.length > 0) {
        sub.options.remove(0);
    }
    Array.from(selected_option).forEach(function (e1,index) {
        let option;
        if (e1 == 'Seleccione un tipo de filtro' && index==0) {
           option = new Option(e1,0);
       } else {
           option = new Option(e1,index + 1);
       }

        sub.appendChild(option);
    });
}

document.addEventListener("DOMContentLoaded", function(event) {
    selectDinamico(main.value);
});

main.addEventListener('change',function() {
    selectDinamico(this.value);
});
