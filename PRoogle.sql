/*
SQLyog Enterprise - MySQL GUI v8.14 
MySQL - 5.0.51a : Database - tuxton
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`tuxton` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `tuxton`;

/*Table structure for table `articulos` */

DROP TABLE IF EXISTS `articulos`;

CREATE TABLE `articulos` (
  `idArticulo` int(10) unsigned NOT NULL auto_increment,
  `idPagina` int(11) unsigned NOT NULL,
  `texto` text NOT NULL,
  PRIMARY KEY  (`idArticulo`),
  FULLTEXT KEY `texto` (`texto`),
  FULLTEXT KEY `texto_2` (`texto`),
  FULLTEXT KEY `texto_3` (`texto`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `articulos` */

insert  into `articulos`(`idArticulo`,`idPagina`,`texto`) values (1,1,'Hola amigos,\r\n\r\nNuevamente estoy aca para comentarles que debido al post anterior, me llegó por parte de un amigo la noticia de que habian ingresado a su cuenta de Gmail desde china.\r\n\r\nEs por eso que se me ocurrió este post, para contarles un poco a los que como el no conocían demasiado las funcionalidades que brinda el Gmail para controlar los accesos a la cuenta.\r\n\r\nDe este problema surgen las preguntas que todos alguna vez nos hicimos:\r\n\r\n    * Me estará pasando lo mismo?\r\n    * Sospecho que alguien sabe mi pass?\r\n    * Como saber si alguien entro en mi cuenta de Gmail?\r\n    * Cuando entraron a mi cuenta por ultima vez?\r\n\r\nBueno, comenzemos. Sucede que Gmail tiene una muy buena herramienta que nos indica el historial de accesos a nuestra cuenta, con fecha y hora de los ultimos accesos y además por una cuestión de estadistica controlan el lugar desde donde se ingresó.\r\n\r\nVeamos esto en detalle:\r\n\r\nSupongamos que uno vive en Bs As mas precisamente en Capital Federal.\r\n\r\nBien, nosotros ingresamos a nuestra cuenta Gmail desde nuestra casa, pero tambien desde nuestro trabajo, y si tienes un SmartPhone ( iphone, Milestone, Nokia N9x, BlackBerry, etc) probablemente tambien tengas tu cuenta de correo configurada ahi.\r\n\r\nTodos estos accesos tienen asociada una IP del proveedor de internet que se utilize para cada dispositivo de los que antes citamos.\r\n\r\nGmail, guarda las IP y controla que frecuentemente la cuenta fue accedida desde alguna de esas IP’s.\r\n\r\nAhora bien, supongamos que un dia alguien entra desde China o EEUU a nuestra cuenta. Bien podria ser yo que estoy de viaje y reviso mi correo desde otro pais, puede ser, pero aún asi es sospechoso dado que nunca antes habian ingresado desde otro pais.\r\n\r\nEntonces Gmail te avisa al ingresar a tu cuenta algo asi :\r\n\r\n    Atención: Es probable que hayan ingresado a esta cuenta desde EEUU.\r\n\r\nAhi sabrás si es correcto o si no puede ser que alguien más ingrese y menos desde otro pais.\r\n\r\nUna vez mas surge la pregunta: Como controlar esto?\r\n\r\nSi nos dirigimos a la parte de abajo de nuestra cuenta de Gmail veremos lo siguiente:\r\n\r\nAhi mismo si tocamos el link que dice Details (Detalles si tu cuenta esta en español) y este nos llevará a la pantalla que nos informa sobre la actividad reciente de nuestra cuenta.\r\n\r\nAlgo asi:\r\n\r\nComo se puede observar en esta segunda imagen, abajo de todo nos indica cual es nuetra IP actual (desde donde estamos ingresando) y en el listado los ultimos accesos a tu cuenta. Como podemos apreciar también dentro del cuadro rojo que resalté se informa que hubo un acceso a la cuenta desde CHINA.\r\n\r\nEsto es algo para nada común, y bastante peligroso dado que la mayoria de la gente tiene en su mail información demasiado vital como para que pueda ser conocida por un pirata informático.\r\n\r\nQue hacer?: Bueno, para empezar apretar el boton que dice “sign out all other sessions” o “cerrar todas las otras sessiones” y que no se ve en la segunda imagen.\r\n\r\nUna vez que cerramos las demás sessiones, es decir nos aseguramos que nadie mas este dentro de nuestro correo. Nos tenemos que preocupar por cambiar nuestra contraseña seleccionando el link que dice “cambia la contraseña ahora mismo“.\r\n\r\nQue nueva contraseña utilizo?\r\n\r\nEste es un tema en el cual me quiero detener. Las contraseñas son muy importantes, no es algo menor y debemos entender que en la actualidad hay maquinas y herramientas muy modernas dedicadas a recuperar contraseñas por diversos metodos como puede ser fuerza bruta\r\n\r\nCon lo cual la contraseña que pongas debe ser realmente fuerte.\r\n\r\nAlgunos consejos:\r\n\r\n    * No utilizes solo letras\r\n    * No utilizes solo numeros\r\n    * No utilizes contraseñas cortas (menos de 8 caracteres)\r\n    * Utiliza letras y numeros no consecutivos\r\n    * Utiliza mayusculas y minusculas\r\n    * Utiliza simbolos (@!”#$%&/()=?¡[]{})\r\n\r\nDe esta forma lograrás tener una contraseña que no sea facil de descubrir por nadie que te conoce (fecha de cumpleaños o aniversarios) ni por ninguna herramienta que se dedique a sacar contraseñas.\r\n\r\nPara que puedas probar lo que les digo, les voy a dejar un link de una herramienta que nos sirve para ver cuan debil o fuerte son nuestras contraseñas habituales.\r\n\r\nTengan la tranquilidad que esta herramienta no guarda contraseñas, solo te informa como deberias construirla para lograr tener una contraseña fuerte.\r\n\r\naca el link de la pagina passwordmeter\r\n\r\nEspero que este post les sea de utilidad. Recuerden que la fortaleza de sus contraseñas son aplicables a cualquier otra aplicacion o sistema que utilizen y no solo para la seguridad de sus cuentas de correo.\r\n\r\nSaludos!'),(2,2,'\r\n\r\nEl mundo de los hackers puede dividirse en tres grupos. Los black hats violan los sistemas informáticos de las empresas por diversión y en busca de ganancias , y toman números de tarjetas de crédito y direcciones de e-mail para venderlas o cambiarlas a otros hackers. Los white hats ayudan a las empresas a detener a sus perjudiciales pares .\r\n\r\nPero es el tercer grupo, el de los gray hats , el que resulta más problemático para las empresas. Estos hackers actúan de varias formas y pueden dejar a una empresa lo suficientemente vulnerable como para perder activos, así como con su reputación manchada a medida que quedan al descubierto sus imperfecciones en materia de seguridad. Los apodos elegidos tienen que ver con los westerns, en donde el villano usa sombrero negro y el héroe uno blanco.\r\n\r\nEstos hackers de gray hat violan las computadoras de una empresa para encontrar los puntos débiles de su seguridad. Eligen luego si notificar a la empresa y guardar silencio hasta que el problema ha sido solucionado o si avergonzar a la compañía con la difusión del problema.\r\n\r\nEl debate entre todos estos grupos sobre cuál es el mejor plan de acción no ha sido resuelto y será uno de los temas a tratar durante la conferencia Def Con 18 Hackers que comienza este viernes en Las Vegas.\r\n\r\nPara las empresas, la mejor estrategia para encontrar fallas en los softwares es un tema igualmente irresuelto. Facebook alienta a sus empleados a tratar de violar el sitio de su empresa. Algunas firmas llegan a alentar a gente de afuera para que viole el sitio. Mint.com, por ejemplo, un sitio web de finanzas personales que es propiedad de Intuit, contrata hackers para poner a prueba su seguridad una vez cada tres meses.\r\n\r\nOtros sólo desean que los hackers se vayan, tal como hizo AT&T después de que un grupo descubrió una falla en el sistema del sitio web de la empresa en junio pasado, que puso al descubierto 114 mil direcciones de e-mail y números de celulares de dueños del iPad3G.\r\n\r\nSi los hackers adhieren a una serie de reglas, las empresas se comprometen a no iniciar acciones legales. Y las empresas prometen trabajar con los hackers para solucionar el problema y darles el crédito adecuado por haber encontrado la falla.\r\n\r\nA algunos gray hat s les encanta el reconocimiento pero otros buscan hacer dinero. Los hackers pueden vender o intercambiar las fallas que descubren en lo que se conoce como el bug market , hasta que la empresa repara la falla y la vuelve inservible.\r\n\r\nAlgunos bugs (errores) pueden llegar a venderse online a 75 mil dólares.\r\n\r\n\r\nEl caso de dos argentinos\r\n\r\nEl viernes pasado, Clarín publicó el caso de Christian Russó (23 años) y Leandro Merlo (22), dos hackers argentinos que ingresaron a la base de datos de usuarios de The Pirate Bay, un sitio célebre y perseguido judicialmente por facilitar el intercambio de música. Según Russó, apenas tuvieron noticia del hueco de seguridad les avisaron a los responsables del sitio, que no les prestaron atención. Los hackers aseguran que no actuaron con mala intención. “Hicimos un trabajo profesional, probamos, documentamos, reportamos, y todo quedó ahí”, le dijo Russó a Clarín. Y agregó: “No vendemos información ni perjudicamos a nadie; (lo de The Pirate Bay), lo hicimos para que la gente sepa lo vulnerable que pueden estar sus datos en la Web”.\r\n'),(3,3,'La Junta Nacional de Auxilio escolar y Becas (JUNAEB) de Chile que maneja casi 3 millones de datos de niños y jóvenes beneficiados por becas y otros programas por un descuido de seguridad expuso datos y documentos relacionados con la Beca Presidente de la República. Muchos de estos datos contienen datos específicos como nombres y el RUT (Rol Único Tributario) que se refiere a un número de identificación perteneciente a cada persona en Chile, con los que pueden cometerse fraudes.\r\n\r\nEsta irregularidad fue descubierta por el programador Paolo Norambuena Sandoval que notificó a Terra.cl quién a su vez alertó a Juan Carlos Cabezas, director nacional de la JUNAEB, quien de inmediato retiro la información.\r\n\r\nEl problema radicaba en una mala configuración del servidor, falta de seguridad y permisos en carpetas que hizo posible descubrir estados datos. Esto sumado con el hecho de que el administrador guardaba los respaldos en la carpeta expuesta http://zeus.junaeb.cl/becapres/ .\r\n\r\nAnte estos hechos es necesario, vigilar y asegurar la confidencialidad de datos privados ya que en Chile esta información está protegida por la Ley Nº 19.628 Sobre Protección de la Vida Privada o Protección de Datos de Carácter Personal.\r\n\r\nEs necesario recurrir a buenas prácticas de seguridad ante este tipo de información, y hay que recordar que Google indexa todo lo que encuentra, si no pregúntenle a aquel administrador del sitio (en el 2007) de la Cámara de Diputados en México.');

/*Table structure for table `paginas` */

DROP TABLE IF EXISTS `paginas`;

CREATE TABLE `paginas` (
  `idPagina` int(10) unsigned NOT NULL auto_increment,
  `dominio` text NOT NULL,
  PRIMARY KEY  (`idPagina`),
  FULLTEXT KEY `NewIndex1` (`dominio`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `paginas` */

insert  into `paginas`(`idPagina`,`dominio`) values (1,'www.tuxton.com.ar'),(2,'www.clarin.com.ar'),(3,'www.fayerwayer.com');

/*Table structure for table `ranking` */

DROP TABLE IF EXISTS `ranking`;

CREATE TABLE `ranking` (
  `idPagina` int(11) unsigned NOT NULL,
  `PageRank` float unsigned NOT NULL,
  `DomPageRank` float unsigned NOT NULL,
  PRIMARY KEY  (`idPagina`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Data for the table `ranking` */

insert  into `ranking`(`idPagina`,`PageRank`,`DomPageRank`) values (1,50,42),(2,40,50),(3,20,49);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
