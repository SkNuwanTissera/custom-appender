package org.example;

import java.io.Serializable;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

@Plugin(name = "MapAppender", category = Core.CATEGORY_NAME, elementType = Appender.ELEMENT_TYPE)
public class MapAppender extends AbstractAppender {

    private ConcurrentMap<String, LogEvent> eventMap = new ConcurrentHashMap<>();

    protected MapAppender(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions, Property[] properties) {
        super(name, filter, layout, ignoreExceptions, properties);
    }

    @PluginFactory
    public static MapAppender createAppender(@PluginAttribute("name") String name, @PluginElement("filter") final Filter filter,
                                             @PluginElement("layout") Layout<? extends Serializable> layout,
                                             @PluginAttribute("ignoreExceptions") boolean ignoreExceptions,
                                             @PluginElement("properties") Property[] properties) {
        return new MapAppender(name, filter, layout, ignoreExceptions, properties);
    }

    @Override
    public void append(LogEvent event) {
        if (event.getLevel()
            .isLessSpecificThan(Level.WARN)) {
            error("Unable to log less than WARN level.");
            return;
        }
        eventMap.put(Instant.now().toString(), event);
        System.out.println(event.getMessage());
        System.out.println("##########");

        LogFormat logFormat = new LogFormat(event.getMessage().getFormattedMessage(), "24342");

        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = null;
        try {
            jsonInString = mapper.writeValueAsString(logFormat);
        } catch (JsonProcessingException ex) {
            java.util.logging.Logger.getLogger(this.getClass().getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        System.out.println(jsonInString);

    }

    public ConcurrentMap<String, LogEvent> getEventMap() {
        return eventMap;
    }

    public void setEventMap(ConcurrentMap<String, LogEvent> eventMap) {
        this.eventMap = eventMap;
    }

}