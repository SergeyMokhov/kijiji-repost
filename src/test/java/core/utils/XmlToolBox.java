package core.utils;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by sergey on 2015-12-21.
 */
public class XmlToolBox {
    public NodeList evaluateXpath(String pathToXml, String xPathExpression) throws SAXException, XPathExpressionException {
        Document xmlDocument = null;
        //Crate instance of Document Builder
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = builderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        //Parse xml file
        try {
            xmlDocument = builder.parse(new FileInputStream(pathToXml));
        } catch (SAXException e) {
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Creating XPath object
        XPath xPath = XPathFactory.newInstance().newXPath();

        //read a nodelist using xpath
        String expression = xPathExpression;

        NodeList nodeList = null;
        try {
            nodeList = (NodeList) xPath.compile(xPathExpression).evaluate(xmlDocument, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            throw e;
        }
        return nodeList;
    }
}
