package com.ejlchina.data.jackson.yaml;

import com.ejlchina.data.jackson.JacksonDataConvertor;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

/**
 * @author Troy.Zhou @ 2017-03-20
 * @since v1.5.0
 * */
public class JacksonYamlDataConvertor extends JacksonDataConvertor {

    public JacksonYamlDataConvertor() {
        this(new YAMLMapper());
    }

    public JacksonYamlDataConvertor(YAMLMapper xmlMapper) {
        super(xmlMapper);
    }

}
