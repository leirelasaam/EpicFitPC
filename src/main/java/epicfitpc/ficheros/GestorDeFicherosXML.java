package epicfitpc.ficheros;

import epicfitpc.modelo.Historico;
import epicfitpc.modelo.Usuario;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;

import java.io.File;
import java.util.List;

/**
 * Gestiona la escritura de históricos en ficheros xml.
 */
public class GestorDeFicherosXML {

	public void crearBackupXML(Usuario usuario, List<Historico> historicos, String filePath) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc;

			File file = new File(filePath);
			if (file.exists()) {
				file.delete();
			}

			doc = dBuilder.newDocument();
			Element rootElement = doc.createElement("backup");
			doc.appendChild(rootElement);

	        Element usuariosElement = doc.createElement("usuarios");
	        rootElement.appendChild(usuariosElement);

	        Element usuarioElement = doc.createElement("usuario");
	        usuariosElement.appendChild(usuarioElement);

			// Agregar los datos del usuario nuevamente
			usuarioElement.appendChild(createTextElement(doc, "id", usuario.getId()));
			usuarioElement.appendChild(createTextElement(doc, "nombre", usuario.getNombre()));
			usuarioElement.appendChild(createTextElement(doc, "apellido", usuario.getApellido()));
			usuarioElement.appendChild(createTextElement(doc, "correo", usuario.getCorreo()));
			usuarioElement.appendChild(createTextElement(doc, "usuario", usuario.getUsuario()));
			usuarioElement.appendChild(createTextElement(doc, "pass", usuario.getPass()));
			usuarioElement.appendChild(createTextElement(doc, "nivel", String.valueOf(usuario.getNivel())));
			usuarioElement
					.appendChild(createTextElement(doc, "esEntrenador", String.valueOf(usuario.isEsEntrenador())));

			Element historicosElement = doc.createElement("historicos");
			usuarioElement.appendChild(historicosElement);

			for (Historico historico : historicos) {
				Element historicoElement = doc.createElement("historico");
				historicoElement.appendChild(createTextElement(doc, "id", historico.getId()));
				historicoElement.appendChild(createTextElement(doc, "fecha", historico.getFecha().toString()));
				historicoElement
						.appendChild(createTextElement(doc, "porcentaje", String.valueOf(historico.getPorcentaje())));
				historicoElement.appendChild(createTextElement(doc, "tiempo", String.valueOf(historico.getTiempo())));
				historicosElement.appendChild(historicoElement);
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filePath));
			transformer.transform(source, result);

			System.out.println("Backup de históricos en XML realizado en " + filePath);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Element createTextElement(Document doc, String tagName, String textContent) {
		Element element = doc.createElement(tagName);
		element.appendChild(doc.createTextNode(textContent));
		return element;
	}
}
