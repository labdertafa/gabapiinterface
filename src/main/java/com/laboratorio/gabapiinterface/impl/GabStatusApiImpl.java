package com.laboratorio.gabapiinterface.impl;

import com.laboratorio.clientapilibrary.model.ApiRequest;
import com.laboratorio.gabapiinterface.exception.GabApiException;
import com.laboratorio.gabapiinterface.model.GabMediaAttachment;
import com.laboratorio.gabapiinterface.model.GabStatus;
import com.laboratorio.gabapiinterface.GabStatusApi;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 12/09/2024
 * @updated 12/09/2024
 */
public class GabStatusApiImpl extends GabBaseApi implements GabStatusApi {
    public GabStatusApiImpl(String accessToken) {
        super(accessToken);
    }

    @Override
    public GabStatus postStatus(String text) {
        return this.postStatus(text, null);
    }

    @Override
    public GabStatus postStatus(String text, String imagenId) {
        String endpoint = this.apiConfig.getProperty("postStatus_endpoint");
        int okStatus = Integer.parseInt(this.apiConfig.getProperty("postStatus_ok_status"));
        
        try {
            String uri = endpoint;
            ApiRequest request = new ApiRequest(uri, okStatus);
            request.addApiPathParam("status", text);
            request.addApiPathParam("visibility", "public");
            request.addApiPathParam("language", "es");
            if (imagenId != null) {
                request.addApiPathParam("media_ids[]", imagenId);
            }
            
            request.addApiHeader("Content-Type", "application/json");
            request.addApiHeader("Authorization", "Bearer " + this.accessToken);
            
            String jsonStr = this.client.executePostRequest(request);
            return this.gson.fromJson(jsonStr, GabStatus.class);
        } catch (Exception e) {
            throw new GabApiException(GabAccountApiImpl.class.getName(), e.getMessage());
        }
    }

    @Override
    public GabMediaAttachment uploadImage(String filePath) {
        String endpoint = this.apiConfig.getProperty("UploadImage_endpoint");
        int okStatus = Integer.parseInt(this.apiConfig.getProperty("UploadImage_ok_status"));
        
        try {
            String uri = endpoint;
            
            ApiRequest request = new ApiRequest(uri, okStatus);
            request.addApiHeader("Authorization", "Bearer " + this.accessToken);
            request.addFileFormData("file", filePath);
                        
            String jsonStr = this.client.executePostRequest(request);
            
            return this.gson.fromJson(jsonStr, GabMediaAttachment.class);
        } catch (Exception e) {
            throw new GabApiException(GabAccountApiImpl.class.getName(), e.getMessage());
        }
    }
}