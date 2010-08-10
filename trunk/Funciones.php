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


function getResult(){
    
	//ESTABLECE LA CONNECTION A LA BD
    $db = getConnection();
    
	//VERIFICO QUE REALMENTE HAYA INGRESADO ALGO PARA REALIZAR LA BUSQUEDA
    if(!isset($_GET['q'])){
        return false;
    }

	//OBTENGO LA CONSULTA
    $busqueda = $_GET['q'];

	//SI NO ES UNA CONSULTA VACIA
    if ($busqueda<>''){

		//DETERMINO EL METODO DE PAGERANK A UTILIZAR
		$metodoRanking = getTipoMetodoPR();
		
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
    }
    return $valores;
}
?>