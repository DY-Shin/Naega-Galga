package com.ssafy.commonpjt.api.service;

import com.ssafy.commonpjt.api.dto.explanationDTO.ExplanationDateDTO;
import com.ssafy.commonpjt.api.dto.explanationDTO.ExplanationInfoDTO;
import com.ssafy.commonpjt.api.dto.explanationDTO.ReserveExplanationDTO;

public interface ExplanationService {
    void addProductExplanation(int productIndex, ExplanationDateDTO explanationDate) throws Exception;

    void reserveExplanation(ReserveExplanationDTO reserveExplanationDTO)
            throws Exception;

    ExplanationInfoDTO getExplanationInfo(int productIndex)
            throws Exception;

    void cancelReservation(int meetingIndex)
            throws Exception;

    void deleteReservation(int meetingIndex)
            throws Exception;
}
