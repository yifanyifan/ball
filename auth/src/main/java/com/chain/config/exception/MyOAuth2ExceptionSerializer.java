package com.chain.config.exception;

import com.chain.common.ResultEntity;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MyOAuth2ExceptionSerializer extends StdSerializer<MyOAuth2Exception> {
    private static final Logger log = LoggerFactory.getLogger(MyOAuth2ExceptionSerializer.class);

    public MyOAuth2ExceptionSerializer() {
        super(MyOAuth2Exception.class);
    }

    @Override
    public void serialize(MyOAuth2Exception e, JsonGenerator jGen, SerializerProvider provider) throws IOException {
        log.info("MyCustomOAuth2Exception返回格式序列化处理，MyExtendOAuth2ExceptionSerializer.class -> e={}", e);

        // 自定义返回格式内容
        ResultEntity baseResult = ResultEntity.failed(e.getMyExtendMessage(), e.getMessage());
        jGen.writeObject(baseResult);
    }
}

