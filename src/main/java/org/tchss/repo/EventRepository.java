package org.tchss.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.tchss.model.Event;
import org.tchss.model.EventType;

import java.util.Date;
import java.util.List;

public interface EventRepository extends CrudRepository<Event, Integer> {

    @Query("SELECT e FROM Event e " +
//            "WHERE e.eventType = :#{#eventType?.value} " +
            "WHERE e.eventType = :eventType " +
            "AND e.date >= :startDate " +
            "AND e.date <= :endDate")
    public List<Event> findAllForThisYearByType(EventType eventType, Date startDate, Date endDate);
}
