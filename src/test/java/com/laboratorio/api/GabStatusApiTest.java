package com.laboratorio.api;

import com.laboratorio.clientapilibrary.utils.ReaderConfig;
import com.laboratorio.gabapiinterface.exception.GabApiException;
import com.laboratorio.gabapiinterface.impl.GabStatusApiImpl;
import com.laboratorio.gabapiinterface.model.GabStatus;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.laboratorio.gabapiinterface.GabStatusApi;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

/**
 *
 * @author Rafael
 * @version 1.2
 * @created 12/09/2024
 * @updated 09/05/2025
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GabStatusApiTest {
    protected static final Logger log = LogManager.getLogger(GabStatusApiTest.class);
    private GabStatusApi statusApi;
    private static String postId;
    
    @BeforeEach
    public void initTest() {
        ReaderConfig config = new ReaderConfig("config//gab_api.properties");
        String accessToken = config.getProperty("access_token");
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
    
    @Test
    public void getPersonalTimeline() {
        int quantity = 50;
        
        List<GabStatus> statuses = statusApi.getPersonalTimeline(quantity);
        int i = 0;
        for (GabStatus status : statuses) {
            i++;
            log.info(i + "-) Status: " + status.toString());
        }
        
        assertEquals(quantity, statuses.size());
    }
    
    @Test
    public void getGlobalTimeline() {
        int quantity = 50;
        
        List<GabStatus> statuses = statusApi.getGlobalTimeline(quantity);
        int i = 0;
        for (GabStatus status : statuses) {
            i++;
            log.info(i + "-) Status: " + status.toString());
        }
        
        assertEquals(quantity, statuses.size());
    }
}