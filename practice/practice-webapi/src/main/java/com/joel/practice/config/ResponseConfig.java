package com.joel.practice.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joel.practice.common.exception.UserFriendlyException;
import com.joel.practice.common.response.AjaxResponse;
import com.joel.practice.common.response.DontWrapResponse;
import com.joel.practice.common.response.RawResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.io.PrintWriter;

@Configuration
@EnableWebMvc
public class ResponseConfig {

    @Slf4j
    @RestControllerAdvice
    //ResponseBodyAdvice拦截Controller方法默认返回参数，统一处理返回值/响应体
    static class ResultResponseAdvice implements ResponseBodyAdvice<Object> {
        private ObjectMapper objectMapper;

        public ResultResponseAdvice(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        /**
         * 判断是否要执行beforeBodyWrite方法，true为执行，false不执行
         *
         * @param methodParameter
         * @param aClass
         * @return
         */
        @Override
        public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
            return true;
        }

        @Override
        public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest request, ServerHttpResponse response) {
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

            if (body instanceof AjaxResponse) {
                AjaxResponse resp = (AjaxResponse) body;
                if (resp.getError() != null) {
                    response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                }
                return body;
            } else if (body instanceof String) {
                try {
                    return objectMapper.writeValueAsString(AjaxResponse.ok(body));
                } catch (JsonProcessingException e) {
                    return AjaxResponse.err("系统错误，请联系管理员");
                }
            } else if (body instanceof RawResponse) {
                RawResponse resp = (RawResponse) body;
                try (PrintWriter writer = ((ServletServerHttpResponse) response).getServletResponse().getWriter()) {
                    writer.write(resp.getBody());
                    writer.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (body instanceof DontWrapResponse) {
                DontWrapResponse resp = (DontWrapResponse) body;
                return resp.getBody();
            }
            return AjaxResponse.ok(body);
        }

        /**
         * 全局异常捕捉处理，统一返回
         * @param e
         * @return
         */
        @ExceptionHandler(value = {Exception.class})
        public AjaxResponse<Object> handleAllExceptions(Exception e){
            log.error("未处理异常："+e);
            return AjaxResponse.err("系统错误，请联系管理员");
        }

        /**
         * 自定义异常捕捉，返回自定义异常的提示信息
         * @param e
         * @return
         */
        @ExceptionHandler(value = {UserFriendlyException.class})
        public AjaxResponse<Object> handleUserFriendlyExceptions(UserFriendlyException e){
            return AjaxResponse.err(e.getCode(),e.getMessage());
        }
    }
}
