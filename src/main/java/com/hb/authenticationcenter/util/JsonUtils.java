package com.hb.authenticationcenter.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hb.authenticationcenter.controller.response.DefaultView;
import com.hb.authenticationcenter.controller.response.ResponseView;
import lombok.extern.slf4j.Slf4j;

/**
 * @author admin
 * @version 1.0
 * @description
 * @date 2022/12/29
 */
@Slf4j
public class JsonUtils {

    public static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * object 转换为 string
     * @param target object
     * @return String
     */
    public static String object2String(Object target) {
        String result = "";
        try {
            result = MAPPER.writeValueAsString(target);
        } catch (JsonProcessingException e) {
            log.error("object convert to string.");
        }
        return result;
    }

    /**
     * object 转换为 string
     * @param view ResponseView
     * @param target object
     * @return String
     */
    public static String object2String(Class<?> view, Object target) {
        String result = "";
        try {
            result = MAPPER.writerWithView(view).writeValueAsString(target);
        } catch (JsonProcessingException e) {
            log.error("object convert to string.");
        }
        return result;
    }
}
