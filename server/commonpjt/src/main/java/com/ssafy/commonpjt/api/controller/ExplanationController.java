package com.ssafy.commonpjt.api.controller;

import com.ssafy.commonpjt.api.dto.explanationDTO.ExplanationDateDTO;
import com.ssafy.commonpjt.api.dto.explanationDTO.ExplanationInfoDTO;
import com.ssafy.commonpjt.api.dto.explanationDTO.ReserveExplanationDTO;
import com.ssafy.commonpjt.api.service.ExplanationService;
import com.ssafy.commonpjt.common.exception.DuplicatedException;
import com.ssafy.commonpjt.common.exception.NoContentException;
import com.ssafy.commonpjt.common.exception.NotFoundUserException;
import com.ssafy.commonpjt.common.exception.NotMyContentsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/meetings/explanations")
public class ExplanationController {

    @Autowired
    private ExplanationService explanationService;

    //판매자가 설명회 등록
    @PostMapping("/{productIndex}")
    public ResponseEntity<?> addExplanation(@PathVariable int productIndex, @RequestBody ExplanationDateDTO explanationDate){
        try{
            explanationService.addProductExplanation(productIndex, explanationDate);
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

    //등록된 설명회를 구매자가 예약 추가
    @PostMapping("")
    public ResponseEntity<?> reserveExplanation(@RequestBody ReserveExplanationDTO reserveExplanationDTO){
        try{
            explanationService.reserveExplanation(reserveExplanationDTO);
        }
        catch(NotFoundUserException e){
            return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
        }
        catch(NoContentException e){
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
        catch(DuplicatedException e){
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        catch(Exception e){
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @GetMapping("/{productIndex}")
    public ResponseEntity<?> getMeetingExplanationInfo(@PathVariable("productIndex") int productIndex){
        ExplanationInfoDTO info = null;
        try {
            info = explanationService.getExplanationInfo(productIndex);
        }
        catch (NoContentException e) {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
        catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ExplanationInfoDTO>(info, HttpStatus.OK);
    }

    @DeleteMapping("/cancel/{meetingIndex}")
    public ResponseEntity<?> cancelReservation(@PathVariable int meetingIndex){
        try{
            explanationService.cancelReservation(meetingIndex);
        }
        catch(NotFoundUserException e){
            return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
        }
        catch(NoContentException e){
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @DeleteMapping("/{meetingIndex}")
    public ResponseEntity<Void> deleteExplanation(@PathVariable int meetingIndex){
        try{
            explanationService.deleteReservation(meetingIndex);
        }
        catch(NotMyContentsException e){
            return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
        }
        catch(NotFoundUserException e){
            return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
        }
        catch(NoContentException e){
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
