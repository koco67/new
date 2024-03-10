package com.htw.product.listener;

import com.htw.product.domain.ProductService;
import com.htw.product.entity.MessageType;
import com.htw.product.error.ErrorResponseException;
import com.htw.product.model.Product;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;


@Slf4j
public class ProductListener {

    @Autowired
    private ProductService productService;

    @RabbitListener(queues = "${queue-names.product-service}")
    public String handleRequest(Message message) {

        final MessageType messageType;
        try {
            messageType = MessageType.valueOf(message.getMessageProperties().getType());
        } catch (IllegalArgumentException e) {
            return logInvalidMessageType(message.getMessageProperties().getType());
        }

        try {
            switch (messageType) {
                case GET_ALL_PRODUCTS: {
                    log.info("get all products request processed");
                    return getAllProducts();
                }
                case GET_PRODUCT_BY_ID: {
                    log.info("get product by id request processed");
                    return getProductById();
                }
                case CREATE_PRODUCT: {
                    log.info("create product request processed");
                    return createProduct();
                }
                case UPDATE_PRODUCT: {
                    var product = extractProductFrom(message);
                    log.info("update product for product {} request processed", product.getName());
                    return updateProduct(product);
                }
                case DELETE_PRODUCT: {
                    var userId = extractUserIdFrom(message);
                    log.info("delete products with id {} request processed", userId);
                    return deleteProductById(userId);
                }

                default: {
                    return errorResponse();
                }
            }
        } catch (ErrorResponseException e) {
            return errorResponse();
        }
    }

    private String errorResponse() {
        log.error("respond with message 'errorResponse'");
        return "errorResponse";
    }

    private String getBodyFrom(Message message) {
        return new String(message.getBody(), StandardCharsets.UTF_8);
    }

    private String logInvalidMessageType(String type) {
        log.info("invalid message type: " + type);
        return errorResponse();
    }

    private String updateProduct(Product product) throws ErrorResponseException {
        return new Gson().toJson(productService.updateProduct(product));
    }


    private String createProduct() throws ErrorResponseException {
        return new Gson().toJson(productService.createProduct(null));
    }
    private String getProductById() {
        return new Gson().toJson(productService.getProductById(null));
    }
    private String getAllProducts() {
        return new Gson().toJson(productService.getAllProducts());
    }
}
