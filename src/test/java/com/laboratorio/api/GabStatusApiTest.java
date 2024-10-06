package com.laboratorio.api;

import com.laboratorio.gabapiinterface.exception.GabApiException;
import com.laboratorio.gabapiinterface.impl.GabStatusApiImpl;
import com.laboratorio.gabapiinterface.model.GabStatus;
import com.laboratorio.gabapiinterface.utils.GabApiConfig;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.laboratorio.gabapiinterface.GabStatusApi;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 12/09/2024
 * @updated 06/10/2024
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GabStatusApiTest {
    private GabStatusApi statusApi;
    private static String postId;
    
    @BeforeEach
    public void initTest() {
        String accessToken = GabApiConfig.getInstance().getProperty("access_token");
        this.statusApi = new GabStatusApiImpl(accessToken);
    }
    
    @Test @Order(1)
    public void postStatus() {
        String text = "Hola, les saludo desde El laboratorio de Rafa. Post automático";
        
        GabStatus status = this.statusApi.postStatus(text);
        postId = status.getId();
        
        assertTrue(!status.getId().isEmpty());
        assertTrue(status.getContent().contains(text));
    }
    
    @Test
    public void postInvalidStatus() {
        this.statusApi = new GabStatusApiImpl("INVALID_TOKEN");
        
        assertThrows(GabApiException.class, () -> {
            this.statusApi.postStatus("");
        });
    }
    
    @Test @Order(2)
    public void deleteStatus() {
        boolean result = this.statusApi.deleteStatus(postId);
        
        assertTrue(result);
    }
    
    @Test @Order(3)
    public void postImage() throws Exception {
        String imagen = "C:\\Users\\rafa\\Pictures\\Formula_1\\Monza_1955.jpg";
        String text = "Hola, les saludo desde El laboratorio de Rafa. Post automático";
        
        GabStatus status = this.statusApi.postStatus(text, imagen);
        postId = status.getId();
        
        assertTrue(!status.getId().isEmpty());
        assertTrue(status.getContent().contains(text));
    }
    
    @Test @Order(4)
    public void deleteStatusWithImage() {
        boolean result = this.statusApi.deleteStatus(postId);
        
        assertTrue(result);
    }
}