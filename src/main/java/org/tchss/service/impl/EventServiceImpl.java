package org.tchss.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tchss.model.Price;
import org.tchss.model.Event;
import org.tchss.model.EventType;
import org.tchss.repo.PriceRepository;
import org.tchss.repo.EventRepository;
import org.tchss.service.EventService;

import java.util.Calendar;
import java.util.List;

@Log4j2
@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private EventRepository eventRepository;

    @Override
    public List<Event> findAllByEventType(EventType eventType) {
        Calendar startDate = Calendar.getInstance();
        int year = startDate.get(Calendar.YEAR);
        startDate.set(year, 0, 1);

        Calendar endDate = Calendar.getInstance();
        endDate.set(year, 11, 31);

        return eventRepository.findAllForThisYearByType(eventType,
                startDate.getTime(),
                endDate.getTime());
    }

    @Override
    public Price getPrice() {
        Iterable<Price> allPrices = priceRepository.findAll();
        // There will only ever be a single Price object.
        return allPrices.iterator().next();
    }
}
