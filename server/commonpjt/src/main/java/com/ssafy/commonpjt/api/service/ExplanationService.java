package com.ssafy.commonpjt.api.service;

import com.ssafy.commonpjt.api.dto.explanationDTO.ExplanationInfoDTO;
import com.ssafy.commonpjt.api.dto.explanationDTO.ReserveExplanationDTO;
import com.ssafy.commonpjt.common.exception.DuplicatedException;
import com.ssafy.commonpjt.common.exception.NoContentException;
import com.ssafy.commonpjt.common.exception.NotFoundUserException;
import com.ssafy.commonpjt.common.exception.NotMyContentsException;

public interface ExplanationService {
    public void addProductExplanation(int productIndex) throws NotMyContentsException, NotFoundUserException, NoContentException, DuplicatedException, Exception;

    public void reserveExplanation(ReserveExplanationDTO reserveExplanationDTO)
            throws NotFoundUserException, NoContentException, DuplicatedException, Exception;

    public ExplanationInfoDTO getExplanationInfo(int meetingIndex)
            throws NoContentException, NotFoundUserException, Exception;
}
