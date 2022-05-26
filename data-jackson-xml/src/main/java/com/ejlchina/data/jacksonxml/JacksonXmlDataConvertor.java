package com.ejlchina.data.jacksonxml;

import com.ejlchina.data.jackson.JacksonDataConvertor;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class JacksonXmlDataConvertor extends JacksonDataConvertor {

    public JacksonXmlDataConvertor() {
        this(new XmlMapper());
    }

    public JacksonXmlDataConvertor(XmlMapper xmlMapper) {
        super(xmlMapper);
    }

}
