<%-- 
    Document   : home
    Created on : 15/01/2021, 10:36:06
    Author     : Nico
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FEX</title>
        
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular.min.js"></script>
    </head>
    <body ng-app="app" ng-controller="fex" ng-init="init()"  ng-cloak>
        <h1>SUBI TU EXCEL!</h1>
        
        <form id="formulario-upload-foto-xls" 
            class="sin-padding" 
            enctype="multipart/form-data" 
            method="POST">
                        
            <!-- INPUT FILE:-->
            <input id="input-upload-foto-xls" 
                class="input-upload-foto-xls" 
                name="archivos" type="file"  
                accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
            
            <div id="progress-wrp">
                <div class="progress-bar"></div >
                <div class="status">0%</div>
            </div>
        </form>
        
        <button type="button" class="btn btn-primary col-12" 
            ng-show="archivoSubido != null"
            ng-click="procesarExcelProductos()">
            PROCESAR EXCEL
        </button>
        
            
        <p>RTA:{{archivoSubido}}</p>
    </body>
    <script>
        app = angular.module('app', []);
        app.controller('fex', function($scope)
        {
            
        <%@include file="../../funciones/Upload-fn.js" %>
                
            $scope.init = function()
            {
               console.log("hola"); 
            };
            
            $('#input-upload-foto-xls').on('change', function()
            {
                console.log("CAMBIO INPUT UPLOAD EXCEL");
                $scope.uploadXLS("formulario-upload-foto-xls");
            });
            
            $scope.procesarExcelProductos = function()
            {
                console.log("ENVIO SOLICITUD PROCESAR EXCEL!!");
                
                $.ajax(
                {
                    url:"../../procesarExcelProductos",
                    data:
                    {
                        "url":JSON.stringify($scope.archivoSubido)
                    },
                    beforeSend: function (xhr) 
                    {
                        $scope.cargandoProcesarExcel = true;
                    },
                    success: function (resultado, textStatus, jqXHR) 
                    {
                        if(resultado)
                        {
                            window.location.reload();
                        }
//                        $scope.procesadoOK = resultado;
                        $scope.cargandoProcesarExcel = false;
//                        $scope.$evalAsync();
                    }

                });
            }
        });
    </script>
    <style>
    #progress-wrp 
    {
        border: 1px solid #0099CC;
        padding: 1px;
        position: relative;
        border-radius: 3px;
        margin: 10px;
        text-align: left;
        background: #fff;
        box-shadow: inset 1px 3px 6px rgba(0, 0, 0, 0.12);
    }
    #progress-wrp .progress-bar
    {
        height: 20px;
        border-radius: 3px;
        background-color: #79f763;
        width: 0;
        box-shadow: inset 1px 1px 10px rgba(0, 0, 0, 0.11);
    }
    #progress-wrp .status
    {
        top:3px;
        left:50%;
        position:absolute;
        display:inline-block;
        color: #000000;
    }
    </style>
</html>


