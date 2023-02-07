package com.ssafy.commonpjt.api.controller;

import com.ssafy.commonpjt.api.service.ExplanationService;
import com.ssafy.commonpjt.common.exception.DuplicatedException;
import com.ssafy.commonpjt.common.exception.NotFoundUserException;
import com.ssafy.commonpjt.common.exception.NotMyContentsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/meetings/explanations")
public class ExplanationController {

    @Autowired
    private ExplanationService explanationService;

    @PostMapping("/{productIndex}")
    public ResponseEntity<?> addExplanation(@PathVariable int productIndex){
        try{
            explanationService.addProductExplanation(productIndex);
        }
        //잘못된 사용자의 접근
        catch(NotFoundUserException e){
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
        }
        //값 중복
        catch(DuplicatedException e){
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        //사용자는 맞는데 자신이 등록한 매물이 아님
        catch(NotMyContentsException e){
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
}
