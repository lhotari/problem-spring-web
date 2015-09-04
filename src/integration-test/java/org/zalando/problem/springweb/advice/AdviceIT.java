package org.zalando.problem.springweb.advice;

/*
 * #%L
 * problem-handling
 * %%
 * Copyright (C) 2015 Zalando SE
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.zalando.problem.ProblemModule;
import org.zalando.problem.springweb.advice.example.ExampleRestController;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public abstract class AdviceIT {

    static final String URI_HANDLER_OK = "http://localhost/api/handler-ok";
    static final String URI_HANDLER_PUT = "http://localhost/api/handler-put";
    static final String URI_HANDLER_PROBLEM = "http://localhost/api/handler-problem";
    static final String URI_HANDLER_THROWABLE = "http://localhost/api/handler-throwable";
    static final String URI_HANDLER_NO_MAPPING = "http://localhost/api/no-handler";
    static final String URI_HANDLER_PARAMS = "http://localhost/api/handler-params";
    static final String URI_HANDLER_HEADERS = "http://localhost/api/handler-headers";
    static final String URI_HANDLER_CONVERSION = "http://localhost/api/handler-conversion";
    static final String URI_HANDLER_MULTIPART = "http://localhost/api/handler-multipart";

    static final String URI_HANDLER_CONSUMES_PRODUCES = "http://localhost/api/handler-problem";
    static final String URI_NO_MAPPING = "http://localhost/no-handler";

    protected final MockMvc mvc = standaloneSetup(new ExampleRestController())
            .setControllerAdvice(advice())
            .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper()))
            .build();

    protected abstract Object advice();

    private ObjectMapper objectMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new ProblemModule());
        objectMapper.registerModule(new Jdk8Module());
        return objectMapper;
    }

}