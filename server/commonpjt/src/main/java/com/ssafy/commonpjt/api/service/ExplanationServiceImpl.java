package com.ssafy.commonpjt.api.service;

import com.ssafy.commonpjt.api.dto.explanationDTO.ExplanationDateDTO;
import com.ssafy.commonpjt.api.dto.explanationDTO.ExplanationInfoDTO;
import com.ssafy.commonpjt.api.dto.explanationDTO.ReserveExplanationDTO;
import com.ssafy.commonpjt.common.exception.DuplicatedException;
import com.ssafy.commonpjt.common.exception.NoContentException;
import com.ssafy.commonpjt.common.exception.NotFoundUserException;
import com.ssafy.commonpjt.common.exception.NotMyContentsException;
import com.ssafy.commonpjt.common.security.SecurityUtil;
import com.ssafy.commonpjt.db.entity.Explanation;
import com.ssafy.commonpjt.db.entity.Meeting;
import com.ssafy.commonpjt.db.entity.Product;
import com.ssafy.commonpjt.db.entity.User;
import com.ssafy.commonpjt.db.repository.ExplanationRepository;
import com.ssafy.commonpjt.db.repository.MeetingRepository;
import com.ssafy.commonpjt.db.repository.ProductRepository;
import com.ssafy.commonpjt.db.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ExplanationServiceImpl implements  ExplanationService{

    private static final Logger log = LoggerFactory.getLogger(ExplanationService.class);

    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    MeetingRepository meetingRepository;

    @Autowired
    ExplanationRepository explanationRepository;

    String oneOnManyUrl = "/meeting/one-on-many/";
    @Override
    @Transactional
    public void addProductExplanation(int productIndex, ExplanationDateDTO explanationDate) throws NotMyContentsException, NotFoundUserException, NoContentException, DuplicatedException, Exception {
        User user = userRepository.findByUserId(SecurityUtil.getLoginUsername()).orElseThrow(() -> new NotFoundUserException());
        Product product = productRepository.findById(productIndex).orElseThrow(()-> new NoContentException());

        Meeting meeting = meetingRepository.findByProductAndOwner(product, user);

        if(meeting !=null)
            throw new DuplicatedException();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.sss");
        log.info("date : " + explanationDate.getDateString());
        Date parsedDate = dateFormat.parse(explanationDate.getDateString());
        Timestamp timestamp = new Timestamp(parsedDate.getTime());

        int userIndex = user.getUserIndex();
        int productSellerIndex = product.getProductSeller().getUserIndex();

        //본인이 등록한 매물이 아니라면
        if(userIndex != productSellerIndex){
            throw new NotMyContentsException();
        }

        meeting = new Meeting().builder().owner(user).product(product).reserveAt(timestamp).build();
        meetingRepository.save(meeting);

        meeting = meetingRepository.findByProductAndOwner(product, user);

        String url = createOneOnManyUrl(meeting.getMeetingIndex());
        meeting.setMeetingUrl(url);
        meetingRepository.save(meeting);
    }

    @Transactional
    @Override
    public void reserveExplanation(ReserveExplanationDTO reserveExplanationDTO)
            throws NotFoundUserException, NoContentException, DuplicatedException, Exception {
        //구매자 정보
        User user = userRepository.findByUserId(SecurityUtil.getLoginUsername())
                                .orElseThrow(() -> new NotFoundUserException());
        int productIndex = reserveExplanationDTO.getProductIndex();
        log.info("meeting index" + productIndex);

        Product product = productRepository.findById(productIndex)
                                .orElseThrow(()-> new NoContentException());
        Meeting meeting = meetingRepository.findByProduct(product);

        //이미 등록된 예약이라면
        Explanation reservation = explanationRepository.findByMeetingAndReserveUser(meeting, user);
        if(reservation!=null)
            throw new DuplicatedException();

        Explanation explanation = Explanation.builder()
                .meeting(meeting)
                .reserveUser(user).build();

        explanationRepository.save(explanation);
    }

    public ExplanationInfoDTO getExplanationInfo(int productIndex)
            throws NoContentException, NotFoundUserException, Exception{
        Product product = productRepository.findById(productIndex).orElseThrow(()-> new NoContentException());
        Meeting meeting = meetingRepository.findByProduct(product);
        User requestUser = userRepository.findByUserId(SecurityUtil.getLoginUsername()).orElseThrow(() -> new NotFoundUserException());
        User productSeller = product.getProductSeller();

        ExplanationInfoDTO dto = new ExplanationInfoDTO();

        Explanation explanation = explanationRepository.findByMeetingAndReserveUser(meeting, requestUser);
        if(meeting!=null){
            dto.setMeetingIndex(meeting.getMeetingIndex());
            dto.setReservedAt(meeting.getReserveAt().toString());
        }
        dto.setSellerIndex(productSeller.getUserIndex());
        if(explanation!=null){
            User buyer = explanation.getReserveUser();
            dto.setBuyerIndex(buyer.getUserIndex());
        }
        return dto;
    }

    @Transactional
    @Override
    public void cancelReservation(int meetingIndex) throws NoContentException, NotFoundUserException, Exception {
        User requestUser = userRepository.findByUserId(SecurityUtil.getLoginUsername()).orElseThrow(() -> new NotFoundUserException());
        Meeting meeting = meetingRepository.findById(meetingIndex).orElseThrow(()-> new NoContentException());

        Explanation explanation = explanationRepository.findByMeetingAndReserveUser(meeting, requestUser);
        explanationRepository.delete(explanation);
    }

    private String createOneOnManyUrl(int index){
        StringBuilder sb = new StringBuilder();
        sb.append(oneOnManyUrl);
        sb.append(index);
        return sb.toString();
    }
}
