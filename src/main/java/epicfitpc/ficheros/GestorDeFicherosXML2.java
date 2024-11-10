package epicfitpc.ficheros;

import org.w3c.dom.Document;

import org.w3c.dom.Element;

import epicfitpc.modelo.Historico;
import epicfitpc.modelo.Usuario;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

public class GestorDeFicherosXML2 {

	public void crearBackupXML(List<Usuario> usuarios, List<Historico> historicos, String filePath) {
		
		/*DocumentBuilderFactory dbFactoria = DocumentBuilderFactory.newInstance();
		
		DocumetBuilder dBuilder = dbFactoria.newDocumentBuilder();
		
		/*Definiendo el fichero XML que se va a leer mediante  el objeto FILE*/
		
		/*File archivo = new File("historicos.xml");*/
		
		/*Proscesar el documento XML mediante el me+étodo parse() del objero XMLReader, pasandole un objeto File */
		
		/*Document doc = dBuilder.parse(archivo);
		doc.getDocumentElement().normalize();
		
		 Element rootElement = doc.createElement("backup");
         doc.appendChild(rootElement);

         // Agregar usuarios
         Element usuariosElement = doc.createElement("usuarios");
         rootElement.appendChild(usuariosElement);*/
		
		
	}

}
