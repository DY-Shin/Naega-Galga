package com.ssafy.commonpjt.api.service;

import com.ssafy.commonpjt.common.exception.DuplicatedException;
import com.ssafy.commonpjt.common.exception.NoContentException;
import com.ssafy.commonpjt.common.exception.NotFoundUserException;
import com.ssafy.commonpjt.common.exception.NotMyContentsException;

public interface ExplanationService {
    public void addProductExplanation(int productIndex) throws NotMyContentsException, NotFoundUserException, NoContentException, DuplicatedException, Exception;
}
