package com.laboratorio.gabapiinterface.impl;

import com.google.gson.reflect.TypeToken;
import com.laboratorio.clientapilibrary.model.ApiMethodType;
import com.laboratorio.clientapilibrary.model.ApiRequest;
import com.laboratorio.clientapilibrary.model.ApiResponse;
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
import java.util.stream.Collectors;

/**
 *
 * @author Rafael
 * @version 1.4
 * @created 11/09/2024
 * @updated 06/06/2025
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
            ApiRequest request = new ApiRequest(url, okStatus, ApiMethodType.GET);
            request.addApiHeader("Content-Type", "application/json");
            
            ApiResponse response = this.client.executeApiRequest(request);
            log.debug("Response getAccountById: {}", response.getResponseStr());
            
            return this.gson.fromJson(response.getResponseStr(), GabAccount.class);
        } catch (Exception e) {
            throw new GabApiException("Error recuperando los datos de la cuenta Gab con id: " + userId, e);
        }
    }
    
    @Override
    public GabAccount getAccountByUsername(String username) {
        String endpoint = this.apiConfig.getProperty("getAccountByUsername_endpoint");
        int okStatus = Integer.parseInt(this.apiConfig.getProperty("getAccountByUsername_ok_status"));
        
        try {
            String url = endpoint + "/" + username;
            ApiRequest request = new ApiRequest(url, okStatus, ApiMethodType.GET);
            request.addApiHeader("Content-Type", "application/json");
            
            ApiResponse response = this.client.executeApiRequest(request);
            log.debug("Response getAccountByUsername: {}", response.getResponseStr());
            
            return this.gson.fromJson(response.getResponseStr(), GabAccount.class);
        } catch (Exception e) {
            throw new GabApiException("Error recuperando los datos de la cuenta Gab con username: " + username, e);
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
        return this.getAccountList(instruccionInfo, userId, quantity, posicionInicial);
    }
    
    @Override
    public List<String> getFollowersIds(String userId, int limit) throws Exception {
        GabAccountListResponse response = this.getFollowers(userId, limit, 0, null);
        return response.getAccounts().stream()
                .map(account -> account.getId())
                .collect(Collectors.toList());
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
        return this.getAccountList(instruccionInfo, userId, quantity, posicionInicial);
    }
    
    @Override
    public List<String> getFollowingsIds(String userId, int limit) throws Exception {
        GabAccountListResponse response = this.getFollowings(userId, limit, 0, null);
        return response.getAccounts().stream()
                .map(account -> account.getId())
                .collect(Collectors.toList());
    }

    @Override
    public boolean followAccount(String userId) {
        String endpoint = this.apiConfig.getProperty("followAccount_endpoint");
        String complementoUrl = this.apiConfig.getProperty("followAccount_complemento_url");
        int okStatus = Integer.parseInt(this.apiConfig.getProperty("followAccount_ok_status"));
        
        try {
            String uri = endpoint + "/" + userId + "/" + complementoUrl;
            
            ApiRequest request = new ApiRequest(uri, okStatus, ApiMethodType.POST);
            request.addApiHeader("Content-Type", "application/json");
            request.addApiHeader("Authorization", "Bearer " + this.accessToken);
            
            ApiResponse response = this.client.executeApiRequest(request);
            log.debug("Response followAccount: {}", response.getResponseStr());
            GabRelationship relationship = this.gson.fromJson(response.getResponseStr(), GabRelationship.class);
            
            return relationship.isFollowing();
        } catch (Exception e) {
            throw new GabApiException("Error siguiendo la cuenta Gab con id: " + userId, e);
        }
    }

    @Override
    public boolean unfollowAccount(String userId) {
        String endpoint = this.apiConfig.getProperty("unfollowAccount_endpoint");
        String complementoUrl = this.apiConfig.getProperty("unfollowAccount_complemento_url");
        int okStatus = Integer.parseInt(this.apiConfig.getProperty("unfollowAccount_ok_status"));
        
        try {
            String uri = endpoint + "/" + userId + "/" + complementoUrl;

            ApiRequest request = new ApiRequest(uri, okStatus, ApiMethodType.POST);
            request.addApiHeader("Content-Type", "application/json");
            request.addApiHeader("Authorization", "Bearer " + this.accessToken);
            
            ApiResponse response = this.client.executeApiRequest(request);
            log.debug("Response unfollowAccount: {}", response.getResponseStr());
            GabRelationship relationship = this.gson.fromJson(response.getResponseStr(), GabRelationship.class);
            
            return !relationship.isFollowing();
        } catch (Exception e) {
            throw new GabApiException("Error dejando de seguir la cuenta Gab con id: " + userId, e);
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
            ApiRequest request = new ApiRequest(uri, okStatus, ApiMethodType.POST, requestJson);
            
            request.addApiHeader("Content-Type", "application/json");
            request.addApiHeader("Authorization", "Bearer " + this.accessToken);
            
            ApiResponse response = this.client.executeApiRequest(request);
            log.debug("Response checkrelationships: {}", response.getResponseStr());
            
            return this.gson.fromJson(response.getResponseStr(), new TypeToken<List<GabRelationship>>(){}.getType());
        } catch (Exception e) {
            throw new GabApiException("Error verificando las relaciones con cuentas Gab", e);
        }
    }

    @Override
    public List<GabAccount> getSuggestions(GabSuggestionType type) {
        String endpoint = this.apiConfig.getProperty("getSuggestions_endpoint");
        int okStatus = Integer.parseInt(this.apiConfig.getProperty("getSuggestions_ok_status"));
        
        try {
            String uri = endpoint + "?type=" + type.name().toLowerCase();
            ApiRequest request = new ApiRequest(uri, okStatus, ApiMethodType.GET);
            // request.addApiHeader("type", type.name().toLowerCase());
            
            request.addApiHeader("Content-Type", "application/json");
            request.addApiHeader("Authorization", "Bearer " + this.accessToken);

            ApiResponse response = this.client.executeApiRequest(request);
            GabSuggestionsResponse suggestionsResponse = this.gson.fromJson(response.getResponseStr(), GabSuggestionsResponse.class);
            
            log.debug("Cuentas sugeridas encontradas: " + suggestionsResponse.getAccounts().size());
            
            return suggestionsResponse.getAccounts();
        } catch (Exception e) {
            throw new GabApiException("Error recuperando las sugerencias de seguimiento de Gab", e);
        }
    }
}