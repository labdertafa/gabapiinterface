package com.laboratorio.api;

import com.laboratorio.gabapiinterface.exception.GabApiException;
import com.laboratorio.gabapiinterface.impl.GabStatusApiImpl;
import com.laboratorio.gabapiinterface.model.GabMediaAttachment;
import com.laboratorio.gabapiinterface.model.GabStatus;
import com.laboratorio.gabapiinterface.utils.GabApiConfig;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.laboratorio.gabapiinterface.GabStatusApi;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 12/09/2024
 * @updated 12/09/2024
 */

public class GabStatusApiTest {
    private GabStatusApi statusApi;
    
    @BeforeEach
    public void initTest() {
        String accessToken = GabApiConfig.getInstance().getProperty("access_token");
        this.statusApi = new GabStatusApiImpl(accessToken);
    }
    
    @Test
    public void postStatus() {
        String text = "Hola, les saludo desde El laboratorio de Rafa. Post automático";
        
        GabStatus status = this.statusApi.postStatus(text);
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
    
    @Test
    public void postImage() throws Exception {
        String imagen = "C:\\Users\\rafa\\Pictures\\Formula_1\\Monza_1955.jpg";
        String text = "Hola, les saludo desde El laboratorio de Rafa. Post automático";
        
        GabMediaAttachment media = this.statusApi.uploadImage(imagen);
        assertTrue(media.getPreview_url() != null);
        
        GabStatus status = this.statusApi.postStatus(text, media.getId());
        assertTrue(!status.getId().isEmpty());
        assertTrue(status.getContent().contains(text));
    }
}