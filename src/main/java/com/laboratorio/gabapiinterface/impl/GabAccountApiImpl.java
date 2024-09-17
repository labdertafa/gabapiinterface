package com.laboratorio.gabapiinterface.impl;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.laboratorio.clientapilibrary.model.ApiRequest;
import com.laboratorio.gabapiinterface.exception.GabApiException;
import com.laboratorio.gabapiinterface.model.GabAccount;
import com.laboratorio.gabapiinterface.model.GabRelationship;
import com.laboratorio.gabapiinterface.model.response.GabAccountListResponse;
import com.laboratorio.gabapiinterface.utils.InstruccionInfo;
import com.laboratorio.gabapiinterface.GabAccountApi;
import com.laboratorio.gabapiinterface.model.GabSuggestionType;
import com.laboratorio.gabapiinterface.model.request.GabRelationshipRequest;
import com.laboratorio.gabapiinterface.model.response.GabSuggestionsResponse;
import java.util.List;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 11/09/2024
 * @updated 17/09/2024
 */
public class GabAccountApiImpl extends GabBaseApi implements GabAccountApi {
    public GabAccountApiImpl(String accessToken) {
        super(accessToken);
    }

    @Override
    public GabAccount getAccountById(String userId) {
        String endpoint = this.apiConfig.getProperty("getAccountById_endpoint");
        int okStatus = Integer.parseInt(this.apiConfig.getProperty("getAccountById_ok_status"));
        
        try {
            String url = endpoint + "/" + userId;
            ApiRequest request = new ApiRequest(url, okStatus);
            request.addApiHeader("Content-Type", "application/json");
            
            String jsonStr = this.client.executeGetRequest(request);
            
            return this.gson.fromJson(jsonStr, GabAccount.class);
        } catch (JsonSyntaxException e) {
            logException(e);
            throw e;
        } catch (Exception e) {
            throw new GabApiException(GabAccountApiImpl.class.getName(), e.getMessage());
        }
    }

    @Override
    public GabAccountListResponse getFollowers(String userId) throws Exception {
        return this.getFollowers(userId, 0);
    }
    
    @Override
    public GabAccountListResponse getFollowers(String userId, int limit) throws Exception {
        return this.getFollowers(userId, limit, 0);
    }

    @Override
    public GabAccountListResponse getFollowers(String userId, int limit, int quantity) throws Exception {
        return this.getFollowers(userId, limit, quantity, null);
    }

    @Override
    public GabAccountListResponse getFollowers(String userId, int limit, int quantity, String posicionInicial) throws Exception {
        String endpoint = this.apiConfig.getProperty("getFollowers_endpoint");
        String complementoUrl = this.apiConfig.getProperty("getFollowers_complemento_url");
        int okStatus = Integer.parseInt(this.apiConfig.getProperty("getFollowers_ok_status"));
        int defaultLimit = Integer.parseInt(this.apiConfig.getProperty("getFollowers_default_limit"));
        int maxLimit = Integer.parseInt(this.apiConfig.getProperty("getFollowers_max_limit"));
        int usedLimit = limit;
        if ((limit == 0) || (limit > maxLimit)) {
            usedLimit = defaultLimit;
        }
        InstruccionInfo instruccionInfo = new InstruccionInfo(endpoint, complementoUrl, okStatus, usedLimit);
        return this.getCounterAccountList(instruccionInfo, userId, quantity, posicionInicial);
    }

    @Override
    public GabAccountListResponse getFollowings(String userId) throws Exception {
        return this.getFollowings(userId, 0);
    }
    
    @Override
    public GabAccountListResponse getFollowings(String userId, int limit) throws Exception {
        return this.getFollowings(userId, limit, 0);
    }

    @Override
    public GabAccountListResponse getFollowings(String userId, int limit, int quantity) throws Exception {
        return this.getFollowings(userId, limit, quantity, null);
    }

    @Override
    public GabAccountListResponse getFollowings(String userId, int limit, int quantity, String posicionInicial) throws Exception {
        String endpoint = this.apiConfig.getProperty("getFollowings_endpoint");
        String complementoUrl = this.apiConfig.getProperty("getFollowings_complemento_url");
        int okStatus = Integer.parseInt(this.apiConfig.getProperty("getFollowings_ok_status"));
        int defaultLimit = Integer.parseInt(this.apiConfig.getProperty("getFollowings_default_limit"));
        int maxLimit = Integer.parseInt(this.apiConfig.getProperty("getFollowings_max_limit"));
        int usedLimit = limit;
        if ((limit == 0) || (limit > maxLimit)) {
            usedLimit = defaultLimit;
        }
        InstruccionInfo instruccionInfo = new InstruccionInfo(endpoint, complementoUrl, okStatus, usedLimit);
        return this.getCounterAccountList(instruccionInfo, userId, quantity, posicionInicial);
    }

    @Override
    public boolean followAccount(String userId) {
        String endpoint = this.apiConfig.getProperty("followAccount_endpoint");
        String complementoUrl = this.apiConfig.getProperty("followAccount_complemento_url");
        int okStatus = Integer.parseInt(this.apiConfig.getProperty("followAccount_ok_status"));
        
        try {
            String uri = endpoint + "/" + userId + "/" + complementoUrl;
            
            ApiRequest request = new ApiRequest(uri, okStatus);
            request.addApiHeader("Content-Type", "application/json");
            request.addApiHeader("Authorization", "Bearer " + this.accessToken);
            
            String jsonStr = this.client.executePostRequest(request);
            GabRelationship relationship = this.gson.fromJson(jsonStr, GabRelationship.class);
            
            return relationship.isFollowing();
        } catch (Exception e) {
            throw new GabApiException(GabAccountApiImpl.class.getName(), e.getMessage());
        }
    }

    @Override
    public boolean unfollowAccount(String userId) {
        String endpoint = this.apiConfig.getProperty("unfollowAccount_endpoint");
        String complementoUrl = this.apiConfig.getProperty("unfollowAccount_complemento_url");
        int okStatus = Integer.parseInt(this.apiConfig.getProperty("unfollowAccount_ok_status"));
        
        try {
            String uri = endpoint + "/" + userId + "/" + complementoUrl;

            ApiRequest request = new ApiRequest(uri, okStatus);
            request.addApiHeader("Content-Type", "application/json");
            request.addApiHeader("Authorization", "Bearer " + this.accessToken);
            
            String jsonStr = this.client.executePostRequest(request);
            GabRelationship relationship = this.gson.fromJson(jsonStr, GabRelationship.class);
            
            return !relationship.isFollowing();
        } catch (Exception e) {
            throw new GabApiException(GabAccountApiImpl.class.getName(), e.getMessage());
        }
    }

    @Override
    public List<GabRelationship> checkrelationships(List<String> usersId) {
        String endpoint = this.apiConfig.getProperty("checkrelationships_endpoint");
        int okStatus = Integer.parseInt(this.apiConfig.getProperty("checkrelationships_ok_status"));
        
        try {
            GabRelationshipRequest relationshipRequest = new GabRelationshipRequest(usersId);
            String requestJson = this.gson.toJson(relationshipRequest);
            
            String uri = endpoint;
            ApiRequest request = new ApiRequest(uri, okStatus);
            
            request.addApiHeader("Content-Type", "application/json");
            request.addApiHeader("Authorization", "Bearer " + this.accessToken);
            request.setPayload(requestJson);
            
            String jsonStr = this.client.executePostRequest(request);
            
            return this.gson.fromJson(jsonStr, new TypeToken<List<GabRelationship>>(){}.getType());
        } catch (Exception e) {
            throw new GabApiException(GabAccountApiImpl.class.getName(), e.getMessage());
        }
    }

    @Override
    public List<GabAccount> getSuggestions(GabSuggestionType type) {
        String endpoint = this.apiConfig.getProperty("getSuggestions_endpoint");
        int okStatus = Integer.parseInt(this.apiConfig.getProperty("getSuggestions_ok_status"));
        
        try {
            String uri = endpoint + "?type=" + type.name().toLowerCase();
            ApiRequest request = new ApiRequest(uri, okStatus);
            // request.addApiHeader("type", type.name().toLowerCase());
            
            request.addApiHeader("Content-Type", "application/json");
            request.addApiHeader("Authorization", "Bearer " + this.accessToken);

            String jsonStr = this.client.executeGetRequest(request);
            GabSuggestionsResponse response = this.gson.fromJson(jsonStr, GabSuggestionsResponse.class);
            
            log.debug("Cuentas sugeridas encontradas: " + response.getAccounts().size());
            
            return response.getAccounts();
        } catch (Exception e) {
            throw new GabApiException(GabAccountApiImpl.class.getName(), e.getMessage());
        }
    }
}