package com.ssafy.commonpjt.api.controller;

import com.ssafy.commonpjt.api.dto.explanationDTO.ExplanationDateDTO;
import com.ssafy.commonpjt.api.dto.explanationDTO.ExplanationInfoDTO;
import com.ssafy.commonpjt.api.dto.explanationDTO.ReserveExplanationDTO;
import com.ssafy.commonpjt.api.service.ExplanationService;
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
    public ResponseEntity<?> addExplanation(@PathVariable int productIndex, @RequestBody ExplanationDateDTO explanationDate) throws Exception {
        explanationService.addProductExplanation(productIndex, explanationDate);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    //등록된 설명회를 구매자가 예약 추가
    @PostMapping("")
    public ResponseEntity<?> reserveExplanation(@RequestBody ReserveExplanationDTO reserveExplanationDTO) throws Exception {
        explanationService.reserveExplanation(reserveExplanationDTO);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @GetMapping("/{productIndex}")
    public ResponseEntity<?> getMeetingExplanationInfo(@PathVariable("productIndex") int productIndex) throws Exception {
        ExplanationInfoDTO info = null;
        info = explanationService.getExplanationInfo(productIndex);
        return ResponseEntity.ok(info);
    }

    @DeleteMapping("/cancel/{meetingIndex}")
    public ResponseEntity<?> cancelReservation(@PathVariable int meetingIndex) throws Exception {
        explanationService.cancelReservation(meetingIndex);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @DeleteMapping("/{meetingIndex}")
    public ResponseEntity<?> deleteExplanation(@PathVariable int meetingIndex) throws Exception {
        explanationService.deleteReservation(meetingIndex);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
