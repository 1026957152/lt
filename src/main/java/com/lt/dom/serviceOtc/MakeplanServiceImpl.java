package com.lt.dom.serviceOtc;


import com.google.gson.*;
import com.lt.dom.OctResp.BookingResourceResp;
import com.lt.dom.OctResp.BookingServiceReq;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.*;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.vo.AvailabilityCalendarVO;
import com.lt.dom.vo.ResourceOpeningHours;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MakeplanServiceImpl {
    @Autowired
    private BookingServiceRepository bookingServiceRepository;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private BookingProviderRepository bookingProviderRepository;
    @Autowired
    private BookingResourceRepository bookingResourceRepository;


    @Autowired
    private FileStorageServiceImpl fileStorageService;

    @Autowired
    private BookingMakeplanRepository bookingMakeplanRepository;

    @Autowired
    private IdGenServiceImpl idGenService;



    public BookingMakeplan create(BookingProvider supplier, BookingMakeplanReq theatreReq) {
        BookingMakeplan theatre = new BookingMakeplan();
      //  theatre.setName(theatreReq.getName());

        if(theatreReq.getAll_day()){
            theatre.setBookedFrom(LocalDateTime.now());
            theatre.setBookedTo(LocalDateTime.now());

        }else{
            theatre.setBookedFrom(theatreReq.getBookedFrom());
            theatre.setBookedTo(theatreReq.getBookedTo());

        }
        theatre.setSupplier(supplier.getSupplier());
        theatre.setProvider(supplier.getId());

      //  theatre.setName(supplier.getName());
        theatre.setCode(idGenService.makePlanCode());
        theatre.setState(EnumBookingMakeplanState.awaiting_confirmation);
        theatre.setActive(true);
        theatre.setNotes(theatre.getNotes());
        theatre.setExpiresAt(LocalDateTime.now().plusMinutes(20));

      theatre.setStatus(EnumBookingMakeplanStatus.in_progress_);


/*        theatre.setDay_count(theatreReq.getDay_count());
        theatre.setEnds_on(theatreReq.getEnds_on());
        theatre.setStarts_on(theatreReq.getStarts_on());*/


        theatre = bookingMakeplanRepository.save(theatre);




        return theatre;


    }

    public Trip update(Trip supplier, TripReq tripReq) {
        return supplier;
    }


    public BookingService editBookingService(BookingService showtime, BookingServiceReq bookingServiceResp) {

        showtime.setTitle(bookingServiceResp.getTitle());
        showtime.setDescription(bookingServiceResp.getDescription());

        showtime.setType(bookingServiceResp.getType());

        showtime = bookingServiceRepository.save(showtime);


        BookingService finalShowtime = showtime;
        bookingServiceResp.getMedias().stream().forEach(e->{
            if(e!=null){
                fileStorageService.saveFromTempDocumentCode(finalShowtime.getCode(), EnumDocumentType.makeplan_resource,e.getThumbnail());

            }
        });

        return showtime;
    }
    public BookingService createBookingService(Supplier supplier, BookingServiceReq bookingServiceResp) {
        BookingService showtime = new BookingService();

        showtime.setTitle(bookingServiceResp.getTitle());
        showtime.setDescription(bookingServiceResp.getDesc_long());
        showtime.setCode(idGenService.serviceCode());
        showtime.setType(bookingServiceResp.getType());
        showtime.setSupplier(supplier.getId());




        showtime.setPrivacyLevel(bookingServiceResp.getPrivacyLevel());
        showtime.setInterval(bookingServiceResp.getDuration());
        showtime.setSlug(bookingServiceResp.getSlug());
        showtime.setDesc_short(bookingServiceResp.getDesc_short());
        showtime = bookingServiceRepository.save(showtime);


        BookingService finalShowtime = showtime;
        bookingServiceResp.getMedias().stream().forEach(e->{
            if(e!=null){
                fileStorageService.saveFromTempDocumentCode(finalShowtime.getCode(), EnumDocumentType.makeplan_resource,e.getThumbnail());

            }
        });

        return showtime;
    }

    public BookingResource createResource(Supplier supplier, BookingResourceReq bookingResourceReq) {
        BookingResource showtime = new BookingResource();

        showtime.setTitle(bookingResourceReq.getTitle());
        showtime.setCode(idGenService.nextId(""));
        showtime.setSupplier(supplier.getId());
        showtime.setDesc_long(bookingResourceReq.getDesc_long());
        showtime.setDesc_short(bookingResourceReq.getDesc_short());

        showtime.setPrivacyLevel(bookingResourceReq.getPrivacyLevel());

        showtime.setType(bookingResourceReq.getType());

        if(bookingResourceReq.getType().equals(EnumResourceType.guide)){
            showtime.setGuideLevel(bookingResourceReq.getGuide().getGuideLevel());
            showtime.setService(bookingResourceReq.getGuide().getService());
        }



/*
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalTime>) (json, type, jsonDeserializationContext) ->
                ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString()).toLocalTime()).create();
*/


/*        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalTime>) (json, type, jsonDeserializationContext) ->
                new JsonPrimitive(json.format(DateTimeFormatter.ISO_LOCAL_DATE))).create();*/

        Gson gson = new GsonBuilder()
            //    .registerTypeAdapter(LocalDate.class, new JsonParse.LocalDateAdapter())
       //         .registerTypeAdapter(LocalDateTime.class, new JsonParse.LocalDateTimeAdapter())
                .registerTypeAdapter(LocalTime.class, new JsonParse.LocalTimeAdapter())

                .create();



        showtime.setOpeningHoursMon(gson.toJson(bookingResourceReq.getOpeningHoursMon()));
        showtime.setOpeningHoursTue(gson.toJson(bookingResourceReq.getOpeningHoursTue()));
        showtime.setOpeningHoursThu(gson.toJson(bookingResourceReq.getOpeningHoursThu()));
        showtime.setOpeningHoursWed(gson.toJson(bookingResourceReq.getOpeningHoursWed()));
        showtime.setOpeningHoursFri(gson.toJson(bookingResourceReq.getOpeningHoursFri()));
        showtime.setOpeningHoursSat(gson.toJson(bookingResourceReq.getOpeningHoursSat()));
        showtime.setOpeningHoursSun(gson.toJson(bookingResourceReq.getOpeningHoursSun()));


        showtime = bookingResourceRepository.save(showtime);




/*        if(bookingResourceReq.getAudioOption().equals(EnumAudioOption.custom_upload)){
            fileStorageService.saveFromTempDocumentCode(exhibition.getCode(), EnumDocumentType.WayPoint_audio,bookingResourceReq.getAudio());

        }*/


        BookingResource finalShowtime = showtime;
        bookingResourceReq.getMedias().stream().forEach(e->{
            if(e!=null){
                fileStorageService.saveFromTempDocumentCode(finalShowtime.getCode(), EnumDocumentType.makeplan_resource,e.getThumbnail());

            }
        });

        return showtime;
    }




    public BookingResource editResource(BookingResource showtime, BookingResourceReq bookingResourceReq) {

        showtime.setTitle(bookingResourceReq.getTitle());


        showtime = bookingResourceRepository.save(showtime);


        BookingResource finalShowtime = showtime;
        bookingResourceReq.getMedias().stream().forEach(e->{
            if(e!=null){
                fileStorageService.saveFromTempDocumentCode(finalShowtime.getCode(), EnumDocumentType.makeplan_resource,e.getThumbnail());

            }
        });

        return showtime;
    }


    public BookingProvider createProvider(Supplier supplier, BookingProviderReq movieReq) {
        BookingProvider showtime = new BookingProvider();

        showtime.setService(movieReq.getService());

        showtime.setSupplier(supplier.getId());
        showtime.setResource(movieReq.getResource());
        showtime.setActive(movieReq.getActive());

        showtime = bookingProviderRepository.save(showtime);
        return showtime;
    }


    public BookingProvider createProviderForBookingService(BookingService supplier, BookingProviderForServiceReq movieReq) {
        BookingProvider showtime = new BookingProvider();

        showtime.setSlug(supplier.getSlug());
        showtime.setTitle(movieReq.getTitle());
        showtime.setDesc_short(movieReq.getDesc_short());
        showtime.setDesc_long(movieReq.getDesc_long());
        showtime.setPrivacyLevel(movieReq.getPrivacyLevel());

        showtime.setService(supplier.getId());

        showtime.setSupplier(supplier.getSupplier());
        showtime.setResource(movieReq.getResource());
        showtime.setActive(movieReq.getActive());

        showtime.setActive(movieReq.getActive());

        showtime = bookingProviderRepository.save(showtime);
        return showtime;
    }

    public BookingProvider editProvider(BookingProvider showtime, BookingProviderReq movieReq) {


        showtime.setActive(movieReq.getActive());

        showtime = bookingProviderRepository.save(showtime);
        return showtime;
    }


    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");


    private void slots(LocalDate localDate, LocalTime[] localTimes, List<AvailabilityCalendarVO.OpeningHour> openingHours){

        for(int i = 0; i< localTimes.length-1 ; i++){
            AvailabilityCalendarVO.OpeningHour openingHour = new AvailabilityCalendarVO.OpeningHour();


            openingHour.setBooked_from(localDate.atTime(localTimes[i]));
            openingHour.setFrom(formatter.format(localTimes[i]));

            openingHour.setBooked_to(localDate.atTime(localTimes[i+1]));

            openingHour.setTo(formatter.format(localTimes[i+1]));
            openingHours.add(openingHour);
        };
    }
    public List<AvailabilityCalendarVO> getBookingResources_opening_hours(List<LocalDate> localDates, BookingResourceResp bookingResourceResp) {

        List<AvailabilityCalendarVO> aa =  localDates.stream().map(x->{

            AvailabilityCalendarVO availabilityCalendarVO = new AvailabilityCalendarVO();

            ResourceOpeningHours resourceOpeningHours = new ResourceOpeningHours();
            availabilityCalendarVO.setAvailable(true);
            availabilityCalendarVO.setBooking_at(x);
            availabilityCalendarVO.setBooking_at_text(AvailabilityCalendarVO.dealBooking_at_text(x));



            List<AvailabilityCalendarVO.OpeningHour> openingHourList = new ArrayList<>();

            availabilityCalendarVO.setOpeningHours(openingHourList);
            resourceOpeningHours.setDate(x);
            if(x.getDayOfWeek().equals(DayOfWeek.MONDAY)){


                slots(x,bookingResourceResp.getOpeningHoursMon(), openingHourList);

                resourceOpeningHours.setOpening_hours(Arrays.asList(bookingResourceResp.getOpeningHoursMon()));

                //  return x.getDayOfWeek() == DayOfWeek.MONDAY;
            }

            if(x.getDayOfWeek().equals(DayOfWeek.TUESDAY)){
                slots(x, bookingResourceResp.getOpeningHoursTue(), openingHourList);
                resourceOpeningHours.setOpening_hours(Arrays.asList(bookingResourceResp.getOpeningHoursTue()));

            }
            if(x.getDayOfWeek().equals(DayOfWeek.THURSDAY)){
                slots(x, bookingResourceResp.getOpeningHoursThu(), openingHourList);
                resourceOpeningHours.setOpening_hours(Arrays.asList(bookingResourceResp.getOpeningHoursThu()));

            }

            if(x.getDayOfWeek().equals(DayOfWeek.WEDNESDAY)){
                slots(x, bookingResourceResp.getOpeningHoursWed(), openingHourList);
                resourceOpeningHours.setOpening_hours(Arrays.asList(bookingResourceResp.getOpeningHoursWed()));


            }
            if(x.getDayOfWeek().equals(DayOfWeek.FRIDAY)){
                slots(x, bookingResourceResp.getOpeningHoursFri(), openingHourList);
                resourceOpeningHours.setOpening_hours(Arrays.asList(bookingResourceResp.getOpeningHoursFri()));

            }
            if(x.getDayOfWeek().equals(DayOfWeek.SATURDAY)){
                slots(x, bookingResourceResp.getOpeningHoursSat(), openingHourList);
                resourceOpeningHours.setOpening_hours(Arrays.asList(bookingResourceResp.getOpeningHoursSat()));


            }
            if(x.getDayOfWeek().equals(DayOfWeek.SUNDAY)){
                slots(x, bookingResourceResp.getOpeningHoursSun(), openingHourList);
                resourceOpeningHours.setOpening_hours(Arrays.asList(bookingResourceResp.getOpeningHoursSun()));

            }
            return availabilityCalendarVO;
        }).filter(e->e!=null).collect(Collectors.toList());


        return aa;



    }


}
