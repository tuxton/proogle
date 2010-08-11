<?php
require_once 'Funciones.php';
?>

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
        <link rel="stylesheet" type="text/css" media="screen" href="style.css" />
        <script src="http://www.google.com/jsapi"></script>
        <script>google.load("jquery", "1.2.6");</script>
        <script>google.load("jqueryui", "1.5.3");</script> 
        <script src="main.js" type="text/javascript"></script>
        <title>Teoria de la Comunicaci&oacute;n</title>
    </head>
    <body>
        <div align="center">
            <br/>
            <br/>
            <img src="PRoogle_logo.jpg" height="150"  class="buscador" alt="PRoogle"/>
            <form id="fbuscador" name="fbuscador" method="get">
                <input class="buscador" size="50" type="text" name="q" value="<?php echo (isset($_GET['q']))?$_GET['q']:'';?>"/>
                <br/>
                <br/>
                <div class="rta">
                    <div class="info">Seleccione el tipo de Filtro:</div>
                    <input class="boton_buscador" type="submit" name="filter" value="PageRank"/>
                    <input class="boton_buscador" type="submit" name="filter" value="DomPageRank" />
                    <input class="boton_buscador" type="submit" name="filter" value="Ind" />
                    <input class="boton_buscador" type="submit" name="filter" value="HostInd" />
                    <input class="boton_buscador" type="submit" name="filter" value="HostPageRank" />
                    <input class="boton_buscador" type="submit" name="filter" value="DomInd" />
                    
                    <div class="check" align="left">
                        <br/>
                        <input class="check_mmr" type="checkbox" onclick="disableBuscador()" name="mrr" value="true">Procesar archivo por lote</input>
                    </div>
                </div>
            </form>
        </div>
        <div class="rta" align="center">
            <?php
            echo getResult();
            ?>
        </div>
    </body>
</html>