package com.laboratorio.api;

import com.laboratorio.clientapilibrary.utils.ReaderConfig;
import com.laboratorio.gabapiinterface.exception.GabApiException;
import com.laboratorio.gabapiinterface.impl.GabNotificationApiImpl;
import com.laboratorio.gabapiinterface.model.response.GabNotificationListResponse;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.laboratorio.gabapiinterface.GabNotificationApi;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 11/09/2024
 * @updated 09/05/2025
 */
public class GabNotificationApiTest {
    private GabNotificationApi notificationApi;
    
    @BeforeEach
    private void initNotificationApi() {
        ReaderConfig config = new ReaderConfig("config//gab_api.properties");
        String accessToken = config.getProperty("access_token");
        this.notificationApi = new GabNotificationApiImpl(accessToken);
    }
    
    @Test
    public void get20Notifications() throws Exception { // Con default limit
        int cantidad  = 20;
        
        GabNotificationListResponse notificationListResponse = this.notificationApi.getAllNotifications(0, cantidad);

        assertTrue(!notificationListResponse.getNotifications().isEmpty());
        assertTrue(notificationListResponse.getMinId() != null);
    }
    
    @Test
    public void get20NotificationsWithLimit() throws Exception { // Con limit
        int cantidad  = 20;
        int limit = 50;
        
        GabNotificationListResponse notificationListResponse = this.notificationApi.getAllNotifications(limit, cantidad);

        assertTrue(!notificationListResponse.getNotifications().isEmpty());
        assertTrue(notificationListResponse.getMinId() != null);
    }
    
    @Test
    public void getAllNotifications() throws Exception {
        GabNotificationListResponse notificationListResponse = this.notificationApi.getAllNotifications(80);

        assertTrue(!notificationListResponse.getNotifications().isEmpty());
        assertTrue(notificationListResponse.getMinId() != null);
    }
    
    @Test
    public void getNotificationError() {
        this.notificationApi = new GabNotificationApiImpl("INVALID_TOKEN");

        assertThrows(GabApiException.class, () -> {
            this.notificationApi.getAllNotifications();
        });
    }
}