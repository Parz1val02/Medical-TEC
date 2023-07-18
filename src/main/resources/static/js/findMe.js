window.addEventListener('DOMContentLoaded', () => {
    const dniInput = document.getElementById('dni');
    const nameInput = document.getElementById('name');
    const surnameInput = document.getElementById('surname');
    const dniValidationMessage = document.getElementById('dniValidationMessage');

    //validos
    const nomInput = document.getElementById('nom');
    const lenomInput=document.getElementById('lenom');


    dniInput.addEventListener('input', () => {
        const dni = dniInput.value;
        dniValidationMessage.textContent = '';
        if (dni.length === 8) {
            // Make API request to retrieve data
            const url1 = '/dni/'+dni;

            fetch(url1)
            .then(response => response.json())
                    .then(data => {
                        if (data) {
                            //nameInput.value = 'DNI already exists in the database';
                            dniValidationMessage.textContent="El dni ya se encuentra la base de datos o en proceso de registro";

                        }else{
                            const url = `https://dniruc.apisperu.com/api/v1/dni/${dni}?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImVkdXBpc2NvdGUuY2VwdEBnbWFpbC5jb20ifQ.EBZ7aEbbb51PcGiWtZwV1RopnJsYV3m8XqyRv4xotok`;

                            fetch(url)
                                .then(response => response.json())
                                .then(data => {
                                    // Populate the other input fields
                                    nameInput.value = data.nombres!== undefined ? data.nombres : 'Datos no encontrados';
                                    surnameInput.value = data.apellidoPaterno !== undefined && data.apellidoMaterno !== undefined
                                        ? data.apellidoPaterno + ' ' + data.apellidoMaterno
                                        : 'Datos no encontrados';

                                    nomInput.value = data.nombres!== undefined ? data.nombres : 'Datos no encontrados';
                                    lenomInput.value = data.apellidoPaterno !== undefined && data.apellidoMaterno !== undefined
                                        ? data.apellidoPaterno + ' ' + data.apellidoMaterno
                                        : 'Datos no encontrados';

                                })
                                .catch(error => {
                                    console.error('Error:', error);
                                    // Clear the other input fields in case of error
                                    nameInput.value = '';
                                    surnameInput.value = '';
                                });
                        }
            })


        } else {
            // Clear the other input fields if DNI length is not 8
            nameInput.value = '';
            surnameInput.value = '';
        }
    });
});