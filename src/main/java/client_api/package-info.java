@XmlJavaTypeAdapters({
        @XmlJavaTypeAdapter(type = LocalTime.class, value = LocalTimeXmlAdapter.class),
        @XmlJavaTypeAdapter(type = Duration.class , value = DurationXmlAdapter.class),
        @XmlJavaTypeAdapter(type = LocalDate.class , value = LocalDateAdapter.class)
})


package client_api;

import client_api.XmlAdapter.LocalDateAdapter;
import com.migesok.jaxb.adapter.javatime.DurationXmlAdapter;
import com.migesok.jaxb.adapter.javatime.LocalTimeXmlAdapter;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

