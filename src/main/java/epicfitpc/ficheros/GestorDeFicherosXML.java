package epicfitpc.ficheros;

import epicfitpc.modelo.Historico;
import epicfitpc.modelo.Usuario;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
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
				doc = dBuilder.parse(file);
				doc.getDocumentElement().normalize();
			} else {
				doc = dBuilder.newDocument();
				Element rootElement = doc.createElement("backup");
				doc.appendChild(rootElement);
			}

			// Obtener el nodo "usuarios" o crear uno nuevo si no existe
			Element rootElement = doc.getDocumentElement();
			NodeList usuariosNodes = rootElement.getElementsByTagName("usuarios");
			Element usuariosElement;
			if (usuariosNodes.getLength() > 0) {
				usuariosElement = (Element) usuariosNodes.item(0);
			} else {
				usuariosElement = doc.createElement("usuarios");
				rootElement.appendChild(usuariosElement);
			}

			// Buscar si el usuario ya existe en el archivo XML
			NodeList usuarioNodes = usuariosElement.getElementsByTagName("usuario");
			Element usuarioElement = null;
			for (int i = 0; i < usuarioNodes.getLength(); i++) {
				Element existingUsuario = (Element) usuarioNodes.item(i);
				NodeList usuarioIdNodes = existingUsuario.getElementsByTagName("usuario");

				if (usuarioIdNodes.getLength() > 0 && usuarioIdNodes.item(0) != null) {
					String usuarioId = usuarioIdNodes.item(0).getTextContent();
					if (usuarioId.equals(usuario.getUsuario())) {
						usuarioElement = existingUsuario;
						break;
					}
				}
			}

			// Si el usuario no existe, crear un nuevo nodo
			if (usuarioElement == null) {
				usuarioElement = doc.createElement("usuario");
				usuariosElement.appendChild(usuarioElement);
			} else {
				removeChildElements(usuarioElement, "id", "nombre", "apellido", "correo", "usuario", "pass", "nivel",
						"esEntrenador");
				Node historicosNode = usuarioElement.getElementsByTagName("historicos").item(0);
				if (historicosNode != null) {
					usuarioElement.removeChild(historicosNode);
				}
			}

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
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filePath));
			transformer.transform(source, result);

			System.out.println("Backup XML actualizado en " + filePath);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Element createTextElement(Document doc, String tagName, String textContent) {
		Element element = doc.createElement(tagName);
		element.appendChild(doc.createTextNode(textContent));
		return element;
	}
	
	private void removeChildElements(Element parent, String... tagNames) {
	    for (String tagName : tagNames) {
	        NodeList nodes = parent.getElementsByTagName(tagName);
	        for (int i = 0; i < nodes.getLength(); i++) {
	            Node node = nodes.item(i);
	            if (node != null) {
	                parent.removeChild(node);
	            }
	        }
	    }
	}
}
