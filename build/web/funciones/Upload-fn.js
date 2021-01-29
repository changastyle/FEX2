
$scope.urlUploadPersonas = "uploadFotoPersona";
$scope.urlUploadCategorias = "uploadFotoCategoria";
$scope.urlUploadFotoGrupo = "uploadFotoGrupo";
$scope.urlUploadAdjuntos = "uploadAdjunto";
$scope.urlASubirCorrespondienteCarpeta = "uploadOne";
$scope.urlUploadXLS = "uploadXLS";

// FUNCIONES DE UPLOAD SEGUN TIPO DE SUBIDA Y CARPETA:
$scope.uploadFotoHist = function(formularioID)
{
    $scope.urlASubirCorrespondienteCarpeta = $scope.urlUploadPersonas;
    $scope.$evalAsync();
    return $scope.uploadMany(formularioID);
}
$scope.uploadFotoCategoria = function(formularioID)
{
    $scope.urlASubirCorrespondienteCarpeta = $scope.urlUploadCategorias;
    
    rta = $scope.uploadMany(formularioID);
    console.log("rta:"+ JSON.stringify(rta[0]));
    $scope.categoria.foto = rta[0];
    $scope.$evalAsync();
    
    return  rta;
}
$scope.uploadFotoProducto = function(formularioID)
{
    console.log("SOY YO");
    $scope.urlASubirCorrespondienteCarpeta = $scope.urlUploadCategorias;
    
    rta = $scope.uploadMany(formularioID);
    console.log("rta:"+ JSON.stringify(rta[0]));
    
    for(i = 0 ; i < rta.length; i++)
    {
        fotoProducto = $scope.getFotoProductoEmpty($scope.producto.arrFotosProducto);
        fotoProducto.foto = rta[i];
        $scope.producto.arrFotosProducto.push(fotoProducto);
        
    }
//    fotoProducto.id = -1;
//    obj = JSON.parse('{"id":-1,"foto":"' + JSON.stringify(rta[0]) + '"}');
    $scope.$evalAsync();
    
    return  rta;
}
//$scope.uploadXLS = function(formularioID)
//{
//    console.log("SOY YO");
//    $scope.urlASubirCorrespondienteCarpeta = $scope.urlUploadXLS;
//    $scope.$evalAsync();
//    
//    rta = $scope.uploadMany(formularioID);
//    console.log("rta:"+ JSON.stringify(rta[0]));
//    
//    console.log("RTA UPLOAD XLS: "+ rta );
////    for(i = 0 ; i < rta.length; i++)
////    {
////        fotoProducto = $scope.getFotoProductoEmpty($scope.producto.arrFotosProducto);
////        fotoProducto.foto = rta[i];
//        
////        $scope.producto.arrFotosProducto.push(fotoProducto);
//        
////    }
////    fotoProducto.id = -1;
////    obj = JSON.parse('{"id":-1,"foto":"' + JSON.stringify(rta[0]) + '"}');
//    $scope.$evalAsync();
//    
//    return  rta;
//}
$scope.archivoSubido = null;
$scope.uploadXLS = function(formularioID)
{
    respuesta = null;
    console.log("VOY A SUBIR ARCHIVO DEL FORMULARIO " + formularioID + " @ " + $scope.urlASubirCorrespondienteCarpeta);
    var formData = new FormData(document.getElementById(formularioID));

    $scope.archivoSubido = null;
    
    $.ajax({
        url: "../../uploadXLS",
//        async: false,
        type: "post",
        dataType: "html",
        data: formData,
        cache: false,
        contentType: false,
        processData: false,
        xhr: function()
        {
            //UPLOAD PROGRESS:
            var xhr = $.ajaxSettings.xhr();
            if (xhr.upload) 
            {
                console.log("UPLOADING");
                xhr.upload.addEventListener('progress', function(event) 
                {
                    var percent = 0;
                    var position = event.loaded || event.position;
                    var total = event.total;

                    console.log("TOTAL : " + total);
                    if (event.lengthComputable)
                    {
                        percent = Math.ceil(position / total * 100);
                    }

                    //update progressbar
                    $(".progress-bar").css("width", + percent +"%");

                    $(".status").text(percent +"%");
                }, true);
            }

            return xhr;
        },
        success: function (resultado, textStatus, jqXHR) 
        {
            console.log("respueta upload file: " + resultado);
            respuesta = JSON.parse(resultado);
            $scope.archivoSubido = respuesta[0];
            $scope.$evalAsync();
        },
        cache: false,
        processData: false
    });
    
    $scope.$evalAsync();
    
    return respuesta;
}
$scope.uploadFotoPromo = function(formularioID)
{
    $scope.urlASubirCorrespondienteCarpeta = $scope.urlUploadAdjuntos;
    
    rta = $scope.uploadMany(formularioID);
    console.log("rta:"+ JSON.stringify(rta));
    
    $scope.promoEditando.foto = rta[0];
    
    $scope.$evalAsync();
    return  rta;
}
//$scope.uploadFotoPersona = function(formularioID)
//{
//    $scope.urlASubirCorrespondienteCarpeta = $scope.urlUploadPersonas;
//    $scope.$evalAsync();
//    return $scope.uploadOne(formularioID);
//}
//$scope.uploadFotoGrupo = function(formularioID)
//{
//    $scope.urlASubirCorrespondienteCarpeta = $scope.urlUploadFotoGrupo;
//    $scope.$evalAsync();
//    return $scope.uploadOne(formularioID);
//}
//$scope.uploadAdjunto = function(formularioID)
//{
//    $scope.urlASubirCorrespondienteCarpeta = $scope.urlUploadAdjuntos;
//    $scope.$evalAsync();
//    return $scope.uploadOne(formularioID);
//}



 // NO TOCAR:
$scope.ultUpload;
$scope.uploadOne = function(formularioID)
{
    respuesta = null;
    console.log("VOY A SUBIR ARCHIVO DEL FORMULARIO " + formularioID + " @ " + $scope.urlASubirCorrespondienteCarpeta);
    var formData = new FormData(document.getElementById(formularioID));

    ya = new Date();
    if(($scope.ultUpload.getTime() - ya.getTime()) > 1000)
    {
        $scope.ultUpload = new Date();
        
        $.ajax({
            url: "../../" + $scope.urlASubirCorrespondienteCarpeta,
            async: false,
            type: "post",
            dataType: "html",
            data: formData,
            cache: false,
            contentType: false,
            processData: false,
            xhr: function()
            {
                var xhr = new window.XMLHttpRequest();

                //UPLOAD PROGRESS:
                xhr.upload.addEventListener("progress", function(evt)
                {
                    if (evt.lengthComputable) 
                    {
                        var percentComplete = evt.loaded / evt.total;

                        porcentaje = Math.round(percentComplete * 100 );
                        console.log("PORCENTAJE UPLOAD: " + porcentaje+ "%");

                        $("#porcentaje-carga-" + formularioID).html( porcentaje + "%");
                    }
                }, false);

                //DOWNLOAD PROGRESS:
                xhr.addEventListener("progress", function(evt)
                {
                    if (evt.lengthComputable) 
                    {
                        var percentComplete = evt.loaded / evt.total;
                        console.log("PORCENTAJE DOWNLOAD: " + porcentaje+ "%");
                    }
                }, false);

              return xhr;
            },
            success: function (resultado, textStatus, jqXHR) 
            {
                console.log("respueta upload one: " + resultado);
                respuesta = resultado;
            },
            cache: false,
            processData: false
        });
    }
    

    
    return respuesta;
}
$scope.uploadMany = function(formularioID)
{
    respuesta = null;
    console.log("VOY A SUBIR ARCHIVO DEL FORMULARIO " + formularioID + " @ " + $scope.urlASubirCorrespondienteCarpeta);
    var formData = new FormData(document.getElementById(formularioID));

    $.ajax({
        url: "../../uploadMany",
        async: false,
        type: "post",
        dataType: "html",
        data: formData,
        cache: false,
        contentType: false,
        processData: false,
        xhr: function()
        {
            var xhr = new window.XMLHttpRequest();

            //UPLOAD PROGRESS:
            xhr.upload.addEventListener("progress", function(evt)
            {
                if (evt.lengthComputable) 
                {
                    var percentComplete = evt.loaded / evt.total;

                    porcentaje = Math.round(percentComplete * 100 );
                    console.log("PORCENTAJE UPLOAD: " + porcentaje+ "%");

                    $("#porcentaje-carga-" + formularioID).html( porcentaje + "%");
                }
            }, false);

            //DOWNLOAD PROGRESS:
            xhr.addEventListener("progress", function(evt)
            {
                if (evt.lengthComputable) 
                {
                    var percentComplete = evt.loaded / evt.total;
                    console.log("PORCENTAJE DOWNLOAD: " + porcentaje+ "%");
                }
            }, false);

          return xhr;
        },
        success: function (resultado, textStatus, jqXHR) 
        {
            console.log("respueta upload one: " + resultado);
            respuesta = JSON.parse(resultado);
        },
        cache: false,
        processData: false
    });
    
    return respuesta;
}
//$scope.uploadMany = function(formularioID)
//{
//    respuesta = null;
//    console.log("VOY A SUBIR ARCHIVO DEL FORMULARIO " + formularioID + " @ " + $scope.urlASubirCorrespondienteCarpeta);
//    var formData = new FormData(document.getElementById(formularioID));
//
//    $.ajax({
//        url: "../../uploadMany",
//        async: false,
//        type: "post",
//        dataType: "html",
//        data: formData,
//        cache: false,
//        contentType: false,
//        processData: false,
//        xhr: function()
//        {
//            var xhr = new window.XMLHttpRequest();
//
//            //UPLOAD PROGRESS:
//            xhr.upload.addEventListener("progress", function(evt)
//            {
//                if (evt.lengthComputable) 
//                {
//                    var percentComplete = evt.loaded / evt.total;
//
//                    porcentaje = Math.round(percentComplete * 100 );
//                    console.log("PORCENTAJE UPLOAD: " + porcentaje+ "%");
//
//                    $("#porcentaje-carga-" + formularioID).html( porcentaje + "%");
//                }
//            }, false);
//
//            //DOWNLOAD PROGRESS:
//            xhr.addEventListener("progress", function(evt)
//            {
//                if (evt.lengthComputable) 
//                {
//                    var percentComplete = evt.loaded / evt.total;
//                    console.log("PORCENTAJE DOWNLOAD: " + porcentaje+ "%");
//                }
//            }, false);
//
//          return xhr;
//        },
//        success: function (resultado, textStatus, jqXHR) 
//        {
//            console.log("respueta upload one: " + resultado);
//            respuesta = JSON.parse(resultado);
//        },
//        cache: false,
//        processData: false
//    });
//    
//    return respuesta;
//}