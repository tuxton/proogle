<?php
require_once 'DBService.php';

function getConnection(){
    return new DBService('config.conf');
}
function getSingleMatch($db, $busqueda, $metodoRanking){

    //SI SOLO HAY UNA PALABRA DE BUSQUEDA SE ESTABLECE UNA INSTRUCION CON LIKE
    $sql = "SELECT idArticulo FROM
							articulos a, paginas p, ranking r
					WHERE p.idPagina = a.idPagina
						  AND p.idPagina = r.idPagina
						  AND (texto LIKE '%$busqueda%'
						  OR dominio LIKE '%$busqueda%')
	GROUP BY idArticulo
	ORDER BY r.$metodoRanking DESC";

    return $db->query($sql);
}


function getMultiMatch($db, $busqueda, $metodoRanking){

    //SI HAY UNA FRASE SE UTILIZA EL ALGORTIMO DE BUSQUEDA AVANZADO DE MATCH AGAINST
    //busqueda de frases con mas de una palabra y un algoritmo especializado
    $sql = "SELECT idArticulo , MATCH ( dominio, texto ) AGAINST ( '*$busqueda*'IN BOOLEAN MODE ) AS Score FROM
						   articulos a, paginas p, ranking r
					WHERE MATCH ( dominio, texto ) AGAINST ( '*$busqueda*'IN BOOLEAN MODE )
						  AND a.idPagina = p.idPagina
						  AND p.idPagina = r.idPagina
					GROUP BY idArticulo
					ORDER BY r.$metodoRanking DESC, Score DESC ";

    return $db->query($sql);
}

function getArticuloDetalle($db, $idArticulo){

    $sql = "SELECT * FROM
					articulos a, paginas p, ranking r 
				WHERE a.idPagina = p.idPagina 
					  AND p.idPagina = r.idPagina 
					  AND idArticulo =%s 
				GROUP BY a.idArticulo";

    return $db->query(sprintf($sql,$idArticulo));

}

function getTipoMetodoPR(){

    if(isset($_GET['filter'])){
        return $_GET['filter'];
    }
    return 'PageRank';
}


function getResultIterativo($db,$busqueda,$metodoRanking){

    //CUENTA EL NUMERO DE PALABRAS
    $trozos = explode(" ",$busqueda);
    $numero = count($trozos);

    //DETERMINA CUAL METODO USAR EN BASE AL TIPO DE CONSULTA
    if ( $numero == 1 ) {
        $result = getSingleMatch($db, $busqueda, $metodoRanking);
    }
    elseif ($numero > 1) {
        $result = getMultiMatch($db, $busqueda, $metodoRanking);
    }

    $contador = 0;
    $cantidad = $db->numRows();

    if($cantidad > 0){
        $valores = "<br><div class=\"info\">Cantidad de resultados: <strong>$cantidad</strong><br>Tipo de busqueda: <strong>$metodoRanking</strong></div><br>";
    }else{
        $valores = "<br><div class=\"info\">La consulta <strong>$busqueda</strong> no obtuvo resultados.</div><br>";
    }

    While($articulo = $db->getObjectResult($result))
    {
        $contador++;
        $resultArt = getArticuloDetalle($db, $articulo->idArticulo);
        $detalle = $db->getObjectResult($resultArt);

        //ARMAMOS EL RESULTADO PARA CADA COINCIDENCIA
        $referencia = $detalle->dominio;
        $cuerpoArticulo = substr($detalle->texto,0,300).'<strong> ...</strong>';
        $variablesObj = get_object_vars($detalle);
        $valores .=  "<div align=\"center\"><b><a href=\"http://$referencia\">$contador - $referencia</a></b> <br>".$cuerpoArticulo.
                "<br><div class=\"prk\">$metodoRanking: $variablesObj[$metodoRanking]</div></div><br>";
    }
    return $valores;
}

function getResultMMR($db,$metodoRanking){

    $config = getConfig('config.conf');

    $mrr = @fopen($config['MRRfilename'], "r");

    if(!$mrr) {
        return false;
    }

    $datosMRR = array();
    $i=0;
    while(!feof($mrr)){
        $datosMRR_busqueda = array();
        $busqueda = fgets($mrr);
        $busqueda = explode(",",$busqueda);
        $datosMRR_busqueda['busqueda'] = $busqueda[0];
        $datosMRR_busqueda['idPagina'] = $busqueda[1];
        $datosMRR[$i] = $datosMRR_busqueda;
        $i++;
    }
    fclose($mrr);

    if(sizeof($datosMRR) > 0){

        $resultado = getCalculoMRR($db,$metodoRanking,$datosMRR);
        if($resultado > 0){
            return "<br><div>El calculo de MRR sobre un total de <strong>".count($datosMRR)."</strong> consultas  arroja el porcentaje: <strong>$resultado</strong></div><br>";
        }
    }
    return "<br><div class=\"info\">La consulta no obtuvo resultados. (verifique el archivo de consultas)</div><br>";
}

function getCalculoMRR($db,$metodoRanking,$datosMRR){
    $resultado = 0;
    foreach($datosMRR as $dato){

        //CUENTA EL NUMERO DE PALABRAS
        $trozos = explode(" ",$dato['busqueda']);
        $numero = count($trozos);

        //DETERMINA CUAL METODO USAR EN BASE AL TIPO DE CONSULTA
        if ( $numero == 1 ) {
            $result = getSingleMatch($db, $dato['busqueda'], $metodoRanking);
        }
        elseif ($numero > 1) {
            $result = getMultiMatch($db, $dato['busqueda'], $metodoRanking);
        }

        $contador = 0;
        $cantidad = $db->numRows();

        if($cantidad > 0){
            $posicion = 0;
            $encontre = false;
            While(($articulo = $db->getObjectResult($result)) && !$encontre){
                $resultArt = getArticuloDetalle($db, $articulo->idArticulo);
                $detalle = $db->getObjectResult($resultArt);
                
                
                if((int)$detalle->idPagina == (int)$dato['idPagina']){
                    $encontre = true;
                }
                $posicion++;
            }
            if($encontre){
                
                $resultado += (1/$posicion);
            }
        }
    }
    return $resultado/count($datosMRR);
}



function getConfig($configPath){

    $handle = @fopen($configPath, "r");

    if(!$handle) {
        return false;
    }

    $logConfig = array();

    while(!feof($handle)){
        $buffer = fgets($handle, 4096);
        $datos  = split('=',$buffer);
        $key    = trim($datos[0]);
        unset($datos[0]);
        $value  = trim(implode(' ',$datos));
        $logConfig[$key]= $value;
    }

    fclose($handle);

    if(sizeof($logConfig) > 0){
        return $logConfig;
    }
    else{
        return false;
    }

}
function getResult(){

    //ESTABLECE LA CONNECTION A LA BD
    $db = getConnection();

    //VERIFICO QUE REALMENTE HAYA INGRESADO ALGO PARA REALIZAR LA BUSQUEDA
    if(!isset($_GET['q']) && !isset($_GET['mrr'])){
        return false;
    }

    //OBTENGO LA CONSULTA
    $busqueda = $_GET['q'];

    //DETERMINO EL METODO DE PAGERANK A UTILIZAR
    $metodoRanking = getTipoMetodoPR();

    //SI NO ES UNA CONSULTA VACIA
    if ($busqueda<>''){

        $valores = getResultIterativo($db,$busqueda,$metodoRanking);
    }elseif(isset($_GET['mrr']) && $_GET['mrr']){

        $valores = getResultMMR($db,$metodoRanking);
    }

    return $valores;
}
?>
