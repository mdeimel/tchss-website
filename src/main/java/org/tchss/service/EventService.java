package org.tchss.service;

import org.tchss.model.Price;
import org.tchss.model.Event;
import org.tchss.model.EventType;

import java.util.List;

public interface EventService {

    public List<Event> findAllByEventType(EventType eventType);

    public Price getPrice();
}
