package com.ejlchina.data.jacksonyaml;

import com.ejlchina.data.jackson.JacksonDataConvertor;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

public class JacksonYamlDataConvertor extends JacksonDataConvertor {

    public JacksonYamlDataConvertor() {
        this(new YAMLMapper());
    }

    public JacksonYamlDataConvertor(YAMLMapper xmlMapper) {
        super(xmlMapper);
    }

}
