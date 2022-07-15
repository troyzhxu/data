package com.ejlchina.data.xml;

import com.ejlchina.data.Array;
import com.ejlchina.data.DataConvertor;
import com.ejlchina.data.Mapper;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class XmlDataConvertor implements DataConvertor {

    private String[] nameKeys = {"name", "key"};
    private String[] valueKeys = {"value"};

    private boolean serializeFormatted = false;

    private DocumentBuilderFactory dbFactory;


    public XmlDataConvertor() {
        this(DocumentBuilderFactory.newInstance());
    }

    public XmlDataConvertor(DocumentBuilderFactory dbFactory) {
        this.dbFactory = dbFactory;
    }

    protected Element parseElement(InputStream in, Charset charset) {
        DocumentBuilder builder;
        try {
            // DocumentBuilder 是线程不安全的，所有每次解析都得新起一个 Builder
            builder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new IllegalStateException("DocumentBuilderFactory 配置异常", e);
        }
        try {
            InputSource source = new InputSource(in);
            source.setEncoding(charset.name());
            return builder.parse(source).getDocumentElement();
        } catch (SAXException|IOException e) {
            throw new IllegalStateException("XML 解析异常", e);
        }
    }

    @Override
    public Mapper toMapper(InputStream in, Charset charset) {
        Element root = parseElement(in, charset);
        return new XmlMapper(nameKeys, valueKeys, root);
    }

    @Override
    public Array toArray(InputStream in, Charset charset) {
        Element root = parseElement(in, charset);
        return new XmlArray(nameKeys, valueKeys, XmlUtils.children(root));
    }

    @Override
    public byte[] serialize(Object object, Charset charset) {
        if (object instanceof XmlMapper || object instanceof XmlArray) {
            return object.toString().getBytes(charset);
        }
        try {
            JAXBContext context = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, serializeFormatted);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, charset.name());
            StringWriter writer = new StringWriter();
            marshaller.marshal(object, writer);
            return writer.toString().getBytes(charset);
        } catch (JAXBException e) {
            throw new IllegalStateException("XML 序列化异常：", e);
        }
    }

    @Override
    public String serialize(Object object) {
        if (object instanceof XmlMapper || object instanceof XmlArray) {
            return object.toString();
        }
        try {
            JAXBContext context = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, serializeFormatted);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, StandardCharsets.UTF_8.name());
            StringWriter writer = new StringWriter();
            marshaller.marshal(object, writer);
            return writer.toString();
        } catch (JAXBException e) {
            throw new IllegalStateException("XML 序列化异常：", e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T toBean(Type type, InputStream in, Charset charset) {
        return (T) Deserializer.getInstance().deserialize(toMapper(in, charset), type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> toList(Class<T> type, InputStream in, Charset charset) {
        Array array = toArray(in, charset);
        List<T> list = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            list.add((T) Deserializer.getInstance().deserialize(array.getMapper(i), type));
        }
        return list;
    }

    public String[] getNameKeys() {
        return nameKeys;
    }

    public void setNameKeys(String[] nameKeys) {
        this.nameKeys = nameKeys;
    }

    public String[] getValueKeys() {
        return valueKeys;
    }

    public void setValueKeys(String[] valueKeys) {
        this.valueKeys = valueKeys;
    }

    public DocumentBuilderFactory getDbFactory() {
        return dbFactory;
    }

    public void setDbFactory(DocumentBuilderFactory dbFactory) {
        this.dbFactory = dbFactory;
    }

    public boolean isSerializeFormatted() {
        return serializeFormatted;
    }

    public void setSerializeFormatted(boolean serializeFormatted) {
        this.serializeFormatted = serializeFormatted;
    }

}
