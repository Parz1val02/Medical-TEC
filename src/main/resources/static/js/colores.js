document.addEventListener("DOMContentLoaded", function() {
    // Función para obtener y mostrar los colores
    function obtenerColores() {
        console.log("clash3")

        $.ajax({
            url: "/color",
            method: "GET",
            cache: true, // Habilitar el almacenamiento en caché del navegador
            success: function(data) {
                var color1 = data.color1;
                var color2 = data.color2;

                // Aplicar los valores recibidos según sea necesario
                $("body").css("background-color", color1);
                $("#change, #change2").css("background-color",  color2);
                $('[name="change1"]').css("background-color",  color2);

                // Guardar los colores en el almacenamiento local como un objeto JSON
                var colors = {
                    color1: color1,
                    color2: color2
                };
                localStorage.setItem('colors', JSON.stringify(colors));
            }
        });
    }
    // Verificar si los colores ya están almacenados en el localStorage
    var storedColors = localStorage.getItem('colors');

    // Si los colores no están almacenados, obtenerlos a través de Ajax
    if (!storedColors) {
        obtenerColores();
    } else {
        // Si los colores están almacenados, obtenerlos del localStorage
        var storedColorsObj = JSON.parse(storedColors);
        console.log("clash2")
        // Aplicar los colores almacenados a los elementos necesarios
        $("body").css("background-color", storedColorsObj.color1);
        $("#change, #change2").css("background-color", storedColorsObj.color2);
        $('[name="change1"]').css("background-color", storedColorsObj.color2);

        // Actualizar los colores en la caché del navegador si han cambiado en la base de datos
        obtenerColores();
    }
});


