package com.lt.dom.serviceOtc;


import com.lt.dom.oct.*;
import com.lt.dom.repository.BookingRuleRepository;
import com.lt.dom.repository.GuideRepository;
import com.lt.dom.repository.InchargeBookingRepository;
import com.lt.dom.vo.GuideSummaryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GuideServiceImpl {


    @Autowired
    private GuideRepository guideRepository;

    @Autowired
    private IdGenServiceImpl idGenService;
    @Autowired
    private InchargeBookingRepository inchargeBookingRepository;


    public Guide beGuide(User user) {


        Guide guide = new Guide();
        guide.setUserId(user.getId());
        guide.setCode(idGenService.guideCode());
        guide.setReal_name(user.getRealName());
        guide.setId_card(user.getIdCard());
        guide.setPhone(user.getPhone());
        return guideRepository.save(guide);

    }


    public void guideToTourBooking(Guide guide, TourBooking tourBooking) {

        tourBooking.setAdditional_info_guide_name(guide.getReal_name());
        tourBooking.setAdditional_info_guide_id(guide.getId_card());
        tourBooking.setAdditional_info_guide_phone(guide.getPhone());


        GuideInchargeBooking guideInchargeBooking = new GuideInchargeBooking();
        guideInchargeBooking.setBooking(tourBooking.getId());
        guideInchargeBooking.setGuideId(guide.getId());
        guideInchargeBooking.setAgency(tourBooking.getOwner());
        inchargeBookingRepository.save(guideInchargeBooking);
    }

    public GuideSummaryVo guideSummary(Guide guide) {
        List<GuideInchargeBooking> guideInchargeBookings = inchargeBookingRepository.findAll();

        GuideSummaryVo guideSummaryVo = new GuideSummaryVo();
        guideSummaryVo.setTotal_incharge(guideInchargeBookings.size());

        return guideSummaryVo;
    }
}
