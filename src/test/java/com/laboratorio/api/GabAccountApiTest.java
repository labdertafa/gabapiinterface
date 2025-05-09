package com.laboratorio.api;

import com.laboratorio.clientapilibrary.utils.ReaderConfig;
import com.laboratorio.gabapiinterface.exception.GabApiException;
import com.laboratorio.gabapiinterface.impl.GabAccountApiImpl;
import com.laboratorio.gabapiinterface.model.GabAccount;
import com.laboratorio.gabapiinterface.model.GabRelationship;
import com.laboratorio.gabapiinterface.model.response.GabAccountListResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import com.laboratorio.gabapiinterface.GabAccountApi;
import com.laboratorio.gabapiinterface.model.GabSuggestionType;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Rafael
 * @version 1.2
 * @created 11/09/2024
 * @updated 09/05/2025
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GabAccountApiTest {
    protected static final Logger log = LogManager.getLogger(GabAccountApiTest.class);
    private static GabAccountApi accountApi;
    
    @BeforeEach
    public void initTest() {
        ReaderConfig config = new ReaderConfig("config//gab_api.properties");
        String accessToken = config.getProperty("access_token");
        accountApi = new GabAccountApiImpl(accessToken);
    }
    
    @Test
    public void getAccountById() {
        String userId = "265538";
        
        GabAccount account = accountApi.getAccountById(userId);
        
        assertEquals(userId, account.getId());
    }
    
    @Test
    public void findAccountByInvalidId() {
        String id = "1125349753AAABBB60";
        
        assertThrows(GabApiException.class, () -> {
            accountApi.getAccountById(id);
        });
    }
    
    @Test
    public void getAccountByUsername() {
        String username = "TruthriseswithLove";
        
        GabAccount account = accountApi.getAccountByUsername(username);
        
        assertEquals(username, account.getUsername());
    }
    
    @Test
    public void getAccountByInvalidUsername() {
        String username = "TruthriseswithLoveXX";
        
        assertThrows(GabApiException.class, () -> {
            accountApi.getAccountByUsername(username);
        });
    }
    
    @Test
    public void get40Followers() throws Exception {
        String id = "265538";
        int maxLimit = 80;
        int cantidad = 40;
        
        GabAccountListResponse accountListResponse = accountApi.getFollowers(id, maxLimit, cantidad);

        assertEquals(cantidad, accountListResponse.getAccounts().size());
        assertTrue(!accountListResponse.getMaxId().isEmpty());
    }
    
    @Test
    public void get40FollowersDefaultLimit() throws Exception {
        String id = "265538";
        int defaultLimit = 0;
        int cantidad = 40;
        
        GabAccountListResponse accountListResponse = accountApi.getFollowers(id, defaultLimit, cantidad);

        assertEquals(cantidad, accountListResponse.getAccounts().size());
        assertTrue(!accountListResponse.getMaxId().isEmpty());
    }
    
    @Test
    public void get80Followers() throws Exception {
        String id = "265538";
        int maxLimit = 80;
        int cantidad = 80;
        
        GabAccountListResponse accountListResponse = accountApi.getFollowers(id, maxLimit, cantidad);

        assertEquals(cantidad, accountListResponse.getAccounts().size());
        assertTrue(!accountListResponse.getMaxId().isEmpty());
    }
    
    @Test
    public void get81Followers() throws Exception {
        String id = "265538";
        int maxLimit = 80;
        int cantidad = 81;
        
        GabAccountListResponse accountListResponse = accountApi.getFollowers(id, maxLimit, cantidad);

        assertEquals(cantidad, accountListResponse.getAccounts().size());
        assertTrue(!accountListResponse.getMaxId().isEmpty());
    }
    
    @Test
    public void get200Followers() throws Exception {
        String id = "265538";
        int maxLimit = 80;
        int cantidad = 200;
        
        GabAccountListResponse accountListResponse = accountApi.getFollowers(id, maxLimit, cantidad);

        assertEquals(cantidad, accountListResponse.getAccounts().size());
        assertTrue(!accountListResponse.getMaxId().isEmpty());
    }
    
    @Test
    public void getAllFollowers() throws Exception {     // Usa default limit
       String id = "5511959";
        
        GabAccountListResponse accountListResponse = accountApi.getFollowers(id);
        log.info("Seguidores encontrados: " + accountListResponse.getAccounts().size());
        
        assertTrue(!accountListResponse.getAccounts().isEmpty());
    }
    
    @Test
    public void getFollowersIds() throws Exception {     // Usa default limit
        String id = "6636039";
        
        List<String> usersIds = accountApi.getFollowersIds(id, 0);
        log.info("Seguidores encontrados: " + usersIds.size());
        
        assertTrue(!usersIds.isEmpty());
    }
    
    @Test
    public void getFollowersInvalidId() {
        String id = "1125349753AAABBB60";
        
        assertThrows(GabApiException.class, () -> {
            accountApi.getFollowers(id);
        });
    }
    
    @Test
    public void get40Followings() throws Exception {
        String id = "265538";
        int maxLimit = 80;
        int cantidad = 40;
        
        GabAccountListResponse accountListResponse = accountApi.getFollowings(id, maxLimit, cantidad);

        assertEquals(cantidad, accountListResponse.getAccounts().size());
        assertTrue(!accountListResponse.getMaxId().isEmpty());
    }
    
    @Test
    public void get40FollowingsDefaultLimit() throws Exception {
        String id = "265538";
        int defaulLimit = 0;
        int cantidad = 40;
        
        GabAccountListResponse accountListResponse = accountApi.getFollowings(id, defaulLimit, cantidad);

        assertEquals(cantidad, accountListResponse.getAccounts().size());
        assertTrue(!accountListResponse.getMaxId().isEmpty());
    }
    
    @Test
    public void get80Followings() throws Exception {
        String id = "265538";
        int maxLimit = 80;
        int cantidad = 80;
        
        GabAccountListResponse accountListResponse = accountApi.getFollowings(id, maxLimit, cantidad);

        assertEquals(cantidad, accountListResponse.getAccounts().size());
        assertTrue(!accountListResponse.getMaxId().isEmpty());
    }
    
    @Test
    public void get81Followings() throws Exception {
        String id = "265538";
        int maxLimit = 80;
        int cantidad = 81;
        
        GabAccountListResponse accountListResponse = accountApi.getFollowings(id, maxLimit, cantidad);

        assertEquals(cantidad, accountListResponse.getAccounts().size());
        assertTrue(!accountListResponse.getMaxId().isEmpty());
    }
    
    @Test
    public void get200Followings() throws Exception {
        String id = "265538";
        int maxLimit = 80;
        int cantidad = 200;
        
        GabAccountListResponse accountListResponse = accountApi.getFollowings(id, maxLimit, cantidad);

        assertEquals(cantidad, accountListResponse.getAccounts().size());
        assertTrue(!accountListResponse.getMaxId().isEmpty());
    }

    @Test
    public void getAllFollowings() throws Exception { // Usa default limit
        String id = "5511959";
        
        GabAccountListResponse accountListResponse = accountApi.getFollowings(id);

        assertTrue(accountListResponse.getAccounts().size() >= 0);
        assertTrue(accountListResponse.getMaxId() == null);
    }
    
    @Test
    public void getFollowingsInvalidId() {
        String id = "1125349753AAABBB60";
        
        assertThrows(GabApiException.class, () -> {
            accountApi.getFollowings(id);
        });
    }
    
    @Test @Order(1)
    public void followAccount() {
        String id = "5511959";
        
        boolean result = accountApi.followAccount(id);
        
        assertTrue(result);
    }
    
    @Test
    public void followInvalidAccount() {
        String id = "1125349753AAABBB60";
        
        assertThrows(GabApiException.class, () -> {
            accountApi.followAccount(id);
        });
    }
    
    @Test @Order(2)
    public void unfollowAccount() {
        String id = "5511959";
        
        boolean result = accountApi.unfollowAccount(id);
        
        assertTrue(result);
    }
    
    @Test
    public void unfollowInvalidAccount() {
        String id = "1125349753AAABBB60";
        
        assertThrows(GabApiException.class, () -> {
            accountApi.unfollowAccount(id);
        });
    }
    
    @Test
    public void checkMutualRelationship() {
        List<String> usersId = new ArrayList<>();
        usersId.add("24835");
        usersId.add("725749");
        usersId.add("265538");
        
        List<GabRelationship> relationships  = accountApi.checkrelationships(usersId);
        assertEquals(3, relationships.size());
        assertTrue(relationships.get(2).isFollowing());
        assertTrue(relationships.get(2).isFollowed_by());
    }
    
    @Test
    public void getSuggestions() {
        List<GabAccount> accounts = accountApi.getSuggestions(GabSuggestionType.VERIFIED);
        assertTrue(accounts.size() >= 0);
    }
}