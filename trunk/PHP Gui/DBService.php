<?php

class DBService{
	public $conn;
	private $result;

	public function __construct($configPath){
		$this->config = $this->getConfig($configPath);
		$this->conn = mysql_connect($this->config['host'],$this->config['user'],$this->config['pass']);
		mysql_select_db($this->config['database'], $this->conn);
	}

	public function query($sql){
		 $this->result = mysql_query($sql, $this->conn);
		 return $this->result;
	}
	 
	public function numRows(){
		return mysql_num_rows($this->result);
	}
	 
	public function getObjectResult($result){
		if(is_null($result)){
			$result = $this->result;
		}
		return mysql_fetch_object($result);
	}

	public function getConfig($configPath){

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
}

?>
